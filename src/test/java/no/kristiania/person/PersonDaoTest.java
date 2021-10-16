package no.kristiania.person;

import no.kristiania.http.Person;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {
    private PersonDao dao = new PersonDao(TestData.createDataSource());
    
    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        Person person = examplePerson();
        dao.save(person);
        assertThat(dao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    private Person examplePerson() {
        Person person = new Person();
        person.setFirstName(TestData.pickOne("Johannes", "Janine", "James", "Jane", "Jill", "Jacob"));
        person.setLastName(TestData.pickOne("Smith", "Williams", "Jones"));
        return person;
    }

}
