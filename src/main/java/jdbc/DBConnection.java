package jdbc;

import exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String dbDriver = "org.h2.Driver";
    private static final String dbConnection = "jdbc:h2:mem:test";
    private static final String dbUser = null;
    private static final String dbPassword = null;

    private DBConnection() {}

    public static Connection initialConnection() throws ConnectionException {
        try {
            Class.forName(dbDriver);
            return DriverManager.getConnection(dbConnection, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException(1, "ClassNotFoundException");
        } catch (SQLException e) {
            throw new ConnectionException(2, "DriverManager.getConnection is error");
        }
    }

    public static void destroyConnection(Connection connection) throws ConnectionException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionException(3, "connection.close() is error");
        }
    }


}
