package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class AdminService {
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	BorrowerDAO bodao;
	
	@Autowired
	LibraryBranchDAO lbdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BookLoansDAO bldao;
	
	/*********************AUTHORS*************************/
	
	@Transactional
	@RequestMapping(value = "/saveAuthor", method = RequestMethod.POST, consumes="application/json")
	public void saveAuthor(@RequestBody Author author) throws Exception {
		
		if (author == null || author.getAuthorName() == null
				|| author.getAuthorName().length() == 0){
			throw new Exception(
					"Author Name cannot be empty");
		}
		else {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				adao.addAuthor(author);
			}
		}
	}
	
	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException {
		List<Author> authors = new ArrayList<>();
		if (searchString != null) {
			authors = adao.readAllAuthorsByName(pageNo, searchString);
		} else {
			authors = adao.readAllAuthors(pageNo);
		}
		for(Author a: authors){
			a.setBooks(bdao.readAllBooksByAuthorId(a.getAuthorId()));
		}
		return authors;
	}
	
	public Integer getAuthorsCount() throws SQLException {
		return adao.getAuthorsCount();
	}

	@Transactional
	public void deleteAuthor(Author author) throws SQLException {
		adao.deleteAuthor(author);
	}

	public Author getAuthorByPK(Integer authorId) throws SQLException {
		return adao.readAuthorsByPK(authorId);
	}
	
	
/********************************BOOKS*****************************************/
	
	@Transactional
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST, consumes="application/json")
	public void saveBook(@RequestBody Book book) throws Exception {
		
		if (book == null || book.getTitle() == null
				|| book.getTitle().length() == 0){
			throw new Exception(
					"Book Name cannot be empty");
		}
		else {
			if (book.getBookId() != null) {
				bdao.updateBook(book);

			} else {
				bdao.addBook(book);

			}
		}
	}
	
	
	
	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException {
		if (searchString != null) {
			return bdao.readAllBooksByTitle(pageNo,searchString);
		}
		else{
			return bdao.readAllBooks(pageNo);
		}
	}
	
	
	public Integer getBooksCount() throws SQLException {
		return bdao.getBooksCount();
	}

	@Transactional
	public void deleteBook(Book book) throws SQLException {
		bdao.deleteBook(book);
	}

	public Book getBookByPK(Integer bookId) throws SQLException {
		return bdao.readBooksByPK(bookId);
	}
		
/********************************PUBLISHER*****************************************/	
	@Transactional
	@RequestMapping(value = "/savePublisher", method = RequestMethod.POST, consumes = "application/json")
	public void savePublisher(@RequestBody Publisher publisher)
			throws Exception {

		if (publisher == null || publisher.getPublisherName() == null
				|| publisher.getPublisherAddress() == null
				|| publisher.getPublisherName().length() == 0) {
			throw new Exception("Book Name cannot be empty");
		} else {
			if (publisher.getPublisherId() != null) {
				pdao.updatePublisher(publisher);

			} else {
				pdao.addPublisher(publisher);
			}
		}
	}

	public List<Publisher> getAllPublisher(Integer pageNo, String searchString)
			throws SQLException, ClassNotFoundException {
		if (searchString != null) {
			return pdao.readAllPublishersByName(pageNo, searchString);
		} else {
			return pdao.readAllPublisher(pageNo);
		}
	}

	public Integer getPublisherCount() throws SQLException {
		return pdao.getPublishersCount();
	}

	@Transactional
	public void deletePublisher(Publisher publisher) throws SQLException {
		pdao.deletePublisher(publisher);
	}

	public Publisher getPublisherByPK(Integer publisherId) throws SQLException {
		return pdao.readPublishersByPK(publisherId);

	}
	
	
	/*************************************OVER-RIDE DUE DATE
	 * @throws SQLException **********************************************/
	
	
	public List<BookLoans> getallBookloans(Integer pageNo, String searchString) throws SQLException{
	
			return bldao.readallfromBookLoans(pageNo);	
	}
	@Transactional
	public void updateBookLoans(BookLoans bl, Integer value) throws SQLException, ClassNotFoundException{
		
		bldao.updateDueDate(bl, value);		
	}
	
	public BookLoans getallBookloansDateout(Timestamp t) throws SQLException{
		
			return bldao.readBookLoansforDateout(t).get(0);
	}
	

	public List<Genre> getAllGenre() throws ClassNotFoundException, SQLException{			
			return gdao.readAllGenres(1);
	}

	public Genre getGenreByPK(Integer genreId) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
	
				return gdao.readGenreByID(genreId);
	}
	
	

	
