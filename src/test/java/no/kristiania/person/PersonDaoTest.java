package no.kristiania.person;

import org.junit.jupiter.api.Test;

public class PersonDaoTest {
    
    private PersonDao dao = new PersonDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedPerson() {
        Person person = examplePerson();
        dao.save(person);
        assertThat(dao.retrieve(person.getId()))
                // TODO
        
        ;
    }
}
