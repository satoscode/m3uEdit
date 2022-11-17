import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //use a switch case to make a menu of methods of tools class
        System.out.println("insert option number");
        System.out.println("1. formatList");
        System.out.println("2. createListByCategory");
        System.out.println("3. ShowCategoryList");
        System.out.println("4. deleteCategory");
        System.out.println("5. makeFolder");
        //use new scanner
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        //use a switch case to make a menu of methods of tools class
        //menu option exit, if user insert 0
        int option = scanner.nextInt();
        while (option != 0) {

            System.out.println("insert option number");
            System.out.println("1. formatList");
            System.out.println("2. createListByCategory");
            System.out.println("3. ShowCategoryList");
            System.out.println("4. deleteCategory");
            System.out.println("5. makeFolder");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    Tools.formatList();
                    break;
                case 2:
                    System.out.println("insert category name");
                    String category = scanner2.nextLine();
                    Tools.createListByCategory(category);
                    break;
                case 3:
                    Tools.ShowCategoryList();
                    break;
                case 4:
                    System.out.println("insert category name");
                    String categoryDelete = scanner2.nextLine();
                    Tools.deleteCategory(categoryDelete);
                    break;
                case 5:
                    Tools.makeFolders();
                    break;
                default:
                    System.out.println("invalid option");
            }
        }
    }
}