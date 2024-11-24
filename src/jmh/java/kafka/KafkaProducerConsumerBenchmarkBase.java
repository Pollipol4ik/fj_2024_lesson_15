package kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class KafkaProducerConsumerBenchmarkBase extends BaseBenchmark {
    private List<KafkaProducer<String, String>> producers;
    private List<KafkaConsumer<String, String>> consumers;

    protected final int numberOfProducers;
    protected final int numberOfConsumers;

    public KafkaProducerConsumerBenchmarkBase(int numberOfProducers, int numberOfConsumers) {
        this.numberOfProducers = numberOfProducers;
        this.numberOfConsumers = numberOfConsumers;
    }

    @Setup(Level.Trial)
    public void setup() {
        producers = new ArrayList<>();
        consumers = new ArrayList<>();

        Properties producerProps = createKafkaProducerProperties();
        for (int i = 0; i < numberOfProducers; i++) {
            producers.add(new KafkaProducer<>(producerProps));
        }

        Properties consumerProps = createKafkaConsumerProperties();
        for (int i = 0; i < numberOfConsumers; i++) {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
            consumer.subscribe(List.of("test-topic"));
            consumers.add(consumer);
        }
    }

    private Properties createKafkaProducerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    private Properties createKafkaConsumerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        producers.forEach(KafkaProducer::close);
        consumers.forEach(KafkaConsumer::close);
    }

    @Benchmark
    public void kafkaProducersConsumers(Blackhole blackhole) {
        producers.forEach(producer -> {
            var sendFuture = producer.send(new ProducerRecord<>("test-topic", "key", "value"));
            blackhole.consume(sendFuture);
        });

        consumers.forEach(consumer -> {
            var messages = consumer.poll(Duration.ofMillis(100));
            blackhole.consume(messages);
        });

        blackhole.consume(producers);
        blackhole.consume(consumers);
    }
}
