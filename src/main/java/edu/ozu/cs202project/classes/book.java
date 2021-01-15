package edu.ozu.cs202project.classes;

import java.util.Date;

public class book
{
    public Integer book_id;
    public String title;
    public Date  publication_date;
    public String author_name;
    public String publisher;
    public String genre;
    public String topics;
    public Boolean is_avaliable;
    public Boolean is_borrowed;
    public Integer borrow_count;
    public Boolean is_held;
    public Integer held_user;
    public double penalty;



    public book(Integer id, String title, Date publication, String author, String publisher, String genre, String topics, Boolean borrowed, Boolean held, Integer huser, Integer bcount, Double pen, Boolean isaval) {
        this.book_id = id;
        this.title=title;
        this.publication_date=publication;
        this.author_name=author;
        this.publisher=publisher;
        this.genre=genre;
        this.topics = topics;
        this.is_borrowed = borrowed;
        this.is_held = held;
        this.held_user = huser;
        this.borrow_count = bcount;
        this.penalty = pen;
        this.is_avaliable = isaval;
    }


    public void setTitle(String title)
    {
        this.title=title;
    }

    public String toString()
    {
        return "The details of the book are: " + title;
    }



}