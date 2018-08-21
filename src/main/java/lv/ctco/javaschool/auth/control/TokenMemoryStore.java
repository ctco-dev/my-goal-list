package lv.ctco.javaschool.auth.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@ApplicationScoped
@SuppressWarnings("unused")
public class TokenMemoryStore implements RememberMeIdentityStore {
    private final Map<String, CredentialValidationResult> store = new ConcurrentHashMap<>();
    @Inject
    private HttpServletRequest request;

    @Override
    public CredentialValidationResult validate(RememberMeCredential credential) {
        String token = credential.getToken();
        return store.getOrDefault(token, CredentialValidationResult.INVALID_RESULT);
    }

    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
        String token = UUID.randomUUID().toString();
        store.put(token, new CredentialValidationResult(callerPrincipal, groups));
        return token;
    }

    @Override
    public void removeLoginToken(String token) {
        store.remove(token);
    }

}