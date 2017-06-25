package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Library;
import com.gcit.lms.entity.Publisher;

public class AdminService {
	ConnectionUtil cUtil = new ConnectionUtil();

	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				adao.addAuthor(author);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	public void saveBook(Book book) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBook(book);
				Integer bookId = bdao.addBookWithID(book);
				
				if(book.getAuthors()!=null && !book.getAuthors().isEmpty()){
					for(Author a: book.getAuthors()){
						bdao.addBookAuthors(bookId, a.getAuthorId());
					}
				}
				if(book.getGenres()!=null && !book.getGenres().isEmpty()){
					for(Genre a: book.getGenres()){
						bdao.addBookGenres(bookId, a.getGenreId());
					}
				}
				if(book.getPublisher()!=null) {
					//System.out.println("Pub Id: "+ book.getPublisher().getPublisherId());
					bdao.updateBookPublisher(bookId,book.getPublisher().getPublisherId());
				}
				System.out.println("here 1");
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("hey");
		} finally {
			if (conn != null) {
				conn.close();
			}
			
		}
	}

	
	public List<Author> getAllAuthors() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
				return adao.readAllAuthors();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public void deleteAuthor(Author author) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			adao.deleteAuthor(author);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public Author getAuthorById(Integer authorId) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			AuthorDAO authordao = new AuthorDAO(conn);
			return authordao.readAuthorByID(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	/*
	public Integer getBookCount() throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO adao = new BookDAO(conn);
			return adao.getBooksCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	*/
	
	public List<Author> getAuthorsByName(Integer pageNo, String authorName) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllAuthorsByName(authorName);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	
	public void addBranch(Library branch) throws Exception{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			LibraryBranchDAO adao = new LibraryBranchDAO(conn);
			adao.addBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Adding branch failed");
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	
	
	public void editBorrower(Borrower borrower) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BorrowerDAO adao = new BorrowerDAO(conn);
			adao.updateBorrower(borrower);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void editBranch(Library branch) throws Exception{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.updateLibBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	
	public void deleteBranch(Library branch) throws Exception{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.deleteLibBranch(branch);
			System.out.println("Deleted successfully");
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Delete branch failed.");
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	

	
	public List<Book> getAllBooks() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
				return bdao.readAllBooks();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	
	public List<Library> getBranchsByName(Integer pageNo, String branchName) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readBranchesByName(branchName);
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	
	public List<Book> getBooksByName(Integer pageNo, String bookName) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAllBooksByTitle(bookName);
		} catch (SQLException e) {
			System.out.println("Get books by name failed!");
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	
	public List<?> getAllBorrowers(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.readAllBorrowers(pageNo);
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		//return null;
		
	}
	
	public void deleteBook(Book book) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			bdao.deleteBook(book);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public List<Genre> getAllGenres() throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readAllGenres();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	
	public List<Publisher> getAllPublishers() throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readAllPublishers();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Genre getGenreById(Integer genreId) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readGenreByID(genreId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Publisher getPublisherById(Integer pubId) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readPublishersById(pubId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	
}
