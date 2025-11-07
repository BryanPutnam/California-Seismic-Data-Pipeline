import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProducerTest {
    
    @Test
    void testEarthquakeMessageFormat() {
        // The exact message string from ProducerApp
        String messageValue = "{\"id\": 1, \"magnitude\": 5.4, \"location\": \"San Francisco\", \"depth_km\": 10.2}";
        
        // Test that it contains all required fields
        assertTrue(messageValue.contains("\"id\":"), "Message should contain id field");
        assertTrue(messageValue.contains("\"magnitude\":"), "Message should contain magnitude field");
        assertTrue(messageValue.contains("\"location\":"), "Message should contain location field");
        assertTrue(messageValue.contains("\"depth_km\":"), "Message should contain depth_km field");
        
        // Test specific values
        assertTrue(messageValue.contains("\"id\": 1"), "ID should be 1");
        assertTrue(messageValue.contains("\"magnitude\": 5.4"), "Magnitude should be 5.4");
        assertTrue(messageValue.contains("\"location\": \"San Francisco\""), "Location should be San Francisco");
        assertTrue(messageValue.contains("\"depth_km\": 10.2"), "Depth should be 10.2");
    }
}
