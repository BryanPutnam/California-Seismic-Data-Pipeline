import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class USGSClientTest {
    @Test 
    void assertUrl() { 
        String actualApiUrl = com.sdp.client.USGSClient.USGS_EARTHQUAKE_API_STRING; 
        String expectedApiUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"; 
        assertEquals(actualApiUrl, expectedApiUrl, "Incorrect API URL STRING"); 
    }
}
