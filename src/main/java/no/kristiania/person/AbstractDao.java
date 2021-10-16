package no.kristiania.person;

import javax.sql.DataSource;

public class AbstractDao {
    private final DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
