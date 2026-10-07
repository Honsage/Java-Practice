package oop;

public class Polymorphism {

    public static void dynamicBinding() {
        new B();
        // expect -1 10, got 0 10, cause super() calls applying to inheritor class members
    }

    public static void staticBinding() {}

    public static void bindingByReference() {}

    public static void bindingByType() {
        A a = new B();
        a.sprint();
    }
}

class A {
    int x = -1;
    A() { print(); } // calls before B constructor
    void print() { System.out.println("A"); }
    static void sprint() {
        System.out.println("A");
    }
}

class B extends A {
    int x = 10;
    B() { super(); print(); }
    @Override
    void print() { System.out.println("B, x = " + x); }
    static void sprint() {
        System.out.println("B");
    }
}
