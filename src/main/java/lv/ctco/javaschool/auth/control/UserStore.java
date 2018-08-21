package lv.ctco.javaschool.auth.control;

import lv.ctco.javaschool.auth.control.exceptions.InvalidPasswordException;
import lv.ctco.javaschool.auth.control.exceptions.InvalidUsernameException;
import lv.ctco.javaschool.auth.control.exceptions.UsernameAlreadyExistsException;
import lv.ctco.javaschool.auth.entity.domain.Role;
import lv.ctco.javaschool.auth.entity.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserStore {
    private static final int MIN_PASSWORD_LENGTH = 5;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Pbkdf2PasswordHash hash;

    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findUserByUsername(String username) {
        username = username.trim();
        List<User> user = em.createQuery("select u from User u where upper(u.username) = :username", User.class)
                .setParameter("username", username.toUpperCase())
                .getResultList();
        return user.isEmpty() ? Optional.empty() : Optional.of(user.get(0));
    }

    public User createUser(String username, String password, Role role) throws InvalidUsernameException, InvalidPasswordException, UsernameAlreadyExistsException {
        username = username == null ? null : username.trim();
        validateUsername(username);
        validatePassword(password);
        if (findUserByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        String pwdHash = hash.generate(password.toCharArray());
        User user = new User();
        user.setUsername(username);
        user.setPassword(pwdHash);
        user.setRole(role);
        em.persist(user);
        return user;
    }

    void validateUsername(String username) throws InvalidUsernameException {
        username = username == null ? null : username.trim();
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException();
        }
    }

    void validatePassword(String password) throws InvalidPasswordException {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH || password.startsWith(" ") || password.endsWith(" ")) {
            throw new InvalidPasswordException();
        }
    }

}
