<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src='js/jquery-1.8.2.js'></script>
<title>Insert title here</title>
</head>
	<body style="padding:40px;">
		<h1>Results</h1>
		<button id="alertbutton" type="button">Alert</button>
		<ul>
		
		</ul>
		<script>
		
		var x = <% 
			out.println((String) request.getAttribute("attr1"));
		%>;
		
		$('#alertbutton').click(function(){
			alert(JSON.stringify(x));
		});
		</script>
	</body>
</html>