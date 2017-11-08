<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CodeInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //接收信息，并作校验处理。
  //输入参数
  FICodeTransSchema tFICodeTransSchema   = null;
  VData tVData = new VData();
  FICodeTransSet tFICodeTransSet = new FICodeTransSet();
  String CodeGridNo[] = request.getParameterValues("CodeGridNo");  //得到序号列的所有值
  String tCodeType[] = request.getParameterValues("CodeGrid1");
  String tCode[] = request.getParameterValues("CodeGrid2");
  String tCodeName[] = request.getParameterValues("CodeGrid3");
  String tCodeAlias[] = request.getParameterValues("CodeGrid4");
  String tOtherSign[] = request.getParameterValues("CodeGrid5");

  int  lineCount = CodeGridNo.length; //得到接受到的记录数

  //CodeUI tCodeUI   = new CodeUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
	
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
  loggerDebug("CodeSave","------transact:"+transact);
  loggerDebug("CodeSave","------lineCount:"+lineCount);
  
  try
  {
	  	for(int i=0; i<lineCount; i++)
	  	{
	  		tFICodeTransSchema   = new FICodeTransSchema();
		    tFICodeTransSchema.setCodeType(tCodeType[i]);
		    tFICodeTransSchema.setCode(tCode[i]);
		    tFICodeTransSchema.setCodeName(tCodeName[i]);
		    tFICodeTransSchema.setCodeAlias(tCodeAlias[i]);
		    tFICodeTransSchema.setOtherSign(tOtherSign[i]);
		    tFICodeTransSet.add(tFICodeTransSchema);
	    }
	 tVData.addElement(tFICodeTransSet);  
	 loggerDebug("CodeSave","------setCodeType:"+tFICodeTransSchema.getCodeType());
	 loggerDebug("CodeSave","------setCode:"+tFICodeTransSchema.getCode());
	 loggerDebug("CodeSave","------setCodeName:"+tFICodeTransSchema.getCodeName());
	 loggerDebug("CodeSave","------setCodeAlias:"+tFICodeTransSchema.getCodeAlias());
	 loggerDebug("CodeSave","------setOtherSign:"+tFICodeTransSchema.getOtherSign());
  // 准备传输数据 VData  	
   uiBusinessDelegate.submitData(tVData,transact,"CodeUI");

  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
    ex.printStackTrace();
  }
  
//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
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
  
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

