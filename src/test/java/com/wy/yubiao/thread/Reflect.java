package com.wy.yubiao.thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/5 9:54
 * @description: 反射
 */
public class Reflect {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Dto> dtoClass = Dto.class;
        Constructor<Dto> constructor = dtoClass.getDeclaredConstructor(new Class[]{});
        constructor.setAccessible(true);
        Dto dto =  constructor.newInstance();
        System.out.println(dto.getId());
        Dto[] dtos = new Dto[]{dto};
        Arrays.sort(dtos, comparing(Dto::getId,nullsFirst(naturalOrder())));
    }

}

class Dto{

    private Integer id;
    private String name;

    private Dto(){
        this(1,"yubiao");
    }

    private Dto(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}