<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDAlgoTempLibSave.jsp
//程序功能：算法模板库
//创建日期：2009-3-17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 

 PDchoflagBL tPDchoflagBL = new PDchoflagBL();
 
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
 
 String tRadio[] = request.getParameterValues("InpMulline9GridSel");  
 String tCol1[] = request.getParameterValues("Mulline9Grid1");  
 String tCol4[] = request.getParameterValues("Mulline9Grid4");  
 String tCol5[] = request.getParameterValues("Mulline9Grid5");  
 //参数格式=” Inp+MulLine对象名+Sel” 
 
 PD_LMRiskDutySchema tPd_LmriskdutySchema = new PD_LMRiskDutySchema();
 int count = tRadio.length;
 
 for (int index=0; index< tRadio.length;index++)
 {	 
  if(tRadio[index].equals("1"))
  {
	  tPd_LmriskdutySchema.setRiskCode(tCol1[index]);
	  tPd_LmriskdutySchema.setChoFlag(tCol5[index]);
	  tPd_LmriskdutySchema.setDutyCode(tCol4[index]);
	  
	  break;
  }
}
 
 transferData.setNameAndValue("PD_LMRiskDutySchema",tPd_LmriskdutySchema);
 transferData.setNameAndValue("TableName", request.getParameter("TableName"));
 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  tPDchoflagBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = tPDchoflagBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


