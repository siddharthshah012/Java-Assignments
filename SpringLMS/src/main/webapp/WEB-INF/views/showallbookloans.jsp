<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%@include file="include.html" %>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookLoans" %>
<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div>
		List of all the books loaned by card Number:
		<table class="table">
			<tr>
				<th>ID</th>
				<th>Book Title</th>
				<th>Branch Name</th>
				<th>Branch Address</th>
				<th>Due date</th>
				<th>SELECT!!!!</th>
			</tr>
			<gcit:forEach items="${bookloans }" var="bl" varStatus="loopCounter">
				<tr>
					<td>${loopCounter.count}</td>
					<td>${bl.book.title}</td>
					<td>${bl.branch.branchName}</td>
					<td>${bl.branch.branchAddress}</td>
					<td>${bl.dueDate }</td>
					<td><form method="post" action="returnBook">
						<input type="hidden" name="dateOut" value='${bl.dateOut} }'>
						<input type="hidden" name="bookId" value=${bl.book.bookId }>
						<input type="hidden" name="branchId" value=${bl.branch.branchId }>
						<button type="submit" >RETURN BOOK</button>
					
					</form> </td>				
				</tr>
			</gcit:forEach>
		</table>
</div>