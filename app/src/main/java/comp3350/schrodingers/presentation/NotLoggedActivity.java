package comp3350.schrodingers.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import comp3350.schrodingers.R;

// Class - handles the page which asks a user to login or create account
public class NotLoggedActivity extends AppCompatActivity {

    // Buttons for login or create account
    private Button Login;
    private Button Account;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_not_logged);

        // Instantiate views
        Login = (Button) findViewById(R.id.login_button);
        Account = (Button) findViewById(R.id.account_button);

        // Onclick listener for the login button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotLoggedActivity.this, LoginActivity.class);
                NotLoggedActivity.this.startActivity(intent);

            }
        });

        // Onclick listener for the create account button
        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotLoggedActivity.this, CreateAccountActivity.class);
                NotLoggedActivity.this.startActivity(intent);
            }
        });
    }

}