<%
/***************************************************************
 * <p>ProName：SignatureCut.jsp</p>
 * <p>Title：签名图片保存查询界面</p>
 * <p>Description：签名图片保存查询界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%
	GlobalInput tG=(GlobalInput)session.getValue("GI");
	String tOperate = request.getParameter("Operate");

	if("cut".equals(tOperate)){//保存剪切的图片
		String tFlag = "Succ";
		String tInfo = "";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		String x1 = request.getParameter("X1");
		String y1 = request.getParameter("Y1");
		String w = request.getParameter("W");
		String h =  request.getParameter("H");
		String pageName =  request.getParameter("PageName");
		String docCode =  request.getParameter("DocCode");
		String tRealPath = application.getRealPath("/").replace('\\','/');
		if(tRealPath == null || tRealPath.equals("")){
			tRealPath = session.getServletContext().getRealPath("/");
		}
		TransferData sTransferData = new TransferData();
		sTransferData.setNameAndValue("X1", x1);
		sTransferData.setNameAndValue("Y1", y1);   	
		sTransferData.setNameAndValue("W", w);   	
		sTransferData.setNameAndValue("H", h);   	
		sTransferData.setNameAndValue("PageName", pageName);   	
		sTransferData.setNameAndValue("DocCode", docCode);   
		sTransferData.setNameAndValue("RealPath", tRealPath);   
	 	VData tVData = new VData();
	 	tVData.add(tG);
	 	tVData.add(sTransferData);
	 	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 	if(!tBusinessDelegate.submitData(tVData, "", "SignatureUI")){
			 tFlag = "Fail";
			 tInfo = "截图失败,原因是:"+tBusinessDelegate.getCErrors().getFirstError();
		 }
		 String outjson = "{ \"Flag\":\""+tFlag+"\",\"Info\":\""+tInfo+"\"}";
	 	 out.print(outjson);
		
	}else if ("sq".equals(tOperate)){//查询签名
		
		int size = 0;
		String tPicPath = "";
		String docCode =  request.getParameter("DocCode");
		ExeSQL mExeSQL = new ExeSQL();
		String tSql = "";
		tSql="select concat(concat(concat('http://',c.serverport), a.picpath , a.pagename) , a.pagesuffix )from es_doc_pages a,es_doc_main b,es_server_info c  where a.docid = b.docid  and a.hostname = c.hostname and a.pagetype = '7'";
		tSql += " and b.doccode = '"+ "?docCode?" +"' " ;
		SQLwithBindVariables tsqlvb = new SQLwithBindVariables();
		tsqlvb.sql(tSql);
		tsqlvb.put("docCode",docCode);

  		tPicPath = mExeSQL.getOneValue(tsqlvb);
  		if(tPicPath!= null && !tPicPath.equals("")){
  			size++;
  		}
	  	
	  	String outjson = "{ \"Size\":\""+size+"\",\"PicPath\":\""+tPicPath+"\"}";
	 	out.print(outjson);
	}
%>
