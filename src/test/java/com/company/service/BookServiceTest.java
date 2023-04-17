package com.company.service;

import com.company.dao.BookDAO;
import com.company.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @Captor
    ArgumentCaptor<Integer> intCaptor;

    @Mock
    BookDAO mockDAO;

    @InjectMocks
    BookService service;

    @Test
    public void getAll_whenDAOReturnsList_returnTheSame() {
        // given
        Book book1 = new Book();
        book1.setName("a");
        Book book2 = new Book();
        book2.setName("b");
        List<Book> expected = List.of(book1, book2);

        // when
        when(mockDAO.findAll(null)).thenReturn(expected);
        List<Book> actual = service.getAll(null);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_whenProvidedBookId_DAOReturnsSameOne() {
        // given
        Book book = new Book();
        int id = 1;
        book.setId(id);

        // when
        when(mockDAO.delete(id)).thenReturn(true);
        service.deleteById(book.getId());

        // then
        verify(mockDAO).delete(intCaptor.capture());

        int actualId = intCaptor.getValue();
        assertEquals(id, actualId);
    }

    @Test
    public void getById_whenProvidedBookId_DAOReturnsSameOne_Once() {
        //given
        Book expected = new Book();
        int id = 1;
        expected.setId(id);

        //when
        when(mockDAO.findById(id)).thenReturn(expected);
        Book actual = service.getById(id);

        //then
        assertEquals(expected, actual);
        verify(mockDAO, times(1)).findById(id);
    }

    @Test
    public void getByName_whenProvidedBookName_ReturnsSameOne_Once() {
        //given
        Book expected = new Book();
        expected.setName("name");

        //when
        when(mockDAO.findByName(expected.getName())).thenReturn(expected);
        Book actual = service.getByName(expected.getName());

        //then
        assertEquals(expected, actual);
        verify(mockDAO, times(1)).findByName(expected.getName());
    }

    @Test
    public void create_WhenProvidedBook_DAOReturnsSame_Once() {
        //given
        Book expected = new Book();
        expected.setId(1);
        expected.setName("book");
        expected.setAuthor("author");
        expected.setPublisher("publisher");
        expected.setCount(1);
        expected.setPublicationDate(LocalDate.of(1992, 02, 02));

        //when

        when(mockDAO.insert(expected)).thenReturn(true);
        service.create(expected);

        //then
        verify(mockDAO).insert(bookCaptor.capture());
        Book actual = bookCaptor.getValue();
        assertEquals(actual, expected);
        verify(mockDAO, times(1)).insert(expected);
    }

    @Test
    public void update_whenProvideBookAndItValidates_UpdatedBookIsSame_Once() {
        //given
        Book expected = new Book();
        expected.setId(1);
        expected.setName("book");
        expected.setAuthor("author");
        expected.setPublisher("publisher");
        expected.setCount(1);
        expected.setPublicationDate(LocalDate.of(1992, 02, 02));

        //when

        when(mockDAO.update(expected)).thenReturn(true);
        service.update(expected);

        //then
        verify(mockDAO).update(bookCaptor.capture());
        Book actual = bookCaptor.getValue();
        assertEquals(actual, expected);
        verify(mockDAO, times(1)).update(expected);
    }

    @Test
    public void getCount() {
        assertTrue(true);
    }
}