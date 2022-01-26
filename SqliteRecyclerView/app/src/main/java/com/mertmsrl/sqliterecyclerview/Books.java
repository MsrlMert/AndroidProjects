package com.mertmsrl.sqliterecyclerview;

public class Books {
    private int id;
    private String bookName, bookAuthor;

    public Books(int id, String bookName, String bookAuthor) {
        this.id = id;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
