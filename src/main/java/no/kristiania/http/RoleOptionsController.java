package no.kristiania.http;

import no.kristiania.person.RoleDao;

import java.sql.SQLException;

public class RoleOptionsController implements HttpController {
    private final RoleDao roleDao;

    public RoleOptionsController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String responseText = "";

        int value = 1;
        for (String role : roleDao.listAll()) {
            responseText += "<option value=" + (value++) + ">" + role + "</option>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
