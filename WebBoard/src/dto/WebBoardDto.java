package dto;

import java.sql.Timestamp;

public class WebBoardDto {
	private String threadId;		// スレッドID
	private int postId; 			// 投稿ID
	private int replyId;			// 返信ID
	private String userId; 		// 投稿者ID
	private String userName;		// 投稿者名（投稿表示の時のみ使用）
	private String messages; 		// 投稿内容
	private String postStatus; 	// 投稿状態
	private Timestamp postTime; 	// 投稿時間
	private String postImg1;		// 画像の名前(1)
	private String postImg2;		// 画像の名前(2)
	private String postMode;		// 投稿の種類（親の投稿or子の投稿）

	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	public Timestamp getPostTime() {
		return postTime;
	}
	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}
	public String getPostImg1() {
		return postImg1;
	}
	public void setPostImg1(String postImg1) {
		this.postImg1 = postImg1;
	}
	public String getPostImg2() {
		return postImg2;
	}
	public void setPostImg2(String postImg2) {
		this.postImg2 = postImg2;
	}
	public String getPostMode() {
		return postMode;
	}
	public void setPostMode(String postMode) {
		this.postMode = postMode;
	}

}
