package com.donishchenko.testgame.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.donishchenko.testgame.config.EngineConstants.NANO_PER_TICK;
import static com.donishchenko.testgame.utils.CommonUtils.getNanoTime;

@Component
public class InputThread extends Thread {

    /* Engine*/
    @Autowired
    private GameEngine engine;

    @Override
    public void run() {
        setName("Input-Thread");

        while (true) {
            long current = getNanoTime();

            // TODO input thread
//            engine.processInput();

            long sleepFor = current + NANO_PER_TICK / 2 - getNanoTime();
            try {
                TimeUnit.NANOSECONDS.sleep(sleepFor);
            } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
    }
}
