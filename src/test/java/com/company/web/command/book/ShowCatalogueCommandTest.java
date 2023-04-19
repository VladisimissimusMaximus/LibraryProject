package com.company.web.command.book;

import com.company.dao.OperationDAO;
import com.company.model.Book;
import com.company.model.Operation;
import com.company.service.BookService;
import com.company.service.OperationService;
import com.company.util.selection.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowCatalogueCommandTest {

    @Captor
    ArgumentCaptor<List<Book>> booksCaptor;

    @Mock
    BookService service;

    @Mock
    HttpServletRequest req;

    @Mock
    HttpServletResponse res;

    @InjectMocks
    ShowCatalogueCommand command;

    @BeforeEach
    void beforeEach() {
        when(req.getRequestDispatcher(any()))
                .thenReturn(mock(RequestDispatcher.class));
    }

    @Test
    public void process_whenServiceReturnBooks_setTheSameOneToRequestAttributes() throws Exception {
        // given
        List<Book> expected = new ArrayList<>();

        // when
        when(service.getAll(any())).thenReturn(expected);
        command.process();

        // then
        verify(req, times(1)).setAttribute(eq("books"), booksCaptor.capture());
        List<Book> actual = booksCaptor.getValue();

        assertEquals(expected, actual);
    }

}