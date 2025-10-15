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

        // 🔹 Leer Authorization Header
        String authHeader = httpRequest.getHeader("Authorization");
        System.out.println("[ClerkAuthFilter] Authorization header: " + authHeader);
        System.out.println("[ClerkAuthFilter] Request URL: " + httpRequest.getRequestURL());
        System.out.println("[ClerkAuthFilter] Method: " + httpRequest.getMethod());

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
            System.out.println("[ClerkAuthFilter] Header: " + headerName + " = " + headers.get(headerName));
        }

        try {
            // 🔹 Validar el token con Clerk - CORREGIR authorizedParty
            RequestState requestState = AuthenticateRequest.authenticateRequest(
                    headers,
                    AuthenticateRequestOptions
                            .secretKey("sk_live_ixrco2zDunfdDyQ2SFeZQB6QPQPD7qwNq85VPfMQHy")
                            .authorizedParties(Arrays.asList("http://10.43.103.209", "http://localhost:8085", "http://127.0.0.1", "http://10.43.102.15","https://1cb8343a3c8d.ngrok-free.app","http://localhost:30080","http://127.0.0.1:4040"))
                            .build()
            );

            System.out.println("[ClerkAuthFilter] ¿Está autenticado Clerk? " + requestState.isSignedIn());
            System.out.println("[ClerkAuthFilter] Reason: " + requestState.reason());
            
            if (!requestState.isSignedIn()) {
                System.out.println("[ClerkAuthFilter] Token rechazado por Clerk. Reason: " + requestState.reason());
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Clerk token inválido: " + requestState.reason());
                return;
            }

            // 🔹 Obtener claims y guardar el userId
            Map<String, Object> claims = requestState.claims().orElseThrow();
            String userId = (String) claims.get("sub");
            request.setAttribute("clerkUserId", userId);

            System.out.println("[ClerkAuthFilter] Usuario autenticado: " + userId);
            System.out.println("[ClerkAuthFilter] Claims keys: " + claims.keySet());

        } catch (Exception e) {
            System.out.println("[ClerkAuthFilter] ERROR validando token Clerk: " + e.getMessage());
            e.printStackTrace();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error validando token Clerk: " + e.getMessage());
            return;
        }

        // 🔹 Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }
}