<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="include.html" %>


<%
  String branchName =(String)request.getAttribute("branchName");
  String title = (String)request.getAttribute("title");
  int oldcopies = (Integer)request.getAttribute("oldcopies");
  int newcopies = (Integer)request.getAttribute("newcopies");
 %>
<table class=table>
	<tr>
		<th>Branch ID</th>
		<th>Title</th>
		<th>Old Copies</th>
		<th>New Copies</th>
	</tr>
	<tr>
		<td><%=branchName%></td>
		<td><%=title%></td>
		<td><%=oldcopies%></td>
		<td><%=newcopies%></td>
	</tr>
</table>