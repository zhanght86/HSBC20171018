<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：LDPersonSave.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  //接收信息，并作校验处理。
  //输入参数

  LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();
  LCGrpPolSet mLCGrpPolSet =new LCGrpPolSet();

  TransferData tTransferData = new TransferData();
  GroupPlanRiskUI tGroupPlanRiskUI   = new GroupPlanRiskUI();
  //输出参数
  String FlagStr = "";
  String Content = "";
  String mLoadFlag = "";
  mLoadFlag=request.getParameter("LoadFlag");

  tTransferData.setNameAndValue("LoadFlag",mLoadFlag);


  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("GroupPlanRiskSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("GroupPlanRiskSave","页面失效,请重新登陆");
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
  }
  else //页面有效
  {
   String Operator  = tGI.Operator ;  //保存登陆管理员账号
   String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码

  CErrors tError = null;
  String tBmCert = "";
  loggerDebug("GroupPlanRiskSave","aaaa");
  //后面要执行的动作：添加，修改，删除
  String fmAction=request.getParameter("mOperate");
  String tGrpContNo = request.getParameter("GrpContNo");
  String tCValiDate=request.getParameter("CValiDate");
  String mRiskCode="";
    loggerDebug("GroupPlanRiskSave","fmAction:"+fmAction);
    if(fmAction.equals("INSERT||GROUPRISK"))
    {
        tLCGrpContSchema.setGrpContNo(tGrpContNo);
        tLCGrpContSchema.setPrtNo(tGrpContNo);
        int lineCount = 0;
        String[] arrCount = request.getParameterValues("ContPlanGridNo");
        if (arrCount != null){
        	String[] tChk = request.getParameterValues("InpContPlanGridChk");
        	String[] tRiskCode = request.getParameterValues("ContPlanGrid2");	//险种编码

        	lineCount = arrCount.length; //行数
        		for(int i=0;i<lineCount;i++){
                   LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                   if(!tRiskCode[i].equals(mRiskCode))
                   {
                   mRiskCode=tRiskCode[i];
                   tLCGrpPolSchema.setRiskCode(tRiskCode[i]);
                   tLCGrpPolSchema.setCValiDate(tCValiDate);
                   tLCGrpPolSchema.setPayIntv("0");
                    //因为界面中不会传入个值，故在此将此值赋为1000000
                   tLCGrpPolSchema.setExpPeoples("1000000");
                   tLCGrpPolSchema.setDistriFlag("0");
                   mLCGrpPolSet.add(tLCGrpPolSchema);
                   }
                   else
                   {
                   continue;
                   }
                   }
          }
    }
    if(fmAction.equals("DELETE||GROUPRISK"))
    {
         tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        int lineCount = 0;
        String[] arrCount = request.getParameterValues("ContPlanGridNo");
        if (arrCount != null){
        	String[] tChk = request.getParameterValues("InpContPlanGridChk");
        	String[] tRiskCode = request.getParameterValues("ContPlanGrid2");	//险种编码

        	lineCount = arrCount.length; //行数
        		for(int i=0;i<lineCount;i++){
                   LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                   if(!tRiskCode[i].equals(mRiskCode))
		   {
		     mRiskCode=tRiskCode[i];
		     tLCGrpPolSchema.setRiskCode(tRiskCode[i]);
		     tLCGrpPolSchema.setCValiDate(tCValiDate);
		     tLCGrpPolSchema.setPayIntv("0");
		     //因为界面中不会传入个值，故在此将此值赋为1000000
		     tLCGrpPolSchema.setExpPeoples("1000000");
		     tLCGrpPolSchema.setDistriFlag("0");
		     mLCGrpPolSet.add(tLCGrpPolSchema);
		   }
                   else
                   {
                   continue;
                   }
                   }
          }
    }
        try
         {
            // 准备传输数据 VData
             VData tVData = new VData();

             tVData.add(mLCGrpPolSet);
             tVData.add(tLCGrpContSchema);
             tVData.add(tTransferData);
             tVData.add(tGI);
             tGroupPlanRiskUI.submitData(tVData,fmAction);
            
          }

    catch(Exception ex)
    {
      FlagStr = "Fail";
    }


  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tGroupPlanRiskUI.mErrors;
    if (!tError.needDealError())
    {
    	FlagStr = "Succ";
    }
    else
    {
    	FlagStr = "Fail";
    }
   }
     loggerDebug("GroupPlanRiskSave","aaaa:"+FlagStr);
%>
<script language="javascript">
	parent.fraInterface.fm.all("mFlagStr").value="<%=FlagStr%>";
</script>
<%
} //页面有效区
%>


