package com.telefonica.jee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the articulo database table.
 * 
 */
@Entity
@Table(name="articulo")
@NamedQuery(name="Articulo.findAll", query="SELECT a FROM Articulo a")
public class Articulo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "descripcion")
	private String descripcion;

	@ManyToMany
	@JoinTable(name = "articulo_etiqueta",
	joinColumns = @JoinColumn(name = "articulo_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "etiqueta_id", referencedColumnName = "id"))
	private List<Etiqueta> etiquetas = new ArrayList<>();

	public Articulo() {
	}
	
	public Articulo(int id, String titulo, String descripcion, List<Etiqueta> etiquetas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.etiquetas = etiquetas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Articulo [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", num etiquetas: " + etiquetas.size() + "]";
	}

	
	
}