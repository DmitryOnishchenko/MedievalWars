package com.donishchenko.testgame.engine;

import com.donishchenko.testgame.engine.stats.UpdateStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static com.donishchenko.testgame.config.EngineConstants.NANO_PER_TICK;
import static com.donishchenko.testgame.utils.CommonUtils.getNanoTime;

@Component
public class UpdateThread extends Thread {

    /* Engine */
    @Autowired private GameEngine engine;

    /* Update info */
    @Autowired private UpdateStats updateStats;

    public Queue<KeyEvent> keyEventQueue = new ConcurrentLinkedQueue<>();

    public String report() {
        return updateStats.report();
    }

    @Override
    public void run() {
        setName("Update-Thread");

        long timer = getNanoTime();
        long previous = getNanoTime();
        long lag = 0;

        updateStats.setStartTime(timer);

        while (true) {
            long current = getNanoTime();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;

            KeyEvent keyEvent = keyEventQueue.poll();
            engine.processInput(keyEvent);

            while (lag >= NANO_PER_TICK) {
                updateStats.start();
                engine.update();
                lag -= NANO_PER_TICK;

                updateStats.updateTps();
            }
            updateStats.end();

            long sleepFor = current + NANO_PER_TICK - getNanoTime();
            try {
                TimeUnit.NANOSECONDS.sleep(sleepFor);
            } catch (InterruptedException ex) {
                engine.onError("InterruptedException in UpdateThread");
            }
        }
    }
}
