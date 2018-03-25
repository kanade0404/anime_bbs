package webBoard;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.CommonObject;

public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType(CommonObject.CONTENT_TYPE);
		HttpSession session = request.getSession(); // セッション情報の取得

		if (session.getAttribute("LOGIN_INFO") != null) {
			// セッションがある場合はログアウトに伴いセッション情報を破棄
			session.invalidate();

			// キャッシュのクリア
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			Date today = new Date();
			response.setDateHeader("Last-Modified", today.getTime());

		}

		// ログイン画面に戻る

		RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.LOGIN_JSP);
		dispatch.forward(request, response);
		//response.sendRedirect(CommonObject.LOGIN_JSP);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
