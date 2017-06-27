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
import com.gcit.lms.entity.Library;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/deleteAuthor", "/editAuthor", "/pageAuthors", "/searchAuthors",
			/*"/addBook","/deleteBook","editBook","/addBranch","/deleteBranch","/editBranch", 
			"/addBorrower","/deleteBorrower","/editBorrower",*/"/addPublisher","/deletePublisher",
			"/editPublisher"})
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
		String path="";
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
				path="/viewauthors.jsp";
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
				path="/viewauthors.jsp";
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
								path="/viewauthors.jsp";
								
								break;
								
		case"/deleteBook":	Book book = new Book();
							
							if (request.getParameter("bookId") != null && !request.getParameter("bookId").isEmpty()) {
							book.setBookId(Integer.parseInt(request.getParameter("bookId")));
								try {
									adminService.deleteBook(book);
									message = "book deleted Successfully";
								} catch (SQLException e) {
									e.printStackTrace();
									message = "Book delete failed. Try Again!";
								}
								path="/viewBooks.jsp";
							}
							
							
							
							break;
		case"/deleteBranch":Library library  = new Library();
							library.setBranchId(Integer.parseInt(request.getParameter("branchId")));
								try {
									adminService.deleteBranch(library);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								path="/viewBranch.jsp";
								break;
		case"/deletePublisher": Publisher pub  = new Publisher();
								pub.setPublisherId(Integer.parseInt(request.getParameter("pubId")));
								try {
									System.out.println(pub +"in servlet");
									adminService.deletePublisher(pub);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								path="/viewpublishers.jsp";
								break;
		case"/deleteBorrower": Borrower bor =new Borrower();
								bor.setCardNo(Integer.parseInt(request.getParameter("cardNo")));
								try {
									adminService.deleteBorrower(bor);
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								path="/viewBorrowers.jsp";
								break;
		default:
			break;
		}
		if(!isAjax){
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher(path);
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
		
		switch (reqUrl) {
		case "/addAuthor":
			author.setAuthorName(request.getParameter("authorName"));
			try {
				adminService.saveAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		
		case"/addBook": 	System.out.println("here");
							addBook(request, response);
							
							break;
		case "/editAuthor":
							author.setAuthorName(request.getParameter("authorName"));
							author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
							try {
								adminService.saveAuthor(author);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							RequestDispatcher rd = request.getRequestDispatcher("/viewauthors.jsp");
							rd.forward(request, response);
							break;
							
		case "/searchAuthors":
							String searchString = request.getParameter("searchString");
							try {
								
								List<Author> authors = adminService.getAllAuthors(1, searchString);
								request.setAttribute("authors", authors);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							RequestDispatcher rd1 = request.getRequestDispatcher("/viewauthors.jsp");
							rd1.forward(request, response);
							break;
							
		case "/addBranch":
							Library lib = new Library();
							//author.setAuthorName(request.getParameter("authorName"));
							lib.setBranchName(request.getParameter("branchName"));
							lib.setBranchAddress(request.getParameter("branchAddress"));
								try {
									adminService.saveBranch(lib);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								RequestDispatcher rd2 = request.getRequestDispatcher("/viewBranch.jsp");
								rd2.forward(request, response);
								break;
					
		case"/addBorrower":	Borrower bor = new Borrower();
							bor.setName(request.getParameter("name"));
							bor.setAddress(request.getParameter("address"));
							bor.setPhone(request.getParameter("phone"));
							
							
							try {
								adminService.saveBorrower(bor);
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							RequestDispatcher rd3 = request.getRequestDispatcher("/viewBorrowers.jsp");
							rd3.forward(request, response);
							break;
							
		case"/addPublisher": Publisher pub = new Publisher();
							pub.setPublisherName(request.getParameter("publisherName"));
							pub.setPublisherAddress(request.getParameter("publisherAddress"));
							pub.setPublisherPhones(request.getParameter("publisherPhone"));
							
							try {
								adminService.savePublisher(pub);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							RequestDispatcher rd4 = request.getRequestDispatcher("/viewpublishers.jsp");
							rd4.forward(request, response);
							break;
		case"/editPublisher": Publisher pub1 = new Publisher();
							pub1.setPublisherName(request.getParameter("publisherName"));
							pub1.setPublisherAddress(request.getParameter("publisherAddress"));
							pub1.setPublisherPhones(request.getParameter("publisherPhone"));
							pub1.setPublisherId(Integer.parseInt((request.getParameter("publisherId"))));
							
							System.out.println(pub1.getPublisherId() + "in servlet" + pub1.getPublisherAddress());
							try {
								adminService.savePublisher(pub1);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							RequestDispatcher rd5 = request.getRequestDispatcher("/viewpublishers.jsp");
							rd5.forward(request, response);
							break;
		case"/editBorrowers":Borrower bow = new Borrower();
							
							bow.setName(request.getParameter("borrowerName"));
							bow.setAddress(request.getParameter("borrowerAddress"));
							bow.setPhone(request.getParameter("borrowerPhone"));
							bow.setCardNo(Integer.parseInt(request.getParameter("cardNo")));
		
							try {
								adminService.saveBorrower(bow);
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							RequestDispatcher rd6 = request.getRequestDispatcher("/viewBorrowers.jsp");
							rd6.forward(request, response);
							
							break;
		case"/editBranch": Library lib1 = new Library();
							
							lib1.setBranchName(request.getParameter("branchName"));
							lib1.setBranchAddress(request.getParameter("branchAddress"));
							lib1.setBranchId(Integer.parseInt((request.getParameter("branchId"))));
				
							try {
								adminService.saveBranch(lib1);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							RequestDispatcher rd7 = request.getRequestDispatcher("/viewBranch.jsp");
							rd7.forward(request, response);
							break;
							
							
			default:
				break;
		}
		
	}

	private void addBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
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
				Author a = service.getAuthorByPK(Integer.parseInt(temp[0]));
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
		RequestDispatcher rd = request.getRequestDispatcher("/viewBooks.jsp");
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
