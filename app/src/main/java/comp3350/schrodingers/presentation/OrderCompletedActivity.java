package comp3350.schrodingers.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.objects.Book;

public class OrderCompletedActivity extends AppCompatActivity {

    private AccessBooks bookAccess;
    private List<Book> purchases;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        setContentView(R.layout.activity_order_completed);

        // Set toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Set menu title color
        myToolbar.setTitleTextColor(0XFFFFFFFF);

        // Acquire buttons
        Button returnHome = (Button) findViewById(R.id.returnHome);

        // Acquire access to books list
        bookAccess = Services.getBookAccess();

        // Acquire parameter (ie list of purchased book IDs)
        ArrayList<Integer> purchaseIDs = getIntent().getIntegerArrayListExtra("purchaseIDs");

        // Initialize purchases array
        purchases = new ArrayList<>();

        // Reacquire list of purchase books
        for (Integer bookID : purchaseIDs){
            purchases.add(bookAccess.searchBookById(bookID));
        }

        // Display list of purchased books
        BookAdapter adapter = new BookAdapter(this, R.layout.item, purchases);
        ListView bookListView = (ListView)findViewById(R.id.purchasedBooks);
        bookListView.setAdapter(adapter);

        // Button listeners
        // Update return home
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderCompletedActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
