package com.aswin.cruddemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aswin.cruddemo.entity.MailDetails;
import com.aswin.cruddemo.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {
	@Autowired
	private MailService mailService;

	@PostMapping("/sendmail")
	public String sendMail(@RequestBody MailDetails details) {
		String status = mailService.sendSimpleMail(details);
		return status;
	}

	@PostMapping("/Attach")
	public String sendMailWithAttachment(@RequestBody MailDetails details) {
		String status = mailService.sendMailWithAttachment(details);
		return status;
	}

}
