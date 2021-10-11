package no.kristiania.people;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonDaoTest {

    private final PersonDao dao = new PersonDao(testDataSource());

    private DataSource testDataSource() {
        JdbcDataSource database = new JdbcDataSource();
        database.setUrl("jdbc:h2:mem:persondb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(database).load().migrate();
        return database;
    }

    @Test
    void shouldRetrieveSavedPerson() throws SQLException {
        Person person = examplePerson();
        dao.save(person);
        
        assertThat(dao.retrieve(person.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(person);
    }

    @Test
    void shouldListPeopleByLastName() throws SQLException {
        Person matchingPerson = examplePerson();
        matchingPerson.setLastName("Testerson");
        dao.save(matchingPerson);

        Person otherMatchingPerson = examplePerson();
        otherMatchingPerson.setLastName(matchingPerson.getLastName());
        dao.save(otherMatchingPerson);

        Person nonMatchingPerson = examplePerson();
        dao.save(nonMatchingPerson);
        
        assertThat(dao.listByLastName(matchingPerson.getLastName()))
                .extracting(Person::getId)
                .contains(matchingPerson.getId(), otherMatchingPerson.getId())
                .doesNotContain(nonMatchingPerson.getId());
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
}
