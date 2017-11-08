<%
//**************************************************************************************************
//Name：LLClaimRegisterConclusionSave.jsp
//Function：立案结论信息提交
//Author：zhoulei
//Date: 2005-6-15 16:28
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//LLClaimRegisterConclusionUI tLLClaimRegisterConclusionUI = new LLClaimRegisterConclusionUI();
String busiName="grpLLClaimRegisterConclusionUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

//页面有效
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimRegisterConclusionSave","页面失效,请重新登陆");    
}
else 
{
  String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //获取操作符
    String RgtFlag="0"; //用来区分立案操作和延迟立案操作，0-延迟立案；1—立案操作
    if(request.getParameter("RgtFlag")!=null) 
    { 
       RgtFlag=request.getParameter("RgtFlag");
    }
    loggerDebug("LLClaimRegisterConclusionSave","RgtFlag:"+RgtFlag);    
    String transact = request.getParameter("fmtransact");
    String tRptNo = request.getParameter("RptNo");
    String tAccNo = request.getParameter("AccNo");
    String GrpCustomerNo = request.getParameter("GrpCustomerNo");
    String DeferRgtReason=StrTool.unicodeToGBK(request.getParameter("DeferRgtReason"));   //防止出现乱码 
    String DeferRgtRemark=StrTool.unicodeToGBK(request.getParameter("DeferRgtRemark"));   //防止出现乱码 
    
    if(GrpCustomerNo == null && GrpCustomerNo.equals("")){
    //给付结论信息
    if (request.getParameterValues("PolDutyCodeGridNo") == null)
    {
        loggerDebug("LLClaimRegisterConclusionSave","无给付结论!");
    }
    else
    {
        String tGrid1 [] = request.getParameterValues("PolDutyCodeGrid1"); //保单号
        String tGrid2 [] = request.getParameterValues("PolDutyCodeGrid2"); //给付责任类型
        String tGrid4 [] = request.getParameterValues("PolDutyCodeGrid4"); //给付责任编码
        String tGrid13 [] = request.getParameterValues("PolDutyCodeGrid13"); //得到第13列给付代码的所有值
        String tGrid16 [] = request.getParameterValues("PolDutyCodeGrid16"); //dutycode
      for (int index=0;index < tGrid1.length;index++)
        {
            LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
            tLLClaimDetailSchema.setClmNo(tRptNo);
            tLLClaimDetailSchema.setCaseNo(tRptNo);
            tLLClaimDetailSchema.setPolNo(tGrid1[index]);
            tLLClaimDetailSchema.setGetDutyKind(tGrid2[index]);
            tLLClaimDetailSchema.setGetDutyCode(tGrid4[index]);
            tLLClaimDetailSchema.setDutyCode(tGrid16[index]);
            tLLClaimDetailSchema.setCaseRelaNo(tAccNo);
            tLLClaimDetailSchema.setGiveType(tGrid13[index]);
            mLLClaimDetailSet.add(tLLClaimDetailSchema);
        }
    }
  }
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("ClmNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RgtConclusion",request.getParameter("RgtConclusion") );
    mTransferData.setNameAndValue("NoRgtReason",request.getParameter("NoRgtReason") );
    mTransferData.setNameAndValue("DeferRgtReason",DeferRgtReason );
    mTransferData.setNameAndValue("DeferRgtRemark",DeferRgtRemark );    
    mTransferData.setNameAndValue("RgtState", request.getParameter("rgtType"));    
    mTransferData.setNameAndValue("RgtFlag", RgtFlag);    
    
if(GrpCustomerNo!=null&&!GrpCustomerNo.equals("")){
    mTransferData.setNameAndValue("BeAdjSum", request.getParameter("Grpstandpay"));
    mTransferData.setNameAndValue("GrpFlag", "1"); //团单标记
    }else{
    mTransferData.setNameAndValue("BeAdjSum", request.getParameter("adjpay"));
    mTransferData.setNameAndValue("GrpFlag", "2"); //个单标记
    }
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(mLLClaimDetailSet);

    try
    {
        //数据提交
//        if (!tLLClaimRegisterConclusionUI.submitData(tVData,transact))
//        {
//            Content = " LLClaimRegisterConclusionUI处理数据失败，原因是: " + tLLClaimRegisterConclusionUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
        if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLClaimRegisterConclusionUI处理数据失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLClaimRegisterConclusionUI处理数据失败";
				FlagStr = "Fail";				
			}
		}
        
        else
        {
            tVData.clear();
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
    }
    catch(Exception ex)
    {
        Content = "数据提交LLClaimRegisterConclusionUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }

}

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
