package no.kristiania.person;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.http.Person;

import java.io.IOException;
import java.sql.SQLException;

public class ListPeopleController implements HttpController {
    private final PersonDao personDao;

    public ListPeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";
        for (Person person : personDao.listAll()) {
            messageBody += "<div>" + person.getFirstName() + " " + person.getLastName() + "</div>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
