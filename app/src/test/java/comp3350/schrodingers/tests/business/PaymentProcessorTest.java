package comp3350.schrodingers.tests.business;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import comp3350.schrodingers.business.cardExceptions.CardException;
import comp3350.schrodingers.business.PaymentProcessor;
import comp3350.schrodingers.objects.User.Billing;

public class PaymentProcessorTest extends TestCase {
    private PaymentProcessor validator;
    private long correctNum = 1234123412341234L;
    private int correctCvv = 123;
    private String correctDate = "11/22";
    private String correctName = "JOHN SMITH";
    private Billing card;

    public PaymentProcessorTest(String arg0){
        super(arg0);
        validator = new PaymentProcessor();
        card=null;
    }
    private void cardHandler(Billing c){
        try {
            validator.validateCard(c);
        }catch(CardException e){
            System.out.println("\t"+e);
        }
    }

    @Test(expected= CardException.class)
    public void testEmptyCardNumber(){
        System.out.println("\nStarting testPaymentProcessor: empty Card Number");
        card = new Billing(0L,correctName,correctDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: empty Card Number");
    }
    @Test(expected= CardException.class)
    public void testShortCardNumber(){
        System.out.println("\nStarting testPaymentProcessor: long Card Number");
        card = new Billing(999999999L,correctName,correctDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: long Card Number");
    }
    @Test(expected= CardException.class)
    public void testEmptyDate(){
        System.out.println("\nStarting testPaymentProcessor: empty Date");
        card = new Billing(999999999L,"",correctDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: empty Date");
    }
    @Test(expected= CardException.class)
    public void testIncorrectFormatDate(){
        System.out.println("\nStarting testPaymentProcessor: incorrect Date format");
        String incDate = "012019";
        System.out.println("\t"+incDate+":");
        card = new Billing(correctNum,correctName,incDate,correctCvv);
        cardHandler(card);
        incDate = "08/2019";
        System.out.println("\t"+incDate+":");
        card = new Billing(correctNum,correctName,incDate,correctCvv);
        cardHandler(card);
        incDate = "5/19";
        System.out.println("\t"+incDate+":");
        card = new Billing(correctNum,correctName,incDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: incorrect Date format");
    }
    @Test(expected= CardException.class)
    public void testExpiredDate(){
        System.out.println("\nStarting testPaymentProcessor: expired Date");
        System.out.println("\tSame year, past month:");
        String incDate = "01/19";
        card = new Billing(correctNum,correctName,incDate,correctCvv);
        cardHandler(card);
        System.out.println("\tPast year:");
        incDate = "01/16";
        card = new Billing(correctNum,correctName,incDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: expired Date");
    }
    @Test(expected= CardException.class)
    public void testSuspiciousDate(){
        System.out.println("\nStarting testPaymentProcessor: suspicious Date");
        String incDate = "01/68";
        card = new Billing(correctNum,correctName,incDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: suspicious Date");
    }
    @Test(expected= CardException.class)
    public void testEmptyCvv(){
        System.out.println("\nStarting testPaymentProcessor: empty CVV");
        card = new Billing(correctNum,correctName,correctDate,0);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: empty CVV");
    }
    @Test(expected= CardException.class)
    public void testShortCvv(){
        System.out.println("\nStarting testPaymentProcessor: short CVV");
        card = new Billing(correctNum,correctName,correctDate,1);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: short CVV");
    }
    @Test(expected= CardException.class)
    public void testEmptyName(){
        System.out.println("\nStarting testPaymentProcessor: empty name");
        card = new Billing(correctNum,"",correctDate,correctCvv);
        cardHandler(card);
        System.out.println("\nFinished testPaymentProcessor: empty name");
    }
}
