package lv.ctco.javaschool.auth.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class AuthenticationApiTest {
    @Mock
    private UserStore userStore;

    @InjectMocks
    private AuthenticationApi authenticationApi;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void testReturnUserDto() {
//        User user = new User();
//        user.setUsername("aa");
//        user.setPhone("1234567");
//        user.setEmail("a@b.com");
//        Mockito.when(userStore.getCurrentUser()).thenReturn(user);
//        UserLoginDto dto = authenticationApi.returnUserDto();
//        assertEquals(user.getUsername(), dto.getUsername());
//        assertEquals(user.getEmail(), dto.getEmail());
//        assertEquals(user.getPhone(), dto.getPhone());
//    }
}
