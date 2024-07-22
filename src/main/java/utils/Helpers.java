package utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jakarta.xml.bind.*;
import models.*;

// Adopting the Singleton method for this class instance
public enum Helpers {
	INSTANCE;
	
//	Added a helper method `createGson` to reduce repetition in creating `Gson` instances.
	    private Gson createGson(boolean prettyPrint) {
	        GsonBuilder builder = new GsonBuilder();
	        if (prettyPrint) {
	            builder.setPrettyPrinting();
	        }
	        return builder.create();
	    }

	    public String convertFilmsToJson(ArrayList<Film> films) {
	        Gson gson = createGson(true);
	        return gson.toJson(films);
	    }
	    
	    public ArrayList<Film> convertJsonToFilms(String json){
	        Gson gson = createGson(false);
	        return gson.fromJson(json, new TypeToken<ArrayList<Film>>() {}.getType());
	    }

	    public Film convertJsonToFilm(String json) {
	        Gson gson = createGson(false);
	        return gson.fromJson(json, Film.class);
	    }

	    public String convertFilmToJson(Film film){
	        Gson gson = createGson(true);
	        return gson.toJson(film);
	    }

	    public String convertObjectToJson(Object object){
	        Gson gson = createGson(true);
	        return gson.toJson(object);
	    }
	    
	    public String convertFilmsToXml(ArrayList<Film> films) throws JAXBException {
	        StringWriter writer = new StringWriter();
	        JAXBContext context = JAXBContext.newInstance(FilmList.class);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        
	        FilmList filmList = new FilmList(films);
	        
	        marshaller.marshal(filmList, writer);
	        return writer.toString();
	    }

	    public ArrayList<Film> convertXmlToFilms(String xml) throws JAXBException{
	        JAXBContext context = JAXBContext.newInstance(FilmList.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        StringReader reader = new StringReader(xml);
	        FilmList filmList = (FilmList) unmarshaller.unmarshal(reader);
	        return filmList.getFilms();
	    }

	    public String convertFilmToXml(Film film) throws JAXBException {
	        StringWriter writer = new StringWriter();
	        JAXBContext context = JAXBContext.newInstance(Film.class);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        
	        marshaller.marshal(film, writer);
	        return writer.toString();
	    }

	    public String convertObjectToXml(Object object) throws JAXBException {
	        StringWriter writer = new StringWriter();
	        JAXBContext context = JAXBContext.newInstance(object.getClass());
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        
	        marshaller.marshal(object, writer);
	        return writer.toString();
	    }

	    public Film convertXmlToFilm(String xml) throws JAXBException {
	        JAXBContext context = JAXBContext.newInstance(Film.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        StringReader reader = new StringReader(xml);
	        return (Film) unmarshaller.unmarshal(reader);
	    }

	    public String convertFilmToText(Film film) {
	        return String.format("%d\t%s\t%d\t%s\t%s\t%s", 
	                             film.getId(), film.getTitle(), film.getYear(), 
	                             film.getDirector(), film.getStars(), film.getReview());
	    }

	    public String convertFilmsToText(ArrayList<Film> films) {
	        return films.stream()
	                    .map(this::convertFilmToText)
	                    .collect(Collectors.joining("\n"));
	    }

	    public Film convertTextToFilm(String text) {
	        String[] parts = text.split("\t");
	        Film film = new Film();
	        film.setId(Integer.parseInt(parts[0]));
	        film.setTitle(parts[1]);
	        film.setYear(Integer.parseInt(parts[2]));
	        film.setDirector(parts[3]);
	        film.setStars(parts[4]);
	        film.setReview(parts.length > 5 ? parts[5] : "");
	        return film;
	    }

	    public ArrayList<Film> convertTextToFilms(String text) {
	        return Arrays.stream(text.split("\n"))
	                     .filter(line -> !line.trim().isEmpty())
	                     .map(this::convertTextToFilm)
	                     .collect(Collectors.toCollection(ArrayList::new));
	    }
}
