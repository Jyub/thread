package com.wy.yubiao.thread;

import org.apache.tomcat.jni.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;
import java.util.function.Supplier;


/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/5 10:45
 * @description: 打印类的结构
 */
public class PrintClassStructure {

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("e.g. java.util.Date");
        String name = scanner.next();
        Class<?> clz = Class.forName(name);
        //获取父类
        Class<?> superclass = clz.getSuperclass();
        //获取修饰符
        String modifier = Modifier.toString(clz.getModifiers());
        if (modifier.length() > 0) System.out.print(modifier+" ");
        System.out.print("class "+name);
        if (superclass != null && superclass != Object.class)
            System.out.print(" extends "+superclass.getName());
        System.out.println("{");
        //获取所有字段
        Field[] fields = clz.getDeclaredFields();
        System.out.println("=======================fields=====================");
        for (Field field : fields) {
            System.out.println("\t"+Modifier.toString(field.getModifiers())+" "
            +field.getType()+" "+field.getName()+";");
        }
        System.out.println("=======================fields=====================");
        //获取所有的构造器
        Constructor<?>[] constructors = clz.getConstructors();
        System.out.println("=======================constructors=====================");
        for (Constructor<?> constructor : constructors) {
            System.out.print("\t"+Modifier.toString(constructor.getModifiers())+" "+name);
            System.out.print("(");
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.print(parameterTypes[i].getName());
                if (i != parameterTypes.length-1)
                    System.out.print(",");
            }
            System.out.println(");");
        }
        System.out.println("=======================constructors=====================");
        //获取所有方法
        System.out.println("=======================allMethods=====================");
        Method[] methods = clz.getDeclaredMethods();
        printMethods(methods);
        System.out.println("=======================allMethods=====================");
        //获取所有方法
        System.out.println("=======================publicMethods=====================");
        Method[] publicMethods = clz.getMethods();
        printMethods(publicMethods);
        System.out.println("=======================publicMethods=====================");
        System.out.println("}");
    }

    private static void printMethods(Method[] methods){
        for (Method method : methods) {
            if (Modifier.isAbstract(method.getModifiers()))
                continue;
            System.out.print("\t"+Modifier.toString(method.getModifiers())+" "+method.getReturnType()
                    +" "+method.getName()+"(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.print(parameterTypes[i].getName());
                if (i != parameterTypes.length-1)
                    System.out.print(",");
            }
            System.out.println(");");
        }

    }
}
