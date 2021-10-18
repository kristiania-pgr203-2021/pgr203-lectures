package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RoleDaoTest {
    
    private RoleDao dao = new RoleDao(TestData.testDataSource());

    @Test
    void shouldListSavedRoles() {
        String role1 = "role-" + UUID.randomUUID();
        String role2 = "role-" + UUID.randomUUID();
        
        dao.save(role1);
        dao.save(role2);
        
        assertThat(dao.listAll())
                .contains(role1, role2);
    }
}
