package com.gcit.lms.Service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.DAO.AuthorDAO;
import com.gcit.lms.Entity.Author;

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

}
