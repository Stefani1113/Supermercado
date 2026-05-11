package com.proyecto.supermercado.dto;

import lombok.Data;


@Data
public class HttpGlobalResponse<T> {
    
    private T data;

    
    private String message;
}
