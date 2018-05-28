package com.service;

import com.util.DBOper;
import com.util.Rs2List;
import com.util.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 
 * @author key
 *
 */
@WebService
public class GeneralOpSQLImpl implements IGeneralOpSQL {
	
	/**
	 * 根据传来的SQL语句进行查询
	 */
	@Override
	public ArrayList<String> getObjByDefaultDBSql(String sql) {
		DBOper query = new DBOper();
		try {
			ResultSet rs = query.executeQuery(Toolkit.nullToStr(sql));
			rs.last();
			int rowCounter = rs.getRow();
			rs.beforeFirst();
			ArrayList<String> list = Rs2List.convert(rs, rowCounter);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				query.closeAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public int executeByDefaultDBSql(String sql) {
		int returnValue = 0;
		DBOper query = new DBOper();
		try {
			returnValue = query.executeUpdate(Toolkit.nullToStr(sql));
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return returnValue;
		} finally {
			try {
				query.closeAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取用户信息，此方法传入用户名和密码，返回用户姓名和科室
	 * @param userNmae 用户名
	 * @param password 密码
	 * @return 姓名<;>科室
	 */
	@Override
	public String isUserRight(String userName, String password) {
		String returnValue = "空";
		DBOper query = new DBOper();
		try {
			ResultSet rs = query
					.executeQuery("select password,name,DEPT_CODE from STAFF_DICT where user_name='" + userName + "' ");
			if ((rs != null) && (rs.next())) {
				String pwd = rs.getString(1);
				System.out.println("password=" + query.Encrypt_JW(password));
				if ((query.Encrypt_JW(password).equals(pwd)) && (rs.getString(2) != null)) {
					returnValue = rs.getString(2) + "<;>" + rs.getString(3);
				}
			}

			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return returnValue;
		} finally {
			try {
				query.closeAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public String getSystemVersionDesc() {
		String Desc = "";
		try {
			File f = new File(super.getClass().getClassLoader().getResource("systemversiondesc.xml").getPath());
			if (f.exists()) {
				SAXReader reader = new SAXReader();

				Document doc = reader.read(f);
				Element root = doc.getRootElement();

				Iterator<?> itr = root.elementIterator("VALUE");
				Element data = (Element) itr.next();
				Desc = data.elementText("VERSION").trim() + "<;>" + data.elementText("DESC").trim();
			}
		} catch (Exception localException) {
		}
		return Desc;
	}
}