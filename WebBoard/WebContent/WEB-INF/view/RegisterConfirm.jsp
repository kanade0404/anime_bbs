<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.CommonObject"%>
<%@ page import="dto.UserInfoDto"%>
<%
    UserInfoDto dto = (UserInfoDto) session.getAttribute("RegisterUser");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<title>登録情報確認</title>
</head>
<body>
    <p>下記のユーザーを登録します</p>
    <p>
        ログインID:<%=dto.getUserId()%><br> 名前:<%=dto.getUserName()%><br>
    </p>
    <form action=<%=CommonObject.REGISTER_USER_SERVLET %> method="GET" class="form-signin">
	<button class="btn btn-lg btn-primary btn-block"  name="action" value="done" type="SUBMIT">登録</button>
	</form>
	<form action=<%=CommonObject.LOGIN_SERVLET %>>
	<button class="btn btn-lg btn-primary btn-block" type="SUBMIT">戻る</button>
	</form>
</body>
</html>