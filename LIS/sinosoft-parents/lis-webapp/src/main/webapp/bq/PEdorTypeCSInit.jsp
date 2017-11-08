<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@ page language="java" pageEncoding="GBK"%>
<%
	String tOper = request.getParameter("oper");
	String tErrorMsg = "";
	if(tOper.equals("1")){
		String tContNo =  request.getParameter("ContNo");
		String Name = "";
		String IDType= "";
		String IDTypeName= "";
		String IDNo="";
		if(tContNo == null || tContNo.equals("")){
			tErrorMsg = "查询保单号为空！";
		}else{
			String tSql = "Select a.appntname, a.appntidtype, (select codename from ldcode  where codetype = 'idtype' and code = a.appntidtype), a.appntidno from lccont a where contno = '"+tContNo+"'";
		    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		    TransferData sTransferData=new TransferData();
		    sTransferData.setNameAndValue("SQL", tSql);
		    VData tVData = new VData();
		  	tVData.add(sTransferData);
		  	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  	if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
		      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
		      if(tSSRS!=null && tSSRS.getMaxRow()>0){
			      Name = tSSRS.GetText(1,1);
			      IDType = tSSRS.GetText(1,2);
			      IDTypeName = tSSRS.GetText(1,3);
			      IDNo = tSSRS.GetText(1,4);
		      }else{
		    	  tErrorMsg = "查询投保人信息失败！";
		      }
		  	}
		    String outjson = "{ \"Name\":\""+Name+"\",\"IDType\":\""+IDType+"\",\"IDTypeName\":\""+IDTypeName+"\",\"IDNo\":\""+IDNo+"\",\"ErrMsg\":\""+tErrorMsg+"\"}";
		    out.print(outjson);
		}
	}else if(tOper.equals("2")){
		int size = 0;
		String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
		String tPicPath = "";
		String tSql = "";
		tSql="select 'http://'||c.serverport|| a.picpath || a.pagename || a.pagesuffix from es_doc_pages a,es_doc_main b,es_server_info c  where a.docid = b.docid and b.doccode = '"+tEdorAcceptNo+"' and a.hostname = c.hostname and a.pagetype = '7'";
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
	 	out.print(outjson);
	}
%>
