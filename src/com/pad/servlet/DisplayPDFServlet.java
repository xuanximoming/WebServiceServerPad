package com.pad.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 展示PDF的电子病历
 * @author key
 *
 */
@WebServlet("/DisplayPDFServlet")
public class DisplayPDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String patientID = request.getParameter("patientID");
			String emrTypeID = request.getParameter("emrTypeID");

			request.setAttribute("patientID", patientID);
			request.setAttribute("emrTypeID", emrTypeID);
			request.getRequestDispatcher("displayPdf.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}