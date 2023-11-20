package com.smart.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

	public void removeAttributeThrroughSession() {
		try {
			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getSession();
			session.removeAttribute("message");
			session.removeAttribute("PasswdChangemessage");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void removeAttributeThrroughSession1() {
		try {
			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getSession();
			session.removeAttribute("samePasswdCase");
			session.removeAttribute("confrimPasswdmessage");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
