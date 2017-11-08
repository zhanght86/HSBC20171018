
<%
//程序名称：codeQueryKernel.jsp
//程序功能：codeQuery查询功能的核心，暂时用于校验功能的代码查询部分
//创建日期：2002-10-18
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容       
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%!
public String codeQueryKernel(String codeType, Object session) {     
  String strResult = "";
	codeType = codeType.toLowerCase().trim();      
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	VData tData=new VData();
	LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	tLDCodeSchema.setCodeType(codeType);
	tData.add(tLDCodeSchema);
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session;
  tData.add(tGI);
	tBusinessDelegate.submitData(tData,"QUERY||MAIN","CodeQueryUI");
	if (tBusinessDelegate.getCErrors().needDealError()) {
	  loggerDebug("CodeQueryKernel.jsp",tBusinessDelegate.getCErrors().getFirstError()) ;
	}	else {
	  tData.clear();
	  tData=tBusinessDelegate.getResult();
	  strResult=(String)tData.get(0);
	  //strValue=StrTool.unicodeToGBK(strResult);
	  //loggerDebug("CodeQueryKernel.jsp",strResult);
	}
	
	return strResult;
}

//tongmeng 2008-05-07 modify
//修改verifyInput校验策略
public String codeQueryKernel2(String codeType,String codeField,String codeCondition, Object session,String codeValue) {     
  String strResult = "";
  TransferData tTransferData = new TransferData();
	codeType = codeType.toLowerCase().trim();  	      
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	VData tData=new VData();
	LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	tLDCodeSchema.setCodeType(codeType);
	
	tTransferData.setNameAndValue("codeCondition",codeCondition);
	tTransferData.setNameAndValue("conditionField",codeField) ;
	tTransferData.setNameAndValue("codeValue",codeValue) ;
	tData.add(tTransferData);
	tData.add(tLDCodeSchema);
	
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session;
  tData.add(tGI);
	
	tBusinessDelegate.submitData(tData,"QUERY||MAIN","CodeQueryUI");
	if (tBusinessDelegate.getCErrors().needDealError()) {
	  loggerDebug("CodeQueryKernel.jsp",tBusinessDelegate.getCErrors().getFirstError()) ;
	}	else {
	  tData.clear();
	  tData=tBusinessDelegate.getResult();
	  strResult=(String)tData.get(0);
	  //strValue=StrTool.unicodeToGBK(strResult);
	  //loggerDebug("CodeQueryKernel.jsp",strResult);
	}
	
	return strResult;
}
%>
