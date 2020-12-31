<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>임시로그인창</title>
</head>
<body>
	<h1>임시로그인창(은혜테스트용)</h1>
	<form action="temp_loginOK" method="post">
		<label for="s_id">ID:</label>
		<input type="text" id="s_id" name="s_id"/>
		<input type="button" value="로그인" onclick="send(this.form)"/>
	</form>
	
<script>
	function send(frm){
		frm.submit();
	}
</script>
</body>
</html>