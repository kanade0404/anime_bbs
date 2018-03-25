<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.CommonObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<title>ユーザー登録</title>
</head>
<body>
    <div class = "container">
<div class="wrapper" >
			<form action=<%=CommonObject.REGISTER_USER_SERVLET %> method="POST" class="form-signin" style = background-color:rgba(0,0,0,0.0);>
			<h3 class="form-signin-heading" style = color:white;>welcome! please sigh in</h3>
<hr class="colorgraph">
			<p>ログインID：
			<br>
			<input type="text" id="USER_ID" name="USER_ID" maxlength="20" class="form-control" placeholder="userid">
			</p>
			<p>パスワード：
			<br>
			<input type="password" id="PASSWORD" name="PASSWORD" maxlength="20" class="form-control" placeholder="password">
			</p>
			<p>名前:
			<br>
			<input type="text" id="USER_NAME" name="USER_NAME" maxlength="20" class="form-control" placeholder="username">
			</p>
			<br>
			<button class="btn btn-lg btn-primary btn-block"  name="SUBMIT" type="SUBMIT">確認</button>
			</form>
			<form action=<%=CommonObject.HOME_SERVLET %> method="get">
			<button class="btn btn-lg btn-primary btn-block"  name="SUBMIT" type="SUBMIT">戻る</button>
			</form>
		 </div>
		</div>
</body>
</html>