package comp3350.schrodingers.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.schrodingers.tests.business.AccessPaymentInfoTest;
import comp3350.schrodingers.tests.business.AccessPurchaseHistoryTest;
import comp3350.schrodingers.tests.business.AccessRatingsTest;
import comp3350.schrodingers.tests.business.AccessShoppingCartTest;
import comp3350.schrodingers.tests.business.AccessUserInfoTest;
import comp3350.schrodingers.tests.business.PaymentProcessorTest;
import comp3350.schrodingers.tests.business.UserValidatorTest;
import comp3350.schrodingers.tests.objects.BillingTest;
import comp3350.schrodingers.tests.objects.BookTest;
import comp3350.schrodingers.tests.objects.UserTest;
import comp3350.schrodingers.tests.business.AccessWishlistTest;
import comp3350.schrodingers.tests.business.AccessBooksTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BillingTest.class,
        BookTest.class,
        UserTest.class,
        PaymentProcessorTest.class,
        UserValidatorTest.class,
        AccessUserInfoTest.class,
        AccessPaymentInfoTest.class,
        AccessPurchaseHistoryTest.class,
        AccessWishlistTest.class,
        AccessBooksTest.class,
        AccessRatingsTest.class,
        AccessShoppingCartTest.class,


})
public class AllUnitTests
{

}
