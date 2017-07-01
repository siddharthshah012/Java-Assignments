package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {

	ConnectionUtil cUtil = new ConnectionUtil();
	
	public int checkCardNo(Integer cardNo) throws SQLException{
		
		System.out.println("in service card" +cardNo);
		Connection conn = null;
		conn = cUtil.getConnection();
		int flag = 0;
		
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			//System.out.println("condition" + brdao.readBorrowerByID(cardNo));
			
			if(brdao.CheckCard(cardNo) != 0){
				flag = 1;
				return flag;
			}
		}catch (Exception e){
			System.out.println("in cathc");
			conn.rollback();
		}finally{
			conn.close();
		}
		System.out.println("in service"+ flag);
		return flag;
		
	}
	
	
	public List<LibraryBranch> getAllBranches() throws SQLException, ClassNotFoundException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readAllBranches();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		//return null;
		
	}
	
	
	public List<Book> readAllBookswithBranch(Integer branchId)
			throws SQLException, ClassNotFoundException {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			// LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			BookDAO bdao = new BookDAO(conn);
			System.out.println("branchId" +branchId);
			return bdao.readAllBookswithBranch(branchId);

		} catch (SQLException e) {
			System.out.println("Get books by name failed!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;

	}
	
	
	public Integer checkBookOut(BookLoans book)
			throws ClassNotFoundException, SQLException {

		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			// LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			BookLoansDAO bldao = new BookLoansDAO(conn);
			//System.out.println("bookid "+bookId+"branchid"+branchId+"cardno"+cardNo);
			
			return bldao.checkOutbook(book);
			

		} catch (SQLException e) {
			System.out.println("Get books by name failed!");
			conn.rollback();
		} finally {
			conn.commit();
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<BookLoans> readBooksforCardinTBL(BookLoans bookloans) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			// LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			BookLoansDAO bldao = new BookLoansDAO(conn);
			//System.out.println("branchId" +cardNo);
			return (List <BookLoans>)bldao.readallBookswithcardNumber(bookloans);

		} catch (SQLException e) {
			System.out.println("Get books by name failed!");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}


	public void returnBookloan(BookLoans bl) throws SQLException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			// LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			BookLoansDAO bldao = new BookLoansDAO(conn);
			//System.out.println("bookid "+bookId+"branchid"+branchId+"cardno"+cardNo);
			
			bldao.returnBookloans(bl);
			

		} finally {
			try {
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (conn != null) {
				conn.close();
			}
		}
	
		
	}
	
	

	
	
}