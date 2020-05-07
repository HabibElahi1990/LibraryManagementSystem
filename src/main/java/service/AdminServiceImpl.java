package service;

import exception.ConnectionException;
import jdbc.UserDAO;
import model.User;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminServiceImpl{

    Connection connection;
    Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public void addLibrarian(User user) throws ConnectionException {
        UserDAO userDAO = new UserDAO(connection);
        userDAO.insertUser(user);
        logger.log(Level.INFO, "insert is success");
    }

    public void viewLibrarian(Integer userId) throws ConnectionException {
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.findUser(userId);
        logger.log(Level.INFO, "view is success");
        System.out.println(user.toString());
    }

    public void viewAllLibrarian() throws ConnectionException {
        UserDAO userDAO = new UserDAO(connection);
        List<User> allUser = userDAO.findAllUser();
        allUser.forEach(n -> System.out.println(n.toString()));
        logger.log(Level.INFO, "view All is success");

    }

    public void deleteLibrarian(Integer userId) throws ConnectionException {
        UserDAO userDAO = new UserDAO(connection);
        userDAO.deleteObject(userId, User.class);
        logger.log(Level.INFO, "view is success");
    }
}
