<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDDutyGetAliveSave.jsp
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
 

 //PDDutyGetAliveBL pDDutyGetAliveBL = new PDDutyGetAliveBL();
 
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
 
 /*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
transferData.setNameAndValue("list",list);

*/


String tGETDUTYKIND =  request.getParameter("GETDUTYKIND");
String tGETINTV =  request.getParameter("GETINTV");
String tGETSTARTPERIOD =  request.getParameter("GETSTARTPERIOD");
String tGETSTARTUNIT =  request.getParameter("GETSTARTUNIT");
String tSTARTDATECALREF =  request.getParameter("STARTDATECALREF");
String tSTARTDATECALMODE =  request.getParameter("STARTDATECALMODE");
String tENDDATECALREF =  request.getParameter("ENDDATECALREF");
String tENDDATECALMODE =  request.getParameter("ENDDATECALMODE");
String tAFTERGET =  request.getParameter("AFTERGET");
String tCALCODE =  request.getParameter("CALCODE");
String tCalCodeType = request.getParameter("CalCodeSwitchFlag");

String tGETENDPERIOD =  request.getParameter("GETENDPERIOD");
String tGETENDUNIT =  request.getParameter("GETENDUNIT");
String tGETACTIONTYPE =  request.getParameter("GETACTIONTYPE");
String tURGEGETFLAG =  request.getParameter("URGEGETFLAG");
String tMAXGETCOUNTTYPE =  request.getParameter("MAXGETCOUNTTYPE");
String tNeedReCompute = request.getParameter("NeedReCompute");

transferData.setNameAndValue("GETDUTYKIND",tGETDUTYKIND);
transferData.setNameAndValue("GETINTV",tGETINTV);
transferData.setNameAndValue("GETSTARTPERIOD",tGETSTARTPERIOD);
transferData.setNameAndValue("GETSTARTUNIT",tGETSTARTUNIT);
transferData.setNameAndValue("STARTDATECALREF",tSTARTDATECALREF);
transferData.setNameAndValue("STARTDATECALMODE",tSTARTDATECALMODE);
transferData.setNameAndValue("ENDDATECALREF",tENDDATECALREF);
transferData.setNameAndValue("ENDDATECALMODE",tENDDATECALMODE);
transferData.setNameAndValue("AFTERGET",tAFTERGET);
transferData.setNameAndValue("CALCODE",tCALCODE);
transferData.setNameAndValue("CalCodeType",tCalCodeType);

transferData.setNameAndValue("GETENDPERIOD",tGETENDPERIOD);
transferData.setNameAndValue("GETENDUNIT",tGETENDUNIT);
transferData.setNameAndValue("GETACTIONTYPE",tGETACTIONTYPE);
transferData.setNameAndValue("URGEGETFLAG",tURGEGETFLAG);
transferData.setNameAndValue("MAXGETCOUNTTYPE",tMAXGETCOUNTTYPE);
transferData.setNameAndValue("NeedReCompute",tNeedReCompute);

transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("tableName2",request.getParameter("tableName2"));
transferData.setNameAndValue("getDutyCode2",request.getParameter("getDutyCode2"));
transferData.setNameAndValue("getDutyCode",request.getParameter("GetDutyCode"));
transferData.setNameAndValue("dutyType2",request.getParameter("dutyType2"));
transferData.setNameAndValue("IsSubmitData","0");
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));

 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDDutyGetAliveBL.submitData(tVData,operator);
String busiName="PDDutyGetAliveBL";
  
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
  /*
  if(((String)request.getParameter("tableName")).equals("PD_LMDutyGetAlive"))
  {
   	RiskState.setState(request.getParameter("RiskCode"), "产品条款定义->责任生存给付信息", "1");
   }
  else
  {
  	RiskState.setState(request.getParameter("RiskCode"), "理赔业务控制->保障责任赔付明细","1");
  }
 }
 catch(Exception ex)
 {
  Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDDutyGetAliveBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " 保存成功! ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " 保存失败，原因是:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }*/

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.setCalCode("<%=tCALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

