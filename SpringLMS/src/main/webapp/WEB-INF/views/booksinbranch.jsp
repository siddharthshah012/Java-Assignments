<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gcit.lms.entity.*" %>
<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div>

<h5>	${message} </h5>
<table class="table">
	<tr>
			<th>Book ID</th>
			<th>Book Title</th>
		<!-- <th>Author Name</th> -->		
			<th>Edit</th>
		</tr>
	
		<gcit:forEach 	items="${book}" var="book" varStatus="loopCounter">
		
		<tr>
		
		<td>${loopCounter.count}</td>
		<td>${book.Title}</td>
		<!-- Add Title -->
		<td><form action="checkout" method="get">
			<input type="hidden" name = "bookId" value= ${book.bookId }>
			<input type="hidden" name = "branchId" value= ${branchId }>
			<input type="hidden" name = "cardNo" value= ${cardNo }>
			
			<button type="submit"  class="btn btn-sm btn-primary">SELECT!</button></form></td>
		
		</tr>
		</gcit:forEach>
	</table>
</div>



