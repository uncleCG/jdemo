package com.demo.http.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author zhangchangyong
 * @date 2020-05-22 19:15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateDemo {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getTest1() {
        String httpUrl = "http://127.0.0.1:8080/get/test?name=zhangchangyong";
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(httpUrl, String.class);
        log.info("statusCodeValue={}", responseEntity.getStatusCodeValue());
        log.info("body={}", responseEntity.getBody());
    }

    public void test() {
        String rootUrl = "";
        //1. 简单Get请求
        String result = restTemplate.getForObject(rootUrl + "get1?para=my", String.class);
        System.out.println("简单Get请求:" + result);

        //2. 简单带路径变量参数Get请求
        result = restTemplate.getForObject(rootUrl + "get2/{1}", String.class, 239);
        System.out.println("简单带路径变量参数Get请求:" + result);

        //3. 返回对象Get请求（注意需包含compile group: 'com.google.code.gson', name: 'gson', version: '2.8.5'）
        ResponseEntity<Test1> responseEntity = restTemplate.getForEntity(rootUrl + "get3/339", Test1.class);
        System.out.println("返回:" + responseEntity);
        System.out.println("返回对象Get请求:" + responseEntity.getBody());

        //4. 设置header的Get请求
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "123");
        ResponseEntity<String> response = restTemplate.exchange(rootUrl + "get4", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        System.out.println("设置header的Get请求:" + response.getBody());

        //5. Post对象
        Test1 test1 = new Test1();
        test1.setName("buter");
        test1.setSex(1);
        result = restTemplate.postForObject(rootUrl + "post1", test1, String.class);
        System.out.println("Post对象:" + result);

        //6. 带header的Post数据请求
        response = restTemplate.postForEntity(rootUrl + "post2", new HttpEntity<Test1>(test1, headers), String.class);
        System.out.println("带header的Post数据请求:" + response.getBody());

        //7. 带header的Put数据请求
        //无返回值
        restTemplate.put(rootUrl + "put1", new HttpEntity<Test1>(test1, headers));
        //带返回值
        response = restTemplate.exchange(rootUrl + "put1", HttpMethod.PUT, new HttpEntity<Test1>(test1, headers), String.class);
        System.out.println("带header的Put数据请求:" + response.getBody());

        //8. del请求
        //无返回值
        restTemplate.delete(rootUrl + "del1/{1}", 332);
        //带返回值
        response = restTemplate.exchange(rootUrl + "del1/332", HttpMethod.DELETE, null, String.class);
        System.out.println("del数据请求:" + response.getBody());
    }

}

class Test1 {

    private String name;
    private Integer sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
