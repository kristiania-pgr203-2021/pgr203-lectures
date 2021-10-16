package no.kristiania.person;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestData {
    public static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:testDatabase");
        return dataSource;
    }
}
