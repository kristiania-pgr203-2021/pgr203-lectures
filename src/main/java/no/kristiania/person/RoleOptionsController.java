package no.kristiania.person;

import no.kristiania.http.HttpController;

public class RoleOptionsController implements HttpController {
    private final RoleDao roleDao;

    public RoleOptionsController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
