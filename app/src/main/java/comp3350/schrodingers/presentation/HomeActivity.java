package comp3350.schrodingers.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;

import comp3350.schrodingers.R;
import comp3350.schrodingers.application.Main;
import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.business.AccessBooks;
import comp3350.schrodingers.business.AccessUserInfo;
import comp3350.schrodingers.objects.User;
import comp3350.schrodingers.objects.Book;

// Class - hosts all activity and views located on the home/browse page
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Holds DB access for books
    AccessBooks bookList;
    List <Book> allBooks;

    // Holds DB access for user info
    private AccessUserInfo userList;
    private User user;

    // Layouts and adapters
    ListView searchLayout;
    GridView browseLayout;
    BookAdapter arrayAdapter;

    //Button
    private Button sortByPriceAsc;
    private Button sortByPriceDsc;
    private Button sortByNameAsc;
    private Button sortByNameDsc;

    private int greenColor;
    private int whiteColor;

    // Method - instantiates views when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate home layout
        setContentView(R.layout.activity_home);

        // Setup toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate navigation menu
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Instantiate DB within device
        copyDatabaseToDevice();

        // Instantiate user DB access and current user
        userList = Services.getUserInfoAccess();
        user = userList.getUser();

        // Create book list and adapter

        bookList = Services.getBookAccess();

        allBooks = bookList.getAllBooks();
        changeAdapter(R.layout.book_present);

        // Image Button (book catalog) Listeners
        browseLayout = (GridView)findViewById(R.id.browseView);
        createBookCatalogListeners(browseLayout);

        //sorting by price button
        sortByPriceAsc = (Button)findViewById(R.id.sortByPriceAsc);
        sortByPriceDsc = (Button)findViewById(R.id.sortByPriceDsc);
        sortByNameAsc = (Button)findViewById(R.id.sortByNameAsc);
        sortByNameDsc = (Button)findViewById(R.id.sortByNameDsc);

        greenColor = getResources().getColor(R.color.colorPrimary);
        whiteColor = Color.WHITE;
        sortByPriceAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBooks = bookList.sortByPrice(1);
                changeAdapter(R.layout.book_present);
                createBookCatalogListeners(browseLayout);
                resetButtonColor();
                sortByPriceAsc.setBackgroundColor(whiteColor);
                sortByPriceAsc.setTextColor(greenColor);

            }
        });

        sortByPriceDsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBooks = bookList.sortByPrice(2);
                changeAdapter(R.layout.book_present);
                createBookCatalogListeners(browseLayout);
                resetButtonColor();
                sortByPriceDsc.setBackgroundColor(whiteColor);
                sortByPriceDsc.setTextColor(greenColor);

            }
        });

        sortByNameAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBooks = bookList.sortByName(1);
                changeAdapter(R.layout.book_present);
                createBookCatalogListeners(browseLayout);
                resetButtonColor();
                sortByNameAsc.setBackgroundColor(whiteColor);
                sortByNameAsc.setTextColor(greenColor);

            }
        });

        sortByNameDsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBooks = bookList.sortByName(2);
                changeAdapter(R.layout.book_present);
                createBookCatalogListeners(browseLayout);
                resetButtonColor();
                sortByNameDsc.setBackgroundColor(whiteColor);
                sortByNameDsc.setTextColor(greenColor);
            }
        });

    }

    private void resetButtonColor(){

        sortByPriceAsc.setBackgroundColor(greenColor);
        sortByPriceAsc.setTextColor(whiteColor);
        sortByPriceDsc.setBackgroundColor(greenColor);
        sortByPriceDsc.setTextColor(whiteColor);
        sortByNameAsc.setBackgroundColor(greenColor);
        sortByNameAsc.setTextColor(whiteColor);
        sortByNameDsc.setBackgroundColor(greenColor);
        sortByNameDsc.setTextColor(whiteColor);
    }

    // Method - sets email and username within main menu
    private void updateDisplayUser() {

        // Acquire user currently logged in
        user = userList.getUser();

        // Acquire views on menu
        TextView userName = (TextView)findViewById(R.id.username);
        TextView userEmail = (TextView)findViewById(R.id.email);

        // Update views
        if (user != null) {
            userName.setText(user.getUserName());
            userEmail.setText(user.getEmail());
        }
    }

    public void changeAdapter(int id){
        arrayAdapter = new BookAdapter(this, id, allBooks);
    }

    // Method - creates top menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Sets current user details within menu
        updateDisplayUser();

        // Show search list (and hide browse list) when the search icon is selected
        MenuItem searchIcon = menu.findItem(R.id.action_search);

        searchIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                changeAdapter(R.layout.item);
                searchLayout = (ListView)findViewById(R.id.booklist);
                searchLayout.setAdapter(arrayAdapter);

                // Hide browse stuff while searching
                browseLayout.setVisibility(ScrollView.INVISIBLE);
                sortByPriceAsc.setVisibility(Button.INVISIBLE);
                sortByPriceDsc.setVisibility(Button.INVISIBLE);
                sortByNameAsc.setVisibility(Button.INVISIBLE);
                sortByNameDsc.setVisibility(Button.INVISIBLE);
                return true;
            }
        });

        // Perform book search and update search list
        SearchView searchView = (SearchView) searchIcon.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    arrayAdapter.filter("");
                    searchLayout.clearTextFilter();
                } else {
                    arrayAdapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    // Method - handles selection of menu options
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_account) {
            if (user != null) {
                Intent loggedIntent = new Intent(HomeActivity.this, LoggedActivity.class);
                HomeActivity.this.startActivity(loggedIntent);
            } else {
                Intent notLoggedIntent = new Intent(HomeActivity.this, NotLoggedActivity.class);
                HomeActivity.this.startActivity(notLoggedIntent);
            }
        }else if(id == R.id.purchase_history){
            if (user != null) {
                Intent loggedIntent = new Intent(HomeActivity.this, PurchaseHistoryActivity.class);
                HomeActivity.this.startActivity(loggedIntent);
            } else {
                Intent notLoggedIntent = new Intent(HomeActivity.this, NotLoggedActivity.class);
                HomeActivity.this.startActivity(notLoggedIntent);
            }
        }else if(id == R.id.my_wishlist){
            if (user != null) {
                Intent loggedIntent = new Intent(HomeActivity.this, WishlistActivity.class);
                HomeActivity.this.startActivity(loggedIntent);
            } else {
                Intent notLoggedIntent = new Intent(HomeActivity.this, NotLoggedActivity.class);
                HomeActivity.this.startActivity(notLoggedIntent);
            }
        }else if(id == R.id.my_shoppingCart){
            if (user != null) {
                Intent loggedIntent = new Intent(HomeActivity.this, ShoppingCartActivity.class);
                HomeActivity.this.startActivity(loggedIntent);
            } else {
                Intent notLoggedIntent = new Intent(HomeActivity.this, NotLoggedActivity.class);
                HomeActivity.this.startActivity(notLoggedIntent);
            }
        }else if(id == R.id.my_recommendations){
            if (user != null) {
                Intent loggedIntent = new Intent(HomeActivity.this, RecommendationsActivity.class);
                HomeActivity.this.startActivity(loggedIntent);
            } else {
                Intent notLoggedIntent = new Intent(HomeActivity.this, NotLoggedActivity.class);
                HomeActivity.this.startActivity(notLoggedIntent);
            }
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Method - ensure currently logged user is kept logged in upon resuming application
    @Override
    public void onResume() {
        super.onResume();
        user = userList.getUser();
    }

    // Method - create book Catalog button listeners
    public void createBookCatalogListeners(GridView browseLayout) {

        // Create image button listeners
        browseLayout.setAdapter(arrayAdapter);
        browseLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                int bookID = position;
                Context homeContext = HomeActivity.this;
                Class viewBookClass = ViewBookInfoActivity.class;

                Intent intent = new Intent(homeContext, viewBookClass);
                intent.putExtra("id", Integer.toString(bookID));
                startActivity(intent);
            }
        });

    }

    // Method - Initialize DB in the device
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    // Method - Copy SC.script to DB
    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
