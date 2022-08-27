package com.uce.prueba3.repository.modelo;

import java.math.BigDecimal;

public class ReporteVentas {
    private String codigoBarras;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;


    public ReporteVentas(String codigoBarras, String nombreProducto, Integer cantidad, BigDecimal precioUnitario,
            BigDecimal subtotal) {
        this.codigoBarras = codigoBarras;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }


    public ReporteVentas() {
    }


    public String getCodigoBarras() {
        return codigoBarras;
    }


    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }


    public String getNombreProducto() {
        return nombreProducto;
    }


    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }


    public Integer getCantidad() {
        return cantidad;
    }


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }


    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


    public BigDecimal getSubtotal() {
        return subtotal;
    }


    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }


    @Override
    public String toString() {
        return "ReporteVentas [cantidad=" + cantidad + ", codigoBarras=" + codigoBarras + ", nombreProducto="
                + nombreProducto + ", precioUnitario=" + precioUnitario + ", subtotal=" + subtotal + "]";
    }

    
}
