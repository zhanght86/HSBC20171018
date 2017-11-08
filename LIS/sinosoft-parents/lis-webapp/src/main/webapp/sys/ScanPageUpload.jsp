<%@page import="com.sinosoft.lis.schema.LDScanMainSchema"%>
<%@page import="com.sinosoft.lis.db.LDScanMainDB"%>
<%@page import="com.sinosoft.lis.schema.LDScanPagesSchema"%>
<%@page import="java.io.File"%>
<%@page import="com.jspsmart.upload.SmartUpload"%>
<%@page import="com.sinosoft.lis.pubfun.PubSubmit"%>
<%@page import="com.sinosoft.lis.pubfun.MMap"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page language="java" pageEncoding="GBK" %>
<%
	GlobalInput tG=(GlobalInput)session.getValue("GI");
	String subType =  request.getParameter("subType");
	if(subType!=null &&!"".equals(subType)){
		LDScanMainDB tLDScanMainDB = new LDScanMainDB();
		tLDScanMainDB.setSubType(subType);
		if(tLDScanMainDB.getInfo()){
			LDScanMainSchema tLDScanMainSchema = tLDScanMainDB.getSchema();
			SmartUpload su = new SmartUpload();
			su.initialize(pageContext);
			su.setTotalMaxFileSize(5000000);   
		 	su.setAllowedFilesList("jpg,jpeg,gif,png");  
			su.setDeniedFilesList("exe,bat,jsp,htm,html,txt");  
			su.upload();
		
			String tRealPath = application.getRealPath("/").replace('\\','/');
			if(tRealPath == null || tRealPath.equals("")){
				tRealPath = session.getServletContext().getRealPath("/");
			}
			String tPicPath = "xerox/EasyScan/SubType/"+subType+"/";
		    File file = new File(tRealPath+tPicPath);
		    if(!file.exists()){
	        	file.mkdirs();
	        }
		    String tSql = "Select (case when max(PageCode) is not null then max(PageCode) else 0 end)+1 from LDScanPages where subtype= '"+subType+"'";
		    String fileName = su.getFiles().getFile(0).getFileName();
		    String newCode = new ExeSQL().getOneValue(tSql);
		    if(newCode==null ||newCode.equals("")||newCode.equals("0")){
		    	newCode ="1";
		    }
			int docIndex = fileName.lastIndexOf(".");
			int fileLen = fileName.length();
			String tSuffix = fileName.substring(docIndex,fileLen);
			String newFileName = subType+"_"+newCode+"_"+PubFun.getCurrentTime().replaceAll(":", "");
			su.getFiles().getFile(0).saveAs(tRealPath+tPicPath+newFileName+tSuffix);
			LDScanPagesSchema tLDScanPagesSchema = new LDScanPagesSchema();
			tLDScanPagesSchema.setSubType(subType);
			tLDScanPagesSchema.setPageCode(newCode);
			tLDScanPagesSchema.setPageName(newFileName);
			tLDScanPagesSchema.setPageSuffix(tSuffix);
			tLDScanPagesSchema.setPicPath(tPicPath);
			tLDScanPagesSchema.setOperator(tG.Operator);
			tLDScanPagesSchema.setMakeDate(PubFun.getCurrentDate());
			tLDScanPagesSchema.setMakeTime(PubFun.getCurrentTime());
			tLDScanPagesSchema.setModifyDate(PubFun.getCurrentDate());
			tLDScanPagesSchema.setModifyTime(PubFun.getCurrentTime());
		    MMap tMap = new MMap();
		    tMap.put(tLDScanPagesSchema, "INSERT");
		    tLDScanMainSchema.setNumPages(newCode);
		    tMap.put(tLDScanMainSchema, "UPDATE");
		    VData tVData = new VData();
		    tVData.add(tMap);
		    //数据提交
		    PubSubmit tSubmit = new PubSubmit();
		    if (!tSubmit.submitData(tVData, "")) {
		    }
		}
	}
		
	
%>
