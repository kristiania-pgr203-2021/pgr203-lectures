package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    @Test
    void shouldRetrieveSavedPerson() {
        HelloDatabase dao = new HelloDatabase();

        Person person = randomPerson();
        dao.save(person);
        assertThat(dao.retrieve(person.getId()))
                .isEqualTo(person);
    }

    private Person randomPerson() {
        Person person = new Person();
        person.setFirstName(pickOne("Johannes", "Jane", "Jonas", "Josephine", "Jamal"));
        return person;
    }

    private String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }


}
