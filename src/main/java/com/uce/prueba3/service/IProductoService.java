package com.uce.prueba3.service;

import com.uce.prueba3.repository.modelo.Producto;

public interface IProductoService {
    public void ingresarProductos(Producto p);

    public void consultarStock(String codigo);
}
