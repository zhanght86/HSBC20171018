<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	String tOper = request.getParameter("oper");
	String subType =  request.getParameter("subType");
	if(tOper.equals("1")){
		if(subType == null || subType.equals("")){
		}else{
			String outJson = "[{\"id\":\""+subType+"\",\"text\":\""+subType+"\", \"iconCls\":\"icon-save\",\"state\":\"open\"";
			String tSql = "select a.objcode,( case when (select objname from LDScanObjDef where subType = a.SubType and objCode = a.ObjCode) is not null then (select objname from LDScanObjDef where subType = a.SubType and objCode = a.ObjCode) else a.ObjCode end) from  ldscanposition a where a.subtype = '"+subType+"'";
		    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		    TransferData sTransferData=new TransferData();
		    sTransferData.setNameAndValue("SQL", tSql);
		    VData tVData = new VData();
		  	tVData.add(sTransferData);
		  	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  	if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
		      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
		      if(tSSRS!=null && tSSRS.getMaxRow()>0){
		    	  outJson+=",\"children\":[";
			      for(int i = 1;i<=tSSRS.MaxRow;i++){
			    	  String nodeCode = tSSRS.GetText(i,1);
			    	  String nodeName = tSSRS.GetText(i,2);
			    	  outJson += "{\"id\":\""+nodeCode+"\",\"text\":\""+nodeName+"\", \"iconCls\":\"icon-search\"}";
				      if(i<tSSRS.MaxRow){
				    	  outJson+=",";
				      }
			      }
			      outJson+="]"; 
		      }
		  	}
		  	outJson+="}]";
			loggerDebug("ScanPositionDefInit",outJson);
		    out.print(outJson);
		}
	}else if(tOper.equals("2")){
		int size = 0;
		String tPicPath = "";
		String tSql = "";
		tSql="select concat(concat(concat(concat('http://',c.serverport), a.picpath), a.pagename) , a.pagesuffix) from LDScanPages a,LDScanMain b,es_server_info c  where a.subtype = b.subtype and b.subtype = '"+subType+"' order by a.pagecode";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	    TransferData sTransferData=new TransferData();
	    sTransferData.setNameAndValue("SQL", tSql);
	    VData tVData = new VData();
	  	tVData.add(sTransferData);
	  	String outJson = "[";
	  	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  	if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
		      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
		      if(tSSRS!=null && tSSRS.getMaxRow()>0){
			      for(int i = 1;i<=tSSRS.MaxRow;i++){
			    	  String picPath = tSSRS.GetText(i,1);
			    	  outJson += "{\"picPath\":\""+picPath+"\"}";
				      if(i<tSSRS.MaxRow){
				    	  outJson+=",";
				      }
			      }
			      outJson+="]"; 
		      }
	  	}
	 	out.print(outJson);
	}else if(tOper.equals("3")){
		 String sql ="Select objCode,picIndex,x1,y1,x2,y2 from LDScanPosition where subType = '"+subType+"'";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 TransferData sTransferData=new TransferData();
		 sTransferData.setNameAndValue("SQL", sql);
		 VData tVData = new VData();
		 tVData.add(sTransferData);
		 tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
		      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
		      String outjson = "[";
		      for(int i = 1;i<=tSSRS.MaxRow;i++){
			      String objCode = tSSRS.GetText(i,1);
			      String picIndex = tSSRS.GetText(i,2);
			      String x1 = tSSRS.GetText(i,3);
			      String y1 = tSSRS.GetText(i,4);
			      String x2 = tSSRS.GetText(i,5);
			      String y2 = tSSRS.GetText(i,6);
			      outjson += "{ \"objCode\":\""+objCode+"\",\"picIndex\":\""+picIndex+"\",\"x1\":\""+x1+"\",\"y1\":\""+y1+"\",\"x2\":\""+x2+"\",\"y2\":\""+y2+"\"}";
			      if(i<tSSRS.MaxRow){
			    	  outjson+=",";
			      }
		      }
		      outjson += "]";
		      
		      out.print(outjson);
		  }	
		
	}else if(tOper.equals("4")){
		String cropType = request.getParameter("cropType");
	    String sql ="Select pageCode,x1,y1,width,height from ldscancropdef where subType = '"+subType+"' and cropType = '"+cropType+"'";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	    TransferData sTransferData=new TransferData();
	    sTransferData.setNameAndValue("SQL", sql);
	    VData tVData = new VData();
	  	tVData.add(sTransferData);
	  	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  	if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
	      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
	      String pageCode = "";
	      String x1 = "";
	      String y1 ="";
	      String width = "";
	      String height = "";
	      String size = "0";
	      if(tSSRS!=null && tSSRS.MaxRow>0){
		      pageCode = tSSRS.GetText(1,1);
		      x1 = tSSRS.GetText(1,2);
		      y1 = tSSRS.GetText(1,3);
		      width = tSSRS.GetText(1,4);
		      height = tSSRS.GetText(1,5);
		      size = String.valueOf(tSSRS.getMaxRow());
	      }
	      String outjson = "{ \"pageCode\":\""+pageCode+"\",\"x1\":\""+x1+"\",\"y1\":\""+y1+"\",\"width\":\""+width+"\",\"height\":\""+height+"\",\"size\":\""+size+"\"}";
	      out.print(outjson);
	   }	
	}
	
%>
