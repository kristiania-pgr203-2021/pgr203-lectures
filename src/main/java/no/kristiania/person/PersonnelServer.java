package no.kristiania.person;

import no.kristiania.http.HttpServer;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

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

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url", "jdbc:postgresql://localhost:5432/person_db"));
        dataSource.setUser(properties.getProperty("dataSource.user", "person_dbuser"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
