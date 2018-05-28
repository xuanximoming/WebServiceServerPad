package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOper
{
  private Connection conn = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;

  public ResultSet executeQuery(String sql) {
    if (DBUtil.isDebug) {
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("这是自服务启动（服务重新启动重新计算）的第 " + DBUtil.getInstance().getDbOperCounter() + " 次数据库操作");
      System.out.println("************dataBaseOper begin*************");
      System.out.println("sql:::  " + sql);
    }

    if (this.conn == null) {
      this.conn = DBUtil.getInstance().getDBConnectionByDefaultDb();
    }
    try
    {
      if (this.conn == null) {
        System.out.println("conn is null  !!!!");
        return null;
      }if (this.conn.isClosed()) {
        System.out.println("conn is closed!!!!");
        return null;
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
      return null;
    }

    if (this.conn != null) {
      long before = 0L;
      if (DBUtil.isDebug)
        before = System.currentTimeMillis();
      try
      {
        this.ps = this.conn.prepareStatement(sql, 1004, 1007);
        this.rs = this.ps.executeQuery();
        return this.rs;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      } finally {
        if (DBUtil.isDebug) {
          long after = System.currentTimeMillis();
          long time = after - before;
          System.out.println("数据库进行修改操作的用的时间是： " + time + " 毫秒");
          System.out.println("************dataBaseOper end***************");
        }
      }
    }
    return null;
  }

  public int executeUpdate(String sql) {
    if (DBUtil.isDebug) {
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("这是自服务启动（服务重新启动重新计算）的第 " + DBUtil.getInstance().getDbOperCounter() + " 次数据库操作");
      System.out.println("************dataBaseOper begin*************");
      System.out.println("sql:::  " + sql);
    }

    if (this.conn == null) {
      this.conn = DBUtil.getInstance().getDBConnectionByDefaultDb();
    }

    int returnValue = 0;
    try {
      if (this.conn == null) {
        System.out.println("conn is null  !!!!");
        return returnValue;
      }if (this.conn.isClosed()) {
        System.out.println("conn is closed!!!!");
        return returnValue;
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
      return returnValue;
    }

    if (this.conn != null) {
      long before = 0L;
      if (DBUtil.isDebug)
        before = System.currentTimeMillis();
      try {
        this.ps = this.conn.prepareStatement(sql);
        returnValue = this.ps.executeUpdate();
        return returnValue;
      } catch (Exception e) {
        e.printStackTrace();
        return returnValue;
      } finally {
        if (DBUtil.isDebug) {
          long after = System.currentTimeMillis();
          long time = after - before;
          System.out.println("数据库进行修改操作的用的时间是： " + time + " 毫秒");
          System.out.println("************dataBaseOper end***************");
        }
      }
    }
    return returnValue;
  }

  public ResultSet executeQuery(String sql, String dbName) {
    if (DBUtil.isDebug) {
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("这是自服务启动（服务重新启动重新计算）的第 " + DBUtil.getInstance().getDbOperCounter() + " 次数据库操作");
      System.out.println("************dataBaseOper begin*************");
      System.out.println("sql:::  " + sql);
    }

    if (this.conn == null) {
      this.conn = DBUtil.getInstance().getDBConnectionByDbName(dbName);
    }
    try
    {
      if (this.conn == null) {
        System.out.println("conn is null  !!!!");
        return null;
      }if (this.conn.isClosed()) {
        System.out.println("conn is closed!!!!");
        return null;
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
      return null;
    }

    if (this.conn != null) {
      long before = 0L;
      if (DBUtil.isDebug)
        before = System.currentTimeMillis();
      try
      {
        this.ps = this.conn.prepareStatement(sql, 1004, 1007);
        this.rs = this.ps.executeQuery();
        return this.rs;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      } finally {
        if (DBUtil.isDebug) {
          long after = System.currentTimeMillis();
          long time = after - before;
          System.out.println("数据库进行修改操作的用的时间是： " + time + " 毫秒");
          System.out.println("************dataBaseOper end***************");
        }
      }
    }
    return null;
  }

  public int executeUpdate(String sql, String dbName) {
    if (DBUtil.isDebug) {
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("这是自服务启动（服务重新启动重新计算）的第 " + DBUtil.getInstance().getDbOperCounter() + " 次数据库操作");
      System.out.println("************dataBaseOper begin*************");
      System.out.println("sql:::  " + sql);
    }

    if (this.conn == null) {
      this.conn = DBUtil.getInstance().getDBConnectionByDbName(dbName);
    }

    int returnValue = 0;
    try {
      if (this.conn == null) {
        System.out.println("conn is null  !!!!");
        return returnValue;
      }if (this.conn.isClosed()) {
        System.out.println("conn is closed!!!!");
        return returnValue;
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
      return returnValue;
    }

    if (this.conn != null) {
      long before = 0L;
      if (DBUtil.isDebug)
        before = System.currentTimeMillis();
      try {
        this.ps = this.conn.prepareStatement(sql);
        returnValue = this.ps.executeUpdate();
        return returnValue;
      } catch (Exception e) {
        e.printStackTrace();
        return returnValue;
      } finally {
        if (DBUtil.isDebug) {
          long after = System.currentTimeMillis();
          long time = after - before;
          System.out.println("数据库进行修改操作的用的时间是： " + time + " 毫秒");
          System.out.println("************dataBaseOper end***************");
        }
      }
    }

    return returnValue;
  }

  public void closeResultSet() {
    try {
      if (this.rs != null) {
        this.rs.close();
        if (DBUtil.isDebug)
          System.out.println("成功释放ResultSet ：" + this.rs);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closePreparedStatement() {
    try {
      if (this.ps != null) {
        this.ps.close();
        if (DBUtil.isDebug)
          System.out.println("成功释放P....S....：" + this.ps);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeConnection() {
    try {
      if (this.conn != null) {
        this.conn.close();
        if (DBUtil.isDebug)
          System.out.println("成功释放Connection：" + this.conn);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeAll() throws Exception {
    if (DBUtil.isDebug) {
      System.out.println();
      System.out.println("************releaseObj begin*************");
    }
    if (this.rs != null) {
      this.rs.close();
      if (DBUtil.isDebug) {
        System.out.println("成功释放ResultSet : " + this.rs);
      }
    }
    if (this.ps != null) {
      this.ps.close();
      if (DBUtil.isDebug) {
        System.out.println("成功释放P...S.....：" + this.ps);
      }
    }
    if (this.conn != null) {
      this.conn.close();
      if (DBUtil.isDebug) {
        System.out.println("成功释放Connection：" + this.conn);
      }
    }
    if (DBUtil.isDebug)
      System.out.println("************releaseObj end***************");
  }

  public String Encrypt_JW(String src)
  {
    String oneChar = "";
    String result = "";
    if (src.length() == 0) {
      return "";
    }
    src = src.trim();
    for (int i = 0; i < src.length(); ++i) {
      oneChar = src.substring(i, i + 1);
      char[] arrChar = oneChar.toCharArray();
      int k;
      if (i % 2 == 0)
        k = arrChar[0] - i + 8 - 1;
      else {
        k = arrChar[0] + i - 32 + 1;
      }

      result = result + (char)k;
    }
    return result;
  }
}