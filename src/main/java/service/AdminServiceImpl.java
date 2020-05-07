package service;

import exception.ConnectionException;
import jdbc.UserDAO;
import model.User;
import org.h2.util.StringUtils;

import java.rmi.ServerException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminServiceImpl{

    Connection connection;
    Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public void addLibrarian(Scanner scanner) throws ConnectionException, ServerException {

        System.out.println("please insert user info");
        System.out.println("name: ");
        String name=scanner.next();
        System.out.println("userName: ");
        String userName=scanner.next();
        System.out.println("pass");
        String pass=scanner.next();
        System.out.println("userType: 1=admin , 2=Librarian");
        Integer userType=scanner.nextInt();

        if (StringUtils.isNullOrEmpty(name))
            throw new ServerException("name is invalid");
        if (StringUtils.isNullOrEmpty(userName))
            throw new ServerException("userName is invalid");
        if (StringUtils.isNullOrEmpty(pass))
            throw new ServerException("pass is invalid");
        if (userType==null && !(userType.equals(1) || userType.equals(2)))
            throw new ServerException("userType is invalid");
        User user=new User();
        user.setName(name);
        user.setUserName(userName);
        user.setPassword(pass);
        user.setUserType(userType);

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

    public User findUserByUserNameAndPassword(String userName,String password,Integer userType) throws ConnectionException, ServerException {
        UserDAO userDAO=new UserDAO(connection);
        User user = userDAO.findUser(userName, password,userType);
        if (user==null){
            throw new ServerException("user by this info in not registered");
        }
        return user;
    }
}
