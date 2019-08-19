package com.wy.yubiao.aop;

import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/19 16:32
 * @description: TODO
 */
@Component
public class User {

    private String name;
    private Long id;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
