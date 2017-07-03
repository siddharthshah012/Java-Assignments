<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="include.html"%>
<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>




<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Below are the list of Branches in LMS</h2>
	${message}
	<table class="table">
		<tr>
			<th>Branch ID</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>Edit</th>
			
		</tr>
		<gcit:forEach items="${branches }" var="loop" varStatus="loopCounter">
		<tr>
			<td>${loopCounter.count } </td>
			<td>${branch.branchName }</td>
			<td>${branch.branchAddress }</td>
			 <td><a href="choosebook.jsp?branchId=${branches.branchId }">
   <button class="btn btn-sm btn-primary">Submit</button></a></td>
		</tr>
	</gcit:forEach>
	</table>
</div>


