package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ThreadDto;

public class ThreadDao {
	private Connection conn;

	// コンストラクタ
	public ThreadDao(Connection c) {
		this.conn = c;
	}
	public ThreadDto searchThread(String threadId, String threadName) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ThreadDto dto = new ThreadDto();
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT                 ");
		buf.append("   THREAD_ID           ");
		buf.append("  ,THREAD_NAME          ");
		buf.append("FROM                   ");
		buf.append("  SEIYA_DB.THREAD_NAME ");
		buf.append("WHERE                  ");
		buf.append(" THREAD_ID = ?         ");

		try{
			ps = conn.prepareStatement(buf.toString());
			int idx = 0;
			ps.setString(++idx, threadId);
			rs = ps.executeQuery();

			if(rs.next()){
			dto.setThreadId(   rs.getString("THREAD_ID")  );
			dto.setThreadName( rs.getString("THREAD_NAME"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			rs.close();
			ps.close();
		}

		return dto;
	}
}
