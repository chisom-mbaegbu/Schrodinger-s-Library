package comp3350.schrodingers.presentation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.business.AccessPurchasedBooks;
import comp3350.schrodingers.business.AccessShoppingCart;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.objects.User;

// Class - handles page for reviewing purchase
public class ReviewPurchaseActivity extends AppCompatActivity {

    // Access user info
    private AccessUserInfo userAccess;
    private User currUser;
    private User.Address userAddress;
    private User.Billing userBilling;

    // Personal info
    private String userName;
    private String email;

    // Address info
    private String address;
    private String postalCode;
    private String country;
    private String state;
    private String city;

    // Billing info
    private long creditCard;
    private String cardName;
    private int cvv;
    private String expiry;

    // Shopping Cart
    private AccessShoppingCart accessShoppingCart;

    // Books
    private AccessBooks accessBooks;

    // Purchase History
    private AccessPurchasedBooks accessPurchasedBooks;

    // Boolean
    private boolean missingUsername = false;
    private boolean missingEmail = false;
    private boolean missingAddress = false;
    private boolean missingPostal = false;
    private boolean missingCountry = false;
    private boolean missingState = false;
    private boolean missingCity = false;
    private boolean missingCardNo = false;
    private boolean missingCardName = false;
    private boolean missingCvv = false;
    private boolean missingExpiry = false;
    private boolean allInfoEntered = false;
    private boolean usingCart = false;

    // Misc
    private int totalCost;
    private List<Book> purchases;
    private Button enterAddress;
    private Button enterCredit;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        setContentView(R.layout.activity_review_purchase);

        // Set toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set menu title color
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        // Acquire information about user
        userAccess = Services.getUserInfoAccess();
        currUser = userAccess.getUser();

        userAddress = currUser.getAddress();
        userBilling = currUser.getBilling();

        // Acquire buttons
        enterAddress = (Button) findViewById(R.id.enterAddress);
        enterCredit = (Button) findViewById(R.id.enterCredit);
        Button checkout = (Button) findViewById(R.id.checkout);

        // AT FIRST - hide buttons
        enterAddress.setVisibility(View.INVISIBLE);
        enterCredit.setVisibility(View.INVISIBLE);

        // Acquire parameters passed to program
        Bundle extras = getIntent().getExtras();
        if(extras != null){

            // Display selected book for purchase
            int selectedBookID = Integer.parseInt(extras.getString("SELECTED_BOOK"));
            accessBooks = Services.getBookAccess();
            purchases = new ArrayList<Book>();
            purchases.add(accessBooks.searchBookById(selectedBookID));

            BookAdapter adapter = new BookAdapter(this, R.layout.item, purchases);
            ListView bookListView = (ListView)findViewById(R.id.ShoppingCartReview);
            bookListView.setAdapter(adapter);

            // Acquire cost
            totalCost = Integer.parseInt(purchases.get(0).getPrice().substring(1));

        } else {

            // Inform that shopping cart is being used
            usingCart = true;

            // Access shopping cart persistence
            accessShoppingCart = Services.getShoppingCartAccess();

            // Display shopping shopping cart
            try {
                purchases = accessShoppingCart.getBooks();
                BookAdapter adapter = new BookAdapter(this, R.layout.item, purchases);
                ListView bookListView = (ListView)findViewById(R.id.ShoppingCartReview);
                bookListView.setAdapter(adapter);

                // Acquire cost
                for(Book book : purchases){
                    totalCost += Integer.parseInt(book.getPrice().substring(1));
                }
            }catch(UserException e){
                Messages.warning(this, e.toString());
            }

        }

        displayInformation();

