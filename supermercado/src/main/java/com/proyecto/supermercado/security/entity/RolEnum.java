package com.proyecto.supermercado.security.entity;

public enum RolEnum {

    ADMIN(1L),
    CAJERO(2L);

    private final Long id;

    RolEnum(Long id) { this.id = id; }

    public Long getId() { return id; }

    public static RolEnum fromId(Long id) {
        for (RolEnum rol : values()) {
            if (rol.id.equals(id)) return rol;
        }
        throw new IllegalArgumentException("Rol desconocido con id: " + id);
    }
}
