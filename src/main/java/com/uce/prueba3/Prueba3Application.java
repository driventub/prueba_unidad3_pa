package com.uce.prueba3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uce.prueba3.repository.modelo.Producto;
import com.uce.prueba3.service.IGestorService;
import com.uce.prueba3.service.IProductoService;

@SpringBootApplication
public class Prueba3Application implements CommandLineRunner {

	

	@Autowired
	private IGestorService gestorService;

	@Autowired
	private IProductoService productoService;
	public static void main(String[] args) {
		SpringApplication.run(Prueba3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// 1
		List<Producto> listaProducto = new ArrayList<>();
		Producto fanta = new Producto();

		fanta.setCategoria("Bebida");
		fanta.setCodigoBarras("123F");
		fanta.setNombre("Fanta");
		fanta.setPrecio(new BigDecimal("1.5"));
		fanta.setStock(39);

		this.productoService.ingresarProductos(fanta);
		this.productoService.ingresarProductos(fanta);

		// 2
		Producto fantaPrueba = new Producto();
		fantaPrueba.setCodigoBarras("123F");
		fantaPrueba.setStock(2);

		listaProducto.add(fantaPrueba);

		this.gestorService.realizarVenta(listaProducto, "178234897-2", "838");
		
		// 3
		this.productoService.consultarStock("123F");

		// 4
		// this.gestorService.generarReporte(LocalDateTime.of(2022, 8,26,1,1,1), "Bebida", 3);
	}	

}
