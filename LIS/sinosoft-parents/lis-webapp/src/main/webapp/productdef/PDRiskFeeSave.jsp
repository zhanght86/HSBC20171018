<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRiskFeeSave.jsp
//程序功能：账户管理费定义
//创建日期：2009-3-14
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
 

 //PDRiskFeeBL pDRiskFeeBL = new PDRiskFeeBL();
 
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

 String INSUACCNO = request.getParameter("INSUACCNO");
 String PAYINSUACCNAME = request.getParameter("PAYINSUACCNAME");
 String FEECODE = request.getParameter("FEECODE");
 String FEENAME = request.getParameter("FEENAME");
 String PAYPLANCODE = request.getParameter("PAYPLANCODE");
 String FEEKIND = request.getParameter("FEEKIND");
 String FEECALMODE = request.getParameter("FEECALMODE");

 String FEEITEMTYPE = request.getParameter("FEEITEMTYPE");
 String FEETAKEPLACE = request.getParameter("FEETAKEPLACE");
 String FEEPERIOD = request.getParameter("FEEPERIOD");
 String FEESTARTDATE = request.getParameter("FEESTARTDATE");
 String FEECALMODETYPE = request.getParameter("FEECALMODETYPE");
 String FEECALCODE = request.getParameter("FEECALCODE");
 String RISKCODE = request.getParameter("RiskCode");
 
 //String values[] = request.getParameterValues("Mulline9Grid4");
 
 java.util.ArrayList list = new java.util.ArrayList();
 list.add(FEECODE);
 list.add(FEENAME);
 list.add(INSUACCNO);
 list.add(PAYPLANCODE);
 list.add(PAYINSUACCNAME);
 list.add(FEEKIND);
 list.add(FEEITEMTYPE);
 list.add(FEETAKEPLACE);
 list.add(FEECALMODE);
 list.add(FEECALMODETYPE);
 list.add(FEECALCODE);
 list.add(FEEPERIOD);
 list.add(FEESTARTDATE);
 list.add(RISKCODE);
 /*
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }*/
transferData.setNameAndValue("list",list);
 
 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
 transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDRiskFeeBL.submitData(tVData,operator);
String busiName="PDRiskFeeBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	//  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " "+"处理成功!"+" ";
  	FlagStr = "Success";
  	FEECALCODE = (String)tBusinessDelegate.getResult().get(0);
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  /*
 }
 catch(Exception ex)
 {
  Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDRiskFeeBL.mErrors;
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
 }
*/
 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.setCalCode("<%=FEECALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

