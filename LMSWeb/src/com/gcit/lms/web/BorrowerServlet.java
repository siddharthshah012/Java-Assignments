package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Library;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/BorrowerServlet","/checkCardNo","/booksinBranch","/checkout"})
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
		case "/checkout": checkout(request);
						message="passed";
						break;
						
						
		}
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("/showallbranches.jsp");
		rd.forward(request, response);
		
	}

	private void checkout(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//int bookId,branchId,cardNo;
		String message ="";
		BookLoans bl = new BookLoans();
		BorrowerService bservice = new BorrowerService();
		
					bl.setBookId(Integer.parseInt(request.getParameter("bookId")));;
					bl.setBranchId(Integer.parseInt(request.getParameter("bookId")));
					bl.setCardNo(Integer.parseInt(request.getParameter("cardNo")));
					
					try {
						bservice.checkBookOut(bl);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
