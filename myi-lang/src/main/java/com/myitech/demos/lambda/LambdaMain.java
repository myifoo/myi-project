package com.myitech.demos.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Description:
 *
 *      1. Lambda expressions enable to treat functionality as method argument, or code as data;
 *
 *
 * java.util.function 内部接口：Predicate, Function, Consumer, Supplier
 *
 *
 * <p>
 * Created by A.T on 2018/01/30
 */
public class LambdaMain {

    public static void main(String[] args) {


        /**
         *  Example 1 : lambda 是不可变闭包，不能修改 local 的值
         *
         *      1. lambda内部可以使用静态、非静态和局部变量，这称为lambda内的变量捕获。
         *      2. lambda 表达式中对外部变量的修改不会影响变量本身
         *
         */
        String local = "main";
        new Thread(() -> System.out.println("local variable : "+local)).start(); // lambda + local variable use
        new Thread(() -> System.out.println("local variable : "+local.replace("m", "M"))).start(); // lambda + local variable use

        System.out.println(local); // 依然是 main

        //
        /**
         *  Example 2 ： Method reference
         *
         *      1. lambda表达式内可以使用方法引用
         *      2. 当需要对参数做修改时，这无法使用方法引用
         */
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(System.out::println); // Java8 , use method reference instead.
        features.forEach(n -> System.out.println(n+" XX"));


        // Example 3 : Predicate
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        System.out.println("Languages which starts with J :");
        filter(languages, (str)->str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->str.length() > 4);

        // Example 4:  Predication A and Predication B
        Predicate<String> A = (n) -> n.startsWith("J"); // Start with J
        Predicate<String> B = (n) -> n.length() == 4; // length is 4
        languages.stream().filter(A.and(B)).forEach(System.out::println);


        // Example 5: map & reduce & collect
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost; // Step 1: map
            total = total + price;          // Step 2: reduce
        }
        System.out.println("Total : " + total);

        // lambda + map + reduce
        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);


        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String G7Countries = G7.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
        System.out.println(G7Countries);

    }

    public static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " xx");
            }
        }
    }

}
