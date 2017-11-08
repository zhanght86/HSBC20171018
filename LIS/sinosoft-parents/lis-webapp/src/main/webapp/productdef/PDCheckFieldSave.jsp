<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDCheckFieldSave.jsp
//程序功能：投保规则
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
 

 //PDCheckFieldBL pDCheckFieldBL = new PDCheckFieldBL();
 
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
 
 
 String tRiskCode =  request.getParameter("RiskCode");
 String tCheckField =  request.getParameter("CheckField");
 String tSerialNo =  request.getParameter("Serialno");
 String tCalCode =  request.getParameter("CalCode");
 String tMsg =  request.getParameter("Msg");
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
 String tSTANDBYFLAG1 =  request.getParameter("STANDBYFLAG1");
 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
 
 System.out.println("tCalCodeType:"+tCalCodeType);
 /*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 } 
 
transferData.setNameAndValue("list",list);

*/
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
transferData.setNameAndValue("FieldName",tCheckField);
transferData.setNameAndValue("SerialNo",tSerialNo);
transferData.setNameAndValue("CalCode",tCalCode);
transferData.setNameAndValue("Msg",tMsg);
transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("STANDBYFLAG1",tSTANDBYFLAG1);

 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDCheckFieldBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"处理失败，原因是:"+"" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tCalCode = (String)rVData.get(0);
    Content = " "+"处理成功!"+" ";
  	FlagStr = "Succ";
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }


 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.setCalCode("<%=tCalCode%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

