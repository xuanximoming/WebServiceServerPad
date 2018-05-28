package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class DBUtil
{
  private static DBUtil instance = null;
  private static int dbOperCounter = 1;
  public static boolean isDebug = false;

  private static ArrayList<String> dbList = new ArrayList<String>();

  public static DBUtil getInstance()
  {
    if (instance == null) {
      instance = new DBUtil();
      try {
        Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
        loadDBNameFromProperties();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return instance;
  }

  public Connection getDBConnectionByDefaultDb() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("proxool.dcsoftDefaultDbpool");

      if (isDebug) {
        if (conn != null) {
          dbOperCounter += 1;
        }

        System.out.println();
        System.out.println("************Connection begin*************");
        System.out.println("成功得到Connection：" + conn);
        System.out.println("************Connection end***************");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return conn;
  }

  public Connection getDBConnectionByDbName(String dbName) {
    String dbUrl = null;
    for (int i = 0; i < dbList.size(); ++i) {
      String listDbName = (String)dbList.get(i);
      if (listDbName.contains(dbName)) {
        dbUrl = "proxool." + listDbName;
        break;
      }
    }

    if (dbUrl == null) {
      System.out.println("数据库配置错误");
      return null;
    }

    Connection conn = null;
    try {
      conn = DriverManager.getConnection(dbUrl);

      if (isDebug) {
        if (conn != null) {
          dbOperCounter += 1;
        }

        System.out.println();
        System.out.println("************Connection begin*************");
        System.out.println("成功得到Connection：" + conn);
        System.out.println("************Connection end***************");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return conn;
  }

  public static void closeResultSet(ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
        if (isDebug)
          System.out.println("成功释放ResultSet：" + rs);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void closePreparedStatement(PreparedStatement ps) {
    try {
      if (ps != null) {
        ps.close();
        if (isDebug)
          System.out.println("成功释放P....S....：" + ps);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void closeConnection(Connection conn) {
    try {
      if (conn != null) {
        conn.close();
        if (isDebug)
          System.out.println("成功释放Connection：" + conn);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws Exception {
    if (isDebug) {
      System.out.println();
      System.out.println("************releaseObj begin*************");
    }
    if (rs != null) {
      rs.close();
      if (isDebug) {
        System.out.println("成功释放ResultSet : " + rs);
      }
    }
    if (ps != null) {
      ps.close();
      if (isDebug) {
        System.out.println("成功释放P....S....：" + ps);
      }
    }
    if (conn != null) {
      conn.close();
      if (isDebug) {
        System.out.println("成功释放Connection：" + conn);
      }
    }
    if (isDebug)
      System.out.println("************releaseObj end***************");
  }

  public int getDbOperCounter()
  {
    if ((isDebug) && (dbOperCounter >= 999999998)) {
      dbOperCounter = 1;
      System.out.println("数据库操作已经超过了999999998次了，计数重新开始");
    }
    return dbOperCounter;
  }

  private static void loadDBNameFromProperties() {
    URL url = getInstance().getClass().getClassLoader().getResource("dcsoftProxool.properties");

    FileInputStream is = null;
    try {
      is = new FileInputStream(url.toString().substring(6));
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    Properties props = new Properties();
    try {
      props.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Enumeration<?> e = props.keys();
    while (e.hasMoreElements()) {
      String key = (String)e.nextElement();
      if (key.contains("proxool.alias")) {
        String value = props.getProperty(key);
        dbList.add(value);
      }
    }

    Enumeration<?> e1 = props.keys();

    while (e1.hasMoreElements()) {
      String key = (String)e1.nextElement();
      if (key.equals("isDebug")) {
        String value = props.getProperty(key);
        isDebug = Boolean.parseBoolean(value);
        return;
      }
    }
  }
}