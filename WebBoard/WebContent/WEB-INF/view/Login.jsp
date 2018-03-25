<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.http.Cookie"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="common.CommonObject"%>

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン画面</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	</head>
	<%	response.setContentType(CommonObject.CONTENT_TYPE);
	//接続時にセッションを取得する
	if (session.getAttribute("LOGIN_INFO") != null) {
		// セッションが既に存在する場合は自動的にログインできる
		response.sendRedirect(CommonObject.BOARD_JSP);
	} else {
		// Cookieの取得
		Cookie cookie[] = request.getCookies();
		String cookieUserId = null;
		String cookiePassWord = null;
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("userId")) {
					cookieUserId = cookie[i].getValue();
				}
				if (cookie[i].getName().equals("passWord")) {
					cookiePassWord = cookie[i].getValue();
				}
			}
		}%>
<body>

<div class="container">

	<div class="wrapper">
	<form id="LOGIN_FORM" action=<%=CommonObject.EXECUTE_LOGIN_SERVLET %> method="POST" name="LOGIN_FORM" class="form-signin">
		<h3 class="form-signin-heading">ようこそ！ログインしましょう！</h3>
			<hr class="colorgraph">
			<input type="text" id="USER_ID" name="USER_ID"
			<%-- Cookieがあった場合はユーザーIDを入力しておく --%>
		<% if (cookieUserId != null) { %> value =<%=cookieUserId%><%} %> class="form-control" placeholder="userid">
			<br>
			<input type="password" id="PASSWORD" name="PASSWORD"
			<%-- Cookieがあった場合はパスワードを入力しておく --%>
		<%if (cookiePassWord != null) {%>value =<%=cookiePassWord %> <%}%>  class="form-control" placeholder="password">
			<br>
			<button class="btn btn-lg btn-primary btn-block"  name="SUBMIT" value="LOGIN" type="SUBMIT">ログイン</button>
	</form>
	<form action=<%=CommonObject.REGISTER_FORM_SERVLET %> method="GET" name="REGISTER_FORM" class="form-signin">
	<button class="btn btn-lg btn-primary btn-block"  name="SUBMIT" value="LOGIN" type="SUBMIT">アカウント作成</button>
	</form>
	<form action=<%=CommonObject.HOME_SERVLET %>>
	<button class="btn btn-lg btn-primary btn-block" type="SUBMIT">戻る</button>
	</form>
		<%-- ログイン失敗時はその旨を通知 --%>
		<%
		if (request.getParameter("LOGIN") != null) {%>
			<p><font color="red"><%=CommonObject.MESSAGE_LOGIN_NG %></font></p>
		<%
		} %>
		<%-- 利用するJavaScriptファイルを外部から読み取り --%>
		<script type="text/javascript" src="http://localhost:8080/WebBoard/js/WebBoardUtils.js"></script>
	<%
	}%>
	</div>
	</div>
</body>
</html>