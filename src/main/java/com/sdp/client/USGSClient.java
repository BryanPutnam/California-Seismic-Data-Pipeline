package com.sdp.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class USGSClient {
        
    public static final String USGS_EARTHQUAKE_API_STRING = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"; 
    
    // MAIN FOR TESTING ONLY, PRODUCER WILL CALL getData()
    public static void main(String[] args) { 
        String jsonResponse = getData(USGS_EARTHQUAKE_API_STRING); 
        System.out.println(jsonResponse); 
    }
    // Prevents instantiation (Utility Class)
    private USGSClient() {}

    public static String getData(String url) {
        try { 
            HttpClient client = HttpClient.newHttpClient(); 
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .build(); 
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body(); 
            return jsonResponse; 
        } catch (IOException | InterruptedException e) { 
            throw new RuntimeException("Failed to fetch data from USGS API"); 
        }
    }
}