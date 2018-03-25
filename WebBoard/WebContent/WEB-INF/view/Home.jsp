<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.CommonObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>あに☆まち</title>
    <link rel="stylesheet" href=<%=common.CommonObject.HOME_CSS %>>
  </head>
  <body>
    <img class="logo" src="http://raki.st/Hr0HHW/img1">
        <h1>あの街に行ってみよう。		LET'S GO TO A CITY</h1>
        <p>あに☆まちは"聖地"とその街に関わる方をつなぐプラットフォームです。</p>
        <p>あなたの好きなアニメのゆかりの地に行ってみませんか？</p>
        <div class="btn-wrapper">
        	<a href=<%=CommonObject.LOGIN_SERVLET %> class="btn">ログイン</a>
        	<a href=<%=CommonObject.REGISTER_FORM_SERVLET %> class="btn">新規登録</a>
        </div>


        <p>多くのアニメでは実際の街や風景を元に作画が行われています</p>
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>taritari2.jpg">
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>taritari1.png">
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>nonnonschool.jpg">
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>realnonnonschool.jpg">
        <p>その場所に行って想いを馳せたり、デザインの精巧さやアレンジポイントを見つけるのもいいです</p>
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>realTensyudo.jpg">
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>tensyudo.jpg">
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>kiminonahaShinjuku.jpg">
        <img class="image" src="<%=common.CommonObject.IMAGE_PATH %>stepBridge.jpg">
          <h2>多くの聖地があなたを待っています</h2>
          <h3>あなたもあに☆まちで体験を投稿しませんか？</h3>
        <a href=<%=CommonObject.REGISTER_FORM_SERVLET %> class="square_btn">新規登録</a>
  </body>
</html>