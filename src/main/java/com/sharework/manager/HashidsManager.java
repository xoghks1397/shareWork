package com.sharework.manager;

import org.hashids.Hashids;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashidsManager {
	String SALT = "rwy4674_erw";
	Hashids hashIds = new Hashids(SALT);

	public String toEncode(int number) {
		return hashIds.encode(number);
	}

	public long[] toDecodeArray(String code) {
		return hashIds.decode(code);
	}
}
