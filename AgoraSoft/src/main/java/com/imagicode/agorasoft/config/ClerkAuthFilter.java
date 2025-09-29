package com.imagicode.agorasoft.config;

import com.clerk.backend_api.helpers.security.AuthenticateRequest;
import com.clerk.backend_api.helpers.security.models.AuthenticateRequestOptions;
import com.clerk.backend_api.helpers.security.models.RequestState;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class ClerkAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        System.out.println("[ClerkAuthFilter] Request path: " + path);

        // 🔹 Permitir libremente inventario
        if (path.startsWith("/inventario")||path.startsWith("/api/inventario")) {
            System.out.println("[ClerkAuthFilter] Ruta inventario → omitimos ClerkAuth");
            chain.doFilter(request, response);
            return;
        }

        // 🔹 Leer Authorization Header
        String authHeader = httpRequest.getHeader("Authorization");
        System.out.println("[ClerkAuthFilter] Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[ClerkAuthFilter] ERROR: No Bearer token found");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Clerk token");
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("[ClerkAuthFilter] Token length: " + token.length());

        // 🔹 Extraer headers para Clerk
        Map<String, List<String>> headers = new HashMap<>();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, Collections.list(httpRequest.getHeaders(headerName)));
        }

        try {
            RequestState requestState = AuthenticateRequest.authenticateRequest(
                    headers,
                    AuthenticateRequestOptions
                            .secretKey("sk_test_GNj2mKLiTHeAdLs1kF7RR0vvVZ2dzVGrVgiGIrqsVF")
                            .authorizedParty("http://localhost:4200") // ⚠️ ajusta según frontend
                            .build()
            );

            if (!requestState.isSignedIn()) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Clerk token inválido: " + requestState.reason());
                return;
            }

            Map<String, Object> claims = requestState.claims().orElseThrow();
            String userId = (String) claims.get("sub");
            request.setAttribute("clerkUserId", userId);

        } catch (Exception e) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error validando token Clerk: " + e.getMessage());
            return;
        }

        // 🔹 Continuar
        chain.doFilter(request, response);
    }
}
