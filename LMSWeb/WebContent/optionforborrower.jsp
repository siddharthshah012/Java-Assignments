<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="include.html" %>
    <%int cardNo = Integer.parseInt(request.getParameter("cardNo"));%>
    

    <a href="showallbranches.jsp?cardNo=<%= cardNo %>">Checkout</a>
    <a href="showallloans.jsp?cardNo=<%= cardNo %>">Return</a>
    