package com.telefonica.jee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.telefonica.jee.model.Articulo;
import com.telefonica.jee.model.Autor;
import com.telefonica.jee.model.Direccion;
import com.telefonica.jee.model.Empleado;
import com.telefonica.jee.model.Etiqueta;
import com.telefonica.jee.model.Libro;
import com.telefonica.jee.model.Producto;
import com.telefonica.jee.model.Socio;
import com.telefonica.jee.model.Venta;

public class Main {

	// manager (gestor de persistencia)
	static EntityManagerFactory emf;
	static EntityManager manager;

	public static void main(String[] args) {
		emf = Persistence.createEntityManagerFactory("jpa");
		
		// Ejemplos de diferentes tipos de queries
		createQuery();
		createNamedQuery();
		createNativeQuery();
		createQueryWithParametersByPosition();
		createQueryWithParametersByName();
		getNumberOfProducts();

		// Ejemplos de transacciones
		persistProductos();
		findModifyProductos(2, "nombre editado");
		mergeProducto();
		removeProducto(1);

		// Ejemplos asociaciones
		oneToOneAssociation();
		oneToManyAssociation();
		manyToOneAssociation();
		manyToManyAssociation();
		
		
		getNumberOfProducts();

		
		// Error Lazy Initialization: solucionar con EAGER o utilizar un .size() cuando consideremos para forzar a JPA recuperar esos datos

		// Como evitar borrar los elementos hijos de una entidad cuando ésta es borrada
		// utilizar Preremove y poner en esa tabla la relación a null
		// Por ejemplo si queremos borrar un autor y mantener los libros
		
		emf.close();
	}

	private static void getNumberOfProducts() {
		initializeManager();
		
		TypedQuery<Long> query = manager.createQuery(
				"SELECT COUNT(p) FROM Producto p", Long.class);
		Long numprod = query.getSingleResult();
		System.out.println("El numero de productos es: " + numprod);
		closeManager();
	}

	private static void manyToManyAssociation() {

		Etiqueta etiqueta1 = new Etiqueta();
		etiqueta1.setNombre("etiqueta1");
		Etiqueta etiqueta2 = new Etiqueta();
		etiqueta2.setNombre("etiqueta2");
		Etiqueta etiqueta3 = new Etiqueta();
		etiqueta3.setNombre("etiqueta3");
		
		Articulo articulo1 = new Articulo();
		articulo1.setTitulo("articulo1");

		Articulo articulo2 = new Articulo();
		articulo2.setTitulo("articulo2");
		
		
		initializeManager();
		
		manager.getTransaction().begin();
		manager.persist(etiqueta1);
		manager.persist(etiqueta2);
		manager.persist(etiqueta3);

		manager.persist(articulo1);
		manager.persist(articulo2);
		
		articulo1.getEtiquetas().add(etiqueta1);
		etiqueta1.getArticulos().add(articulo1);
		
		articulo2.getEtiquetas().add(etiqueta2);
		articulo2.getEtiquetas().add(etiqueta3);
		etiqueta2.getArticulos().add(articulo2);
		etiqueta3.getArticulos().add(articulo2);

		System.out.println(articulo1.toString());
		System.out.println(articulo2.toString());
		System.out.println(etiqueta1.toString());
		System.out.println(etiqueta2.toString());
		System.out.println(etiqueta3.toString());
		
		manager.getTransaction().commit();
		
	}

	private static void manyToOneAssociation() {
		Libro libro = new Libro();
		libro.setTitulo("El libro negro del programador");
		libro.setEditorial("Wenceslao");
		
		Libro libro2 = new Libro();
		libro2.setTitulo("Magias");
		libro2.setEditorial("Wenceslao");
		
		Autor autor = new Autor();
		autor.setNombre("Quevedo");
		autor.setNacionalidad("Española");
		libro.setAutor(autor);
		libro2.setAutor(autor);
		
		autor.getLibros().addAll(Arrays.asList(new Libro[] {libro, libro2}));


		initializeManager();
		
		manager.getTransaction().begin();
		manager.persist(autor);
		manager.getTransaction().commit();
		
		// Actualizamos un libro
		manager.getTransaction().begin();
		libro.setEditorial("Changed book editorial");
		manager.merge(libro);
		manager.getTransaction().commit();
		
		
		// Solucion al error No se fijan entidades en relacion bidireccional
		// 1 - añadirlo a mano
		// 2 - añadir en el metodo setlibro un setAuthor
		
		// No guarda porque no se hace en los dos lados
		manager.getTransaction().begin();
		manager.remove(libro);
		manager.getTransaction().commit();
		
		// Para poder borrar el libro tenemos que borrarlo desde ambos lados
		manager.getTransaction().begin();
		autor.getLibros().remove(0);
		manager.remove(libro);
		manager.getTransaction().commit();
//		
		closeManager();
	}

