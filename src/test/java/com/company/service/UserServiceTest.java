package com.company.service;

import com.company.dao.UserDAO;
import com.company.model.Book;
import com.company.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Captor
    ArgumentCaptor<Integer> intCaptor;

    @Mock
    UserDAO mockDAO;

    @InjectMocks
    UserService service;

    @Test
    public void getAllUsers_WhenDAOReturnsList_ServiceReturnsSame() {
        // given
        User user1 = new User();
        user1.setName("a");
        User user2 = new User();
        user2.setName("b");
        List<User> expected = List.of(user1, user2);

        // when
        when(mockDAO.findAll()).thenReturn(expected);
        List<User> actual = service.getAllUsers();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_WhenProvidedId_DAOReturnsSameOne() {
        // given
        User user = new User();
        int id = 1;
        user.setId(id);

        // when
        when(mockDAO.delete(id)).thenReturn(true);
        service.deleteById(user.getId());

        // then
        verify(mockDAO).delete(intCaptor.capture());

        int actualId = intCaptor.getValue();
        assertEquals(id, actualId);
    }

    @Test
    public void getById_WhenProvidedUserId_DAOReturnsSameOne_Once() {
        //given
        User expected = new User();
        int id = 1;
        expected.setId(id);

        //when
        when(mockDAO.findById(id)).thenReturn(expected);
        User actual = service.getById(id);

        //then
        assertEquals(expected, actual);
        verify(mockDAO, times(1)).findById(id);
    }

    @Test
    public void getByEmail_WhenProvidedEmail_DAOReturnsSameOne_Once() {
        //given
        User expected = new User();
        String email = "email@gmail.com";
        expected.setEmail(email);

        //when
        when(mockDAO.findByEmail(email)).thenReturn(expected);
        User actual = service.getByEmail(email);

        //then
        assertEquals(expected, actual);
        verify(mockDAO, times(1)).findByEmail(email);
    }

    @Test
    public void register_WhenProvidedUser_DAOReturnsSameOne() {
        //given
        User expected = new User();
        expected.setId(1);
        expected.setEmail("email@gmail.com");
        expected.setPassword("123Fa");
        expected.setName("Name");

        //when

        when(mockDAO.insert(expected)).thenReturn(true);
        service.register(expected);

        //then
        verify(mockDAO).insert(userCaptor.capture());
        User actual = userCaptor.getValue();
        assertEquals(actual, expected);
        verify(mockDAO, times(1)).insert(expected);
    }

    @Test
    public void update_WhenProvidedUserAndItValidates_DAOReturnsSameOne() {
        //given
        User expected = new User();
        expected.setId(1);
        expected.setName("name");
        expected.setPassword("123AS");
        expected.setEmail("email@gmail.com");
        expected.setRegistered(LocalDateTime.of(2020, 12, 12, 12, 12));

        //when

        when(mockDAO.update(expected)).thenReturn(true);
        service.update(expected);

        //then
        verify(mockDAO).update(userCaptor.capture());
        User actual = userCaptor.getValue();
        assertEquals(actual, expected);
        verify(mockDAO, times(1)).update(expected);
    }


}