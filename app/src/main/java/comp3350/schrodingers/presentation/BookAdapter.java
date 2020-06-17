package comp3350.schrodingers.presentation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import comp3350.schrodingers.business.Services;
import comp3350.schrodingers.objects.Book;
import comp3350.schrodingers.R;
import comp3350.schrodingers.business.AccessBooks;

import java.util.ArrayList;
import java.util.List;

// Class - handles changing book information into something viewable
public class BookAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Book> bookList;
    private ArrayList<Book> arrayList;
    private AccessBooks bookAccess;
    private int layoutID;

    // Constructor - passed in a list of books
    public BookAdapter(Context context, int layId, List<Book> list) {

        this.context = context;
        this.bookList = list;

        this.bookAccess = Services.getBookAccess();

        inflater = LayoutInflater.from(this.context);
        arrayList = new ArrayList<>();
        this.arrayList.addAll(bookList);
        layoutID = layId;
    }

    // Method - holds
    public class ViewHolder {
        TextView title, author;
        ImageView icon;
    }

    // Method - return the size of the booklist
    @Override
    public int getCount() {
        return bookList.size();
    }

    // Method - return book based on selected position
    @Override
    public Book getItem(int position) {
        return bookList.get(position);
    }

    // Method - returns bookID of selected book
    @Override
    public long getItemId(int position) {
        return bookList.get(position).getBookID();
    }

    // Method - instantiate views using book information
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(layoutID, null);

            //
            holder.title = (TextView)convertView.findViewById(R.id.bookTitle);
            holder.author = (TextView)convertView.findViewById(R.id.bookAuthor);
            holder.icon = (ImageView)convertView.findViewById(R.id.bookIcon);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        // Set book title and author
        holder.title.setText(bookList.get(position).getBookName());
        holder.author.setText(bookList.get(position).getAuthor());

        // Acquire icon/picture and set as relevant picture
        int iconID = -1;
        try {
            iconID = R.drawable.class.getField(getItem(position).getIconId()).getInt(null);
        } catch (Exception e) {
            // DON'T CARE/UNHANDLABLE - image resources must reside in drawable
            Log.d("BookAdapterException", "Failure to get drawable id.", e);
        }

        holder.icon.setImageResource(iconID);

        // onClick listener for ViewBookInfoActivity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewBookInfoActivity.class);
                intent.putExtra("id", Integer.toString(bookList.get(position).getBookID()));
                context.startActivity(intent);
            }
        });

        return convertView;
    } // end of getView

    // Method - Filters search list when user enters search criteria
    public void filter(String query) {

        // Filter book list
        bookAccess.filter(query, bookList, arrayList);

        // Update booklist with filter changes
        notifyDataSetChanged();
    }
}
