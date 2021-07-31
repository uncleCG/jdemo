package com.demo.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JacksonTestMain {

    public static void main(String[] args) {
        // String jsonString = "{\"error_code\":0,\"error_msg\":\"ok\",\"data\":{\"watercount\":97,\"electriccount\":31,\"clodcount\":50,\"hotcount\":43,\"waterpercent\":37,\"electricpercent\":39,\"clodpercent\":100,\"hotpercent\":55,\"carbonemission\":29}}";
        String respJsonStr = "{\"error_code\":0,\"error_msg\":\"ok\",\"data\":[{\"watercount\":97,\"electriccount\":31,\"clodcount\":50,\"hotcount\":43,\"waterpercent\":37,\"electricpercent\":39,\"clodpercent\":100,\"hotpercent\":55,\"carbonemission\":29}]}";
        List<DemoBean> respEnergyUsedStatistics = parseRespData(respJsonStr, DemoBean.class);

        CommonResp<DemoBean> respEnergyUsedStatisticsCommonResp = parseResp(respJsonStr, DemoBean.class);
        List<DemoBean> data = (List<DemoBean>) respEnergyUsedStatisticsCommonResp.getData();
    }

    /**
     * 解析泛型resp
     *
     * @param respJsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> parseRespData(String respJsonStr, Class<T> tClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType innerType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(CommonResp.class, innerType);
            CommonResp<T> commonResp = objectMapper.readValue(respJsonStr, javaType);
            return objectMapper.convertValue(commonResp.getData(), innerType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析泛型resp
     *
     * @param respJsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> CommonResp<T> parseResp(String respJsonStr, Class<T> tClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType innerType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(CommonResp.class, innerType);
            return objectMapper.readValue(respJsonStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
