package webBoard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogic.WebBoardLogic;
import common.CommonObject;

/**
 * Servlet implementation class WebBoardAllDelete
 */
public class ExecuteAllDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExecuteAllDelete() {
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

		request.setCharacterEncoding(CommonObject.CHAR_CODE);
		response.setContentType(CommonObject.CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		WebBoardLogic logic = new WebBoardLogic();
		logic.executeAllDelete(out);

		RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.BOARD_SERVLET);
		dispatch.forward(request, response);
		//response.sendRedirect(CommonObject.BOARD_JSP);
	}

}
