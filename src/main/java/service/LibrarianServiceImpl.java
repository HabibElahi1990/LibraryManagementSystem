package service;

import exception.ConnectionException;
import jdbc.BookDAO;
import model.Book;
import model.User;
import org.h2.util.StringUtils;

import java.rmi.ServerException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianServiceImpl {

    Connection connection;
    Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    public LibrarianServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Scanner scanner, User user) throws ConnectionException, ServerException {
        System.out.println("please insert user info");
        System.out.println("name: ");
        String name=scanner.next();

        if (StringUtils.isNullOrEmpty(name))
            throw new ServerException("name is invalid");

        Book book=new Book();
        book.setBookStatus(0);
        book.setName(name);
        book.setUserId(user.getId());
        BookDAO bookDAO=new BookDAO(connection);
        bookDAO.insertBook(book);
        logger.log(Level.INFO, "insert is success");
    }

    public void issueBooks(List<Integer> issueIntegerList) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        for (Integer i:issueIntegerList){
            bookDAO.updateBooks(1,i);
        }
        logger.log(Level.INFO, "issue is success");
    }

    public void viewIssueBooks(Integer userId) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        List<Book> allBook = bookDAO.findAllBookByStatusList(1,userId);
        logger.log(Level.INFO, "find is success");
        allBook.forEach(n-> System.out.println(n.toString()));
    }

    public void returnBooks(List<Integer> issueIntegerList) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        for (Integer i:issueIntegerList){
            bookDAO.updateBooks(2,i);
        }
        logger.log(Level.INFO, "return is success");
    }

}
