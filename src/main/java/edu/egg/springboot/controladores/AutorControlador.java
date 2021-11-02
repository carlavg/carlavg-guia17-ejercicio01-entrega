package edu.egg.springboot.controladores;

import edu.egg.springboot.entidades.Autor;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.servicios.AutorServicio;
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
@RequestMapping("/autores")
public class AutorControlador {

	@Autowired
	private AutorServicio autorServicio;

	@GetMapping
	public ModelAndView mostrarTodos() {
		ModelAndView mav = new ModelAndView("autores");
		mav.addObject("listaAutores", autorServicio.buscarTodos());
		return mav;
	}

	@GetMapping("/crear")
	public ModelAndView crearAutor() {
		ModelAndView mav = new ModelAndView("autor-formulario");
		mav.addObject("autor", new Autor());
		mav.addObject("title", "Crear Autor");
		mav.addObject("action", "guardar");
		return mav;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editarAutor(@PathVariable Integer id) throws ServicioExcepciones {
		ModelAndView mav = new ModelAndView("autor-formulario");
		mav.addObject("autor", autorServicio.buscarPorId(id));
		mav.addObject("title", "Editar Autor");
		mav.addObject("action", "modificar");
		return mav;
	}

	@PostMapping("/guardar")
	public RedirectView guardar(@RequestParam String nombre) throws ServicioExcepciones {
		autorServicio.crear(nombre);
		return new RedirectView("/autores");
	}

	@PostMapping("/modificar")
	public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombre) throws ServicioExcepciones {
		autorServicio.modificar(id, nombre);
		return new RedirectView("/autores");
	}

	@PostMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable Integer id) throws ServicioExcepciones {
		autorServicio.eliminar(id);
		return new RedirectView("/autores");
	}

}
