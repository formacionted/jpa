package com.telefonica.jee.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the socio database table.
 * 
 */
@Entity
@Table(name="libro")
@NamedQuery(name="Libro.findAll", query="SELECT l FROM Libro l")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String titulo;
	
	private String editorial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_autor") //indica a JPA que tiene que guardar el autor en una columna "id_autor" dentro de la tabla Libro
	private Autor autor;

	public Libro() {
	}

	public Libro(int id, String titulo, String editorial, Autor autor) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.editorial = editorial;
		this.autor = autor;
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

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", editorial=" + editorial + ", autor=" + autor.getId() + "]";
	}
	

	
}