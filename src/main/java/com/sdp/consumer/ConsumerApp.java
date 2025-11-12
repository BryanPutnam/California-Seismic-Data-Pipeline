package com.sdp.consumer; 

import org.apache.kafka.clients.consumer.ConsumerRecord; 
import org.apache.kafka.clients.consumer.ConsumerRecords; 
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections; 
import java.util.Properties; 

public class ConsumerApp { 
    
    public static void main(String[] args) { 
        KafkaConsumer<String, String> consumer = createConsumer(); 
        subscribeToTopic(consumer, com.sdp.producer.ProducerApp.KAFKA_TOPIC);
        consumerMessages(consumer);

    }

    public static Properties getConsumerConfig() { 
        Properties props = new Properties(); 
        props.put("bootstrap.servers", "localhost:9092"); // kafka broker 
        props.put("group.id", "sdp-consumer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // Convert Kafka Binary to Java String
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // Convert Kafka Binary to Java String
        return props; 
    }

    private static KafkaConsumer<String, String> createConsumer() { 
        return new KafkaConsumer<>(getConsumerConfig()); 
    }

    private static void subscribeToTopic(KafkaConsumer<String, String> consumer, String topic) { 
        consumer.subscribe(Collections.singletonList(topic)); 
        System.out.println("Subscribed To Topic: " + topic); 
    }

    private static void consumerMessages(KafkaConsumer<String, String> consumer) { 
        try {
            while(true) { 
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000)); 
                for(ConsumerRecord<String, String> record : records) { 
                    System.out.println(record.value()); 
                }
            }
        } finally { 
            consumer.close(); 
        }
    }
} 