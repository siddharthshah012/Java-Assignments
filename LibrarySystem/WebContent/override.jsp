<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	AdminService adminservice = new AdminService();
	List<BookLoans> bookloans = new ArrayList();/*adminservice.getallBookloans();*/
	for(BookLoans b: bookloans){
	out.println(b.getDateOut());}
%>


<div class ="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Below are the list of Books in LMS</h2>
	<a href="welcome.jsp">Welcome page</a></br>
	
	${message} 

	<table class="table">
		<tr>
			<th> ID</th>
			<th>Book Name</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>Borrower Name</th>
			<th> Due Date</th>
			<th>OVER RIDE</th>
		</tr>
		<%for(BookLoans b: bookloans){ %>
		<tr>
			<td><%=bookloans.indexOf(b)+1 %></td>
			<td><%=b.getBooks().getTitle()%></td>
			<td><%=b.getBranch().getBranchName() %></td>
			<td><%=b.getBranch().getBranchAddress() %></td>
			<td><%=b.getBorrower().getName() %></td>
			<td><%=b.getDueDate() %></td>
			
			<td><button type="button" class="btn btn-sm btn-primary"
					onclick="javascript:location.href='selectduedate.jsp?dateOut=<%=b.getDateOut()%>'">Edit!</button></td>
		</tr>
		<%} %>
	</table>
</div>
<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="overrideDuedate" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// codes works on all bootstrap modal windows in application
		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
</script>