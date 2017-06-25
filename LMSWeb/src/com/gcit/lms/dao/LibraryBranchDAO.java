package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Library;

public class LibraryBranchDAO extends BaseDAO{

	public LibraryBranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void createLibBranch(Library library) throws Exception {
		save("insert into tbl_library_branch (branchName, branchAddress) values(?, ?)",
				new Object[] { library.getBranchName(),
						library.getBranchAddress() });
	}
	
	
	public void updateLibBranch(Library library) throws Exception {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { library.getBranchName(),
						library.getBranchAddress(), library.getBranchId() });

	}
	
	public void deleteLibBranch(Library library) throws Exception {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { library.getBranchId() });
	}
	
	public Library readLibBranchByID(Integer branchId)
			throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<Library> library = (List<Library>) read(
				"select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId });
		if (library != null && !library.isEmpty()) {
			return library.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Library> readByLibraryName(String searchString, int pageNo,
			int pageSize) throws Exception {
		setPageNo(pageNo);

		searchString = "%" + searchString + "%";
		return (List<Library>) read(
				"select * from tbl_library_branch where branchName like ?",
				new Object[] { searchString });
	}
	
	@SuppressWarnings("unchecked")
	public List<Library> readAllBranchs(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return (List<Library>) read("select * from tbl_library_branch", null);
	}
	


	

	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<Library> authors = new ArrayList<>();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		while (rs.next()) {
			Library lib = new Library();
			lib.setBranchId(rs.getInt("branchId"));
			lib.setBranchAddress(rs.getString("branchAddress"));
			lib.setBooks((List<Book>) lbdao
					.readFirstLevel(
							"select * from tbl_book where bookId IN (Select bookId from tbl_book_authors where authorId = ?)",
							new Object[] { lib.getBranchId() }));
			authors.add(lib);
		}
		return authors;
		
		
		//return null;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}