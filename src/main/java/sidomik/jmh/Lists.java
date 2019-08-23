package sidomik.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 50, time = 20, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 100, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class Lists {

    private List<String> list;

    @Param({"java.util.ArrayList", "java.util.LinkedList"})
    private String listClassName;

    @Param({"0", "1000", "1000000"})
    private int initialListSize;

    @Setup(Level.Iteration)
    public void setup() throws Exception {
        list = (List<String>) Class.forName(listClassName).getDeclaredConstructor().newInstance();
        for (int i = 0; i < initialListSize; i++) {
            list.add("some test string");
        }
    }

    @Benchmark
    public void listInsert() {
        list.add("some test string");
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .jvmArgs("-Xms2g", "-Xmx2g", "-XX:+PrintFlagsFinal", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseEpsilonGC"
                )
                .addProfiler(GCProfiler.class)
                .build();
        new Runner(opt).run();
    }
}
