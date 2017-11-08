package com.sinosoft.easyscan5.core.controller.adapter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

public class EasyScanServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(EasyScanServlet.class);

	/**
	 * Constructor of the object.
	 */
	public EasyScanServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DiskFileUpload nDiskFileUpload = new DiskFileUpload();
		
			nDiskFileUpload.setSizeMax(4194304);
			
			StringBuffer bufXML = new StringBuffer(1024);
			  List nFileItems = nDiskFileUpload.parseRequest(request);
			 
			  Iterator nIterator = nFileItems.iterator();
			  Iterator nIterator1 = nFileItems.iterator();
			 
			  StringBuffer xmlBuffer=new StringBuffer(1024);
			  String CON_KEY="IndexXML";
			  String strKey=CON_KEY;
			  int i=0;
			 
			  while(nIterator.hasNext()){
			    FileItem nFileItem = (FileItem)nIterator.next();
			    if(nFileItem.getFieldName().equalsIgnoreCase(strKey)){
			      xmlBuffer.append(nFileItem.getString());
			    }
			    i++;
			    strKey =CON_KEY + String.valueOf(i);
			  }
			
			  String strXML=xmlBuffer.toString().trim();
			EasyScanAdapterBL bl = new EasyScanAdapterBL();
		
			String result = bl.execute(request, strXML);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("EasyScanAdapter Post 异常", e);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
