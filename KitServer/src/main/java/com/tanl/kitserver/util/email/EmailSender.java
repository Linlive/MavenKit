package com.tanl.kitserver.util.email;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送邮件
 * Created by Administrator on 2016/6/2.
 */
public class EmailSender {

	private static final String emailFrom = "tl_friend@163.com";
	private static final String emailName = "tl_friend@163.com";
	private static final String emailPassword = "wy123456";

	public static boolean sendTo(String addrTo){
		boolean sendSuccess = true;
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.163.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		// 设置环境信息
		Session session = Session.getInstance(props);

		//String code = GenerateRandomString.generateVerificationString(6);

		try {
			// 创建邮件对象
			Message msg = new MimeMessage(session);
			msg.setSubject("Travel kit");
			// 设置邮件内容
			msg.setText("找回密码请点击\nhttp://192.168.1.222:8080/KitServer\n请勿回复此邮件\n");// + "code is " + code);
			// 设置发件人
			msg.setFrom(new InternetAddress(emailFrom));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			//Transport.send(msg, new Address[]{new InternetAddress(emailFrom)});
			//密码
			transport.connect(emailName, emailPassword);
			// 发送邮件
			transport.sendMessage(msg, new Address[]{new InternetAddress(addrTo)});
			// 关闭连接
			transport.close();

		} catch (AddressException e) {
			e.printStackTrace();
			sendSuccess = false;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			sendSuccess = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			sendSuccess = false;
		}
		return sendSuccess;
	}
}
