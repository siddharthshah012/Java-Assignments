<%@include file="include.html"%>
<%@page import="java.util.*"%>
<%
    Integer branchId = Integer.parseInt(request.getParameter("branchId"));
	Integer bookId = Integer.parseInt(request.getParameter("bookId"));
%>

      <form action="addCopies" method="post">       
		Enter new number of copies: <input type="text" name="numbCopies">
			<input type="hidden" name="bookId" value=<%=bookId %>>
			<input type="hidden" name="branchId" value=<%=branchId%>>
        <button type="submit" class="btn btn-primary">copies</button>
      </form>
