package com.demo.http.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchangyong
 * @date 2020-05-21 11:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String name;
    private Integer age;
    private String gender;
    private String motto;
}
