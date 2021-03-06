package com.gcit.LibrarySystem.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class JDBCtest {
	
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost/library";
	public static String username = "root";
	public static String password = "siddharth";
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username, password);
			conn.setAutoCommit(Boolean.FALSE);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void main(String[] args) throws SQLException {
		String query= "" ;
		Connection conn = getConnection();
//		Statement stmt = conn.createStatement();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter author name to insert: ");
		String authorName = scan.nextLine();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select * from tbl_author where authorName like ?");
			authorName = "%"+authorName+"%";
			pstmt.setString(1, authorName);
//		query = "insert into tbl_author (authorName) values('"+authorName+"')";
//		stmt.executeUpdate(query);
			query = "select * from tbl_author where authorName like '%"+authorName+"%'";
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				System.out.println("AUthor ID: " +rs.getInt("authorId"));
				System.out.println("Author Name: "+rs.getString("authorName"));
				System.out.println("----------------");
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(pstmt!=null){
				pstmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
	}

}
