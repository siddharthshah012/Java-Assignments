<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@include file="include.html"%>
<%@page import="java.util.*"%>
<%@page import="java.util.List"%>


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
		
		<gcit:forEach items="branches" var="br" varStatus="loopCounter">
		<tr>
			<td>${loopCounter.count }</td>
			
			<td>${branch.branchName }</td>
			<td>${branch.branchAddress }</td>
			
			
			<td><button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editBranchModal"
					href='editselectedbranch.jsp?branchId=${branch.branchId }'">Edit!</button></td>
		</tr>
		
		</gcit:forEach>
	</table>
</div>
<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editBranchModal" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// codes works on all bootstrap modal windows in application
		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
</script>

