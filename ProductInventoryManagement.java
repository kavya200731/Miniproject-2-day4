
import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Scanner;

public class ProductInventoryManagement {

    static HashMap<Integer, Integer> inventory = new HashMap<>();
    static HashMap<Integer, String> productNames = new HashMap<>();
    static HashMap<Integer, String> productCategories = new HashMap<>();
    static HashMap<Integer, Double> productPrices = new HashMap<>();

    static HashSet<String> categories = new HashSet<>();
    static LinkedHashSet<String> recentProducts = new LinkedHashSet<>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       PRODUCT INVENTORY SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Product Quantity");
            System.out.println("5. Sell Product");
            System.out.println("6. Remove Product");
            System.out.println("7. Add Product Category");
            System.out.println("8. Display Unique Categories");
            System.out.println("9. Display Sorted Categories");
            System.out.println("10. Display Recently Added Products");
            System.out.println("11. Generate Inventory Report");
            System.out.println("12. Exit");

            System.out.print("Enter Your Choice: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;

                case 2:
                    displayProducts();
                    break;

                case 3:
                    searchProduct();
                    break;

                case 4:
                    updateQuantity();
                    break;

                case 5:
                    sellProduct();
                    break;

                case 6:
                    removeProduct();
                    break;

                case 7:
                    manageCategories();
                    break;

                case 8:
                    displayCategories();
                    break;

                case 9:
                    displaySortedCategories();
                    break;

                case 10:
                    manageRecentProducts();
                    break;

                case 11:
                    generateReport();
                    break;

                case 12:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 12);

