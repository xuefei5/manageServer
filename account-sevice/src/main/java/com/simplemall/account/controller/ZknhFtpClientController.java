package com.simplemall.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplemall.micro.serv.common.bean.Result;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/zknh_Image_upload")
public class ZknhFtpClientController {
    private String FTP_ADDRESS = "39.104.93.182";     // ftp 服务器ip地址

    private Integer FTP_PORT = 21;       // ftp 服务器port，默认是21

    private String FTP_USERNAME = "ftpuser";    // ftp 服务器用户名

    private String FTP_PASSWORD = "ftpuser";    // ftp 服务器密码

    private String FTP_BASE_PATH = "/home/ftpuser/health";   // ftp 服务器存储图片的绝对路径

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
            // 1. 取原始文件名
            String oldName = uploadFile.getOriginalFilename();

            // 2. ftp 服务器的文件名
            String newName = oldName;
            //图片上传
            boolean resultBool = uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    uploadFile.getInputStream(), FTP_BASE_PATH, newName);
            //返回结果
            if(!resultBool) {
                result.setMessage("上传失败！");
                result.setSuccess(false);
                return result;
            }
            result.setMessage(newName);
            result.setSuccess(true);
            return result;

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
            int reply = ftpClient.getReplyCode(); // 获取连接ftp 状态返回值
            System.out.println("code : " + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect(); // 如果返回状态不再 200 ~ 300 则认为连接失败
                return result;
            }
            // 4. 读取本地文件
//          FileInputStream inputStream = new FileInputStream(new File("F:\\hello.png"));
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
}
