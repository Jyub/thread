package com.wy.yubiao.thread;

/**
 * @program: thread
 * @author: yubiao
 * @create: 2019/08/04 19:53
 * @description: 测试类初始化
 */
public class TestClassInit {
    /**
     * 类初始化时，若未赋值，则虚拟机自动赋零值
     * 然后检查域赋值和代码块赋值，根据先后顺序初始化，后边的初始化会将前边的相同字段的值覆盖
     * 然后调用构造器初始化，若构造器中第一行调用另一个构造器则跳转到指定构造器执行
     * 调用构造器初始化
     */

    private int id = 1;
    {
        id = 10;
        flag = false;
    }
    private boolean flag = true;
    private Double d;

    @Override
    public String toString() {
        return "TestClassInit{" +
                "id=" + id +
                ", flag=" + flag +
                ", d=" + d +
                ", clazz=" + clazz +
                '}';
    }

    private Class clazz = int.class;

    public TestClassInit() {
        this(0,false,null);
        this.id = 20;
    }

    public TestClassInit(int id, boolean flag, Double d) {
        this.id = id;
        this.flag = flag;
        this.d = d;
    }

    public static void main(String[] args) {
        TestClassInit testClassInit = new TestClassInit();
        System.out.println(testClassInit.toString());
    }

}
