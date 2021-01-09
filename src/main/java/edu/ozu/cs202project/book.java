package edu.ozu.cs202project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class book
{
    public Integer book_id;
    public String title;
    public Date  publication_date;
    public String author_name;
    public Integer publisher;
    public String genre;
    public String topics;
    public Boolean is_avaliable;
    public Boolean is_borrowed;
    public Integer borrow_count;
    public Integer last_borrow;
    public Boolean is_held;
    public Integer held_user;
    public double penalty;



    public book(Integer id, String title, Date publication, String author, Integer publisher, String genre, String topics, Boolean borrowed, Boolean held) {
        this.book_id = id;
        this.title=title;
        this.publication_date=publication;
        this.author_name=author;
        this.publisher=publisher;
        this.genre=genre;
        this.topics = topics;
        this.is_borrowed = borrowed;
        this.is_held = held;
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