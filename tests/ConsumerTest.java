import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsumerTest { 

    @Test 
    void assertSameTopic() { 
        String prodTopic = com.sdp.producer.ProducerApp.KAFKA_TOPIC; 
        String consTopic = com.sdp.consumer.ConsumerApp.KAFKA_TOPIC; 
        assertEquals(prodTopic, consTopic, "Producer and Consumer Topics Do Not Match");
    }
}
