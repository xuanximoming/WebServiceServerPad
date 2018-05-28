package com.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface IHelloWebService {
	@WebMethod
	public abstract String example(String paramString);
}