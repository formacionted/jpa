package com.telefonica.jee.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@Table(name="producto")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproducto;

	private String nombre;

	private BigDecimal precio;

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="productoBean")
	private List<Venta> ventas;

	public Producto() {
	}

	
	public int getIdproducto() {
		return this.idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setProductoBean(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setProductoBean(null);

		return venta;
	}
	
    @Override
    public String toString() {
        return "Producto{" +
            "idProducto=" + getIdproducto() +
            ", nombre='" + getNombre() + "'" +
            ", precio='" + getPrecio() + "'" +
            "}";
    }

	public Producto(String nombre, BigDecimal precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}
	
	@PostPersist 
	public void listenerPostPersist() {
		System.out.println("ESCUCHADOR: Se ha creado el producto " +  this.getNombre());
	}
    

}