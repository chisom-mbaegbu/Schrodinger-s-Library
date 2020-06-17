package comp3350.schrodingers.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.business.AccessWishlist;
import comp3350.schrodingers.business.AccessShoppingCart;
import comp3350.schrodingers.business.AccessRatings;
import comp3350.schrodingers.business.userExceptions.NotLoggedException;
import comp3350.schrodingers.business.userExceptions.UserException;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.objects.Ratings;
import comp3350.schrodingers.objects.User;

// Class - shows all book information and facilitates options like
// rating, purchasing, or reviewing
public class ViewBookInfoActivity extends AppCompatActivity {

    // Various info displayed when viewing book
    private Button addWishlist;
    private Button viewWishlist;
    private Button rateButton;
    private Button purchaseButton;
    private Button addToCart;
    private Button viewCart;
    private RatingBar ratingBar;
    private ListView viewRateList;
    private ArrayAdapter<Ratings> rateAdapter;
    private List<Ratings> ratings;
    private EditText review;
    private User user;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associate layout
        setContentView(R.layout.activity_view_book_info);

        // Setup toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Acquire passed parameter (id bookID)
        Intent intent = getIntent();
        String str_id = intent.getStringExtra("id");
        final int int_id = Integer.parseInt(str_id);

        // Instantiate buttons
        addWishlist = (Button) findViewById(R.id.addButton);
        viewWishlist = (Button) findViewById(R.id.viewButton);
        rateButton = (Button)findViewById(R.id.submitRate);
        purchaseButton = (Button) findViewById(R.id.purchaseButton);
        addToCart = (Button) findViewById(R.id.addCartButton);
        viewCart = (Button) findViewById(R.id.viewCartButton);
        
