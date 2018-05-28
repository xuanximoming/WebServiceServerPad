package com.pad.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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


@WebServlet("/getEmrPDFPathServlet")
public class getEmrPDFPathServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public static String readBolbPDF2File(String patientID, String visitID, String emrTypeID) {
		String sql = "select p.doctitle,p.pdfdata from emr.emr_inhospital t,emr.emr_pdfdoc p where t.inhospital_id = p.inhospital_id and t.mrn = '"
				+ patientID + "' and t.inhospitaltime = '" + visitID + "' and p.doctype_id = '" + emrTypeID + "'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String PDF_path = null;
		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.88:1521:orcl1", "system", "manager");
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {

					String docTitle = rs.getString("doctitle");
					BLOB blob = (BLOB) rs.getBlob("pdfdata");
					InputStream in = blob.getBinaryStream();
					if (in != null) {

						File emr_dir = new File("E:\\病历文档目录");
						File patientID_dir = new File("E:\\病历文档目录\\" + patientID);
						File visitID_dir = new File("E:\\病历文档目录\\" + patientID + "\\" + visitID);

						if ((!emr_dir.exists()) && (!emr_dir.isDirectory())) {
							emr_dir.mkdir();
						}

						if ((!patientID_dir.exists()) && (!patientID_dir.isDirectory())) {
							patientID_dir.mkdir();
						}
						if ((!visitID_dir.exists()) && (!visitID_dir.isDirectory())) {
							visitID_dir.mkdir();
						}

						PDF_path = "\\\\192.168.2.101\\病历文档目录\\" + patientID + "\\" + visitID + "\\" + docTitle
								+ ".pdf";
						FileOutputStream fos = new FileOutputStream(PDF_path);
						int len = (int) blob.length();
						byte[] buffer = new byte[len];
						while ((len = in.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
						}
						fos.close();
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
		return PDF_path;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientID = request.getParameter("patientID");
		String visitID = request.getParameter("visitID");
		String emrTypeID = request.getParameter("emrTypeID");
		String PDF_PATH = null;
		if ((patientID != null) && (visitID != null) && (emrTypeID != null))
			PDF_PATH = readBolbPDF2File(patientID, visitID, emrTypeID);
		else {
			PDF_PATH = "";
		}
		if (PDF_PATH == null) {
			PDF_PATH = "";
		}
		response.setCharacterEncoding("gbk");
		response.getWriter().write(PDF_PATH);
		response.getWriter().flush();
		response.getWriter().close();
	}

	public static void main(String[] args) {
		String patientID = "00091419";
		String visitID = "2";
		String emrTypeID = "53";
		readBolbPDF2File(patientID, visitID, emrTypeID);
	}
}