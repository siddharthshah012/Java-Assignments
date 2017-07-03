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

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Library;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/BorrowerServlet","/checkCardNo","/booksinBranch","/checkout","/loanbooks","/returnBook"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = "";
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/checkout": checkout(request, response);
						message="passed";
						break;
						
		/*case "/loanbooks": returnLoanBook(request,response);
							message="Successfull";
							break;*/
		}
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("/showallbranches.jsp");
		rd.forward(request, response);	
	}
/*
	@SuppressWarnings("unchecked")
	private void returnLoanBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		List<BookLoans> bookLoansList = new ArrayList<BookLoans>();
		BorrowerService bservice = new BorrowerService();
		
		if (cardNo> 0){
			
			BookLoans bookloans = new BookLoans();
			
			Borrower bor = new Borrower();
			bor.setCardNo(cardNo);
			bookloans.setBorrower(bor);
			try {
				bookLoansList= (List<BookLoans>) bservice.readBooksforCard(bookloans);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("bookLoansList", bookLoansList);
			RequestDispatcher rd = request.getRequestDispatcher("/showallloans.jsp");
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
*/
	private void checkout(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		//int bookId,branchId,cardNo;
		String message ="";
		Integer bookList = 0;
		BookLoans bl = new BookLoans();
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		BorrowerService bservice = new BorrowerService();
		if (cardNo > 0) {
			BookLoans bookLoans=new BookLoans();
			
			Book books = new Book();
			books.setBookId(bookId);
			bookLoans.setBooks(books);
			
			Borrower borrower = new Borrower();
			borrower.setCardNo(cardNo);
			bookLoans.setBorrower(borrower);
			
			Library lib = new Library();
			lib.setBranchId(branchId);
			bookLoans.setBranch(lib);
			
			try {
				bookList = (Integer) bservice.checkBookOut(bookLoans);
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
	}

	public static Library library;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		
		String returnPath="";
		switch (reqUrl) {
		case "/checkCardNo":checkCardNo(request, response);
							//returnPath = "/verifyBAdmin.jsp";
							break;
		}
		
	}
	
	@SuppressWarnings("unused")
	private void checkCardNo(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int cardNo = 0;
		cardNo = Integer.parseInt(request.getParameter("cardNo"));
		System.out.println("in servelet" + cardNo);
		BorrowerService bservice = new BorrowerService();
		try {
			if (bservice.checkCardNo(cardNo) == 1){
				System.out.println("in if loop servlet");
				request.setAttribute("cardNo", cardNo);
				RequestDispatcher rd = request.getRequestDispatcher("/optionforborrower.jsp");
				try {
					String addAuthorResult = "Card verification Passed";
					request.setAttribute("message", addAuthorResult);
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/cardNo.jsp");
				try {
					String addAuthorResult = "Card verification failed";
					request.setAttribute("message", addAuthorResult);
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	

}