//	
//	ConnectionUtil cUtil = new ConnectionUtil();
///***************************************************************************/
//	
//	
//	
//	public void saveAuthor(Author author) throws SQLException {
//		Connection conn = null;
//
//		conn = cUtil.getConnection();
//		AuthorDAO adao = new AuthorDAO(conn);
//		try {
//			if (author.getAuthorId() != null) {
//				adao.updateAuthor(author);
//			} else {
//				adao.addAuthor(author);
//			}
//
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//	}
//
//	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException {
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		AuthorDAO adao = new AuthorDAO(conn);
//		try {
//			if (searchString != null) {
//				return adao.readAllAuthorsByName(pageNo, searchString);
//			} else {
//				return adao.readAllAuthors(pageNo);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//	}
//
//	public Integer getAuthorsCount() throws SQLException {
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		AuthorDAO adao = new AuthorDAO(conn);
//		try {
//			return adao.getAuthorsCount();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//	}
//
//	public void deleteAuthor(Author author) throws SQLException {
//		Connection conn = null;
//
//		conn = cUtil.getConnection();
//		AuthorDAO adao = new AuthorDAO(conn);
//		try {
//			adao.deleteAuthor(author);
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//	}
//
//	public Author getAuthorByPK(Integer authorId) throws SQLException {
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		AuthorDAO adao = new AuthorDAO(conn);
//		try {
//			return adao.readAuthorsByPK(authorId);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//	}
//
//	
///****************************************************************************************/	
//	
//	public List<Book> getAllBooks(Integer pageNo1, String searchString) throws SQLException {
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		BookDAO bdao = new BookDAO(conn);
//		try {
//			if (searchString != null) {
//				return bdao.readAllBooksByTitle(pageNo1,searchString);
//			}
//			else{
//				return bdao.readAllBooks(pageNo1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		return null;
//	}
//
//
//	public void saveBook(Book books) throws SQLException {
//		Connection conn = null;
//	
//		conn = cUtil.getConnection();
//		BookDAO bdao = new BookDAO(conn);
//		try {
//			if (books.getBookId() != null) {
//				bdao.updateBook(books);
//			} else {
//				
//				Integer bookId = bdao.addBookWithID(books);
//				
//				if(books.getAuthors()!=null && !books.getAuthors().isEmpty()){
//					for(Author a: books.getAuthors()){
//						bdao.addBookAuthors(bookId, a.getAuthorId());
//					}
//				}
//				if(books.getGenres()!=null && !books.getGenres().isEmpty()){
//					for(Genre a: books.getGenres()){
//						bdao.addBookGenres(bookId, a.getGenreId());
//					}
//				}
//				if(books.getPublisher()!=null) {
//					//System.out.println("Pub Id: "+ book.getPublisher().getPublisherId());
//					bdao.updateBookPublisher(bookId,books.getPublisher().getPublisherId());
//				}
//				System.out.println("here 1");
//				
//				
//				
//				
//				
//				
//				bdao.addBook(books);
//			}
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//	}
//
//	public Integer getBooksCount() throws SQLException {
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		BookDAO bdao = new BookDAO(conn);
//		try {
//			return bdao.getBooksCount();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//	}
//	
//	
//	public void deleteBook(Book book) throws SQLException {
//		Connection conn = null;
//
//		conn = cUtil.getConnection();
//		BookDAO bdao = new BookDAO(conn);
//		try {
//			bdao.deleteBook(book);
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//	}
//	
//	public Book getBookByPK(Integer bookId) throws SQLException {
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		BookDAO bdao = new BookDAO(conn);
//		try {
//			return bdao.readBooksByPK(bookId);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//	}
//
//
///**
// * @throws ClassNotFoundException 
// * @throws SQLException *********************************************************************************************/
//
//	
//	public List<Publisher> getAllPublishers(Integer pageNo, String searchString) throws ClassNotFoundException, SQLException{
//
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		PublisherDAO pdao = new PublisherDAO(conn);
//		try {
//			if (searchString != null) {
//				return pdao.readAllPublishersByName(pageNo, searchString);
//			}
//			else{
//				return pdao.readAllPublisher(pageNo);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		return null;
//		
//	}
//	
//	public void savePublisher(Publisher publishers) throws SQLException{
//		
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		PublisherDAO pdao = new PublisherDAO(conn);
//		try {
//			if (publishers.getPublisherId() != null) {
//				pdao.updatePublisher(publishers);
//			}
//			else{
//				pdao.addPublisher(publishers);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}		
//	}
//	
//	public Integer getPublishersCount() throws SQLException{
//
//
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		PublisherDAO pdao = new PublisherDAO(conn);
//		try {
//			return pdao.getPublishersCount();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//		
//	}
//	
//	public void deletePublisher(Publisher publisher) throws SQLException{
//		
//		Connection conn = null;
//
//		conn = cUtil.getConnection();
//		PublisherDAO pdao = new PublisherDAO(conn);
//		try {
//			pdao.deletePublisher(publisher);
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		
//		
//	}
//	
//	public Publisher getPublisherbyPK(Integer pubId) throws SQLException{
//		
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		PublisherDAO pdao = new PublisherDAO(conn);
//		try {
//			return pdao.readPublishersByPK(pubId);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//
//		return null;
//		
//	}
//	
//	
//	
//	/************************************************************************************************/
//	
//
//	public List<Genre> getAllGenre() throws ClassNotFoundException, SQLException{
//		Connection conn = null;
//		conn = cUtil.getConnection();
//		try{
//			GenreDAO gndao = new GenreDAO(conn);
//			return gndao.readAllGenres();
//		}catch (Exception e){
//			e.printStackTrace();
//		}finally{
//			conn.close();
//		}
//		return null;
//	}
//
//	public Genre getGenreByPK(Integer genreId) throws SQLException {
//		// TODO Auto-generated method stub
//		Connection conn = null;
//		try {
//			conn = cUtil.getConnection();
//			GenreDAO gdao = new GenreDAO(conn);
//			try {
//				return gdao.readGenreByID(genreId);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return null;
//	}
//	
//	/*
//	
//	public Integer getBooksLoansCount() throws  {
//		
//			Connection conn = null;
//			conn = cUtil.getConnection();
//			BookLoansDAO bldao = new BookLoansDAO(conn);
//			try {
//				return bldao.get
//						pdao.getPublishersCount();
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				if (conn != null) {
//					conn.close();
//				}
//			}
//
//			return null;
//			
//		}*/
//	
//	

}