	private static void oneToManyAssociation() {

		Libro libro = new Libro();
		libro.setTitulo("Clean code");
		libro.setEditorial("Wenceslao");
		
		Libro libro2 = new Libro();
		libro2.setTitulo("java POO");
		libro2.setEditorial("Wenceslao");
		
		Autor autor = new Autor();
		autor.setNombre("Matuchi");
		autor.setNacionalidad("Española");

		libro.setAutor(autor);
		libro2.setAutor(autor);

		initializeManager();
		
		manager.getTransaction().begin();
		manager.persist(autor);
		manager.persist(libro);
		manager.persist(libro2);
		manager.getTransaction().commit();
		
		manager.getTransaction().begin();
		manager.remove(libro);
		manager.getTransaction().commit();
		
		closeManager();
		
		
	}

	private static void oneToOneAssociation() {

		Empleado empleado = new Empleado();
		Direccion direccion = new Direccion();
		direccion.setDireccion("Avenida Padre Isla");
		direccion.setPais("Spain");
		empleado.setNombre("Farinfanfunfi");
		empleado.setEdad(22);
		empleado.setDireccion(direccion);
		
		initializeManager();
		manager.getTransaction().begin();
		manager.persist(empleado);
		manager.getTransaction().commit();
		
		// Para borrarlo (borra la direccion tambien)
//		transaction.begin();
//		manager.remove(empleado);
//		transaction.commit();
		
		closeManager();
		
	}

	private static void removeProducto(int productoId) {
		
		initializeManager();
		
		manager.getTransaction().begin();
		Producto paquito = manager.find(Producto.class, productoId);
		manager.remove(paquito);
		manager.getTransaction().commit();
		
		closeManager();
	}

	/**
	 * merge acepta entidades que no tienen porque ser managed 
*  no confundir con persist
	 */
	private static void mergeProducto() {
// Creamos un nuevo manager para mostrar como funciona una entidad detached y el metodo merge
		initializeManager();
		
		Producto calculadora = new Producto();
		calculadora.setNombre("Calculadora Casio");
		calculadora.setPrecio(new BigDecimal(19.99));
		manager.getTransaction().begin();
		manager.persist(calculadora);
		manager.getTransaction().commit();
		manager.close();
		
		// man.close convierte la entidad en detached, por lo que al cambiar el nombre y guardar con persist no surte efecto
		initializeManager();
		manager.getTransaction().begin();


		try {
			manager.persist(calculadora);
		} catch (PersistenceException e) {
			// Se soluciona con merge
			System.out.println("Capturado error detached entity " + e.getMessage());
			manager.close();
		}
		
		// Se soluciona con merge
		initializeManager();
		manager.getTransaction().begin();
		calculadora.setNombre("Detached calculator");
		manager.merge(calculadora);
		manager.getTransaction().commit();
		
		closeManager();
	}

	/**
	 * Encuentra un producto por su identificador y modifica su nombre
	 */
	private static void findModifyProductos(int productoId, String nombre) {
		
		initializeManager();
		
		manager.getTransaction().begin();
		Producto producto1 = manager.find(Producto.class, productoId);
		producto1.setNombre(nombre);
		manager.getTransaction().commit();
		
		closeManager();
	}



