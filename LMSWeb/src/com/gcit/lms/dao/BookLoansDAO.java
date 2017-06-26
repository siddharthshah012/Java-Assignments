package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Publisher;

public class BookLoansDAO extends BaseDAO {

	public BookLoansDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public Integer checkOutbook(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		try{
		save("INSERT INTO tbl_book_loans VALUES "
				+ "(?,?,?,NOW(),NOW()+interval 2 week, null);",new Object[] {bookLoans.getBookId(),bookLoans.getBranchId(),bookLoans.getCardNo()});
		//System.out.println("ahsdbak"+ bookId+" " + branchId+" "+cardNo);
		System.out.println("entry added to the book loans table");
		System.out.println(bookLoans.getBookId());
		save("update tbl_book_copies set noOfCopies = noOfCopies-1 where bookId=? and branchId =?", new Object[] {bookLoans.getBookId(),bookLoans.getBranchId()});
		return 1;
		}
		catch(Exception e){
		System.out.println(e);
		return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<?> readBooksforCardNo(int cardNo)
			throws ClassNotFoundException, SQLException { // return the book
															// object.!!
		// TODO Auto-generated method stub
		return (List<?>) read(
				"select * from tbl_book as bk where b.bookId in "
				+ "(select bookId from tbl_book_loans where cardNo=? and dateIn is NULL )",
				new Object[] { cardNo});
		// select b.title from books where b.Id in (select bId from bookLoans
		// where cardNo = ?)", new Object[] {borrower.getCardNo()}
	}
	/*
	public List<BookLoans> returnBook()
	public Integer returnBook(){
		return null;
		
	}*/
	
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		//Connection conn =
		return null;
		
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<BookLoans> bookloans = new ArrayList<BookLoans>();
		//Here i am returning the books that a borrower has checked out.
		BookLoansDAO bldao = new BookLoansDAO(conn);
		
		while(rs.next()){
			BookLoans bl = new BookLoans();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getTimestamp("dateOut"));
			bl.setDueDate(rs.getTimestamp("dueDate"));
			bl.setDateIn(rs.getTimestamp("dateIn"));
			bookloans.add(bl);
		}
		return bookloans;
	}

}
