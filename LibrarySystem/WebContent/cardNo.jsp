<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="include.html" %>
<div class ="jumbotron">

<!-- <form action="checkCardNo" method="post"> -->

	PLEASE ENTER YOUR CARD NO:
	<input id="cardNumber" type="text" name="cardNo">
	<button class="btn btn-lg btn-info" type="submit" onclick="checkcard()"> Enter </button>
		<div id="checkcard">
		
		<p>${message}</p>
		</div>

	<!-- </form> --> 
<script type="text/javascript">

function checkcard(){
	$.ajax({
		url: "checkCardNo",
		method: "post",
		data:{
			cardNo: $('#cardNumber').val()
		}
	}).done(function (data){
		$('#checkcard').html(data)
	})
}

</script>
	
</div>
    