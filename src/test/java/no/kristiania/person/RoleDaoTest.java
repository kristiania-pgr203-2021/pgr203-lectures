package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleDaoTest {

    private final RoleDao dao = new RoleDao(TestData.createDataSource());
    
    @Test
    void shouldListSavedRoles() throws SQLException {
        String roleOne = "role-" + UUID.randomUUID();
        dao.save(roleOne);
        String roleTwo = "role-" + UUID.randomUUID();
        dao.save(roleTwo);
        
        assertThat(dao.listAll())
                .contains(roleOne, roleTwo);
    }
}
