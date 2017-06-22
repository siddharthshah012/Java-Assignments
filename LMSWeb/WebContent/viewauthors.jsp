<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>

<%
AdminService adminService = new AdminService();
List<Author> authors = adminService.getAllAuthors();%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>LMS</title>

</head>
<body>
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Below are the list of Authors in LMS</h2>
	${message}
	<table>
		<tr>
			<th>Author ID</th>
			<th>Author Name</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%for(Author a: authors){ %>
		<tr>
			<td><%=authors.indexOf(a)+1 %></td>
			<td><%=a.getAuthorName() %></td>	
			<td><button type="button">Edit!</button></td>
			<td><button type="button" onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>'">Delete!</button></td>	
		</tr>
		<%} %>
	</table>
</body>
</html>