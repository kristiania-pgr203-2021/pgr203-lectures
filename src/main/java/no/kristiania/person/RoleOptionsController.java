package no.kristiania.person;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

public class RoleOptionsController implements HttpController {
    private final RoleDao roleDao;

    public RoleOptionsController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) {
        return null;
    }
}
