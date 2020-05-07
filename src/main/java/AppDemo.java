import java.util.Scanner;

public class AppDemo {

    public static void main(String[] args) {
        boolean useApp = true;
        Scanner scanner = new Scanner(System.in);
        while (useApp) {
            mainMenu(scanner);
        }
    }

    static void mainMenu(Scanner scanner) {
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

    static void userMenu(Scanner scanner) {
        System.out.println("please enter number from your User for login");
        System.out.println("1-Admin");
        System.out.println("2-Libraian");
        System.out.println("3-back");
        int selected = scanner.nextInt();
        switch (selected) {
            case 1:
                adminMenu(scanner);
                break;
            case 2:
                libraianMenu(scanner);
                break;
            case 3:
                mainMenu(scanner);
                break;
        }
    }

    static void adminLogin(Scanner scanner){

    }

    static void libraianLogin(Scanner scanner){

    }

    static void adminMenu(Scanner scanner) {

    }

    static void libraianMenu(Scanner scanner) {

    }
}
