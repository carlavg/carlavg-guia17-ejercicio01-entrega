
package edu.egg.springboot.excepciones;

public class ServicioExcepciones extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicioExcepciones() {
	}

	public ServicioExcepciones(String msg) {
		super(msg);
	}

}
