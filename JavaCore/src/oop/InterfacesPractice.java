package oop;

public class InterfacesPractice {

    public static void practice() {
        Somethable smth = new SomeClass();
        smth.doSmth();

        System.out.println(smth instanceof SomeClass);
    }

}

interface Somethable {
    int CONST = 5; // only public static final fields are allowed

    void doSmth(); // abstract method that implementor has to override

    void doAnotherSmth();

    default void mayNotBeOverrided() { // method with default implementation that may be not overrided
        System.out.println("There is basic logic");
        auxiliaryMethod();
    }

    private void auxiliaryMethod() { // private methods to use in defaults
        System.out.println("Privacy");
    }
}

interface FunctionalInterface { // = the interface with exactly one abstract method
    int foo();
}

class SomeClass implements Somethable {

    @Override
    public void doSmth() {
        System.out.println("I must override it...");
    }

    @Override
    public void doAnotherSmth() {}
}