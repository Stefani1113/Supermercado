package com.proyecto.supermercado.security.filter;

import com.proyecto.supermercado.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            responderError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Token requerido. Incluye el header: Authorization: Bearer <token>");
            return;
        }

        String token = authHeader.substring(7); // quita "Bearer "

        if (!jwtService.esValido(token)) {
            responderError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Token invalido o expirado. Inicia sesion nuevamente en POST /auth/login");
            return;
        }

        Long rolId = jwtService.extraerRolId(token);
        String username = jwtService.extraerUsername(token);

        request.setAttribute("rolId", rolId);
        request.setAttribute("username", username);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList()
                );
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private void responderError(
            HttpServletResponse response,
            int status,
            String mensaje
    ) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"error\": \"" + mensaje + "\"}"
        );
    }
}
