package com.wy.yubiao.aop;

import com.wy.yubiao.aop.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/19 16:48
 * @description: TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private User user;

    @RequestMapping("/info")
    public String info(){
        user.setId(10L);
        return user.getId().toString();
    }

}
