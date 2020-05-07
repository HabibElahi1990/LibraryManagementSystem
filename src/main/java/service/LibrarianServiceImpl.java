package service;

import exception.ConnectionException;
import jdbc.BookDAO;
import model.Book;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianServiceImpl {

    Connection connection;
    Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

    public LibrarianServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        bookDAO.insertBook(book);
        logger.log(Level.INFO, "insert is success");
    }

    public void issueBooks(Integer[] bookIdArray) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        int i=0;
        while (i<bookIdArray.length){
            bookDAO.updateBooks(1,bookIdArray[i]);
            i++;
        }
        logger.log(Level.INFO, "issue is success");
    }

    public void viewIssueBooks(Integer userId) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        List<Book> allBook = bookDAO.findAllBook(userId);
        logger.log(Level.INFO, "find is success");
        allBook.forEach(n->n.toString());
    }

    public void returnBooks(Integer[] bookIdArray) throws ConnectionException {
        BookDAO bookDAO=new BookDAO(connection);
        int i=0;
        while (i<bookIdArray.length){
            bookDAO.updateBooks(2,bookIdArray[i]);
            i++;
        }
        logger.log(Level.INFO, "return is success");
    }

}
