package com.sharework.api;

import java.nio.charset.StandardCharsets;

import java.util.Base64;

import org.apache.http.HttpHeaders;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.sharework.model.ReqParamsSendSms;

@Configuration
public class SendSmsApi {

	private WebClient webClient = WebClient
			.builder()
			.baseUrl("https://api.bluehouselab.com/smscenter/v1.0/sendsms")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json", "utf-8")
			.build();

	public HttpStatus sendSms(ReqParamsSendSms smsdto) {

		// 가리자
		final String SECRET_KEY = "bkbs4674:e91fe53c911211e9bbad0cc47a1fcfae";
		byte key[] = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
		String AUTH_KEY = "Basic " + Base64.getEncoder().encodeToString(key);

		Gson gson = new Gson();
		String smsJson = gson.toJson(smsdto);

		ClientResponse response = webClient
				.post()
				.header("Authorization", AUTH_KEY)
				.bodyValue(smsJson)
				.exchange()
				.block();

		return response.statusCode();
	}
}
