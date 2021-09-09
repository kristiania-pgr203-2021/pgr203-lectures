package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldRespondWithFixedContent() throws IOException {
        HttpServer server = new HttpServer(10080);
        HttpClient client = new HttpClient("localhost", 10080, "/hello");
        assertEquals("Hello World", client.getMessageBody());
    }
}