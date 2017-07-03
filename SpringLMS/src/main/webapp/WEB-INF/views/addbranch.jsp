<%@page import="com.gcit.lms.entity.Library"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Please Insert Author Details</h2>
	<form action="addBranch" method="post">
		Enter Name to be added: <input type="text" name="branchName"><br />
		Enter Address to be added: <input type="text" name="branchAddress"><br />
		
		<button type="submit">Add Branch!</button>
		
		
	</form>
</div>