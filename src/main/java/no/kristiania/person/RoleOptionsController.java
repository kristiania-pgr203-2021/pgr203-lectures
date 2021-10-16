package no.kristiania.person;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class RoleOptionsController implements HttpController {
    private final RoleDao roleDao;

    public RoleOptionsController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String messageContent = "";
        for (String role : roleDao.listAll()) {
            messageContent += "<option>" + role + "</option>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", messageContent);
    }
}
