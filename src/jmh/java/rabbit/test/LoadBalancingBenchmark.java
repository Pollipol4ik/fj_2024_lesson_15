package rabbit.test;

import rabbit.RabbitMQBenchmarkBase;

public class LoadBalancingBenchmark extends RabbitMQBenchmarkBase {
    public LoadBalancingBenchmark() {
        super(3, 1);
    }
}
