package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldRespondWith404ForUnknownFiles() throws IOException {
        HttpServer server = new HttpServer(10001);
        HttpClient client = new HttpClient("localhost", 10001, "/no-such-file");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldIncludeRequestTargetIn404Message() throws IOException {
        HttpServer server = new HttpServer(10002);
        HttpClient client = new HttpClient("localhost", 10002, "/no-such-file");
        assertEquals("File not found: /no-such-file", client.getMessageBody());
    }
}