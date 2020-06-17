package comp3350.schrodingers.business;

import comp3350.schrodingers.objects.Book;

public class BookBuilder {

    private Book tempBook;

    public BookBuilder() {
        tempBook = new Book();
    }

    // Builder (ie setter) methods
    public BookBuilder id (int bookID) {
        tempBook.setBookID(bookID);
        return this;
    }

    public BookBuilder name (String bookName) {
        tempBook.setBookName(bookName);
        return this;
    }

    public BookBuilder author (String author) {
        tempBook.setAuthor(author);
        return this;
    }

    public BookBuilder price (String price) {
        tempBook.setPrice(price);
        return this;
    }

    public BookBuilder genre (String genre) {
        tempBook.setGenre(genre);
        return this;
    }

    public BookBuilder stock (String bookStock) {
        tempBook.setBookStock(bookStock);
        return this;
    }

    public BookBuilder icon (String iconId) {
        tempBook.setIconId(iconId);
        return this;
    }

    public Book buildBook (){
        return tempBook;
    }

}
