package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;

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
}
