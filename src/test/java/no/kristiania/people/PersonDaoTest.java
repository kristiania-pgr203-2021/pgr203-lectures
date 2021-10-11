package no.kristiania.people;

import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {
    
    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        PersonDao dao = new PersonDao(createDataSource());
        Person person = examplePerson();
        dao.save(person);
        
        assertThat(dao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }
    

    private Person examplePerson() {
        Person person = new Person();
        person.setFirstName(pickOne("Johannes", "Jane", "Josephine", "James", "Jenny"));
        person.setLastName(pickOne("Persson", "Olsen", "Jones", "Jamesson"));
        return person;
    }

    private static final Random random = new Random();

    private static String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }

    private DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("*****");
        return dataSource;
    }
}