	/**
	 * Crea y almacena productos
	 */
	private static void persistProductos() {
		initializeManager();
		
		Producto p1 = new Producto("Tornillo simétrico", new BigDecimal(1.57));
		Producto p2 = new Producto("Arandela simétrica ", new BigDecimal(1.57));
		Producto p3 = new Producto("Tuerca simétrica ", new BigDecimal(1.57));
		Producto p4 = new Producto("Presilla simétrica ", new BigDecimal(1.57));
		Socio socio = new Socio();
		socio.setNombre("Pepe");
		socio.setEdad(19);
		socio.setCuota(new BigDecimal(2.3));
		socio.setFecha(new Date());
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			socio.setFecha(sdf.parse("21/12/2012"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			manager.getTransaction().begin();   
			manager.persist(p1); 
			manager.persist(p2); 
			manager.persist(p3);
			manager.persist(p4);
			manager.persist(socio);
			p1.setNombre("paquito");
			manager.getTransaction().commit();    
		}catch(Exception ex){
			ex.printStackTrace();
			manager.getTransaction().rollback();   
		}finally{
			closeManager();
		}		
	}

	/**
	 * Ejemplo de ejecucion de una sentencia jpql con parametros por nombre
	 */
	private static void createQueryWithParametersByName() {
		initializeManager();
		
		String jpql = "SELECT p FROM Producto p WHERE p.nombre = :nombre AND p.precio > :precio ";
		Query query = manager.createQuery(jpql);
		query.setParameter("nombre", "Tuerca%");
		query.setParameter("precio", new BigDecimal(34.5));
		List<Producto> products = query.getResultList();
		for (Producto product : products) {
			System.out.print(product.getIdproducto() + " === ");
			System.out.print(product.getNombre() + " === ");
			System.out.println(product.getPrecio() + " === ");
			List<Venta> ventas = product.getVentas();
			for (Venta v : ventas) {
				System.out.print("=====" + v.getIdventas() + " === ");
				System.out.println(v.getCantidad());
			}
		}
		
		closeManager();
	}

	/**
	 * Ejemplo de ejecucion de una sentencia jpql con parametros por posicion
	 */
	private static void createQueryWithParametersByPosition() {
		initializeManager();
		
		String jpql = "SELECT p FROM Producto p WHERE p.nombre = ?1 AND p.precio > ?2 ";
		Query query = manager.createQuery(jpql);
		query.setParameter(1, "Tuerca%");
		query.setParameter(2, new BigDecimal(34.5));

		List<Producto> products = query.getResultList();
		for (Producto product : products) {
			System.out.print(product.getIdproducto() + " === ");
			System.out.print(product.getNombre() + " === ");
			System.out.println(product.getPrecio() + " === ");
			List<Venta> ventas = product.getVentas();
			for (Venta v : ventas) {
				System.out.print("=====" + v.getIdventas() + " === ");
				System.out.println(v.getCantidad());
			}
		}
		
		closeManager();
	}

	/**
	 * Ejemplo de ejecucion de una sentencia sql
	 */
	private static void createNativeQuery() {

		initializeManager();
		System.out.println(" \n\n ejemplo createNativeQuery");
		String sql = "SELECT * FROM producto";
		Query nativeQuery = manager.createNativeQuery(sql);
		List<Object[]> products = nativeQuery.getResultList();
		for (Object[] product : products) {
			
			System.out.print(product[0] + " === ");
			System.out.print(product[1] + " === ");
			System.out.println(product[2] + " === ");
			
		}
		
		closeManager();
	}

	/**
	 * Ejemplo de ejecución de una sentencia jpql
	 */
	private static void createQuery() {
		initializeManager();

		System.out.println(" \n\n ejemplo createQuery");
		String jpql = "SELECT p FROM Producto p";
		Query query = manager.createQuery(jpql);
		List<Producto> products = query.getResultList();
		for (Producto product : products) {
			System.out.print(product.getIdproducto() + " === ");
			System.out.print(product.getNombre() + " === ");
			System.out.println(product.getPrecio() + " === ");
			List<Venta> ventas = product.getVentas();
			for (Venta v : ventas) {
				System.out.print("=====" + v.getIdventas() + " === ");
				System.out.println(v.getCantidad());
			}
		}
		closeManager();
		
	}

	/**
	 * Ejemplo de ejecución de una sentencia jpql predefinida en la clase Producto
	 */
	private static void createNamedQuery() {

		initializeManager();
		
		System.out.println("ejemplo createNamedQuery");
		TypedQuery<Producto> namedQuery = manager.createNamedQuery("Producto.findAll", Producto.class);
		List<Producto> products = namedQuery.getResultList();
		for (Producto product : products) {
			System.out.print(product.getIdproducto() + " === ");
			System.out.print(product.getNombre() + " === ");
			System.out.println(product.getPrecio() + " === ");
			List<Venta> ventas = product.getVentas();
			for (Venta v : ventas) {
				System.out.print("=====" + v.getIdventas() + " === ");
				System.out.println(v.getCantidad());
			}
		}
		
		closeManager();
	}
	
	private static void closeManager() {
		manager.close();
	}

	private static void initializeManager() {
		manager = emf.createEntityManager();
	}
	

}
