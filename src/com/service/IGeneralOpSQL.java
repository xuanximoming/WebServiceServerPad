package com.service;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface IGeneralOpSQL
{
	@WebMethod
  public abstract ArrayList<String> getObjByDefaultDBSql(String paramString);
	@WebMethod
  public abstract int executeByDefaultDBSql(String paramString);
	@WebMethod
  public abstract String isUserRight(String paramString1, String paramString2);
}