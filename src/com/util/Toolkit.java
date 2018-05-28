package com.util;

public class Toolkit
{
  public static final String nullToStr(String str)
  {
    if (str == null) {
      return "";
    }
    return str.trim();
  }
}