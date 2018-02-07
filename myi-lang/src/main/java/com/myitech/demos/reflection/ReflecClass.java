package com.myitech.demos.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 *      1. Every type is either a reference or a primitive. Classes, enums, and arrays as well as interfaces are all reference types.
 *      2. Every type of object has an immutable instance of java.lang.Class.
 *      3. Class is the entry point for all of the Reflection APIs.
 *
 * <p>
 * Created by A.T on 2018/01/30
 */
// Top level class
public class ReflecClass {

    private enum E {A, B}

    // Inner class
    private class MemberInnerClass {

    }

    // Inner interface
    private interface Hello {
        void hi();
    }

    // Nested class
    private static class StaticInnerClass {
    }

    private static class ChildClass extends ReflecClass {

    }

    public MemberInnerClass getMemberInnerClassInstance() {
        return new MemberInnerClass();
    }

    static public Class getClassMethods() throws ClassNotFoundException {
        Class s1 = "foo".getClass();
        Class s2 = String.class;
        Class s3 = Class.forName("java.lang.String");

        ReflecClass ro = new ReflecClass();
        Class r = ro.getClass();
        Class r2 = ReflecClass.class;

        Class e = E.A.getClass();
        Class e2 = E.class;

        List l = new ArrayList<String>();
        Class a = l.getClass();
        Class a2 = ArrayList.class;

        String[][] ss = new String[][]{};
        Class ss1 = ss.getClass();
        Class ss2 = String[][].class;
        Class ss3 = Class.forName("[[Ljava.lang.String;");

        return null;
    }

    static public void getClassesMethods() {
        Class c = Character.class.getSuperclass();

        // Declared: all include public, protected, private
        Class<?>[] cs1 = Character.class.getDeclaredClasses();
        Class<?>[] cs2 = Character.class.getClasses();


        /**
         *    There are five kinds of classes (or interfaces):
         *      a) Top level classes
         *      b) Nested classes (static member classes)
         *      c) Inner classes (non-static member classes)
         *      d) Local classes (named classes declared within a method)
         *      e) Anonymous classes
         *
         *    DeclaringClass:
         *    如果一个类A定义在另外一个类B的内部（nested, inner, local, or anonymous）,那么B就是A的DeclaringClass
         *
         */
        System.out.println( StaticInnerClass.class.getDeclaringClass() );
        System.out.println( StaticInnerClass.class.getEnclosingClass() );
        System.out.println( MemberInnerClass.class.getDeclaringClass() );
        System.out.println( MemberInnerClass.class.getEnclosingClass() );
        System.out.println( new ChildClass().getMemberInnerClassInstance().getClass().getEnclosingClass() );
        System.out.println( new ChildClass().getMemberInnerClassInstance().getClass().getDeclaringClass() );

        String str = "Hello World!";
        String name = "ReflectClass";

        // Different between DeclaringClass and EnclosingClass:
        // Anonymous Class Declarations will not have a declaring class but will have an enclosing class.
        new ReflecClass().print(new Hello() {
            private String name = "Hello";

            @Override
            public void hi() {
                System.out.println(str); // Hello World!
                System.out.println(name); // Hello

                assert this.getClass().isAnonymousClass(); // 匿名类
                assert Hello.class.isAssignableFrom(this.getClass()); // instance of Hello

                System.out.println(this.getClass().getDeclaringClass()); // null
                System.out.println(this.getClass().getEnclosingClass()); // ReflectClass
            }
        });
    }

    private void print(Hello hello) {
        hello.hi();
    }




}
