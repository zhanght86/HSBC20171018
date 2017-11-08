<%
//程序名称：PEdorTypeAGSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人  Nicholas  更新日期  2005-06   更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息

  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  LJSGetDrawSchema tLJSGetDrawSchema = null;
  LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
  LPPolSchema tLPPolSchema = new LPPolSchema();
  //PEdorAGDetailUI tPEdorAGDetailUI   = new PEdorAGDetailUI();
  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();

  CErrors tError = null;
  //后面要执行的动作：添加

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String Result="";
  String AppObj = "";

    AppObj = request.getParameter("AppObj");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");


  //个人保单批改信息
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));

    //个人险种信息，只可修改领取方式和帐户信息
    tLPPolSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPPolSchema.setEdorType(request.getParameter("EdorType"));
    tLPPolSchema.setPolNo(request.getParameter("PolNo"));
    tLPPolSchema.setGetForm(request.getParameter("GoonGetMethod"));
    if ("0".equals(request.getParameter("GoonGetMethod")))
    {
        tLPPolSchema.setGetBankCode(request.getParameter("GetBankCode"));
        tLPPolSchema.setGetBankAccNo(request.getParameter("GetBankAccNo"));
        tLPPolSchema.setGetAccName(request.getParameter("GetAccName"));
    }

    //仅传入主键，其它信息由后台查出
  String tGridNo[] = request.getParameterValues("PolGridNo");
  String tGetNoticeNo[] =request.getParameterValues("PolGrid4"); //给付通知书号码
  String tDutyCode[] =request.getParameterValues("PolGrid5"); //责任编码
  String tGetDutyCode[] =request.getParameterValues("PolGrid6"); //给付责任编码
  String tGetDutyKind[] =request.getParameterValues("PolGrid7"); //给付责任类型

    int iCount = tGridNo.length;
    System.out.println("--icount:"+iCount);

    try{
      for (int i=0;i<iCount;i++)
      {
        tLJSGetDrawSchema = new LJSGetDrawSchema();
        tLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo[i]);
        tLJSGetDrawSchema.setDutyCode(tDutyCode[i]);
        tLJSGetDrawSchema.setGetDutyKind(tGetDutyKind[i]);
        tLJSGetDrawSchema.setGetDutyCode(tGetDutyCode[i]);
        tLJSGetDrawSchema.setPolNo(request.getParameter("PolNo"));

      tLJSGetDrawSet.add(tLJSGetDrawSchema);
      }
  }
  catch (Exception e)
    {
        System.out.println(e.toString());
    }

try
  {
     //统一更新日期，时间
     String theCurrentDate = PubFun.getCurrentDate();
     String theCurrentTime = PubFun.getCurrentTime();
     //tLPEdorItemSchema.setMakeDate(theCurrentDate);
     //tLPEdorItemSchema.setMakeTime(theCurrentTime);
     tLPEdorItemSchema.setModifyDate(theCurrentDate);
     tLPEdorItemSchema.setModifyTime(theCurrentTime);

     //准备传输数据 VData
     VData tVData = new VData();

        //保存个人保单信息(保全)
        tVData.add(tG);
        tVData.add(tLPEdorItemSchema);
        tVData.add(tLJSGetDrawSet);
        tVData.add(tLPPolSchema);

       //boolean tag = tPEdorAGDetailUI.submitData(tVData,"");
       //boolean tag = tEdorDetailUI.submitData(tVData,"");
        boolean tag;
        if("G".equals(AppObj))
        {
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
        tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tVData.addElement(tLPGrpEdorItemSchema);
        System.out.println("Group submitData");
        tag = tGEdorDetailUI.submitData(tVData, "OPERATION");
        }
        else
        {
        System.out.println("Person submitData");
        tag = tEdorDetailUI.submitData(tVData, "OPERATION");
        }
       if (tag)
     {
          System.out.println("Successful");
     }
     else
     {
          System.out.println("Fail");
     }
    }
catch(Exception ex)
{
    Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
}
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr.equals(""))
  {
    //tError = tPEdorAGDetailUI.mErrors;
    //tError = tEdorDetailUI.mErrors;
    if("G".equals(AppObj))
        tError = tGEdorDetailUI.mErrors;
    else
        tError = tEdorDetailUI.mErrors;
    if (!tError.needDealError())
    {
        FlagStr = "Success";
        Content = " 保存成功";
    }
    else
    {
        FlagStr = "Fail";
        Content = " 保存失败，原因是:" + tError.getFirstError();
    }
  }

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
