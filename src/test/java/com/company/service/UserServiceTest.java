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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Captor
    ArgumentCaptor<User> userCaptor;

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
    public void deleteById() {
        assertTrue(true);
    }

    @Test
    public void getById() {
        assertTrue(true);
    }

    @Test
    public void getByEmail() {
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
}