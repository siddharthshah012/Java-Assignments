<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>





<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
			</button>
				<h4 class="modal-title" id="myModalLabel">Edit Author Details</h4>
</div>
			
<form action="editPublisher" method="post">
	<div class="modal-body">
		Enter Publisher Details to be added: 
		<input type="text" name="publisherName" value="${publisher.publisherName }"><br/>
		<input type="text" name="publisherAddress" value="${publisher.publisherAddress }"><br/>
		<input type="text" name="publisherPhone" value="${publisher.publisherPhone }"><br/> 
		<input type="hidden" name="publisherId" value=${publisher.publisherId }>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Edit Publisher</button>
	</div>
</form>