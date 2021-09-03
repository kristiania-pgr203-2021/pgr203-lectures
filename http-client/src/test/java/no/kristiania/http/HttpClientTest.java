package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpClientTest {
    
    @Test
    void shouldGetResponseCode() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/status/200");
        assertEquals(200, client.getStatusCode());
        client = new HttpClient("httpbin.org", 80, "/status/400");
        assertEquals(400, client.getStatusCode());
    }
    
    @Test
    void shouldReadHeaders() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }
    
    @Test
    void shouldReadContentLength() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertTrue(client.getContentLength() > 10);
    }

    @Test
    void shouldReadMessageBody() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertTrue(client.getMessageBody().startsWith("<!DOCTYPE html>\n<html>"),
                "expected <" + client.getMessageBody() + " to be html"
        );
    }
}