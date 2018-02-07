package com.myitech.demos.generics;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by A.T on 2018/01/31
 */
public class GenericSample {
    /**
     *  1. The cast is slightly annoying.
     *  2. It also introduces the possibility of a run time error, since the programmer may be mistaken.
     */
    public static void castType() {
        List myList = new LinkedList();
        myList.add(new Integer(2));
        Integer x = (Integer)myList.iterator().next();
        assert x == 2;
    }

    public static void simpleGeneric() {
        List<Integer> myList = new LinkedList<>();
        myList.add(2);
        Integer x = myList.iterator().next();
        assert x == 2;
    }

    public static void showCompileError() {
        List l1 = new LinkedList<Runnable>();
        l1.add(new Object()); // no compile error
        l1.add(new Thread()); // no compile error

        List<Runnable> l2 = new LinkedList<>();
//        l2.add(new Object()); // compile error
        l2.add(new Thread()); // no compile error

        List<?> l3 = new LinkedList<Runnable>();
//        l3.add(new Object()); // compile error
//        l3.add(new Thread()); // compile error

        Canvas c = new Canvas();
        List<Circle> clist = new LinkedList<>();
        List<Shape> slist = new LinkedList<>();
        slist.add(new Circle());
        clist.add(new Circle());

        c.drawAll(slist); // no compile error
        c.drawAll2(slist); // no compile error
//        c.drawAll(clist); // compile error
        c.drawAll2(clist); // no compile error


    }

    private static <T> List getList(T[] ts) {
        List<T> myList = new LinkedList<>();
        for (T t : ts) {
            myList.add(t);
        }
        return myList;
    }

    private static List getShapes() {
        List<Shape> myList = new LinkedList<>();
        myList.add(new Circle());
        myList.add(new Circle());
        myList.add(new Rectangle());
        return myList;
    }

    public static void showWildCards() {
        List<Integer> list= getList(new Integer[]{1, 2, 3, 4, 5});
        List list2 = new LinkedList<Integer>();
        list2.add(2);
        list2.add(3);

        printCollection1(list);
        //printCollection2(list); // compile error
        printCollection2(list2);
        printCollection3(list);
    }


    // Silly method with compile error
    static void fromArrayToCollection1(Object[] a, Collection<?> c) {
        for (Object o : a) {
//            c.add(o); // compile-time error
        }
    }

    // Method with generic type parameters
    private static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
        for (T o : a) {
            c.add(o); // Correct
        }
    }

    public static void showGenericMethod() {
        Object[] oa = new Object[100];
        Collection<Object> co = new LinkedList<>();

        // T inferred to be Object
        fromArrayToCollection(oa, co);

        String[] sa = new String[100];
        Collection<String> cs = new LinkedList<>();

        // T inferred to be String
        fromArrayToCollection(sa, cs);

        // T inferred to be Object
        fromArrayToCollection(sa, co);

    }


    // What's the element's type?
    private static void printCollection1(Collection c) {
        Iterator i = c.iterator();
        for (int k = 0; k < c.size(); k++) {
            System.out.println(i.next());
        }
    }

    // Object is nonsense !!
    private static void printCollection2(Collection<Object> c) {
        for (Object e : c) {
            System.out.println(e);
        }
    }

    // Yes, Integer is good!
    private static void printCollection3(Collection<? extends Integer> c) {
        for (Integer e : c) {
            System.out.println(e + 2);
        }
    }

    // Another way
    private static <T> void printCollection4(Collection<T> c) {
        for (T t: c) {
            System.out.println(t);
        }
    }

    private static class Canvas{
        public void draw(Shape s) {
            s.draw(this);
        }

        public void drawAll(List<Shape> shapes) {
            for (Shape s: shapes) {
                s.draw(this);
            }
        }

        public void drawAll2(List<? extends Shape> shapes) {
            for (Shape s: shapes) {
                s.draw(this);
            }
        }
    }

    private static abstract class Shape{
        public abstract void draw(Canvas c);
    }

    public static class Circle extends Shape {
        private int x, y, radius;
        public void draw(Canvas c) {
            System.out.println("Circle");
        }
    }

    public static class Rectangle extends Shape {
        private int x, y, width, height;
        public void draw(Canvas c) {
            System.out.println("Rectangle");
        }
    }
}
