package com.donishchenko.testgame.engine.stats;

import org.springframework.stereotype.Component;

import javax.management.*;
import java.lang.management.ManagementFactory;

import static com.donishchenko.testgame.config.EngineConstants.*;
import static com.donishchenko.testgame.utils.CommonUtils.*;

@Component
public class UpdateStats {
    /* Info */
    private String tpsExpected = Integer.toString(TPS);
    private int tpsActual;
    private String report = "TPS: 0/0 (0 ms)";

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
        double cpuLoad = getProcessCpuLoad();
        report = "TPS: " + tpsActual + '/' + tpsExpected + " (" + (end - start) / 1_000_000d + " ms) | CPU: " + cpuLoad;
    }

    public double getProcessCpuLoad() {
        try {
            MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
            ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

            if (list.isEmpty())     return Double.NaN;

            Attribute att = (Attribute)list.get(0);
            Double value  = (Double)att.getValue();

            // usually takes a couple of seconds before we get real values
            if (value == -1.0)      return Double.NaN;
            // returns a percentage value with 1 decimal point precision
            return ((int)(value * 1000) / 10.0);
        } catch (MalformedObjectNameException | InstanceNotFoundException | ReflectionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