        // Button listeners
        // Update address info
        enterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewPurchaseActivity.this, PersonInfo.class);
                startActivityForResult(intent, 1);
            }
        });

        // Update payment info
        enterCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewPurchaseActivity.this, PaymentActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Complete checkout
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateCheckout();
            }
        });
    }

    private void displayInformation(){
        // Display information
        displayUserInfo();

        // Display button if missing info
        if(missingUsername || missingEmail || missingAddress || missingPostal || missingCountry || missingState || missingCity) {
            enterAddress.setVisibility(View.VISIBLE);
        }

        // Display button if missing info
        if(missingCardNo || missingCardName || missingCvv || missingExpiry) {
            enterCredit.setVisibility(View.VISIBLE);
        }
    }


    private void displayUserInfo() {
        // Display user name
        TextView review_userName = (TextView) findViewById(R.id.review_userName);
        if (currUser.noUserName()) {
            review_userName.setText("***Please Enter Username.***");
            missingUsername = true;
        } else {
            userName = currUser.getUserName();
            review_userName.setText(userName);
        }
        review_userName.setTextColor(Color.parseColor("#000000"));

        // Display email
        TextView review_email = (TextView) findViewById(R.id.review_email);
        if (currUser.noEmail()) {
            review_email.setText("***Please Enter Username.***");
            missingEmail = true;
        } else {
            email = currUser.getEmail();
            review_email.setText(email);
        }
        review_email.setTextColor(Color.parseColor("#000000"));

        // Display address
        TextView review_address = (TextView) findViewById(R.id.review_address);
        if (userAddress.noAddr()) {
            review_address.setText("***Please Enter Address.***");
            missingAddress = true;
        } else {
            address = userAddress.getAddress();
            review_address.setText(address);
        }
        review_address.setTextColor(Color.parseColor("#000000"));

        // Display postal code
        TextView review_postalCode = (TextView) findViewById(R.id.review_postalCode);
        if (userAddress.noPostal()) {
            review_postalCode.setText("***Please Enter Postal Code.***");
            missingPostal = true;
        } else {
            postalCode = userAddress.getPostalCode();
            review_postalCode.setText(postalCode);
        }
        review_postalCode.setTextColor(Color.parseColor("#000000"));

        // Display country
        TextView review_country = (TextView) findViewById(R.id.review_country);
        if (userAddress.noCountry()) {
            review_country.setText("***Please Enter Country.***");
            missingCountry = true;
        } else {
            country = userAddress.getCountry();
            review_country.setText(country);
        }
        review_country.setTextColor(Color.parseColor("#000000"));

        // Display state
        TextView review_state = (TextView) findViewById(R.id.review_state);
        if (userAddress.noState()) {
            review_state.setText("***Please Enter State.***");
            missingState = true;
        } else {
            state = userAddress.getState();
            review_state.setText(state);
        }
        review_state.setTextColor(Color.parseColor("#000000"));

        // Display city
        TextView review_city = (TextView) findViewById(R.id.review_city);
        if (userAddress.noCity()) {
            review_city.setText("***Please Enter City.***");
            missingCity = true;
        } else {
            city = userAddress.getCity();
            review_city.setText(city);
        }
        review_city.setTextColor(Color.parseColor("#000000"));

        // Display credit card number
        TextView review_cardNo = (TextView) findViewById(R.id.review_creditCard);
        if (userBilling.noCardNo()) {
            review_cardNo.setText("***Please Enter Credit Card.***");
            missingCardNo = true;
        } else {
            creditCard = userBilling.getCardNumber();
            review_cardNo.setText(Long.toString(creditCard));
        }
        review_cardNo.setTextColor(Color.parseColor("#000000"));

        // Display credit card name
        TextView review_cardName = (TextView) findViewById(R.id.review_cardName);
        if (userBilling.noCardName()) {
            review_cardName.setText("***Please Enter Credit Card.***");
            missingCardName = true;
        } else {
            cardName = userBilling.getFullName();
            review_cardName.setText(cardName);
        }
        review_cardName.setTextColor(Color.parseColor("#000000"));

        // Display credit card cvv
        TextView review_cvv = (TextView) findViewById(R.id.review_cvv);
        if (userBilling.noCvv()) {
            review_cvv.setText("***Please Enter CVV.***");
            missingCvv = true;
        } else {
            cvv = userBilling.getCvv();
            review_cvv.setText(Long.toString(cvv));
        }
        review_cvv.setTextColor(Color.parseColor("#000000"));


        // Display credit card expiry
        TextView review_expiry = (TextView) findViewById(R.id.review_expiry);
        if (userBilling.noExpiry()) {
            review_expiry.setText("***Please Enter Expiry.***");
            missingExpiry = true;
        } else {
            expiry = userBilling.getExpiry();
            review_expiry.setText(expiry);
        }
        review_expiry.setTextColor(Color.parseColor("#000000"));

        // Display subtotal
        TextView review_subtotal = (TextView) findViewById(R.id.subtotal);
        String subtotalString = "$" + String.valueOf(totalCost);
        review_subtotal.setText(subtotalString);
        review_subtotal.setTextColor(Color.parseColor("#000000"));
    }

    private void validateCheckout(){

        allInfoEntered = !missingUsername && !missingEmail && !missingAddress && !missingPostal
                && !missingCountry && !missingState && !missingCity && !missingCardNo && !missingCardName
                && !missingCvv && !missingExpiry;

        // Check that all info is entered
        if(allInfoEntered){

            ArrayList<Integer> purchaseIDs = new ArrayList<>();

            // Update purchase history and push purchases to next page
            try {
                accessPurchasedBooks = Services.getPurchasedBooksAccess();
                for(Book book : purchases) {
                    if(accessPurchasedBooks.insertBook(book))
                        purchaseIDs.add(book.getBookID());
                }

                // Empty shopping cart (if used)
                if(usingCart) {
                    accessShoppingCart.emptyCart();
                }

            } catch (UserException e){
                HandleUserExceptions exceptionHandler = new HandleUserExceptions(e);
                exceptionHandler.showMessage(this);
            }

            // Acquire next page and pass purchases
            if(purchaseIDs.size() != 0) {
                Intent intent = new Intent(ReviewPurchaseActivity.this, OrderCompletedActivity.class);
                intent.putIntegerArrayListExtra("purchaseIDs", purchaseIDs);
                startActivity(intent);
            }else
                Messages.warning(this, "It seems you already bought all the books of the list in the past!");

        } else {
            // Show snackbar/tool tip stating the user needs more info
            Snackbar cannotCheckout = Snackbar.make(findViewById(R.id.review_purchase), R.string.failedCheckout, Snackbar.LENGTH_LONG);
            cannotCheckout.getView().setBackgroundColor(ContextCompat.getColor(ReviewPurchaseActivity.this, R.color.colorPrimary));
            cannotCheckout.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            startActivity(getIntent());
            this.finish();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        currUser = userAccess.getUser();
        displayInformation();
    }


}
