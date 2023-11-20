package com.smart.entities;

public class Email {

	private String subject;
	private String to;
	private String message;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Email(String subject, String to, String message) {
		super();
		this.subject = subject;
		this.to = to;
		this.message = message;
	}
	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Email [subject=" + subject + ", to=" + to + ", message=" + message + "]";
	}
	
}
