<%@page import="com.sinosoft.lis.schema.LDScanObjDefSchema"%>
<%@page import="java.io.File"%>
<%@page import="com.sinosoft.lis.db.LDScanPagesDB"%>
<%@page import="com.sinosoft.lis.schema.LDScanCropDefSchema"%>
<%@page import="com.sinosoft.lis.db.LDScanCropDefDB"%>
<%@page import="com.sinosoft.lis.pubfun.PubSubmit"%>
<%@page import="com.sinosoft.lis.pubfun.MMap"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.schema.LDScanPositionSchema"%>
<%@page import="com.sinosoft.lis.db.LDScanPositionDB"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page language="java" pageEncoding="GBK"%>

<%
	GlobalInput tG=(GlobalInput)session.getValue("GI");
	String oper = request.getParameter("oper");
	if("1".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String objCode = request.getParameter("objCode");
		String objName = request.getParameter("objName");
		objName = java.net.URLDecoder.decode(objName,"UTF-8");
		LDScanPositionDB tLDScanPositionDB = new LDScanPositionDB();
		tLDScanPositionDB.setSubType(subType);
		tLDScanPositionDB.setObjCode(objCode);
		if(tLDScanPositionDB.getInfo()){
			Flag = "F";
			Msg = "该元素已经存在!";
		}else{
		    MMap tMap = new MMap();
			LDScanPositionSchema tLDScanPositionSchema = new LDScanPositionSchema();
			tLDScanPositionSchema.setSubType(subType);
			tLDScanPositionSchema.setObjCode(objCode);
			tLDScanPositionSchema.setPicIndex(0);
			tLDScanPositionSchema.setx1(0);
			tLDScanPositionSchema.sety1(0);
			tLDScanPositionSchema.setx2(0);
			tLDScanPositionSchema.sety2(0);
			tLDScanPositionSchema.setOperator(tG.Operator);
			tLDScanPositionSchema.setMakeDate(PubFun.getCurrentDate());
			tLDScanPositionSchema.setMakeTime(PubFun.getCurrentTime());
			tLDScanPositionSchema.setModifyDate(PubFun.getCurrentDate());
			tLDScanPositionSchema.setModifyTime(PubFun.getCurrentTime());
			if(objName!=null && !"".equals(objName)){
				LDScanObjDefSchema tLDScanObjDefSchema = new LDScanObjDefSchema();
				tLDScanObjDefSchema.setSubType(subType);
				tLDScanObjDefSchema.setObjCode(objCode);
				tLDScanObjDefSchema.setObjName(objName);
				tLDScanObjDefSchema.setOperator(tG.Operator);
				tLDScanObjDefSchema.setMakeDate(PubFun.getCurrentDate());
				tLDScanObjDefSchema.setMakeTime(PubFun.getCurrentTime());
				tMap.put(tLDScanObjDefSchema, "DELETE&INSERT");
			}
		    tMap.put(tLDScanPositionSchema, "INSERT");
		    VData tVData = new VData();
		    tVData.add(tMap);
		    //数据提交
		    PubSubmit tSubmit = new PubSubmit();
		    if (!tSubmit.submitData(tVData, "")) {
				Flag = "F";
				Msg = "数据库提交失败!";
		    }
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}else if("2".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String objCode = request.getParameter("objCode");
		if(objCode!=null && !"".equals(objCode)&&subType!=null && !"".equals(subType)){
			String tSql = "delete from ldscanposition where subtype = '"+subType+"' and objcode = '"+objCode+"'";
			if(!new ExeSQL().execUpdateSQL(tSql)){
				
				Flag = "F";
				Msg = "删除失败!";
			}else {
				tSql = "delete from LDScanObjDef where subtype = '"+subType+"' and objcode = '"+objCode+"'";
				if(!new ExeSQL().execUpdateSQL(tSql)){
					Flag = "F";
					Msg = "删除失败!";
				}
			}
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}else if("3".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String objCode = request.getParameter("objCode");
		String picIndex = request.getParameter("picIndex");
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String x2 = request.getParameter("x2");
		String y2 = request.getParameter("y2");
		LDScanPositionDB tLDScanPositionDB = new LDScanPositionDB();
		tLDScanPositionDB.setSubType(subType);
		tLDScanPositionDB.setObjCode(objCode);
		if(tLDScanPositionDB.getInfo()){
			LDScanPositionSchema tLDScanPositionSchema =tLDScanPositionDB.getSchema();
			tLDScanPositionSchema.setPicIndex(picIndex);
			tLDScanPositionSchema.setx1(x1);
			tLDScanPositionSchema.sety1(y1);
			tLDScanPositionSchema.setx2(x2);
			tLDScanPositionSchema.sety2(y2);
			tLDScanPositionSchema.setModifyDate(PubFun.getCurrentDate());
			tLDScanPositionSchema.setModifyTime(PubFun.getCurrentTime());
		    MMap tMap = new MMap();
		    tMap.put(tLDScanPositionSchema, "UPDATE");
		    VData tVData = new VData();
		    tVData.add(tMap);
		    //数据提交
		    PubSubmit tSubmit = new PubSubmit();
		    if (!tSubmit.submitData(tVData, "")) {
				Flag = "F";
				Msg = "数据库提交失败!";
		    }
		}else{
			Flag = "F";
			Msg = "该元素不存在!";
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}else if("4".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String cropType = request.getParameter("cropType");
		String pageCode = request.getParameter("pageCode");	
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String x2 = request.getParameter("w");
		String y2 = request.getParameter("h");
		LDScanCropDefDB tLDScanCropDefDB = new LDScanCropDefDB();
		tLDScanCropDefDB.setSubType(subType);
		tLDScanCropDefDB.setCropType(cropType);
		LDScanCropDefSchema tLDScanCropDefSchema = null;
		if(tLDScanCropDefDB.getInfo()){
			tLDScanCropDefSchema =tLDScanCropDefDB.getSchema();
		} else{
			tLDScanCropDefSchema = new LDScanCropDefSchema();
			tLDScanCropDefSchema.setSubType(subType);
			tLDScanCropDefSchema.setCropType(cropType);
			tLDScanCropDefSchema.setOperator(tG.Operator);
			tLDScanCropDefSchema.setMakeDate(PubFun.getCurrentDate());
			tLDScanCropDefSchema.setMakeTime(PubFun.getCurrentTime());
		}
		tLDScanCropDefSchema.setPageCode(pageCode);
		tLDScanCropDefSchema.setx1(x1);
		tLDScanCropDefSchema.sety1(y1);
		tLDScanCropDefSchema.setwidth(x2);
		tLDScanCropDefSchema.setheight(y2);
		tLDScanCropDefSchema.setModifyDate(PubFun.getCurrentDate());
		tLDScanCropDefSchema.setModifyTime(PubFun.getCurrentTime());
		MMap tMap = new MMap();
		tMap.put(tLDScanCropDefSchema, "DELETE&INSERT");
		VData tVData = new VData();
		tVData.add(tMap);
		//数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tVData, "")) {
			Flag = "F";
			Msg = "数据库提交失败!";
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}else if("5".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String pageCode = request.getParameter("pageCode");	
		String tRealPath = application.getRealPath("/").replace('\\','/');
		if(tRealPath == null || tRealPath.equals("")){
			tRealPath = session.getServletContext().getRealPath("/");
		}
		ExeSQL tExeSQL = new ExeSQL();
		String tFileName = tExeSQL.getOneValue("select concat(concat(picpath , pagename) , pagesuffix) from LDScanPages where subType = '"+subType+"' and pageCode = '"+pageCode+"'");
		File file = new File(tRealPath+tFileName);
		if(file.delete()){
			String tSql = "delete from LDScanPages where subType = '"+subType+"' and pageCode = '"+pageCode+"'";
			tExeSQL.execUpdateSQL(tSql);
			tSql = "update ldscanmain a set a.numpages = (select count(*) from LDScanPages where subtype = a.subtype) where a.subtype = '"+subType+"'";
			tExeSQL.execUpdateSQL(tSql);
		}else{
			Flag = "F";
			Msg = "删除失败!";
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}else if("6".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String objCode = request.getParameter("objCode");
		String picIndex = request.getParameter("picIndex");
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String x2 = request.getParameter("x2");
		String y2 = request.getParameter("y2");
		LDScanPositionDB tLDScanPositionDB = new LDScanPositionDB();
		tLDScanPositionDB.setSubType(subType);
		tLDScanPositionDB.setObjCode(objCode);
		VData tVData = new VData();
		MMap tMap = new MMap();
		if(tLDScanPositionDB.getInfo()){
			LDScanPositionSchema tLDScanPositionSchema =tLDScanPositionDB.getSchema();
			tLDScanPositionSchema.setPicIndex(picIndex);
			tLDScanPositionSchema.setx1(x1);
			tLDScanPositionSchema.sety1(y1);
			tLDScanPositionSchema.setx2(x2);
			tLDScanPositionSchema.sety2(y2);
			tLDScanPositionSchema.setModifyDate(PubFun.getCurrentDate());
			tLDScanPositionSchema.setModifyTime(PubFun.getCurrentTime());
		    tMap.put(tLDScanPositionSchema, "UPDATE");
		}else{
			LDScanPositionSchema tLDScanPositionSchema = new LDScanPositionSchema();
			tLDScanPositionSchema.setSubType(subType);
			tLDScanPositionSchema.setObjCode(objCode);
			tLDScanPositionSchema.setPicIndex(picIndex);
			tLDScanPositionSchema.setx1(x1);
			tLDScanPositionSchema.sety1(y1);
			tLDScanPositionSchema.setx2(x2);
			tLDScanPositionSchema.sety2(y2);
			tLDScanPositionSchema.setOperator(tG.Operator);
			tLDScanPositionSchema.setMakeDate(PubFun.getCurrentDate());
			tLDScanPositionSchema.setMakeTime(PubFun.getCurrentTime());
			tLDScanPositionSchema.setModifyDate(PubFun.getCurrentDate());
			tLDScanPositionSchema.setModifyTime(PubFun.getCurrentTime());
		    tMap.put(tLDScanPositionSchema, "INSERT");
		}
		tVData.add(tMap);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tVData, "")) {
			Flag = "F";
			Msg = "数据库提交失败!";
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}else if("7".equals(oper)){
		String Flag = "T";
		String Msg = "";
		String subType = request.getParameter("subType");
		String objCode = request.getParameter("objCode");
		String objName = request.getParameter("objName");
		objName = java.net.URLDecoder.decode(objName,"UTF-8");
		loggerDebug("ScanPositionDefSubmit",objName);
		LDScanPositionDB tLDScanPositionDB = new LDScanPositionDB();
		tLDScanPositionDB.setSubType(subType);
		tLDScanPositionDB.setObjCode(objCode);
		if(tLDScanPositionDB.getInfo()){
			LDScanObjDefSchema tLDScanObjDefSchema = new LDScanObjDefSchema();
			tLDScanObjDefSchema.setSubType(subType);
			tLDScanObjDefSchema.setObjCode(objCode);
			tLDScanObjDefSchema.setObjName(objName);
			tLDScanObjDefSchema.setOperator(tG.Operator);
			tLDScanObjDefSchema.setMakeDate(PubFun.getCurrentDate());
			tLDScanObjDefSchema.setMakeTime(PubFun.getCurrentTime());
		    MMap tMap = new MMap();
		    tMap.put(tLDScanObjDefSchema, "DELETE&INSERT");
		    VData tVData = new VData();
		    tVData.add(tMap);
		    //数据提交
		    PubSubmit tSubmit = new PubSubmit();
		    if (!tSubmit.submitData(tVData, "")) {
				Flag = "F";
				Msg = "数据库提交失败!";
		    }
		}else{
			Flag = "F";
			Msg = "该元素不存在!";
		}
	    String outjson = "{ \"Flag\":\""+Flag+"\",\"Msg\":\""+Msg+"\"}";
	    out.print(outjson);
	}
		
		
	
	
%>
