
package edu.egg.springboot.servicios;

import edu.egg.springboot.entidades.Autor;
import edu.egg.springboot.entidades.Editorial;
import java.util.List;
import edu.egg.springboot.entidades.Libro;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.repositorios.LibroRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

	@Autowired
	private LibroRepositorio libroRepositorio;

	@Transactional
	public void crearLibro(String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, Editorial editorial, Autor autor) throws Exception {

		validarDatos(titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

		Libro libro = new Libro();
		libro.setTitulo(titulo);
		libro.setEjemplares(ejemplares);
		libro.setAnio(anio);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes(ejemplaresRestantes);
		libro.setAutor(autor);
		libro.setEditorial(editorial);

		libroRepositorio.save(libro);

	}

	@Transactional
	public void modificarLibro(long id, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, Autor autor, Editorial editorial) throws ServicioExcepciones {

		validarDatos(id);
		validarDatos(titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

		Optional<Libro> respuesta = libroRepositorio.findById(id);

		if (respuesta.isPresent()) {

			Libro libro = respuesta.get();
			libro.setAnio(anio);
			libro.setTitulo(titulo);
			libro.setEjemplares(ejemplares);
			libro.setEjemplaresRestantes(ejemplaresRestantes);
			libro.setEjemplaresPrestados(ejemplaresPrestados);
			libro.setEditorial(editorial);
			libro.setAutor(autor);

			libroRepositorio.save(libro);
		} else {
			throw new ServicioExcepciones("No se ha encontrado el libro solicitado");
		}

	}

	@Transactional(readOnly = true)
	public List<Libro> buscarTodos() {
		return libroRepositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Libro buscarPorIsbn(Long isbn) throws ServicioExcepciones {
		validarDatos(isbn);
		Optional<Libro> respuesta = libroRepositorio.findById(isbn);
		return respuesta.orElse(null);
	}

	@Transactional
	public void eliminar(long isbn) throws ServicioExcepciones {
		validarDatos(isbn);
		libroRepositorio.deleteById(isbn);
	}

	@Transactional
	public void darAlta(long isbn) throws ServicioExcepciones {
		validarDatos(isbn);
		Libro libro = buscarPorIsbn(isbn);
		libro.setAlta(Boolean.TRUE);
		libroRepositorio.save(libro);
	}

	public void validarDatos(Long isbn) throws ServicioExcepciones {
		if (isbn == null) {
			throw new ServicioExcepciones("El isbn del libro no puede estar vac??o");
		}
	}

	public void validarDatos(String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes) throws ServicioExcepciones {

		try {

			if (titulo == null || titulo.trim().isEmpty()) {
				throw new ServicioExcepciones("El t??tulo del libro no puede estar vac??o");
			}

			if (anio == null) {
				throw new ServicioExcepciones("El a??o de publicaci??n del libro no puede estar vac??o");
			}

			if (ejemplares == null) {
				throw new ServicioExcepciones("La cantidad de ejemplares del libro no puede estar vac??a");
			}

			if (ejemplaresPrestados == null) {
				throw new ServicioExcepciones("La cantidad de ejemplares prestados del libro no puede estar vac??a");
			}

			if (ejemplaresRestantes == null) {
				throw new ServicioExcepciones("La cantidad de ejemplares restantes del libro no puede estar vac??a");
			}
		} catch (ServicioExcepciones e) {
			throw new ServicioExcepciones("Error al validar los datos");
		}

	}

}
