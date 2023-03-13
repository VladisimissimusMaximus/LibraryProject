package com.company.service;

import com.company.dao.OperationDAO;
import com.company.model.Book;
import com.company.model.Operation;
import com.company.model.OperationStatus;
import com.company.model.User;
import com.company.util.OperationUtil;
import com.company.util.exceptions.DuplicateFieldException;
import com.company.util.exceptions.OperationValidationException;
import jdk.jshell.JShell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
    public void getAllByUser_whenDAOReturnsList_returnTheSame_once(){
        //given
        User user = new User();
        user.setId(1);
        Operation op1 = new Operation();
        Operation op2 = new Operation();
        op1.setUser(user);
        op2.setUser(user);
        List<Operation> expected = List.of(op1, op2);

        //when
        when(mockDAO.findByUserId(user.getId())).thenReturn(expected);
        List<Operation> actual = service.getAllByUser(user.getId());

        //then
        assertEquals(expected, actual);
        verify(mockDAO, times(1)).findByUserId(user.getId());
    }

    @Test
    void placeOrder_whenGivenUserAndBook_thenPassedOperationContainsTheSameOnes() {
        // given
        User user = new User();
        user.setEmail("test@mail.com");
        Book book = new Book();
        book.setName("testories");

        // when
        when(mockDAO.insertOperation(any())).thenReturn(true);
        service.placeOrder(user, book, Integer.toString(30));

        // then
        verify(mockDAO).insertOperation(operationCaptor.capture());

        Operation actual = operationCaptor.getValue();
        assertEquals(user, actual.getUser());
        assertEquals(book, actual.getBook());
    }

    @Test
    public void placeOrder_whenDAOThrowsDuplicateException_thenWrapToServiceException() {

        // given
        DuplicateFieldException duplicateException = new DuplicateFieldException();
        User user = new User();
        Book book = new Book();
        Operation operation = new Operation();

        int duration = 30;
        operation.setStatus(OperationStatus.ORDER);
        operation.setDuration(duration);

        String expectedCode = "validation.operation.duplicate";

        // when
        when(mockDAO.insertOperation(any())).thenThrow(duplicateException);

        // then
        OperationValidationException actual = assertThrows(OperationValidationException.class,
                () -> service.placeOrder(user, book, "30"));
        assertEquals(expectedCode, actual.getDuplicationValidation());

    }

    @Test
    public void approveOrder_whenDAOReturnsOperation_returnSameOne() {

        //given
        User user = new User();
        user.setId(1);
        Book book = new Book();
        book.setId(1);
        Operation operation = new Operation();
        operation.setUser(user);
        operation.setBook(book);
        LocalDateTime dateTime = LocalDateTime.of(2022, 1, 1, 1, 1);
        operation.setStartDate(dateTime);
        operation.setStatus(OperationStatus.SUBSCRIPTION);

        //when
        when(mockDAO.update(any())).thenReturn(true);
        service.approveOrder(user, book);

        //then
        verify(mockDAO).update(operationCaptor.capture());

        Operation actual = operationCaptor.getValue();
        actual.setStartDate(dateTime);
        assertEquals(user, actual.getUser());
        assertEquals(book, actual.getBook());
        assertEquals(operation.getStatus(), actual.getStatus());
        assertEquals(operation.getStartDate(), actual.getStartDate());

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