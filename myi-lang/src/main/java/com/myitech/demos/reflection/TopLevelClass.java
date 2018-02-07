package com.myitech.demos.reflection;

/**
 * Description:
 *
 *      1. member nested class：作为 enclosing class 的成员定义的，成员嵌套类有enclosing class属性
 *      2. local nested class: 定义在 enclosing class 的方法里面，局部嵌套类有enclosing class 属性和enclosing method 属性
 *      3. anonymous nested class：匿名嵌套类没有显示的定义一个类，直接通过new 的方法创建类的实例。一般回调模式情况下使用的比较多
 *
 *
 * Created by A.T on 2018/01/30
 */
public class TopLevelClass {
    private class InnerClass {
    }

    private static class NestedClass {
    }

    public void hi() {
        class LocalClass {

        }

        // 匿名类
        System.out.print(new InnerClass().toString());
    }
}
