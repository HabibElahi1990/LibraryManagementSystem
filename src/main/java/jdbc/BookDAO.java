package jdbc;

import exception.ConnectionException;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends BaseDAO{
    
    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public void createBook() throws ConnectionException {
        String sql = "create table Book (" +
                "id  number(10),\n" +
                "name  varchar(512 char),\n" +
                "bookStatus  number(1),\n" +
                "userId  number(10),\n" +
                ")";
        super.createModelTable(sql);
    }

    public void insertBook(Book book) throws ConnectionException {

        String query = "insert into Book(id,name,bookStatus,userId) values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, getId(Book.class));
            preparedStatement.setString(2, book.getName());
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, book.getUserId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(query) is error");
        }
    }

    public Book findBookById(Integer id) throws ConnectionException {
        Book Book = new Book();
        String q = "select * from Book u where u.id=? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book.setId(resultSet.getInt(1));
                Book.setName(resultSet.getString(2));
                Book.setBookStatus(resultSet.getInt(3));
                Book.setUserId(resultSet.getInt(4));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
        return Book;
    }

    public List<Book> findAllBookByStatusList(Integer bookStatus,Integer userId) throws ConnectionException {
        List<Book> bookList = new ArrayList<>();
        String q = "select * from Book u where u.bookStatus=? and u.userId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setInt(1, bookStatus);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book Book = new Book();
                Book.setBookStatus(resultSet.getInt(3));
                Book.setId(resultSet.getInt(1));
                Book.setName(resultSet.getString(2));
                Book.setUserId(resultSet.getInt(4));
                bookList.add(Book);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
        return bookList;
    }

    public List<Book> findAllBook(Integer userId) throws ConnectionException {
        List<Book> bookList = new ArrayList<>();
        String q = "select * from Book u where u.userId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book Book = new Book();
                Book.setId(resultSet.getInt(1));
                Book.setName(resultSet.getString(2));
                Book.setBookStatus(resultSet.getInt(3));
                Book.setUserId(resultSet.getInt(4));
                bookList.add(Book);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
        return bookList;
    }

    public void updateBooks(Integer statusId,Integer id) throws ConnectionException {
        String sql="update book b set b.bookStatus=? where b.id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,statusId);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectionException(4, "connection.prepareStatement(q)");
        }
    }
}
