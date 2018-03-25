package webBoard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businessLogic.WebBoardLogic;
import common.CommonObject;
import dto.UserInfoDto;

/**
 * Servlet implementation class Servlet12
 */
public class ExecuteLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExecuteLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 基本的に利用しない
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType(CommonObject.CONTENT_TYPE);
		request.setCharacterEncoding(CommonObject.CHAR_CODE); // HTTP通信で引数を受け取るときの文字コードの指定
		PrintWriter out = response.getWriter();

		String userId = request.getParameter("USER_ID");
		String passWord = request.getParameter("PASSWORD");


		// ログイン情報の取得
		WebBoardLogic logic = new WebBoardLogic();
		UserInfoDto dto = logic.executeSelectUserInfo(userId, passWord, out);

		if (dto.getUserId() != null) {
			// ログインできている場合はセッションを作成してログイン完了画面へ
			HttpSession session = request.getSession();
			session.setAttribute("LOGIN_INFO", dto);

			// ログインに成功したユーザーをcookieに登録する
			Cookie cookieUserId = new Cookie("userId", userId);
			Cookie cookiePassWord = new Cookie("passWord", passWord);
			response.addCookie(cookieUserId);
			response.addCookie(cookiePassWord);

			// ログインできた場合は掲示板画面へ
			RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.THREAD_SERVLET);
			dispatch.forward(request, response);
			//response.sendRedirect(CommonObject.THREAD_JSP);
		} else {
			// ログインできていない場合はログイン画面へ戻る
			RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.LOGIN_JSP + "?LOGIN=NG");
			dispatch.forward(request, response);
			//response.sendRedirect(CommonObject.LOGIN_JSP + "?LOGIN=NG");
		}
	}

}
