package lv.ctco.javaschool.auth.control;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.Optional;

import static java.util.Collections.singleton;

public class DatabaseIdentityStore implements IdentityStore {
    @Inject
    @SuppressWarnings("CdiInjectionPointsInspection")
    private Pbkdf2PasswordHash hash;
    @Inject
    private UserStore userStore;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (!(credential instanceof UsernamePasswordCredential)) {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
        String username = ((UsernamePasswordCredential) credential).getCaller();
        Password password = ((UsernamePasswordCredential) credential).getPassword();

        Optional<User> user = userStore.findUserByUsername(username);
        return user
                .map(u -> verify(u, password))
                .orElse(CredentialValidationResult.INVALID_RESULT);
    }

    private CredentialValidationResult verify(User user, Password password) {
        boolean valid = hash.verify(password.getValue(), user.getPassword());
        if (valid) {
            return new CredentialValidationResult(user.getUsername(), singleton(user.getRole().name()));
        } else {
            return CredentialValidationResult.INVALID_RESULT;
        }
    }
}
