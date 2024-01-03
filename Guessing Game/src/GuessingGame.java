import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
/**
 * Class takes input from user
 * @author William Baldwin
 * 10/1/2022
 */

public class GuessingGame {
/**
    * Main method executes code 
    * @param args command line arguments 
    */
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner in = new Scanner(System.in);
        if (args.length == 1) {
            try {
                int seed = Integer.parseInt(args[0]);
                rand.setSeed(seed);
            }
            catch (NumberFormatException e) {
                System.out.println("Usage: java -cp bin GuessingGame <seed>");
                System.exit(1);
            }
        } else {
            System.out.println("Usage: java -cp bin GuessingGame <seed>");
            System.exit(1);
        }

        System.out.println("               Welcome to the Guessing Game!");
        System.out.println("You must try to guess a secret code consisting of 4 digits.");
        System.out.println("After each guess,  the total number of correct digits (CD)");   
        System.out.println("and the number of correct digits in the correct place (CP)");
        System.out.println("for the guess and all preceding guesses will be output. You");
        System.out.println("will have 10 chances to guess the secret code, which will");
        System.out.println("be revealed, if you do not guess it!");
        System.out.println("\n");

       
        final int length = 4;
        final int three = 3;
        final int four = 4;
        final int five = 5;
        final int six = 6;
        final int seven = 7;
        final int eight = 8;
        final int nine = 9;
        final int attempts = 10;
        final double onefourth = .25;
        String stringguess = "";
        int[] guess = new int [length];
        int[] guess1 = new int [length];
        int[] guess2 = new int [length];
        int[] guess3 = new int [length];
        int[] guess4 = new int [length];
        int[] guess5 = new int [length];
        int[] guess6 = new int [length];
        int[] guess7 = new int [length];
        int[] guess8 = new int [length];
        int[] guess9 = new int [length];
        double count = 0;
        int i = 0;
        int [] [] guesslist = new int[attempts] [four];
        int [] [] cpcd = new int[attempts] [2];
        int [] code = getSecretCode(rand, length);
        String secretcode = Arrays.toString(code);
        Character firstnum = secretcode.charAt(1);
        Character secondnum = secretcode.charAt(four);
        Character thirdnum = secretcode.charAt(seven);
        Character lastnum = secretcode.charAt(attempts);

