package lv.ctco.javaschool.auth.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@SuppressWarnings("unused")
@AutoApplySession
@RememberMe(
        cookieHttpOnly = false,
        cookieName = "TOKEN",
        cookieSecureOnly = false)
@ApplicationScoped
public class AuthenticationMechanism implements HttpAuthenticationMechanism {
    @Inject
    private DatabaseIdentityStore identityStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        boolean isAuthRequest = httpMessageContext.isAuthenticationRequest();
        if (isAuthRequest) {
            return authenticate(request, response, httpMessageContext);
        }
        boolean isProtected = httpMessageContext.isProtected();
        if (isProtected) {
            return checkAuthenticated(request, response, httpMessageContext);
        }
        return httpMessageContext.doNothing();
    }

    private AuthenticationStatus checkAuthenticated(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        Set<String> groups = httpMessageContext.getGroups();
        if (groups != null && !groups.isEmpty()) {
            return httpMessageContext.doNothing();
        }
        if (request.getServletPath().startsWith("/api")) {
            return httpMessageContext.responseUnauthorized();
        } else {
            return httpMessageContext.redirect("/login.jsp");
        }
    }

    private AuthenticationStatus authenticate(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        Credential credential = httpMessageContext.getAuthParameters().getCredential();
        if (credential == null) {
            return httpMessageContext.responseUnauthorized();
        }
        if (!(credential instanceof UsernamePasswordCredential)) {
            return httpMessageContext.doNothing();
        }
        CredentialValidationResult result = identityStore.validate(credential);
        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            return httpMessageContext.notifyContainerAboutLogin(result);
        } else if (request.getServletPath().startsWith("/api")) {
            return httpMessageContext.responseUnauthorized();
        } else {
            return httpMessageContext.redirect("/login.jsp");
        }
    }
}
