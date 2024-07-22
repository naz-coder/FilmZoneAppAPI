package database;

import models.Film;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.sql.*;

public class FilmDAO {

	Film oneFilm = null;
	Connection conn = null;
	Statement stmt = null;
	String user = "anajembf";
	String password = "brIsterg7";
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

	public FilmDAO() {
	}

	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Film getNextFilm(ResultSet rs) {
		Film thisFilm = null;
		try {
			thisFilm = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("stars"), rs.getString("review"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thisFilm;
	}

	public ArrayList<Film> getAllFilms() {

		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "select * from films";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
				allFilms.add(oneFilm);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;
	}

	public Film getFilmByID(int id) {

		openConnection();
		oneFilm = null;
		// Create select statement and execute it
		try {
			String selectSQL = "select * from films where id=" + id;
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return oneFilm;
	}

// Add new film to the database
	public void InsertFilm(Film newfilm) {
		if (getFilmByID(newfilm.getId()) != null) {
			System.out.println("Film already exists");
			return;
		}
		openConnection();
		try {
			String selectSQL = "insert into films (id, title, year, director, stars, review) VALUES (" + newfilm.getId()
					+ ", '" + newfilm.getTitle() + "', " + newfilm.getYear() + ", '" + newfilm.getDirector() + "', '"
					+ newfilm.getStars() + "', '" + newfilm.getReview() + "')";
			stmt.execute(selectSQL);
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

	}

	public void UpdateFilm(Film editfilm) {
		openConnection();
		try {
			String selectSQL = "update films set title=?, year=?, director=?, stars=?, review=? where id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, editfilm.getTitle());
			preparedStatement.setInt(2, editfilm.getYear());
			preparedStatement.setString(3, editfilm.getDirector());
			preparedStatement.setString(4, editfilm.getStars());
			preparedStatement.setString(5, editfilm.getReview());
			preparedStatement.setInt(6, editfilm.getId());

			preparedStatement.executeUpdate();
			preparedStatement.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	// Search film by entering a search string
	public Collection<Film> SearchFilm(String searchStr) {
		openConnection();
		ArrayList<Film> searchResult = new ArrayList<>();

		try {
			if (searchStr.isEmpty()) {
				System.out.println("Enter a valid keyword to search");
			} else {
				String selectSQL = "select * from films where title like ? or year like ? or director like ? or stars like ?";
				PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
				for (int s = 1; s <= 4; s++) {
					preparedStatement.setString(s, "%" + searchStr + "%");
				}
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					Film film = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
							rs.getString("director"), rs.getString("stars"), rs.getString("review"));
					searchResult.add(film);
				}
				rs.close();
				preparedStatement.close();
			}
		} catch (SQLException se) {
			System.out.println("Error Searching film: " + se.getMessage());
		} finally {
			closeConnection();
		}
		return searchResult;

	}

	// Display searched films
	public static void displayFilms(Collection<Film> films) {
		if (films == null || films.isEmpty()) {
			System.out.println("No films found, ensure you entered a valid keyword or year.");
		}
		for (Film film : films) {
			
		}
	}

	// Display all films in the dbs
	public static void displayAllFilms(ArrayList<Film> allFilms) {
		for (Film film : allFilms) {
			
		}
	}

	// Delete film from the database
	public void DeleteFilm(int id) {
		openConnection();
		 try { 
			 String selectDQL = "delete from films where id=" + id + "";
		     stmt.execute(selectDQL); closeConnection(); 
	     // Check if the film exists before deletion, after deletion as well 
		     if (getFilmByID(id) == null) {
		    	 System.out.println("Successfully Deleted"); 
		    }
		 
		  else { System.out.println("Error Deleting Film with ID: " + id +
		  " Film stil Exists."); } } catch (SQLException se) {
		  System.out.println("Error Deleting Film: " + se.getMessage()); 
	}
		 

	}

}
