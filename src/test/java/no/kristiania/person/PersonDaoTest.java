package no.kristiania.person;

import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    private final PersonDao dao = new PersonDao(createDataSource());

    @Test
    void shouldRetrieveSavedPersonFromDatabase() throws SQLException {
        Person person = examplePerson();
        dao.save(person);
        assertThat(dao.retrieve(person.getId()))
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    @Test
    void shouldListPeopleByLastName() throws SQLException {
        Person matchingPerson = examplePerson();
        matchingPerson.setLastName("Testperson");
        dao.save(matchingPerson);
        Person anotherMatchingPerson = examplePerson();
        anotherMatchingPerson.setLastName(matchingPerson.getLastName());
        dao.save(anotherMatchingPerson);

        Person nonMatchingPerson = examplePerson();
        dao.save(nonMatchingPerson);

        
        assertThat(dao.listByLastName(matchingPerson.getLastName()))
                .extracting(Person::getId)
                .contains(matchingPerson.getId(), anotherMatchingPerson.getId())
                .doesNotContain(nonMatchingPerson.getId());
    }

    private Person examplePerson() {
        Person person = new Person();
        person.setFirstName(pickOne("Johannes", "Juliett", "Jane", "James", "John"));
        person.setLastName(pickOne("Persson", "Olsen", "Johnson", "Walker"));
        return person;
    }
    
    public static String pickOne(String... alternatives) {
        return alternatives[new Random().nextInt(alternatives.length)];
    }
    

    private DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("*****");
        return dataSource;
    }

}
