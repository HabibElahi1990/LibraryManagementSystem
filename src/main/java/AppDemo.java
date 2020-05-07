import exception.ConnectionException;
import jdbc.BookDAO;
import jdbc.DBConnection;
import jdbc.UserDAO;
import model.User;
import service.AdminServiceImpl;
import service.LibrarianServiceImpl;

import java.rmi.ServerException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppDemo {

    private static Connection connection;
    private static AdminServiceImpl adminService;
    private static LibrarianServiceImpl librarianService;
    private static User currentUser;

    public static void main(String[] args) {
        try {
            connection = DBConnection.initialConnection();
            registerAdminUserFirFirstTime();
            BookDAO bookDAO=new BookDAO(connection);
            bookDAO.createBook();

            boolean useApp = true;
            Scanner scanner = new Scanner(System.in);
            while (useApp) {
                mainMenu(scanner);
            }
            DBConnection.destroyConnection(connection);
        } catch (ConnectionException | ServerException e) {
            System.err.println(e.getMessage());
        }
    }

    static void registerAdminUserFirFirstTime() throws ConnectionException {
        User user = new User();
        user.setName("Admin");
        user.setUserName("admin");
        user.setPassword("admin");
        user.setUserType(1);
        UserDAO userDAO = new UserDAO(connection);
        userDAO.createUserTable();
        userDAO.insertUser(user);
    }

    static void mainMenu(Scanner scanner) throws ConnectionException, ServerException {
        System.out.println("1-login");
        System.out.println("2-Exit");
        int selected = scanner.nextInt();

        switch (selected) {
            case 1:
                userMenu(scanner);
                break;
            case 2:
            default:
                System.exit(0);
                break;
        }
    }

    static void userMenu(Scanner scanner) throws ConnectionException, ServerException {
        System.out.println("please enter number from your User for login");
        System.out.println("1-Admin");
        System.out.println("2-Libraian");
        System.out.println("3-back");
        int selected = scanner.nextInt();
        switch (selected) {
            case 1:
                adminService = new AdminServiceImpl(connection);
                userLogin(scanner,1);
                adminMenu(scanner);
                break;
            case 2:
                librarianService = new LibrarianServiceImpl(connection);
                userLogin(scanner,2);
                libraianMenu(scanner);
                break;
            case 3:
                mainMenu(scanner);
                break;
        }
    }

    static void userLogin(Scanner scanner,Integer userType) throws ConnectionException, ServerException {
        System.out.println("please insert userName , password");
        System.out.println("userName:");
        String userName = scanner.next();
        System.out.println("pass:");
        String pass = scanner.next();
        currentUser = adminService.findUserByUserNameAndPassword(userName, pass,userType);
    }

    static void adminMenu(Scanner scanner) throws ConnectionException, ServerException {
        System.out.println("please select your action");
        System.out.println("1-addLibrarian ");
        System.out.println("2-viewLibrarian ");
        System.out.println("3-viewAllLibrarian ");
        System.out.println("4-deleteLibrarian ");
        System.out.println("5-logout");
        int selected = scanner.nextInt();
        switch (selected) {
            case 1:
                adminService.addLibrarian(scanner);
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                adminMenu(scanner);
                break;
            case 2:
                System.out.println("please enter user id");
                int viewId = scanner.nextInt();
                adminService.viewLibrarian(viewId);
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                adminMenu(scanner);
                break;
            case 3:
                adminService.viewAllLibrarian();
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                adminMenu(scanner);
                break;
            case 4:
                System.out.println("please enter user id");
                int deleteId = scanner.nextInt();
                adminService.deleteLibrarian(deleteId);
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                adminMenu(scanner);
                break;
            case 5:
                userMenu(scanner);
                break;
        }
    }

    static void libraianMenu(Scanner scanner) throws ConnectionException, ServerException {
        System.out.println("please select your action");
        System.out.println("1-addBook ");
        System.out.println("2-issueBooks ");
        System.out.println("3-viewIssueBooks ");
        System.out.println("4-returnBooks ");
        System.out.println("5-logout");
        int selected = scanner.nextInt();
        switch (selected) {
            case 1:
                librarianService.addBook(scanner,currentUser);
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                libraianMenu(scanner);
                break;
            case 2:
                List<Integer> issueIntegerList=new ArrayList<>();
                System.out.println("please enter book id to issue if list from id is end please write 0");
                do{
                    int num=scanner.nextInt();
                    issueIntegerList.add(num);
//                    System.out.println(num);
                }
                while (scanner.nextInt()>0);

                librarianService.issueBooks(issueIntegerList);
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                libraianMenu(scanner);
                break;
            case 3:
                librarianService.viewIssueBooks(currentUser.getId());
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                libraianMenu(scanner);
                break;
            case 4:
                List<Integer> returnintegerList=new ArrayList<>();
                System.out.println("please enter book id to issue if list from id is end please write 0");
                do{

                    int num=scanner.nextInt();
                    returnintegerList.add(num) ;
//                    System.out.println(num);
                }
                while (scanner.nextInt()>0);

                librarianService.returnBooks(returnintegerList);
                System.out.println("are you continue? yes/no");
                if (scanner.next().equals("no"))
                    System.exit(0);
                libraianMenu(scanner);
                break;
            case 5:
                userMenu(scanner);
                break;
        }
    }
}
