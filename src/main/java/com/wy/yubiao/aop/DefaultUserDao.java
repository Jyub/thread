package com.wy.yubiao.aop;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/19 17:37
 * @description: TODO
 */
public class DefaultUserDao implements UserDao{

    @Override
    public void findOne() {
        System.out.println("i am here");
    }
}
