package no.kristiania.person;

import no.kristiania.http.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDao extends AbstractDao {
    public PersonDao(DataSource dataSource) {
        super(dataSource);
    }

    public void save(Person person) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into people (first_name, last_name) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                
                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    person.setId(rs.getLong("id"));
                }
            }
        }
    }

    public Person retrieve(long id) {
        return null;
    }
}
