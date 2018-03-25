package dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserInfoDto;

//データベースにログインフォームから送付されたユーザー名とパスワードを持ったユーザーがいるか検索
public class UserInfoDao {
	private Connection con;

	// コンストラクタ
	public UserInfoDao(Connection c) {
		this.con = c;
	}

	public boolean doInsert(UserInfoDto dto) {
		PreparedStatement ps = null;
		StringBuffer buf = new StringBuffer();

		buf.append("INSERT INTO USER_INFO (  ");
		buf.append("   USER_ID               ");
		buf.append("  ,PASSWORD              ");
		buf.append("  ,USER_NAME             ");
		buf.append("  ,ADMINISTRATOR_FLG     ");
		buf.append(") VALUES (               ");
		buf.append("   ? ");
		buf.append("  ,? ");
		buf.append("  ,? ");
		buf.append("  ,? ");
		buf.append(")    ");

		try {
			ps = con.prepareStatement(buf.toString());

			int i = 0;
			ps.setString(++i, dto.getUserId());
			ps.setString(++i, dto.getPassWord());
			ps.setString(++i, dto.getUserName());
			ps.setInt(++i, dto.getAdministratorFlg());

			ps.executeUpdate();

			// クローズする
			if (!ps.isClosed()) {
				ps.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean checkData(ArrayList<String> list, String userId, String newUserId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isCheck = true;
		StringBuffer buf = new StringBuffer();

		buf.append("SELECT     ");
		buf.append("  USER_ID  ");
		buf.append("FROM"       );
		buf.append("  USER_INFO");
		try {
			// データベースに問い合わせ
			ps = con.prepareStatement(buf.toString());
			rs = ps.executeQuery();

			// 検索結果の取り出し
			while (rs.next()) {
				// USER_IDを取り出してlistにadd
				String mId = rs.getString("USER_ID");
				list.add(mId);
			}

			for (Object o : list) {
				userId = o.toString().trim();
				if (!newUserId.equals(userId)) {
					isCheck = true;
				}else{
					isCheck = false;
					break;
				}
			}
			//クローズする
			if(!ps.isClosed()){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isCheck;
	}

	public UserInfoDto doSelect(String userId, String passWord, PrintWriter out) throws SQLException {

		UserInfoDto dto = new UserInfoDto();
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer buf = new StringBuffer();
		buf.append("SELECT               ");
		buf.append("   USER_ID           ");
		buf.append("  ,USER_NAME         ");
		buf.append("  ,PASSWORD          ");
		buf.append("  ,ADMINISTRATOR_FLG ");
		buf.append("FROM                 ");
		buf.append("  USER_INFO          ");
		buf.append("WHERE                ");
		buf.append("  USER_ID  = ? AND   ");
		buf.append("  PASSWORD = ?       ");

		try {
			ps = con.prepareStatement(buf.toString());
			int idx = 0;
			ps.setString(++idx, userId);
			ps.setString(++idx, passWord);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setUserId(rs.getString("USER_ID"));
				dto.setUserName(rs.getString("USER_NAME"));
				dto.setPassWord(rs.getString("PASSWORD"));
				dto.setAdministratorFlg(rs.getInt("ADMINISTRATOR_FLG"));

			}

		} catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		} finally {
			ps.close();
			rs.close();
		}
		return dto;
	}
}
