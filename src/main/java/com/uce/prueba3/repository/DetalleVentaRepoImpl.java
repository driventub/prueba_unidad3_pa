package com.uce.prueba3.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.uce.prueba3.repository.modelo.DetalleVenta;

@Repository
@Transactional
public class DetalleVentaRepoImpl implements IDetalleVentaRepo {

	private static Logger LOG = LogManager.getLogger(DetalleVentaRepoImpl.class);

	@PersistenceContext
	private EntityManager e;

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public DetalleVenta buscar(Integer id) {
		return this.e.find(DetalleVenta.class, id);
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(DetalleVenta deve) {
		this.e.merge(deve);

	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void eliminar(Integer id) {
		DetalleVenta gBorrar = this.buscar(id);
		this.e.remove(gBorrar);

	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void insertar(DetalleVenta deve) {
		this.e.persist(deve);

	}

	@Override
	public List<DetalleVenta> buscarReporte(LocalDateTime fechaVenta, String categoria, Integer cantidad) {
		TypedQuery<DetalleVenta> myTypedQuery = this.e
				.createQuery(
						"SELECT NEW com.uce.prueba3.repository.modelo.ReporteVentas(p.codigoBarras, p.nombre, d.cantidad, d.precioUnitario, d.subtotal) FROM DetalleVenta d  JOIN  d.venta v JOIN  d.producto p WHERE v.fecha >= :fecha AND p.categoria = :categoria AND d.cantidad > :cantidad ",
						DetalleVenta.class)
				.setParameter("fecha", fechaVenta)
				.setParameter("categoria", categoria)
				.setParameter("cantidad", cantidad);
		return myTypedQuery.getResultList();
		
	}

}