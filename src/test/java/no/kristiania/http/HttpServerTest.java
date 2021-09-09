package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    private final HttpServer server = new HttpServer(0);

    HttpServerTest() throws IOException {
    }

    @Test
    void shouldRespondWithFixedContent() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello");
        assertEquals("Hello world", client.getMessageBody());
    }

    @Test
    void shouldRespondWith404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/no-content-here");
        assertEquals(404, client.getStatusCode());
    }
}