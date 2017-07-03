<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="include.html" %>
<%@page import="java.util.*" %>
<%@page import="com.gcit.lms.service.AdminService" %>

<%AdminService adminService = new AdminService();
List<Author> authors = adminService.getAllAuthors(1);
List<Genre> genres = adminService.getAllGenres();
List<Publisher> publishers =adminService.getAllPublishers();
%>
<div>
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Please Insert Author Details</h2>
	<form action="addBook" method="post">
		<select name="authorName"> 
		<%for (Author a: authors) {%>
			<option  value="<%=a.getAuthorId() %>"><%=a.getAuthorName()%></option>
			
			<% }%>
			</select>
			
		<select name="genreName">
			<%for (Genre g: genres) {%>
				<option value="<%=g.getGenreId() %>"><%=g.getGenreName()%></option>
			<% }%>
		</select>
		
		<select name="publisherName">
			<%for (Publisher p: publishers) {%>
				<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%>,<%=p.getPublisherAddress()%></option>
			<% }%>
		</select>
		
		Enter Book Name to be added: <input type="text" name="bookName"><br />
		<button type="submit">Add Book!</button>
		
	</form>
</div>