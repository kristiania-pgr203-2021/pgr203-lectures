package no.kristiania;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDaoTest {

    private final Random random = new Random();

    @Test
    void shouldRetrieveSavedPerson() {
        Person person = randomPerson();
        PersonDao personDao = new PersonDao(null);
        
        personDao.save(person);
        assertThat(personDao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    private Person randomPerson() {
        Person person = new Person();
        person.setFirstName(pickOne("James", "Jane", "Johannes", "Jeff", "Jane", "Julie"));
        return person;
    }

    private String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}