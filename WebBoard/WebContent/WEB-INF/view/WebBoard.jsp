<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="common.CommonObject"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="businessLogic.WebBoardLogic"%>
<%@ page import="dto.WebBoardDto"%>
<%@ page import="dto.UserInfoDto"%>
<%@ page import="dto.ThreadDto"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	response.setContentType(CommonObject.CONTENT_TYPE);
	PrintWriter writer = response.getWriter();
	//セッションの取得
	if (session.getAttribute("LOGIN_INFO") == null) {
		response.sendRedirect(CommonObject.LOGIN_JSP);
	} else {
		UserInfoDto loginInfo = (UserInfoDto) session.getAttribute("LOGIN_INFO");
		String userId = loginInfo.getUserId();
		String userName = loginInfo.getUserName();
		int administratorFlg = loginInfo.getAdministratorFlg();
		WebBoardLogic logic = new WebBoardLogic();
		ThreadDto threadInfo = (ThreadDto)session.getAttribute("THREAD_SEARCH");
		if(threadInfo == null){
			response.sendRedirect(CommonObject.HOME_SERVLET);
		}else{
			String threadId = threadInfo.getThreadId();
			String threadName = threadInfo.getThreadName();
%>
<title><%=threadName %></title>
	<link href=<%=CommonObject.BOARD_CSS %> rel="stylesheet">
	<script src="http://yourdomian/jquery.js" type="text/javascript"></script>
	<script src="http://yourdomian/jquery.MultiFile.js" type="text/javascript"></script>
</head>
<body>
<img class="logo" src="http://raki.st/Hr0HHW/img1">
<%--投稿フォームの生成 --%>
<form action="<%=CommonObject.EXECUTE_INSERT_SERVLET %>" method="POST" id="POST_FORM" enctype="multipart/form-data" onsubmit="return checkBrank(id, 'MESSAGE', '投稿内容')">
<h2><%=threadName %></h2>
<h3><%=userName %>さんようこそ！</h3>
<textarea name="MESSAGE" rows="5" cols="50"></textarea>
<input type="hidden" name="USER_ID" value="<%=userId %>">
<input type="hidden" name="REPLY_ID" value="0">
<input type="hidden" name="THREAD_ID" value="<%=threadId %>">
<input type="hidden" name="POST_MODE" value="POST">
<br>
<input class="multi" type="file" name="FILENAME" accept="image/*" size="50" maxlength="2"  multiple="multiple" >
<br>
<input class="button" type="submit" id="POST_BTN" value="投稿">
</form>
<br>
			<%
			ArrayList<WebBoardDto> list = new ArrayList<WebBoardDto>();
			session = request.getSession();
			// 投稿内容を取得してArrayListに格納
			list = (ArrayList<WebBoardDto>) session.getAttribute("BOARD_LIST");
			//詳細検索の条件を取得
			String searchName = request.getParameter("SEARCH_NAME");
			String searchMessage = request.getParameter("SEARCH_MESSAGE");
			//※投稿時間実装用※
			String startTime = request.getParameter("SEARCH_START_TIME");
			String lastTime = request.getParameter("SEARCH_LAST_TIME");
			if (searchName == null){
				searchName = CommonObject.BRANK;
			}
			if (searchMessage == null){
				searchMessage = CommonObject.BRANK;
			}
			if(startTime == null){
				startTime = CommonObject.BRANK;
			}
			if(lastTime == null){
				lastTime = CommonObject.BRANK;
			}
			%>
<%-- 以下検索フォーム --%>
	<form action=<%=CommonObject.BOARD_SERVLET %> method="get">
		<b>検索</b>
		<p>名前：
			<input type="hidden" name="SEARCH" value="ON">
			<input type="text" id="ID_SEARCH_NAME" name="SEARCH_NAME" size="10">
			投稿内容：
			<input type="text" id="ID_SEARCH_MESSAGE" name="SEARCH_MESSAGE" size="20">
			<input type="hidden" name="THREAD_ID" value=<%=threadId %>>
			投稿時間（時間と分も入力してください）：
			<input type="datetime-local" name= "SEARCH_START_TIME" >
			～
			<input type="datetime-local"  name = "SEARCH_LAST_TIME" >
			<input type="submit" value=" 検索">
		</p>
	</form>
<%-- 以上検索フォーム --%>
<HR width = "600" align = "left">
			<%
			//投稿一覧を表示する
			for (int i = 0; i < list.size(); i++) {
				WebBoardDto dto = list.get(i);
				String titleId = dto.getThreadId();
				boolean isPostUser = userId.equals(dto.getUserId()); // 投稿者か否か
				int postId = dto.getPostId();
				int replyId = dto.getReplyId();
				String message = dto.getMessages().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
						.replace("\"", "&quot;").replace("'", "&#039;");
				String image1 = dto.getPostImg1();
				String image2 = dto.getPostImg2();
				// IDの生成
				String formIdDelete = "DEL_FORM" + String.valueOf(dto.getPostId());
				String formIdUpdate = "UPD_FROM" + String.valueOf(dto.getPostId());
				String formIdDefault = "DFT_FORM" + String.valueOf(dto.getPostId());
				String formIdReply = "RPL_FORM" + String.valueOf(dto.getPostId());
				%>
				<%-- ▼親の投稿 --%>
				<%
				if(replyId == 0){
				%>
				<div class="box29">
					<div class="box-title">
						<p><%=dto.getUserName()%>	<%=dto.getPostTime()%></p>
						<%-- ▲投稿属性の生成 --%>	<%-- 投稿時間 --%>
					</div>
					<%
					// 管理者アカウントを除き、他ユーザーのデータは削除・編集できない
					// 削除用、編集用フォームを予め用意しておき、JavaScriptを使って表示を切り替えています
					if (isPostUser || (administratorFlg == 1)) {
						%>
						<%-- 削除用フォーム --%>
						<form method="POST" id="<%=formIdDelete %>" enctype="multipart/form-data" action="<%=CommonObject.EXECUTE_EDIT_SERVLET %>">
						<p><%=message %></p>
						<br>
						<%
						if(!image1.equals("") && !image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}else if(!image1.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
						<%
						}else if(!image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}
						%>
						<br>
						<input class="button" type="button" value="編集" onclick="changeEditMode('<%=formIdUpdate%>', '<%=formIdDelete%>')">
						<input class="button" type="submit" value="削除">
						<input type="hidden" name="THREAD_ID" value=<%=threadId %>>
						<input type="hidden" name="POST_ID" value="<%=postId %>">
						<input type="hidden" name="REPLY_ID" value="<%=replyId %>">
						<input type="hidden" name="POST_MODE" value="DELETE">
						</form>
						<%-- 編集用フォーム（画面表示時は非表示にする） --%>
						<form method="post" id="<%=formIdUpdate%>" action="<%=CommonObject.EXECUTE_EDIT_SERVLET %>" enctype="multipart/form-data" style="display: none;">
						<textarea id="MESSAGE" name="MESSAGE" rows=5 cols=50><%=message %></textarea>
						<br>
						<br>
						<%
						if(!image1.equals("") && !image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}else if(!image1.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
						<%
						}else if(!image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}
						%>
						<input class="multi" type="file" name="FILENAME" accept="image/*" size="50" maxlength="2"  multiple="multiple" >
						<br>
						<input class="button" type="submit" value="保存" onclick="return checkEdited('<%=formIdUpdate%>')">
						<input class="button" type="button" value="戻る" onclick="changeEditMode('<%=formIdDelete%>', '<%=formIdUpdate%>')">
						<input type="hidden" name="THREAD_ID" value="<%=threadId %>">
						<input type="hidden" name="POST_ID" value="<%=postId %>">
						<input type="hidden" name="REPLY_ID" value="<%=replyId %>">
						<input type="hidden" name="POST_MODE" value="UPDATE">
						<input type="hidden" id="OLD_VALUE" value="<%=message %>">
						</form>
					<%
					}else{
					%>
						<p><%=message %></p>
						<br>
						<%
						if(!image1.equals("") && !image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}else if(!image1.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
						<%
						}else if(!image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}
						%>
						<br>
						<form method="POST" id="<%=formIdDefault %>" action="<%=CommonObject.BOARD_SERVLET %>">
						<input class="button" type="button" type="submit" value="返信" onclick="return changeEditMode('<%=formIdReply %>', '<%=formIdDefault %>')">
						</form>

						<%-- 返信用フォーム（画面表示は非表示にする） --%>
						<form method="POST" id="<%=formIdReply %>" action="<%=CommonObject.EXECUTE_INSERT_SERVLET %>" enctype="multipart/form-data"  style="display: none;">
						<textarea name="MESSAGE" rows=5 cols=50><%=message %></textarea>
						<br>
						<input class="multi" type="file" name="FILENAME" accept="image/*" size="50" maxlength="2"  multiple="multiple" >
						<br>
						<input class="button" type="submit" value="送信" onclick="return checkEdited('<%=formIdReply%>')">
						<input class="button" type="button" value="戻る" onclick="changeEditMode('<%=formIdDefault%>', '<%=formIdReply%>')">
						<input type="hidden" name="USER_ID" value="<%=userId %>">
						<input type="hidden" name="THREAD_ID" value="<%=threadId %>">
						<input type="hidden" name="POST_ID" value="<%=postId %>">
						<input type="hidden"  name="POST_MODE" value="REPLY">
						</form>
							<%-- ▲親の投稿 --%>
					<%
					}
					%>
				</div>
				<%
				}else{
				%>
					<%-- ▼子の投稿 --%>
					<dd>
					<div class="box29" >
						<div class="box-title">
							<p><%=dto.getUserName()%>	<%=dto.getPostTime()%></p>
							<%-- ▲投稿属性の生成 --%>	<%-- 投稿時間 --%>
						</div>
					<%
					// 管理者アカウントを除き、他ユーザーのデータは削除・編集できない
					// 削除用、編集用フォームを予め用意しておき、JavaScriptを使って表示を切り替えています
					if (isPostUser || (administratorFlg == 1)) {
					%>
						<%-- 削除用フォーム --%>
						<form method="POST" id="<%=formIdDelete %>" enctype="multipart/form-data" action="<%=CommonObject.EXECUTE_EDIT_SERVLET %>">
						<p><%=message %></p>
						<br>
						<%
						if(!image1.equals("") && !image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}else if(!image1.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
						<%
						}else if(!image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}
						%>
						<br>
						<input class="button" type="button" value="編集" onclick="changeEditMode('<%=formIdUpdate%>', '<%=formIdDelete%>')">
						<input class="button" type="submit" value="削除">
						<input type="hidden" name="THREAD_ID" value=<%=threadId %>>
						<input type="hidden" name="POST_ID" value="<%=postId %>">
						<input type="hidden" name="REPLY_ID" value="<%=replyId %>">
						<input type="hidden" name="POST_MODE" value="DELETE">
						</form>
						<%-- 編集用フォーム（画面表示時は非表示にする） --%>
						<form method="post" id="<%=formIdUpdate%>" action="<%=CommonObject.EXECUTE_EDIT_SERVLET %>" enctype="multipart/form-data" style="display: none;">
						<textarea id="MESSAGE" name="MESSAGE" rows=5 cols=50><%=message %></textarea>
						<br>
						<%
						if(!image1.equals("") && !image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}else if(!image1.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
						<%
						}else if(!image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}
						%>
						<br>
						<input class="multi" type="file" name="FILENAME" accept="image/*" size="50" maxlength="2"  multiple="multiple" >
						<br>
						<input class="button" type="submit" value="保存" onclick="return checkEdited('<%=formIdUpdate%>')">
						<input class="button" type="button" value="戻る" onclick="changeEditMode('<%=formIdDelete%>', '<%=formIdUpdate%>')">
						<input type="hidden" name="THREAD_ID" value="<%=threadId %>">
						<input type="hidden" name="POST_ID" value="<%=postId %>">
						<input type="hidden" name="REPLY_ID" value="<%=replyId %>">
						<input type="hidden" name="POST_MODE" value="UPDATE">
						<input type="hidden" id="OLD_VALUE" value="<%=message %>">
						</form>
					<%
					}else{
					%>
						<p><%=message %></p>
						<%
						if(!image1.equals("") && !image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}else if(!image1.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image1 %>">
						<%
						}else if(!image2.equals("")){
						%>
							<img class="cover" src="<%=CommonObject.UPLOAD_PATH %><%=image2 %>">
						<%
						}
					}
					%>
					</div>
					</dd>
				<%
				}
			}
			//管理者ユーザーの場合は全削除機能を利用できるようにする
			if (administratorFlg == 1) {
			%>
			<form method="GET" action="<%=CommonObject.EXECUTE_ALL_DELETE_SERVLET %>">
			<input type="submit" value="全ての投稿内容を完全削除">
			</form>
			<%
			}
			%>
			<br>
			<a href=<%=CommonObject.THREAD_SERVLET %> class="btn">
				<i class="fa fa-caret-right"></i> 戻る
			</a>
			<br>
			<%--利用するJavaScriptファイルを外部から読み取り--%>
			<script type="text/javascript" src="http://localhost:8080/WebBoard/js/WebBoardUtils.js"></script>
	<%
		}
	}
	%>
</body>
</html>