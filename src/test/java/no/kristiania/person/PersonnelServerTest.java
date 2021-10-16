package no.kristiania.person;

import no.kristiania.http.HttpClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonnelServerTest {

    private final HttpServer server = new HttpServer(0);

    public PersonnelServerTest() throws IOException {
    }

    @Test
    void shouldListRolesFromDatabase() throws IOException, SQLException {
        RoleDao roleDao = new RoleDao(TestData.createDataSource());
        roleDao.deleteAll();
        roleDao.save("Assistant");
        roleDao.save("Student");
        roleDao.save("Teacher");
        
        server.addController("/api/roleOptions", new RoleOptionsController(roleDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/roleOptions");
        assertEquals(
                "<option>Assistant</option><option>Student</option><option>Teacher</option>",
                client.getMessageBody()
        );
    }


}
