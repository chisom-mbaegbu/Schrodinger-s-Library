package comp3350.schrodingers.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.R;

// Class - handles the login page
public class LoginActivity extends AppCompatActivity {

    // Various views used by the page
    private Button login;
    private EditText email;
    private EditText password;
    private TextView info;

    // Store user DB access
    AccessUserInfo userInfoAccess = Services.getUserInfoAccess();

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_user_login);

        // Instantiate views
        email = (EditText)findViewById(R.id.edtEmail);
        password = (EditText)findViewById(R.id.edtPassword);
        login = (Button)findViewById(R.id.btnLogin);
        info = (TextView)findViewById(R.id.edtView);

        // Set onclick listener to the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(email.getText().toString(), password.getText().toString());

            }
        });

    }

    // Method - validate user info and change to preferences page
    private void validate(String Email, String Password) {

        User logUser = userInfoAccess.login(Email, Password);
        if (logUser != null) {
            Intent intent = new Intent(LoginActivity.this, LoggedActivity.class);
            startActivity(intent);
        } else {
            info.setText("Incorrect email or password, try again");
        }

    }

}