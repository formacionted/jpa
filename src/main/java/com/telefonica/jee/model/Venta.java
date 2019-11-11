package com.telefonica.jee.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ventas database table.
 * 
 */
@Entity
@Table(name="ventas")
@NamedQuery(name="Venta.findAll", query="SELECT v FROM Venta v")
public class Venta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idventas;

	private int cantidad;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="producto")
	private Producto productoBean;

	//bi-directional many-to-one association to Socio
	@ManyToOne
	@JoinColumn(name="socio")
	private Socio socioBean;

	public Venta() {
	}

	public int getIdventas() {
		return this.idventas;
	}

	public void setIdventas(int idventas) {
		this.idventas = idventas;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProductoBean() {
		return this.productoBean;
	}

	public void setProductoBean(Producto productoBean) {
		this.productoBean = productoBean;
	}

	public Socio getSocioBean() {
		return this.socioBean;
	}

	public void setSocioBean(Socio socioBean) {
		this.socioBean = socioBean;
	}

}