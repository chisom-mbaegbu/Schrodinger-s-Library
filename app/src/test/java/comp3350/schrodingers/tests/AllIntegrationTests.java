package comp3350.schrodingers.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.schrodingers.tests.business.AccessPaymentInfoIT;
import comp3350.schrodingers.tests.business.AccessPurchaseHistoryIT;
import comp3350.schrodingers.tests.business.AccessShoppingCartIT;
import comp3350.schrodingers.tests.business.AccessUserInfoIT;
import comp3350.schrodingers.tests.business. AccessWishlistIT;
import comp3350.schrodingers.tests.business. AccessBooksIT;
import comp3350.schrodingers.tests.business. AccessRatingsIT;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessPaymentInfoIT.class,
        AccessUserInfoIT.class,
        AccessPurchaseHistoryIT.class,
        AccessWishlistIT.class,
        AccessBooksIT.class,
        AccessRatingsIT.class,
        AccessShoppingCartIT.class
})
public class AllIntegrationTests {
}
