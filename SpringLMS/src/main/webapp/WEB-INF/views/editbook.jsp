<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>



<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">EDIT BOOK DETAILS</h4>
</div>
<form action="editBook" method="post">
	<div class="modal-body">

		Enter Title to be added: 
		<input type="text" name="title" value="${book.title }"><br /> 
		<input type="hidden" name="bookId" value=${book.bookId }>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Book</button>
	</div>
</form>