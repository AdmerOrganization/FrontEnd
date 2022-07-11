package com.example.tolearn.Entity;

import junit.framework.TestCase;

public class MovieTest extends TestCase {

    public void testGetTitle() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        if(movie.getTitle() == "text1"){
            result = true;
        }
        assertTrue(result);
    }

    public void testSetTitle() {
        Movie movie = new Movie("text2", "text2", "text3", "text4");
        boolean result = false;
        movie.setTitle("text1");
        if(movie.getTitle() == "text1"){
            result = true;
        }
        assertTrue(result);
    }

    public void testGetRating() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        if(movie.getRating() == "text2"){
            result = true;
        }
        assertTrue(result);
    }

    public void testSetRating() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        movie.setRating("text5");
        if(movie.getRating() == "text5"){
            result = true;
        }
        assertTrue(result);
    }

    public void testGetYear() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        if(movie.getYear() == "text3"){
            result = true;
        }
        assertTrue(result);
    }

    public void testSetYear() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        movie.setYear("text6");
        if(movie.getYear() == "text6"){
            result = true;
        }
        assertTrue(result);
    }

    public void testGetPlot() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        if(movie.getPlot() == "text4"){
            result = true;
        }
        assertTrue(result);
    }

    public void testSetPlot() {
        Movie movie = new Movie("text1", "text2", "text3", "text4");
        boolean result = false;
        movie.setPlot("text7");
        if(movie.getPlot() == "text7"){
            result = true;
        }
        assertTrue(result);
    }

    public void testIsExpanded() {
    }

    public void testSetExpanded() {
    }

    public void testTestToString() {
    }
}