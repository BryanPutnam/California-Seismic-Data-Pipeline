import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProducerTest { 

    @Test 
    void assertExpectedKafkaTopic() { 
        String expectedTopic = "earthquake_data"; 
        String actualTopic = com.sdp.producer.ProducerApp.KAFKA_TOPIC; 
        assertEquals(expectedTopic, actualTopic, "Kafka Topic does not match expected topic name"); 
    }

    @Test 
    void assertExpectedTopicKey() { 
        String expectedKey = "quake-1"; 
        String actualKey = com.sdp.producer.ProducerApp.TOPIC_KEY; 
        assertEquals(expectedKey, actualKey, "Topic Key does not match expected topic key"); 
    }
}
