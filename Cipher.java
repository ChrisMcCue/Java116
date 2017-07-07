import java.util.*;
import java.io.*;
/**
 *The following program is a caesar cipher that encodes and decodes user files
 *based on a user shift key
 *
 *@author Chris McCue
 */
public class Cipher {

    public static final int ALPHABET = 26;
    /**
     *Starts the program
     *
     *@param args command line arguments
     */
    public static void main(String[]args) {
        Scanner console = new Scanner(System.in);
        Scanner encryptScanner = new Scanner(System.in);
        Scanner actionScanner = new Scanner(System.in);
        Scanner decryptScanner = new Scanner(System.in);
        Scanner input = null;
        PrintStream output = null;
        System.out.println("\t \t \tWelcome to the Encrypter program.");
        System.out.println("The following program will encrypt or decrypt"
                    + " text within a file based on the number of jumps"
                    + " provided.");
        int shiftAmount = getShiftAmount(console);
        String prime = " ";
        String quit = "q";
        String encrypt = "e";
        String decrypt = "d";
        while(!prime.equalsIgnoreCase(quit)) {
            System.out.print("Would you like to (E)ncrypt or (D)ecrypt a message? Or (Q)uit? ");
            prime = actionScanner.next();
            if(prime.equalsIgnoreCase(encrypt)) {
                input = getInputScanner(encryptScanner);
                output = getOutputPrintStream(encryptScanner);
                processFile(shiftAmount, true, input, output);
            } else if (prime.equalsIgnoreCase(decrypt)) {
                input = getInputScanner(decryptScanner);
                output = getOutputPrintStream(decryptScanner);
                processFile(shiftAmount, false, input, output);
            }
        }
    }
    /**
     *Asks the user for a shift amount - amount must be between 1 and 25
     *
     *@param console scanner for user input
     *@return usershift shift to encode / decode files
     */
    public static int getShiftAmount(Scanner console) {
        System.out.print("Enter a shift amount (1 - 25): ");
        while (!console.hasNextInt()) {
            console.next();
            System.out.print("Error: Enter a valid integer between 1 - 25: ");
        }
        int userShift = console.nextInt();
        while (userShift < 1 || userShift > 25) {
            System.out.print("Error: Enter a valid integer between 1 - 25: ");
            while (!console.hasNextInt()) {
                console.next();
                System.out.print("Error: Enter a valid integer between 1 - 25: ");
            }
            userShift = console.nextInt();
        }
        return userShift;
    }
    /**
     *Asks the user for an input filename - input file must exist
     *
     *@param in scanner for user input
     *@return file the input file that the program will encode / decode
     */
    public static Scanner getInputScanner(Scanner in) {
        Scanner file = null;
        while (file == null) {
            System.out.print("Input filename? ");
            String name = in.nextLine();
            try {
                file = new Scanner(new File(name));
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
        return file;
     }

     /**
      *Asks the user for an output filename
      *
      *@param out scanner for user input
      *@return outputFile name of printstream file
      */
     public static PrintStream getOutputPrintStream(Scanner out) {
         PrintStream outputFile = null;
         while (outputFile == null) {
             System.out.print("Output filename? ");
             File filename = new File(out.nextLine());
             try {
                 if(!filename.exists()){
                     outputFile = new PrintStream(filename);
                 } else {
                     System.out.println("File already exists.");
                     System.exit(1);
                 }
             } catch (FileNotFoundException e) {
                 System.out.println("File unable to be written. Please try again.");
             }
         }
         return outputFile;
     }

     /**
      *Combines the shift amount, ecrypt / decrypt action, input file, and
      *output file to pass to the encrypt / decrypt method
      *@param shiftAmount shift amount to encode / decode
      *@param encrypt boolean to encode / decode
      *@param input input file to encode / decode
      *@param output printstream output file
      */
     public static void processFile(int shiftAmount, boolean encrypt, Scanner input, PrintStream output) {
         if(encrypt == true) {
             while(input.hasNext()) {
                 String line = input.nextLine();
                 output.println(encryptLine(shiftAmount, line));
             }
         } else {
             while(input.hasNext()) {
                 String line = input.nextLine();
                 output.println(decryptLine(shiftAmount, line));
             }
         }
     }

     /**
      *Encrypts the line passed to the method with the shift amount provided
      *
      *@param shiftAmount amount to shift for encryption
      *@param line line to be encrypted
      *@return encrypt encrypted line
      */
     public static String encryptLine(int shiftAmount, String line) {
         String encrypt = "";
         int c = 0;
         if(shiftAmount < 1 || shiftAmount > 25) {
             throw new IllegalArgumentException("Shift amount is less than 1 or greater than 25.");
         }
         for (int i = 0; i < line.length(); i++) {
             c = line.charAt(i);
             if(Character.isUpperCase(c)) {
                 c = c + (shiftAmount % ALPHABET);
                 if (c > 'Z') {
                     c = c - ALPHABET;
                 }
             } else if (Character.isLowerCase(c)) {
                 c = c + (shiftAmount % ALPHABET);
                 if (c > 'z') {
                     c = c - ALPHABET;
                 }
             }
             encrypt += (char) c;
        }
        return encrypt;
     }

     /**
      *Decrypts the line passed to the method with the shift amount provided
      *
      *@param shiftAmount
      *@param line
      *@return decrypt decrypted line
      */
     public static String decryptLine(int shiftAmount, String line) {
         String decrypt = "";
         int c = 0;
         if(shiftAmount < 1 || shiftAmount > 25) {
             throw new IllegalArgumentException("Shift amount is less than 1 or greater than 25.");
         }
         for (int i = 0; i < line.length(); i++) {
             c = line.charAt(i);
             if(Character.isUpperCase(c)) {
                 c = c - (shiftAmount % ALPHABET);
                 if (c < 'A') {
                     c = c + ALPHABET;
                 }
             } else if (Character.isLowerCase(c)) {
                 c = c - (shiftAmount % ALPHABET);
                 if (c < 'a') {
                     c = c + ALPHABET;
                 }
             }
             decrypt += (char) c;
        }
        return decrypt;
     }
 }
