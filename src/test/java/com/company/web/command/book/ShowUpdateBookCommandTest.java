package com.company.web.command.book;

import com.company.model.Book;
import com.company.service.BookService;
import com.company.web.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowUpdateBookCommandTest {

    @Mock
    BookService service;

    @Mock
    HttpServletRequest req;

    RequestDispatcher dispatcher;

    @Mock
    HttpServletResponse res;

    @InjectMocks
    ShowUpdateBookCommand command;

    @Test
    void init_whenProvidedID_thenInitializeFieldWithSameOne() {
        // given
        int expectedId = 1;

        // when
        when(req.getRequestURI()).thenReturn("library/book/1");
        command.init(req, res);
        int actualId = command.bookId;

        // then
        assertEquals(expectedId, actualId);

    }

    @Test
    void process_whenServiceReturnedBook_thenForwardToUpdateBook() throws Exception {
        // given
        Book expectedBook = new Book();
        int expectedId = 1;
        expectedBook.setId(expectedId);
        command.bookId = expectedId;

        dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(dispatcher);

        // when
        when(service.getById(eq(expectedId))).thenReturn(expectedBook);
        command.process();

        // then
//        verify(req, times(1)).setAttribute(eq("books"), booksCaptor.capture());
        verify(req, times(1)).getRequestDispatcher(View.SUBMIT_BOOK.getPath());
        verify(dispatcher, times(1)).forward(req, res);
    }
}