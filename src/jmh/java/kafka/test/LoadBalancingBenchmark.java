package kafka.test;


import kafka.KafkaProducerConsumerBenchmarkBase;

public class LoadBalancingBenchmark extends KafkaProducerConsumerBenchmarkBase {
    public LoadBalancingBenchmark() {
        super(3, 1);
    }
}
