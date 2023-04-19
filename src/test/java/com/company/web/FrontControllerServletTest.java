package com.company.web;

import com.company.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class FrontControllerServletTest {

    HttpServletResponse res;
    HttpServletRequest req;

    RequestDispatcher dispatcher;

    @BeforeEach
    void beforeEach() {
        res = mock(HttpServletResponse.class);
        req = mock(HttpServletRequest.class);
        dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any()))
                .thenReturn(dispatcher);
    }

    @Test
    public void doService_whenCommandIsNotFound_thenForwardToErrorPage() throws Exception {
        // given
        HttpServlet servlet = new FrontControllerServlet();
        when(req.getMethod()).thenReturn(HttpMethod.GET.name());
        when(req.getRequestURI()).thenReturn("/library/app/blablabla");
        when(req.getContextPath()).thenReturn("/library");
        when(req.getServletPath()).thenReturn("/app");

        // when
        servlet.service(req, res);

        // then
        verify(req, times(1)).getRequestDispatcher(View.ERROR.getPath());
        verify(dispatcher, times(1)).forward(req, res);
    }
}