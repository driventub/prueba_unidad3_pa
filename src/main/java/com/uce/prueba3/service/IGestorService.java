package com.uce.prueba3.service;

import java.time.LocalDateTime;
import java.util.List;

import com.uce.prueba3.repository.modelo.Producto;

public interface IGestorService {
    public void realizarVenta(List<Producto> productos, String cedulaCliente, String numeroVenta );

    public void generarReporte(LocalDateTime fechaVenta, String categoria, Integer cantidad);

}
