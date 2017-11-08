<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//输出参数
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  VData tVData = new VData();

  LCGrpCustomerImportLogSet tLCGrpCustomerImportLogSet=new LCGrpCustomerImportLogSet(); //团体出险人导入错误日志表
/*
  String tRgtNo[] = request.getParameterValues("DiskErrQueryGrid1");  //立案号
  String tBatchNo[] = request.getParameterValues("DiskErrQueryGrid5"); //批次号
  String tID[] = request.getParameterValues("DiskErrQueryGrid6"); //导入序号

  String tChk[] = request.getParameterValues("InpDiskErrQueryGridChk"); //参数格式=” Inp+MulLine对象名+Chk”

    for(int index=0;index<tChk.length;index++)
    {
       LCGrpCustomerImportLogSchema tLCGrpCustomerImportLogSchema=new LCGrpCustomerImportLogSchema();
       tLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo[index]); //立案号
       tLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo[index]); //批次号
       tLCGrpCustomerImportLogSchema.setID(tID[index]); //导入序号
       tLCGrpCustomerImportLogSet.add(tLCGrpCustomerImportLogSchema);
    }            
*/


     LCGrpCustomerImportLogSchema tLCGrpCustomerImportLogSchema=new LCGrpCustomerImportLogSchema();
     tLCGrpCustomerImportLogSchema.setRgtNo(request.getParameter("tRptNo")); //立案号
     tLCGrpCustomerImportLogSchema.setBatchNo(request.getParameter("tBatchNo")); //批次号
     tLCGrpCustomerImportLogSchema.setID(request.getParameter("tID")); //导入序号
     tLCGrpCustomerImportLogSchema.setOperator(request.getParameter("Operator")); //登陆用户
     tLCGrpCustomerImportLogSet.add(tLCGrpCustomerImportLogSchema);

  tVData.add(tG);
  tVData.add(tLCGrpCustomerImportLogSet);

//  DiskDeleteInsuredBL tDiskDeleteInsuredBL = new DiskDeleteInsuredBL();
//  
//  if(!tDiskDeleteInsuredBL.submitData(tVData,""))
//  {
//      Content = " 删除失败，原因是: " + tDiskDeleteInsuredBL.mErrors.getError(0).errorMessage;
//      FlagStr = "Fail";
//  }
String busiName="grpDiskDeleteInsuredBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "删除失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "删除失败";
		FlagStr = "Fail";				
	}
}

  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "")
  {
    //tError = tDiskDeleteInsuredBL.mErrors;
     tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " 删除成功! ";
      FlagStr = "Succ";
    }
    else                                                                           
    {
      Content = " 删除失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
  //添加各种预处理
  loggerDebug("DiskDeleteInsured","--------------------"+Content);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
