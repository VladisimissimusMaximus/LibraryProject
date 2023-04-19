package com.company.web.command.book;

import com.company.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookCommandTest {


    @Mock
    BookService service;

    @Mock
    HttpServletRequest req;

    @Mock
    HttpServletResponse res;

    @InjectMocks
    DeleteBookCommand command;

    @Test
    void init_whenProvidedID_thenInitializeFieldWithSameOne(){
        // given
        int expectedId = 1;

        //when
        when(req.getRequestURI()).thenReturn("library/book/1");
        command.init(req, res);
        int actualId = command.bookId;

        //then
        assertEquals(expectedId, actualId);

    }

    @Test
    void process_whenProcessCommand_SendRedirectToCatalogue() throws Exception {
        //when
        command.process();

        //then
        verify(res, times(1)).sendRedirect("null/catalogue");

    }
}