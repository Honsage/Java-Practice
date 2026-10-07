package syntax;

import java.util.Scanner;

public class SyntaxPractice {
    public static void primitiveTypes() {
        byte b = 127; // 1 byte
//        byte b = -130; // syntax error (out of scope)
        short s = 20220; // 2 byte
        int i = 4; // 4 bytes
        long l = 8L; // 8 bytes

        boolean bl = true; // 4 bytes as int

        float f = 23.09f; // 4 bytes, IEEE 754
        double d = 9.0002; // 8 bytes, IEEE 754
        double db = 2e-1; // aka 0.2
        System.out.println(db);

        char c = '@'; // 2 bytes, unsigned
        char ch = 65535;
    }

    public static void numberSystems() {
        int bin = 0b101; // aka 5
        int hex = 0x1AF; // aka 431
        System.out.println(hex);
        int dec = 10_000_000;
    }

    public static void strings() {
        String s = "Hello";
        String empty = "";
        String space = " ";
        String hello = "Hello";
        System.out.println(s == hello); // true cause of String Pool

        String a = "A";
        String newa = new String("A");
        System.out.println(a == newa); // false: different refs
        System.out.println(a.equals(newa)); // true

        // String is immutable
        String p1 = "Hello, ";
        p1 += " world"; // creates new string
        System.out.println(p1);
    }

    public static void loops() {
        boolean condition = true;
        while (condition) {
            // do smth if cond
        }

        do {
            // do smth and check to enough
        } while (condition);

        for (int i = 0 /* init block */; i < 10 /* condition before iter */; ++i /* after each iter */) {
            // do smth
        }

        while (condition) {
            if (condition) {
                break; // break from loop
            } else continue; // break current iteration from there
        }

        loop1:
        for (; ;) {
            loop2:
            for (; ;) {
                if (condition) break loop1; // break from outer
                else break loop2; // break from inner
            }
        }

        // for each
        String[] arr = {"kk", "rr", "ll"};
        for (String s : arr) {
            System.out.println(s);
        }
    }

    public static void input() {
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNext()) {
            int val = scanner.nextInt();
        }
    }

    public static void conditions() {
        boolean cond1 = true, cond2 = true;
        if (cond1) {
            // smth
        } else if (cond2) {
            // another smth
        } else {
            // otherwise
        }

        // switch-case
        int a = 2;
        switch (a) {
            case 1:
                // smth
                break;
            case 2:
                // another smth
                break;
            default: {
                // otherwise
            }
        }

        // switch expression (Java 14+)
        int b = switch (a) {
            case 1 -> a;
            case 2 -> a * 2;
            default -> a - 1;
        };

        int day = 3;
        String dayName = switch (day) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("Рабочий день");
                yield "Будний"; // as return from block of switch expr
            }
            case 6, 7 -> {
                System.out.println("Выходной");
                yield "Выходной";
            }
            default -> "Неизвестный день";
        };

        // alternative syntax
        dayName = switch (day) {
            case 1: yield "Понедельник";
            case 2: yield "Вторник";
            case 3: yield "Среда";
            default: yield "Неизвестный день";
        };

        // pattern matching
        Object ob = "k";
        switch (ob) {
            case Integer i: {
                break;
            }
            case String s: {
                break;
            }
            case null: {

            }
            default: {

            }
        }


        // guard conditions "when"
        record Point(int x, int y) {}

        Point p = new Point(5, 10);

        String result = switch (p) {
            case Point(int x, int y) when x > 0 && y > 0 -> "Точка в первой четверти";
            case Point(int x, int y) when x < 0 && y > 0 -> "Точка во второй четверти";
            case Point(int x, int y) -> "Точка на осях";
        };
    }

    public static void arrays() {
        // array is an Java Object
        int[] arr = new int[10];
        int[] numbers = {1, 2, 3};
        System.out.println(arr.length); // property
        System.out.println(arr.clone()); // method

        Object[] obarr = new Object[3];
        obarr[0] = "ss";
        obarr[1] = 9;
        obarr[2] = 'f';
        System.out.println(obarr[2]);
    }

    public static void multidirectionalArrays() {
        int[][] matrix = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        System.out.println(matrix[1][1]);
    }

    public static void stringBuilder() {
        StringBuilder sb = new StringBuilder("Hello");
        // method chaining
        sb.append(", ").append(5).append("world").append("!");
        System.out.println(sb.toString());
    }

    public static void stringFormat() {
        String sir = "Sir";
        System.out.printf("Hello, %d' %s and %f", 10, sir, -.6);
        // left padding with spaces
        System.out.printf("%5d\n", 118); // "  118"
        // right padding
        System.out.printf("%-5d\n", 118); // "118  "
        // precision
        System.out.printf("%.2f", 64.6757); // 2 digits after "." with rounding
        String.format("%f", 2.6);
    }

    public static void varargs(String... ss) {
        System.out.println(ss[0]); // varargs is simple array of args
    }
}

