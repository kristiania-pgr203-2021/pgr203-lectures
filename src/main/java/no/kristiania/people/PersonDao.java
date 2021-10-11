package no.kristiania.people;

import javax.sql.DataSource;

public class PersonDao {
    private Person person;

    public PersonDao(DataSource dataSource) {
        
    }

    public void save(Person person) {

        this.person = person;
    }

    public Person retrieve(Long id) {
        return person;
    }
}
