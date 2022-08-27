package com.uce.prueba3.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.prueba3.repository.IDetalleVentaRepo;
import com.uce.prueba3.repository.IProductoRepo;
import com.uce.prueba3.repository.IVentaRepo;
import com.uce.prueba3.repository.modelo.DetalleVenta;
import com.uce.prueba3.repository.modelo.Producto;
import com.uce.prueba3.repository.modelo.Venta;

@Service
public class GestorServiceImpl implements IGestorService {

    private static Logger LOG = LogManager.getLogger(GestorServiceImpl.class);

    @Autowired
    private IProductoRepo productoRepo;

    @Autowired
    private IVentaRepo ventaRepo;

    @Autowired
    private IDetalleVentaRepo detalleVentaRepo;

    @Override
    @Transactional(value = TxType.REQUIRED)
    public void realizarVenta(List<Producto> productos, String cedulaCliente, String numeroVenta) {
        
        Venta venta = new Venta();
        BigDecimal total = new BigDecimal("0");
        List<DetalleVenta> listaDetalles = new ArrayList<>();
        productos.forEach(producto -> {
            
            DetalleVenta detalleVenta = new DetalleVenta();
            
            try {
                Producto productoBuscado = this.productoRepo.buscarCodigo(producto.getCodigoBarras());
                BigDecimal precioUnitario = null;
                Integer cantidad = null;
                BigDecimal subtotal = null;
                // En el caso de que exista actualizo
                if (productoBuscado.getCodigoBarras() != null) {
                    // En el caso que stock sea 0
                    if (productoBuscado.getStock() <= 0) {
                        LOG.error("No existe stock");
                        throw new RuntimeException();
                    } else if (productoBuscado.getStock() <= producto.getStock()) {

                        cantidad = productoBuscado.getStock();

                    } else {
                        cantidad = producto.getStock();
                    }

                    precioUnitario = productoBuscado.getPrecio();
                    productoBuscado.setStock(productoBuscado.getStock() - cantidad);
                    detalleVenta.setCantidad(cantidad);
                    detalleVenta.setPrecioUnitario(precioUnitario);
                    subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
                    detalleVenta.setSubtotal(subtotal);
                    detalleVenta.setProducto(productoBuscado);
                    detalleVenta.setVenta(venta);
                    listaDetalles.add(detalleVenta);
                    total.add(subtotal);
                    
                    this.productoRepo.actualizar(productoBuscado);

                    

                }
            } catch (Exception e) {
                LOG.error("No se ha encontrado un producto para el codigo especificado");
                throw new RuntimeException();
            }
            
        });

        venta.setCedulaCliente(cedulaCliente);
        venta.setFecha(LocalDateTime.now());
        venta.setNumero(numeroVenta);
        venta.setTotalVenta(total);
        venta.setDetalles(listaDetalles);
        this.ventaRepo.insertar(venta);

    }

    @Override
    public void generarReporte(LocalDateTime fechaVenta, String categoria, Integer cantidad) {
        List<DetalleVenta> lista = this.detalleVentaRepo.buscarReporte(fechaVenta, categoria, cantidad);
        lista.forEach(detalle -> LOG.info(detalle.toString()));
        
    }

}
