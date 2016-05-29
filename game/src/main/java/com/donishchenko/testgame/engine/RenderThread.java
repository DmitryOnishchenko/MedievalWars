package com.donishchenko.testgame.engine;

import com.donishchenko.testgame.engine.stats.RenderStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.donishchenko.testgame.config.EngineConstants.*;
import static com.donishchenko.testgame.utils.CommonUtils.*;

@Component
public class RenderThread extends Thread {

    /* Engine */
    @Autowired private GameEngine engine;

    /* Render info */
    @Autowired private RenderStats renderStats;

    public String report() {
        return renderStats.report();
    }

    @Override
    public void run() {
        long timer = getNanoTime();
        long previous = getNanoTime();
        long lag = 0;

        renderStats.setStartTime(timer);

        while (true) {
            long current = getNanoTime();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;

            renderStats.start();

//            double deltaTime = 1 - lag / EngineV2.MS_PER_UPDATE;
            engine.render();
            renderStats.updateFps();
            renderStats.end();

            if (USE_FPS_LIMIT) {
                long sleepFor = current + NANO_PER_FRAME - getNanoTime();
                try {
                    TimeUnit.NANOSECONDS.sleep(sleepFor);
                } catch (InterruptedException ex) {
                    engine.onError("InterruptedException in RenderThread");
                }
            }
        }
    }
}
