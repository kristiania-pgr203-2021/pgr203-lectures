package no.kristiania.people;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    private final PersonDao dao = new PersonDao(TestDatabase.testDataSource());

    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        Person person = examplePerson();
        dao.save(person);
        
        assertThat(dao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    @Test
    void shouldNotRetrieveMissingPerson() throws SQLException {
        assertThat(dao.retrieve(-1)).isNull();
    }

    @Test
    void shouldListPeopleByLastName() throws SQLException {
        Person matchingPerson = examplePerson();
        matchingPerson.setLastName("Testerson");
        dao.save(matchingPerson);

        Person otherMatchingPerson = examplePerson();
        otherMatchingPerson.setLastName(matchingPerson.getLastName());
        dao.save(otherMatchingPerson);

        Person nonMatchingPerson = examplePerson();
        dao.save(nonMatchingPerson);
        
        assertThat(dao.listByLastName(matchingPerson.getLastName()))
                .extracting(Person::getId)
                .contains(matchingPerson.getId(), otherMatchingPerson.getId())
                .doesNotContain(nonMatchingPerson.getId());
    }

    private Person examplePerson() {
        Person person = new Person();
        person.setFirstName(TestDatabase.pickOne("Johannes", "Jane", "Josephine", "James", "Jenny"));
        person.setLastName(TestDatabase.pickOne("Persson", "Olsen", "Jones", "Jamesson"));
        return person;
    }

}
