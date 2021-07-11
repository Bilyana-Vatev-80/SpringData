package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import com.example.springintro.service.model.BookInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "C:\\Spring Data\\Spring-DataAdvacedQuering-Exercise\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookTitleWithAgaRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGoldenBooksTitlesWithCopiesLessThan5000() {
        return bookRepository.findAllByEditionTypeAndCopiesIsLessThan(EditionType.GOLD,5000)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksTitlesWithPriceLowerThan5AndGreaterThan40() {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5L), BigDecimal.valueOf(40L))
                .stream()
                .map(book -> String.format("%s - $%.2f", book.getTitle(),book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findNotReleaseBookTitleLessInYear(int year) {
        LocalDate lower = LocalDate.of(year, 1, 1);
        LocalDate upper = LocalDate.of(year, 12, 31);

        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(lower, upper)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllReleasedBookBeforeDate(LocalDate year) {
        return bookRepository.findAllByReleaseDateBefore(year)
                .stream()
                .map(book -> String.format("%s %s %.2f", book.getTitle(),
                        book.getEditionType().name(),book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookTitlesWhereContains(String strings) {

        return bookRepository.findAllByTitleContaining(strings)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllTitleWithAuthorWithLastNameStartWith(String strings) {
        return bookRepository.findAllByAuthor_LastNameStartsWith(strings)
                .stream()
                .map(book -> String.format("%s (%s %s)", book.getTitle(),
                        book.getAuthor().getFirstName(),book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int findCountOfBooksWithTitleLengthLongerThan(int titleLength) {
        return bookRepository.countOfBooksWithTitleLengthMoreThan(titleLength);
    }

    @Override
    public List<String> findBooksInfoByBookTitle(String bookTitle) {
        return null;
    }


    @Override
    @Transactional
    public int increaseCopiesByDate(LocalDate localDate, int copies) {
        int effectedRow = bookRepository.updateCopiesByRealiseDate(copies, localDate);
        return effectedRow * copies;
    }

    @Override
    public BookInfo findBookByTitle(String title) {
        Book byTitle = this.bookRepository.findByTitle(title);
        BookInfo bookInfo = new BookInfo(byTitle.getTitle(), byTitle.getEditionType(),
                byTitle.getAgeRestriction(), byTitle.getPrice());

        return bookInfo;
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
