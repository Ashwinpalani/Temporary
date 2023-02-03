package com.aswin.cruddemo.serviceimpl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.aswin.cruddemo.entity.MailDetails;
import com.aswin.cruddemo.service.MailService;

@Service
public class Mailserviceimpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendSimpleMail(MailDetails details) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBoby());
			mailMessage.setSubject(details.getSubject());
			javaMailSender.send(mailMessage);
			return "Mail sent Successfully";

		} catch (Exception e) {
			return "Error while Sending mail";
		}
	}

	@Override
	public String sendMailWithAttachment(MailDetails details) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBoby());
			mimeMessageHelper.setSubject(details.getSubject());
			FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
			mimeMessageHelper.addAttachment(file.getFilename(), file);
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		} catch (Exception e) {
			return "Error while sending Mail!!!";
		}
	}

}
