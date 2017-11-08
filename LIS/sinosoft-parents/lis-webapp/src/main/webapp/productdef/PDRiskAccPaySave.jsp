<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRiskAccPaySave.jsp
//程序功能：账户型险种定义
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
 

 //PDRiskAccPayBL pDRiskAccPayBL = new PDRiskAccPayBL();
 
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
 String  PAYPLANCODE = request.getParameter("PAYPLANCODE");
 String  INSUACCNO = request.getParameter("INSUACCNO");
 String  RISKCODE = request.getParameter("RISKCODE");
 String  PAYPLANNAME = request.getParameter("PAYPLANNAME");
  String  DEFAULTRATE = request.getParameter("DEFAULTRATE");
 String  NEEDINPUT = request.getParameter("NEEDINPUT");
 String  ACCCREATEPOS = request.getParameter("ACCCREATEPOS");
 String  CALFLAG = request.getParameter("CALFLAG");
 //String  RISKACCPAYNAME = request.getParameter("RISKACCPAYNAME");
 
	String CALCODEMONEY="";
	String  PAYNEEDTOACC = "";
	String  RISKACCPAYNAME = "";
	String  ASCRIPTION = "";
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));

 //String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 


 list.add(PAYPLANCODE);
 list.add(INSUACCNO);
 list.add(RISKCODE);
 list.add(DEFAULTRATE);
  list.add(NEEDINPUT);
 //list.add(ACCCREATEPOS);
 list.add(CALCODEMONEY);
 list.add(CALFLAG);
 list.add(PAYPLANNAME);
 list.add(PAYNEEDTOACC);
 list.add(RISKACCPAYNAME);
 list.add(ASCRIPTION);
 
 //list.add(RISKACCPAYNAME);
transferData.setNameAndValue("list",list);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RISKCODE"));
//String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
//transferData.setNameAndValue("CalCodeType",tCalCodeType);
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDRiskAccPayBL.submitData(tVData,operator);
String busiName="PDRiskAccPayBL"; 
  
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
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  /*
  
  new RiskState().setState(request.getParameter("riskcode"), "契约业务控制->账户交费定义", "1");
 }
 catch(Exception ex)
 {
  Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDRiskAccPayBL.mErrors;
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
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

