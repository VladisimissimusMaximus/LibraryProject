package com.company.service;

import com.company.dao.OperationDAO;
import com.company.model.Book;
import com.company.model.Operation;
import com.company.model.User;
import com.company.util.exceptions.DuplicateFieldException;
import com.company.util.exceptions.OperationValidationException;
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
public class OperationServiceTest {

    @Captor
    ArgumentCaptor<Operation> operationCaptor;

    @Mock
    OperationDAO mockDAO;

    @InjectMocks
    OperationService service;

    @Test
    void getAll_whenDAOReturnsList_thenReturnTheSameOne() {
        // given
        Operation op1 = new Operation();
        Operation op2 = new Operation();
        List<Operation> expected = List.of(op1, op2);

        // when
        when(mockDAO.findAll(null)).thenReturn(expected);
        List<Operation> actual = service.getAll(null);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void returnBook_whenGivenUserAndBook_thenPassedOperationContainsTheSameOnes() {
        // given
        User user = new User();
        user.setEmail("test@mail.com");
        Book book = new Book();
        book.setName("testories");

        // when
        when(mockDAO.delete(any())).thenReturn(true);
        service.returnBook(user, book);

        // then
        verify(mockDAO).delete(operationCaptor.capture());

        Operation actual = operationCaptor.getValue();
        assertEquals(user, actual.getUser());
        assertEquals(book, actual.getBook());
    }

    @Test
    void returnBook_whenDAOThrowsDuplicateException_thenWrapToServiceException() {
        // given
        DuplicateFieldException duplicateException = new DuplicateFieldException();
        User user = new User();
        Book book = new Book();

        String expectedCode = "validation.operation.duplicate";

        // when
        when(mockDAO.delete(any())).thenThrow(duplicateException);

        // then
        OperationValidationException actual = assertThrows(OperationValidationException.class,
                () -> service.returnBook(user, book));
        assertEquals(expectedCode, actual.getDuplicationValidation());
    }

    @Test
    public void placeOrder() {
        assertTrue(true);
    }

    @Test
    public void approveOrder() {
        assertTrue(true);
    }

    @Test
    public void takeToReadingRoom() {
        assertTrue(true);
    }

    @Test
    public void cancel() {
        assertTrue(true);
    }
}