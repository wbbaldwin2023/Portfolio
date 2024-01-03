


import java.util.Scanner;

/**
 * Class takes input from user
 * @author William Baldwin
 * 9/18/2022
 */
public class ClubStore {

/**
    * Main method executes code 
    * @param args command line arguments 
    */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("                    Welcome to our Club Store!"); 
        System.out.println("\nAll orders must be placed between October 15 and November 30, 2022.");
        System.out.println("When prompted, please enter today's date and the number of each");
        System.out.println("item you would like to purchase. Please enter S to choose Standard");
        System.out.println("(five-day) shipping or T to choose Two-day shipping. Orders of");
        System.out.println("$25.00 or more receive free Standard shipping. Entering the correct");
        System.out.println("coupon code also entitles you to free Standard shipping! A receipt");
        System.out.println("and the arrival date of your order will be displayed.");

        System.out.print("\nMonth Day (e.g., 11 9): ");
        int month = in.nextInt();
        int day = in.nextInt();

        if (isValidDate(month, day) == false) {
            throw new IllegalArgumentException("Invalid date");
        }

        

        System.out.print("Number of Water Bottles($10.00 each): " );
        int bottle = in.nextInt();
        checkForNegative(bottle);

        System.out.print("Number of Coffee Mugs($12.00 each): ");
        int mugs = in.nextInt();
        checkForNegative(mugs);

        System.out.print("Number of Tote Bags($18.00 each): ");
        int bags = in.nextInt();
        checkForNegative(bags);

        int subtotal = getSubtotal(bottle, mugs, bags);

        int numItems = bags + mugs + bottle;
        final int pricenoshipping = 25;

        boolean twoDayShipping = false;
        boolean hasValidCoupon = false;
        String yn = "lol";
        
        if(numItems > 0){
            System.out.print("Shipping (S-tandard, T-wo Day): ");
            String c = in.next();
            if ((c.equals("S")  || c.equals("s")) && (subtotal < pricenoshipping)) {
                System.out.print("Coupon (y,n): ");
                yn = in.next();
                if (yn.charAt(0) == 'y' || yn.charAt(0) == 'Y') {
                    System.out.print("Coupon Code: ");
                    String coupon = in.next();
                    if(coupon.equals("PACK2022")) { 
                        hasValidCoupon = true;
                    } else {
                        System.out.println("Invalid code"); } 
                }
            } else { if ("t".equals(c) || "T".equals(c)) {
                    twoDayShipping = true; }
                else if ( !"t".equals(c) && !"T".equals(c) && !"S".equals(c) && !"s".equals(c)) {
                    System.out.println("Invalid shipping");
                    System.exit(1);
                }
            
            } 
            
        }
        int numShippingDays;
        final int standardShippingDays = 5;
        final int twoDayShippingDays = 2;

        if (twoDayShipping == true) { 
            numShippingDays = twoDayShippingDays; 
        } else { 
            numShippingDays = standardShippingDays; 
        }
        
/* 
coded all of this before I saw we needed a method for Arrival date,
 but using number of shipping days is much easier anyways
        if ((twoDayShipping == true) && (day + 2 > numDaysInOct)) {
            month++;
            day = (day+2) % numDaysInOct;
        } else {
            if (twoDayShipping == true) {
                day = day + 2;
            } else {
                if ((day + 5 > numDaysInOct) && (twoDayShipping == false)) {
                 month++;
                 day = (day + 5) % numDaysInOct;
                } else {
                     if (twoDayShipping == false) {
                    day = day + 5;
                     }
                }
            }
        } 
*/

        int shippingCost = getShippingCost(subtotal, twoDayShipping, hasValidCoupon);

        int total = subtotal + shippingCost;

