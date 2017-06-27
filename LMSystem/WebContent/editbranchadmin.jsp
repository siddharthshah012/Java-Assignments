<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.entity.Library"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	Integer branchId = Integer.parseInt(request.getParameter("libBranchId"));
	//out.println(publisherId);
	AdminService service = new AdminService();
	Library library = service.getBranchByID(branchId);
	//out.println(publisher.getPublisherId());
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
			</button>
				<h4 class="modal-title" id="myModalLabel">Edit Author Details</h4>
</div>
			
<form action="editBranch" method="post">
	<div class="modal-body">
		Enter Branch Details to be added: 
		<input type="text" name="branchName" value="<%=library.getBranchName()%>"><br/>
		<input type="text" name="branchAddress" value="<%=library.getBranchAddress()%>"><br/>
		<input type="hidden" name="branchId" value=<%=library.getBranchId()%>>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Branch</button>
	</div>
</form>