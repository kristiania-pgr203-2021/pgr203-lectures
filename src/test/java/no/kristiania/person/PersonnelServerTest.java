package no.kristiania.person;

import no.kristiania.http.HttpClient;
import no.kristiania.http.HttpPostClient;
import no.kristiania.http.HttpServer;
import no.kristiania.http.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void shouldCreateNewPerson() throws IOException, SQLException {
        PersonDao personDao = new PersonDao(TestData.createDataSource());
        server.addController("/api/newPerson", new CreatePersonController(personDao));
        
        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/newPerson",
                "lastName=Persson&firstName=Test"
        );
        assertEquals(200, postClient.getStatusCode());
        
        assertThat(personDao.listAll())
                .extracting(Person::getLastName)
                .contains("Persson");
    }

    @Test
    void shouldListPeople() throws SQLException, IOException {
        PersonDao personDao = new PersonDao(TestData.createDataSource());
        
        Person person = new Person();
        person.setFirstName("Noen André");
        person.setLastName("Persson");
        personDao.save(person);
        
        server.addController("/api/people", new ListPeopleController(personDao));
        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/people");
        assertThat(client.getMessageBody()).contains("Noen André Persson");
    }
    
}
