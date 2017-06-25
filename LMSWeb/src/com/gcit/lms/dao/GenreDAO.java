package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO{

	public GenreDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}
	
	public void updateGenre(Genre genre) throws SQLException {
		save("Update tbl_genre set genreName= ? where genreId = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws SQLException {
		save("delete from tbl_genre where genreId = ?", new Object[] {genre.getGenreId()});

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenres(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<Genre>) read("select * from tbl_genre", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException{
		return (List<Genre>) readAll("select * from tbl_genre", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Genre> readallgenresbyname(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<Genre>) read("select * from tbl_genre where genreName like ?", new Object[] {searchString});
	}
	
	public Genre readGenreByID(Integer genreID) throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<Genre> listGenre = (List<Genre>) read("select * from tbl_genre where genre_id = ?", new Object[] { genreID });
		if (listGenre != null && !listGenre.isEmpty()) {
			return listGenre.get(0);
		}
		return null;
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Genre> genres = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			g.setBooks((List<Book>) bdao.readFirstLevel("select * from tbl_book where bookId IN(select bookId from tbl_book_genres where genre_id=?) ", new Object[]{g.getGenreId()}));
			
			genres.add(g);
		}
		return genres;
	}

	@Override	
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Genre> listGenres = new ArrayList<>();
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			listGenres.add(g);
		}
		return listGenres;
	}

}
