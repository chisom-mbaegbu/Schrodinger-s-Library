package comp3350.schrodingers.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import comp3350.schrodingers.persistence.BooksPersistence;
import comp3350.schrodingers.objects.Book;

public class AccessBooks {

    private BooksPersistence booksPersistence;

    // Constructor - inject DB access
    public AccessBooks(BooksPersistence persistence) {
        booksPersistence = persistence;
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = booksPersistence.getAllBooks();
        return allBooks;
    }

    public Book searchBookById(int id) {
        List<Book> books = booksPersistence.getAllBooks();
        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            Book nextBook = bookIterator.next();  //holds the element to be compared to find the author
            if (nextBook.getBookID() == id) {
                return nextBook;
            }

        }//returns a books by the ID.
        return null;
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> authorBook = new ArrayList<>();
        List<Book> books = booksPersistence.getAllBooks();
        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            Book nextBook = bookIterator.next();  //holds the element to be compared to find the author
            if ((nextBook.getAuthor().toLowerCase()).contains(author)) {
                authorBook.add(nextBook);
            }

        }
        return authorBook; //returns a list of books by the author.
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> titleBook = new ArrayList<>();
        List<Book> books = booksPersistence.getAllBooks();
        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            Book nextBook = bookIterator.next();  //holds the element to be compared to find the title
            if ((nextBook.getBookName().toLowerCase()).contains(title)) {
                titleBook.add(nextBook);
            }

        }
        return titleBook; //returns a list of books by the title.
    }

    public List<Book> searchBookByGenre(String genre, int count) {// only used for recommendations
        List<Book> titleBook = new ArrayList<>();
        List<Book> books = booksPersistence.getAllBooks();
        Iterator<Book> bookIterator = books.iterator();
        int k=count;
        while (bookIterator.hasNext() && k>0) {
            Book nextBook = bookIterator.next();  //holds the element to be compared to find the title
            if (nextBook.getGenre().toLowerCase().compareTo(genre.toLowerCase()) == 0) {
                titleBook.add(nextBook);
                k--;
            }

        }
        return titleBook; //returns a list of books by the title, max of upto count no of books.
    }

    // Method - Filters search list when user enters search criteria
    public List<Book> filter(String query, List<Book> bookList, ArrayList<Book> arrayList) {

        List<Book> compareList = new <Book>ArrayList();
        query = query.toLowerCase().trim();
        bookList.clear();

        if (query.length() == 0) {
            bookList.addAll(arrayList);
        } else {

            List<Book> listbyTitle = searchBookByTitle(query);
            compareList.addAll(listbyTitle);
            List<Book> listbyAuthor = searchBookByAuthor(query);
            compareList.addAll(listbyAuthor);

            for (Book item : compareList) {

                if (!bookList.contains(item)) {
                    bookList.add(item);
                }
            }
        }
        return bookList;
    }

    public List<Book> sortByPrice(int direction){

        List <Book> unSortedList =  getAllBooks();

        if(direction == 1)
            Collections.sort(unSortedList,Book.bookPriceComparatorAsc);
        else
            Collections.sort(unSortedList,Book.bookPriceComparatorDsc);


        return unSortedList;

    }

    public List<Book> sortByName(int direction){

        List <Book> unSortedList =  getAllBooks();

        if(direction == 1)
            Collections.sort(unSortedList,Book.bookNameComparatorAsc);
        else
            Collections.sort(unSortedList,Book.bookNameComparatorDsc);


        return unSortedList;

    }

}
