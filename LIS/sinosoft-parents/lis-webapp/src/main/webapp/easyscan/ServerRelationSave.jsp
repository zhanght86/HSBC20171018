<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%>
  <%@page import="java.sql.*"%>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//接收信息，并作校验处理。
  loggerDebug("ServerRelationSave","--mulLineSave.jsp-- begin save");
  ServerRelationUI tServerRelationUI   = new ServerRelationUI();

  //输入参数
  ES_COM_SERVERSchema tES_Com_ServerSchema   = new ES_COM_SERVERSchema();
  ES_COM_SERVERSet tES_Com_ServerSet   = new ES_COM_SERVERSet();

  //输出参数
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";
  int checked = 0;
  String pkWherePart = "";

  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
  loggerDebug("ServerRelationSave","--mulLineSave.jsp--transact:"+transact);

  tES_Com_ServerSchema.setManageCom(request.getParameter("ManageCom"));
  tES_Com_ServerSchema.setHost_Name(request.getParameter("Host_Name"));

  loggerDebug("ServerRelationSave","--mulLineSave.jsp--10");
  
  String tGridNo[] = request.getParameterValues("CodeGridNo");  //得到序号列的所有值
  String tChk[] = request.getParameterValues("InpCodeGridChk");    //参数格式="MulLine对象名+Chk"
  String tGrid1  [] = request.getParameterValues("CodeGrid1");  //得到第1列的所有值
  String tGrid2  [] = request.getParameterValues("CodeGrid2");  //得到第2列的所有值
  
  int count = tChk.length; //得到接受到的记录数
  
  loggerDebug("ServerRelationSave","--mulLineSave.jsp--20:" + count + ":" + tGridNo.length);
  
  
  for(int index=0; index< count; index++)
  {
	loggerDebug("ServerRelationSave","--mulLineSave.jsp--30  "+ count);
	if(tChk[index].equals("1"))
	{
		checked = index;
		loggerDebug("ServerRelationSave","--mulLineSave.jsp--31  " + tGrid1[index] + checked);
		//选中的行
		ES_COM_SERVERSchema tServerSchema = new ES_COM_SERVERSchema();
		tServerSchema.setManageCom(tGrid1[index]);
		tServerSchema.setHost_Name(tGrid2[index]);
		loggerDebug("ServerRelationSave","--mulLineSave.jsp--32  " + tGrid1[index]);
		tES_Com_ServerSet.add(tServerSchema);

	}
  }


	loggerDebug("ServerRelationSave","--mulLineSave.jsp--40");
  try
  {
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tES_Com_ServerSchema);
    tVData.add(tES_Com_ServerSet);

    loggerDebug("ServerRelationSave","--ManageSave.jsp--before submitData");
    tServerRelationUI.submitData(tVData,transact);
    loggerDebug("ServerRelationSave","--ManageSave.jsp--after  submitData");
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tServerRelationUI.mErrors;
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
