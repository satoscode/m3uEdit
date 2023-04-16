import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import mypackage.M3UProcessor;


/*
public class Main {
    public static void main(String[] args) throws IOException {
        //use a switch case to make a menu of methods of tools class
        System.out.println("insert option number");
        System.out.println("1. formatList");
        System.out.println("2. createListByCategory");
        System.out.println("3. ShowCategoryList");
        System.out.println("4. deleteCategory");
        System.out.println("5. makeFolder");
        System.out.println("6. ShowTvCategoryList");
        System.out.println("7. deleteCategoryTV");
        System.out.println("8. createListByCategory2");
        //use new scanner
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        //use a switch case to make a menu of methods of tools class
        //menu option exit, if user insert 0
        int option = scanner.nextInt();
        while (option != 0) {



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
                case 6:
                    Tools.ShowTvCategoryList();
                    break;
                case 7:
                    System.out.println("insert category name");
                    String categoryDeleteTV = scanner2.nextLine();
                    Tools.deleteCategoryTV(categoryDeleteTV);
                    break;
                case 8:
                    System.out.println("insert category name");
                    String category2 = scanner2.nextLine();
                    Tools.createListByCategory2(category2);
                    break;
                default:
                    System.out.println("invalid option");
            }
            System.out.println("insert option number");
            System.out.println("1. formatList");
            System.out.println("2. createListByCategory");
            System.out.println("3. ShowCategoryList");
            System.out.println("4. deleteCategory");
            System.out.println("5. makeFolder");
            System.out.println("6. ShowTvCategoryList");
            System.out.println("7. deleteCategoryTV");
            System.out.println("8. createListByCategory2");
            option = scanner.nextInt();
        }
    }
}*/

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            displayMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    Tools.formatList();
                    break;
                case 2:
                    System.out.println("insert category name");
                    String category = scanner.nextLine();
                    Tools.createListByCategory(category);
                    break;
                case 3:
                    Tools.ShowCategoryList();
                    break;
                case 4:
                    System.out.println("insert category name");
                    String categoryDelete = scanner.nextLine();
                    Tools.deleteCategory(categoryDelete);
                    break;
                case 5:
                    Tools.makeFolders();
                    break;
                case 6:
                    Tools.ShowTvCategoryList();
                    break;
                case 7:
                    System.out.println("insert category name");
                    String categoryDeleteTV = scanner.nextLine();
                    Tools.deleteCategoryTV(categoryDeleteTV);
                    break;
                case 8:
                    System.out.println("insert category name");
                    String category2 = scanner.nextLine();
                    Tools.createListByCategory2(category2);
                    break;
                case 9:
                    System.out.println("Enter the path of the M3U file:");
                    String inputFilePath = scanner.nextLine();

                    // Obtener la ruta de salida de la misma ubicación donde se encuentra el archivo M3U
                    String outputPath = new File(inputFilePath).getParent();

                    // Llamar al método processM3UFile con la ruta de entrada y de salida
                    M3UProcessor.processM3UFile(inputFilePath, outputPath);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("invalid option");
            }
        } while (option != 0);
    }

    private static void displayMenu() {
        System.out.println("Please choose an option:");
        System.out.println("1. Format list");
        System.out.println("2. Create list by category");
        System.out.println("3. Show list of categories");
        System.out.println("4. Delete category");
        System.out.println("5. Create folders");
        System.out.println("6. Show list of TV categories");
        System.out.println("7. Delete TV category");
        System.out.println("8. Create list by category 2");
        System.out.println("9. Process M3U file");
        System.out.println("0. Exit");
    }
}