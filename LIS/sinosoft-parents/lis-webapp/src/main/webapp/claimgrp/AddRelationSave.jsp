<%
//**************************************************************************************************
//页面名称: AddRelationSave
//页面功能：连带被保人关系补录
//修改日期：  修改原因/内容：
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("AddRelationSave","页面失效,请重新登陆");    
}
else //页面有效
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("AddRelationSave","-----transact= "+transact);
    String tChk[] = request.getParameterValues("InpInsuredGridChk");
    //***************************************
    //获取页面信息 
    //***************************************  
	String tCustomerNo[] = request.getParameterValues("InsuredGrid1");
	String tRelation[] = request.getParameterValues("InsuredGrid7");
	String tMainCustomerNo = request.getParameter("MainCustomerNo");
	String tPolNo[] = request.getParameterValues("InsuredGrid8");
    int tcontCount = tChk.length;
    for(int i = 0; i < tcontCount; i++){
    	if(tChk[i].equals("1")){
			LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
			tLCInsuredRelatedSchema.setCustomerNo(tCustomerNo[i]);
			tLCInsuredRelatedSchema.setMainCustomerNo(tMainCustomerNo);
			tLCInsuredRelatedSchema.setPolNo(tPolNo[i]);
			tLCInsuredRelatedSchema.setRelationToInsured(tRelation[i]);
			try{
				VData tVData = new VData();
		        tVData.add(tGI);
		        tVData.add(tLCInsuredRelatedSchema);
//		        AddRelationUI tAddRelationUI = new AddRelationUI();
//		       if(!tAddRelationUI.submitData(tVData,transact)){
//		            Content = "提交失败，原因是: " +
//		            tAddRelationUI.mErrors.getError(0).errorMessage;
//		            FlagStr = "Fail";
//		        }
				String busiName="grpAddRelationUI";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "提交失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "提交失败";
						FlagStr = "Fail";				
					}
				}

		        else{
		            Content = "数据提交成功";
		            FlagStr = "Succ";
		        }
			}
			catch(Exception ex){
		        Content = "数据提交失败，原因是:" + ex.toString();
		        FlagStr = "Fail";
		    }
    	}
	}
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
