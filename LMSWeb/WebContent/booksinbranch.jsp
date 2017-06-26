<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gcit.lms.entity.Library" %>
<%@ page import="com.gcit.lms.entity.*" %>
<%@include file="include.html" %>

<%@page import="com.gcit.lms.web.*" %>
<% Integer branchId = Integer.parseInt(request.getParameter("branchId")); %>
<% Integer cardNo=Integer.parseInt(request.getParameter("cardNo")); %>
<% BorrowerService bservice = new BorrowerService();
	
	List<Book> books = bservice.readAllBookswithBranch(branchId);
%>
<div>
	${message}
<table class="table">
	<tr>
			<th>Book ID</th>
			<th>Book Title</th>
			<th>Author Name</th>
			<th>Edit</th>
		</tr>
		
		<%for (Book b: books){ %>
		<tr>
		
		<td><%= (books.indexOf(b)+1) %></td>
		<td><%= b.getTitle() %></td>
		<td><%for (Author a: b.getAuthors()){
			out.print(a.getAuthorName()+" ");		
		}%> </td>
		<td><form action="checkout" method="get"><input type="hidden" name = "bookId" value=<%=b.getBookId()%>>
			<input type="hidden" name = "branchId" value=<%=branchId%>>
			<input type="hidden" name = "cardNo" value=<%=cardNo %>>
			
			<input type="submit"  class="btn btn-sm btn-primary">SELECT!</form></td>
			
		<%} %>
		</tr>
		
	</table>
</div>



