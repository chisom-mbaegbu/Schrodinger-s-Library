package comp3350.schrodingers.objects;
import java.util.Comparator;


public class Book {

    private int bookID = -1;
    private String bookName = null;
    private String author= null;
    private String price= null;
    private String genre= null;
    private String bookStock= null;
    private String iconId= null;

    // constructor
    public Book() {}


    // Setter methods
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBookStock(String bookStock) {
        this.bookStock = bookStock;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;

    }


    // Getter methods
    public int getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    public String getBookStock() {
        return bookStock;
    }

    public String getIconId() {
        return iconId;
    }



    public static Comparator<Book> bookPriceComparatorAsc = new Comparator<Book>() {

        public int compare(Book b1, Book b2) {
            String book1Price = b1.getPrice();
            String book2Price = b2.getPrice();

            int price1 = Integer.parseInt(book1Price.substring(1));
            int price2 = Integer.parseInt(book2Price.substring(1));

            if (price1 > price2)
                return 1;
            else
                return -1;
        }
    };

    public static Comparator<Book> bookPriceComparatorDsc = new Comparator<Book>() {

        public int compare(Book b1, Book b2) {
            String book1Price = b1.getPrice();
            String book2Price = b2.getPrice();

            int price1 = Integer.parseInt(book1Price.substring(1));
            int price2 = Integer.parseInt(book2Price.substring(1));

            if (price1 < price2)
                return 1;
            else
                return -1;
        }
    };
    public static Comparator<Book> bookNameComparatorAsc = new Comparator<Book>() {

        public int compare(Book b1, Book b2) {
            String book1name = b1.getBookName();
            String book2name = b2.getBookName();


            return book1name.compareToIgnoreCase(book2name);
        }
    };

    public static Comparator<Book> bookNameComparatorDsc = new Comparator<Book>() {

        public int compare(Book b1, Book b2) {
            String book1name = b1.getBookName();
            String book2name = b2.getBookName();


            return book2name.compareToIgnoreCase(book1name);
        }
    };

    @Override
    public String toString() {
        return "Book{" +

                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", genre='" + genre + '\'' +
                ", bookStock='" + bookStock + '\'' +
                ", iconId='" + iconId + '\'' +
                '}';
    }
}
