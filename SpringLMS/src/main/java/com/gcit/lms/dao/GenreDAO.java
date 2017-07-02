package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{

	
	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}
	
	public void updateGenre(Genre genre) throws SQLException {
		template.update("Update tbl_genre set genreName= ? where genreId = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws SQLException {
		template.update("delete from tbl_genre where genreId = ?", new Object[] {genre.getGenreId()});

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenres(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<Genre>) template.query("select * from tbl_genre", this);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Genre> readallgenresbyname(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<Genre>)  template.query("select * from tbl_genre where genreName like ?", new Object[] {searchString},this);
	}
	
	public Genre readGenreByID(Integer genreID) throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<Genre> listGenre = (List<Genre>)  template.query("select * from tbl_genre where genre_id = ?", new Object[] { genreID },this);
		if (listGenre != null && !listGenre.isEmpty()) {
			return listGenre.get(0);
		}
		return null;
	}
	
	public Integer getGenresCount() throws SQLException{
		return template.queryForObject("select count(*) as COUNT from tbl_genre", Integer.class);
	}	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Genre> genre = new ArrayList<Genre>();
		
		while(rs.next()){
			Genre b = new Genre();
			b.setGenreId(rs.getInt("genre_id"));
			b.setGenreName(rs.getString("genre_name"));
			
			
			genre.add(b);
		}
		return genre;
	}


}
