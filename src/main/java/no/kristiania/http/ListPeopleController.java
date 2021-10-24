package no.kristiania.http;

import no.kristiania.person.PersonDao;

import java.sql.SQLException;

public class ListPeopleController implements HttpController {
    public ListPeopleController(PersonDao personDao) {
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        return null;
    }
}
