package com.demo.http.okhttp;

import com.demo.http.domain.User;
import com.demo.http.util.OkHttpClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okio.BufferedSink;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * http示例
 *
 * @author JustryDeng
 * @date 2019/6/6 23:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpDemo {
 
    private static final Logger logger = LoggerFactory.getLogger(HttpDemo.class);
 
    /** 使用jaskson作为json转换器 */
    private ObjectMapper jacksonMapper = new ObjectMapper();
 
    private static OkHttpClient client;
 
    static {
        client = OkHttpClientUtil.getHttpClient();
    }
 
    /**
     * get-同步
     */
    @Test
    public void getTestOne() throws IOException {
        // 地址必须要写上http/https
        String httpUrl = "http://127.0.0.1:8080/get/test?name=JustryDeng";
        // 默认即为get请求
        Request request = new Request.Builder()
                // 注意: header添加的是 唯一的 键值对
                //      .addHeader添加的是 同key,多value的键值
                //      header相当于Map,addHeader相当于Multimap
                .header("key1", "value1")
                .addHeader("key2", "value2")
                .url(httpUrl)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        // 响应头
        // response有多个提取header的方法，下面列举的只是其一
        Headers headers = response.headers();
        // 响应体
        ResponseBody responseBody = response.body();
        // 将响应体转换为 字符串
        String responseStr = responseBody == null ? null : responseBody.string();
        /*
         * 响应体的 string() 方法对于小文档来说十分方便、高效。
         * 但是如果响应体太大（超过1MB），应避免使用 string()方法 ，
         * 因为他会将把整个文档加载到内存中。对于超过1MB的响应body，
         * 应使用流的方式来处理body。
         */
        // 将响应体转换为 流
        // responseBody.byteStream();
        logger.info("响应内容为 -> {}", responseStr);
    }
 
    /**
     * get-异步
     *
     * 注:在一个工作线程中下载文件，当响应可读时回调Callback接口。读取
     *    响应时会阻塞当前线程。OkHttp现阶段不提供异步api来接收响应体。
     */
    @Test
    public void getTestTwo() throws InterruptedException {
        // 地址必须要写上http/https
        String httpUrl = "http://127.0.0.1:8080/get/test?name=邓沙利文";
        // 默认即为get请求
        Request request = new Request.Builder()
                // 注意: header添加的是 唯一的 键值对
                //      .addHeader添加的是 同key,多value的键值
                //      header相当于Map,addHeader相当于Multimap
                //.header("key1", "value1")
                //.addHeader("key2", "value2")
                .url(httpUrl)
                .build();
        //异步
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("请求失败！请求相关信息为 -> {}!", call.request(), e);
            }
 
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body() == null ? null : response.body().string();
                logger.info("请求成功！响应Response为 -> {}!, 响应体数据为 -> {}！", response, responseStr);
            }
        });
        // 为了观察异步线程的日志输出， 这里不妨让当前线程休眠几秒
        TimeUnit.SECONDS.sleep(5);
    }
 
    /**
     * post-同步-提交请求体数据为json等类型的数据
     */
    @Test
    public void postTestOne() throws IOException {
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        // 地址必须要写上http/https
        String url = "http://127.0.0.1:8080/post/test/one";
        User user = User.builder().name("张三").age(12).gender("男").motto("瓜兮兮~").build();
        String jsonStr = jacksonMapper.writeValueAsString(user);
        // 请求体数据类型任意，只要保证MediaType与其对应就行
        // 如: MediaType.parse("application/json; charset=utf-8");
        //     MediaType.parse("text/html; charset=utf-8");
        //     MediaType.parse("text/xml; charset=utf-8");
        //     ……
        // RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, jsonStr);
        RequestBody body = RequestBody.Companion.create(jsonStr, MEDIA_TYPE_JSON);
        Request request = new Request.Builder()
                // 注意: header添加的是 唯一的 键值对
                //      .addHeader添加的是 同key,多value的键值
                //      header相当于Map,addHeader相当于Multimap
                //.header("key1", "value1")
                //.addHeader("key2", "value2")
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        // 响应头
        // response有多个提取header的方法，下面列举的只是其一
        Headers headers = response.headers();
        Iterator<String> iterator = headers.names().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            logger.info("response hearder:{}={}", headerName, headers.get(headerName));
        }
        ResponseBody responseBody = response.body();
        // 将响应体转换为 字符串
        String responseStr = responseBody == null ? null : responseBody.string();
        // 将响应体转换为 流
        // responseBody.byteStream();
        logger.info("响应内容为 -> {}", responseStr);
    }
 
    /**
     * post-同步-提交表单
     */
    @Test
    public void postTestTwo() throws IOException {
        // 地址必须要写上http/https
        String url = "http://127.0.0.1:8080/post/test/two";
        RequestBody formBody = new FormBody.Builder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "XXXXXXXXXXXXXXX")
                .build();
 
        Request request = new Request.Builder()
                // 注意: header添加的是 唯一的 键值对
                //      .addHeader添加的是 同key,多value的键值
                //      header相当于Map,addHeader相当于Multimap
                //.header("key1", "value1")
                //.addHeader("key2", "value2")
                .url(url)
                .post(formBody)
                .build();
 
        Response response = client.newCall(request).execute();
        // 响应头
        // response有多个提取想用header的方法，下面列举的只是其一
        // response.headers();
        ResponseBody responseBody = response.body();
        // 将响应体转换为 字符串
        String responseStr = responseBody == null ? null : responseBody.string();
        // 将响应体转换为 流
        // responseBody.byteStream();
        logger.info("响应内容为 -> {}", responseStr);
    }
 
    /**
     * post-同步-提交流
     */
    @Test
    public void postTestThree() throws IOException {
        MediaType MEDIA_TYPE_HTML = MediaType.parse("text/html; charset=utf-8");
        // 地址必须要写上http/https
        String url = "http://127.0.0.1:8080/post/test/three";
        String streamData = "<b>我是流代表的数据~</b>";
        RequestBody streamBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_HTML;
            }
 
            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(streamData);
            }
        };
        Request request = new Request.Builder()
                // 注意: header添加的是 唯一的 键值对
                //      .addHeader添加的是 同key,多value的键值
                //      header相当于Map,addHeader相当于Multimap
                //.header("key1", "value1")
                //.addHeader("key2", "value2")
                .url(url)
                .post(streamBody)
                .build();
 
        Response response = client.newCall(request).execute();
        // 响应头
        // response有多个提取想用header的方法，下面列举的只是其一
        // response.headers();
        ResponseBody responseBody = response.body();
        // 将响应体转换为 字符串
        String responseStr = responseBody == null ? null : responseBody.string();
        // 将响应体转换为 流
        // responseBody.byteStream();
        logger.info("响应内容为 -> {}", responseStr);
    }
 
    /**
     * post-同步-提交文件
     */
    @Test
    public void postTestFour() throws IOException {
        MediaType MEDIA_TYPE_FILE = MediaType.parse("multipart/form-data");
        // 地址必须要写上http/https
        String url = "http://127.0.0.1:8080/post/test/four";
        File fileOne = new File("C:\\Users\\Administrator\\Desktop\\加解密.png");
        File fileTwo = new File("D:\\work\\关于停用美讯，使用美办办公的通知.pdf");
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                // 注意:服务端获取到的真实文件名，即为此处第二个参数的名字
                // 注意:表单的形式上传文件的话，需要key,第一个参数即为 对应的key
                .addFormDataPart("fileName", fileOne.getName(), RequestBody.create(MEDIA_TYPE_FILE, fileOne))
                .addFormDataPart("fileName", fileTwo.getName(), RequestBody.create(MEDIA_TYPE_FILE, fileTwo))
                .build();
        Request request = new Request.Builder()
                // 注意: header添加的是 唯一的 键值对
                //      .addHeader添加的是 同key,多value的键值
                //      header相当于Map,addHeader相当于Multimap
                //.header("key1", "value1")
                //.addHeader("key2", "value2")
                .url(url)
                .post(body)
                .build();
 
 
        Response response = client.newCall(request).execute();
        // 响应头
        // response有多个提取想用header的方法，下面列举的只是其一
        // response.headers();
        ResponseBody responseBody = response.body();
        // 将响应体转换为 字符串
        String responseStr = responseBody == null ? null : responseBody.string();
        // 将响应体转换为 流
        // responseBody.byteStream();
        logger.info("响应内容为 -> {}", responseStr);
    }
 
}