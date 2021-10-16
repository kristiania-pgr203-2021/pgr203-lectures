package no.kristiania.person;

import javax.sql.DataSource;
import java.util.List;

public class RoleDao extends AbstractDao {
    public RoleDao(DataSource dataSource) {
        super(dataSource);
    }

    public void save(String role) {

    }

    public List<String> list() {
        return null;
    }
}
