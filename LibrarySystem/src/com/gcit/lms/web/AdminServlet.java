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
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/deleteAuthor", "/editAuthor", "/pageAuthors", "/searchAuthors",
			"/addBook","/deleteBook","/editBook","/pageBooks","/searchBooks",
			"/addPublisher","/deletePublisher","/editPublisher","/pagePublishers","/searchPublishers"})
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
		
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Author author = new Author();
		String message = "";
		Boolean isAjax = Boolean.FALSE;
		switch (reqUrl) {
		case "/deleteAuthor":
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
			break;
		case "/pageAuthors":
			if (request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));

				try {
					List<Author> authors = adminService.getAllAuthors(pageNo, null);
					request.setAttribute("authors", authors);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/searchAuthors":
			String searchString = request.getParameter("searchString");
			System.out.println(searchString);
			try {
				
				List<Author> authors = adminService.getAllAuthors(1, searchString);
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("<tr><th>Author ID</th><th>Author Name</th><th>Books by Author</th><th>Edit</th><th>Delete</th></tr>");
				for(Author a: authors){
					strBuf.append("<tr><td>+"+authors.indexOf(a) + 1+"</td><td>"+a.getAuthorName()+"</td><td>");
					for(Book b: a.getBooks()){
						strBuf.append(b.getTitle() + '|');
					}
					strBuf.append("</td><td><button type='button' class='btn btn-sm btn-primary'data-toggle='modal' data-target='#editAuthorModal'href='editauthor.jsp?authorId="+a.getAuthorId()+"'>Edit!</button></td>");
					strBuf.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()+"''>Delete!</button></td></tr>");
				}
				response.getWriter().write(strBuf.toString());
				isAjax = Boolean.TRUE;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case"/deleteBook": Book book = new Book();
							if (request.getParameter("bookId") != null && !request.getParameter("bookId").isEmpty()) {
							book.setBookId(Integer.parseInt(request.getParameter("bookId")));
				
							try {
								adminService.deleteBook(book);
								message = "Book deleted Successfully";
							} catch (SQLException e) {
								e.printStackTrace();
									message = "Book delete failed. Try Again!";
								}
							}
							
							request.setAttribute("message", message);
							RequestDispatcher rd = request.getRequestDispatcher("/viewbooks.jsp");
							rd.forward(request, response);
							isAjax = Boolean.TRUE;
							
							break;
		case "/pageBooks":
							if (request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
								Integer pageNo1 = Integer.parseInt(request.getParameter("pageNo"));
				
								try {
									List<Book> books = adminService.getAllBooks(pageNo1, null);
									request.setAttribute("books", books);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								request.setAttribute("message", message);
								RequestDispatcher rd1 = request.getRequestDispatcher("/viewbooks.jsp");
								rd1.forward(request, response);
								isAjax = Boolean.TRUE;
							}
							break;
							
		case"/searchBooks":String searchStringB = request.getParameter("searchString");
							System.out.println(searchStringB);
							try {
								List<Book> books = adminService.getAllBooks(1, searchStringB);
								StringBuffer strBuf1 = new StringBuffer();
								strBuf1.append("<tr><th>Book ID</th><th>Book Title</th><th>Author Name</th>"
										+ "<th>Genre</th><th> Publisher</th><th>EDIT</th><th>Delete</th></tr>");
								for(Book b: books){
									
									strBuf1.append("<tr><td>"+books.indexOf(b)+1+"</td><td>"+b.getTitle()+"</td><td>");
									for(Author a: b.getAuthors()){
										strBuf1.append(a.getAuthorName()+"|");
										} 
									strBuf1.append("</td><td>");
									for(Genre g: b.getGenres()){
										strBuf1.append(g.getGenreName()+"|");
									}
									strBuf1.append("</td><td>"+b.getPublisher().getPublisherName()+"</td><td><button type='button' "
											+ "class='btn btn-sm btn-primary' data-toggle='modal' data-target='#editBookModal' "
											+ "href='editbook?bookId="+b.getBookId()+"'>Edit!</button></td>	");
									strBuf1.append("<td><button type='button' class='btn btn-sm btn-danger' "
											+ "onclick='javascript:location.href='deleteBook?bookId="+b.getBookId()+"''>Delete!</button></td></tr>");
								}
								
								response.getWriter().write(strBuf1.toString());
								isAjax = Boolean.TRUE;
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
							
		case "/deletePublisher":
			Publisher publisher = new Publisher();
			List<Publisher> publishers = null;
			@SuppressWarnings("unused")
			String path="";
			if (request.getParameter("publisherId") != null
					&& !request.getParameter("publisherId").isEmpty()) {
				publisher.setPublisherId(Integer.parseInt(request.getParameter("publisherId")));
				
				try {
					adminService.deletePublisher(publisher);
					publishers = adminService.getAllPublishers(1, null);
					message = "Publisher deleted Successfully";
					
					StringBuffer strBuf1 = new StringBuffer();
					
					strBuf1.append("<tr><th>ID</th><th>Publisher Name</th>"
							+ "<th>Publisher Address</th><th>Publisher Phone</th>"
							+ "<th>EDIT</th><th>DELETE</th></tr>");
					for (Publisher p: publishers){
						strBuf1.append("<tr><td>"+(publishers.indexOf(p)+1)+"</td><td>"+p.getPublisherName()+"</td><td>"+p.getPublisherAddress()+""
								+ "</td><td>"+p.getPublisherPhones()+"</td>");
						strBuf1.append("<td><button type='button' class='btn btn-sm btn-success' data-toggle='modal' "
								+ "data-target='#editPubModal' href='editpublishers.jsp?pubId="+p.getPublisherId()+"'>EDIT!</button></td>");
						strBuf1.append("<td><button type='button' class='btn btn-sm btn-danger' id='publisherId' onclick='deletePub()' value="+p.getPublisherId()+">Delete!</button></td>");
						strBuf1.append("</tr>");
					}
					response.getWriter().write(strBuf1.toString());
					path="/viewpublishers.jsp";
					
				} catch (SQLException e) {
					e.printStackTrace();
					message = "publisher delete failed. Try Again!";
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			request.setAttribute("message", message);
			RequestDispatcher rdpub = request
					.getRequestDispatcher("/viewpublishers.jsp");
			rdpub.forward(request, response);
			isAjax = Boolean.TRUE;
			break;
			
		case"/pagePublishers":
			
			if (request.getParameter("pageNo") != null && !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo1 = Integer.parseInt(request.getParameter("pageNo"));
			try {
				List<Publisher> publishersPage = adminService.getAllPublishers(pageNo1, null);
				request.setAttribute("publishers", publishersPage);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//request.setAttribute("message", message);
			RequestDispatcher rd1 = request.getRequestDispatcher("/viewpublishers.jsp");
			rd1.forward(request, response);
			isAjax = Boolean.TRUE;
			
			}
			break;
		case"/searchPublishers": 
			String searchStringP = request.getParameter("searchString");
			System.out.println(searchStringP);
			
			try {
				List<Publisher> publishersSearch = adminService.getAllPublishers(1, searchStringP);
				StringBuffer strBuf2 = new StringBuffer();
				
				strBuf2.append("<tr><th>ID</th><th>Publisher Name</th>"
						+ "<th>Publisher Address</th><th>Publisher Phone</th>"
						+ "<th>EDIT</th><th>DELETE</th></tr>");
				for (Publisher p: publishersSearch){
					strBuf2.append("<tr><td>"+(publishersSearch.indexOf(p)+1)+"</td><td>"+p.getPublisherName()+"</td><td>"+p.getPublisherAddress()+""
							+ "</td><td>"+p.getPublisherPhones()+"</td>");
					strBuf2.append("<td><button type='button' class='btn btn-sm btn-success' data-toggle='modal' "
							+ "data-target='#editPubModal' href='editpublishers.jsp?pubId="+p.getPublisherId()+"'>EDIT!</button></td>");
					strBuf2.append("<td><button type='button' class='btn btn-sm btn-danger' id='publisherId' onclick='deletePub()' value="+p.getPublisherId()+">Delete!</button></td>");
					strBuf2.append("</tr>");
				}
				response.getWriter().write(strBuf2.toString());
				path="/viewpublishers.jsp";
				

				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
				
				
		default:
			break;
		}
		if(!isAjax){
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("/viewauthors.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Author author = new Author();
		String path="";
		Boolean flag = Boolean.TRUE;
		
		switch (reqUrl) {
		case "/addAuthor":
			author.setAuthorName(request.getParameter("authorName"));
			try {
				adminService.saveAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			path="/viewauthors.jsp";
			break;
		case "/editAuthor":
			author.setAuthorName(request.getParameter("authorName"));
			author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
			try {
				adminService.saveAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			path="/viewauthors.jsp";
			break;
		case "/searchAuthors":
			String searchString = request.getParameter("searchString");
			try {
				
				List<Author> authors = adminService.getAllAuthors(1, searchString);
				request.setAttribute("authors", authors);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			path="/viewauthors.jsp";
			break;
			
		case"/addBook":addBook(request,response);
				flag = Boolean.FALSE;
			/*
			Book book = new Book();
			book.setTitle(request.getParameter("title"));
			book.setBookId(Integer.parseInt(request.getParameter("bookId")));
			
			try {
				adminService.saveBook(book);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			path="/viewbooks.jsp";*/
			break;
		case"/editBook":
			Book book1 = new Book();
			book1.setTitle(request.getParameter("title"));
			book1.setBookId(Integer.parseInt(request.getParameter("bookId")));
			
			try {
				adminService.saveBook(book1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			path="/viewbooks.jsp";
			break;
		case "/searchBooks":
			String searchStringB = request.getParameter("searchStringB");
			try {
				
				List<Book> books = adminService.getAllBooks(1, searchStringB);
				request.setAttribute("books", books);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			path="/viewbooks.jsp";
			break;
		case"/addPublisher":
			Publisher publisher = new Publisher();

			// publisher.setPublisherId(Integer.parseInt(request.getParameter("")));
			publisher.setPublisherName(request.getParameter("publisherName"));
			publisher.setPublisherAddress(request
					.getParameter("publisherAddress"));
			publisher
					.setPublisherPhones(request.getParameter("publisherPhone"));

			try {
				adminService.savePublisher(publisher);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			path = "/viewpublishers.jsp";
			break;
			
		case"/searchPublishers": 
			String searchPublishers = request.getParameter("searchStringP");
			
			try {
				List<Publisher> publishersList = adminService.getAllPublishers(1, searchPublishers);
				request.setAttribute("publishers", publishersList);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		default:
			break;
		}
		
		if (flag == Boolean.TRUE){
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		}
	}
	
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		Book book = new Book();
		String[] authors, genres,publisher ;
		String title, publisherData = null;
		
		book.setTitle(request.getParameter("bookName"));
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
				Author a = service.getAuthorByPK(Integer.parseInt(temp[0]));
				authorList.add(a);
			}
			for(String genre: genres) {
				String[] temp = genre.split(" ");
				Genre g = service.getGenreByPK(Integer.parseInt(temp[0]));
				genreList.add(g);
			}
			book.setAuthors(authorList);
			book.setGenres(genreList);
			book.setTitle(title);
			if(!publisherData.isEmpty()) {
				publisherSelected = service.getPublisherbyPK(Integer.parseInt(publisher[0]));
				book.setPublisher(publisherSelected);
			}else
				book.setPublisher(null);
			
			service.saveBook(book);
		} catch (SQLException e) {
			//System.out.println("Adding book failed.");
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/viewbooks.jsp");
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
