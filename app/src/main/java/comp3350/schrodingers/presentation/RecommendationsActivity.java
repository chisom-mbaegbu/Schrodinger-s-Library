package comp3350.schrodingers.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.business.AccessBooks;

import comp3350.schrodingers.R;

public class RecommendationsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private Spinner Choice;
    private Button select;
    private AccessBooks browser = Services.getBookAccess();
    private String option = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        Choice = (Spinner) findViewById(R.id.spinner_genre);
        Choice.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Choice.setAdapter(adapter);

        select = (Button)findViewById(R.id.confirmGenre);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(option);

            }
        });

    }

    private void validate(String choice) {
        List<Book> recs = browser.searchBookByGenre(choice,3);
        BookAdapter adapter = new BookAdapter(this, R.layout.item, recs);
        ListView bookListView = (ListView)findViewById(R.id.recView);
        bookListView.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        option = arg0.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}