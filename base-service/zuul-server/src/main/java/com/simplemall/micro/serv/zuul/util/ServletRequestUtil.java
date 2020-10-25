package com.simplemall.micro.serv.zuul.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ServletRequestUtil {
    private static ObjectMapper om = new ObjectMapper();

    public static HashMap<String, Object> getJson(InputStream getInputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        //将空格和换行符替换掉避免使用反序列化工具解析对象时失败
        String jsonString = sb.toString().replaceAll("\\s", "").replaceAll("\n", "");
        //json转map<String,Object>
        HashMap<String, Object> mmap = om.readValue(jsonString, HashMap.class);
        return mmap;

    }
}
