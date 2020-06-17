package comp3350.schrodingers.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.cardExceptions.CardException;
import comp3350.schrodingers.objects.User.Billing;
import comp3350.schrodingers.R;
import comp3350.schrodingers.business.AccessPaymentInfo;

// Class - handles the page for updating payment info
public class PaymentActivity extends AppCompatActivity {

    // Store access to DB and current credit card of logged user
    private AccessPaymentInfo accessCards;
    private Billing card;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_payment);

        // Setup toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up toolbar title color
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        // Instantiate access to DB and get current credit card
        accessCards = Services.getPaymentInfoAccess();
        card = accessCards.getCard();

        // Set presented information as stored user info (if user has already entered it)
        if (!card.noCardNo()) {
            Billing singleCard = card;
            EditText userBilling = (EditText)findViewById(R.id.billing);
            EditText expDate = (EditText)findViewById(R.id.expDate);
            EditText cvv = (EditText)findViewById(R.id.cvv);
            EditText cardName = (EditText)findViewById(R.id.cardName);
            userBilling.setText("" + singleCard.getCardNumber());
            expDate.setText(singleCard.getExpiry());
            cvv.setText("" + singleCard.getCvv());
            cardName.setText(singleCard.getFullName());
        }
    }

    // Method - insert card into DB upon button press
    public void buttonChangePayment(View v) {
        EditText editCardNum = (EditText)findViewById(R.id.billing);
        EditText editExpDate = (EditText)findViewById(R.id.expDate);
        EditText editCvv = (EditText)findViewById(R.id.cvv);
        EditText editCardName = (EditText)findViewById(R.id.cardName);

        long cn = 0L;
        if (editCardNum.getText().toString().length() != 0)
            cn = Long.parseLong(editCardNum.getText().toString());
        String exp = editExpDate.getText().toString();
        String name = editCardName.getText().toString();
        int cv = 0;
        if (editCvv.getText().toString().length() != 0)
            cv = Integer.parseInt(editCvv.getText().toString());
        Billing newCard = new Billing(cn, name, exp, cv);

        try {
            AccessUserInfo accessUser = Services.getUserInfoAccess();
            accessCards.insertCard(newCard, accessUser.getUser().getEmail());

            // Inform parent activity that info has been updated
            setResult(RESULT_OK, null);
            finish();

        } catch (CardException c) {
            HandleCardExceptions handleCard = new HandleCardExceptions(c);
            handleCard.showMessage(this);
        }
    }
}
