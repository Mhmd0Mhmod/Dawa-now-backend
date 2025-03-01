package org.dawanow.dawanowapi.security;

import jakarta.servlet.http.HttpServletRequest;
import org.dawanow.dawanowapi.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

@Component
public class OwnerAccessEvaluator implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String pathAdminId = context.getVariables().get("adminId");

        if (!StringUtils.hasText(pathAdminId)) {
            return new AuthorizationDecision(false);
        }

        HttpServletRequest request = context.getRequest();
        String jwt = parseJwt(request);

        if (jwt == null) {
            return new AuthorizationDecision(false);
        }

        Long tokenUserId = jwtTokenUtil.extractUserId(jwt);
        UserRole role = jwtTokenUtil.extractRole(jwt);

        if (role == UserRole.Admin) {
            // Admin has access to everything
            return new AuthorizationDecision(true);
        }

        if ((role == UserRole.Pharmacist_Admin || role == UserRole.Delivery_Admin || role == UserRole.Provider_Admin)
                && tokenUserId == Long.parseLong(pathAdminId)) {
            // Owner accessing their own resources
            return new AuthorizationDecision(true);
        }

        return new AuthorizationDecision(false);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}