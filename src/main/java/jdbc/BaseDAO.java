package jdbc;

import exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO {

    protected Connection connection;

    protected Integer getId(Object obj) throws ConnectionException {
        String query = "select max(o.id) from " + ((Class) obj).getName().replace(((Class) obj).getPackageName()+".","") + " o";
        try {
            Integer id = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            preparedStatement.close();
            return id + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(query) is error");
        }
    }

    public void createModelTable(String sql) throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "create table is error");
        }
    }

    public void deleteObject(Integer id,Object obj) throws ConnectionException {
        String q = "delete from "+((Class) obj).getName().replace(((Class) obj).getPackageName()+".","") +" o where o.id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "delete obj is error");
        }
    }
}
