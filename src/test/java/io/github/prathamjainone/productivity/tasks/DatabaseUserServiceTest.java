package io.github.prathamjainone.productivity.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.prathamjainone.productivity.users.model.User;
import io.github.prathamjainone.productivity.users.repository.UserRepository;
import io.github.prathamjainone.productivity.users.exceptions.UserNotFoundException;
import io.github.prathamjainone.productivity.users.exceptions.UserValidationException;
import io.github.prathamjainone.productivity.users.service.DatabaseUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DatabaseUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DatabaseUserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(
                "testuser",
                "testuser@example.com",
                "password123",
                "Test User"
        );
        testUser.setId(1L);
    }

    @Test
    @DisplayName("Should create a user when given valid data")
    void testCreateUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User created = userService.createUser(testUser);

        // Assert
        assertNotNull(created);
        assertEquals(testUser.getUsername(), created.getUsername());
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should throw exception when creating null user")
    void testCreateNullUser() {
        // Act & Assert
        assertThrows(UserValidationException.class, () -> userService.createUser(null));

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return all users")
    void testGetAllUsers() {
        // Arrange
        User user2 = new User("anotheruser", "anotheruser@example.com", "password123", "Another User");
        user2.setId(2L);

        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals("testuser", users.get(0).getUsername());
        assertEquals("anotheruser", users.get(1).getUsername());
    }

    @Test
    @DisplayName("Should return user when ID exists")
    void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        User found = userService.getUserById(1L);

        // Assert
        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("testuser", found.getUsername());
    }

    @Test
    @DisplayName("Should throw exception when user ID doesn't exist")
    void testGetUserByIdNotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }
}
