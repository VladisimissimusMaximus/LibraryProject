package com.company.service;

import com.company.dao.BookDAO;
import com.company.model.Book;
import com.company.util.BookUtil;
import com.company.util.exceptions.BookValidationException;
import com.company.util.selection.Filter;
import com.company.util.selection.Paging;
import com.company.util.selection.SelectionOptions;

import java.util.List;

public class BookService {
    public static final BookDAO dao = BookDAO.getInstance();

    public List<Book> getAll(SelectionOptions options){
        return dao.findAll(options);
    }

    public void deleteById(Integer id){
        dao.delete(id);
    }

    public Book getById(Integer id){
        return dao.findById(id);
    }

    public Book getByName(String name) {return dao.findByName(name);}

    public void update(Book book){
        BookUtil.validateName(book);
        BookUtil.validateAuthor(book);
        BookUtil.validatePublisher(book);
        BookUtil.validatePublicationDate(book);
        BookUtil.validateCount(book);

        BookUtil.handleDAOException(() -> dao.update(book));
    }

    public void create(Book book) throws BookValidationException{
        BookUtil.validateName(book);
        BookUtil.validateAuthor(book);
        BookUtil.validatePublisher(book);
        BookUtil.validatePublicationDate(book);
        BookUtil.validateCount(book);

        BookUtil.handleDAOException(() -> dao.insert(book));
    }

    public int getCount(Filter filter) {
        return filter != null
                ? dao.findCount(filter)
                : dao.findCount();
    }
}
