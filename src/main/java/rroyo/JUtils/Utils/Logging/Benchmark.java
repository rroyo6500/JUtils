package rroyo.JUtils.Utils.Logging;

import rroyo.JUtils.Utils.Console.TStyle;
import rroyo.JUtils.Utils.Core.Validator;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility to measure execution time and resource consumption (CPU/Memory).
 * Designed for simple profiling in development environments.
 */
public class Benchmark {

    private static final Map<String, BenchData> activeBenchmarks = new HashMap<>();

    /**
     * Starts a benchmark with a specific name.
     */
    public static void start(String name) {
        Validator.notBlank(name, "Benchmark name cannot be blank");
        LoggerAux.debug("Benchmark " + name + " started");
        activeBenchmarks.put(name, new BenchData());
    }

    /**
     * Stops the benchmark and prints the results to the console.
     */
    public static void stop(String name) {
        BenchData data = activeBenchmarks.remove(name);
        Validator.notNull(data, "No active benchmark found with name: " + name);

        long endTime = System.currentTimeMillis();

        // Captura de métricas finales
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        long duration = endTime - data.startTime;
        double memUsedMB = (memBean.getHeapMemoryUsage().getUsed() - data.startMem) / (1024.0 * 1024.0);
        long cpuTimeNs = threadBean.getCurrentThreadCpuTime() - data.startCpuTime;

        LoggerAux.debug(
                String.format("""
                        [BENCHMARK: %s]
                        \t> Time:  \t%d ms
                        \t> CPU:   \t%.2f ms (Thread time)
                        \t> Memory:\t%+.2f MB (Heap Variation)
                        """,
                        name,
                        duration,
                        cpuTimeNs / 1_000_000.0,
                        memUsedMB
                )
        );

    }

    /**
     * Inner class to store initial state.
     */
    private static class BenchData {
        long startTime = System.currentTimeMillis();
        long startMem = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        long startCpuTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }
}