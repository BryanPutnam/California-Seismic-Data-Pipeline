import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProducerTest { 

    @Test
    void assertExpectedURI() {
        String expectedURI = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
        String actualURI = com.sdp.producer.ProducerApp.USGS_EARTHQUAKE_API_URL; 
        assertEquals(expectedURI, actualURI, "ProducerApp URI does not match expected URI");
    }

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
