package webBoard;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businessLogic.WebBoardLogic;
import common.CommonObject;
import dto.UserInfoDto;


public class RegisterUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // フォワード先
        String forwardPath = null;

        // サーブレットクラスの動作を決定する「action」の値を
        // リクエストパラメーターから取得(P142)
        String action = request.getParameter("action");

        // 「登録の開始」をリクエストされた時の処理
        if (action == null) {
            // フォワード先を指定
            forwardPath = CommonObject.REGISTER_FORM_JSP;
        }

        // 登録確認画面から「登録実行」をリクエストされた時の処理
        else if (action.equals("done")) {
            // セッションスコープに保存された登録ユーザーを取得
            HttpSession session = request.getSession();
            UserInfoDto dto = (UserInfoDto) session.getAttribute("RegisterUser");

            // 登録処理の呼び出し
            WebBoardLogic logic = new WebBoardLogic();
            // 登録情報の保存
            boolean succesInsert = logic.executeCreateAccount(dto);
            if (succesInsert == true) {
            	// 不要となったセッションスコープ内のインスタンスを削除
            	session.removeAttribute("RegisterUser");

            	// 登録後のフォワード先を設定
            	forwardPath = CommonObject.REGISTER_DONE_JSP;
            	// 設定されたフォワード先にフォワード
                RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
                dispatcher.forward(request, response);
            } else {
            	// 保存に失敗した場合はエラー画面を表示する
            	RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.REGISTER_ERROR_JSP);
        		dispatch.forward(request, response);
            	//response.sendRedirect(CommonObject.REGISTER_ERROR_JSP);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストパラメーターの取得
        request.setCharacterEncoding(CommonObject.CHAR_CODE);
        String userName = request.getParameter("USER_NAME");
        String id = null;	//ID重複チェックするためだけの変数
        String userId = request.getParameter("USER_ID");
        String passWord = request.getParameter("PASSWORD");
        int administratorFlg = 0;
        ArrayList<String> list = new ArrayList<String>();

        WebBoardLogic logic = new WebBoardLogic();
		//ID重複チェックを行います
		boolean checkResult = logic.executeCheckUserInfo(list, id, userId);
		//IDに重複がない（=返り値がtrue）なら登録を行います
		if(checkResult == true){
			// 登録するユーザーの情報を設定
			UserInfoDto dto = new UserInfoDto(userName, userId, passWord, administratorFlg);

			// セッションスコープに登録ユーザーを保存
			HttpSession session = request.getSession();
			session.setAttribute("RegisterUser", dto);

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher(CommonObject.REGISTER_CONFIRM_JSP);
			dispatcher.forward(request, response);
		}else{
			//IDが重複した場合は重複通知画面を表示する
			RequestDispatcher dispatch = request.getRequestDispatcher(CommonObject.DUPLICATION_ERROR_JSP);
    		dispatch.forward(request, response);
			//response.sendRedirect(CommonObject.DUPLICATION_ERROR_JSP);
		}
    }

}
