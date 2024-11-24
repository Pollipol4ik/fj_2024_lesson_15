package kafka.test;

import kafka.KafkaProducerConsumerBenchmarkBase;

public class SimpleConfigurationBenchmark extends KafkaProducerConsumerBenchmarkBase {
    public SimpleConfigurationBenchmark() {
        super(1, 1);
    }
}
