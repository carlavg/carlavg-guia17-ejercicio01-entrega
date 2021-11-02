
package edu.egg.springboot.servicios;

import java.util.List;
import edu.egg.springboot.entidades.Editorial;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.repositorios.EditorialRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

	@Autowired
	private EditorialRepositorio editorialRepositorio;

	@Transactional
	public void crear(String nombre) throws Exception {

		validarDatos(nombre);

		Editorial editorial = new Editorial();
		editorial.setNombre(nombre);
		editorial.setAlta(Boolean.TRUE);

		editorialRepositorio.save(editorial);

	}

	@Transactional
	public void modificar(Integer id, String nombre) throws ServicioExcepciones {
		validarDatos(id);
		validarDatos(nombre);

		Optional<Editorial> respuesta = editorialRepositorio.findById(id);

		if (respuesta.isPresent()) {
			Editorial editorial = respuesta.get();
			editorial.setNombre(nombre);
			editorial.setAlta(Boolean.TRUE);

			editorialRepositorio.save(editorial);
		} else {
			throw new ServicioExcepciones("No se pudo encontrar la editorial por id");
		}
	}

	@Transactional(readOnly = true)
	public List<Editorial> buscarTodas() {
		return editorialRepositorio.findAll();
	}

	@Transactional(readOnly = true)
	public Editorial buscarPorId(Integer id) {
		Optional<Editorial> respuesta = editorialRepositorio.findById(id);
		return respuesta.orElse(null);
	}

	@Transactional
	public void eliminar(Integer id) {
		editorialRepositorio.deleteById(id);
	}

	public void bajaEditorial(Integer id) throws ServicioExcepciones {

		validarDatos(id);

		Optional<Editorial> respuesta = editorialRepositorio.findById(id);

		if (respuesta == null) {
			throw new ServicioExcepciones("No existe una editorial con ese ID");
		} else {

			Editorial editorial = respuesta.get();
			editorial.setAlta(Boolean.FALSE);

			editorialRepositorio.save(editorial);

		}
	}

	public void altaEditorial(Integer id) throws ServicioExcepciones {

		validarDatos(id);

		Optional<Editorial> respuesta = editorialRepositorio.findById(id);

		if (respuesta == null) {
			throw new ServicioExcepciones("No existe una editorial con ese ID");
		} else {

			Editorial editorial = respuesta.get();
			editorial.setAlta(Boolean.TRUE);

			editorialRepositorio.save(editorial);

		}
	}

	public void validarDatos(String nombre) throws ServicioExcepciones {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new ServicioExcepciones("El nombre de la editorial no puede estar vacío");
		}

	}

	public void validarDatos(Integer id) throws ServicioExcepciones {
		if (id == null) {
			throw new ServicioExcepciones("El id de la editorial no puede estar vacío");
		}

	}

}
