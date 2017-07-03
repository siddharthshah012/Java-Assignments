package com.gcit.lms;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	BorrowerService bservice;
	
	@Autowired
	LibrarianService lservice;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "welcome";
	}
		
	@RequestMapping(value = "/admin", method= RequestMethod.GET)
	public String admin(){
		return "admin";
	}
	
	@RequestMapping(value = "/author", method= RequestMethod.GET)
	public String author(){
		return "author";
	}
	
	@RequestMapping(value = "/book", method= RequestMethod.GET)
	public String book(){
		return "book";
	}
	
	@RequestMapping(value = "/publisher", method= RequestMethod.GET)
	public String publisher(){
		return "publisher";
	}
	
	
	/*******************
	 * AUTHORS
	 * ******************/
	
	@RequestMapping(value = "/viewAuthors", method= RequestMethod.GET)
	public String viewAuthor(Model model) throws SQLException{
		
		try {
			model.addAttribute("authors", adminService.getAllAuthors(1, null));
			List<Author> authors = new ArrayList<>();
			Integer authCount = adminService.getAuthorsCount();
			int pages = 0;
			if (authCount % 10 > 0) {
				pages = authCount / 10 + 1;
			} else {
				pages = authCount / 10;
			}
			model.addAttribute("pages", pages);		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "viewauthors";
	}
	
	
	
	@RequestMapping(value = "/pageAuthors", method= RequestMethod.GET)
	public String pageAuthor(Model model, @RequestParam("pageNo") Integer pageNo){
		if (pageNo != null ) {
			try {
				List<Author> authors = adminService.getAllAuthors(pageNo, null);
				model.addAttribute("authors", authors);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "viewauthors";
	}
	
	
	@RequestMapping(value = "/addAuthor", method= RequestMethod.GET)
	public String addAuthor(Model model, @RequestParam("bookId") Integer bookId,
			@RequestParam("bookName") String bookName, @RequestParam("authorName") String authorName
			) throws Exception{
		
		Author author = new Author();
		author.setAuthorName(authorName);
		
		adminService.saveAuthor(author);
		return "viewAuthors";
	}
	
	@RequestMapping(value = "/editAuthor", method= RequestMethod.GET)
	public String editAuthor(Model model, @RequestParam("bookId") Integer bookId, @RequestParam("authorId") Integer authorId,
			@RequestParam("authorName") String authorName
			) throws Exception{
		
		Author author = new Author();
		author.setAuthorName(authorName);
		author.setAuthorId(authorId);
		
		adminService.saveAuthor(author);
		return "viewAuthors";
	}
	
	@RequestMapping(value = "/deleteAuthor", method= RequestMethod.GET)
	public String deleteAuthor(Model model, @RequestParam("authorId") Integer authorId ) throws ClassNotFoundException, SQLException{
		
		String message = "";
		if (authorId != null ) {
			
			Author author =  new Author();
			author.setAuthorId(authorId);
			try{
				adminService.deleteAuthor(author);
				message = "Author deleted Successfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author delete failed. Try Again!";
			}
		}
		return "viewauthors";
	
	}

	@RequestMapping(value = "/searchAuthor", method = RequestMethod.GET)
	public String searchAuthor(Model model,
			@RequestParam("searchString") String searchString,
			HttpServletResponse response) throws IOException {

		try {

			List<Author> authors = adminService.getAllAuthors(1, searchString);
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("<tr><th>Author ID</th><th>Author Name</th><th>Books by Author</th><th>Edit</th><th>Delete</th></tr>");
			for (Author a : authors) {
				strBuf.append("<tr><td>+" + authors.indexOf(a) + 1
						+ "</td><td>" + a.getAuthorName() + "</td><td>");
				for (Book b : a.getBooks()) {
					strBuf.append(b.getTitle() + '|');
				}
				strBuf.append("</td><td><button type='button' class='btn btn-sm btn-primary'data-toggle='modal' data-target='#editAuthorModal'href='editauthor.jsp?authorId="
						+ a.getAuthorId() + "'>Edit!</button></td>");
				strBuf.append("<td><button type='button' class='btn btn-sm btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="
						+ a.getAuthorId() + "''>Delete!</button></td></tr>");
			}
			response.getWriter().write(strBuf.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "viewauthors";

	}
	
	/*
	 * 
	 * BOOKS
	 * 
	 */
	
	
	@RequestMapping(value= "/viewBook", method = RequestMethod.GET)
	public String viewBooks(Model model) throws SQLException{
		
		model.addAttribute("books", adminService.getAllBooks(1, null));
		List<Book> books = new ArrayList<>();
		Integer bookCount = adminService.getBooksCount();
		int pages = 0;
		if (bookCount % 10 > 0) {
			pages = bookCount / 10 + 1;
		} else {
			pages = bookCount / 10;
		}
		model.addAttribute("pages", pages);	
		
		return null;
		
		
	}
	
	
	@RequestMapping(value = "/pageBooks", method= RequestMethod.GET)
	public String pageBook(Model model, @RequestParam("pageNo") Integer pageNo){
		if (pageNo != null ) {
			try {
				List<Book> books = adminService.getAllBooks(pageNo, null);
				model.addAttribute("books", books);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "viewbooks";
	}
	
	
	
	@RequestMapping(value = "/editBook", method = RequestMethod.GET)
	public String editBook(Model model,@RequestParam("title") String title,@RequestParam("bookId") Integer bookId) throws Exception{
		
		Book book1 = new Book();
		book1.setTitle(title);
		book1.setBookId(bookId);
		
		try {
			adminService.saveBook(book1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/viewbooks";
	}
	
	@RequestMapping(value = "/searchBooks", method = RequestMethod.GET)
	public String searchBook(Model model,
			@RequestParam("searchString") String searchString,
			HttpServletResponse response) throws IOException {
		
		try {
			List<Book> books = adminService.getAllBooks(1, searchString);
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
			//isAjax = Boolean.TRUE;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewbooks";
	}
	

	@RequestMapping(value = "/deleteBook", method= RequestMethod.GET)
	public String deleteBook(Model model, @RequestParam("bookId") Integer bookId){
		
		Book book = new Book();
		String message= "";
		if (bookId != null ) {
		book.setBookId(bookId);
			try {
				adminService.deleteBook(book);
				message = "book deleted Successfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Book delete failed. Try Again!";
			}
		}
		model.addAttribute("message",message);
		
		return "viewbooks";
	}
	

	@RequestMapping(value = "/addBook", method= RequestMethod.GET)
	public String addBook(Model model, @RequestParam("bookId") Integer bookId,
			@RequestParam("bookName") String bookName,
			@RequestParam("auhtorId") String authors,
			@RequestParam("genres") String genres,
			@RequestParam("publisherId") Publisher publisher) throws ClassNotFoundException, SQLException{
		
		Book book = new Book();
	
		String title, publisherData = null;
		book.setTitle("bookName");
		
		//publisher = publisher.split(" ");
	
		AdminService service = new AdminService();
		List<Author> authorList = new ArrayList<>();
		List<Genre> genreList = new ArrayList<>();
		Publisher publisherSelected;
		
		try {
			for(Author author: authorList) {
				String[] temp = author.split(" ");
				Author a = adminService.getAuthorByPK(Integer.parseInt(temp[0]));
				authorList.add(a);
			}
			for(String genre: genreList) {
				String[] temp = genre.split(" ");
				Genre g = adminService.getGenreByPK(Integer.parseInt(temp[0]));
				genreList.add(g);
			}
			book.setAuthors(authorList);
			book.setGenres(genreList);
			book.setTitle(title);
			if(!publisherData.isEmpty()) {
				publisherSelected = service.getPublisherByPK(publisher.getPublisherId());
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
		
		return "viewBooks";
	}
	
	
	
	
	
	
	/*
	 * 
	 * PUBLISHERS
	 * 
	 * Changes to be made call fucntion for pages
	 */
	
	@RequestMapping(value= "/viewpublishers", method = RequestMethod.GET)
	public String viewPublishers(Model model) throws SQLException, ClassNotFoundException{
		
		model.addAttribute("publishers", adminService.getAllPublisher(1,null));
		List<Publisher> publishers = new ArrayList<>();
		Integer publishercount = adminService.getPublisherCount();
		int pages = 0;
		if (publishercount % 10 > 0) {
			pages = publishercount / 10 + 1;
		} else {
			pages = publishercount / 10;
		}
		model.addAttribute("pages", pages);	
		
		return "viewpublishers";
	}
	
	@RequestMapping(value = "/pagePublishers", method= RequestMethod.GET)
	public String pagePublisher(Model model, @RequestParam("pageNo") Integer pageNo) throws ClassNotFoundException{
		if (pageNo != null ) {
			try {
				List<Publisher> publishers = adminService.getAllPublisher(1, null);
				model.addAttribute("publishers", publishers);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "viewbooks";
	}
	
	
	@RequestMapping(value = "/editPublisher", method = RequestMethod.GET)
	public String editPublisher(Model model,@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhones") String publisherPhone,
			@RequestParam("publisherId") Integer publisherId) throws Exception{
		
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhones(publisherPhone);
		
		try {
			adminService.savePublisher(publisher);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/viewbooks";
	}
	
	
	@RequestMapping(value = "/addPublisher", method = RequestMethod.GET)
	public String addPublisher(Model model,@RequestParam("publisherName") String publisherName,
			@RequestParam("publisherAddress") String publisherAddress,
			@RequestParam("publisherPhones") String publisherPhone
			) throws Exception{
		
		Publisher publisher = new Publisher();
		//publisher.setPublisherId(publisherId);
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhones(publisherPhone);
		
		try {
			adminService.savePublisher(publisher);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/viewbooks";
	}
	
	
	@RequestMapping(value="/deletePublisher",method = RequestMethod.POST)
	public String deletePublisher(Model model, @RequestParam("publisherId") Integer publisherId, HttpServletResponse response) throws IOException{
		
		Publisher publisher = new Publisher();
		List<Publisher> publishers = null;
		String message= "";
		if (publisherId != null) {
			publisher.setPublisherId(publisherId);
			
			try {
				adminService.deletePublisher(publisher);
				publishers = adminService.getAllPublisher(1, null);
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
				
			} catch (SQLException e) {
				e.printStackTrace();
				message = "publisher delete failed. Try Again!";
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		model.addAttribute("message", message);
		return "viewpublishers";
	}
	
	@RequestMapping(value="/searchPublisher",method = RequestMethod.POST)
	public String searchPublisher(Model model, @RequestParam("publisherId") Integer publisherId, 
			@RequestParam("searchString") String searchString,
			HttpServletResponse response) throws IOException{
	

		try {
			List<Publisher> publishersSearch = adminService.getAllPublisher(1, searchString);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewpublishers";
	}

	
	/****************************Borrower
	 * @throws Exception **********************************************/
	
	
	/*@RequestMapping(value="/cardno", method = RequestMethod.GET)
	public String cardno()
	{
		return "cardno";
	}*/
	
	@RequestMapping(value = "/checkCardNo", method = RequestMethod.POST)
	public String check(Model model, @RequestParam("cardNo") Integer cardNo,
			HttpServletResponse response) throws Exception {

		// cardNo = Integer.parseInt(request.getParameter("cardNo"));

		if (bservice.checkCardNo(cardNo) == 1) {
			System.out.println("in if loop servlet");
			model.addAttribute("cardNo", cardNo);

			String addAuthorResult = "Card verification Passed. "
					+ "What would you like to choose?";
			model.addAttribute("message", addAuthorResult);
			return "/optionborrower";

		} else {
			StringBuffer strBuf = new StringBuffer();
			String addAuthorResult = "Card verification failed";
			strBuf.append(addAuthorResult);
			response.getWriter().write(strBuf.toString());
			return "cardNo";
			// rd.forward(request, response);
		}
	}
	
	
	@RequestMapping(value = "/optionborrower", method = RequestMethod.GET)
	public String optionForBorrower(Model model,
			@RequestParam("cardNo") Integer cardNo) throws SQLException {
		model.addAttribute("cardNo", cardNo);
		return "optionborrower";

	}
	
	
	@RequestMapping(value = "/showallbranches", method = RequestMethod.GET)
	public String showBranches(Model model,
			@RequestParam("cardNo") Integer cardNo) throws SQLException {
		model.addAttribute("cardNo", cardNo);
		Integer branchCount = bservice.getBranchesCount();
		Integer pages = pageCount(branchCount);
		model.addAttribute("pages",pages);
		return "optionborrower";

	}
	
	@RequestMapping(value = "/bookinbranch", method = RequestMethod.GET)
	public String booksInBranches(Model model,
			@RequestParam("cardNo") Integer cardNo,
			@RequestParam("branchId") Integer branchId) throws SQLException,
			ClassNotFoundException {
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("branchId", branchId);
		List<Book> book = bservice.readAllBookswithBranch(branchId);
		model.addAttribute("books",book);
		return "bookinbranch";

	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkOut(Model model, @RequestParam("cardNo") Integer cardNo, @RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId){
		
		BookLoans bookLoans = new BookLoans();
		Book books = new Book();
		books.setBookId(bookId);
		bookLoans.setBooks(books);
		
		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		bookLoans.setBorrower(borrower);
		
		LibraryBranch lib = new LibraryBranch();
		lib.setBranchId(branchId);
		bookLoans.setBranch(lib);
		
		Integer bookList = 0;
		String message ="";
		try {
			bookList = (Integer) bservice.checkBookOut(bookLoans);
			message ="UPdated";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "Failed to update";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "Failed to update";
		}
		model.addAttribute("message",message);
		return "showallbookloans";
		
	}
	
	@RequestMapping(value = "/returnBook", method = RequestMethod.GET)
	public String checkout(Model model, @RequestParam("dateOut") String dateOut,
			@RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId) throws ParseException{
		
		BookLoans bl = new BookLoans();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date parsedDate = df.parse(dateOut);
		
		String message="";
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		bl.setDateOut(timestamp);
		
		Book book = new Book();
		book.setBookId(bookId);
		bl.setBooks(book);
			
		
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(branchId);
		bl.setBranch(branch);
		
		//BorrowerService bservice = new BorrowerService();
		try {
			bservice.returnBookloan(bl);
			message="passed";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showallbookloans";
	} 

	public Integer pageCount(Integer count) throws SQLException{
		
		
		int pages = 0;
		if (count % 10 > 0) {
			pages = count / 10 + 1;
		} else {
			pages = count / 10;
		}
		return pages;	
		
	}
	
	/*******************Librarian*********
	 * 
	 * 
	 * Logic to be changed !!!!!
	 */
	
	@RequestMapping(value = "/librarian", method= RequestMethod.GET)
	public String librarian(){
		return "librarian";
	}
	
	@RequestMapping(value = "/selectbranch", method= RequestMethod.GET)
	public String selectbranch(Model model, @RequestParam("branchId") Integer branchId){
		
		model.addAttribute(branchId);
		return "selectbranch";
	}
	
	
	@RequestMapping(value = "/editBranches", method= RequestMethod.GET)
	public String editBranches(Model model,
			@RequestParam("branchId") Integer branchId,@RequestParam("branchName") String branchName, 
			@RequestParam(value = "branchAddress") String branchAddress) throws Exception{
		LibraryBranch branch = new LibraryBranch();
		List<LibraryBranch> branches = lservice.getAllBranches(1);
		
		branch.setBranchId(branchId);
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		lservice.saveBranch(branch);
		model.addAttribute("branches",branches);
		return "selectbranch";
	}
	
	
	@RequestMapping(value = "/addcopies", method= RequestMethod.GET)
	public String addCopies(){
		return "addcopies";
	}
	
	@RequestMapping(value = "/updatecopies", method = RequestMethod.GET)
	public String chooseCopies(Model model,
			@RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId,
			@RequestParam("numbCopies") Integer numbCopies)
			throws SQLException, ClassNotFoundException {

	int copies = 0;
		BookCopies newcops;

		copies = lservice.getNoCopies(branchId,bookId);
		model.addAttribute("copies", copies);
		
		if(copies==0)
			lservice.insertCopies(branchId,bookId,numbCopies);
		else
			lservice.updateCopies(branchId,bookId,copies+numbCopies);
		
		LibraryBranch branch1 = new LibraryBranch();
		Book book1 = new Book();
		
		
		branch1 = lservice.getBranchByPK(branchId);
		
		book1 = lservice.getBookByPK(bookId);
		
		String branchName = branch1.getBranchName();
		String title = book1.getTitle();
		
		newcops = lservice.readCopies(branchId,bookId);
		
		model.addAttribute("branchName",branchName);
		model.addAttribute("title",title);
		model.addAttribute("newcopies", newcops);
		
		return "result";
	}
	
}
