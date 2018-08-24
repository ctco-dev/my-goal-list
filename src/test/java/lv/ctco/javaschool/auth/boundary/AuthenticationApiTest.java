package lv.ctco.javaschool.auth.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationApiTest {

    @Mock
    private UserStore userStore;

    @InjectMocks
    private AuthenticationApi authenticationApi;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test() {
        User user = new User();
        user.setUsername("aa");
        user.setPhone("1234567");
        user.setEmail("a@b.com");
        Mockito.when(userStore.getCurrentUser()).thenReturn(user);
        UserLoginDto dto = authenticationApi.returnUserDto();
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPhone(), dto.getPhone());

//        assertEquals(dto.getUsername(), "aa");
//        assertEquals(dto.getEmail(), "a@b.com");
//        assertEquals(dto.getPhone(), "1234567");
    }
}
