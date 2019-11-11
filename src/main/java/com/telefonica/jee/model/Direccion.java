package com.telefonica.jee.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the socio database table.
 * 
 */
@Entity
@Table(name="direccion")
@NamedQuery(name="Direccion.findAll", query="SELECT d FROM Direccion d")
public class Direccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddireccion;

	private String direccion;

	private String pais;
	
	@OneToOne(mappedBy = "direccion", fetch = FetchType.LAZY) //mappedBy indica que no es el owner, lo es la otra parte
	private Empleado empleado;

	public Direccion() {
	}

	public Direccion(int iddireccion, String direccion, String pais) {
		this.iddireccion = iddireccion;
		this.direccion = direccion;
		this.pais = pais;
	}

	public int getIddireccion() {
		return iddireccion;
	}

	public void setIddireccion(int iddireccion) {
		this.iddireccion = iddireccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Direccion [iddireccion=" + iddireccion + ", direccion=" + direccion + ", pais=" + pais + ", empleado="
				+ empleado + "]";
	}
	
	
}