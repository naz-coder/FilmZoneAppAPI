package models;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="filmlist")
public class FilmList {
	@XmlElement(name="film")
	private ArrayList<Film> films;

	public FilmList(ArrayList<Film> films) {
		super();
		this.films = films;
	}

	public ArrayList<Film> getFilms() {
		return films;
	}

	public void setFilms(ArrayList<Film> films) {
		this.films = films;
	}

	public FilmList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
