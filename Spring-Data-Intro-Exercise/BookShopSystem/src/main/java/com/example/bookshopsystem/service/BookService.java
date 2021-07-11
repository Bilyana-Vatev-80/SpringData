package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsNamesWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByFirstNameLastNameOrderByReleaseDate(String firstName, String lastname);


}
