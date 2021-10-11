package no.kristiania.people;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class OrganizationDaoTest {

    private final OrganizationDao dao = new OrganizationDao(TestDatabase.testDataSource());

    @Test
    void shouldRetrieveSavedOrganization() throws SQLException {
        Organization organization = exampleOrganization();
        dao.save(organization);
        
        assertThat(dao.retrieve(organization.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(organization);
    }

    private Organization exampleOrganization() {
        Organization organization = new Organization();
        organization.setName(TestDatabase.pickOne("Høyskolen Kristiania", "Sopra Steria", "Kystverket"));
        organization.setSector(TestDatabase.pickOne("Education", "Consulting", "Government"));
        return organization;
    }

}
