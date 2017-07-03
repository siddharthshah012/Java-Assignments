<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@ include file="include.html"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gcit.lms.entity.Book" %>
<%@ page import="com.gcit.lms.service.AdminService" %>
<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div>
	${message};
	<table class="table">
		<tr>
			<th>Branch ID</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>SELECT</th>
		</tr>
		<gcit:forEach items="${branches}" var = "branch" varStatus="loopCounter">
		
		<tr>
			<td>${loopCounter.count}</td>
			<td>${branch.branchName}</td>	
			<td>${branch.branchAddress}</td>
			<td><button type="button"  class="btn btn-sm btn-primary" data-toggle="modal" 
			data-target="#myModal1" href="booksinbranch.jsp?branchId=${branch.branchId }&cardNo=${cardNo}">SELECT!</button></td>	
		</tr>
		</gcit:forEach>
	</table>
	
</div>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		
		</div>
	</div>
</div>

