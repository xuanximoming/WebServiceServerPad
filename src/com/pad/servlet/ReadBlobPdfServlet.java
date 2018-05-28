package com.pad.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReadBlobPdfServlet")
public class ReadBlobPdfServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if ((request.getParameter("patientID") == null) || (request.getParameter("emrTypeID") == null))
			return;
		try {
			String sql = "select p.pdfdata from emr.emr_inhospital t,emr.emr_pdfdoc p where t.inhospital_id = p.inhospital_id and t.mrn = '"
					+ request.getParameter("patientID") + "' and p.doctype_id = '" + request.getParameter("emrTypeID")
					+ "'";
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			InputStream is = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.88:1521:orcl1", "system", "manager");
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					is = rs.getBlob("pdfdata").getBinaryStream();
				}

				if (is != null) {
					is = new BufferedInputStream(is);
					OutputStream os = response.getOutputStream();

					byte[] buf = new byte[1024];
					int num;
					while ((num = is.read(buf)) != -1) {
						os.write(buf, 0, num);
					}
					os.close();
				}
				is.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
				try {
					if ((rs != null) && (!rs.isClosed())) {
						rs.close();
					}
					if ((stmt != null) && (!stmt.isClosed())) {
						stmt.close();
					}
					if ((conn == null) || (conn.isClosed())) {
						try {
							if ((rs != null) && (!rs.isClosed())) {
								rs.close();
							}
							if ((stmt != null) && (!stmt.isClosed())) {
								stmt.close();
							}
							if ((conn != null) && (!conn.isClosed()))
								conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						return;
					}
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					if ((rs != null) && (!rs.isClosed())) {
						rs.close();
					}
					if ((stmt != null) && (!stmt.isClosed())) {
						stmt.close();
					}
					if ((conn != null) && (!conn.isClosed()))
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				if ((rs != null) && (!rs.isClosed())) {
					rs.close();
				}
				if ((stmt != null) && (!stmt.isClosed())) {
					stmt.close();
				}
				if ((conn != null) && (!conn.isClosed()))
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static InputStream query_getEmrPdfBlob(String patientID, String emrTypeID)
			throws ClassNotFoundException, SQLException {
		String sql = "select p.pdfdata from emr.emr_inhospital t,emr.emr_pdfdoc p where t.inhospital_id = p.inhospital_id and t.mrn = '"
				+ patientID + "' and p.doctype_id = '" + emrTypeID + "'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		InputStream result = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.88:1521:orcl1", "system", "manager");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				result = rs.getBlob("pdfdata").getBinaryStream();
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		String result = "";
		URL U = new URL(
				"http://192.168.3.59:8080/WebServer-Pad/getEmrPDFPath?patientID=00091419&visitID=2&emrTypeID=1");
		URLConnection connection = U.openConnection();
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result = result + line;
		}
		in.close();
		System.out.print(result);
	}
}