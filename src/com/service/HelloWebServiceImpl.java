package com.service;

import javax.jws.WebService;

@WebService
public class HelloWebServiceImpl implements IHelloWebService {
	@Override
	public String example(String message) {
		return getAuthor() + "," + message;
	}

	private String getAuthor() {
		return "zhangyi";
	}
}