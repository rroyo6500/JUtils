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
public final class Benchmark {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Benchmark() {}

    /**
     * Map that stores active benchmarks, associating each benchmark name with its BenchData.
     */
    private static final Map<String, BenchData> activeBenchmarks = new HashMap<>();

    /**
     * Starts a benchmark with a specific name.
     * @param name The name of the benchmark to start.
     */
    public static void start(String name) {
        Validator.notBlank(name, "Benchmark name cannot be blank");
        LoggerAux.debug("[BENCHMARK: " + TStyle.bold(TStyle.underline(name)) + "] ●");
        activeBenchmarks.put(name, new BenchData());
    }

    /**
     * Stops the benchmark and prints the results to the console.
     * @param name The name of the benchmark to stop.
     * @return A formatted string with the benchmark results.
     */
    public static String stop(String name) {
        BenchData data = activeBenchmarks.remove(name);
        Validator.notNull(data, "No active benchmark found with name: " + name);

        long endTime = System.currentTimeMillis();

        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        long duration = endTime - data.startTime;
        double memUsedMB = (memBean.getHeapMemoryUsage().getUsed() - data.startMem) / (1024.0 * 1024.0);
        long cpuTimeNs = threadBean.getCurrentThreadCpuTime() - data.startCpuTime;

        String benchStr = String.format("[BENCHMARK: %s] ▶ \n%s",
                TStyle.bold(TStyle.underline(name)),
                TStyle.white(String.format("""
                                \t> Time:  \t%s ms
                                \t> CPU:   \t%s ms (Thread time)
                                \t> Memory:\t%s MB (Heap Variation)
                                """,
                        TStyle.bold(TStyle.blue(duration)),
                        TStyle.bold(TStyle.green(String.format("%.2f", cpuTimeNs / 1_000_000.0))),
                        TStyle.bold(TStyle.magenta(String.format("%+.2f", memUsedMB)))
                ))
        );
        LoggerAux.debug(benchStr);
        return benchStr;
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