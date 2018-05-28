package com.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface IGeneralOpFile {
	@WebMethod
	public abstract String tranferFileStringWithEncode(String paramString);

	@WebMethod
	public abstract String fetchFileStringWithEncode(String paramString1, String paramString2);
}