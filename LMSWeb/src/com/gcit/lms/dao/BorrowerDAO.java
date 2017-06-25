package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
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
	
	public void addBorrower(Borrower borrower) throws SQLException {
		save("Insert into tbl_borrower(name,address,phone) values (?,?,?)",
				new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	
	public Integer addBorrowerWithId(Borrower borrower) throws SQLException {
		return saveWithID("Insert into tbl_borrower(name,address,phone) values (?,?,?)",
				new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException {
		save("Update tbl_borrower set name= ?, address=?, phone=? where cardNo = ?",
				new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone(), borrower.getCardNo() });
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException {
		save("delete from tbl_borrower where cardNo = ?", new Object[] {borrower.getCardNo()});

	}

}
