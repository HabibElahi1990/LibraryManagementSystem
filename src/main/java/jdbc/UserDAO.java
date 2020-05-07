package jdbc;

import exception.ConnectionException;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO {

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void createUser() throws ConnectionException {
        String query = "create table user (" +
                "id  number(10),\n" +
                "name  varchar(512 char),\n" +
                "userName  varchar(30 char),\n" +
                "password  varchar(30 char),\n" +
                "userType  number(1)" +
                ")";
        super.createModelTable(query);
    }

    public void insertUser(User user) throws ConnectionException {
        String query = "insert into user(id,name,userName,password,userType) values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, getId(User.class));
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getUserType());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(query) is error");
        }
    }

    public User findUser(Integer id) throws ConnectionException {
        User user = new User();
        String q = "select * from user u where u.id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                user.setUserType(resultSet.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
        return user;
    }

    public User findUser(String userName,String pass) throws ConnectionException {
        User user = new User();
        String q = "select * from user u where u.id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
//            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                user.setUserType(resultSet.getInt(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
        return user;
    }

    public List<User> findAllUser() throws ConnectionException {
        List<User> userList = new ArrayList<>();
        String q = "select * from user u ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setUserName(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                user.setUserType(resultSet.getInt(5));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
        return userList;
    }

}
