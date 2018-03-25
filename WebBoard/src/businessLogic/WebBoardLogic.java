package businessLogic;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import common.CommonObject;
import dao.ThreadDao;
import dao.UserInfoDao;
import dao.WebBoardDao;
import dto.SearchConditionDto;
import dto.ThreadDto;
import dto.UserInfoDto;
import dto.WebBoardDto;

public class WebBoardLogic {
	// 接続情報
	String driverName = CommonObject.DRIVER_NAME;
	String jdbcUrl = CommonObject.JDBC_URL;
	String userId = CommonObject.USER_ID;
	String userPass = CommonObject.USER_PASS;
	// 接続情報の生成
	private Connection setConnection(Connection con) {
		try {
			// Connectionの発行
			Class.forName(driverName);
			con = DriverManager.getConnection(jdbcUrl, userId, userPass);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	private Connection setConnection(Connection con, PrintWriter out) {
		try {
			// Connectionの発行
			Class.forName(driverName);
			con = DriverManager.getConnection(jdbcUrl, userId, userPass);
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		} catch (ClassNotFoundException e) {
			out.println("ClassNotFoundException:" + e.getMessage());
		}
		return con;
	}

	//ユーザー情報の登録
	public boolean executeCreateAccount(UserInfoDto dto) {

		boolean succesInsert = false;
		Connection con = null;

		try {
			// データベースにアクセスしてデータを取得
			con = setConnection(con);
			// データベースにアクセスして登録情報を挿入
			UserInfoDao dao = new UserInfoDao(con);
			succesInsert = dao.doInsert(dto);
			// Connectionをクローズ
			if (!con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return succesInsert;
	}

	// ログイン情報の取得（userID:ID,パスワード、その他）
	public UserInfoDto executeSelectUserInfo(String userId, String passWord, PrintWriter out) {
		Connection con = null;
		UserInfoDto dto = new UserInfoDto();

		try {
			con = setConnection(con, out);
			UserInfoDao dao = new UserInfoDao(con);
			dto = dao.doSelect(userId, passWord, out);
			// Connectionをクローズ
			if (!con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		}

		return dto;
	}
	//ID重複をチェックします
	public boolean executeCheckUserInfo(ArrayList<String>list, String id, String newId){
		boolean checkId = false;
		Connection con = null;

		try{
			con = setConnection(con);
			UserInfoDao dao = new UserInfoDao(con);
			checkId = dao.checkData(list, id, newId);
			if (!con.isClosed()) {
				con.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return checkId;

	}

	//スレッド名の検索
	public ThreadDto searchThreadName(String threadId, String threadName){
		Connection con = null;
		ThreadDto dto = new ThreadDto();

		try{
			// データベースにアクセスしてデータを取得
			con = setConnection(con);
			ThreadDao dao = new ThreadDao(con);
			dto = dao.searchThread(threadId, threadName);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	//投稿内容を取得します
	public ArrayList<WebBoardDto> getWebBoardList(SearchConditionDto dto, String threadId) {
		Connection con = null;
		ArrayList<WebBoardDto> list = new ArrayList<WebBoardDto>();

		if (list.isEmpty()) {
			con = setConnection(con);
			WebBoardDao dao = new WebBoardDao(con);
			// 投稿内容をデータベースから取得する
			try {
				list = dao.getWebBoardList(dto, threadId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	//投稿内容をデータベースに挿入します
	public void executeInsert(PrintWriter out, WebBoardDto dto) {
		Connection con = null;
		con = setConnection(con, out);
		WebBoardDao dao = new WebBoardDao(con);

		try {
			dao.doInsert(out, dto);
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		}
	}

	// 投稿内容をデータベースから削除する（ステータスを変える）
	public void executeDelete(PrintWriter out, int postNo, int replyId) {
		Connection con = null;
		con = setConnection(con, out);
		WebBoardDao dao = new WebBoardDao(con);

		try {
			dao.doDelete(out, postNo, replyId);
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		}
	}

	//投稿内容の編集をします
	public void executeUpdate(PrintWriter out, WebBoardDto dto) {
		Connection con = null;
		con = setConnection(con, out);
		WebBoardDao dao = new WebBoardDao(con);

		try {
			dao.doUpdate(out, dto);
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		}
	}

	//投稿内容を全て削除します（ステータスを変える）
	public void executeAllDelete(PrintWriter out) {
		Connection con = null;
		con = setConnection(con, out);
		WebBoardDao dao = new WebBoardDao(con);

		try {
			dao.doAllDelete(out);
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		}
	}
}
