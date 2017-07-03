<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@include file="include.html" %>
<%@page import="java.util.*" %>
<%@page import="com.gcit.lms.service.AdminService" %>


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
		<gcit:forEach items="authors" var="a">
			<option  value="${a.author.authorId }">${a.author.authorName }</option>
		
			</gcit:forEach>
			</select> </td>
	<td>
		<select multiple name="genreName">
			
			<gcit:forEach items="${genres }" var="g">
				<option value="${g.genre.genreId }">${g.genre.genreName }</option>
			</gcit:forEach>
		</select> </td>
		<td><select name="publisherName">
		<gcit:forEach items=${publishers } var="p">
			
				<option value="${p.publisher.publisherId }">${p.publisher.publisherName },${p.publisher.publisherAddress }</option>
		</gcit:forEach>
		</select> </td>
	</tr>
	</table>
		Enter Book Name to be added: <input type="text" name="bookName"><br />
		
		<button class="btn btn-sm btn-primary" type="submit">Add Book!</button>
		
	</form>
	
</div>