        System.out.println("\n");
        System.out.printf("%-10s", "Subtotal:");
        System.out.printf("$%3d", subtotal);
        System.out.println(".00");
        System.out.println("Shipping: $  " + shippingCost + ".00");
        System.out.printf("%-10s", "Total:");
        System.out.printf("$%3d", total);
        System.out.println(".00");
        if (subtotal > 0) {
            System.out.println("Arrival Date: " + getArrivalDate(month, day, numShippingDays));
        }
    }

    /**
     * check if date is valid
     * 
     * @param month month of input
     * @param day day of input
     * @return true or false
     */

    public static boolean isValidDate(int month, int day) {
        final int oct = 10;
        final int nov = 11;
        final int lowestDay = 15;
        final int highOct = 31;
        final int highNov = 30;
        if (month != oct && month != nov) { return (false); }
        
        if (month == oct) { if (day < lowestDay || day > highOct) { return (false); } }
        
        if (month == nov) { if (day < 1 || day > highNov) { return (false); } } 

        return (true);
    }

    /**
     * check if input is negative
     * 
     * @param  a input
     * system exit if input is negative
     * 
     */
    
    public static void checkForNegative(int a) {
        if ( a < 0) { 
            System.out.print ("Invalid number"); 
            System.exit(1); 
        }
    }
    
    /**
     * Finds subtotal of order
     * 
     * @param  numberOfBottles number of bottles ordered
     * @param numberOfMugs number of mugs ordered
     * @param numberOfBags number of bags ordered
     * @return subtotal
     * @throws IllegalArgumentException If number of anything ordered is negative; Message:
     *             Invalid number
     */
    
    public static int getSubtotal(int numberOfBottles, int numberOfMugs, int numberOfBags) {
        final int bottlePrice = 10;
        final int mugPrice = 12;
        final int bagPrice = 18;
        if (numberOfBottles < 0 || numberOfMugs < 0 || numberOfBags < 0) {
            throw new IllegalArgumentException("Invalid number");
        }
        return (bottlePrice * numberOfBottles) + (mugPrice * numberOfMugs) + 
            (bagPrice * numberOfBags);
    }

    /**
     * Finds the corresponding arrival day number
     * 
     * @param d day
     * @param m month
     * 
     * @return corresponding number to day of the week
     * 
     */
    
    public static int dayOfTheWeek(int d, int m) {
        final int y = 2022;
        final int fourhun = 400;
        final int onehun = 100;
        final int twelve = 12;
        final int fourteen = 14;
        final int four = 4;
        final int thirtyone = 31;
        final int seven = 7;

        int w = y - (fourteen - m) / twelve;
        
        int x = w + w / four - w / onehun + w / fourhun;
        
        int z = m +  twelve * ((fourteen - m) / twelve) - 2;
        
        return ((d + x + (thirtyone * z) / twelve) % seven);
        
    }

    /**
     * Finds the shipping cost of the order
     * 
     * @param  subtotal subtotal before total cost of order
     * @param twoDayShipping if they got two day shipping
     * @param hasValidCoupon if they have a valid coupon
     * @return shipping cost
     * @throws IllegalArgumentException If subtotal is negative; Message:
     *             Invalid subtotal
     */

    public static int getShippingCost(int subtotal, boolean twoDayShipping,
        boolean hasValidCoupon) {
        final int costOfTwoDayShipping = 5;
        final int costOfStandardShipping = 3;
        final int priceCap = 25;
        int costOfCouponOr25Subtotal = 0;
        
        if (twoDayShipping == true) {
            return (costOfTwoDayShipping);
        } else {
        
            if(hasValidCoupon == true || subtotal >= priceCap) {
                return (costOfCouponOr25Subtotal);
            } 
            if(subtotal == 0) {
                return (0);
            }
            if (subtotal < 0) {
                throw new IllegalArgumentException("Invalid subtotal");
            }
            else { return (costOfStandardShipping); }
        }
    }

    /**
     * Finds the Arrival date of the order
     * 
     * @param  orderMonth month of order
     * @param orderDay day of order
     * @param numberOfShippingDays number of days it will take to be shipped
     * @return arrivalDate
     * @throws IllegalArgumentException if orderDay, shipping days,
     *  or order month is wrong. Message:
     *             Invalid date, Invalid days
     */

    public static String getArrivalDate(int orderMonth, int orderDay, int numberOfShippingDays) {
        final int numDaysInOct = 31;
        final int numDaysInNov = 30;
        final int year = 2022;
        final int october = 10;
        final int nov = 11;
        final int minDays = 15;
        final int octMax = 31;
        final int maxShip = 5;

        if (orderMonth != october && orderMonth != nov) { 
            throw new IllegalArgumentException("Invalid date");
        }

        if (orderMonth == 10) {
            if (orderDay < minDays || orderDay > octMax){
                throw new IllegalArgumentException("Invalid date"); } }
        if (numberOfShippingDays < 1 || numberOfShippingDays > maxShip) {
            throw new IllegalArgumentException("Invalid days");
        }

        if (orderDay + numberOfShippingDays > numDaysInOct && orderMonth == october) {
            orderDay = (orderDay + numberOfShippingDays) % numDaysInOct;
            orderMonth++;
        } else if (orderDay + numberOfShippingDays > numDaysInNov && orderMonth == nov) {
            orderDay = (orderDay + numberOfShippingDays) % numDaysInNov;
            orderMonth++;
        } else {orderDay = orderDay + numberOfShippingDays; }
        
        
        int dayOfTheWeek = dayOfTheWeek(orderDay, orderMonth);
        String dayName = "lol";
        final int four = 4;
        final int five = 5;
        final int six = 6;
        if (dayOfTheWeek == 0) { dayName = "Sun"; }
        if (dayOfTheWeek == 1) { dayName = "Mon"; }
        if (dayOfTheWeek == 2) { dayName = "Tue"; }
        if (dayOfTheWeek == 3) { dayName = "Wed"; }
        if (dayOfTheWeek == four) { dayName = "Thu"; }
        if (dayOfTheWeek == five) { dayName = "Fri"; }
        if (dayOfTheWeek == six) { dayName = "Sat"; }

        String arrivalDate = dayName + ", " + orderMonth + "/" + orderDay + "/" + year;
        return (arrivalDate);
    }



}

    












































