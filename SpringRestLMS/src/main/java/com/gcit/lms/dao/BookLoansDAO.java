package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>>{

	public Integer checkOutbook(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		try{
			template.update("INSERT INTO tbl_book_loans VALUES "
					+ "(?,?,?,NOW(),NOW()+interval 2 week, null);",
					new Object[] { bookLoans.getBooks().getBookId(),
							bookLoans.getBranch().getBranchId(),
							bookLoans.getBorrower().getCardNo() });
			//System.out.println("ahsdbak"+ bookId+" " + branchId+" "+cardNo);
		System.out.println("entry added to the book loans table");
			System.out.println(bookLoans.getBooks().getBookId());
			template.update("update tbl_book_copies set noOfCopies = noOfCopies-1 where bookId=? and branchId =?",
					new Object[] { bookLoans.getBooks().getBookId(),
							bookLoans.getBranch().getBranchId() });
		return 1;
		}
		catch(Exception e){
		System.out.println(e);
		return null;
		}
	}
	
	public List<BookLoans> readBookLoansforDateout(Timestamp t) throws SQLException{
		return template.query("SELECT * FROM tbl_book_loans WHERE dateOut=?", new Object[]{t},this);
	} 
	
	public List<BookLoans> readallfromBookLoans(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		return template.query("SELECT * FROM tbl_book_loans where dueDate> NOW() and dateIn is null ;", this);
	}
	
	public Integer getBookLoansCount() throws SQLException{
			return template.queryForObject("select count(*) as COUNT from tbl_book_loans ", Integer.class);
		}

	
	public List<?> readallfromBookLoansByName(Integer pageNo, String searchstring) throws SQLException{
		setPageNo(pageNo);
		searchstring = "%"+searchstring+"%";
		return template.query("SELECT * FROM tbl_book_loans where "
				+ "dueDate> NOW() and dateIn is null and "
				+ "cardNo IN "
				+ "(SELECT name from tbl_borrower where name = ? and cardNo = cardNo); ",new Object[]{searchstring},this);
	}
	public List<BookLoans> readallBookswithcardNumber(BookLoans bookloans) throws SQLException{
		
		return template.query(
				"SELECT * from tbl_book_loans where cardNo=? and dateIn is NULL;",
				new Object[] { bookloans.getBorrower().getCardNo() },this);
	}
	
	
	public List<BookLoans> readBooksforCardNo(BookLoans bookloans)
			throws ClassNotFoundException, SQLException { // return the book
															// object.!!
		// TODO Auto-generated method stub
		
		System.out.println("IN DAO");
		return template.query(
				"select * from tbl_book as bk where bk.bookId IN "
				+ "(select bookId from tbl_book_loans where cardNo=? and dateIn is NULL );",
				new Object[] { bookloans.getBorrower().getCardNo()},this);
		// select b.title from books where b.Id in (select bId from bookLoans
		// where cardNo = ?)", new Object[] {borrower.getCardNo()}
	}

	
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> loans = new ArrayList<>();

		while(rs.next())
		{
		BookLoans l = new BookLoans();
		l.setDateOut(rs.getTimestamp("dateOut"));
		l.setDueDate(rs.getTimestamp("dueDate"));
		l.setDateIn(rs.getTimestamp("dateIn"));
		
		loans.add(l);
		}
		return loans;
		}

	public void updateDueDate(BookLoans bl, int value) throws ClassNotFoundException, SQLException{
		template.update(
				"UPDATE tbl_book_loans SET dueDate = DATE_ADD(NOW(),INTERVAL ? DAY) WHERE dateOut=?",
				new Object[] { value, bl.getDateOut(),
						 });
		
		}
	
	public void returnBookloans(BookLoans bl) throws SQLException {
		// TODO Auto-generated method stub
		template.update("update tbl_book_loans set dateIn = NOW() where dateOut = ?", new Object[]{bl.getDateOut()});
		
		template.update("update tbl_book_copies set noOfCopies = noOfCopies+1 where bookId=? and branchId =?",
				new Object[] { bl.getBooks().getBookId(),
						bl.getBranch().getBranchId() });
		System.out.println("here in bookloansdao");
	}

}
