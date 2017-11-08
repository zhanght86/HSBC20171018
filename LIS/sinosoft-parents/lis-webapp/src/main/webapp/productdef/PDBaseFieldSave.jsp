<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDBaseFieldSave.jsp
//程序功能：基础信息字段描述
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
  PDBaseFieldBL pDBaseFieldBL = new PDBaseFieldBL();
  
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
 
 	String tTableCode = request.getParameter("TableCode");//基础表名代码
 	String tTableName = request.getParameter("TableName");//基础表名
 	String tFieldCode = request.getParameter("FieldCode");//基础字段名代码
 	String tFieldName = request.getParameter("FieldName");//基础字段名 		
 	String tFieldType = request.getParameter("FieldType");//字段类型名称
 	String tFieldTypeName = request.getParameter("FieldTypeName");//字段类型
 	String tIsDisplayCode = request.getParameter("IsDisplayCode");//是否显示
 	String tOfficialDesc = request.getParameter("OfficialDesc");//官方字段描述
 	String tBusiDesc = request.getParameter("BusiDesc");//业务字段备注
 	
	transferData.setNameAndValue("TableCode", tTableCode);
	transferData.setNameAndValue("TableName", tTableName);
	transferData.setNameAndValue("FieldCode", tFieldCode);
	transferData.setNameAndValue("FieldName", tFieldName);
	transferData.setNameAndValue("FieldType", tFieldType);
	transferData.setNameAndValue("FieldTypeName", tFieldTypeName);
	transferData.setNameAndValue("IsDisplayCode", tIsDisplayCode);
	transferData.setNameAndValue("OfficialDesc", tOfficialDesc);
	transferData.setNameAndValue("BusiDesc", tBusiDesc);	

  //String values[] = request.getParameterValues("");
  //java.util.ArrayList list = new java.util.ArrayList();
  //for(int i = 0; i < values.length; i++)
  //{
  // list.add(values[i]);
  //}
	//transferData.setNameAndValue("list",list);
	//transferData.setNameAndValue("tableName",request.getParameter("tableName"));
  try
  {
  	// 准备传输数据 VData
  	VData tVData = new VData();
  	tVData.add(tG);
  	tVData.add(transferData);
  	pDBaseFieldBL.submitData(tVData,operator);
  }
  catch(Exception ex)
  {
  	Content = ""+"保存失败，原因是:"+"" + ex.toString();
  	FlagStr = "Fail";
 	}

 	//如果在Catch中发现异常，则不从错误类中提取错误信息
 	if (FlagStr=="")
 	{
  	tError = pDBaseFieldBL.mErrors;
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
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

