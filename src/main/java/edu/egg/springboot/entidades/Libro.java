
package edu.egg.springboot.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.SQLDelete;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Libro SET alta = false WHERE isbn = ?;")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long isbn; 
	@Column(nullable = false)
	private String titulo;
	private Integer anio;
	@Column(nullable = false)
	private Integer ejemplares;
	@Column(nullable = false)
	private Integer ejemplaresPrestados;
	@Column(nullable = false)
	private Integer ejemplaresRestantes;
	private Boolean alta;

	@JoinColumn(nullable = false)
	@ManyToOne
	private Autor autor;
	@JoinColumn(nullable = false)
	@ManyToOne
	private Editorial editorial;

	public Libro() {
		autor = new Autor();
		editorial = new Editorial();
		alta = true;
	}

	public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.anio = anio;
		this.ejemplares = ejemplares;
		this.ejemplaresPrestados = ejemplaresPrestados;
		this.ejemplaresRestantes = ejemplaresRestantes;
		this.alta = alta;
		this.autor = autor;
		this.editorial = editorial;
	}

}
