package com.example.bookshopsystem;

import com.example.bookshopsystem.model.entities.Book;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BookService bookService1) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService1;
    }

    @Override
    public void run(String... args) throws Exception {
        seedDate();
        //printAllAfterYear(2000);
        //printAllAuthorsNamesWithReleaseDateBeforeYear(1990); is not correct ?
        //printAllAuthorsAndNumbersOfTheirBooks();
        //printAllBooksByAuthorNameOrderByReleaseDate("George","Powell");
    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName,String lastName) {
        bookService.findAllBooksByFirstNameLastNameOrderByReleaseDate(firstName, lastName)
        .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumbersOfTheirBooks() {
        authorService.getAllAuthorsAndNumbersOfTheirBooks()
        .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithReleaseDateBeforeYear(int year) {
        bookService.findAllAuthorsNamesWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllAfterYear(int year) {
        bookService.findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedDate() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
