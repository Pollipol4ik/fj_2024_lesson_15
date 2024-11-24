package kafka.test;


import kafka.KafkaProducerConsumerBenchmarkBase;

public class LoadBalancingMultipleConsumersBenchmark extends KafkaProducerConsumerBenchmarkBase {
    public LoadBalancingMultipleConsumersBenchmark() {
        super(3, 3);
    }
}