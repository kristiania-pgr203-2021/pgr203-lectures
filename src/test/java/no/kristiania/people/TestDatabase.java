package no.kristiania.people;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.util.Random;

public class TestDatabase {
    private static final Random random = new Random();

    public static DataSource testDataSource() {
        JdbcDataSource database = new JdbcDataSource();
        database.setUrl("jdbc:h2:mem:persondb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(database).load().migrate();
        return database;
    }

    public static String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
