package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/deleteAuthor" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
	}

	AdminService adminService = new AdminService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Author author = new Author();
		String message = "";
		if (request.getParameter("authorId") != null && !request.getParameter("authorId").isEmpty()) {
			author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));

			try {
				adminService.deleteAuthor(author);
				message = "Author deleted Successfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author delete failed. Try Again!";
			}
		}
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("/viewauthors.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Author author = new Author();
		author.setAuthorName(request.getParameter("authorName"));
		try {
			adminService.saveAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/viewauthors.jsp");
		rd.forward(request, response);
	}

}
