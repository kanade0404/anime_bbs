package common;

public class CommonObject {
	public static final String PARTITION = " / "; // 仕切り
	public static final String HONORIFIC = " さん"; // 敬称
	public static final String BRANK = ""; // 空欄

	public static final String POST_STATUS_NORMAL = "NORMAL"; // 投稿状況が「通常」
	public static final String POST_STATUS_DELETE = "DELETE"; // 投稿状況が「削除」

	// JSP
	// ホーム画面のJSP
	public static final String HOME_JSP = "/WEB-INF/view/Home.jsp";
	//タイトル一覧画面のJSP
	public static final String THREAD_JSP = "/WEB-INF/view/Thread.jsp";
	// 掲示板のJSP
	public static final String BOARD_JSP = "/WEB-INF/view/WebBoard.jsp";
	// ログインのJSP
	public static final String LOGIN_JSP = "/WEB-INF/view/Login.jsp";
	// ユーザー登録のJSP
	public static final String REGISTER_FORM_JSP = "/WEB-INF/view/RegisterForm.jsp";
	// ユーザー登録内容の確認のJSP
	public static final String REGISTER_CONFIRM_JSP = "/WEB-INF/view/RegisterConfirm.jsp";
	// ユーザー登録完了のJSP
	public static final String REGISTER_DONE_JSP = "/WEB-INF/view/RegisterDone.jsp";
	// ユーザー登録エラーのJSP
	public static final String REGISTER_ERROR_JSP = "/WEB-INF/view/RegisterError.jsp";
	// ユーザーID重複のJSP
	public static final String DUPLICATION_ERROR_JSP = "/WEB-INF/view/DuplicationId.jsp";

	// サーブレット
	public static final String HOME_SERVLET = "DispHome";
	public static final String THREAD_SERVLET = "DispThread";
	public static final String BOARD_SERVLET = "DispWebBoard";
	public static final String LOGIN_SERVLET = "DispLogin";
	public static final String REGISTER_FORM_SERVLET = "DispRegisterForm";
	public static final String EXECUTE_LOGIN_SERVLET = "ExecuteLogin";
	public static final String EXECUTE_INSERT_SERVLET = "ExecuteInsert";
	public static final String EXECUTE_EDIT_SERVLET = "ExecuteEdit";
	public static final String EXECUTE_ALL_DELETE_SERVLET = "ExecuteAllDelete";
	public static final String LOGOUT_SERVLET = "Logout";
	public static final String REGISTER_USER_SERVLET = "RegisterUser";

	// CSS
	public static final String HOME_CSS = "css/Home.css";	// ホーム画面のCSS
	public static final String THRED_CSS = "css/Thread.css";	// タイトル画面のCSS
	public static final String BOARD_CSS = "css/WebBoard.css";	// 掲示板画面のCSS

	// 接続情報
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String JDBC_URL = "jdbc:mysql://localhost/seiya_db?autoReconnect=true&useSSL=false";
	public static final String USER_ID = "seiya";
	public static final String USER_PASS = "kanseiya44";


	public static final String IMAGE_PATH = "image\\";	//静的jpgファイルのフォルダ
	public static final String UPLOAD_PATH = "upload\\";	//アップロードされたjpgファイルのフォルダ
	public static final String CHAR_CODE = "UTF-8"; // 文字コード
	public static final String CONTENT_TYPE = "text/html;charset=UTF-8"; //HTMLのコンテキストタイプ

	public static final String MESSAGE_LOGIN_NG = "ユーザーIDまたはパスワードが違います。";
}
