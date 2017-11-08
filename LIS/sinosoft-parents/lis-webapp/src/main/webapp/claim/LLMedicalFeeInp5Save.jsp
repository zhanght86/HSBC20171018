<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp5Save.jsp
//程序功能：其他信息保存
//创建日期：2005-05-19 11:10:36
//创建人  ：续涛
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//准备通用参数
CErrors tError = null;
String FlagStr = "FAIL";
String Content = "";
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//页面有效性
if(tG == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLMedicalFeeInp5Save","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //准备数据容器信息
    LLOtherFactorSchema tLLOtherFactorSchema = new LLOtherFactorSchema();

    //准备后台数据
    tLLOtherFactorSchema.setClmNo(request.getParameter("claimNo"));
    tLLOtherFactorSchema.setCaseNo(tLLOtherFactorSchema.getClmNo());
    tLLOtherFactorSchema.setSerialNo(request.getParameter("SerialNo5").trim());
    tLLOtherFactorSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLOtherFactorSchema.setFactorType(request.getParameter("FactorType"));
    tLLOtherFactorSchema.setFactorCode(request.getParameter("FactorCode"));
    tLLOtherFactorSchema.setFactorName(request.getParameter("FactorName"));
    tLLOtherFactorSchema.setFactorValue(request.getParameter("FactorValue")); //费用金额
    tLLOtherFactorSchema.setCurrency(request.getParameter("FactorCurrency"));
    tLLOtherFactorSchema.setSelfAmnt(request.getParameter("selfFactorFeeSum")); //自费自付金额
    
    tLLOtherFactorSchema.setAdjSum(tLLOtherFactorSchema.getFactorValue()); //调整金额设为初始金额
    tLLOtherFactorSchema.setUnitNo("0");     //机构代码置0
    tLLOtherFactorSchema.setUnitName(request.getParameter("FactorUnitName"));  //机构名称
    tLLOtherFactorSchema.setStartDate(request.getParameter("FactorStateDate")); 
    tLLOtherFactorSchema.setEndDate(request.getParameter("FactorEndDate")); 
    tLLOtherFactorSchema.setFeeItemType("T"); //费用项目类型
    tLLOtherFactorSchema.setAdjReason(request.getParameter("theReason4")); 
    tLLOtherFactorSchema.setAdjRemark(request.getParameter("Remark4")); 

    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLOtherFactorSchema );

//        LLMedicalFeeInp5UI tLLMedicalFeeInp5UI = new LLMedicalFeeInp5UI();
//        if (tLLMedicalFeeInp5UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp5UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "信息提交保存，原因是: " + tLLMedicalFeeInp5UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
		String busiName="LLMedicalFeeInp5UI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLMedicalFeeInp5Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = Content + "信息提交保存，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "信息提交保存";
				   FlagStr = "Fail";				
				} 
		}

        else
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }

    }
    catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
