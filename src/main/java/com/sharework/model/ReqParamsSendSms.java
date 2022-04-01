package com.sharework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReqParamsSendSms {
	private final String sender = "01050214674";
	private String[] receivers;
	private String content;
}
