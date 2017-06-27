<%@page import="com.gcit.lms.entity.Library"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html" %>

<% 
AdminService adminService = new AdminService();
List<Library> library = adminService.getAllBranches(1);
%>
<div class="jumbotron">

	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin! Please pick an action!</h2>	

	<table class="table">
		<tr>
			<th>ID</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>EDIT</th>
			<th>DELETE</th>
		</tr>
		<%for(Library l: library){ %>
		<tr>
			<td><%=library.indexOf(l)+1 %></td>
			<td><%=l.getBranchName()%></td>
			<td><%=l.getBranchAddress() %></td>
			<td><button type="button" class="btn btn-sm btn-success" 
				data-toggle="modal" data-target="#editLibModal"
				href="editbranchadmin.jsp?libBranchId=<%=l.getBranchId()%>">EDIT!</button></td>			
			<td><button type="button" class="btn btn-sm btn-danger" 
			onclick="javascript:location.href='deleteBranch?branchId=<%=l.getBranchId()%>'">Delete!</button></td>	
		</tr>
		<%} %>
	</table>
	
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editLibModal" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
				
		</div>
	</div>
</div>
