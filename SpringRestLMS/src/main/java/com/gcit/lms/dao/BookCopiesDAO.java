package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>>{

	public void insertBookCopies(Book book, LibraryBranch branch,
			Integer copies) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)",
				new Object[] { book.getBookId(), branch.getBranchId(), copies });
	}
	// /librarian service.
	public void updateBookCopies(Book book, LibraryBranch branch,
			Integer copies) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book_copies set noOfCopies= ? where bookId = ? and branchId = ?",
				new Object[] { book.getBookId(), branch.getBranchId(), copies });
	}
	public Integer numCopies(int branchId,int bookId) throws SQLException
	{
		if(containsBook(branchId,bookId))
		{
			List<BookCopies> c = readCopies(branchId,bookId);
			return c.get(0).getNoOfCopies();
		}
		return 0;
	}
	public boolean containsBook(int branchId,int bookId) throws SQLException
	{
		List<BookCopies> c= readCopies(branchId,bookId);
		if(c.isEmpty())
			return false;
		return true;
	}
	

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<BookCopies> readCopies(int branchId,int bookId) throws SQLException{
		//setPageNo(pageNo);
		return template.query("select noOfCopies from tbl_book_copies where bookId=? and branchId=?",new Object[] {bookId,branchId},this);
	}

	
	@SuppressWarnings("unchecked")
	public List<BookCopies> readBookCopiesByBranchID(Integer branchId) throws SQLException {
		// TODO Auto-generated method stub
		
		List<BookCopies> brr = (List<BookCopies>) template.query("select * from tbl_book_copies as bc inner join tbl_book as bk on bc.bookId = bk.bookId where branchId = ?", new Object[] {branchId},this);
		if(brr!=null && brr.size() >0){
			return brr;
		}
		return null;
		
	}
	public void addCopies(int branchId, int bookId, int numbCopies) throws SQLException {
		// TODO Auto-generated method stub
		
		template.update("insert into tbl_book_copies(bookId,branchId,noOfCopies) values (?,?,?)", new Object[] {bookId,branchId,numbCopies});
		
	}
	public void updateCopies(int branchId, int bookId, int numbCopies) throws SQLException {
		// TODO Auto-generated method stub
		template.update("update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?", new Object[] {numbCopies,bookId,branchId});
	}

}