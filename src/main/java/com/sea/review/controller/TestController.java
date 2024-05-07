package com.sea.review.controller;


import com.alibaba.fastjson.JSON;
import com.sea.review.bean.Person;
import com.sea.review.service.ExposeService;
import com.sea.review.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Set;

@RestController
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private ExposeService exposeService;

    @GetMapping("test")
    public String test(String name) {
        System.out.println("==enter test");
        testService.testNo();
        Person person = new Person();
        person.setName("cjq");
        person.setBirth(new Date());
        return JSON.toJSONString(person);
    }


    @PostMapping("json")
    public String test(@RequestBody Person person) {
        Set<String> set = person.getSet();
        System.out.println(JSON.toJSONString(set));
        return JSON.toJSONString(person);
    }

    @PostMapping("form")
    public String form(Person person) {
        return JSON.toJSONString(person);
    }


    @GetMapping("expose")
    public String expose() {
        exposeService.exposeOne();
        exposeService.exposeTwo();
        return "Success";
    }

    /**
     * 下载导入模版
     *
     * @param param
     * @return
     */
    @PostMapping("/template")
    public ResponseEntity<byte[]> uploadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "月营收模版.xlsx";
        //返回包含下载文件数据的响应结果
        return getBytesFromFile(fileName);
    }

    public static String RESOURCE_PATH = "/template/";

    private ResponseEntity<byte[]> getBytesFromFile(String fileName) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(RESOURCE_PATH + fileName);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(inputStream.available());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        // 根据浏览器类型，决定"Content-Disposition"响应头的值
        builder.header("Content-Disposition", "attachment;filename=" + fileName);
        //获取文件大小
        int length = inputStream.available();
        //读取文件字节，存放在字节数组中
        int bytesRead = 0;
        byte[] buff = new byte[length];
        while (bytesRead < length) {
            int result = inputStream.read(buff, bytesRead, length - bytesRead);
            if (result == -1) {
                break;
            }
            bytesRead += result;
        }
        inputStream.close();
        return builder.body(buff);
    }
}