        // Instantiate access to book persistence
        final AccessBooks bookList = Services.getBookAccess();
        List<String> list = getBookDetails(bookList, int_id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        // Instantiate access to ratings
        final AccessRatings accessRatings = Services.getRatingsAccess();

        // Set book information and image
        ListView viewbookList = (ListView)findViewById(R.id.bookDetail);
        viewbookList.setAdapter(arrayAdapter);

        ImageView bookImage = (ImageView)findViewById(R.id.bookImage);
        String imageName = bookList.searchBookById(int_id).getIconId();

        int iconID = -1;
        try {
            iconID = R.drawable.class.getField(imageName).getInt(null);
        } catch (Exception e) {
            System.out.println("Cannot find drawable");
        }

        bookImage.setImageResource(iconID);
        final AccessUserInfo userInfo = Services.getUserInfoAccess();
        user = userInfo.getUser();
        // Purchase
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                if(user != null) {
                    Context homeContext = ViewBookInfoActivity.this;
                    Class purchaseBookClass = ReviewPurchaseActivity.class;

                    Intent intent = new Intent(homeContext, purchaseBookClass);
                    String bookID = Integer.toString(int_id);
                    intent.putExtra("SELECTED_BOOK", bookID);
                    startActivity(intent);
                }else
                    showMessage(new NotLoggedException());
            }

        });

        // Shopping Cart
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewShoppingCart();
            }
        });
        
        addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(user != null) {
                        if (insertShoppingCart(bookList.searchBookById(int_id))) {

                            // Show snackbar/tool tip confirming selection has been added
                            Snackbar addedToCart = Snackbar.make(findViewById(R.id.viewBookLayout), R.string.addedToCart, Snackbar.LENGTH_LONG);
                            addedToCart.getView().setBackgroundColor(ContextCompat.getColor(ViewBookInfoActivity.this, R.color.colorPrimary));
                            addedToCart.show();

                        } else {

                            // Show snackbar/tool tip confirming selection already exits (cannot be added)
                            Snackbar alreadyAdded = Snackbar.make(findViewById(R.id.viewBookLayout), R.string.alreadyAddedToCart, Snackbar.LENGTH_LONG);
                            alreadyAdded.getView().setBackgroundColor(ContextCompat.getColor(ViewBookInfoActivity.this, R.color.colorPrimary));
                            alreadyAdded.show();

                        }
                    }else
                        showMessage(new NotLoggedException());
                }
            });
        
        // Ratings Bar
        ratingBar = (RatingBar)findViewById(R.id.bookRatingBar);
        viewRateList = (ListView)findViewById(R.id.ratings);
        ratings = accessRatings.findRatingsByBook(int_id);
        rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ratings);
        viewRateList.setAdapter(rateAdapter);

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(user != null) {
                        if (accessRatings.getRatingsByUser(int_id, user.getEmail()).getRate() == -1) {
                            accessRatings.addRating(int_id, (int) ratingBar.getRating(), review.getText().toString());
                            ratings = accessRatings.findRatingsByBook(int_id);
                            rateAdapter.add(ratings.get(ratings.size() - 1));
                            Snackbar addedRating = Snackbar.make(findViewById(R.id.viewBookLayout), "Your Ratings and Review has been added", Snackbar.LENGTH_LONG);
                            addedRating.getView().setBackgroundColor(ContextCompat.getColor(ViewBookInfoActivity.this, R.color.colorPrimary));
                            addedRating.show();
                        } else {
                            Snackbar notAddedRating = Snackbar.make(findViewById(R.id.viewBookLayout), "You Already Rated and Reviewed This Book", Snackbar.LENGTH_LONG);
                            notAddedRating.getView().setBackgroundColor(ContextCompat.getColor(ViewBookInfoActivity.this, R.color.colorPrimary));
                            notAddedRating.show();
                        }
                    }else
                        showMessage(new NotLoggedException());
                }catch(UserException e){
                    showMessage(e);
                }
            }
        });

        // Review
        review = (EditText)findViewById(R.id.review);

        // Wishlist
        viewWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewWishlist();
            }
        });

        addWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null) {
                    if (insertWishlist(bookList.searchBookById(int_id))) {

                        // Show snackbar/tool tip confirming selection has been added
                        Snackbar addedToWishlist = Snackbar.make(findViewById(R.id.viewBookLayout), R.string.wishAdded, Snackbar.LENGTH_LONG);
                        addedToWishlist.getView().setBackgroundColor(ContextCompat.getColor(ViewBookInfoActivity.this, R.color.colorPrimary));
                        addedToWishlist.show();

                    } else {

                        // Show snackbar/tool tip confirming selection already exits (cannot be added)
                        Snackbar alreadyAdded = Snackbar.make(findViewById(R.id.viewBookLayout), R.string.wishAlreadyAdded, Snackbar.LENGTH_LONG);
                        alreadyAdded.getView().setBackgroundColor(ContextCompat.getColor(ViewBookInfoActivity.this, R.color.colorPrimary));
                        alreadyAdded.show();

                    }
                }else
                    showMessage(new NotLoggedException());
            }
        });
    }

    private void showMessage(UserException e){
        HandleUserExceptions handleUser = new HandleUserExceptions(e);
        handleUser.showMessage(this);
    }

    // Method - acquire details of selected book
    public List<String> getBookDetails(AccessBooks accessBooks, int id) {

        Book book = accessBooks.searchBookById(id);
        List<String> bookInfo = new ArrayList<>();
        bookInfo.add("Book ID : " + book.getBookID());
        bookInfo.add("Book Title : " + book.getBookName());
        bookInfo.add("Book Author : " + book.getAuthor());
        bookInfo.add("Book Price : " + book.getPrice());
        bookInfo.add("Book Genre : " + book.getGenre());
        bookInfo.add("Book Left In Stock : " + book.getBookStock());
        return bookInfo;
    }

    private void viewWishlist() {
        if(user != null) {
            Intent intent = new Intent(ViewBookInfoActivity.this, WishlistActivity.class);
            startActivity(intent);
        }else
            showMessage(new NotLoggedException());
    }

    private boolean insertWishlist(Book book){
        AccessWishlist wishlist = Services.getWishlistAccess();
        try {
            return wishlist.insertBook(book);
        }catch(UserException e){
            showMessage(e);
            return false;
        }

    }

    private void viewShoppingCart() {
        if(user != null) {
            Intent intent = new Intent(ViewBookInfoActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        }else{
            showMessage(new NotLoggedException());
        }
    }

    private boolean insertShoppingCart(Book book){
        AccessShoppingCart shoppingCart = Services.getShoppingCartAccess();
        try {
            return shoppingCart.insertBook(book);
        }catch(UserException e){
            showMessage(e);
            return false;
        }

    }
}
