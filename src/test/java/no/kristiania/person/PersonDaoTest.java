package no.kristiania.person;

import no.kristiania.http.Person;
import org.junit.jupiter.api.Test;

public class PersonDaoTest {
    private PersonDao dao = new PersonDao(TestData.createDataSource());
    
    @Test
    void shouldRetrieveSavedPerson() {
        Person person = examplePerson();
        dao.save(person);
        assertThat(dao.retrieve(person.getId()))
            // TODO: Easier to write this when PersonDao.retrieve is in place
        ;
    }

    private Person examplePerson() {
        Person person = new Person();
        person.setFirstName(TestData.pickOne("Johannes", "Janine", "James", "Jane", "Jill", "Jacob"));
        person.setLastName(TestData.pickOne("Smith", "Williams", "Jones"));
        return person;
    }

}
