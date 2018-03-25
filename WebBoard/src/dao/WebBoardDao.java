package dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.CommonObject;
import dto.SearchConditionDto;
import dto.WebBoardDto;

public class WebBoardDao {
	private Connection conn;

	// コンストラクタ
	public WebBoardDao(Connection c) {
		this.conn = c;
	}

//投稿一覧を取得します
	public ArrayList<WebBoardDto> getWebBoardList(SearchConditionDto searchCondition, String threadId) throws SQLException {

		ArrayList<WebBoardDto> list = new ArrayList<WebBoardDto>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT                          ");
		buf.append("   WB.THREAD_ID                 ");
		buf.append("  ,WB.POST_ID                   ");
		buf.append("  ,WB.REPLY_ID                  ");
		buf.append("  ,WB.USER_ID                   ");
		buf.append("  ,(SELECT USER_NAME FROM USER_INFO WHERE USER_ID = WB.USER_ID) USER_NAME    ");// 副問合せ
		buf.append("  ,WB.POST_MESSAGE              ");
		buf.append("  ,WB.POST_STATUS               ");
		buf.append("  ,WB.POST_TIME                 ");
		buf.append("  ,WB.POST_IMG1                 ");
		buf.append("  ,WB.POST_IMG2                  ");
		buf.append("FROM (WEB_BOARD WB INNER JOIN THREAD_NAME TN ON WB.THREAD_ID = TN.THREAD_ID) ");
		buf.append("INNER JOIN                      ");
		buf.append("  USER_INFO UI                  ");
		buf.append("ON                              ");
		buf.append("  UI.USER_ID=WB.USER_ID         ");
		buf.append("WHERE POST_STATUS = 'NORMAL'    ");
		buf.append("AND                             ");
		buf.append("WB.THREAD_ID = ?                ");

		//検索条件を入力
		if(searchCondition != null){
			if(searchCondition.getSearchName() != null){
				buf.append("AND");
				buf.append("  UI.USER_NAME = ?      ");
			}
			if(searchCondition.getSearchMessage() != null){
				buf.append("AND");
				buf.append("  POST_MESSAGE LIKE ?   ");
			}
			if(searchCondition.getSearchStartTime() != null){
				buf.append("AND");
				buf.append("  WB.POST_TIME >= ?     ");
			}
			if(searchCondition.getSearchLastTime() != null){
				buf.append("AND");
				buf.append("  WB.POST_TIME <= ?     ");
			}
		}

		buf.append("ORDER BY                        ");
		buf.append("   WB.POST_ID DESC              ");
		buf.append("  ,WB.REPLY_ID ASC              ");

		try {
			ps = conn.prepareStatement(buf.toString());

			int idx = 0;
			ps.setString(++idx, threadId);
			//検索条件をセット
			if(searchCondition != null){
				if(searchCondition.getSearchName() != null){
					ps.setString(++idx, searchCondition.getSearchName());
				}
				if(searchCondition.getSearchMessage() != null){
					ps.setString(++idx, "%" + searchCondition.getSearchMessage() + "%");
				}
				if(searchCondition.getSearchStartTime() != null){
					ps.setTimestamp(++idx, searchCondition.getSearchStartTime());
				}
				if(searchCondition.getSearchLastTime() != null){
					ps.setTimestamp(++idx, searchCondition.getSearchLastTime());
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				WebBoardDto dto = new WebBoardDto();
				dto.setThreadId(   rs.getString("THREAD_ID")    );
				dto.setPostId(     rs.getInt("POST_ID")         );
				dto.setReplyId(    rs.getInt("REPLY_ID")        );
				dto.setUserId(     rs.getString("USER_ID")      );
				dto.setUserName(   rs.getString("USER_NAME")    );
				dto.setMessages(   rs.getString("POST_MESSAGE") );
				dto.setPostStatus( rs.getString("POST_STATUS")  );
				dto.setPostTime(   rs.getTimestamp("POST_TIME") );
				dto.setPostImg1(   rs.getString("POST_IMG1")    );
				dto.setPostImg2(   rs.getString("POST_IMG2")    );
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			ps.close();
		}
		return list;
	}



	public void doInsert(PrintWriter out, WebBoardDto dto) throws SQLException {

		// 投稿IDの最大値を取得
		PreparedStatement psGetMaxId = null;
		PreparedStatement psGetMaxReplyId = null;
		ResultSet rsSetMaxId = null;
		ResultSet rsSetMaxReplyId = null;
		int maxPostNo = 0;
		int maxReplyNo = 0;

		StringBuffer buf = new StringBuffer();

		// 親の投稿であれば、POST_IDの最大値を取得してmaxPostNoに代入
		if(dto.getPostMode().equals("POST")){
			buf.append("SELECT                        ");
			buf.append("  MAX(POST_ID) AS MAX_POST_ID ");
			buf.append("FROM                          ");
			buf.append("  WEB_BOARD WB                ");

			try {
				psGetMaxId = conn.prepareStatement(buf.toString());
				rsSetMaxId = psGetMaxId.executeQuery();

				if (rsSetMaxId.next()) {
					maxPostNo = rsSetMaxId.getInt("MAX_POST_ID");
				}
			} catch (SQLException e) {
				out.println("SQLException:" + e.getMessage());
			} finally {
				psGetMaxId.close();
				rsSetMaxId.close();
			}
		// 子の投稿（返信）であれば、親のPOST_IDと合致するREPLY_IDの最大値を取得してmaxReplyNoに代入
		}else if(dto.getPostMode().equals("REPLY")){
			buf.append("SELECT                          ");
			buf.append("  MAX(REPLY_ID) AS MAX_REPLY_ID ");
			buf.append("FROM                            ");
			buf.append("  WEB_BOARD WB                  ");
			buf.append("WHERE POST_ID = ?               ");

			try {
				psGetMaxReplyId = conn.prepareStatement(buf.toString());
				int idx = 0;
				psGetMaxReplyId.setInt(++idx, dto.getPostId());
				rsSetMaxReplyId = psGetMaxReplyId.executeQuery();

				if (rsSetMaxReplyId.next()) {
					maxReplyNo = rsSetMaxReplyId.getInt("MAX_REPLY_ID");
				}
			} catch (SQLException e) {
				out.println("SQLException:" + e.getMessage());
			} finally {
				psGetMaxReplyId.close();
				rsSetMaxReplyId.close();
			}
		}

		// 投稿内容を保存する
		PreparedStatement ps = null;

		buf = new StringBuffer();
		buf.append("INSERT INTO WEB_BOARD (   ");
		buf.append("   THREAD_ID              ");
		buf.append("  ,POST_ID                ");
		buf.append("  ,REPLY_ID               ");
		buf.append("  ,USER_ID                ");
		buf.append("  ,POST_MESSAGE           ");
		buf.append("  ,POST_STATUS            ");
		if(!dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
			buf.append("  ,POST_TIME              ");
			buf.append("  ,POST_IMG1              ");
			buf.append("  ,POST_IMG2              ");
		}else if(!dto.getPostImg1().equals("") && dto.getPostImg2().equals("")){
			buf.append("  ,POST_TIME              ");
			buf.append("  ,POST_IMG1              ");
		}else if(dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
			buf.append("  ,POST_TIME              ");
			buf.append("  ,POST_IMG2              ");
		}else{
			buf.append("  ,POST_TIME              ");
		}
		buf.append(") VALUES (                ");
		buf.append("   ? ");
		buf.append("  ,? ");
		buf.append("  ,? ");
		buf.append("  ,? ");
		buf.append("  ,? ");
		buf.append("  ,? ");
		if(!dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
			buf.append("  ,?           ");
			buf.append("  ,?           ");
			buf.append("  ,?           ");
		}else if(!dto.getPostImg1().equals("") && dto.getPostImg2().equals("")){
			buf.append("  ,?           ");
			buf.append("  ,?           ");
		}else if(dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
			buf.append("  ,?           ");
			buf.append("  ,?           ");
		}else{
			buf.append("  ,?           ");
		}
		buf.append(")    ");

		try {
			ps = conn.prepareStatement(buf.toString());

			int i = 0;
			ps.setString(++i, dto.getThreadId());
			if(maxPostNo != 0){
				ps.setInt(++i, maxPostNo + 1);
				ps.setInt(++i, maxReplyNo);
			}else{
				ps.setInt(++i, dto.getPostId());
				ps.setInt(++i, maxReplyNo + 1);
			}
			ps.setString(++i, dto.getUserId());
			ps.setString(++i, dto.getMessages());
			ps.setString(++i, dto.getPostStatus());
			if(!dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
				ps.setTimestamp(++i, dto.getPostTime());
				ps.setString(++i, dto.getPostImg1());
				ps.setString(++i, dto.getPostImg2());
			}else if(!dto.getPostImg1().equals("") && dto.getPostImg2().equals("")){
				ps.setTimestamp(++i, dto.getPostTime());
				ps.setString(++i, dto.getPostImg1());
			}else if(dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
				ps.setTimestamp(++i, dto.getPostTime());
				ps.setString(++i, dto.getPostImg2());
			}else{
				ps.setTimestamp(++i, dto.getPostTime());
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		} finally {
			ps.close();
		}
	}

	public void doDelete(PrintWriter out, int postNo, int replyId) throws SQLException {
		PreparedStatement ps = null;

		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE WEB_BOARD SET ");
		buf.append("  POST_STATUS = ?    ");
		buf.append("WHERE                ");
		buf.append("  POST_ID = ?        ");
		buf.append("  AND                ");
		buf.append("  REPLY_ID = ?       ");

		try {
			ps = conn.prepareStatement(buf.toString());

			int i = 0;
			ps.setString(++i, CommonObject.POST_STATUS_DELETE);
			ps.setInt(++i, postNo);
			ps.setInt(++i, replyId);

			ps.executeUpdate();
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		} finally {
			ps.close();
		}
	}

	public void doUpdate(PrintWriter out, WebBoardDto dto) throws SQLException {
		PreparedStatement ps = null;

		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE WEB_BOARD SET ");
		buf.append("  POST_MESSAGE = ?   ");
		if(!dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
			buf.append("  ,POST_TIME = ?     ");
			buf.append("  ,POST_IMG1 = ?     ");
			buf.append("  ,POST_IMG2 = ?     ");
		}else if(!dto.getPostImg1().equals("") && dto.getPostImg2().equals("")){
			buf.append("  ,POST_TIME = ?     ");
			buf.append("  ,POST_IMG1 = ?     ");
		}else if(dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
			buf.append("  ,POST_TIME = ?     ");
			buf.append("  ,POST_IMG2 = ?     ");
		}else{
			buf.append("  ,POST_TIME = ?     ");
		}
		buf.append("WHERE                ");
		buf.append("  POST_ID = ?        ");
		buf.append("  AND                ");
		buf.append("  REPLY_ID = ?       ");

		try {
			ps = conn.prepareStatement(buf.toString());

			int i = 0;
			ps.setString(++i, dto.getMessages());
			ps.setTimestamp(++i, dto.getPostTime());
			if(!dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
				ps.setString(++i, dto.getPostImg1());
				ps.setString(++i, dto.getPostImg2());
			}else if(!dto.getPostImg1().equals("") && dto.getPostImg2().equals("")){
				ps.setString(++i, dto.getPostImg1());
			}else if(dto.getPostImg1().equals("") && !dto.getPostImg2().equals("")){
				ps.setString(++i, dto.getPostImg2());
			}
			ps.setInt(++i, dto.getPostId());
			ps.setInt(++i, dto.getReplyId());

			ps.executeUpdate();
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		} finally {
			ps.close();
		}
	}

	public void doAllDelete(PrintWriter out) throws SQLException {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("DELETE FROM WEB_BOARD ");
			ps.executeUpdate();
		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		} finally {
			ps.close();
		}
	}
}
