<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@include file="include.html" %>


	
    <%int cardNo = Integer.parseInt(request.getParameter("cardNo"));%>
    <div>
    ${message}
	</div>
	
	<h5>SELECT THE APPRORIATE OPTION TO PROCEED</h5>
	
    <a href="showallbranches.jsp?cardNo=<%= cardNo %>">Checkout</a>
    
    <a href="showallbookloans.jsp?cardNo=<%= cardNo %>">Return</a>
    