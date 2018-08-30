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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

        UserLoginDto resultDto = authenticationApi.returnUserDto();
        assertThat( resultDto.getUsername(), is(user.getUsername()));
        assertThat( resultDto.getEmail(), is(user.getEmail()));
        assertThat( resultDto.getPhone(), is(user.getPhone()));
    }

    @Test
    void testUserConverterToDto() {
        User user = new User();
        user.setUsername("aa");
        user.setPhone("1234567");
        user.setEmail("a@b.com");
        UserLoginDto dto = authenticationApi.convertToDto(user);
        assertThat( dto.getUsername(), is(user.getUsername()));
        assertThat( dto.getEmail(), is(user.getEmail()));
        assertThat( dto.getPhone(), is(user.getPhone()));
    }

}
