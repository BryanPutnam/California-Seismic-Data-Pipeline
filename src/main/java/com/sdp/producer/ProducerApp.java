package com.sdp.producer; 

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

import java.net.URI; 
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProducerApp { 

    // URI for USGS Earthquake Data (All Earthquakes in the Past Hour)
    static final String USGS_EARTHQUAKE_API_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

    public static void main(String[] args) { 

        // Kafka Producer Config
        Properties props = new Properties(); 
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); 
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 

        // Create Kafka Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props); 

        try { 
            HttpClient client = HttpClient.newHttpClient(); 
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USGS_EARTHQUAKE_API_URL))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("earthquake_data", "quake-1", jsonResponse); // (topic, key, value)

            //Sychronous send (waits for acknowledgement)
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

    // For JUnit Test
    public static URI getURI() { 
        return URI.create(USGS_EARTHQUAKE_API_URL);
    }
}