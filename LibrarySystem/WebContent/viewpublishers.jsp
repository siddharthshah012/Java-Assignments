<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html" %>

<% 
	AdminService adminService = new AdminService();
	List<Publisher> publishers = new ArrayList<>();
	Integer pubCount =adminService.getPublishersCount();
	
	int pages = 0;
	if (pubCount % 10 > 0) {
		pages = pubCount / 10 + 1;
	} else {
		pages = pubCount / 10;
	}
	if (request.getAttribute("publishers") != null) {
		publishers = (List<Publisher>) request.getAttribute("publishers");
	} else {
		publishers = adminService.getAllPublishers(1, null);
	}


%>
<script>
	function searchPublishers(){
		$.ajax({
			url: "searchPublishers",
			data:{
				searchString: $('#searchStringP').val()
			}
		}).done(function (data){
			$('#publishersTable').html(data)
		})
	}
</script>

<div class="jumbotron">

	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin! Please pick an action!</h2>
	
	<a href="welcome.jsp">Welcome page</a></br>
	<a href="book.jsp">GO back</a></br>
	${message}
	
	<div>
			<input type="text" name="searchStringP"  id="searchStringP"  placeholder="Publisher Name" aria-describedby="basic-addon1" oninput="searchPublishers()">
	</div>
	
	<nav aria-label="Page navigation">
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<%
				for (int i = 1; i <= pages; i++) {
			%>
			<li><a href="pagePublishers?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
		
	
	
	<table class="table" id="publishersTable">
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
			<td><button type="button" class="btn btn-sm btn-danger" id="publisherId"
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
			publisherId: $('#publisherId').val()
		}
	}).done(function (data){
		$('#publishersTable').html(data)		
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



