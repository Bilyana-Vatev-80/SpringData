package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.model.BookInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBookTitleWithAgaRestriction(AgeRestriction ageRestriction);

    List<String > findAllGoldenBooksTitlesWithCopiesLessThan5000();

    List<String> findAllBooksTitlesWithPriceLowerThan5AndGreaterThan40();

    List<String >findNotReleaseBookTitleLessInYear(int year);

    List<String> findAllReleasedBookBeforeDate(LocalDate year);

    List<String> findAllBookTitlesWhereContains(String strings);

    List<String> findAllTitleWithAuthorWithLastNameStartWith(String strings);

    int findCountOfBooksWithTitleLengthLongerThan(int titleLength);

    List<String> findBooksInfoByBookTitle(String bookTitle);

    int increaseCopiesByDate(LocalDate localDate, int copies);

    BookInfo findBookByTitle(String title);
}

