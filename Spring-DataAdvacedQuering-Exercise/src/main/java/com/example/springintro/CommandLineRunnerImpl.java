package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import com.example.springintro.service.model.BookInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
        //  printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
     //   printAllAuthorsAndNumberOfTheirBooks();
       // printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        System.out.println("Please enter exercise: ");
        int exerciseNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exerciseNumber){
            case 1 -> booksTitlesByAgeRestriction();
            case 2 -> goldenBooks();
            case 3 -> booksByPrice();
            case 4 -> notReleasedBooks();
            case 5 -> booksReleasedBeforeDate();
            case 6 -> authorSearch();
            case 7 -> booksSearch();
            case 8 -> bookTitlesSearch();
            case 9 -> countBooks();
            case 10 -> totalBookCopies();
            case 11 -> reducedBook();
            case 12 -> increaseBookCopies();
        }

    }

    private void increaseBookCopies() throws IOException {
        System.out.println("Please enter date format:");
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));
        System.out.println("Please enter copies: ");
        int copies = Integer.parseInt(bufferedReader.readLine());

        bookService.increaseCopiesByDate(localDate,copies);

        System.out.println(bookService.increaseCopiesByDate(localDate,copies));
    }

    private void reducedBook() throws IOException {
        System.out.println("Please enter book title: ");
        String title = bufferedReader.readLine();
        BookInfo bookByTitle = bookService.findBookByTitle(title);
        System.out.printf("%s %s %s %.2f%n", bookByTitle.getTitle(),
                bookByTitle.getEditionType().name(),bookByTitle.getAgeRestriction().name(),
                bookByTitle.getPrice());
    }

    private void totalBookCopies() {
        authorService.findAllAuthorsAndTheirTotalCopies()
        .forEach(System.out::println);
    }

    private void countBooks() throws IOException {
        System.out.println("Please enter title length: ");
        int titleLength = Integer.parseInt(bufferedReader.readLine());

        System.out.println(bookService.findCountOfBooksWithTitleLengthLongerThan(titleLength));


    }

    private void bookTitlesSearch() throws IOException {
        System.out.println("Please enter Author lastname starts with:");
        String strings = bufferedReader.readLine();

        bookService.findAllTitleWithAuthorWithLastNameStartWith(strings)
        .forEach(System.out::println);
    }

    private void booksSearch() throws IOException {
        System.out.println("Please enter containing strings: ");
        String strings = bufferedReader.readLine();

        bookService.findAllBookTitlesWhereContains(strings)
        .forEach(System.out::println);
    }

    private void authorSearch() throws IOException {
        System.out.println("Please enter first name ends with strings: ");
        String strings = bufferedReader.readLine();

        authorService.findAuthorFirstNameEndsWith(strings).forEach(System.out::println);
    }

    private void booksReleasedBeforeDate() throws IOException {
        System.out.println("Please enter date in format dd-MM-yyyy: ");
        LocalDate year = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllReleasedBookBeforeDate(year).forEach(System.out::println);
    }

    private void notReleasedBooks() throws IOException {
        System.out.println("Please enter year: ");
        int year = Integer.parseInt(bufferedReader.readLine());

        bookService.findNotReleaseBookTitleLessInYear(year).forEach(System.out::println);
    }

    private void booksByPrice() {
        bookService.findAllBooksTitlesWithPriceLowerThan5AndGreaterThan40()
        .forEach(System.out::println);
    }

    private void goldenBooks() {
        bookService.findAllGoldenBooksTitlesWithCopiesLessThan5000()
        .forEach(System.out::println);
    }

    private void booksTitlesByAgeRestriction() throws IOException {
        System.out.println("Please enter ageRestriction: ");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase(Locale.ROOT));

        bookService.findAllBookTitleWithAgaRestriction(ageRestriction).forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
