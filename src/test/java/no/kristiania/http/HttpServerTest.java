package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpServerTest {

    @Test
    void shouldReturn404ForUnknownRequestTarget() throws IOException {
        HttpServer server = new HttpServer(10001);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }
    
    @Test
    void shouldRespondWithRequestTargetIn404() throws IOException {
        HttpServer server = new HttpServer(10002);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals("File not found: /non-existing", client.getMessageBody());
    }

    @Test
    void shouldRespondWith200ForKnownRequestTarget() throws IOException {
        HttpServer server = new HttpServer(10003);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello");
        assertAll(
                () -> assertEquals(200, client.getStatusCode()),
                () -> assertEquals("text/html", client.getHeader("Content-Type")),
                () -> assertEquals("<p>Hello world</p>", client.getMessageBody())        
        );
    }

    @Test
    void shouldEchoQueryParameter() throws IOException {
        HttpServer server = new HttpServer(0);
        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello?yourName=johannes");
        assertEquals("<p>Hello johannes</p>", client.getMessageBody());
    }

    @Test
    void shouldServeFiles() throws IOException {
        HttpServer server = new HttpServer(0);
        server.setRoot(Paths.get("target/test-classes"));
        
        String fileContent = "A file created at " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/example-file.txt"), fileContent.getBytes());
        
        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldUseFileExtensionForContentType() throws IOException {
        HttpServer server = new HttpServer(0);
        server.setRoot(Paths.get("target/test-classes"));

        String fileContent = "<p>Hello</p>";
        Files.write(Paths.get("target/test-classes/example-file.html"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }
}