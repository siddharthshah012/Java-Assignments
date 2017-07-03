<%@include file="include.html" %>
    <%int cardNo = Integer.parseInt(request.getParameter("cardNo"));%>
   
  
   <div>
   <h5> ${message}</h5>
	</div>
	<div class ="jumbotron">
    <a href="showallbranches.jsp?cardNo=${cardNo}">Checkout</a>
    <a href="showallbookloans.jsp?cardNo=${ cardNo} ">Return</a>
    </div>
    
    