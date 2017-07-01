<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%AdminService service = new AdminService();
	List<Book> books = service.getAllBooks(1,null);
	
%>


<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Please Insert Author Details</h2>
	<form action="addAuthor" method="post">
		<div>
		<h5>Enter Author Name to be added:<h5> 
		<input type="text" name="authorName"><br />
		</div>
		<div>
		<select name="bookId" multiple="multiple">
			<%for(Book b: books){ %>
				<option value="<%=b.getBookId()%>"><%=b.getTitle() %></option>
			<%} %>
		</select>
		</div>
		<div>
		<button type="submit">Add Author!</button>
		</div>
	</form>
</div>