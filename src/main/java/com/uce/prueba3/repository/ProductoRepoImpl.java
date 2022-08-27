package com.uce.prueba3.repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.uce.prueba3.repository.modelo.Producto;

@Repository
@Transactional
public class ProductoRepoImpl implements IProductoRepo{
	
	private static Logger LOG =  LogManager.getLogger(ProductoRepoImpl.class);
	
	
	@PersistenceContext
	private EntityManager e;
	
	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Producto buscar(Integer id) {
		return this.e.find(Producto.class, id);
	}

	
	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(Producto prod) {
		this.e.merge(prod);
		
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void eliminar(Integer id) {
		Producto gBorrar = this.buscar(id);
		this.e.remove(gBorrar);
		
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void insertar(Producto prod) {
		this.e.persist(prod);
		
	}


	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Producto buscarCodigo(String cod) {
		TypedQuery<Producto> myTypedQuery = this.e
				.createQuery("SELECT p FROM Producto p  WHERE p.codigoBarras = :codigo ", Producto.class)
				.setParameter( "codigo", cod);
		return myTypedQuery.getSingleResult();
	}


	@Override
	public Producto buscarCriteriaCodigo(String cod) {
		CriteriaBuilder myBuilder = this.e.getCriteriaBuilder();

        CriteriaQuery<Producto> myQuery = myBuilder.createQuery(Producto.class);

        Root<Producto> prodFrom = myQuery.from(Producto.class);

        Predicate pNombre = myBuilder.equal(prodFrom.get("codigoBarras"), cod);

        CriteriaQuery<Producto> eCompleta = myQuery.select(prodFrom).where(pNombre);

        TypedQuery<Producto> prodFinal = this.e.createQuery(eCompleta);

        return prodFinal.getSingleResult();
	}

	
}

