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

import static org.junit.jupiter.api.Assertions.assertEquals;


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
    void testReturnUserDto() {
        User user = new User();
        user.setUsername("aa");
        user.setPhone("1234567");
        user.setEmail("a@b.com");
        Mockito.when(userStore.getCurrentUser()).thenReturn(user);

        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        Mockito.when(userStore.convertToDto(user)).thenReturn(dto);

        UserLoginDto resultDto = authenticationApi.returnUserDto();
        assertEquals(user.getUsername(), resultDto.getUsername());
        assertEquals(user.getEmail(), resultDto.getEmail());
        assertEquals(user.getPhone(), resultDto.getPhone());
    }
}
