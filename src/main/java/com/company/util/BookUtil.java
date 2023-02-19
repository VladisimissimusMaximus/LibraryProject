package com.company.util;

import com.company.model.Book;
import com.company.util.exceptions.BookValidationException;
import com.company.util.exceptions.DuplicateFieldException;
import com.company.util.exceptions.OperationValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.company.util.ValidationUtil.*;

public class BookUtil {

    public static boolean checkNamesEquals(String name, String name1) {
        return Objects.equals(name, name1);
    }


    public static void validateName(Book book) {
        String name = book.getName();
        if (isEmpty(name)) {
            throw BookValidationException.withNameValidationCode("validation.NotEmpty");
        }
        if (isLongerThan(50, book.getName())) {
            throw BookValidationException.withNameValidationCode("validation.Length.long");
        }
    }

    public static void validateAuthor(Book book) {
        String author = book.getAuthor();

        if (isEmpty(author)) {
            throw BookValidationException.withAuthorValidationCode("validation.NotEmpty");
        }
        if (isLongerThan(50, book.getAuthor())) {
            throw BookValidationException.withAuthorValidationCode("validation.Length.long");
        }

    }

    public static void validatePublisher(Book book) {
        String publisher = book.getPublisher();
        if (isEmpty(publisher)) {
            throw BookValidationException.withPublisherValidationCode("validation.NotEmpty");
        }
        if (isLongerThan(50, book.getPublisher())) {
            throw BookValidationException.withPublisherValidationCode("validation.Length.long");
        }
    }

    public static void validatePublicationDate(Book book) {
        LocalDate publicationDate = book.getPublicationDate();
        if (isEmpty(publicationDate.toString())) {
            throw BookValidationException.withPublicationDateValidationCode("validation.NotEmpty");
        }
    }
    public static void validateCount(Book book){
        Integer count = book.getCount();
        if(!isNonNullInteger(count)){
            throw BookValidationException.withCountValidationCode("validation.number.valid");
        }
        if(count < 0){
            throw BookValidationException.withCountValidationCode("validation.number.positive");
        }
    }

    public static void handleDAOException(Runnable runnable) {
        try {
            runnable.run();
        } catch (DuplicateFieldException e) {
            throw BookValidationException.withDuplicationValidationCode("validation.book.duplicate");
        }
    }
}
