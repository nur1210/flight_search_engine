package fontys.s3.backend.business.impl;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.domain.AccessToken;
import fontys.s3.backend.domain.User;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import fontys.s3.backend.persistence.entity.UserEntity;
import fontys.s3.backend.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GetUserUseCaseImpl.class})
@ExtendWith(SpringExtension.class)
class GetUserUseCaseImplTest {
    @MockBean
    private AccessToken accessToken;

    @Autowired
    private GetUserUseCaseImpl getUserUseCaseImpl;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link GetUserUseCaseImpl#getUser(long)}
     */
    @Test
    void testGetUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        ArrayList<PriceAlertEntity> priceAlerts = new ArrayList<>();
        userEntity.setPriceAlerts(priceAlerts);
        HashSet<UserRoleEntity> userRoles = new HashSet<>();
        userEntity.setUserRoles(userRoles);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(accessToken.hasRole((String) any())).thenReturn(true);
        when(accessToken.getUserId()).thenReturn(123L);
        long userId = 123L;
        Optional<User> actualUser = getUserUseCaseImpl.getUser(userId);
        assertTrue(actualUser.isPresent());
        User getResult = actualUser.get();
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("iloveyou", getResult.getPassword());
        assertEquals("Doe", getResult.getLastName());
        assertEquals(0L, getResult.getId());
        assertEquals("Jane", getResult.getFirstName());
        verify(userRepository).findById((Long) any());
        verify(accessToken).hasRole((String) any());
    }

    /**
     * Method under test: {@link GetUserUseCaseImpl#getUser(long)}
     */
    @Test
    void testGetUser2() {
        UnauthorizedDataAccessException unauthorizedDataAccessException = new UnauthorizedDataAccessException(
                "An error occurred");
        when(userRepository.findById((Long) any())).thenThrow(unauthorizedDataAccessException);
        when(accessToken.hasRole((String) any())).thenReturn(true);
        when(accessToken.getUserId()).thenReturn(123L);
        long userId = 123L;
        assertThrows(UnauthorizedDataAccessException.class, () -> getUserUseCaseImpl.getUser(userId));
        verify(userRepository).findById((Long) any());
        verify(accessToken).hasRole((String) any());
    }

    /**
     * Method under test: {@link GetUserUseCaseImpl#getUser(long)}
     */
    @Test
    void testGetUser3() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        ArrayList<PriceAlertEntity> priceAlerts = new ArrayList<>();
        userEntity.setPriceAlerts(priceAlerts);
        HashSet<UserRoleEntity> userRoles = new HashSet<>();
        userEntity.setUserRoles(userRoles);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(accessToken.hasRole((String) any())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(123L);
        long userId = 123L;
        Optional<User> actualUser = getUserUseCaseImpl.getUser(userId);
        assertTrue(actualUser.isPresent());
        User getResult = actualUser.get();
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("iloveyou", getResult.getPassword());
        assertEquals("Doe", getResult.getLastName());
        assertEquals(0L, getResult.getId());
        assertEquals("Jane", getResult.getFirstName());
        verify(userRepository).findById((Long) any());
        verify(accessToken).hasRole((String) any());
        verify(accessToken).getUserId();
    }

    /**
     * Method under test: {@link GetUserUseCaseImpl#getUser(long)}
     */
    @Test
    void testGetUser4() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        ArrayList<PriceAlertEntity> priceAlerts = new ArrayList<>();
        userEntity.setPriceAlerts(priceAlerts);
        HashSet<UserRoleEntity> userRoles = new HashSet<>();
        userEntity.setUserRoles(userRoles);
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(accessToken.hasRole((String) any())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(1L);
        long userId = 123L;
        assertThrows(UnauthorizedDataAccessException.class, () -> getUserUseCaseImpl.getUser(userId));
        verify(accessToken).hasRole((String) any());
        verify(accessToken).getUserId();
    }
}

