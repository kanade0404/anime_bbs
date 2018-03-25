<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.CommonObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href=<%=common.CommonObject.THRED_CSS %> rel="stylesheet">
<title>タイトル一覧</title>
</head>
<body>
<img class="logo" src="http://raki.st/Hr0HHW/img1">
<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=ACCEL_WORLD">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>accelWorld.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=ACCEL_WORLD">アクセル・ワールド</a>
		</h2>
		<p>東京都杉並区</p>
			<div class="animecopyright">©2015 川原 礫／KADOKAWA アスキー・メディアワークス刊／AWIB Project</div>
	</div>
</div>

<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=OREIMO">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>oreimo.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=OREIMO">俺の妹がこんなに可愛いわけがない</a>
		</h2>
		<p>千葉県千葉市</p>
			<div class="animecopyright">©伏見つかさ／アスキー・メディアワークス／OIP2</div>
	</div>
</div>

<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=KIMINONAHA">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>kiminonaha.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=KIMINONAHA">君の名は。</a>
		</h2>
		<p>東京都新宿区	岐阜県飛騨市</p>
			<div class="animecopyright">©2016「君の名は。」製作委員会</div>
	</div>
</div>


<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=KOKOSAKE">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>kokosake.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=KOKOSAKE">心が叫びたがっているんだ。</a>
		</h2>
		<p>埼玉県秩父市</p>
			<div class="animecopyright">©KOKOSAKE PROJECT</div>
	</div>
</div>

<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=SHIROBAKO">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>shirobako_large.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=SHIROBAKO">SHIROBAKO</a>
		</h2>
		<p>東京都武蔵野市</p>
			<div class="animecopyright">©「SHIROBAKO」製作委員会</div>
	</div>
</div>

<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=EVA">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>eva.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=EVA">『ヱヴァンゲリヲン』シリーズ</a>
		</h2>
		<p>神奈川県箱根市</p>
			<div class="animecopyright">©カラー</div>
	</div>
</div>

<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=TRUE_TEARS">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>truetears_title.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=TRUE_TEARS">true tears</a>
		</h2>
		<p>富山県南砺市</p>
			<div class="animecopyright">©2008 true tears製作委員会</div>
	</div>
</div>

<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=RAKISUTA">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>LuckyStar.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=RAKISUTA">らき☆すた</a>
		</h2>
		<p>埼玉県久喜市</p>
			<div class="animecopyright">©美水かがみ／らっきー☆ぱらだいす</div>
	</div>
</div>


<div class="box15">
    <div>
		<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=LOVELIVE">
		<img class="cover" src="<%=common.CommonObject.IMAGE_PATH %>lovelive.jpg">
		</a>
	</div>
	<div>
		<h2 class="title">
			<a href="<%=CommonObject.BOARD_SERVLET %>?THREAD_ID=LOVELIVE">ラブライブ！</a>
		</h2>
		<p>東京都千代田区</p>
			<div class="animecopyright">©2013 プロジェクトラブライブ！</div>
	</div>
</div>

<a href="<%=CommonObject.LOGOUT_SERVLET %>?SUBMIT=LOGOUT" class="btn">LOGOUT</a>

</body>
</html>