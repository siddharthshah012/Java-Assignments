package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Library;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class LibraryServlet
 */
@WebServlet({"/LibraryServlet","/pageBranch","/editBranches","/addCopies"})
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 LibrarianService lService = new LibrarianService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibraryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Library branch = new Library();
		switch (reqUrl) {
		case "/pageBranch":
			if (request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));

				try {
					List<Library> branches = null;
					try {
						branches = lService.getAllBranches(pageNo);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("branches", branches);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			RequestDispatcher rd8 = request.getRequestDispatcher("/editbranches.jsp");
			rd8.forward(request, response);
			break;
		default:break;
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Library branch = new Library();
		Integer bookId, branchId, numbCopies;
		switch (reqUrl) {
		case "/editBranches":
			branch.setBranchName(request.getParameter("branchName"));
			branch.setBranchAddress(request.getParameter("branchAddress"));
			
			branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
			try {
				lService.saveBranch(branch);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher rd1 = request.getRequestDispatcher("/editbranches.jsp");
			rd1.forward(request, response);
			break;
		
		case "/addCopies":
			//System.out.println("here");
			branchId = Integer.parseInt(request.getParameter("branchId"));
			bookId = Integer.parseInt(request.getParameter("bookId"));
			numbCopies = Integer.parseInt(request.getParameter("numbCopies"));
			System.out.println();
			int copies = 0,newcops=0;
			try {
				copies = lService.getNoCopies(branchId,bookId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(copies==0)
					lService.insertCopies(branchId,bookId,numbCopies);
				else
					lService.updateCopies(branchId,bookId,copies+numbCopies);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Library branch1 = new Library();
			Book book1 = new Book();
			try {
				branch1 = lService.getBranchByPK(branchId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				book1 = lService.getBookByPK(bookId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String branchName = branch1.getBranchName();
			String title = book1.getTitle();
			try {
				newcops = lService.readCopies(branchId,bookId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("branchName", branchName);
			request.setAttribute("title", title);
			request.setAttribute("oldcopies", copies);
			request.setAttribute("newcopies", newcops);
			RequestDispatcher rd8 = request.getRequestDispatcher("/result.jsp");
			rd8.forward(request, response);
			break;
		default:break;
		}
		
		
	}

}
