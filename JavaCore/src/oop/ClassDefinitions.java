package oop;

// public class must be in file with same name
public class ClassDefinitions {
    private final int value = 0;

    private class InnerClass {
        InnerClass() {
            // has access to private members of aggregator
            System.out.println(value);
        }
    }
}

// package-private class
class AuxiliaryClass {
    static int classField;

    int field;

    void print() {
        System.out.println(field);
    }

    static int twice(int val) {
        return val * 2;
    }
}

