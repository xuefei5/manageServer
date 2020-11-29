package com.simplemall.account.controller;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.util.DateUtils;
import com.simplemall.micro.serv.common.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
@Slf4j
@RestController
@RequestMapping("/zknh_Image_upload")
public class ZknhFtpClientController {
    //服务器上使用127.0.0.1,本地使用39.104.93.182
    private String FTP_ADDRESS = "127.0.0.1";     // ftp 服务器ip地址

    private Integer FTP_PORT = 21;       // ftp 服务器port，默认是21

    private String FTP_USERNAME = "ftpuser";    // ftp 服务器用户名

    private String FTP_PASSWORD = "ftpuser";    // ftp 服务器密码

    private String FTP_BASE_PATH = "/home/ftpuser/health";   // ftp 服务器存储图片的绝对路径

    private String FTP_THUMB_PATH_= FTP_BASE_PATH+"/thumbnail";

    private String IMAGE_BASE_URL = "http://39.104.93.182:80/images";  // ftp 服务器外网访问图片路径
    @RequestMapping(value ="/upload",method = RequestMethod.POST)
    public Result<?> pictureUpload(@RequestParam("file") MultipartFile uploadFile) {
        Result<?> result = new Result<>();

             result = uploadPicture(uploadFile);
            // 浏览器擅长处理json格式的字符串，为了减少因为浏览器内核不同导致的bug，建议用json
            //json = new ObjectMapper().writeValueAsString(result);
        return result;
    }
    //内部方法提供给control使用
    public Result<?> uploadPicture(MultipartFile uploadFile) {
        Result<?> result = new Result<>();
        try {
            //获取时间戳,生成新文件名
            String newFileName = DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get())+"."+ImageUtils.FILE_TYPE_JPG;

            //压缩图片--开始

            //压缩主文件
            BufferedImage mainImg = Thumbnails.of(uploadFile.getInputStream()).scale(0.75f)
                    .outputFormat(ImageUtils.FILE_TYPE_JPG).asBufferedImage();

            //创建一个新的BufferedImage，解决图片变色的问题
            BufferedImage mainImgCopy = new BufferedImage(mainImg.getWidth(),mainImg.getHeight(),BufferedImage.TYPE_INT_RGB);
            mainImgCopy.getGraphics().drawImage(mainImg, 0, 0, null);

            InputStream mainInput = ImageUtils.bufferedImageToInputStream(mainImgCopy,ImageUtils.FILE_TYPE_JPG);

            //上传主文件
            boolean resultBool = uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    mainInput, FTP_BASE_PATH, newFileName);

            //生成缩略图
            BufferedImage thumbnail = Thumbnails.of(uploadFile.getInputStream()).scale(0.25f)
                    .outputFormat(ImageUtils.FILE_TYPE_JPG).asBufferedImage();

            //创建一个新的BufferedImage，解决图片变色的问题
            BufferedImage thumbnailCopy = new BufferedImage(thumbnail.getWidth(),thumbnail.getHeight(),BufferedImage.TYPE_INT_RGB);
            mainImgCopy.getGraphics().drawImage(thumbnail, 0, 0, null);

            InputStream thumbnailInput = ImageUtils.bufferedImageToInputStream(thumbnailCopy,ImageUtils.FILE_TYPE_JPG);

            //上传缩略图
            boolean resultBool2 = uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    thumbnailInput, FTP_THUMB_PATH_, newFileName);

            //压缩图片-结束

            //返回结果
            if(!resultBool) {
                result.setMessage("上传失败！");
                result.setSuccess(false);
                return result;
            }else {
                result.setMessage(newFileName);
                result.setSuccess(true);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("上传失败！");
            result.setSuccess(false);
            return result;
        }
    }

    /**
     * ftp 上传图片方法
     * @param ip            ftp 服务器ip地址
     * @param port          ftp 服务器port，默认是21
     * @param account       ftp 服务器用户名
     * @param passwd        ftp 服务器密码
     * @param inputStream   文件流
     * @param workingDir    ftp 服务器存储图片的绝对路径
     * @param fileName      上传到ftp 服务器文件名
     * @throws Exception
     *
     */
    public boolean uploadFile(String ip, Integer port, String account, String passwd,
                              InputStream inputStream, String workingDir, String fileName) throws Exception{
        boolean result = false;
        // 1. 创建一个FtpClient对象
        FTPClient ftpClient = new FTPClient();
        try {
            // 2. 创建 ftp 连接
            ftpClient.connect(ip, port);
            // 3. 登录 ftp 服务器
            ftpClient.login(account, passwd);
            //ftpClient.enterLocalPassiveMode();
            int reply = ftpClient.getReplyCode(); // 获取连接ftp 状态返回值
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect(); // 如果返回状态不再 200 ~ 300 则认为连接失败
                return result;
            }
            // 5. 设置上传的路径
            ftpClient.changeWorkingDirectory(workingDir);
            // 6. 修改上传文件的格式为二进制
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 7. 服务器存储文件，第一个参数是存储在服务器的文件名，第二个参数是文件流
            if (!ftpClient.storeFile(fileName, inputStream)) {
                return result;
            }
            // 8. 关闭连接
            inputStream.close();
            ftpClient.logout();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // FIXME 听说，项目里面最好少用try catch 捕获异常，这样会导致Spring的事务回滚出问题？？？难道之前写的代码都是假代码！！！
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
   /* public static void main(String[] arg) throws IOException {

        Thumbnails.of("/home/ftpuser/health/about.jpg")
                .scale(0.75f)
                .outputQuality(0.1f).outputFormat("jpg")
                .toFile("/home/ftpuser/health/about.jpg");
        *//*Thumbnails.of("D:/h5/team/assets/images/wangshun.jpg")
                .scale(0.75f)
                .outputQuality(0.1f).outputFormat("jpg")
                .toFile("D:/h5/team/assets/images/wangshun.jpg");*//*
    }*/

}
