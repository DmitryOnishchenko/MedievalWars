package com.donishchenko.testgame.engine.stats;

import org.springframework.stereotype.Component;

import static com.donishchenko.testgame.utils.CommonUtils.*;
import static com.donishchenko.testgame.config.EngineConstants.*;

@Component
public class RenderStats {
    /* Info */
    private String fpsExpected = Integer.toString(MAX_FPS);
    private int fpsActual;
    private String report = "FPS: 0/0 | 0 ms";

    private long startTime;
    private long start;
    private long end;

    public void start() {
        start = getNanoTime();
    }

    public void end() {
        end = getNanoTime();
        if (end - startTime >= 1_000_000_000) {
            buildReport();
            fpsActual = 0;
            startTime += 1_000_000_000;
            System.out.println(report);
        }
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void updateFps() {
        fpsActual++;
    }

    public String report() {
        return report;
    }

    private void buildReport() {
        report = "FPS: " + fpsActual + '/' + fpsExpected + " | " + (end - start) / 1_000_000d + " ms";
    }
}
