package no.kristiania.people;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao<T> {
    protected final DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("*****");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }

    protected T retrieveById(long id, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return mapFromResultSet(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    protected abstract T mapFromResultSet(ResultSet rs) throws SQLException;
}
