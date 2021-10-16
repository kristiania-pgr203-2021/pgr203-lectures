package no.kristiania.person;

import javax.sql.DataSource;

public class AbstractDao {
    protected final DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
