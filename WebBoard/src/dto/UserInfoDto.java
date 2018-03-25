package dto;

public class UserInfoDto {
	private String userName;
	private String userId;
	private String passWord;
	private int administratorFlg;

	public UserInfoDto(){

	}

	public UserInfoDto(String userName, String userId, String passWord, int administratorFlg) {
		this.userName = userName;
		this.userId = userId;
        this.passWord = passWord;
        this.administratorFlg = administratorFlg;

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

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getAdministratorFlg() {
		return administratorFlg;
	}

	public void setAdministratorFlg(int administratorFlg) {
		this.administratorFlg = administratorFlg;
	}
}