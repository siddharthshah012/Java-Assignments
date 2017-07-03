package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
@WebServlet({"/BorrowerServlet","/checkCardNo","/checkout","/returnBook"})
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

	private void checkout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String message ="";
		Integer bookList = 0;
		//BookLoans bl = new BookLoans();
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
				if (bookList == 1){
					message = "Updated";
					
				}else{
					message="failed";
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("/showallbranches.jsp");
			rd.forward(request, response);
		} 
		
		
		
	}

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
		case"/returnBook": returnBook(request, response);
							break;
		
	}

}

	private void returnBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BookLoans bl = new BookLoans();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date parsedDate = null;
		try {
			parsedDate = df.parse(request.getParameter("dateOut"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message="";
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		bl.setDateOut(timestamp);
		
		Book book = new Book();
		book.setBookId(Integer.parseInt(request.getParameter("bookId")));
		bl.setBooks(book);
			
		
		Library branch = new Library();
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		bl.setBranch(branch);
		
		BorrowerService bservice = new BorrowerService();
		try {
			bservice.returnBookloan(bl);
			message="passed";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("/cardNo.jsp");
		rd.forward(request, response);
	}

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
				RequestDispatcher rd = request.getRequestDispatcher("/optionborrower.jsp");
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
					StringBuffer strBuf = new StringBuffer();
					
					String addAuthorResult = "Card verification failed";
					
					strBuf.append(addAuthorResult);
					response.getWriter().write(strBuf.toString());
					//rd.forward(request, response);
				} catch (IOException e) {
					
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
