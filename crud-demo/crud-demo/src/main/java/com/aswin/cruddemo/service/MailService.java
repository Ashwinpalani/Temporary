package com.aswin.cruddemo.service;

import com.aswin.cruddemo.entity.MailDetails;

public interface MailService {

	String sendSimpleMail(MailDetails details);
	String sendMailWithAttachment(MailDetails details);
}
