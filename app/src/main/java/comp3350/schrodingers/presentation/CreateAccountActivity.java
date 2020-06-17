package comp3350.schrodingers.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.R;

// Class - presents the layout used for account creation
public class CreateAccountActivity extends AppCompatActivity {

    // Various views used in the layout
    private EditText username;
    private EditText email;
    private EditText password;
    private Button account;

    // Store reference to DB
    AccessUserInfo userInfoAccess;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_create_account);

        // Acquire DB access
        userInfoAccess = Services.getUserInfoAccess();

        // Instantiate views
        username = (EditText)findViewById(R.id.edtName);
        email = (EditText)findViewById(R.id.edtEmail);
        password = (EditText)findViewById(R.id.edtPassword);
        account = (Button)findViewById(R.id.btnAccount);

        // Create on click listener for 'Create Account' button
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username != null && password != null && email != null) // add more test cases later
                    validate(email.getText().toString(), username.getText().toString(), password.getText().toString());

            }
        });

    }

    // Method - validate user and login
    private void validate(String Username, String Email, String Password) {
        try {
            User newUser = userInfoAccess.insertUser(Username, Email, Password);
            Intent intent = new Intent(CreateAccountActivity.this, LoggedActivity.class);
            startActivity(intent);
        }catch(UserException e){
            HandleUserExceptions handleUser = new HandleUserExceptions(e);
            handleUser.showMessage(this);
        }

    }

}