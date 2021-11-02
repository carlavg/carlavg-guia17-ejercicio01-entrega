package edu.egg.springboot.controladores;

import edu.egg.springboot.entidades.Cliente;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {
	
	@Autowired
	private ClienteServicio clienteServicio;
	
	@GetMapping
	public ModelAndView mostrarTodos() {
		ModelAndView mav = new ModelAndView("clientes");
		mav.addObject("listaClientes", clienteServicio.buscarTodos());
		return mav;
	}
	
	@GetMapping("/crear")
	public ModelAndView crearCliente() {
		ModelAndView mav = new ModelAndView("cliente-formulario");
		mav.addObject("cliente", new Cliente());
		mav.addObject("title", "Crear Cliente");
		mav.addObject("action", "guardar");
		return mav;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable Integer id) throws ServicioExcepciones {
		ModelAndView mav = new ModelAndView("cliente-formulario");
		mav.addObject("cliente", clienteServicio.buscarPorId(id));
		mav.addObject("title", "Editar Cliente");
		mav.addObject("action", "modificar");
		return mav;
	}

	
	@PostMapping("/guardar")
	public RedirectView guardar(@RequestParam Long documento, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String telefono) throws ServicioExcepciones {
		clienteServicio.crear(documento, nombre, apellido, telefono);
		return new RedirectView("/clientes");
	}
	
	@PostMapping("/modificar")
	public RedirectView modificar(@RequestParam Integer id, @RequestParam Long documento, @RequestParam String nombre,
			@RequestParam String apellido, @RequestParam String telefono) throws ServicioExcepciones {
		clienteServicio.modificar(id, documento, nombre, apellido, telefono);
		return new RedirectView("/clientes");
	}

}
