package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void shouldRespondWith404ForUnknownFiles() throws IOException {
        HttpServer server = new HttpServer(0);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/no-such-file");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldIncludeRequestTargetIn404Message() throws IOException {
        HttpServer server = new HttpServer(0);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/no-such-file");
        assertEquals("File not found: /no-such-file", client.getMessageBody());
    }

    @Test
    void shouldRespondToHelloAction() throws IOException {
        HttpServer server = new HttpServer(0);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello");
        assertEquals("<p>Hello world</p>", client.getMessageBody());
    }

    @Test
    void shouldParseQueryParameters() throws IOException {
        HttpServer server = new HttpServer(0);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello?username=Johannes");
        assertEquals("<p>Hello Johannes</p>", client.getMessageBody());
    }

    @Test
    void shouldRespondWithFileOnDisk() throws IOException {
        Path contentRoot = Paths.get("target/test-classes");
        String fileContent = "Content created at " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        Files.writeString(contentRoot.resolve("data.txt"), fileContent);

        HttpServer server = new HttpServer(0);
        server.setContentRoot(contentRoot);
        
        HttpClient client = new HttpClient("localhost", server.getPort(), "/data.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldRespondWithContentTypeForFileExtension() throws IOException {
        Path contentRoot = Paths.get("target/test-classes");
        Files.writeString(contentRoot.resolve("index.html"), "<p>Hello</p>");

        HttpServer server = new HttpServer(0);
        server.setContentRoot(contentRoot);

        HttpClient client = new HttpClient("localhost", server.getPort(), "/index.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }
}