package comp3350.schrodingers.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessShoppingCart;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;

// Class - handles presenting shopping cart
public class ShoppingCartActivity extends AppCompatActivity {

    // Store access to persistence
    private AccessShoppingCart accessShoppingCart;

    // Checkout button
    private Button checkout;
    private Button emptyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        // Set up tool bar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        // Access shoppingcart persistence
        accessShoppingCart = Services.getShoppingCartAccess();

        // Display shopping cart
        try {
            List<Book> list = accessShoppingCart.getBooks();
            BookAdapter adapter = new BookAdapter(this, R.layout.item, list);
            ListView bookListView = (ListView)findViewById(R.id.shoppingCart);
            bookListView.setAdapter(adapter);
        }catch(UserException e){
            HandleUserExceptions handleUser = new HandleUserExceptions(e);
            handleUser.showMessage(this);
        }

        // Checkout
        emptyCart = (Button) findViewById(R.id.emptyCart);
        emptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    // Empty shopping cart
                    accessShoppingCart.emptyCart();

                    // Restart activity
                    finish();
                    startActivity(getIntent());

                } catch (UserException e){
                    HandleUserExceptions exceptionHandler = new HandleUserExceptions(e);
                    exceptionHandler.showMessage(ShoppingCartActivity.this);
                }
            }

        });


        // Checkout
        checkout = (Button) findViewById(R.id.proceedCheckout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context homeContext = ShoppingCartActivity.this;
                Class purchaseBookClass = ReviewPurchaseActivity.class;

                Intent intent = new Intent(homeContext, purchaseBookClass);
                startActivity(intent);

            }

        });

    }

}
