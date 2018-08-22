package lv.ctco.javaschool.auth.control;

import lv.ctco.javaschool.auth.control.exceptions.InvalidPasswordException;
import lv.ctco.javaschool.auth.control.exceptions.InvalidUsernameException;
import lv.ctco.javaschool.auth.control.exceptions.UsernameAlreadyExistsException;
import lv.ctco.javaschool.auth.entity.domain.Role;
import lv.ctco.javaschool.auth.entity.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class UserStoreTest {

   /*
    UserStore userStore = new UserStore();
    @Test
    void createUser() {

        User user;

        try {
            user = userStore.createUser("DIMA", "dima_pass", "mail@a.lv", "123123", Role.USER);
            assertEquals("DIMA", user.getUsername());
            assertEquals("dima_pass", user.getPassword());
            assertEquals("mail@a.lv", user.getEmail());
            assertEquals("123123", user.getPhone());

        } catch (UsernameAlreadyExistsException exception) {
            fail(" user already exist");
        } catch (InvalidUsernameException e) {
            fail("invalid username");
        } catch (InvalidPasswordException e) {
            fail("invalid password");
        }

    }

*/


    @Test
    void createUserWithInvalidPassword() {
        UserStore uc = new UserStore();
        Executable invalidPassword = () -> uc.createUser(
                "username",
                null,
                "hello@email.com",
                "123",
                Role.USER
        );
        assertThrows(InvalidPasswordException.class, invalidPassword);

    }



    @Test
    void validateUsername() {
        UserStore uc = new UserStore();
        Executable nullName = () -> uc.validateUsername(null);
        Executable emptyName1 = () -> uc.validateUsername("");
        Executable emptyName2 = () -> uc.validateUsername("    ");
        Executable goodName1 = () -> uc.validateUsername("goodName");
        Executable goodName2 = () -> uc.validateUsername(" good Name  ");

        assertThrows(InvalidUsernameException.class, nullName);
        assertThrows(InvalidUsernameException.class, emptyName1);
        assertThrows(InvalidUsernameException.class, emptyName2);
        try {
            goodName1.execute();
            goodName2.execute();
        } catch (Throwable throwable) {
            fail(throwable.getMessage() + " was thrown");
        }
    }

    @Test
    void validatePassword() {
        UserStore uc = new UserStore();
        Executable nullPass = () -> uc.validatePassword(null);
        Executable emptyPass1 = () -> uc.validatePassword("");
        Executable shortPass1 = () -> uc.validatePassword("1234");
        Executable spacePass1 = () -> uc.validatePassword("    123 ");
        Executable spacePass2 = () -> uc.validatePassword("123 ");
        Executable spacePass3 = () -> uc.validatePassword("    123");
        Executable spacePass4 = () -> uc.validatePassword("   ");
        Executable goodPass1 = () -> uc.validatePassword("12345");

        assertThrows(InvalidPasswordException.class, nullPass);
        assertThrows(InvalidPasswordException.class, emptyPass1);
        assertThrows(InvalidPasswordException.class, shortPass1);
        assertThrows(InvalidPasswordException.class, spacePass1);
        assertThrows(InvalidPasswordException.class, spacePass2);
        assertThrows(InvalidPasswordException.class, spacePass3);
        assertThrows(InvalidPasswordException.class, spacePass4);
        try {
            goodPass1.execute();
        } catch (Throwable throwable) {
            fail(throwable.getMessage() + " was thrown");
        }
    }
}