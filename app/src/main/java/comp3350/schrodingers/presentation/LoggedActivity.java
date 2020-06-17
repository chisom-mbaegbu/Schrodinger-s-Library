package comp3350.schrodingers.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.R;

// Class - handles menu where logged in users can select to update info or logout
public class LoggedActivity extends AppCompatActivity {

    // Holds access to user DB and current user
    private AccessUserInfo userList;
    private User user;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_logged);

        // Setup toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up toolbar title color
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        // Initialize DB access and get current user
        userList = Services.getUserInfoAccess();
        user = userList.getUser();

        // Update menu greeting
        updateGreeting();

    }

    // Method - update menu greeting
    private void updateGreeting() {
        TextView userName = (TextView)findViewById(R.id.greeting);
        userName.setText(user.getUserName());
    }


    // Method - onclick listener for the personal info button
    public void buttonInformationOnClick(View v) {
        Intent personIntent = new Intent(LoggedActivity.this, PersonInfo.class);
        LoggedActivity.this.startActivity(personIntent);
    }

    // Method - onclick listener for the payment info button
    public void buttonPaymentOnClick(View v) {
        Intent payIntent = new Intent(LoggedActivity.this, PaymentActivity.class);
        LoggedActivity.this.startActivity(payIntent);
    }

    // Method - onclick listener for the logout button
    public void buttonLogoutOnClick(View v) {
        userList.logout();
        finish();
    }

    // Method - ensure currently logged user is kept logged in upon resuming application
    @Override
    public void onResume() {
        super.onResume();
        user = userList.getUser();
        updateGreeting();
    }

}
