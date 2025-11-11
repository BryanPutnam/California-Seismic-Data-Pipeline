import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.URI; 

class ProducerTest { 

    @Test
    void assertURIsValid() throws Exception {
        URI expectedURI = URI.create("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
        URI actualURI = com.sdp.producer.ProducerApp.getURI();
        assertEquals(expectedURI, actualURI, "ProducerApp URI does not match expected URI");
    }
}
