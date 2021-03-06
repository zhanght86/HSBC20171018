<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp2Save.jsp
//程序功能：住院信息保存
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
    loggerDebug("LLMedicalFeeInp2Save","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //准备数据容器信息
    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

    //准备后台数据
	tLLFeeMainSchema.setClmNo(request.getParameter("claimNo"));
    tLLFeeMainSchema.setCaseNo(tLLFeeMainSchema.getClmNo());
    tLLFeeMainSchema.setMainFeeNo(request.getParameter("HosMainFeeNo").trim()); //账单号
    tLLFeeMainSchema.setCustomerNo(request.getParameter("custNo")); //客户号
    tLLFeeMainSchema.setHospitalCode(request.getParameter("InHosHosID")); //医院编码
    tLLFeeMainSchema.setFeeType("B");    //住院
            
    //账单明细表
    tLLCaseReceiptSchema.setClmNo(tLLFeeMainSchema.getClmNo());
    tLLCaseReceiptSchema.setCaseNo(tLLFeeMainSchema.getClmNo());
    tLLCaseReceiptSchema.setRgtNo(tLLFeeMainSchema.getClmNo());
    
    tLLCaseReceiptSchema.setMainFeeNo(tLLFeeMainSchema.getMainFeeNo().trim()); //账单号
    tLLCaseReceiptSchema.setFeeDetailNo(request.getParameter("feeDetailNo")); //账单费用明细编号
    tLLCaseReceiptSchema.setCustomerNo(tLLFeeMainSchema.getCustomerNo()); //账单客户号

    tLLCaseReceiptSchema.setFeeItemType(tLLFeeMainSchema.getFeeType());  //项目类型(门诊,住院,费用补偿项目)
    tLLCaseReceiptSchema.setFeeItemCode(request.getParameter("InHosMedFeeType")); //费用类型编码
    tLLCaseReceiptSchema.setFeeItemName(request.getParameter("InHosMedFeeTypeName")); //费用类型名称
    
    tLLCaseReceiptSchema.setCurrency(request.getParameter("InHosMedCurrency"));
    tLLCaseReceiptSchema.setFee(request.getParameter("InHosMedFeeSum")); //费用金额
    tLLCaseReceiptSchema.setSelfAmnt(request.getParameter("selfInHosFeeSum")); //自费/自付金额
    
    tLLCaseReceiptSchema.setStartDate(request.getParameter("InHosStartDate")); //开始日期
    tLLCaseReceiptSchema.setEndDate(request.getParameter("InHosEndDate")); //结束日期
    tLLCaseReceiptSchema.setDayCount(request.getParameter("InHosDayCount1")); //实际住院天数
    tLLCaseReceiptSchema.setDealFlag(request.getParameter("DealFlag"));   //开始日期是否早于出险日期,0是1不是
    tLLCaseReceiptSchema.setAdjReason(request.getParameter("theReason2"));
    tLLCaseReceiptSchema.setAdjRemark(request.getParameter("Remark2"));


    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLFeeMainSchema );
        tVData.add( tLLCaseReceiptSchema );

//        LLMedicalFeeInp1UI tLLMedicalFeeInp1UI = new LLMedicalFeeInp1UI();
//        if (tLLMedicalFeeInp1UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp1UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "信息提交保存，原因是: " + tLLMedicalFeeInp1UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
		String busiName="LLMedicalFeeInp1UI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLMedicalFeeInp2Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
