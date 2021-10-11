package no.kristiania.person;

public class PersonDaoTest {
    
    @Test
    void shouldRetrieveSavedPersonFromDatabase() {
        PersonDao dao = new PersonDao(createDataSource());
        
        Person person = examplePerson();
        dao.save(person);
        
        assertThat(dao.retrieve(person.getId()))
            // TODO
        ;
    }
    
}
