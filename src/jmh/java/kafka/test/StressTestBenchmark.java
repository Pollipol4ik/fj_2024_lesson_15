package kafka.test;

import kafka.KafkaProducerConsumerBenchmarkBase;

public class StressTestBenchmark extends KafkaProducerConsumerBenchmarkBase {
    public StressTestBenchmark() {
        super(10, 10);
    }
}