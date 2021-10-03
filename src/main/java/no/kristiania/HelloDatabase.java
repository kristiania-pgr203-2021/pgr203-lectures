package no.kristiania;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.SQLException;

public class HelloDatabase {
    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("åneidu!");
        
        dataSource.getConnection();
    }
}
