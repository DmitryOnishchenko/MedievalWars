package com.donishchenko.testgame.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {

    private static ExecutorService service = Executors.newFixedThreadPool(1);

    public static void submitTask(Runnable task) {
        service.submit(task);
    }

}
