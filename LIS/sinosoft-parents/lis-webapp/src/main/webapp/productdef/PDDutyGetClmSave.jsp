<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDDutyGetClmSave.jsp
//程序功能：责任给付生存
//创建日期：2009-3-16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>    
<%
 //接收信息，并作校验处理。
 //输入参数
 

 //PDDutyGetClmBL pDDutyGetClmBL = new PDDutyGetClmBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
/*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
transferData.setNameAndValue("list",list);*/
transferData.setNameAndValue("tableName",request.getParameter("tableName"));

transferData.setNameAndValue("tableName2",request.getParameter("tableName2"));
transferData.setNameAndValue("getDutyCode2",request.getParameter("getDutyCode2"));
transferData.setNameAndValue("getDutyCode",request.getParameter("GetDutyCode"));
transferData.setNameAndValue("dutyType2",request.getParameter("dutyType2"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
//transferData.setNameAndValue("IsSubmitData","0");
/*
	document.getElementById('GETDUTYKIND').value= '';
		document.getElementById('INPFLAG').value= '';
		document.getElementById('STATTYPE').value= '';
		document.getElementById('STATTYPEName').value= '';
		document.getElementById('INPFLAGName').value= '';
		document.getElementById('CALCODE').value= '';
*/
String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
String tGETDUTYKIND =  request.getParameter("GETDUTYKIND");
String tSTATTYPE =  request.getParameter("STATTYPE");
String tCALCODE =  request.getParameter("CALCODE");
String tObsPeriod = request.getParameter("ObsPeriod");
String tAFTERGET =  request.getParameter("AfterGet");
String tGETBYHOSDAY = request.getParameter("GetByHosDay");

transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("GETDUTYKIND",tGETDUTYKIND);
transferData.setNameAndValue("STATTYPE",tSTATTYPE);
transferData.setNameAndValue("CALCODE",tCALCODE);
transferData.setNameAndValue("OBSPERIOD",tObsPeriod);
transferData.setNameAndValue("AFTERGET",tAFTERGET);
transferData.setNameAndValue("GETBYHOSDAY",tGETBYHOSDAY);
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDDutyGetClmBL.submitData(tVData,operator);
  String busiName="PDDutyGetClmBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tCALCODE = (String)rVData.get(0);
    Content = " "+"处理成功!"+" ";
  	FlagStr = "Succ";
  	// new RiskState().setState(request.getParameter("RiskCode"), "契约业务控制->险种核保规则", "1");

  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  //RiskState.setState(request.getParameter("RiskCode"), "理赔业务控制->保障责任赔付明细","1");
  
 
 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.setCalCode("<%=tCALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

