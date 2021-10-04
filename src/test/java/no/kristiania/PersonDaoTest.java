package no.kristiania;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDaoTest {

    private final Random random = new Random();
    private final PersonDao personDao = new PersonDao(PersonDao.createDataSource());

    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        Person person = randomPerson();

        personDao.save(person);
        assertThat(personDao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }
    
    @Test
    void shouldFindPersonByLastName() throws SQLException {
        Person matchingPerson = randomPerson();
        matchingPerson.setLastName("Persson");
        personDao.save(matchingPerson);
        
        Person otherMatchingPerson = randomPerson();
        otherMatchingPerson.setLastName(matchingPerson.getLastName());
        personDao.save(otherMatchingPerson);
        
        Person nonMatchingPerson = randomPerson();
        personDao.save(nonMatchingPerson);
        
        assertThat(personDao.findByLastName(matchingPerson.getLastName()))
                .extracting(Person::getId)
                .contains(matchingPerson.getId(), otherMatchingPerson.getId())
                .doesNotContain(nonMatchingPerson.getId());
    }
    

    private Person randomPerson() {
        Person person = new Person();
        person.setFirstName(pickOne("James", "Jane", "Johannes", "Jeff", "Jane", "Julie"));
        person.setLastName(pickOne("Jameson", "Jones", "Williams", "Johnson"));
        return person;
    }

    private String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}