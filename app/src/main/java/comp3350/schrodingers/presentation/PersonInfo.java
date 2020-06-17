package comp3350.schrodingers.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.objects.User.Address;

import comp3350.schrodingers.R;

// Class - page which handles the editing of personal info
public class PersonInfo extends AppCompatActivity {

    // Store user DB access and current user
    private AccessUserInfo userList;
    private User user;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_person_info);

        // Setup toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up toolbar title color
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        // Instantiate access to DB and acquire currently logged user
        userList = Services.getUserInfoAccess();
        user = userList.getUser();

        // Set presented information as stored user info (if user has already entered it)
        if (user != null) {
            EditText userName = (EditText)findViewById(R.id.username);
            userName.setText(user.getUserName());
            EditText userEmail = (EditText)findViewById(R.id.email);
            userEmail.setText(user.getEmail());
            if (!user.getAddress().noAddr()) {
                EditText userAddress = (EditText) findViewById(R.id.address);
                userAddress.setText(user.getAddress().getAddress());
                EditText userCity = (EditText) findViewById(R.id.city);
                userCity.setText(user.getAddress().getCity());
                EditText userState = (EditText) findViewById(R.id.province);
                userState.setText(user.getAddress().getState());
                EditText userZip = (EditText) findViewById(R.id.zip);
                userZip.setText(user.getAddress().getPostalCode());
                EditText userCountry = (EditText) findViewById(R.id.country);
                userCountry.setText(user.getAddress().getCountry());
            }
        }
    }

    // Method - insert personal into DB upon button press
    public void buttonInfoUpdate(View v) {

        // Acquire layout views
        EditText editName = (EditText)findViewById(R.id.username);
        EditText editEmail = (EditText)findViewById(R.id.email);
        EditText editAddress = (EditText)findViewById(R.id.address);
        EditText editCity = (EditText)findViewById(R.id.city);
        EditText editState = (EditText)findViewById(R.id.province);
        EditText editZip = (EditText)findViewById(R.id.zip);
        EditText editCountry = (EditText)findViewById(R.id.country);

        try {

            Address address = new Address(editAddress.getText().toString(),
                    editZip.getText().toString(), editCity.getText().toString(),
                    editState.getText().toString(), editCountry.getText().toString());

            User newUser = user;
            newUser.setUserName(editName.getText().toString());
            newUser.setEmail(editEmail.getText().toString());
            newUser.setAddress(address);

            user = userList.insertUser(newUser);

            // Inform parent activity that info has been updated
            setResult(RESULT_OK, null);
            finish();

        } catch (UserException e) {
            HandleUserExceptions handleUser = new HandleUserExceptions(e);
            handleUser.showMessage(this);
        }
    }
}
