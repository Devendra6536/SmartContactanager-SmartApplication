package com.smart.helper;
import java.util.Date;
import java.util.Properties;
import org.springframework.stereotype.Component;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class SendEmail {

	public boolean send_email(String message, String to, String subject) {
		String from = "devendra8682@gmail.com";
		// TODO Auto-generated method stub
		// variable for email
		String host = "smtp.gmail.com";

		// get the system properties
		Properties properties = System.getProperties();
		System.err.println("PROPERTIES " + properties);

		/* setting the imp info to the properties object */

		// host set and other information is set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.auth", true);

		// Step-1 to get the session object....
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
				return new jakarta.mail.PasswordAuthentication("hjjjh", "dsadsfds");
			}

		});

		session.setDebug(true);
		// compose the message {text, multimedia, attachment}//tglqwkuwrjcsgtak
		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			// from email
			mimeMessage.setFrom(from);

			// adding recipient to message
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// adding subject
			mimeMessage.setSubject(subject);

			// set the date
			mimeMessage.setSentDate(new Date());

			// adding attachment to message
			/*
			 * String path = "/home/team/devendra/JavaBackendDevelopment/welcome.jpg";
			 * // This mimeMultipart is used to contain the text+multimedia part
			 * //MimeMultipart mimeMultipart = new MimeMultipart();
			 * 
			 * // this mime is used to contain the text
			 * //MimeBodyPart textmime = new MimeBodyPart();
			 * 
			 * // this mime is used to contain the media files images,pdf,csv,video, audio
			 * etc
			 * //MimeBodyPart filemime = new MimeBodyPart();
			 * try {
			 * // this is responsible to bind the text
			 * textmime.setText(message);
			 * 
			 * // this is responsible to bind the file
			 * File file = new File(path);
			 * filemime.attachFile(file);
			 * 
			 * // Adding both of the mime into the multipart
			 * mimeMultipart.addBodyPart(textmime);
			 * mimeMultipart.addBodyPart(filemime);
			 * 
			 * } catch (Exception e) {
			 * // TODO: handle exception
			 * e.printStackTrace();
			 * }
			 * 
			 * // this is reponsible to bind the text and (multimedia part + text part)
			 * // combinedly
			 * //mimeMessage.setContent(mimeMultipart);
			 */

			// mimeMessage.setText(message);
			mimeMessage.setContent(message, "text/html");

			Transport.send(mimeMessage);
			System.out.println("Email sent Successfully");
			return true;

		} catch (Exception e) {
			System.out.println("ERROR ");
			e.printStackTrace();
		}
		return false;

	}

}
