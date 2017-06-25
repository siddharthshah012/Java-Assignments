<%@include file="include.html" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book" %>

<%
AdminService adminService = new AdminService();
List<Book> books = adminService.getAllBooks();
/*Integer authorId = Integer.parseInt(request.getParameter("authorId"));
Author author = adminService.getAuthorId(authorId);*/%>
<div class ="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Below are the list of Books in LMS</h2>
	<a href="welcome.jsp">Welcome page</a></br>
	<a href="book.jsp">GO back</a></br>
	${message} 

	<table class="table">
		<tr>
			<th>Book ID</th>
			<th>Book Title</th>
			<th>Delete</th>
		</tr>
		<%for(Book b: books){ %>
		<tr>
			<td><%=books.indexOf(b)+1 %></td>
			<td><%=b.getTitle()%></td>		
			<td><button type="button" class="btn btn-sm btn-danger" onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>'">Delete!</button></td>	
		</tr>
		<%} %>
	</table>
</div>




