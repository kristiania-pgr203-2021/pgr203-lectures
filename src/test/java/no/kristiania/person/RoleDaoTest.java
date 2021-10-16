package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleDaoTest {

    private final RoleDao dao = new RoleDao(TestData.createDataSource());
    
    @Test
    void shouldListSavedRoles() {
        String roleOne = "role-" + UUID.randomUUID();
        dao.save(roleOne);
        String roleTwo = "role-" + UUID.randomUUID();
        dao.save(roleTwo);
        
        assertThat(dao.list())
                .contains(roleOne, roleTwo);
    }
}
