package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@WebServlet("/AndroidVersionServlet")
public class AndroidVersionServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  public void destroy()
  {
    super.destroy();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    String Desc = "";
    try
    {
      File f = new File(super.getClass().getClassLoader().getResource("systemversiondesc.xml").getPath());
      if (f.exists()) {
        SAXReader reader = new SAXReader();

        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        
        @SuppressWarnings("rawtypes")
		Iterator itr = root.elementIterator("VALUE");
        Element data = (Element)itr.next();
        Desc = data.elementText("VERSION").trim() + "<;>" + data.elementText("DESC").trim();
        out.print(Desc);
      }
    } catch (Exception e) {
      out.print("");
    }
    out.flush();
    out.close();
  }
}