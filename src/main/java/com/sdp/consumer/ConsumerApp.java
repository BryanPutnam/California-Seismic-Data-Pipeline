package com.sdp.consumer; 

import org.apache.kafka.clients.consumer.ConsumerRecord; 
import org.apache.kafka.clients.consumer.ConsumerRecords; 
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.security.KeyFactorySpi;
import java.time.Duration;
import java.util.Collections; 
import java.util.Properties; 

public class ConsumerApp { 
    
    public static void main(String[] args) { 
        // Kafka Consumer Config
        Properties props = new Properties(); 
        props.put("bootstrap.servers", "localhost:9092"); // kafka broker 
        props.put("group.id", "sdp-consumer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // Convert Kafka Binary to Java String
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // Convert Kafka Binary to Java String

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props); 
        consumer.subscribe(Collections.singletonList("earthquake_data")); // This is the Kafka Topic name. This is not the same as earthquake_data.avsc in /schemas 
    
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