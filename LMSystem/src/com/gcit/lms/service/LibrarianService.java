package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Library;

public class LibrarianService {
	ConnectionUtil cUtil = new ConnectionUtil();
	public void saveBook(Book book) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBook(book);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	
	public Integer getBooksCount() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
				return bdao.getBooksCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Book getBookByPK(Integer bookId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
				return bdao.readBooksByPK(bookId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public void deleteBook(Book book) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			bdao.deleteBook(book);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public List<Library> getAllBranches(Integer pageNo) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
		try {
				return brdao.readAllBranches(pageNo);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public Integer getBranchesCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
		try {
				return brdao.getBranchesCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public void saveBranch(Library branch) throws Exception {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
		try {
			if (branch.getBranchId()!=0) {
				brdao.updateLibBranch(branch);
			} else {
				brdao.addBranch(branch);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public Library getBranchByPK(Integer branchId) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
		try {
				return (Library) brdao.readLibBranchByID(branchId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return null;
	}
	
	public void deleteBranch(Library branch) throws Exception {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
		try {
			brdao.deleteLibBranch(branch);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public Integer getNoCopies(int branchId,int bookId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookCopiesDAO cdao = new BookCopiesDAO(conn);
		try {
				return cdao.numCopies(branchId,bookId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return 0;
	}
	
	public void insertCopies(int branchId,int bookId,int numbCopies) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookCopiesDAO cdao = new BookCopiesDAO(conn);
		try {
				cdao.addCopies(branchId,bookId,numbCopies);
				conn.commit();
		} 

		catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
	}
	
	public void updateCopies(int branchId,int bookId,int numbCopies) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookCopiesDAO cdao = new BookCopiesDAO(conn);
		try {
				cdao.updateCopies(branchId,bookId,numbCopies);
				conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
	}
	
	public int readCopies(int branchId,int bookId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookCopiesDAO cdao = new BookCopiesDAO(conn);
		List<Integer> l = new ArrayList<Integer>();
		try {
			l = cdao.readCopies(branchId,bookId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
        if(l.isEmpty())
        	return 0;
        return l.get(0);
	}
	
}
