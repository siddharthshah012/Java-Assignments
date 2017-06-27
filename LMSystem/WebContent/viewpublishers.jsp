<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html" %>

<% 
AdminService adminService = new AdminService();
List<Publisher> publishers = adminService.getAllPublisher(); %>
<div class="jumbotron">

	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin! Please pick an action!</h2>	
	
	
	<table class="table" id="tablepub">
		<tr>
			<th>ID</th>
			<th>Publisher Name</th>
			<th>Publisher Address</th>
			<th>Publisher Phone</th>
			<th>EDIT</th>
			<th>DELETE</th>
		</tr>
		<%for(Publisher p: publishers){ %>
		<tr>
			<td><%=publishers.indexOf(p)+1 %></td>
			<td><%=p.getPublisherName()%></td>
			<td><%=p.getPublisherAddress() %></td>
			<td><%=p.getPublisherPhones() %></td>
			<td><button type="button" class="btn btn-sm btn-success" 
				data-toggle="modal" data-target="#editPubModal"
				href="editpublishers.jsp?pubId=<%=p.getPublisherId()%>">EDIT!</button></td>			
			<td><button type="button" class="btn btn-sm btn-danger" id="pubid"
			onclick="deletePub()" value="<%=p.getPublisherId()%>">Delete!</button></td>	
		</tr>
		<%} %>
	</table>

</div>
<script type="text/javascript">


function deletePub(){
	console.log(1);
	$.ajax({
		url:"deletePublisher",
		//method: "get",
		data:{
			pubId: $('#pubid').val()
		}
	}).done(function (data){
		$('#tablepub').html(data)		
	})
	
}
</script>


<div class="modal fade bs-example-modal-lg" tabindex="-1"
	id="editPubModal" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">

		</div>
	</div>
</div>



