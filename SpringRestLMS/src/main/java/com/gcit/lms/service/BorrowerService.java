package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	BorrowerDAO bodao;
	
	@Autowired
	LibraryBranchDAO lbdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BookLoansDAO bldao;
	
	
	public int checkCardNo(Integer cardNo) throws Exception{
		
		int flag = 0;
			
			if(bodao.CheckCard(cardNo) != 0){
				flag = 1;
				return flag;
			}else {
				throw new Exception (" Card NO invalid ");
			}
		
	}
	
	public List<LibraryBranch> getAllBranches() throws SQLException, ClassNotFoundException{
			return lbdao.readAllBranches();
	}
	
	public List<Book> readAllBookswithBranch(Integer branchId)
			throws SQLException, ClassNotFoundException {
		
			return bdao.readAllBookswithBranch(branchId);
	}
	
	public Integer checkBookOut(BookLoans book)
			throws ClassNotFoundException, SQLException {
		
			return bldao.checkOutbook(book);
	}
	
	public List<BookLoans> readBooksforCardinTBL(BookLoans bookloans) throws SQLException{
			return bldao.readallBookswithcardNumber(bookloans);
	}
	public void returnBookloan(BookLoans bl) throws SQLException {
		// TODO Auto-generated method stub
			bldao.returnBookloans(bl);		
	}
	
	public Integer getBranchesCount() throws SQLException{
		return lbdao.getBranchesCount();
	}
		
}
