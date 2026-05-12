package com.proyecto.supermercado.security.dto;

public record LoginResponse(String token, String rol, String username) {}
