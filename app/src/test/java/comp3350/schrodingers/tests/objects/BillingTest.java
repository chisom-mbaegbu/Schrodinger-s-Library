package comp3350.schrodingers.tests.objects;
import org.junit.Test;
import static org.junit.Assert.*;
import comp3350.schrodingers.objects.User.Billing;

public class BillingTest {
    @Test
    public void testBilling(){
        Billing bill;
        System.out.println("\nStarting testBilling");

        bill = new Billing(1234123412341234L,"JOHN SMITH","10/20",321);
        assertNotNull(bill);
        assertEquals(1234123412341234L, bill.getCardNumber());
        assertEquals("JOHN SMITH", bill.getFullName());
        assertEquals("10/20", bill.getExpiry());
        assertEquals(321, bill.getCvv());

        System.out.println("Finished testBilling");

    }
}
