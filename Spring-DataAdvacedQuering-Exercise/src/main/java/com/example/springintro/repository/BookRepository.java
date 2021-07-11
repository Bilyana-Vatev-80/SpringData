package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal greaterPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

    List<Book> findAllByTitleContaining(String title);

    List<Book> findAllByAuthor_LastNameStartsWith(String strings);

    @Query("select count(b) FROM Book b where length(b.title) > :param ")
    int countOfBooksWithTitleLengthMoreThan(@Param(value = "param") int titleLength);

    @Modifying
    @Query("UPDATE Book b SET b.copies = b.copies + : copies where b.releaseDate > : date")
    int updateCopiesByRealiseDate(@Param(value = "copies") int copies,
                                  @Param(value = "date") LocalDate date);

    Book findByTitle(String title);
}

