<%@include file="include.html" %>
<div >
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Please Insert Author Details</h2>
	<form action="addPublisher" method="post">
		Enter Name to be added: <input type="text" name="pubName"><br />
		Enter Address to be added:<input type="text" name="pubAddress"><br/>
		Enter Phone to be added:<input type="text" name="pubPhone"><br/>
		<button type="submit">Add Borrower</button>
	</form>
</div>