        for (int j = 0; j < attempts; j++) {
            System.out.print("Guess 4 digits (e.g., 2 8 5 8): ");
            stringguess = "";
            count = 0;

            for (i = 0; i < length; i++) {
                stringguess = stringguess + in.next();
            /*System.out.println(stringguess);
            System.out.println(stringguess.length()); */

            }
            if (stringguess.length() > four || stringguess.length() < three) {
                System.out.println("Invalid guess \n");
                j = j - 1;

            } else {
                for (i = 0; i < length; i++) {
                    if (stringguess.charAt(i) == '9' || stringguess.charAt(i) == '8' 
                        || stringguess.charAt(i) == '7' ||
                        stringguess.charAt(i) == '6' || stringguess.charAt(i) == '5' 
                        || stringguess.charAt(i) == '4' ||
                        stringguess.charAt(i) == '3' || stringguess.charAt(i) == '2' 
                        || stringguess.charAt(i) == '1' || 
                        stringguess.charAt(i) == '0') {
                        /* guess[i] = Character.getNumericValue(stringguess.charAt(i)); */
                        count += onefourth;
                
                    } else {
                        System.out.println("Invalid guess \n");
                        j = j - 1;
                        break;
                    }
                }
            }

            if (count == 1.00) {
                System.out.println("\n Guess  | CD CP");
                switch (j) {
                    case 0:
                        for (int r = 0; r < 1; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [0] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [0] [0] = getCorrectDigits(code, guess);
                            cpcd [0] [1] = getCorrectDigitsInCorrectPlace(code, guess);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess) == four) {
                            System.out.print("You guessed correctly after 1 guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case 1:
                        for (int r = 0; r < 2; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [1] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess1 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [1] [0] = getCorrectDigits(code, guess1);
                            cpcd [1] [1] = getCorrectDigitsInCorrectPlace(code, guess1);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess1) == four) {
                            System.out.print("You guessed correctly after 2 guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case 2:
                        for (int r = 0; r < three; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [2] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess2 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [2] [0] = getCorrectDigits(code, guess2);
                            cpcd [2] [1] = getCorrectDigitsInCorrectPlace(code, guess2);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess2) == four) {
                            System.out.print("You guessed correctly after " 
                                + three + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case 3:
                        for (int r = 0; r < four; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [three] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess3 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [3] [0] = getCorrectDigits(code, guess3);
                            cpcd [3] [1] = getCorrectDigitsInCorrectPlace(code, guess3);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess3) == four) {
                            System.out.print("You guessed correctly after " + four + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case four:
                        for (int r = 0; r < five; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [four] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess4 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [four] [0] = getCorrectDigits(code, guess4);
                            cpcd [four] [1] = getCorrectDigitsInCorrectPlace(code, guess4);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess4) == four) {
                            System.out.print("You guessed correctly after " 
                                + five + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case five:
                        for (int r = 0; r < six; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [five] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess5 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [five] [0] = getCorrectDigits(code, guess5);
                            cpcd [five] [1] = getCorrectDigitsInCorrectPlace(code, guess5);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess5) == four) {
                            System.out.print("You guessed correctly after " 
                                + six + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case six:
                        for (int r = 0; r < seven; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [six] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess6 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [six] [0] = getCorrectDigits(code, guess6);
                            cpcd [six] [1] = getCorrectDigitsInCorrectPlace(code, guess6);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess6) == four) {
                            System.out.print("You guessed correctly after " 
                                + seven + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case seven:
                        for (int r = 0; r < eight; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [seven] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess7 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [seven] [0] = getCorrectDigits(code, guess7);
                            cpcd [seven] [1] = getCorrectDigitsInCorrectPlace(code, guess7);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess7) == four) {
                            System.out.print("You guessed correctly after " 
                                + eight + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case eight:
                        for (int r = 0; r < nine; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [eight] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess8 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [eight] [0] = getCorrectDigits(code, guess8);
                            cpcd [eight] [1] = getCorrectDigitsInCorrectPlace(code, guess8);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess8) == four) {
                            System.out.print("You guessed correctly after " 
                                + nine + " guess(es)!");
                            System.exit(1);
                        }
                        break;
                    case nine:
                        for (int r = 0; r < attempts; r++) {
                            for (int k = 0; k < length; k++) {
                                guesslist [nine] [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                guess9 [k] = 
                                Character.getNumericValue(stringguess.charAt(k));
                                System.out.print(guesslist [r] [k]);
                                System.out.print(" ");
                            }
                            cpcd [nine] [0] = getCorrectDigits(code, guess9);
                            cpcd [nine] [1] = getCorrectDigitsInCorrectPlace(code, guess9);
                            System.out.print("|  " + cpcd [r] [0] + "  " + cpcd [r] [1] + "\n");
                        }
                        System.out.print("\n");
                        if (getCorrectDigitsInCorrectPlace(code, guess9) == four) {
                            System.out.print("You guessed correctly after " 
                                + attempts + " guess(es)!");
                            System.exit(1);
                        }
                        System.out.print("Sorry, no more guesses - the secret code is " 
                            + firstnum + " " + secondnum + " " + thirdnum + " " + lastnum);
                        break;
                    default: 
                        break;
                }
                
            }
        }
    }
        
    
    /**
     * Finds the secret code
     * 
     * @param rand sectret code array
     * @param length array of guess
     * 
     * @return secret code array
     * @throws IllegalArgumentException if null input
     */
    public static int [] getSecretCode(Random rand, int length) {
        if (rand == null) {
            throw new IllegalArgumentException("Null rand");
        }
        if (length == 0) {
            throw new IllegalArgumentException("Invalid length");
        }
        int [] secretCode = new int [length];
        for (int i = 0; i < length; i++) {
            secretCode[i] = rand.nextInt(10);
        }
        return (secretCode);

    }

    /**
     * Finds the number of correct digits
     * 
     * @param code sectret code array
     * @param guess array of guess
     * 
     * @return number of correct digits
     * 
     */
    public static int getCorrectDigits(int [] code, int [] guess) {
        final int nine = 9;
        if (code == null) {
            throw new IllegalArgumentException("Null code");
        }
        if (guess == null) {
            throw new IllegalArgumentException("Null guess");
        }
        if (code.length != guess.length) {
            throw new IllegalArgumentException("Different lengths");
        }
        for (int i = 0; i < code.length; i ++) {
            if (code[i] > nine || code[i] < 0 || guess[i] > nine || guess[i] < 0) {
                throw new IllegalArgumentException("Invalid digit");
            }
        }
        int count = 0;
        int check = -1;
        for (int i = 0; i < code.length; i++) {
            for (int j = 0; j < code.length; j++) {
                if (code[i] == guess[j] && j != check) {
                    count++;
                    check = j;
                    break;
                }
                
            }
            
        }
        return (count);
    }

    /**
     * Finds the number of correct digits in the correct place
     * 
     * @param code sectret code array
     * @param guess array of guess
     * 
     * @return number of correct digits in correct place
     * 
     */
    public static int getCorrectDigitsInCorrectPlace(int [] code, int [] guess) {
        final int nine = 9;
        if (code == null) {
            throw new IllegalArgumentException("Null code");
        }
        if (guess == null) {
            throw new IllegalArgumentException("Null guess");
        }
        if (code.length != guess.length) {
            throw new IllegalArgumentException("Different lengths");
        }
        for (int i = 0; i < code.length; i ++) {
            if (code[i] > nine || code[i] < 0 || guess[i] > nine || guess[i] < 0) {
                throw new IllegalArgumentException("Invalid digit");
            }
        }
        int count = 0;
        for (int i = 0; i < code.length; i++) {
            if (code[i] == guess[i]) {
                count++;
            }

        
        }
        return (count);
    }
}