package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Library;

public class BookCopiesDAO extends BaseDAO {

	public BookCopiesDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public void Insertbook_copies(Book book, Library branch,
			Integer copies) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)",
				new Object[] { book.getBookId(), branch.getBranchId(), copies });
	}
	// /librarian service.
	public void Updatebook_copies(Book book, Library branch,
			Integer copies) throws ClassNotFoundException, SQLException {
		save("update tbl_book_copies set noOfCopies= ? where bookId = ? and branchId = ?",
				new Object[] { book.getBookId(), branch.getBranchId(), copies });
	}
	
	

	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
