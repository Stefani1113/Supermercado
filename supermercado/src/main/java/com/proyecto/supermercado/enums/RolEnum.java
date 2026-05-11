package com.proyecto.supermercado.enums;


public enum RolEnum {
    ADMIN(1L),
    CAJERO(2L);

    private final Long id;

    RolEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
