package no.kristiania.person;

import no.kristiania.http.HttpServer;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;

public class PersonnelServer {
    private static final Logger logger = LoggerFactory.getLogger(PersonnelServer.class);
    
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(8080);
        
        DataSource dataSource = createDataSource();
        server.addController("/api/roleOptions", new RoleOptionsController(new RoleDao(dataSource)));
        server.addController("/api/newPerson", new CreatePersonController(new PersonDao(dataSource)));
        server.addController("/api/people", new ListPeopleController(new PersonDao(dataSource)));
        
        logger.info("Started server on http://localhost:{}/index.html", server.getPort());
    }

    private static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("*****");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
