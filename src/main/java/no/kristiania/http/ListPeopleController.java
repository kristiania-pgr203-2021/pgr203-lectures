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
        String messageBody = "";

        for (Person person : personDao.listAll()) {
            messageBody += "<div>" + person.getLastName() + ", " + person.getFirstName() + "</div>";
        }


        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
