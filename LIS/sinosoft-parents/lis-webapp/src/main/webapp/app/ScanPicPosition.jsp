<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.pubfun.PubSubmit"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.pubfun.MMap"%>
<%@page import="com.sinosoft.lis.schema.LDScanPositionSchema"%>
<%@ page language="java" pageEncoding="GBK"%>

<%
String operate = request.getParameter("operate");
String useArea = request.getParameter("useArea");
String subType = request.getParameter("subType");
String viewMode =  request.getParameter("viewMode");
String objName = request.getParameter("objName");
String picIndex=request.getParameter("picIndex");
String picLeft=request.getParameter("picLeft");
String picTop=request.getParameter("picTop");
String winWidth=request.getParameter("winWidth");
String winHeight=request.getParameter("winHeight");
String winLeft=request.getParameter("winLeft");
String winTop=request.getParameter("winTop");
String size = "0";

if(operate.equals("S")){
	picIndex = (picIndex==null||picIndex.equals(""))?"0":picIndex;
	picLeft = (picLeft==null||picLeft.equals(""))?"0":picLeft;
	picTop = (picTop==null||picTop.equals(""))?"0":picTop;
	winWidth = (winWidth==null||winWidth.equals(""))?"0":winWidth;
	winHeight = (winHeight==null||winHeight.equals(""))?"0":winHeight;
	winLeft = (winLeft==null||winLeft.equals(""))?"0":winLeft;
	winTop = (winTop==null||winTop.equals(""))?"0":winTop;
	LDScanPositionSchema tLDScanPositionSchema = new LDScanPositionSchema();
	tLDScanPositionSchema.setUseArea(useArea);
	tLDScanPositionSchema.setSubType(subType);
	tLDScanPositionSchema.setViewMode(viewMode);
	tLDScanPositionSchema.setObjName(objName);
	tLDScanPositionSchema.setPicIndex(picIndex);
	tLDScanPositionSchema.setPicLeft(picLeft);
	tLDScanPositionSchema.setPicTop(picTop);
	tLDScanPositionSchema.setWinWidth(winWidth);
	tLDScanPositionSchema.setWinHeight(winHeight);
	tLDScanPositionSchema.setWinLeft(winLeft);
	tLDScanPositionSchema.setWinTop(winTop);
    MMap tMap = new MMap();
    tMap.put(tLDScanPositionSchema, "DELETE&INSERT");
    VData tVData = new VData();
    tVData.add(tMap);
    //数据提交
    PubSubmit tSubmit = new PubSubmit();
    if (!tSubmit.submitData(tVData, "")) {

    }
    String outjson = "{ \"picIndex\":\""+picIndex+"\",\"picLeft\":\""+picLeft+"\",\"picTop\":\""+picTop+"\",\"winWidth\":\""+winWidth+"\",\"winHeight\":\""+winHeight+"\",\"winLeft\":\""+winLeft+"\",\"winTop\":\""+winTop+"\",\"size\":\""+size+"\"}";
    out.print(outjson);
}else if (operate.equals("Q")){
    String sql ="Select PicIndex,PicLeft,PicTop,WinWidth,WinHeight,WinLeft,WinTop from LDScanPosition where useArea = '"+useArea+"' and subType = '"+subType+"' and objName = '"+objName+"' and viewMode = '"+viewMode+"'";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    TransferData sTransferData=new TransferData();
    sTransferData.setNameAndValue("SQL", sql);
    VData tVData = new VData();
  	tVData.add(sTransferData);
  	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  	if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
      picIndex = tSSRS.GetText(1,1);
      picLeft = tSSRS.GetText(1,2);
      picTop = tSSRS.GetText(1,3);
      winWidth = tSSRS.GetText(1,4);
      winHeight = tSSRS.GetText(1,5);
      winLeft = tSSRS.GetText(1,6);
      winTop = tSSRS.GetText(1,7);
      size = String.valueOf(tSSRS.getMaxRow());
      String outjson = "{ \"picIndex\":\""+picIndex+"\",\"picLeft\":\""+picLeft+"\",\"picTop\":\""+picTop+"\",\"winWidth\":\""+winWidth+"\",\"winHeight\":\""+winHeight+"\",\"winLeft\":\""+winLeft+"\",\"winTop\":\""+winTop+"\",\"size\":\""+size+"\"}";
      out.print(outjson);
  	}		   		      
}else if (operate.equals("A")){
	 String sql ="Select objName,viewMode,PicIndex,PicLeft,PicTop,WinWidth,WinHeight,WinLeft,WinTop from LDScanPosition where useArea = '"+useArea+"' and subType = '"+subType+"'";
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
		      objName = tSSRS.GetText(i,1);
		      viewMode = tSSRS.GetText(i,2);
		      picIndex = tSSRS.GetText(i,3);
		      picLeft = tSSRS.GetText(i,4);
		      picTop = tSSRS.GetText(i,5);
		      winWidth = tSSRS.GetText(i,6);
		      winHeight = tSSRS.GetText(i,7);
		      winLeft = tSSRS.GetText(i,8);
		      winTop = tSSRS.GetText(i,9);
		      outjson += "{ \"objName\":\""+objName+"\",\"viewMode\":\""+viewMode+"\",\"picIndex\":\""+picIndex+"\",\"picLeft\":\""+picLeft+"\",\"picTop\":\""+picTop+"\",\"winWidth\":\""+winWidth+"\",\"winHeight\":\""+winHeight+"\",\"winLeft\":\""+winLeft+"\",\"winTop\":\""+winTop+"\"}";
		      if(i<tSSRS.MaxRow){
		    	  outjson+=",";
		      }
	      }
	      outjson += "]";
	      
	      out.print(outjson);
	  }	
}


%>
