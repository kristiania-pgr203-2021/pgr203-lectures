package no.kristiania.people;

public class PersonDaoTest {
    
    @Test
    void shouldRetrieveSavedPerson() {
        PersonDao dao = new PersonDao(createDataSource());
        Person person = examplePerson();
        dao.save(person);
        
        assertThat(dao.retrieve(person.getId()))
            // TODO
        ;
    }
}
