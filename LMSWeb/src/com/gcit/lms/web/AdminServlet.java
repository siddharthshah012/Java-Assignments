package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addauthor", "/deleteAuthor","/editAuthor","/addBook","/viewauthors",
			"/deletebook","/viewBooks" })
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
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "/welcome.jsp";
		String message ="";
		
		switch(reqUrl){
		
		case"/deleteAuthor":deleteAuthor(request);
							forwardPath="/viewauthors.jsp";
							message ="Deleetd AUthor successfully";
								break;
		case"/deletebooks":	deleteBook(request);
							forwardPath="/viewBooks.jsp";
							message ="Deleetd Book successfully";
							break;
		}
		
		
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		String forwardPath = "/welcome.jsp";
		
		switch (reqUrl){
		case "/addAuthor": addAuthor(request);
							forwardPath = "/viewauthors.jsp";
							break;
		case "/editAuthor" :editAuthor(request);
							forwardPath="/viewauthors.jsp";
							break;
		case"/addBook": addBook(request);
						forwardPath="/viewBooks.jsp";
						break;
							
				default:forwardPath="/viewauthors.jsp";
						break;

		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
		
	}
	
	
	private void addAuthor(HttpServletRequest request){
		
		Author author = new Author();
		author.setAuthorName(request.getParameter("authorName"));
		try {
			adminService.saveAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void addBook(HttpServletRequest request) {
		Book book = new Book();
		String[] authors, genres,publisher;
		String title, publisherData;
		
		try{
			title = request.getParameter("title");
		}catch (NullPointerException e){
			return ;
		}
		
		try {
			authors = request.getParameterValues("authors");
			genres = request.getParameterValues("genres");
			publisherData = request.getParameter("publisher");
			publisher = publisherData.split(" ");
		}catch(NullPointerException e){
			return;
		}
	
		AdminService service = new AdminService();
		List<Author> authorList = new ArrayList<>();
		List<Genre> genreList = new ArrayList<>();
		Publisher pub;
		
		try {
			for(String author: authors) {
				String[] temp = author.split(" ");
				Author a = service.getAuthorById(Integer.parseInt(temp[0]));
				authorList.add(a);
			}
			for(String genre: genres) {
				String[] temp = genre.split(" ");
				Genre g = service.getGenreByPk(Integer.parseInt(temp[0]));
				genreList.add(g);
			}
			book.setAuthors(authorList);
			book.setGenres(genreList);
			book.setTitle(title);
			if(!publisherData.equals("Select")) {
				pub = service.getPublisherByPk(Integer.parseInt(publisher[0]));
				book.setPublisher(pub);
			}else
				book.setPublisher(null);
			
			service.addBook(book);
		} catch (SQLException e) {
			System.out.println("Adding book failed.");
			e.printStackTrace();
		}
	}
	
	private void editAuthor(HttpServletRequest request){
		
		Author author = new Author();
		author.setAuthorName(request.getParameter("authorName"));
		author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
		//String message="";
		try {
			adminService.saveAuthor(author);
			//message = "EDIT SUCCESSFUL";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//request.setAttribute("message", message);
		
	}
	
	private void deleteAuthor(HttpServletRequest request){
		
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
		
	}
	private void deleteBook(HttpServletRequest request){
		
		Book book = new Book();
		String message = "";
		
			try {
				adminService.deleteBook(book);
				message = "book deleted Successfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Book delete failed. Try Again!";
			}
		
		
	}
	
	

}
