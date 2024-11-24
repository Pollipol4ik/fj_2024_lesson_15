package rabbit.test;

import rabbit.RabbitMQBenchmarkBase;

public class StressTestBenchmark extends RabbitMQBenchmarkBase {
    public StressTestBenchmark() {
        super(10, 10);
    }
}