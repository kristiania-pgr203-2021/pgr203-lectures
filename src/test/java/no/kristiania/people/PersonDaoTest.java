package no.kristiania.people;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static no.kristiania.people.PersonDao.createDataSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    private final PersonDao dao = new PersonDao(createDataSource());

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
        person.setFirstName(pickOne("Johannes", "Jane", "Josephine", "James", "Jenny"));
        person.setLastName(pickOne("Persson", "Olsen", "Jones", "Jamesson"));
        return person;
    }

    private static final Random random = new Random();

    private static String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
