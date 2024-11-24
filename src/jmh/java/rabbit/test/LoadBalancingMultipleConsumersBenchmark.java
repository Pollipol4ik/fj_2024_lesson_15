package rabbit.test;

import rabbit.RabbitMQBenchmarkBase;

public class LoadBalancingMultipleConsumersBenchmark extends RabbitMQBenchmarkBase {
    public LoadBalancingMultipleConsumersBenchmark() {
        super(3, 3);
    }
}