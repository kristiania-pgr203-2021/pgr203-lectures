package no.kristiania.person;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public class ListPeopleController implements HttpController {
    private final PersonDao personDao;

    public ListPeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        return null;
    }
}
