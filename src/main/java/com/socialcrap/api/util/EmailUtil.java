package com.socialcrap.api.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections.CollectionUtils;

import com.socialcrap.api.model.request.dto.EmailRequest;

public class EmailUtil {

	public static final String from = "do-not-reply@socialcrap.com";
	public static final String password = "";
	public static final String server = "localhost";
	public static final String protocol = "";

	public static boolean sendEmail(EmailRequest request) {

		if (!CollectionUtils.isEmpty(request.getTo())) {

			Session session = Session.getDefaultInstance(getProperties(server, protocol));

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				if (!CollectionUtils.isEmpty(request.getTo())) {
					message.addRecipients(Message.RecipientType.TO, getAddressListFromStringList(request.getTo()));
				}
				if (!CollectionUtils.isEmpty(request.getCc())) {
					message.addRecipients(Message.RecipientType.CC, getAddressListFromStringList(request.getCc()));
				}
				if (!CollectionUtils.isEmpty(request.getBcc())) {
					message.addRecipients(Message.RecipientType.BCC, getAddressListFromStringList(request.getBcc()));
				}

				if (request.getSubject() != null) {
					message.setSubject(request.getSubject());
				}
				Multipart multiPart = new MimeMultipart();

				if (request.getMessage() != null) {
					BodyPart messageBody = new MimeBodyPart();
					messageBody.setText(request.getMessage());
					multiPart.addBodyPart(messageBody);
				}

				BodyPart attachment = null;
				DataSource source = null;

				if (!CollectionUtils.isEmpty(request.getAttachments())) {
					for (String attach : request.getAttachments()) {
						File file = new File(attach);
						if (file.isFile() && file.exists()) {
							source = new FileDataSource(file);
							attachment = new MimeBodyPart();
							attachment.setDataHandler(new DataHandler(source));
							attachment.setFileName(file.getName());
							multiPart.addBodyPart(attachment);
						}
					}
				}

				message.setContent(multiPart);

				Transport.send(message);
				return true;

			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	private static Properties getProperties(String server, String protocol) {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", server);
		properties.put("mail.smtp.auth", true);
		return properties;
	}

	private static Address[] getAddressListFromStringList(List<String> list) {
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		Address[] addressList = new Address[list.size()];
		try {
			for (int i = 0; i < list.size(); i++) {
				addressList[i] = new InternetAddress(list.get(i));
			}
		} catch (AddressException ex) {
			ex.printStackTrace();
		}
		return addressList;
	}

}
