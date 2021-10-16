package no.kristiania.person;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.http.Person;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class CreatePersonController implements HttpController {
    private final PersonDao personDao;

    public CreatePersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        
        Person person = new Person();
        person.setFirstName(queryMap.get("firstName"));
        person.setLastName(queryMap.get("lastName"));
        personDao.save(person);

        return new HttpMessage("HTTP/1.1 200 OK");
    }
}
