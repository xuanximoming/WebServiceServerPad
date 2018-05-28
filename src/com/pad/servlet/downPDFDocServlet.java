package com.pad.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.sql.BLOB;

@WebServlet("/downPDFDocServlet")
public class downPDFDocServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientID = request.getParameter("patientID");
		String visitID = request.getParameter("visitID");
		String pdfdocID = request.getParameter("pdfdocID");
		String sql = "select p.doctitle, p.pdfdata from emr.emr_inhospital t,emr.emr_pdfdoc p where t.inhospital_id = p.inhospital_id and t.mrn = '"
				+ patientID + "' and t.inhospitaltime = '" + visitID + "' and p.pdfdoc_id = '" + pdfdocID + "'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.88:1521:orcl1", "system", "manager");
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					String fileName = rs.getString("doctitle") + ".pdf";
					BLOB blob = (BLOB) rs.getBlob("pdfdata");
					InputStream in = blob.getBinaryStream();
					if (in == null) {
						response.setContentType("application/x-download");
						response.addHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

						OutputStream out = response.getOutputStream();
						BufferedInputStream buf = new BufferedInputStream(in);
						int len = (int) blob.getLength();
						byte[] b = new byte[len];
						while ((len = buf.read(b)) != -1) {
							out.write(b, 0, len);
						}
						out.flush();
						out.close();
					}
					in.close();
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs.close();
				stmt.close();
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}