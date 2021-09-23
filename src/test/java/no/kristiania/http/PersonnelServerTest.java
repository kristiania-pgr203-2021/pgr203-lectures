package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonnelServerTest {

    private final HttpServer server = new HttpServer(0);

    public PersonnelServerTest() throws IOException {
    }

    @Test
    void shouldRetrieveRoles() throws IOException {
        server.setRoles(List.of("Boss", "Assistant"));
        assertEquals(
                "<option value='1'>Boss</option><option value='2'>Assistant</option>",
                new HttpClient("localhost", server.getPort(), "/api/roleOptions").getMessageBody()
        );
    }
}
