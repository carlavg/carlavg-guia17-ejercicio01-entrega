
package edu.egg.springboot.servicios;

import java.util.List;
import edu.egg.springboot.entidades.Autor;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.repositorios.AutorRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

	@Autowired
	private AutorRepositorio autorRepositorio;

	@Transactional
	public void crear(String nombre) throws ServicioExcepciones {

		validarDatos(nombre);

		Autor autor = new Autor();
		autor.setNombre(nombre);
		autor.setAlta(Boolean.TRUE);

		autorRepositorio.save(autor);

	}

	@Transactional
	public void modificar(Integer id, String nombre) throws ServicioExcepciones {
		validarDatos(id);
		validarDatos(nombre);

		Optional<Autor> respuesta = autorRepositorio.findById(id);

		if (respuesta.isPresent()) {

			Autor autor = respuesta.get();
			autor.setNombre(nombre);
			autor.setAlta(Boolean.TRUE);

			autorRepositorio.save(autor);
		} else {
			throw new ServicioExcepciones("No se pudo encontrar el autor");
		}

	}

	@Transactional(readOnly = true)
	public List<Autor> buscarTodos() {
		return autorRepositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Autor buscarPorId(Integer id) throws ServicioExcepciones {
		validarDatos(id);
		Optional<Autor> respuesta = autorRepositorio.findById(id);
		return respuesta.orElse(null);
	}

	@Transactional
	public void eliminar(Integer id) throws ServicioExcepciones {
		validarDatos(id);
		autorRepositorio.deleteById(id);
	}

	@Transactional
	public void baja(Integer id) throws ServicioExcepciones {

		validarDatos(id);

		Optional<Autor> respuesta = autorRepositorio.findById(id);

		if (respuesta == null) {
			throw new ServicioExcepciones("No existe un autor con ese ID");
		} else {

			Autor autor = respuesta.get();
			autor.setAlta(Boolean.FALSE);

			autorRepositorio.save(autor);

		}
	}

	@Transactional
	public void alta(Integer id) throws ServicioExcepciones {

		validarDatos(id);

		Optional<Autor> respuesta = autorRepositorio.findById(id);

		if (respuesta == null) {
			throw new ServicioExcepciones("No existe un autor con ese ID");
		} else {

			Autor autor = respuesta.get();
			autor.setAlta(Boolean.TRUE);

			autorRepositorio.save(autor);
		}
	}

	public void validarDatos(String nombre) throws ServicioExcepciones {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new ServicioExcepciones("El nombre del autor no puede estar vacío");
		}

	}

	public void validarDatos(Integer id) throws ServicioExcepciones {

		if (id == null) {
			throw new ServicioExcepciones("El id del autor no puede estar vacío");
		}

	}

}
