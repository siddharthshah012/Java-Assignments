<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Timestamp"%>

<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>

<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
	Date parsedDate = df.parse((request.getParameter("dateOut")));
	java.sql.Timestamp timestamp = new Timestamp(parsedDate.getTime());
	/*Date parseDate = df.parse(timestamp.toString());*/
	AdminService adminservice = new AdminService();
	/*String timestampasstring =parseDate.toString();*/
	out.println(timestamp);
	
	//BookLoans booksl =adminservice.getallBookloansDateout(t);

%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
			</button>
				<h4 class="modal-title" id="myModalLabel">DUE DATE</h4>
</div>
			
<form action="overrideDuedate" method="post">
	<div class="modal-body">
		ENTER NUMBER OF DAYS YOU WANT TO EXTEND BOOK BY
		<input type="text" name="extendDays">
		<input type="text" name="dateOut" value='<%=timestamp%>'>
		
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">OVERRIDE DATE</button>
	</div>
</form>