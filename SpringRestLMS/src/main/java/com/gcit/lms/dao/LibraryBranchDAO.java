package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>>{

	
	public void addBranch(LibraryBranch library) throws Exception {
		template.update("insert into tbl_library_branch (branchName, branchAddress) values(?, ?)",
				new Object[] { library.getBranchName(),
						library.getBranchAddress() });
	}
	
	
	public void updateLibBranch(LibraryBranch library) throws Exception {
		template.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { library.getBranchName(),
						library.getBranchAddress(), library.getBranchId() });

	}
	
	public void deleteLibBranch(LibraryBranch library) throws Exception {
		template.update("delete from tbl_library_branch where branchId = ?",
				new Object[] { library.getBranchId() });
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllBranches() throws ClassNotFoundException, SQLException{
		//setPageNo(pageNo);
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch", this);
	}
	
	public LibraryBranch readLibBranchByID(Integer branchId)
			throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<LibraryBranch> library = (List<LibraryBranch>)  template.query(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId },this);
		if (library != null && !library.isEmpty()) {
			return (LibraryBranch) library.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readByLibraryName(String searchString, int pageNo,
			int pageSize) throws Exception {
		setPageNo(pageNo);

		searchString = "%" + searchString + "%";
		return (List<LibraryBranch>)  template.query(
				"select * from tbl_library_branch where branchName like ?",
				new Object[] { searchString },this);
	}
	
	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllBranches(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<LibraryBranch>)  template.query("select * from tbl_library_branch", this);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<LibraryBranch> library = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchId(rs.getInt("branchId"));
			lib.setBranchName(rs.getString("branchName"));
			lib.setBranchAddress(rs.getString("branchAddress"));
			
			library.add(lib);
		}
		return library;
		//return null;
	}


	public List<LibraryBranch> readBranchesByName(String branchName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Integer getBranchesCount() throws SQLException {
		// TODO Auto-generated method stub
		return  template.queryForObject("select count(*) as COUNT from tbl_library_branch;", Integer.class);
	}


}