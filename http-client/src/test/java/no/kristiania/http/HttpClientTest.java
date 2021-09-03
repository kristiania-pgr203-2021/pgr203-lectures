package no.kristiania.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpClientTest {
    
    @Test
    void shouldGetResponseCode() {
        HttpClient client = new HttpClient("httpbin.org", 80, "/status/200");
        assertEquals(200, client.getStatusCode());
        client = new HttpClient("httpbin.org", 80, "/status/400");
        assertEquals(400, client.getStatusCode());
    }

}