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

	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Genre> genre = new ArrayList<Genre>();
		BookDAO bdao = new BookDAO(conn);
		
		while(rs.next()){
			Genre b = new Genre();
			b.setGenreId(rs.getInt("genre_id"));
			b.setGenreName(rs.getString("genre_name"));
			
			if ((b.getBooks() != null && b.getBooks().size() > 0)){
			for(Book bo: b.getBooks())
				b.setBooks((List<Book>) bdao.readFirstLevel("select * from tbl_book where bookId = ?)", new Object[] {bo.getBookId()}));
			}
			genre.add(b);
		}
		return genre;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
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
