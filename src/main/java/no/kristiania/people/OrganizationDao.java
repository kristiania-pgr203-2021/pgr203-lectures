package no.kristiania.people;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrganizationDao extends AbstractDao<Organization> {
    public OrganizationDao(DataSource dataSource) {
        super(dataSource);
    }

    public void save(Organization organization) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT into organizations (name, sector) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, organization.getName());
                statement.setString(2, organization.getSector());
                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    organization.setId(rs.getLong("id"));
                }
            }
        }
        
    }
    
    public Organization retrieve(long id) throws SQLException {
        return retrieveById(id, "select * from organizations where id = ?");
    }

    @Override
    protected Organization mapFromResultSet(ResultSet rs) throws SQLException {
        Organization organization = new Organization();
        organization.setId(rs.getLong("id"));
        organization.setName(rs.getString("name"));
        organization.setSector(rs.getString("sector"));
        return organization;
    }
}
