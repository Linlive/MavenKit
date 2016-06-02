package test;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by Administrator on 2016/6/2.
 */
public class MyAuthenticator extends Authenticator {
	String userName=null;
	String password=null;

	public MyAuthenticator(){
	}
	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
}
