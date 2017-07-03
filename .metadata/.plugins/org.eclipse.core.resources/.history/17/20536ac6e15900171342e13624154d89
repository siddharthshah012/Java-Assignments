package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class BookDAO extends BaseDAO{
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws SQLException{
		save("insert into tbl_book(title) values (?)", new Object[] {book.getTitle()});
	}
	
	public Integer addBookWithID(Book book) throws SQLException{
		System.out.println(book.getTitle());
		return saveWithID("insert into tbl_book(title) values (?)", new Object[] {book.getTitle()});
	}
	
	public void updateBook(Book book) throws SQLException{
		save("update tbl_book set title =? where bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws SQLException{
		System.out.println(book.getBookId());
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
		
	}
	
	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException{
		System.out.println(bookId);
		save("insert into tbl_book_authors values (?, ?)", new Object[] {bookId, authorId});
	}
	
	public void addBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException{
		System.out.println(bookId);
		save("insert into tbl_book_genres values (?, ?)", new Object[] {genreId,bookId});
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> readAllBooks() throws SQLException{
		return (List<Book>) read("select * from tbl_book", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> readAllBooksByTitle(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<Book>) read("select * from tbl_book where title like ?", new Object[]{searchString});
	}
	
	@SuppressWarnings("unchecked")
	public Book readBooksByPK(Integer bookId) throws SQLException{
		List<Book> books = (List<Book>) read("select * from tbl_book where bookId = ?", new Object[]{bookId});
		if(books!=null){
			return books.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setAuthors((List<Author>) adao.readFirstLevel("select * from tbl_author where authorId IN (Select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			//do the same for Genres and Publishers
			books.add(b);
		}
		return books;
	}
	
	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

	public Integer getBooksCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateBookPublisher(Integer bookId, Integer pubId) throws SQLException {
		// TODO Auto-generated method stub
		
		//System.out.println("update pub id: "+pubId+" book id: "+bookId);
		save("update tbl_book set pubId = ? where bookId = ?", new Object[]{pubId,bookId});
		
	}

}
