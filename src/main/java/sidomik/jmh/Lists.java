package sidomik.jmh;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 20, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 40, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(4)
public class Lists {

    private List<String> list;

    @Param({"java.util.ArrayList", "java.util.LinkedList"})
    private String listClassName;

    @Setup(Level.Iteration)
    public void setup() throws Exception {
        list = (List<String>) Class.forName(listClassName).getDeclaredConstructor().newInstance();
    }

    @Benchmark
    public void listInsert() {
        list.add("some test string");
    }

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
