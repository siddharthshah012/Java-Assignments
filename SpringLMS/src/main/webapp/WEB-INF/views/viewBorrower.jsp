<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html" %>



<% 
AdminService adminService = new AdminService();
List<Borrower> borrower = adminService.getAllBorrower(); 
out.println(borrower);
%>
<div class="jumbotron">

	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin! Please pick an action!</h2>	
	
	
	<table class="table">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Address</th>
			<th>Phone</th>
			<th>EDIT</th>
			<th>DELETE</th>
		</tr>
		<%for(Borrower b: borrower){ %>
		<tr>
			<td><%=borrower.indexOf(b)+1 %></td>
			<td><%=b.getName()%></td>
			<td><%=b.getAddress()%></td>
			<td><%=b.getPhone() %></td>
			<td><button type="button" class="btn btn-sm btn-success" 
				data-toggle="modal" data-target="#editBorModal"
				href="editborrower.jsp?cardNo=<%=b.getCardNo()%>">EDIT!</button></td>			
			<td><button type="button" class="btn btn-sm btn-danger" onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>'">Delete!</button></td>	
		</tr>
		<%} %>
	</table>

</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editBorModal" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
				
		</div>
	</div>
</div>

