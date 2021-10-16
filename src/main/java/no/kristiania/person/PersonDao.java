package no.kristiania.person;

import no.kristiania.http.Person;

import javax.sql.DataSource;

public class PersonDao extends AbstractDao {
    public PersonDao(DataSource dataSource) {
        super(dataSource);
    }

    public void save(Person person) {
        
    }

    public Person retrieve(long id) {
        return null;
    }
}
