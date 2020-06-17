package comp3350.schrodingers.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessWishlist;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;

// Class - handles presenting user wishlist
public class WishlistActivity extends AppCompatActivity {
    private AccessWishlist accessWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_wishlist);

          // Set up tool bar
          Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
          setSupportActionBar(myToolbar);
          myToolbar.setTitleTextColor(0XFFFFFFFF);

          // Access wishlist persistence
          accessWishlist = Services.getWishlistAccess();

          // Display wish list
        try {
            List<Book> list = accessWishlist.getBooks();
            BookAdapter adapter = new BookAdapter(this, R.layout.item, list);
            ListView bookListView = (ListView)findViewById(R.id.wishList);
            bookListView.setAdapter(adapter);
        }catch(UserException e){
            HandleUserExceptions handleUser = new HandleUserExceptions(e);
            handleUser.showMessage(this);
        }

    }

}
