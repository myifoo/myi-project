package com.myitech.demos.reflection;

/**
 * Description:
 *      1. The abilities to examine or modify the runtime behavior of applications.
 *      2. Reflection is powerful, but should not be used indiscriminately.
 *      3. Performance Overhead, Security Restrictions, Exposure of Internals
 *
 * 反射的对象主要有三种： Classes，Members，and Arrays/Enumerated Types
 *      Classes：
 *      Members：Field, Method, Constructor
 *
 * 反射的内容：name,type,modifiers,value,parameters,invoke,relations ...
 *
 *
 * Created by A.T on 2018/01/30
 */
public class ReflectionMain {
    public static void main(String[] args) {
        try {

//            ReflecClass.getClassMethods();
//            ReflecClass.getClassesMethods();
//
//            ReflectModifier.spy("java.util.concurrent.ConcurrentSkipListMap"); // public
//            ReflectModifier.spy("java.util.concurrent.ConcurrentNavigableMap"); // public abstract interface
//            ReflectModifier.spy("com.myitech.demos.reflection.ModifierModel");

            ReflectModifier.classSpy(new String[] {"java.lang.ClassCastException", "CONSTRUCTOR"});
            ReflectModifier.classSpy(new String[] {"java.nio.channels.ReadableByteChannel", "METHOD"});
            ReflectModifier.classSpy(new String[] {ClassMember.class.getCanonicalName(), "FIELD", "METHOD"});


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
