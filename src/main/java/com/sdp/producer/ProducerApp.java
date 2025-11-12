package com.sdp.producer; 

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class ProducerApp { 

    public static final String KAFKA_TOPIC = "earthquake_data"; 
    public static final String TOPIC_KEY = "quake-1"; 

    public static void main(String[] args) { 
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig()); 

        try { 
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(KAFKA_TOPIC, TOPIC_KEY, getReponseString());
            RecordMetadata metadata = producer.send(record).get(); // Synchronous send: waits for Kafka to confirm message delivery and records metadata after
            System.out.printf("Sent record(key=%s value=%s) meta(partition=%d, offset=%d)%n",
                record.key(), record.value(), metadata.partition(), metadata.offset());
            } catch(Exception e) { 
                e.printStackTrace();
            } finally { 
                producer.flush(); 
                producer.close();
        }
    }
    private static String getReponseString() { 
        String apiJsonResponseString = com.sdp.client.USGSClient.getData(com.sdp.client.USGSClient.USGS_EARTHQUAKE_API_STRING); 
        return apiJsonResponseString; 
    }

    private static Properties getProducerConfig() { 
        Properties props = new Properties(); 
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); 
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
        return props; 
    }

}