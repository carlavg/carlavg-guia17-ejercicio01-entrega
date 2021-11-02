
package edu.egg.springboot.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Long documento;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellido;
	
	private String telefono;
	
	private Boolean alta;

	public Cliente() {
		super();
	}

	public Cliente(Integer id, Long documento, String nombre, String apellido, String telefono, Boolean alta) {
		super();
		this.id = id;
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.alta = alta;
	}

//	@Override
//	public String toString() {
//		return "Cliente [id=" + id + ", documento=" + documento + ", nombre=" + nombre + ", apellido=" + apellido
//				+ ", telefono=" + telefono + ", alta=" + alta + "]";
//	}
	
}