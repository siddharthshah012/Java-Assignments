<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>

<%
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
	AdminService service = new AdminService();
	Book book = service.getBookByPK(bookId);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Book Details</h4>
</div>
<form action="editBook" method="post">
	<div class="modal-body">

		Enter Title to be added: 
		<input type="text" name="authorName" value="<%=book.getTitle()%>"><br /> 
		<input type="hidden" name="authorId" value=<%=book.getBookId()%>>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Book</button>
	</div>
</form>