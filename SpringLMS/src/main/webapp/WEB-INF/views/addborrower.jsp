<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%AdminService service = new AdminService();
	List<Book> books = service.getAllBooks();
	
%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Please Insert Author Details</h2>
	<form action="addBorrower" method="post">
		Enter Name to be added: <input type="text" name="name"><br />
		Enter Address to be added:<input type="text" name="address"><br/>
		Enter Phone to be added:<input type="text" name="phone"><br/>
		<button type="submit">Add Borrower</button>
	</form>
</div>