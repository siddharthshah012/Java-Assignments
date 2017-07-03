<%@include file="include.html" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.*" %>
<% 
	AdminService adminService = new AdminService();
	List<Borrower> borrower = adminService.getAllBorrower();
%>

<div class ="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Below are the list of Books in LMS</h2>
	<a href="welcome.jsp">Welcome page</a></br>
	<a href="book.jsp">GO back</a></br>
	${message} 

	<table class="table">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Address</th>
			<th>Phone</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%for(Borrower b: borrower){ %>
		<tr>
			<td><%=borrower.indexOf(b)+1 %></td>
			<td><%=b.getName()%></td>
			<td><%=b.getAddress() %></td>
			<td><%=b.getPhone() %></td>
			<td><button type="button"  class="btn btn-sm btn-primary" data-toggle="modal" 
			data-target="#editAuthorModal" href="editBorrower.jsp?cardNo=<%=b.getCardNo()%>">EDIT!</button></td>
			<td><button type="button" class="btn btn-sm btn-danger" onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>'">Delete!</button></td>	
		</tr>
		<%} %>
	</table>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editAuthorModal" role="dialog" aria-labelledby="myLargeModalLabel">
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

