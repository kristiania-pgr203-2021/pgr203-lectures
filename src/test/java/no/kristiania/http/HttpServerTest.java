package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldRespondWith404ForUnknownFiles() throws IOException {
        HttpServer server = new HttpServer(10001);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/no-such-file");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldIncludeRequestTargetIn404Message() throws IOException {
        HttpServer server = new HttpServer(10002);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/no-such-file");
        assertEquals("File not found: /no-such-file", client.getMessageBody());
    }

    @Test
    void shouldRespondToHelloAction() throws IOException {
        HttpServer server = new HttpServer(10003);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello");
        assertEquals("<p>Hello world</p>", client.getMessageBody());
    }
}