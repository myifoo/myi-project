package com.myitech.demos.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 *  Description:
 *      Access Modifier: public, protected, private
 *      Modifier requiring override: abstract
 *      Modifier restricting to one instance: static
 *      Modifier prohibiting value modification: final
 *      Annotations
 *
 * <p>
 *  Examples show  how to obtain the declaration components of a class including the modifiers,
 *  generic type parameters, implemented interfaces, and the inheritance path.
 *
 * <p>
 * Created by A.T on 2018/01/30
 */
enum ClassMember { CONSTRUCTOR, FIELD, METHOD, CLASS, ALL }

public class ReflectModifier {

    static public void spy(String className) {
        try {
            Class<?> c = Class.forName(className);
            out.format("Class:%n  %s%n%n", c.getCanonicalName());
            out.format("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));

            out.format("Type Parameters:%n");
            TypeVariable[] tv = c.getTypeParameters();
            if (tv.length != 0) {
                out.format("  ");
                for (TypeVariable t : tv)
                    out.format("%s ", t.getName());
                out.format("%n%n");
            } else {
                out.format("  -- No Type Parameters --%n%n");
            }

            out.format("Implemented Interfaces:%n");
            Type[] intfs = c.getGenericInterfaces();
            if (intfs.length != 0) {
                for (Type intf : intfs)
                    out.format("  %s%n", intf.toString());
                out.format("%n");
            } else {
                out.format("  -- No Implemented Interfaces --%n%n");
            }

            out.format("Inheritance Path:%n");
            List<Class> l = new ArrayList<Class>();
            printAncestor(c, l);
            if (l.size() != 0) {
                for (Class<?> cl : l)
                    out.format("  %s%n", cl.getCanonicalName());
                out.format("%n");
            } else {
                out.format("  -- No Super Classes --%n%n");
            }

            out.format("Annotations:%n");
            Annotation[] ann = c.getAnnotations();
            if (ann.length != 0) {
                for (Annotation a : ann)
                    out.format("  %s%n", a.toString());
                out.format("%n");
            } else {
                out.format("  -- No Annotations --%n%n");
            }

            // production code should handle this exception more gracefully
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
    }

    private static void printAncestor(Class<?> c, List<Class> l) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
            printAncestor(ancestor, l);
        }
    }

    public static void classSpy(String[] args) throws ClassNotFoundException {
        Class<?> c = Class.forName(args[0]);
        out.format("Class:%n  %s%n%n", c.getCanonicalName());

        Package p = c.getPackage();
        out.format("Package:%n  %s%n%n", (p != null ? p.getName() : "-- No Package --"));

        for (int i = 1; i < args.length; i++) {
            switch (ClassMember.valueOf(args[i])) {
                case CONSTRUCTOR:
                    printMembers(c.getConstructors(), "Constructor");
                    break;
                case FIELD:
                    printMembers(c.getFields(), "Fields");
                    break;
                case METHOD:
                    printMembers(c.getMethods(), "Methods");
                    break;
                case CLASS:
                    printClasses(c);
                    break;
                case ALL:
                    printMembers(c.getConstructors(), "Constuctors");
                    printMembers(c.getFields(), "Fields");
                    printMembers(c.getMethods(), "Methods");
                    printClasses(c);
                    break;
                default:
                    assert false;
            }
        }

        // production code should handle these exceptions more gracefully
    }

    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format("  %s%n", ((Field)mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format("  %s%n", ((Constructor)mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format("  %s%n", ((Method)mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format("  -- No %s --%n", s);
        out.format("%n");
    }

    private static void printClasses(Class<?> c) {
        out.format("Classes:%n");
        Class<?>[] clss = c.getClasses();
        for (Class<?> cls : clss)
            out.format("  %s%n", cls.getCanonicalName());
        if (clss.length == 0)
            out.format("  -- No member interfaces, classes, or enums --%n");
        out.format("%n");
    }

}
