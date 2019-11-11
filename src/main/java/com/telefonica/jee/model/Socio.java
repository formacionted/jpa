package com.telefonica.jee.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the socio database table.
 * 
 */
@Entity
@Table(name="socio")
@NamedQuery(name="Socio.findAll", query="SELECT s FROM Socio s")
public class Socio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsocio;

	private BigDecimal cuota;

	private int edad;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String nombre;

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="socioBean")
	private List<Venta> ventas;

	public Socio() {
	}

	public int getIdsocio() {
		return this.idsocio;
	}

	public void setIdsocio(int idsocio) {
		this.idsocio = idsocio;
	}

	public BigDecimal getCuota() {
		return this.cuota;
	}

	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}

	public int getEdad() {
		return this.edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setSocioBean(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setSocioBean(null);

		return venta;
	}

}