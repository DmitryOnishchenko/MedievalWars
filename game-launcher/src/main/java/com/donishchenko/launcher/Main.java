package com.donishchenko.launcher;

import java.io.IOException;

/**
 * Created by Electdead on 29.05.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().exec("java -Xmx64m -cp target/TestGame-0.0.1.jar com.donishchenko.testgame.Application");
    }
}
