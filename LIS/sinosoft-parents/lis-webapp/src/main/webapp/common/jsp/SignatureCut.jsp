<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@ page language="java" pageEncoding="GBK"%>

<%

	String tOper = request.getParameter("oper");
	if("cut".equals(tOper)){
		String tFlag = "Succ";
		String tInfo = "";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String w = request.getParameter("w");
		String h =  request.getParameter("h");
		String pageName =  request.getParameter("pageName");
		System.out.println(pageName);
		String docCode =  request.getParameter("docCode");
		String tRealPath = application.getRealPath("/").replace('\\','/');
		if(tRealPath == null || tRealPath.equals("")){
			tRealPath = session.getServletContext().getRealPath("/");
		}
		TransferData sTransferData = new TransferData();
		sTransferData.setNameAndValue("x1", x1);
		sTransferData.setNameAndValue("y1", y1);   	
		sTransferData.setNameAndValue("w", w);   	
		sTransferData.setNameAndValue("h", h);   	
		sTransferData.setNameAndValue("pageName", pageName);   	
		sTransferData.setNameAndValue("docCode", docCode);   
		sTransferData.setNameAndValue("realPath", tRealPath);   
	 	VData tVData = new VData();
	 	tVData.add(sTransferData);
	 	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 	if(!tBusinessDelegate.submitData(tVData, "", "SignatureBL")){
			 tFlag = "Fail";
			 tInfo = "截图失败,原因是:"+tBusinessDelegate.getCErrors().getFirstError();
		 }
		 String outjson = "{ \"Flag\":\""+tFlag+"\",\"Info\":\""+tInfo+"\"}";
	 	 out.print(outjson);
		
	}else if ("sq".equals(tOper)){
		//查询签名
		/* int size = 0;
		String tPicPath = "";
		String docCode =  request.getParameter("docCode");
		String tSql = "";
		tSql="select concat(concat(concat(concat('http://',c.serverport), a.picpath) , a.pagename) , a.pagesuffix) from es_doc_pages a,es_doc_main b,es_server_info c  where a.docid = b.docid and b.doccode = '"+docCode+"' and a.hostname = c.hostname and a.pagetype = '7'"
			+" union"
			+" select concat(concat(concat(concat('http://',c.serverport), a.picpath), a.pagename), a.pagesuffix) from es_doc_pages a,es_doc_main b,es_server_info c  where a.docid = b.docid and b.doccode = (select prtno from lccont where contno = '"+docCode+"') and a.hostname = c.hostname and a.pagetype = '7'";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	    TransferData sTransferData=new TransferData();
	    sTransferData.setNameAndValue("SQL", tSql);
	    VData tVData = new VData();
	  	tVData.add(sTransferData);
	  	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  	if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI")){
	  		tPicPath = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
	  		if(tPicPath!= null && !tPicPath.equals("")){
	  			size++;
	  		}
	  	}
	  	String outjson = "{ \"Size\":\""+size+"\",\"PicPath\":\""+tPicPath+"\"}";
	 	out.print(outjson);*/
		String docCode =  request.getParameter("docCode");
	 	int size = 0;
	 	String tPicPath = "";
	 	String outjson = "{ \"Size\":\""+size+"\",\"PicPath\":\""+tPicPath+"\"}";
	 	if(docCode != null){
	 		TransferData sTransferData=new TransferData();
	 		sTransferData.setNameAndValue("docCode", docCode);
	 		VData tVData = new VData();
		 	tVData.add(sTransferData);
		 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 	if(tBusinessDelegate.submitData(tVData, "", "SignatureUI")){
		 		VData vDataResult = tBusinessDelegate.getResult();
		 		if(vDataResult != null && !vDataResult.isEmpty()){
		 			Object tmp = vDataResult.get(0);
		 			if(tmp != null && tmp instanceof String){
		 				tPicPath = (String)tmp;
		 				++size;
		 			}
		 		}
		 	    
            }
	 	}
	 	
	 	outjson = "{ \"Size\":\""+size+"\",\"PicPath\":\""+tPicPath+"\"}";
	 	out.print(outjson);
	}
	
	
%>
