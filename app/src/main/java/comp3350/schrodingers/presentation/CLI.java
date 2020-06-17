package comp3350.schrodingers.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessPaymentInfo;
import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.objects.User.Billing;
import comp3350.schrodingers.objects.Book;

// Class - a simple command line interface for debugging
public class CLI
{
    // Stores console info
    public static BufferedReader console;
    public static String inputLine;
    public static String[] inputTokens;

    public static User currentUser;
    public static Billing currentCard;

    public static String userEmail;
    public static long cardNum;


    public static String indent = "  ";

    // Method - runs the CLI
    public static void run() {
        try {
            console = new BufferedReader(new InputStreamReader(System.in));
            process();
            console.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    // Method - processes user command
    public static void process() {
        readLine();
        while ((inputLine != null) && (!inputLine.equalsIgnoreCase("exit"))
                && (!inputLine.equalsIgnoreCase("quit"))
                && (!inputLine.equalsIgnoreCase("q"))
                && (!inputLine.equalsIgnoreCase("bye"))) {    // use cntl-c or exit to exit
            inputTokens = inputLine.split("\\s+");
            parse();
            readLine();
        }
    }

    // Method - reads line/input from user
    public static void readLine() {
        try {
            System.out.print(">");
            inputLine = console.readLine();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    // Method - parses user input
    public static void parse() {
        if (inputTokens[0].equalsIgnoreCase("get")) {
            processGet();
        } else {
            System.out.println("Invalid command.");
        }
    }

    // Method - acquires resource for inspection
    public static void processGet() {
        if (inputTokens[1].equalsIgnoreCase("User")) {
            processGetUser();
        } else if (inputTokens[1].equalsIgnoreCase("Payment")) {
            processGetPayment();
        } else if (inputTokens[1].equalsIgnoreCase("Book")){
            processGetBook();
        } else{
            System.out.println("Invalid data type");
        }
    }

    // Method - acquire reference to user
    public static void processGetUser() {
        AccessUserInfo accessUserInfo;
        accessUserInfo = Services.getUserInfoAccess();
        if(inputTokens.length>2){
            if(inputTokens[2].equalsIgnoreCase("orphan")){
                System.out.println(accessUserInfo.getUser().getUserName());
            }
        }
    }

    // Method - acquire reference to payment info
    public static void processGetPayment() {
        AccessPaymentInfo accessPayInfo;
        accessPayInfo = Services.getPaymentInfoAccess();
        if(inputTokens.length>2){
            if(inputTokens[2].equalsIgnoreCase("orphan")){
                System.out.println(accessPayInfo.getCard());
            }
        }
    }

    // Method - acquire reference to books
    public static void processGetBook(){
        AccessBooks accessBooks = Services.getBookAccess();
        List<Book> array = accessBooks.getAllBooks();
        System.out.println(array.get(0));
    }


}