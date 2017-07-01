<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="include.html" %>
<%@page import="java.util.*" %>
<%@page import="com.gcit.lms.service.AdminService" %>

<%AdminService adminService = new AdminService();
List<Author> authors = adminService.getAllAuthors(1,null);
List<Genre> genres = adminService.getAllGenre( );
List<Publisher> publishers =adminService.getAllPublishers(1,null);
%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Please Insert Book Details</h2>
	<form action="addBook" method="post">
	<table class="table table-striped">
	<tr>
		<th>SELECT AUTHOR NAMES</th>
		<th> SELECT GENRE NAMES</th>
		<th>SELECT PUBLISHER NAME</th>
	</tr>
	
	<tr>
	
	<td>
		<select multiple name="authorName"> 
		<%for (Author a: authors) {%>
			<option  value="<%=a.getAuthorId() %>"><%=a.getAuthorName()%></option>
			
			<% }%>
			</select> </td>
	<td>
		<select multiple name="genreName">
			<%for (Genre g: genres) {%>
				<option value="<%=g.getGenreId() %>"><%=g.getGenreName()%></option>
			<% }%>
		</select> </td>
		<td><select name="publisherName">
			<%for (Publisher p: publishers) {%>
				<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%>,<%=p.getPublisherAddress()%></option>
			<% }%>
		</select> </td>
	</tr>
	</table>
		Enter Book Name to be added: <input type="text" name="bookName"><br />
		
		<button class="btn btn-sm btn-primary" type="submit">Add Book!</button>
		
	</form>
	
</div>