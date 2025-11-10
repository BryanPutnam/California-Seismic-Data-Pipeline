package com.sdp.producer; 

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import java.net.URI; 
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProducerApp { 

    public static void main(String[] args) { 

        // Kafka Producer Config
        Properties props = new Properties(); 
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); 
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 

        // Create Kafka Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props); 

        String topic = "earthquake_data"; 

        // Need to call USGS API and assign return value to messageValue

        try { 
            // Example message payload
            String messageValue = "{\"id\": 1, \"magnitude\": 5.4, \"location\": \"San Francisco\", \"depth_km\": 10.2}"; 

            ProducerRecord<String, String> record = new ProducerRecord<String,String>(topic, "quake-1", messageValue); // (topic, key, value)

            // Sychronous send (waits for acknowledgement)
            RecordMetadata metadata = producer.send(record).get(); 

            System.out.printf("Sent record(key=%s value=%s) meta(partition=%d, offset=%d)%n",
                record.key(), record.value(), metadata.partition(), metadata.offset());
        } catch(InterruptedException | ExecutionException e) { 
            e.printStackTrace();
        } finally { 
            producer.flush(); 
            producer.close();
        }
    }
}