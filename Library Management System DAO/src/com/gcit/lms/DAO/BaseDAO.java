package com.gcit.lms.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO {
	
	public Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	public void save(String query, Object[] vals) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		pstmt.executeUpdate();
		conn.commit();
	}

	@SuppressWarnings("null")
	public Integer saveWithID(String query, Object[] vals) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		setPstmtObjects(vals, pstmt);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	public List<?> read(String query, Object[] vals) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}

	public abstract List<?> extractData(ResultSet rs) throws SQLException;

	public List<?> readFirstLevel(String query, Object[] vals) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		ResultSet rs = pstmt.executeQuery();
		return extractDataFirstLevel(rs);
	}

	public abstract List<?> extractDataFirstLevel(ResultSet rs) throws SQLException;

	private void setPstmtObjects(Object[] vals, PreparedStatement pstmt) throws SQLException {
		if (vals != null) {
			int count = 1;
			for (Object o : vals) {
				pstmt.setObject(count, o);
				count++;
			}
		}
	}
	

}