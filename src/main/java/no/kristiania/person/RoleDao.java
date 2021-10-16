package no.kristiania.person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao extends AbstractDao {
    public RoleDao(DataSource dataSource) {
        super(dataSource);
    }

    public void save(String role) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("insert into roles (name) values (?)")) {
                statement.setString(1, role);
                statement.executeUpdate();
            }
        }
    }

    public List<String> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from roles")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<String> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(rs.getString("name"));
                    }
                    return result;
                }
            }
        }
    }

    public void deleteAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("delete from roles")) {
                statement.executeUpdate();
            }
        }
    }
}
