package de.david.cookbook.rest.util;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

import javax.servlet.http.HttpServletRequest;

public class Util {
    public static String extractKeycloakUserIdFromRequest(HttpServletRequest request) {
        return getTokenFromRequest(request).getSubject();
    }

    public static AccessToken getTokenFromRequest(HttpServletRequest request) {
        return ((KeycloakPrincipal) request.getUserPrincipal())
                        .getKeycloakSecurityContext()
                        .getToken();
    }
}
