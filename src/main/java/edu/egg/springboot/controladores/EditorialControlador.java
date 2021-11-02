package edu.egg.springboot.controladores;

import edu.egg.springboot.entidades.Editorial;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.servicios.EditorialServicio;
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
@RequestMapping("/editoriales")
public class EditorialControlador {

	@Autowired
	private EditorialServicio editorialServicio;

	@GetMapping
	public ModelAndView mostrarTodos() {
		ModelAndView mav = new ModelAndView("editoriales");
		mav.addObject("listaEditoriales", editorialServicio.buscarTodas());
		return mav;
	}

	@GetMapping("/crear")
	public ModelAndView crearEditorial() {
		ModelAndView mav = new ModelAndView("editorial-formulario");
		mav.addObject("editorial", new Editorial());
		mav.addObject("title", "Crear Editorial");
		mav.addObject("action", "guardar");
		return mav;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editarEditorial(@PathVariable Integer id) throws ServicioExcepciones {
		ModelAndView mav = new ModelAndView("editorial-formulario");
		mav.addObject("editorial", editorialServicio.buscarPorId(id));
		mav.addObject("title", "Editar Editorial");
		mav.addObject("action", "modificar");
		return mav;
	}

	@PostMapping("/guardar")
	public RedirectView guardar(@RequestParam String nombre) throws ServicioExcepciones, Exception {
		editorialServicio.crear(nombre);
		return new RedirectView("/editoriales");
	}

	@PostMapping("/modificar")
	public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombre) throws ServicioExcepciones {
		editorialServicio.modificar(id, nombre);
		return new RedirectView("/editoriales");
	}

	@PostMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable Integer id) throws ServicioExcepciones {
		editorialServicio.eliminar(id);
		return new RedirectView("/editoriales");
	}

}
