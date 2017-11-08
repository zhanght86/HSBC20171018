<%
//**************************************************************************************************
//Name：LLClaimAuditGiveTypeSave.jsp
//Function：审核保项结论提交
//Author：zhoulei
//Date: 2005-7-1 16:06
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

LLPolTraceSchema tLLPolTraceSchema=new LLPolTraceSchema();


if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimAuditGiveTypeSave","页面失效,请重新登陆");    
}
else //页面有效
{
    //获取操作符
    String tRptNo = request.getParameter("RptNo");
    String tAccNo = request.getParameter("AccNo");
    String tpolstate = request.getParameter("polstate");
    String tPolNo = request.getParameter("PolNo");
    String tGiveType = request.getParameter("GiveType");
    
    
    //给付结论信息
    if (request.getParameterValues("PolDutyCodeGridNo") == null)
    {
        loggerDebug("LLClaimAuditGiveTypeSave","无给付结论!");
    }
    else
    {
    	  String tGrid1 [] = request.getParameterValues("PolDutyCodeGrid1"); //保单号
          String tGrid2 [] = request.getParameterValues("PolDutyCodeGrid2"); //给付责任类型
          String tGrid4 [] = request.getParameterValues("PolDutyCodeGrid4"); //给付责任编码
          String tGrid13 [] = request.getParameterValues("PolDutyCodeGrid13"); //赔付结论
          String tGrid15 [] = request.getParameterValues("PolDutyCodeGrid15"); //拒付原因代码
          String tGrid17 [] = request.getParameterValues("PolDutyCodeGrid17"); //拒付依据
          String tGrid18 [] = request.getParameterValues("PolDutyCodeGrid18"); //特殊备注
          String tGrid21 [] = request.getParameterValues("PolDutyCodeGrid21"); //调整金额
          String tGrid22 [] = request.getParameterValues("PolDutyCodeGrid22"); //调整原因代码
          String tGrid23 [] = request.getParameterValues("PolDutyCodeGrid23"); //调整原因名称
          String tGrid24 [] = request.getParameterValues("PolDutyCodeGrid24"); //调整备注
          String tGrid28 [] = request.getParameterValues("PolDutyCodeGrid28"); //dutycode
          String tGrid29 [] = request.getParameterValues("PolDutyCodeGrid29"); //客户号
          
    	  String tRadio[] = request.getParameterValues("InpPolDutyCodeGridSel");  
          //参数格式=” Inp+MulLine对象名+Sel” 
		  for(int index=0; index< tRadio.length;index++)
		  {
			  if(tRadio[index].equals("1"))
			  {
				    LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
		            tLLClaimDetailSchema.setClmNo(tRptNo);
		            tLLClaimDetailSchema.setCaseNo(tRptNo);
		            tLLClaimDetailSchema.setPolNo(tGrid1[index]);
		            tLLClaimDetailSchema.setGetDutyKind(tGrid2[index]);
		            tLLClaimDetailSchema.setGetDutyCode(tGrid4[index]);
		            tLLClaimDetailSchema.setDutyCode(tGrid28[index]);
		            tLLClaimDetailSchema.setCaseRelaNo(tAccNo);
		            tLLClaimDetailSchema.setGiveType(tGrid13[index]);
		            tLLClaimDetailSchema.setRealPay(tGrid21[index]);
		            tLLClaimDetailSchema.setAdjReason(tGrid22[index]);
//		            tLLClaimDetailSchema.setAdjReasonName(tGrid18[index]);
		            tLLClaimDetailSchema.setAdjRemark(tGrid24[index]);
		            tLLClaimDetailSchema.setGiveReason(tGrid15[index]);
		            tLLClaimDetailSchema.setGiveReasonDesc(tGrid17[index]);
		            tLLClaimDetailSchema.setSpecialRemark(tGrid18[index]);
		            tLLClaimDetailSchema.setCustomerNo(tGrid29[index]);
		            mLLClaimDetailSet.add(tLLClaimDetailSchema);
		            
		            loggerDebug("LLClaimAuditGiveTypeSave","GiveReasonDesc:"+tGrid17[index]);
		            loggerDebug("LLClaimAuditGiveTypeSave","GiveReasonDesc:"+tGrid18[index]);
			  }
		  }



        tLLPolTraceSchema.setPolNo(tPolNo);
        tLLPolTraceSchema.setnewPolState(tpolstate);        
            
    }
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("AccNo", request.getParameter("AccNo"));
    mTransferData.setNameAndValue("ClmNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("ContType","1");   //总单类型,1-个人总投保单,2-集体总单        
    mTransferData.setNameAndValue("ClmState","30");  //赔案状态，20立案，30审核
    mTransferData.setNameAndValue("GiveType", request.getParameter("GiveType"));
    //---------------------------------------jinsh20070515----------------------------------//
    mTransferData.setNameAndValue("AdjReason", request.getParameter("AdjReason"));
    //---------------------------------------jinsh20070515----------------------------------//
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(mLLClaimDetailSet);
    tVData.add(tLLPolTraceSchema);    

    try
    {
//        LLClaimAuditGiveTypeUI tLLClaimAuditGiveTypeUI = new LLClaimAuditGiveTypeUI();
//        //数据提交
//        if (!tLLClaimAuditGiveTypeUI.submitData(tVData,"UPDATE"))
//        {
//            Content = " LLClaimAuditGiveTypeUI处理数据失败，原因是: " + tLLClaimAuditGiveTypeUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="grpLLClaimAuditGiveTypeUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"UPDATE",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "LLClaimAuditGiveTypeUI处理数据失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "LLClaimAuditGiveTypeUI处理数据失败";
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
        Content = "数据提交LLClaimAuditGiveTypeUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }

}

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
