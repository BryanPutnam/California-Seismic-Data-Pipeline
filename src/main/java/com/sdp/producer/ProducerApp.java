package com.sdp.producer; 

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.io.IOException;
import java.net.URI; 
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ProducerApp { 

    static final String USGS_EARTHQUAKE_API_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    static final String KAFKA_TOPIC = "earthquake_data"; 
    static final String TOPIC_KEY = "quake-1"; 

    public static void main(String[] args) { 
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig()); 

        try { 
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(KAFKA_TOPIC, TOPIC_KEY, getData(USGS_EARTHQUAKE_API_URL)); 
            
            // Synchronous send: waits for Kafka to confirm message delivery and records metadata after
            RecordMetadata metadata = producer.send(record).get(); 

            System.out.printf("Sent record(key=%s value=%s) meta(partition=%d, offset=%d)%n",
                record.key(), record.value(), metadata.partition(), metadata.offset());
            } catch(Exception e) { 
                e.printStackTrace();
            } finally { 
                producer.flush(); 
                producer.close();
        }
    }

    private static String getData(String url) throws IOException, InterruptedException{ 
        HttpClient client = HttpClient.newHttpClient(); 
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(10))
            .build(); 
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body(); 
        return jsonResponse; 
    }

    private static Properties getProducerConfig() { 
        Properties props = new Properties(); 
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); 
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
        return props; 
    }

}