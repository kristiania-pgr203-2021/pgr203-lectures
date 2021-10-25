package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {
    
    private PersonDao dao = new PersonDao(TestData.testDataSource());

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
    void shouldListAllPeople() throws SQLException {
        Person person = examplePerson();
        dao.save(person);
        Person anotherPerson = examplePerson();
        dao.save(anotherPerson);
        
        assertThat(dao.listAll())
                .extracting(Person::getId)
                .contains(person.getId(), anotherPerson.getId());
    }

    public static Person examplePerson() {
        Person person = new Person();
        person.setFirstName(TestData.pickOne("Johannes", "Jane", "Jill", "Jacob", "James"));
        person.setLastName(TestData.pickOne("Williams", "Jones", "Jameson"));
        return person;
    }
}
