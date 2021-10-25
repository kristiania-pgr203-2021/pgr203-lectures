package no.kristiania.http;

import no.kristiania.person.Person;
import no.kristiania.person.PersonDao;

import java.sql.SQLException;

public class ListPeopleController implements HttpController {
    private final PersonDao personDao;

    public ListPeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";

        for (Person person : personDao.listAll()) {
            response += "<div>" + person.getLastName() + ", " + person.getFirstName() + "</div>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
