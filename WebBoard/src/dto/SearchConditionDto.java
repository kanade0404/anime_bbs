package dto;

import java.sql.Timestamp;

public class SearchConditionDto {
	private String searchName;			// ユーザー名
	private String searchMessage;			// 投稿内容
	private Timestamp searchStartTime;	// 投稿時間（始点）
	private Timestamp searchLastTime;		// 投稿時間（終点）

	public Timestamp getSearchStartTime() {
		return searchStartTime;
	}
	public void setSearchStartTime(Timestamp searchStartTime) {
		this.searchStartTime = searchStartTime;
	}
	public Timestamp getSearchLastTime() {
		return searchLastTime;
	}
	public void setSearchLastTime(Timestamp searchLastTime) {
		this.searchLastTime = searchLastTime;
	}
	public String getSearchMessage() {
		return searchMessage;
	}
	public void setSearchMessage(String searchMessage) {
		this.searchMessage = searchMessage;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

}
