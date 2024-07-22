package controllers;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*import org.json.*;*/
import database.FilmDAO;
import jakarta.xml.bind.JAXBException;
import models.Film;
import utils.Helpers;

/*@WebServlet("/FilmApi")*/
@WebServlet("/api")
public class FilmApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO filmdao = new FilmDAO();
		Helpers helpers = Helpers.INSTANCE;
		ArrayList<Film> allFilms = filmdao.getAllFilms();
		
		// Convert response to json
		PrintWriter out = response.getWriter();
		
		// Convert the ArrayList to JSON
		String accept = request.getHeader("Accept");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String data = "";
		if (accept.contains("application/json")) {
		data = gson.toJson(allFilms);
		}else if(accept.contains("application/xml")) {
		try {
			data = helpers.convertFilmsToXml(allFilms);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}else if(accept.contains("text/html")) {
			data = helpers.convertFilmsToText(allFilms);
		}
		out.print(data);
		out.flush();
		
		/*
		 * request.setAttribute("allFilms", allFilms); RequestDispatcher view =
		 * request.getRequestDispatcher("filmapi.jsp"); view.forward(request, response);
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestHandler(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestHandler(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestHandler(request, response);
	}
	
	protected void requestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contentType = request.getContentType();
		switch(contentType) {
		case "application/json":
			jsonRequestHandler(request, response);
			break;
		case "application/xml":
			xmlRequestHandler(request, response);
			break;
		case "text/plain":
			textRequestHandler(request, response);
			break;
		default: 
			response.setContentType("text/plain");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Unsupported content type: " + contentType);
		}
	}
	
	// Parsing and responding to the different data formats
	private void jsonRequestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
	}
	private void xmlRequestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}
	private void textRequestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}
}
