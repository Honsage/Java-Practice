package oop;

public class Initializing {

    public static void order() {
        new BPrinter();
    }
}


class APrinter {
    static {
        System.out.println("From Static Init Block of APrinter");
    }

    {
        System.out.println("From Instance Init Block of APrinter");
    }

    APrinter() {
        System.out.println("From Constructor of APrinter");
    }
}

class BPrinter extends APrinter {
    static {
        System.out.println("From Static Init Block of BPrinter");
    }

    {
        System.out.println("From Instance Init Block of BPrinter");
    }

    BPrinter() {
        System.out.println("From Constructor of BPrinter");
    }
}