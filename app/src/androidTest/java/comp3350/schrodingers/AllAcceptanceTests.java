package comp3350.schrodingers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationTest.class,
        AccountTest.class,
        ShoppingCartTest.class,
        WishlistTest.class,
        FindBookTest.class,
        RateReviewTest.class,
        SortTest.class,
        BookRecommendationsTest.class
})
public class AllAcceptanceTests {
}
