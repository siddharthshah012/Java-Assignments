package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
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

	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if (searchString != null) {
				return adao.readAllAuthorsByName(pageNo, searchString);
			} else {
				return adao.readAllAuthors(pageNo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;
	}
	
	public List<Author> getAllAuthors() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.readAllAuthors(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;
	}

	public Integer getAuthorsCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.getAuthorsCount();

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

	public Author getAuthorByPK(Integer authorId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.readAuthorsByPK(authorId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;
	}

	public List<Book> getAllBooks() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.readAllBooks(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	public void saveBook(Book book) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				//bdao.addBook(book);
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
	
	public void saveBranch(Library branch) throws Exception {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO lbdao =  new LibraryBranchDAO(conn);
		try {
			System.out.println("service" + branch.getBranchId());
			if (branch.getBranchId() != null) {
				lbdao.updateLibBranch(branch);
			} else {
				lbdao.addBranch(branch);
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
	
	public void saveBorrower(Borrower brr) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		
		BorrowerDAO bodoa = new BorrowerDAO(conn);
		try {
			if (brr.getCardNo() != 0) {
				bodoa.updateBorrower(brr);
			} else {
				bodoa.addBorrower(brr);
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
	
	
	public List<Author> getAuthorsByName(Integer pageNo, String authorName) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllAuthorsByName(pageNo, authorName);
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
			try {
				return gdao.readAllGenres();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		//return null;
	}
	
	public Genre getGenreById(Integer genreId) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			try {
				return gdao.readGenreByID(genreId);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public List<Library> getAllBranches(Integer pageNo) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
			return brdao.readAllBranches(pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public Library getBranchByID(Integer branchId) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readLibBranchByID(branchId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<Publisher> getAllPublisher() throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		try {
			PublisherDAO pbdao = new PublisherDAO(conn);
			return pbdao.readAllPublishers();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public List<Genre> getAllGenre() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			GenreDAO gndao = new GenreDAO(conn);
			return gndao.readAllGenres();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	public List<Borrower> getAllBorrower() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.readAllBorrowers();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public Borrower getBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.readBorrowerByID(cardNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public List<BookCopies> getBookCopiesByBranchID(Integer branchId) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			BookCopiesDAO bdao = new BookCopiesDAO(conn);
			return bdao.readBookCopiesByBranchID(branchId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	public void editDueDate(BookLoans bl) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		
		try{
			BookLoansDAO adao = new BookLoansDAO(conn);
			adao.updateDueDate(bl, 12);
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		return;
	}
	
	
	public void deleteBorrower(Borrower brr) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			brdao.deleteBorrower(brr);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
	}
	
	public List<Borrower> getAllBorrowerByName(String searchString) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			BorrowerDAO adao = new BorrowerDAO(conn);
			return adao.readBorrowerByName(searchString);
		}catch (Exception e){
			e.printStackTrace();
			//conn.rollback();
		}finally{
			conn.close();
		}
		return null;
	}
	
	
	public Integer getAuthorCount() throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		conn = cUtil.getConnection();
		Integer count = null;
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			count = adao.getAuthorsCount();
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
				
		return count;
	}
	
	public Integer getBookCount() throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		conn = cUtil.getConnection();
		Integer count = null;
		try{
			BookDAO bdao = new BookDAO(conn);
			count = bdao.totalBookCount();
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		
		return count;
	}
	
	public Integer getBranchCount() throws ClassNotFoundException, SQLException{

		Connection conn = null;
		conn = cUtil.getConnection();
		Integer count = null;
		try{
			LibraryBranchDAO adao = new LibraryBranchDAO(conn);
			count = adao.getBranchesCount();
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}

		return count;
	}
	
	
	public Integer getBorrowerCount() throws ClassNotFoundException, SQLException{

		Connection conn = null;
		conn = cUtil.getConnection();
		Integer count = null;
		try{
			BorrowerDAO adao = new BorrowerDAO(conn);
			count = adao.getBorrowerCount();
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
			
		return count;
	}

	public void deletePublisher(Publisher pub) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		conn = cUtil.getConnection();
		try{
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(pub);;
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
	}

	public void savePublisher(Publisher pub) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;

		conn = cUtil.getConnection();
		PublisherDAO pubDAO =  new PublisherDAO(conn);
		System.out.println("in service " + pub.getPublisherName());
		try {
			if (pub.getPublisherId() != null) {
				pubDAO.updatePublisher(pub);
				System.out.println("in service update");
			} else {
				pubDAO.addPublisher(pub);
				System.out.println("in service add");
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
	
	
	@SuppressWarnings("unchecked")
	public List<BookLoans> getallBookloans(){
		Connection conn = null;
		conn = cUtil.getConnection();
		
		BookLoansDAO bldao = new BookLoansDAO(conn);
		
		try {
			return (List<BookLoans>) bldao.readallfromBookLoans();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void updateBookLoans(BookLoans bl, Integer value) throws SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		
		BookLoansDAO bldao = new BookLoansDAO(conn);
		
		try {
			bldao.updateDueDate(bl, value);
			conn.commit();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
		}
		
		
	}
	
	public BookLoans getallBookloansDateout(Timestamp t){
		Connection conn = null;
		conn = cUtil.getConnection();
		
		BookLoansDAO bldao = new BookLoansDAO(conn);
		
		try {
			return bldao.readBookLoansforDateout(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
}

	
	

