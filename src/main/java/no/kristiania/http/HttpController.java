package no.kristiania.http;

import java.io.IOException;
import java.sql.SQLException;

public interface HttpController {
    HttpMessage handle(HttpMessage request) throws SQLException, IOException;
}
