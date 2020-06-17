package comp3350.schrodingers.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessPurchasedBooks;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;

// Class - presents purchase history info from DB
public class PurchaseHistoryActivity extends AppCompatActivity {

    // Holds access to purchase history in DB
    private AccessPurchasedBooks accessPurchased;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_purchase_history);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);

        // Setup toolbar
        setSupportActionBar(myToolbar);

        // Set up toolbar title color
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        try {
            accessPurchased = Services.getPurchasedBooksAccess();
            List<Book> list = accessPurchased.getBooks();
            BookAdapter adapter = new BookAdapter(this, R.layout.item, list);
            ListView bookListView = (ListView)findViewById(R.id.list_purchased);
            bookListView.setAdapter(adapter);
        }catch (UserException e){
            HandleUserExceptions handleUser = new HandleUserExceptions(e);
            handleUser.showMessage(this);
        }

    }
}
