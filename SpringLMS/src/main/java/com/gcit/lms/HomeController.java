package com.gcit.lms;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@RequestMapping(value = "/addAuthor", method= RequestMethod.GET)
	public String addAuthor(Model model) throws ClassNotFoundException, SQLException{
		
		List<Author> authors = adminService.getAllAuthors(1,null);
		List<Genre> genres = adminService.getAllGenre();
		List<Publisher> publishers = adminService.getAllPublisher(1,null);
		model.addAttribute("authors", authors);
		model.addAttribute("genres", genres);
		model.addAttribute("publishers", publishers);
		
		return "addauthor";
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
