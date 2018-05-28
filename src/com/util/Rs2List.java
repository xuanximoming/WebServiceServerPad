package com.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rs2List
{
  public static synchronized ArrayList<String> convert(ResultSet rs, int counter)
  {
    ArrayList<String> lst = new ArrayList<String>();
    if (rs != null) {
      try {
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String s = "";
        for (int i = 1; i <= count; ++i) {
          s = s + rsmd.getColumnName(i).toLowerCase() + "<;>";
        }
        lst.add(s);

        while (rs.next()) {
          s = "";
          for (int i = 1; i <= count; ++i) {
            s = s + null2String(rs.getString(rsmd.getColumnName(i).toLowerCase()), " ") + "<;>";
          }
          lst.add(s);
        }
        lst.trimToSize();
        if (lst.size() > 0)
          return lst;
      } catch (SQLException e) {
        e.printStackTrace();
        return null;
      }
    }
    return null;
  }

  private static String null2String(String arg0, String arg1) {
    if (arg0 != null) {
      return arg0;
    }
    if (arg1 != null) {
      return arg1;
    }
    return "";
  }
}