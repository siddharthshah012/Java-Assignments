package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class AdminService {
	ConnectionUtil cUtil = new ConnectionUtil();
/***************************************************************************/
	
	
	
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

	
/****************************************************************************************/	
	
	public List<Book> getAllBooks(Integer pageNo1, String searchString) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (searchString != null) {
				return bdao.readAllBooksByTitle(pageNo1,searchString);
			}
			else{
				return bdao.readAllBooks(pageNo1);
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


	public void saveBook(Book books) throws SQLException {
		Connection conn = null;
	
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (books.getBookId() != null) {
				bdao.updateBook(books);
			} else {
				bdao.addBook(books);
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

	public Integer getBooksCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.getBooksCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
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
	
	public Book getBookByPK(Integer bookId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.readBooksByPK(bookId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;
	}


/***********************************************************************************************/




}