package no.kristiania.person;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public class CreatePersonController implements HttpController {
    private final PersonDao personDao;

    public CreatePersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        return new HttpMessage("HTTP/1.1 200 OK");
    }
}
