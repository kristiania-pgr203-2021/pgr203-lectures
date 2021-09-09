package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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

    @Test
    void shouldIncludeRequestTargetIn404Response() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/no-content-here");
        assertEquals("File not found /no-content-here", client.getMessageBody());
    }

    @Test
    void shouldReadFileFromDisk() throws IOException {
        String fileContent = "A file created at " + LocalDateTime.now();
        Files.write(Paths.get("target/test-classes/example-file.txt"), fileContent.getBytes());
        server.setRoot(Paths.get("target/test-classes"));
        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldRespondWithContentType() throws IOException {
        String fileContent = "<h2>Welcome</h2>";
        Files.write(Paths.get("target/test-classes/example-file.html"), fileContent.getBytes());
        server.setRoot(Paths.get("target/test-classes"));
        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }
}