        sc.close();
    }

    // 1. Add Product
    static void addProduct() {

        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Product Price: ");
        double price = readDouble();

        if (price < 0) {
            System.out.println("Price cannot be negative!");
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity = readInt();

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative!");
            return;
        }

        inventory.put(id, quantity);
        productNames.put(id, name);
        productCategories.put(id, category);
        productPrices.put(id, price);

        categories.add(category);
        recentProducts.add(name);

        System.out.println("\nProduct Added Successfully!");
    }

    // 2. Display All Products
    static void displayProducts() {

        if (inventory.isEmpty()) {
            System.out.println("Inventory is Empty!");
            return;
        }

        System.out.println("\n---------- PRODUCT INVENTORY ----------");
        System.out.printf("%-10s %-15s %-15s %-12s %-10s%n",
                "ID", "Product", "Category", "Price", "Quantity");

        for (Integer id : inventory.keySet()) {
            System.out.printf("%-10d %-15s %-15s %-12.2f %-10d%n",
                    id,
                    productNames.get(id),
                    productCategories.get(id),
                    productPrices.get(id),
                    inventory.get(id));
        }

        System.out.println("--------------------------------------");
        System.out.println("Total Products: " + inventory.size());
    }

    // 3. Search Product
    static void searchProduct() {

        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (inventory.containsKey(id)) {
            System.out.println("\nProduct ID: " + id);
            System.out.println("Product Name: " + productNames.get(id));
            System.out.println("Category: " + productCategories.get(id));
            System.out.println("Price: " + productPrices.get(id));
            System.out.println("Quantity: " + inventory.get(id));
        } else {
            System.out.println("Product Not Found!");
        }
    }

    // 4. Update Product Quantity
    static void updateQuantity() {

        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product Not Found!");
            return;
        }

        System.out.print("Enter Additional Quantity: ");
        int quantity = readInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero!");
            return;
        }

        inventory.put(id, inventory.get(id) + quantity);

        System.out.println("Stock Updated Successfully!");
        System.out.println("Product: " + productNames.get(id));
        System.out.println("Available Quantity: " + inventory.get(id));
    }

    // 5. Sell Product
    static void sellProduct() {

        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product Not Found!");
            return;
        }

        System.out.print("Enter Quantity Sold: ");
        int sold = readInt();

        if (sold <= 0) {
            System.out.println("Sale quantity must be greater than zero!");
            return;
        }

        int available = inventory.get(id);

        if (sold > available) {
            System.out.println("Insufficient Stock!");
            System.out.println("Available Quantity: " + available);
            return;
        }

        inventory.put(id, available - sold);

        System.out.println("\nSale Completed Successfully!");
        System.out.println("Product ID: " + id);
        System.out.println("Remaining Quantity: " + inventory.get(id));
    }

    // 6. Remove Product
    static void removeProduct() {

        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product Not Found!");
            return;
        }

        String removedName = productNames.get(id);

        inventory.remove(id);
        productNames.remove(id);
        productCategories.remove(id);
        productPrices.remove(id);

        // Remove name from recently added collection
        recentProducts.remove(removedName);

        System.out.println("Product Removed Successfully!");
    }

    // 7. Category Management
    static void manageCategories() {

        int choice;

        do {
            System.out.println("\n------ CATEGORY MANAGEMENT ------");
            System.out.println("1. Add Category");
            System.out.println("2. Remove Category");
            System.out.println("3. Search Category");
            System.out.println("4. Display Categories");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter Choice: ");
            choice = readInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Category Name: ");
                    String category = sc.nextLine();

                    if (categories.add(category)) {
                        System.out.println("Category Added Successfully!");
                    } else {
                        System.out.println("Category Already Exists!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Category to Remove: ");
                    String removeCategory = sc.nextLine();

                    if (categories.remove(removeCategory)) {
                        System.out.println("Category Removed Successfully!");
                    } else {
                        System.out.println("Category Not Found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Category to Search: ");
                    String searchCategory = sc.nextLine();

                    if (categories.contains(searchCategory)) {
                        System.out.println("Category Found!");
                    } else {
                        System.out.println("Category Not Found!");
                    }
                    break;

                case 4:
                    displayCategories();
                    break;

                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);
    }

    // 8. Display Unique Categories
    static void displayCategories() {

        System.out.println("\n--------- PRODUCT CATEGORIES ---------");

        if (categories.isEmpty()) {
            System.out.println("No Categories Available!");
            return;
        }

        for (String category : categories) {
            System.out.println(category);
        }

        System.out.println("Total Unique Categories: " + categories.size());
    }

    // 9. Display Sorted Categories
    static void displaySortedCategories() {

        if (categories.isEmpty()) {
            System.out.println("No Categories Available!");
            return;
        }

        TreeSet<String> sortedCategories = new TreeSet<>(categories);

        System.out.println("\n--------- SORTED CATEGORIES ---------");

        for (String category : sortedCategories) {
            System.out.println(category);
        }

        System.out.println("\nFirst Category: " + sortedCategories.first());
        System.out.println("Last Category: " + sortedCategories.last());

        System.out.println("\nDescending Order:");

        for (String category : sortedCategories.descendingSet()) {
            System.out.println(category);
        }
    }

    // 10. Recently Added Products Management
    static void manageRecentProducts() {

        int choice;

        do {
            System.out.println("\n------ RECENT PRODUCTS ------");
            System.out.println("1. Display Recently Added Products");
            System.out.println("2. Search Product Name");
            System.out.println("3. Remove Product Name");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter Choice: ");
            choice = readInt();

            switch (choice) {

                case 1:
                    if (recentProducts.isEmpty()) {
                        System.out.println("No Recent Products!");
                    } else {
                        System.out.println("\nRecently Added Products:");

                        for (String name : recentProducts) {
                            System.out.println(name);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Product Name: ");
                    String searchName = sc.nextLine();

                    if (recentProducts.contains(searchName)) {
                        System.out.println("Product Found!");
                    } else {
                        System.out.println("Product Not Found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Product Name to Remove: ");
                    String removeName = sc.nextLine();

                    if (recentProducts.remove(removeName)) {
                        System.out.println("Product Removed!");
                    } else {
                        System.out.println("Product Not Found!");
                    }
                    break;

                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 4);
    }

    // 11. Inventory Report
    static void generateReport() {

        if (inventory.isEmpty()) {
            System.out.println("Inventory is Empty!");
            return;
        }

        System.out.println("\n========== INVENTORY REPORT ==========");

        // Display all products
        displayProducts();

        // Low stock products
        System.out.println("\n------ LOW STOCK PRODUCTS ------");

        boolean found = false;

        for (Integer id : inventory.keySet()) {
            if (inventory.get(id) < 5) {
                System.out.println(
                        productNames.get(id) + " - Quantity: " + inventory.get(id));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No Low Stock Products!");
        }

        // Calculate total units
        int totalUnits = 0;

        for (Integer quantity : inventory.values()) {
            totalUnits += quantity;
        }

        System.out.println("\nTotal Product Units: " + totalUnits);

        // Find product with highest quantity
        int maxQuantity = -1;
        int maxId = -1;

        for (Integer id : inventory.keySet()) {
            if (inventory.get(id) > maxQuantity) {
                maxQuantity = inventory.get(id);
                maxId = id;
            }
        }

        System.out.println("\n------ HIGHEST STOCK PRODUCT ------");
        System.out.println("Product: " + productNames.get(maxId));
        System.out.println("Product ID: " + maxId);
        System.out.println("Quantity: " + maxQuantity);

        // Unique categories
        System.out.println("\nUnique Categories:");

        for (String category : categories) {
            System.out.println(category);
        }

        System.out.println("Total Unique Categories: " + categories.size());

        System.out.println("\n======================================");
    }

    // Read Integer safely from entered text
    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    // Read Double safely from entered text
    static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid price: ");
            }
        }
    }
}
