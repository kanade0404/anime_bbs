package webBoard;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businessLogic.WebBoardLogic;
import common.CommonObject;
import dto.SearchConditionDto;
import dto.ThreadDto;
import dto.WebBoardDto;

public class DispWebBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DispWebBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType(CommonObject.CONTENT_TYPE);

		//スレッドIDをthreadIdに代入
		String threadId = request.getParameter("THREAD_ID");
		String threadName = null;
		//threadIdでthreadNameを特定
		WebBoardLogic logic = new WebBoardLogic();
		ThreadDto dto = logic.searchThreadName(threadId, threadName);
		//スレッド情報をセッションに入れる
		HttpSession session = request.getSession();
		session.setAttribute("THREAD_SEARCH", dto);



		//検索条件をセット
		SearchConditionDto searchCondition = new SearchConditionDto();
		if (request.getParameter("SEARCH") != null) {
			String searchName = request.getParameter("SEARCH_NAME");
			String searchMessage = request.getParameter("SEARCH_MESSAGE");
			String startTime = request.getParameter("SEARCH_START_TIME").replace("T", " ");
			String lastTime = request.getParameter("SEARCH_LAST_TIME").replace("T", " ");
			Timestamp searchStartTime;
			Timestamp searchLastTime;
			try {
				//Stringを日時→Timestampと型変換をする
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			    if (searchName != CommonObject.BRANK) {
					searchCondition.setSearchName(searchName);
				}
				if (searchMessage != CommonObject.BRANK){
					searchCondition.setSearchMessage(searchMessage);
				}
				if(startTime != CommonObject.BRANK){
					Date parsedStartTime = dateFormat.parse(startTime);
					searchStartTime = new Timestamp(parsedStartTime.getTime());
					searchCondition.setSearchStartTime(searchStartTime);
				}
				if(lastTime != CommonObject.BRANK){
					Date parsedLastTime = dateFormat.parse(lastTime);
					searchLastTime = new Timestamp(parsedLastTime.getTime());
					searchCondition.setSearchLastTime(searchLastTime);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		//投稿リストを取得する
		ArrayList<WebBoardDto> webBoardList = new ArrayList<WebBoardDto>();
		webBoardList = logic.getWebBoardList(searchCondition, threadId);

		//セッションに投稿リストを格納する
		session.setAttribute("BOARD_LIST", webBoardList);
		//画面を描画
		RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.BOARD_JSP);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}