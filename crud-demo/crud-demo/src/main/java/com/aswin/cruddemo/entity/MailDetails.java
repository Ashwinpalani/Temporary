package com.aswin.cruddemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDetails {
	private String recipient;
	private String msgBoby;
	private String subject;
	private String attachment;

}
