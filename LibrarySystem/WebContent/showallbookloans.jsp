<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%@include file="include.html" %>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookLoans" %>

<%Integer cardNo = Integer.parseInt(request.getParameter("cardNo")); %>
<%	
	BookLoans bookl = new BookLoans();
	BorrowerService bservice = new BorrowerService();
	Borrower bor = new Borrower();
	bor.setCardNo(cardNo);
	bookl.setBorrower(bor);
	List <BookLoans> bookLoansList = bservice.readBooksforCardinTBL(bookl);
	//for (BookLoans b: bookLoansList){out.println(b.getBooks());}
%>

<div>
		List of all the books loaned by card Number:
		<table class="table">
			<tr>
				<th>ID</th>
				<th>Book Title</th>
				<th>Branch Name</th>
				<th>Branch Address</th>
				<th>Due date</th>
				<th>SELECT!!!!</th>
			</tr>
			<%for(BookLoans bl: bookLoansList){ %>
				<tr>
					<td><%=bookLoansList.indexOf(bl)+1 %></td>
					<td><%= bl.getBooks().getTitle() %></td>
					<td><%=bl.getBranch().getBranchName() %></td>
					<td><%=bl.getBranch().getBranchAddress() %></td>
					<td><%=bl.getDueDate() %></td>
					<td><form method="post" action="returnBook">
						<input type="hidden" name="dateOut" value='<%=bl.getDateOut() %>'>
						<input type="hidden" name="bookId" value=<%=bl.getBooks().getBookId() %>>
						<input type="hidden" name="branchId" value=<%=bl.getBranch().getBranchId() %>>
						<button type="submit" >RETURN BOOK</button>
					
					</form> </td>				
				</tr>
			<%} %>
			
		</table>
</div>