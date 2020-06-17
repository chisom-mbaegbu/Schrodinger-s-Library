package comp3350.schrodingers;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import comp3350.schrodingers.presentation.HomeActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WishlistTest {
    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void addToWishlist(){
        onData(anything()).inAdapterView(withId(R.id.browseView)).atPosition(0).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.viewButton)).perform(click());
        onView(withText("Annabelle Fights Life")).check(matches(isDisplayed()));
    }
}
