<%@page import="com.sinosoft.utility.*"%><%@
include file="../jsp/Log4jUI.jsp"%><%@
page import="com.sinosoft.lis.schema.*"%><%@
page import="com.sinosoft.lis.pubfun.*"%><%@
page import="com.sinosoft.lis.pubfun.*"%><%@
page import="java.io.*"%><%!
public String codeQueryKernel(String codeType, Object session) {     
  String strResult = "";
	codeType = codeType.toLowerCase().trim();  
	      
	CodeQueryUI tCodeQueryUI=new CodeQueryUI();
	VData tData=new VData();
	LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	tLDCodeSchema.setCodeType(codeType);
	tData.add(tLDCodeSchema);
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session;
  tData.add(tGI);
	
	tCodeQueryUI.submitData(tData, "QUERY||MAIN");
	if (tCodeQueryUI.mErrors.needDealError()) {
	  loggerDebug("CodeQueryWindowApplet",tCodeQueryUI.mErrors.getFirstError()) ;
	}	else {
	  tData.clear();
	  tData=tCodeQueryUI.getResult();
	  strResult=(String)tData.get(0);
	  //strValue=StrTool.unicodeToGBK(strResult);
	  //loggerDebug("CodeQueryWindowApplet",strResult);
	}
	
	return strResult;
}

public String codeQueryKernel2(String codeType,String codeField,String codeCondition, Object session) {     
  String strResult = "";
  TransferData tTransferData = new TransferData();
	codeType = codeType.toLowerCase().trim();  	      
	CodeQueryUI tCodeQueryUI=new CodeQueryUI();
	VData tData=new VData();
	LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	tLDCodeSchema.setCodeType(codeType);
	
	tTransferData.setNameAndValue("codeCondition",codeCondition);
	tTransferData.setNameAndValue("conditionField",codeField) ;
	
	tData.add(tTransferData);
	tData.add(tLDCodeSchema);
	
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session;
  tData.add(tGI);
	
	tCodeQueryUI.submitData(tData, "QUERY||MAIN");
	if (tCodeQueryUI.mErrors.needDealError()) {
	  loggerDebug("CodeQueryWindowApplet",tCodeQueryUI.mErrors.getFirstError()) ;
	}	else {
	  tData.clear();
	  tData=tCodeQueryUI.getResult();
	  strResult=(String)tData.get(0);
	  //strValue=StrTool.unicodeToGBK(strResult);
	  //loggerDebug("CodeQueryWindowApplet",strResult);
	}
	
	return strResult;
}
%><%

boolean error = false;
GlobalInput gi = null;
try {
    gi = (GlobalInput)session.getValue("GI");
}
catch(Exception ex) { }
if(gi == null || gi.Operator == null || "".equals(gi.Operator)) {
    error = true;
}
if(error){
    out.println("<script language=javascript>");
    out.println("  session = null;");
    out.println("  try {");
    out.println("    CollectGarbage();");
    out.println("  ) catch(ex) {}");
    out.println("  parent.window.location =\"../../indexlis.jsp\";");
    out.println("</script>");
    session.invalidate();
    return;
}

	String strResult = "";
	String codeType="";
	String codeField="";
	String codeConditon ="";
  
	try {
		codeType = request.getParameter( "codeType");
  		if(request.getParameter( "codeField" )!=null)
  			codeField = request.getParameter( "codeField" );
  		if(request.getParameter( "codeConditon" )!=null)
   			codeConditon = request.getParameter( "codeConditon" );
  		loggerDebug("CodeQueryWindowApplet","\n\n---codeQuery Start---\nCodeType:" + codeType);
  	} catch(Exception ex) {
    		loggerDebug("CodeQueryWindowApplet","codeQuery throw Errors!");	
  	}
  
	try {
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		if(codeConditon==null || codeConditon.equals("")||codeField==null || codeField.equals(""))
			strResult = codeQueryKernel(codeType, tGI);
		else
			strResult = codeQueryKernel2(codeType,codeField,codeConditon, tGI);
	} catch(Exception ex) {
		loggerDebug("CodeQueryWindowApplet","codeQuery throw Errors!");	
	}
			
	if (strResult == "") {
  		strResult = "Code Query Fail";
  	}
	
	OutputStream ous = response.getOutputStream();
	ous.write(strResult.getBytes("GBK"));
	ous.flush();
	ous.close();
%>
