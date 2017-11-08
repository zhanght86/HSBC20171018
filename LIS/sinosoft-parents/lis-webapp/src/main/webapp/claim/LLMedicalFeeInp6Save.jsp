<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp5Save.jsp
//程序功能：社保第三方给付信息保存
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
    loggerDebug("LLMedicalFeeInp6Save","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //准备数据容器信息
    LLOtherFactorSchema tLLOtherFactorSchema = new LLOtherFactorSchema();

    //准备后台数据
    tLLOtherFactorSchema.setClmNo(request.getParameter("claimNo"));
    tLLOtherFactorSchema.setCaseNo(request.getParameter("caseNo"));
    tLLOtherFactorSchema.setSerialNo(request.getParameter("SerialNo6").trim());
    tLLOtherFactorSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLOtherFactorSchema.setCurrency(request.getParameter("FeeThreeCurrency"));
    tLLOtherFactorSchema.setFactorType(request.getParameter("FeeThreeType"));
    tLLOtherFactorSchema.setFactorCode(request.getParameter("FeeThreeCode"));
    tLLOtherFactorSchema.setFactorName(request.getParameter("FeeThreeName"));
    tLLOtherFactorSchema.setFactorValue(request.getParameter("FeeThreeValue"));
    tLLOtherFactorSchema.setAdjSum(request.getParameter("FeeThreeValue")); //调整金额设为初始金额
    tLLOtherFactorSchema.setUnitNo("0");     //机构代码置0
    tLLOtherFactorSchema.setUnitName(request.getParameter("FeeThreeUnitName"));  //机构名称
    tLLOtherFactorSchema.setStartDate(request.getParameter("FeeThreeStateDate")); 
    tLLOtherFactorSchema.setEndDate(request.getParameter("FeeThreeEndDate")); 
    tLLOtherFactorSchema.setAdjRemark(request.getParameter("AdjRemark")); 
    tLLOtherFactorSchema.setFeeItemType("D"); //费用项目类型

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
			            //loggerDebug("LLMedicalFeeInp6Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
