<%
//程序名称：PEdorTypeHJSubmit.jsp
//程序功能：
//创建日期：2008-11-23 16:49:22
//创建人  ：CrtHtml程序创建 LH

%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.lang.*"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=gb2312" %>

<%
  //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  CErrors tErrors = new CErrors();
  //后面要执行的动作：添加，修改

  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";

  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
   transact = request.getParameter("fmtransact");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");

    //个人保单批改信息
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    try
    {
       // 准备传输数据 VData
       VData tVData = new VData();
       //保存个人保单信息(保全)
       tVData.addElement(tG);
       tVData.addElement(tLPEdorItemSchema);
       PEdorHJDetailBL tPEdorHJDetailBL = new PEdorHJDetailBL();
       if (!tPEdorHJDetailBL.submitData(tVData,transact))
       {
          tErrors.copyAllErrors(tPEdorHJDetailBL.mErrors);
       }
    }
    catch(Exception ex)
    {ex.printStackTrace();
        Content = "保存失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
   //如果在Catch中发现异常，则不从错误类中提取错误信息
   //页面反馈信息
  if (!tErrors.needDealError())
  {
      FlagStr = "Success";
      Content = "操作成功！";
  }
  else
  {
      FlagStr = "Fail";
      Content = "操作失败，原因是：" + tErrors.getFirstError();
  }
  tErrors = null;
%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>

