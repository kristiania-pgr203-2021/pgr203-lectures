package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpClientTest {
    @Test
    void shouldReturnStatusCode() throws IOException {
        assertEquals(200, new HttpClient("httpbin.org", 80, "/html").getStatusCode());
        assertEquals(404, new HttpClient("httpbin.org", 80, "/no-such-file").getStatusCode());
    }
    
    @Test
    void shouldReadHeaderLine() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void shouldReadContentLength() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(3741, client.getContentLength());
    }

    @Test
    void shouldReadMessageBody() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertTrue(client.getMessageBody().startsWith("<html"), "Should be HTML: " + client.getMessageBody());
    }
}
