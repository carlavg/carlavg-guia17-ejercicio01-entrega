package edu.egg.springboot.servicios;

import java.util.List;

import edu.egg.springboot.entidades.Autor;
import edu.egg.springboot.entidades.Cliente;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.repositorios.ClienteRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Transactional
	public void crear(Long documento, String nombre, String apellido, String telefono) throws ServicioExcepciones {

		this.validarDatos(documento, nombre, apellido, telefono);

		Cliente cliente = new Cliente();
		cliente.setDocumento(documento);
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setTelefono(telefono);
		cliente.setAlta(Boolean.TRUE);

		clienteRepositorio.save(cliente);

	}
	
	@Transactional
	public void modificar(Integer id, Long documento, String nombre, String apellido, String telefono) throws ServicioExcepciones {
		validarDatos(id);
		validarDatos(documento, nombre, apellido, telefono);

		Optional<Cliente> respuesta = clienteRepositorio.findById(id);

		if (respuesta.isPresent()) {

			Cliente cliente = respuesta.get();
			cliente.setDocumento(documento);
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setTelefono(telefono);
			cliente.setAlta(Boolean.TRUE);

			clienteRepositorio.save(cliente);
		} else {
			throw new ServicioExcepciones("No se pudo encontrar el cliente");
		}

	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() {
		return clienteRepositorio.findAll();
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Integer id) throws ServicioExcepciones {
		validarDatos(id);
		Optional<Cliente> respuesta = clienteRepositorio.findById(id);
		return respuesta.orElse(null);
	}
	
	public void validarDatos(Long documento, String nombre, String apellido, String telefono) throws ServicioExcepciones {

		if (documento == null) {
			throw new ServicioExcepciones("El documento del cliente no puede estar vacío");
		}
		
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new ServicioExcepciones("El nombre del cliente no puede estar vacío");
		}
		
		if (apellido == null || apellido.trim().isEmpty()) {
			throw new ServicioExcepciones("El apellido del cliente no puede estar vacío");
		}

	}
	
	public void validarDatos(Integer id) throws ServicioExcepciones {

		if (id == null) {
			throw new ServicioExcepciones("El id del cliente no puede estar vacío");
		}

	}

}
