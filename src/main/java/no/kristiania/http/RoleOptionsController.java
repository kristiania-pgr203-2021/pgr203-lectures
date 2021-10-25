package no.kristiania.http;

import no.kristiania.person.RoleDao;

public class RoleOptionsController implements HttpController {
    public RoleOptionsController(RoleDao roleDao) {
    }

    @Override
    public HttpMessage handle(HttpMessage request) {
        return null;
    }
}
