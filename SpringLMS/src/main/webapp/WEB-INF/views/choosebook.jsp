
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="include.html"%>
<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.util.*"%>


<div class="jumbotron">
	
	<h2>Below are the list of Books in LMS</h2>
	
	<h5>${message}</h5>

	<table class="table">
		<tr>
			<th>Book ID</th>
			<th>Title</th>
			<th>Author of Book</th>
			<th>Edit</th>
		</tr>
		
		<gcit:forEach items="${books }" var="b" varStatus="loopCounter">
		<tr>
			<td>${loopCounter }</td>
			<td>${book.Title }</td>
			<td>
				<gcit:forEach items="${authors}" var ="a" varStatus="loopCounter">
					${author.auhthorName};
				</gcit:forEach>
			</td>
			<td><a href="updatecops.jsp?bookId=${book.bookId }&branchId=${branchId}">
   <button class="btn btn-sm btn-primary">Submit</button></a></td>
		</tr>
		</gcit:forEach>
	</table>
</div>




