package com.geovivienda.geovivienda.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORS implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("Origin");
        String method = request.getMethod();

        // 🔐 Lista de orígenes permitidos (modo producción)
        List<String> allowedOrigins = Arrays.asList(
                "https://geovivienda-app.onrender.com",
                "https://geovivienda-app.vercel.app",
                "https://api.geoapify.com"
                // Agrega "http://localhost:4200" si deseas permitirlo en desarrollo local
        );

        // ✅ Validación segura: solo permite orígenes explícitos
        if (origin != null && allowedOrigins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Vary", "Origin");
        } else if (origin != null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
            return;
        }

        // ✅ Cabeceras CORS estándar
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        // ✅ Permitir preflight
        if ("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }

    /*
     * ⚠️ MODO DESARROLLO (permitir todo) — NO RECOMENDADO EN PRODUCCIÓN
     *
     * Descomenta esto solo si necesitas permitir todos los orígenes temporalmente.
     *
     * @Override
     * public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
     *         throws IOException, ServletException {
     *     HttpServletResponse response = (HttpServletResponse) res;
     *     HttpServletRequest request = (HttpServletRequest) req;
     *
     *     response.setHeader("Access-Control-Allow-Origin", "*");
     *     response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
     *     response.setHeader("Access-Control-Max-Age", "3600");
     *     response.setHeader("Access-Control-Allow-Headers",
     *             "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
     *
     *     if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
     *         response.setStatus(HttpServletResponse.SC_OK);
     *     } else {
     *         chain.doFilter(req, res);
     *     }
     * }
     */

    @Override
    public void destroy() {
        // Sin implementación
    }
}
