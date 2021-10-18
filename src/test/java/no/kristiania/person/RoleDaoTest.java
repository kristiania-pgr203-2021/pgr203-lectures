package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleDaoTest {
    
    private RoleDao dao = new RoleDao(TestData.testDataSource());

    @Test
    void shouldListSavedRoles() throws SQLException {
        String role1 = "role-" + UUID.randomUUID();
        String role2 = "role-" + UUID.randomUUID();
        
        dao.save(role1);
        dao.save(role2);
        
        assertThat(dao.listAll())
                .contains(role1, role2);
    }
}
