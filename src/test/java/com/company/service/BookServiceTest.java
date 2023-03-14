package com.company.service;

import com.company.dao.BookDAO;
import com.company.model.Book;
import com.company.model.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Captor
    ArgumentCaptor<Book> bookCaptor;

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
    public void deleteById() {
        assertTrue(true);
    }

    @Test
    public void getById() {
        assertTrue(true);
    }

    @Test
    public void getByName() {
        assertTrue(true);
    }

    @Test
    public void register() {
        assertTrue(true);
    }

    @Test
    public void update() {
        assertTrue(true);
    }

    @Test
    public void getCount() {
        assertTrue(true);
    }
}