package com.telefonica.jee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the etiqueta database table.
 * 
 */
@Entity
@Table(name="etiqueta")
@NamedQuery(name="Etiqueta.findAll", query="SELECT e FROM Etiqueta e")
public class Etiqueta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre;
	
	@ManyToMany(mappedBy = "etiquetas")
	private List<Articulo> articulos = new ArrayList<>();
	
	public Etiqueta() {
	}

	public Etiqueta(int id, String nombre, List<Articulo> articulos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.articulos = articulos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Etiqueta [id=" + id + ", nombre=" + nombre + ", articulos: " + articulos.size() + "]";
	}
	
}