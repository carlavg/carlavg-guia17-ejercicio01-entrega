package edu.egg.springboot.controladores;

import edu.egg.springboot.entidades.Libro;
import edu.egg.springboot.excepciones.ServicioExcepciones;
import edu.egg.springboot.servicios.AutorServicio;
import edu.egg.springboot.servicios.EditorialServicio;
import edu.egg.springboot.servicios.LibroServicio;
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
@RequestMapping("/libros")
public class LibroControlador {

	@Autowired
	private LibroServicio libroServicio;

	@Autowired
	private AutorServicio autorServicio;

	@Autowired
	private EditorialServicio editorialServicio;

	@GetMapping
	public ModelAndView mostrarTodos() {
		ModelAndView mav = new ModelAndView("libros");
		mav.addObject("listaLibros", libroServicio.buscarTodos());
		return mav;
	}

	@GetMapping("/crear")
	public ModelAndView crearLibro() {
		ModelAndView mav = new ModelAndView("libro-formulario");
		mav.addObject("libro", new Libro());
		mav.addObject("editoriales", editorialServicio.buscarTodas());
		mav.addObject("autores", autorServicio.buscarTodos());
		mav.addObject("title", "Crear Libro");
		mav.addObject("action", "guardar");
		return mav;
	}

	@GetMapping("/editar/{isbn}")
	public ModelAndView editarLibro(@PathVariable long isbn) throws ServicioExcepciones {
		ModelAndView mav = new ModelAndView("libro-formulario");
		mav.addObject("libro", libroServicio.buscarPorIsbn(isbn));
		mav.addObject("editoriales", editorialServicio.buscarTodas());
		mav.addObject("autores", autorServicio.buscarTodos());
		mav.addObject("title", "Editar Libro");
		mav.addObject("action", "modificar");
		return mav;
	}

	@PostMapping("/modificar")
	public RedirectView modificar(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
			@RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
			@RequestParam Integer ejemplaresRestantes, @RequestParam Integer editorial, @RequestParam Integer autor)
			throws ServicioExcepciones {

		libroServicio.modificarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes,
				autorServicio.buscarPorId(autor), editorialServicio.buscarPorId(editorial));
		return new RedirectView("/libros");
	}

	@PostMapping("/guardar")
	public RedirectView guardar(@RequestParam String titulo, @RequestParam Integer anio,
			@RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
			@RequestParam Integer ejemplaresRestantes, @RequestParam Integer editorial, @RequestParam Integer autor)
			throws Exception {
		libroServicio.crearLibro(titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes,
				editorialServicio.buscarPorId(editorial), autorServicio.buscarPorId(autor));
		return new RedirectView("/libros");
	}

	@PostMapping("/eliminar/{isbn}")
	public RedirectView eliminar(@PathVariable long isbn) throws ServicioExcepciones {
		libroServicio.eliminar(isbn);
		return new RedirectView("/libros");
	}

	@PostMapping("/darAlta/{isbn}")
	public RedirectView darAlta(@PathVariable long isbn) throws ServicioExcepciones {
		libroServicio.darAlta(isbn);
		return new RedirectView("/libros");
	}
}