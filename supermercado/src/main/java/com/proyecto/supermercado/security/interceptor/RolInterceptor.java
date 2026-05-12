package com.proyecto.supermercado.security.interceptor;

import com.proyecto.supermercado.security.entity.RolEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RolInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        Long rolId = (Long) request.getAttribute("rolId");

        if (rolId == null) {
            denegar(response, "No autenticado");
            return false;
        }

        String method = request.getMethod();
        String path   = request.getRequestURI();

  
        if (rolId.equals(RolEnum.CAJERO.getId())) {

            if (path.equals("/api/ventas") && method.equals("POST")) {
                return true;
            }

            if (method.equals("GET")) {
                return true;
            }

            denegar(response,
                    "Acceso denegado. El rol CAJERO solo puede registrar ventas " +
                    "y consultar informacion (GET). Ruta intentada: " + method + " " + path);
            return false;
        }

        return true;
    }

    private void denegar(
            HttpServletResponse response,
            String mensaje
    ) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"error\": \"" + mensaje + "\"}"
        );
    }
}
