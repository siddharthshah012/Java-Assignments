package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}

	
	public void addBorrower(Borrower borrower) throws SQLException {
		template.update("Insert into tbl_borrower(name,address,phone) values (?,?,?)",
				new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	

	
	public void updateBorrower(Borrower borrower) throws SQLException {
		template.update("Update tbl_borrower set name= ?, address=?, phone=? where cardNo = ?",
				new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone(), borrower.getCardNo() });
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException {
		template.update("delete from tbl_borrower where cardNo = ?", new Object[] {borrower.getCardNo()});

	}

	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrowers() throws SQLException {
		// TODO Auto-generated method stub
		//System.out.println("Here");
		return template.query("select * from tbl_borrower", this);
	}

	public Integer CheckCard(Integer cardNo) throws ClassNotFoundException, SQLException{
		
		Integer check = readBorrowerByID(cardNo).getCardNo();
		if (check  != 0)
		return check;
		else{
			return 0;
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public Borrower readBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Borrower> borrower = (List<Borrower>) template.query("select cardNo from tbl_borrower where cardNo = ?;", new Object[] {cardNo},this);
		System.out.println("borrower" +  borrower);
		System.out.println("size" + borrower.size());
		if(borrower!=null && borrower.size() >0){
			System.out.println("in DAO"+borrower.get(0));
			return borrower.get(0);
		}
		else{
			return null;
			}
	}
	

	@SuppressWarnings("unchecked")
	public List<Borrower> readBorrowerByName(String searchString) throws SQLException {
		// TODO Auto-generated method stub
		return (List<Borrower>) template.query("select * from tbl_borrower where name like ?", new Object[] {searchString},this);
	}

	public List<Borrower> getBorrowerCount() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return template.query("select count(*) from tbl_borrower;", this);
	}

}
