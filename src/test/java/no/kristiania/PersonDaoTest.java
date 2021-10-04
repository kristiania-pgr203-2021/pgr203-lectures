package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonDaoTest {

    @Test
    void shouldRetrieveSavedPerson() {
        Person person = randomPerson();
        PersonDao personDao = new PersonDao(null);
        
        personDao.save(person);
        assertThat(personDao.retrieve(person.getId()))
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    private Person randomPerson() {
        return new Person();
    }
}