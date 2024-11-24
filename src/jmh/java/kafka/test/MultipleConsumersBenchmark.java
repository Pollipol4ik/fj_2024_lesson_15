package kafka.test;

import kafka.KafkaProducerConsumerBenchmarkBase;

public class MultipleConsumersBenchmark extends KafkaProducerConsumerBenchmarkBase {
    public MultipleConsumersBenchmark() {
        super(1, 3);
    }
}