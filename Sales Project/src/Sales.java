import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Program to manage inventory for a store
 * 
 * @author William Baldwin 
 */
public class Sales {

    /** 
    * used for line index before quantity
    */
    public static final int FIRST_CONSTANT1 = 36;

    /**
    * Main method executes code 
    * @param args command line arguments 
    */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.print("Usage: java -cp bin ProcessFile input_filename");
            System.exit(1);
        }

        
        Scanner fileScanner = null;
        Scanner inputInvScan = null;
        Scanner inputScan = null;
        Scanner console = new Scanner(System.in);
        String input = args[0];
        String outputname = args[1];
        Path path = Path.of(outputname);
        String answer = "";
        
        try {
            FileInputStream fileInput = new FileInputStream(input);
            fileScanner = new Scanner(fileInput);
        } catch (FileNotFoundException e) {
            System.out.print("\nUnable to access input file: " + input);
            System.exit(1);
        }

        try {
            FileInputStream fileInput = new FileInputStream(input);
            inputInvScan = new Scanner(fileInput);
        } catch (FileNotFoundException e) {
            System.out.print("\nUnable to access input file: " + input);
            System.exit(1);
        }

        try {
            FileInputStream fileInput = new FileInputStream(input);
            inputScan = new Scanner(fileInput);
        } catch (FileNotFoundException e) {
            System.out.print("\nUnable to access input file: " + input);
            System.exit(1);
        }

        if (Files.exists(path)) {
            
            System.out.print(outputname +  " exists - OK to overwrite\n(y,n)?: ");
             //ask about two scanner
            
            answer = console.nextLine();
            if (answer.charAt(0) != 'y' && answer.charAt(0) != 'Y') {
                System.exit(1);
            } 
            
        }
        
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(outputname);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot create output file");
            System.exit(1);
        }
        PrintWriter output = new PrintWriter(outputStream);

        int lines = getNumberOfLines(fileScanner);

        String[] itemIds = new String[lines];
        String[] itemNames = new String[lines];
        int[] itemQuantities = new int[lines];
        if (inputInventory(inputInvScan, itemIds, itemNames, itemQuantities) == false) {
            System.out.print("Invalid input file");
            System.exit(1);
        }
        
        int i = 0;
        
        String line = "";
        while (inputScan.hasNextLine()) {
            line = inputScan.nextLine();
            Scanner inline = new Scanner(line);
            inline.useDelimiter(",");
            if (inline.hasNext()) {
                itemIds[i] = inline.next();
            } 
            if (inline.hasNext()) {
                itemNames[i] = inline.next();
            } 
            if (inline.hasNextInt()) {
                itemQuantities[i] = inline.nextInt();
            }
            i++;
        }
        
        String list = getList(itemIds, itemNames, itemQuantities);
        String itemName = "";
        String option = "";
        String itemID = "";
        boolean validID = false;
        int quantity = 0;
        while (!option.equalsIgnoreCase("Q")) {
            validID = false;
            String cloneLine = list;
            Scanner quantMod = new Scanner(cloneLine);
            itemName = "";

            displayMenu();
            
            option = console.nextLine();
            
            if (option.equalsIgnoreCase("L")) {
                System.out.print("\n  ID            Name          Quantity");
                System.out.print("\n" + list);
                
            }
            if (option.equalsIgnoreCase("S")) {

                System.out.print("Item name (is/contains): ");
                
                itemName += console.nextLine();
                System.out.print("\n  ID            Name          Quantity\n");
                System.out.print(searchByName(itemIds, itemNames, itemQuantities, itemName) + "\n");

            }
            if (option.equalsIgnoreCase("P")) {
                int pIndex =  0 - 1;
                System.out.print("Item id: ");
                itemID = console.nextLine();
                for (int j = 0; j < itemIds.length; j++) {
                    if (itemID.equals(itemIds[j])) {
                        validID = true;
                        pIndex = j;
                        break;
                    }
                }
                if (validID == false) {
                    System.out.print("Invalid item\n");

                } else {
                    System.out.print("Quantity: ");
                    if (console.hasNextInt()) {
                        int newQuant = 0;
                        quantity = console.nextInt();
                        console.nextLine();
                        if (quantity < 0) {
                            System.out.print("Invalid quantity\n");
                            
                        } else {

                            System.out.print(makePurchase(itemIds, itemQuantities, 
                                itemID, quantity) + "\n");
                            if (!makePurchase(itemIds, itemQuantities, itemID, 
                                quantity).equals("Insufficient quantity")) {
                                list = "";
                                for (i = 0; i < pIndex; i++) {
                                    list += quantMod.nextLine() + "\n";
                                }
                                String line2 = quantMod.nextLine() + "\n";
                                Scanner linescan = new Scanner(line2);
                                while(!linescan.hasNextInt()) {
                                    linescan.next();
                                }
                                newQuant = linescan.nextInt() - quantity;
                                list += line2.substring(0, FIRST_CONSTANT1)
                                    + String.format("%2s", newQuant) + "\n";
                                for (i = pIndex + 1; i < itemIds.length; i++) {
                                    if (quantMod.hasNextLine()) {
                                        list += quantMod.nextLine() + "\n";
                                    }
                                } 
                                
                            }
                            
                        }
                        
                    } else {
                        String useless = console.nextLine();
                        System.out.print("Invalid quantity\n");
                    }
                    
                }
                
            }
            if (option.equalsIgnoreCase("Q")) {
                int[] newItemQuantities = new int[itemIds.length];
                Scanner finalquantScan = new Scanner(list);
                for (int p = 0; p < itemIds.length; p++) {
                    while (!finalquantScan.hasNextInt()) {
                        finalquantScan.next();
                    }
                    newItemQuantities[p] = finalquantScan.nextInt();
                    finalquantScan.nextLine();
                }
                finalquantScan.close();
                outputInventory(output, itemIds, itemNames, newItemQuantities);
                System.exit(1);
            } 
            if (!option.equalsIgnoreCase("Q") && !option.equalsIgnoreCase("L")
                && !option.equalsIgnoreCase("S") && !option.equalsIgnoreCase("P")) {
                System.out.print("Invalid option\n");
            }
        }



    }




    /**
     * Displays the menu for the user
     * 
     */
    public static void displayMenu() {
    
        System.out.println("\nSales Program - Please choose an option.\n");
        System.out.println("L - List items");
        System.out.println("S - Search by item name");
        System.out.println("P - Purchase item");
        System.out.println("Q - Quit\n");
        System.out.print("Option: ");
        
    }


    

    /**
     * counts number of lines in the input file
     * 
     * @param in scanenr of input file
     * 
     * @return correct number of lines in the file
     * 
     */
    public static int getNumberOfLines(Scanner in) {
        int count = 0;
        if (in == null) {
            throw new IllegalArgumentException("Null file");
        }

        while ((in.hasNextLine())) {
            in.nextLine();
            count++;

        }
        return count;
    }

    /**
     * checks the input file inventory and sees if it is correctly formatted
     * 
     * @param itemIds array of itemIds
     * @param itemQuantities array of item quantities
     * @param itemNames array of item Names
     * @param in scanner of input file
     * 
     * @return if the input file inventory is formatted correctly or not T/F
     * @throws IllegalArgumentException if null file, null array, or invalid array length
     */
    public static boolean inputInventory(Scanner in, String[] itemIds, String[] itemNames, 
        int[] itemQuantities) {
        if (in == null) {
            throw new IllegalArgumentException("Null file");
        }
        if (itemIds == null || itemNames == null || itemQuantities == null) {
            throw new IllegalArgumentException("Null array");
        }
        if ((itemIds.length != itemNames.length || itemIds.length != itemQuantities.length
            || itemNames.length != itemQuantities.length)) {
            throw new IllegalArgumentException("Invalid array length");
        }
        int i = 0;
        String line = "";
        while (in.hasNextLine()) {
            line = in.nextLine();
            Scanner inline = new Scanner(line);
            inline.useDelimiter(",");
            if (inline.hasNext()) {
                itemIds[i] = inline.next();
            } else {
                inline.close();
                return false;
            }
            if (inline.hasNext()) {
                itemNames[i] = inline.next();
            } else {
                inline.close();
                return false;
            }
            if (inline.hasNextInt()) {
                itemQuantities[i] = inline.nextInt();
            } else {
                inline.close();
                return false;
            }

            if (inline.hasNext()) { //risky
                inline.close();
                return false;
            }
            
            if (itemQuantities[i] < 0) {
                inline.close();
                return false;
            }
            i++;
        }
        if ((itemIds.length != itemNames.length || itemIds.length != itemQuantities.length
            || itemNames.length != itemQuantities.length) || (i != itemIds.length 
            || i != itemNames.length || i != itemQuantities.length)) {
            throw new IllegalArgumentException("Invalid array length");
        }
        for (int k = 0; k < itemIds.length - 1; k++) {
            for (int j = k + 1; j < itemIds.length; j++) {
                if (itemIds[k].equals(itemIds[j])) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * creates a list of IDS name and quantity for each item in the input file
     * 
     * @param itemIds array of itemIds
     * @param itemQuantities array of item quantities
     * @param itemNames array of item Names
     * 
     * @return list of all the items in the input
     * @throws IllegalArgumentException if null array or array length bad
     */
    public static String getList(String[] itemIds, String[] itemNames, 
        int[] itemQuantities) {
        if (itemIds == null || itemNames == null || itemQuantities == null) {
            throw new IllegalArgumentException("Null array");
        }
        if ((itemIds.length != itemNames.length || itemIds.length != itemQuantities.length
            || itemNames.length != itemQuantities.length)) {
            throw new IllegalArgumentException("Invalid array length");
        }
        String list = "";
        int i = 0;
        for (i = 0; i < itemIds.length; i++) {
            list += toString(itemIds[i], itemNames[i], itemQuantities[i]);
            if (i < itemIds.length - 1) {
                list += "\n";
            }
        }
        if ((itemIds.length != itemNames.length || itemIds.length != itemQuantities.length
            || itemNames.length != itemQuantities.length) || (i != itemIds.length 
            || i != itemNames.length || i != itemQuantities.length)) {
            throw new IllegalArgumentException("Invalid array length");
        }
        
        if (list.equals("")) {
            return list;
        }
        return list + "\n";
    }

    /**
     * searches a users input
     * 
     * @param itemIds array of itemIds
     * @param itemQuantities array of item quantities
     * @param itemNames array of item Names
     * @param itemName the user's search message
     * 
     * @return list of items that match the search
     * @throws IllegalArgumentException if array null or length bad
     */
    public static String searchByName(String[] itemIds, String[] itemNames, 
        int[] itemQuantities, String itemName) {
        String list = "";
        if (itemIds == null || itemNames == null || itemQuantities == null) {
            throw new IllegalArgumentException("Null array");
        }
        if (itemIds.length != itemQuantities.length || itemIds.length != itemNames.length
            || itemNames.length != itemQuantities.length) {
            throw new IllegalArgumentException("Invalid array length");
        }
        int count = 0;
        for (int i = 0; i < itemIds.length; i++) {
            if (itemNames[i].indexOf(itemName) > 0 - 1) {
                count++;
            }
        }
        for (int i = 0; i < itemIds.length; i++) {
            if ((itemNames[i].toLowerCase()).indexOf(itemName.toLowerCase()) > 0 - 1) {
                list += toString(itemIds[i], itemNames[i], itemQuantities[i]);
                list += "\n";
            }
        }
        if (list.equals("")) {
            return list;
        }
        return list;
    }

    /**
     * formats string of the array inputs
     * 
     * @param itemId itemID
     * @param itemName name of item
     * @param itemQuantity quantity of item
     * 
     * 
     * @return String of correctly formatted id name and quantity on a line
     * 
     */
    public static String toString(String itemId, String itemName, int itemQuantity) {
        return String.format("%6s  %-25s %4d", itemId, itemName, itemQuantity);
    }

    /**
     * tests if a purchase is successful
     * 
     * @param itemIds array of itemIds
     * @param itemId item chosen to purchase
     * @param itemQuantities array of item quantities
     * @param itemQuantity quantity of item being purchased
     * 
     * @return String if purchase was successful or not
     * @throws IllegalArgumentException if array null or bad length
     */
    public static String makePurchase(String[] itemIds, int[] itemQuantities, String itemId, 
        int itemQuantity) {
        if (itemIds == null || itemQuantities == null) {
            throw new IllegalArgumentException("Null array");
        }
        if (itemIds.length != itemQuantities.length) {
            throw new IllegalArgumentException("Invalid array length");
        }

        int indexItem = -1;
        int count = 0;
        for (int i = 0; i < itemIds.length; i++) {
            if (itemIds[i].equals(itemId)) {
                indexItem = i;
                count++;
            }
        }
        if (count == 0) {
            throw new IllegalArgumentException("Invalid item");
        }
        if (itemQuantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        if (itemQuantity > itemQuantities[indexItem]) {
            return "Insufficient quantity";
        }
        int ogquant = itemQuantities[indexItem];
        if (itemQuantities[indexItem] - itemQuantity > 0 - 1) {
            for (int i = 0; i < itemQuantities.length; i++) {
                if (itemQuantities[i] == ogquant) {
                    itemQuantities[i] = ogquant - itemQuantity;
                    break;
                }
            }
        }

        return "Successful purchase";
    }

    /**
     * outprints the updated inventory
     * 
     * @param out printwriter for output file
     * @param itemIds array of itemIds
     * @param itemNames array of item names
     * @param itemQuantities array of item quantities
     * @throws IllegalArgumentException if file null or array null or length bad
     */
    public static void outputInventory(PrintWriter out, String[] itemIds, String[] itemNames, 
        int[] itemQuantities) {
        if (out == null) {
            throw new IllegalArgumentException("Null file");
        }
        if (itemIds == null || itemNames == null || itemQuantities == null) {
            throw new IllegalArgumentException("Null array");
        }
        if (itemIds.length != itemQuantities.length || itemIds.length != itemNames.length
            || itemNames.length != itemQuantities.length) {
            throw new IllegalArgumentException("Invalid array length");
        }
        for (int i = 0; i < itemIds.length; i++) {
            if (i == itemIds.length - 1) {
                out.print(itemIds[i] + "," + itemNames[i] + "," + itemQuantities[i]);
            } else {
                out.println(itemIds[i] + "," + itemNames[i] + "," + itemQuantities[i]);
            }
        }
        out.close();
    }

}

