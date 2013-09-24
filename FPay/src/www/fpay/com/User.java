package www.fpay.com;

public class User {
private String UserId;
private String UserNumber;
private String UserEmail;
private String UserName;
private String UserBio;

public String getUserId() {
	return UserId;
}
public void setUserId(String userId) {
	UserId = userId;
}
public String getUserNumber() {
	return UserNumber;
}
public void setUserNumber(String userNumber) {
	UserNumber = userNumber;
}
public String getUserEmail() {
	return UserEmail;
}
public void setUserEmail(String userEmail) {
	UserEmail = userEmail;
}
public String getUserName() {
	return UserName;
}
public void setUserName(String userName) {
	UserName = userName;
}
public String getUserBio() {
	return UserBio;
}
public void setUserBio(String userBio) {
	UserBio = userBio;
}
public void setAll(User getUser) {
	// TODO Auto-generated method stub
	UserId = getUser.getUserId();
	UserNumber = getUser.getUserNumber();
	UserEmail = getUser.getUserEmail();
	UserName = getUser.getUserName();
	UserBio = getUser.getUserBio();
}

}
