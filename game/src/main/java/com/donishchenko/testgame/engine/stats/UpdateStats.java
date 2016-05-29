package com.donishchenko.testgame.engine.stats;

import org.springframework.stereotype.Component;

import static com.donishchenko.testgame.config.EngineConstants.*;
import static com.donishchenko.testgame.utils.CommonUtils.*;

@Component
public class UpdateStats {
    /* Info */
    private String tpsExpected = Integer.toString(TPS);
    private int tpsActual;
    private String report = "TPS: 0/0 | 0 ms";

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
            tpsActual = 0;
            startTime += 1_000_000_000;
            System.out.println(report);
        }
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void updateTps() {
        tpsActual++;
    }

    public String report() {
        return report;
    }

    private void buildReport() {
        report = "TPS: " + tpsActual + '/' + tpsExpected + " | " + (end - start) / 1_000_000d + " ms";
    }
}
