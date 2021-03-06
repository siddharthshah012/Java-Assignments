<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>


<%
	Integer publisherId = Integer.parseInt(request.getParameter("pubId"));
	out.println(publisherId);
	AdminService service = new AdminService();
	Publisher publisher = service.getPublisherById(publisherId);
	out.println(publisher.getPublisherId());
%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
			</button>
				<h4 class="modal-title" id="myModalLabel">Edit Author Details</h4>
</div>
			
<form action="editPublisher" method="post">
	<div class="modal-body">
		Enter Publisher Details to be added: 
		<input type="text" name="publisherName" value="<%=publisher.getPublisherName()%>"><br/>
		<input type="text" name="publisherAddress" value="<%=publisher.getPublisherAddress()%>"><br/>
		<input type="text" name="publisherPhone" value="<%=publisher.getPublisherPhones()%>"><br/> 
		<input type="hidden" name="publisherId" value=<%=publisher.getPublisherId()%>>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Publisher</button>
	</div>
</form>