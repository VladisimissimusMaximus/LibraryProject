package com.company.util.exceptions;

public class BookValidationException extends RuntimeException {

    private String nameValidation;
    private String authorValidation;
    private String publisherValidation;
    private String publicationDateValidation;
    private String countValidation;

    public BookValidationException() {
        authorValidation = nameValidation = publisherValidation = publicationDateValidation = "";
    }

    public String getNameValidation() {
        return nameValidation;
    }

    public static BookValidationException withNameValidationCode(String nameValidationCode) {
        BookValidationException exception = new BookValidationException();
        exception.nameValidation = nameValidationCode;
        return exception;
    }

    public String getCountValidation(){ return countValidation;}

    public static BookValidationException withCountValidationCode(String countValidationCode){
        BookValidationException exception = new BookValidationException();
        exception.countValidation = countValidationCode;
        return exception;
    }

    public String getAuthorValidation() {
        return authorValidation;
    }

    public static BookValidationException withAuthorValidationCode(String authorValidationCode) {
        BookValidationException exception = new BookValidationException();
        exception.authorValidation = authorValidationCode;
        return exception;
    }

    public String getPublisherValidation() {
        return publisherValidation;
    }

    public static BookValidationException withPublisherValidationCode(String publisherValidation) {
        BookValidationException exception = new BookValidationException();
        exception.publisherValidation = publisherValidation;
        return exception;
    }

    public String getPublicationDateValidation() {
        return publicationDateValidation;
    }

    public static BookValidationException withPublicationDateValidationCode(String publicationDateValidation) {
        BookValidationException exception = new BookValidationException();
        exception.publicationDateValidation = publicationDateValidation;
        return exception;
    }

}
