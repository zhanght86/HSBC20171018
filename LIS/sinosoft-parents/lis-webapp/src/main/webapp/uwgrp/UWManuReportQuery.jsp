<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuReportQuery.jsp
//程序功能：人工核保核保报告录入
//创建日期：
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
//输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  String tReport = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) {
    out.println("session has expired");
    return;
  }

  //校验处理
  //内容待填充

  //接收信息
  // 投保单列表

  LCUWReportSchema tLCUWReportSchema = new LCUWReportSchema(); //定义一个Schema
  LCUWReportSet tLCUWReportSet = new LCUWReportSet(); //定义一个Set

  String tContNo = request.getParameter("ContNo");
  String tOperator = request.getParameter("Operator");
  String tContent = request.getParameter("Content");

  loggerDebug("UWManuReportQuery","ContNo:"+tContNo);    //需要显示的部分
  loggerDebug("UWManuReportQuery","UWOperator:"+tOperator);
  loggerDebug("UWManuReportQuery","Contente:"+tContent);

  boolean flag = true;


  tLCUWReportSchema.setContNo(tContNo); //给t...Schema赋值
  tLCUWReportSchema.setUWOperator(tOperator);

  //定义DB，验证查询功能
  LCUWReportDB tLCUWReportDB=new LCUWReportDB(); //（xxx为具体类名）
  tLCUWReportDB.setSchema(tLCUWReportSchema); // txxxSchema 中的属性根据需要已经赋值
  tLCUWReportSet=tLCUWReportDB.query();    //根据txxxSchema中不为空的属性查询，返回xxxSet集合
  if(tLCUWReportDB.mErrors.needDealError())
  {
    Content = "查询失败!";
    flag = false;
  }
  else
  {
    if(tLCUWReportSet.size() > 0)
    {
      tLCUWReportSchema=tLCUWReportSet.get(1);
      flag = true;
    }
  }

  if (flag == true)
  {      // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add( tLCUWReportSet);
    tVData.add( tLCUWReportSchema);
    tVData.add( tContent);
    tVData.add( tG );

    // 数据传输
    String busiName="UWReportUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   
    UWReportUI tUWReportUI   = new UWReportUI();
    if (tUWReportUI.submitData(tVData,"QUERY") == false)
    {
      int n = tUWReportUI.mErrors.getErrorCount();
      for (int i = 0; i < n; i++)
        Content = " 自动核保失败，原因是: " + tUWReportUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
    }
    else
    {
    	tVData = new VData();
    	tVData = tUWReportUI.getResult();
    	tReport = (String)tVData.getObjectByObjectName("String",0);
    	if (tReport == null)
    	{
    		tReport = " ";
    	}
    	
    	String Path = application.getRealPath("config//Conversion.config");
	tReport = StrTool.Conversion(tReport,Path);   	
    	loggerDebug("UWManuReportQuery","content111:"+tReport);
%>
<html>
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
				<script language="javascript">
					<!--var treport = Conversion("<%=tReport%>");-->
					<!--parent.fraInterface.fm.Content.value= treport;-->
					parent.fraInterface.fm.Content.value=Conversion("<%=tReport%>");
				</script>
</html>
<%
    }
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail")
    {
      tError = tUWReportUI.mErrors;
      if (!tError.needDealError())
      {
        Content = " 人工核保成功! ";
        FlagStr = "Succ";
      }
      else
      {
        Content = " 人工核保失败，原因是:" + tError.getFirstError();
        FlagStr = "Fail";


      }
    }
  }

%>







