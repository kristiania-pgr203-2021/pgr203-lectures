package no.kristiania.http;

import no.kristiania.person.Person;
import no.kristiania.person.PersonDao;

import java.sql.SQLException;
import java.util.Map;

public class CreatePersonController implements HttpController {
    private final PersonDao personDao;

    public CreatePersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        Person person = new Person();
        person.setFirstName(parameters.get("firstName"));
        person.setLastName(parameters.get("lastName"));
        personDao.save(person);
        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
