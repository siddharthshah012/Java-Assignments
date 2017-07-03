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
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/deleteAuthor","/editAuthor","/addBook"
	,"/deleteBook","/addBorrower","/deleteBorrower","/searchAuthors","/deleteAuthor" })
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
		
		case"/deleteAuthor":deleteAuthor(request,response);
							forwardPath="/viewauthors.jsp";
							message ="Deleetd Author successfully";
							break;
		case"/deleteBook":	deleteBook(request,response);
							forwardPath="/viewBooks.jsp";
							message ="Deleted Book successfully";
							break;
							
		case"/deleteBorrower":deleteBorrower(request, response);
							forwardPath="/viewBorrowers.jsp";
							message ="Deleted Borowwer successfully";
							break;
		}
		
		
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private void deleteBorrower(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
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
		case"/addBook": 	System.out.println("here");
							addBook(request);
							forwardPath="/viewBooks.jsp";
							break;
		case"/addBorrower": System.out.println("here");
							addBorrower(request);
							forwardPath="/borrowadmin.jsp";
							break;
							
		/*default:forwardPath="/viewAuthors.jsp";
				break;*/

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
		String[] authors, genres,publisher ;
		String title, publisherData = null;
		
		book.setTitle(request.getParameter("bookName"));
		
		try {
			adminService.saveBook(book);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			title = request.getParameter("bookName");
		}catch (NullPointerException e){
			return ;
		}
		
		try {
			authors = request.getParameterValues("authorName");
			genres = request.getParameterValues("genreName");
			publisherData = request.getParameter("publisherName");
			publisher = publisherData.split(" ");
		}catch(NullPointerException e){
			return;
		}
	
		AdminService service = new AdminService();
		List<Author> authorList = new ArrayList<>();
		List<Genre> genreList = new ArrayList<>();
		Publisher publisherSelected;
		
		try {
			for(String author: authors) {
				String[] temp = author.split(" ");
				Author a = service.getAuthorById(Integer.parseInt(temp[0]));
				authorList.add(a);
			}
			for(String genre: genres) {
				String[] temp = genre.split(" ");
				Genre g = service.getGenreById(Integer.parseInt(temp[0]));
				genreList.add(g);
			}
			book.setAuthors(authorList);
			book.setGenres(genreList);
			book.setTitle(title);
			if(!publisherData.isEmpty()) {
				publisherSelected = service.getPublisherById(Integer.parseInt(publisher[0]));
				book.setPublisher(publisherSelected);
			}else
				book.setPublisher(null);
			
			try {
				service.saveBook(book);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			//System.out.println("Adding book failed.");
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
	
	private void deleteAuthor(HttpServletRequest request,HttpServletResponse response) {
		
		Author author = new Author();
		Integer authorId;
		String message = "";
		StringBuilder str = new StringBuilder();
		if (request.getParameter("authorId") != null && !request.getParameter("authorId").isEmpty()) {
			 author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
			try {
				adminService.deleteAuthor(author);
				List<Author> authors = null;
				try {
					authors = adminService.getAllAuthors(null);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				str.append("<tr><th>Author Name</th><th>Book Title</th><th>Edit</th><th>Delete</th></tr>");
				for (Author a : authors) {
					str.append("<tr><td>" + a.getAuthorName()
							+ "</td><td>Book Name</td>");
					str.append("<td><button type='button' onclick='javascript:location.href='editAuthor?authorId="
							+ a.getAuthorId()
							+ "''>EDIT</button><td>"
							+ "<button type='button' onclick='javascript:location.href='deleteAuthor?authorId="
							+ a.getAuthorId() + "''>DELETE</button><td>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author delete failed. Try Again!";
			}
				
				request.setAttribute("message", "Author Deleted Sucessfully");
				
				RequestDispatcher rd = request.getRequestDispatcher("viewauthors.jsp");
				message = "Author deleted Successfully";
				
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
	}
	private void deleteBook(HttpServletRequest request, HttpServletResponse response){
		
		Book book = new Book();
		String message = "";
		//if (request.getParameter("bookId") != null && !request.getParameter("bookId").isEmpty()) {
			book.setBookId(Integer.parseInt(request.getParameter("bookId")));
			try {
				adminService.deleteBook(book);
				message = "book deleted Successfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Book delete failed. Try Again!";
			}
		//}
	}

	
	
	private void addBorrower(HttpServletRequest request){
		
		Borrower borrwer = new Borrower();
		borrwer.setName(request.getParameter("name"));
		borrwer.setAddress(request.getParameter("address"));
		borrwer.setPhone(request.getParameter("phone"));

		try {
			try {
				adminService.saveBorrower(borrwer);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
