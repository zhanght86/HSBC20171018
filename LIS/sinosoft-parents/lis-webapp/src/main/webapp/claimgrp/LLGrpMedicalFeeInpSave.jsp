<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLGrpMedicalFeeInpSave.jsp
//程序功能：简易案件门诊/住院信息保存
//创建日期：2005-11-7
//创建人  ：pd
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
<%@page import="com.sinosoft.lis.claimgrp.*"%>
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
    loggerDebug("LLGrpMedicalFeeInpSave","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");

    //准备数据容器信息
    LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
    LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();

    String tChk[] = request.getParameterValues("InpMedFeeInHosInpGridChk");
    String tCustomerNo[] = request.getParameterValues("MedFeeInHosInpGrid1"); //客户号
    String tFeeType[] = request.getParameterValues("MedFeeInHosInpGrid2"); //账单种类
    String tHospitalCode[] = request.getParameterValues("MedFeeInHosInpGrid4"); //医院代码
    String tHospitalName[] = request.getParameterValues("MedFeeInHosInpGrid3"); //医院名称
    String tHospitalGrade[] = request.getParameterValues("MedFeeInHosInpGrid5"); //医院等级
    String tMainFeeNo[] = request.getParameterValues("MedFeeInHosInpGrid6"); //帐单号码
    String tDiseaseName[] = request.getParameterValues("MedFeeInHosInpGrid7"); //疾病名称
    String tDiseaseCode[] = request.getParameterValues("MedFeeInHosInpGrid8"); //疾病代码
    String tClinicStartDate[] = request.getParameterValues("MedFeeInHosInpGrid9"); //开始日期
    String tClinicEndDate[] = request.getParameterValues("MedFeeInHosInpGrid10"); //结束日期
    String tClinicDayCount1[] = request.getParameterValues("MedFeeInHosInpGrid11"); //天数
    String tClinicMedFeeType[] = request.getParameterValues("MedFeeInHosInpGrid13"); //费用类型
    String tClinicMedFeeTypeName[] = request.getParameterValues("MedFeeInHosInpGrid12"); //费用类型name
    String tClinicMedFeeSum[] = request.getParameterValues("MedFeeInHosInpGrid14"); //原始费用
    String tRefuseAmnt[] = request.getParameterValues("MedFeeInHosInpGrid15"); //扣除费用
    String tAdjRemark[] = request.getParameterValues("MedFeeInHosInpGrid21"); //扣除明细
    String tAdjReason[] = request.getParameterValues("MedFeeInHosInpGrid17"); //扣除代码
    String tAdjSum[] = request.getParameterValues("MedFeeInHosInpGrid18"); //合理费用
    String tSecurityAmnt[] = request.getParameterValues("MedFeeInHosInpGrid19"); //社保赔付费用
    String tHospLineAmnt[] = request.getParameterValues("MedFeeInHosInpGrid20"); //住院起付线
    
    String tFeeDetailNo[] = request.getParameterValues("MedFeeInHosInpGrid22"); //账单费用明细

    //准备后台数据
    loggerDebug("LLGrpMedicalFeeInpSave","tChk_length:--->"+tChk.length);
    for(int index=0;index<tChk.length;index++)
    {
      if(tChk[index].equals("1"))
      {
    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    tLLFeeMainSchema.setClmNo(request.getParameter("RptNo")); //赔案号
    tLLFeeMainSchema.setCaseNo(request.getParameter("RptNo")); //分案号
    tLLFeeMainSchema.setMainFeeNo(tMainFeeNo[index]); //帐单号码
    tLLFeeMainSchema.setCustomerNo(tCustomerNo[index]); //客户号
    tLLFeeMainSchema.setHospitalCode(tHospitalCode[index]); //医院代码
    tLLFeeMainSchema.setHospitalName(tHospitalName[index]); //医院名称
    tLLFeeMainSchema.setHospitalGrade(tHospitalGrade[index]); //医院等级
    tLLFeeMainSchema.setFeeType(tFeeType[index]);    //A 门诊 B住院
    tLLFeeMainSet.add(tLLFeeMainSchema);

    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
    tLLCaseReceiptSchema.setClmNo(request.getParameter("RptNo"));
    tLLCaseReceiptSchema.setCaseNo(request.getParameter("RptNo"));
    tLLCaseReceiptSchema.setMainFeeNo(tMainFeeNo[index]);
    tLLCaseReceiptSchema.setRgtNo(request.getParameter("RptNo"));
    tLLCaseReceiptSchema.setFeeItemType(tFeeType[index]);    //A 门诊 B住院
    tLLCaseReceiptSchema.setDiseaseName(tDiseaseName[index]); //疾病名称
    tLLCaseReceiptSchema.setDiseaseCode(tDiseaseCode[index]); //疾病代码
    tLLCaseReceiptSchema.setFeeItemCode(tClinicMedFeeType[index]);   //费用类型
    tLLCaseReceiptSchema.setFeeItemName(tClinicMedFeeTypeName[index]);  //费用类型name
    tLLCaseReceiptSchema.setFee(tClinicMedFeeSum[index]);  //原始费用

    tLLCaseReceiptSchema.setCustomerNo(tCustomerNo[index]); //客户号
    tLLCaseReceiptSchema.setSelfAmnt(tRefuseAmnt[index]);  //扣除费用
    tLLCaseReceiptSchema.setAdjRemark(tAdjRemark[index]);  //扣除原因
    tLLCaseReceiptSchema.setAdjReason(tAdjReason[index]);  //扣除代码
    tLLCaseReceiptSchema.setAdjSum(tAdjSum[index]);  //合理费用
    tLLCaseReceiptSchema.setSecurityAmnt(tSecurityAmnt[index]);  //社保赔付费用

    tLLCaseReceiptSchema.setStartDate(tClinicStartDate[index]);   //开始日期
    tLLCaseReceiptSchema.setEndDate(tClinicEndDate[index]);   //结束日期
    tLLCaseReceiptSchema.setDayCount(tClinicDayCount1[index]);   //天数
    tLLCaseReceiptSchema.setDealFlag("1");   //开始日期是否早于出险日期,0是1不是
    tLLCaseReceiptSchema.setHospLineAmnt(tHospLineAmnt[index]);   //住院起付线
    
    tLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo[index]);   //账单费用明细
    tLLCaseReceiptSet.add(tLLCaseReceiptSchema);
     }
  }
    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLFeeMainSet );
        tVData.add( tLLCaseReceiptSet );

//        LLGrpMedicalFeeInpBL tLLGrpMedicalFeeInpBL = new LLGrpMedicalFeeInpBL();
//        if (tLLGrpMedicalFeeInpBL.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLGrpMedicalFeeInpBL.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "信息提交保存，原因是: " + tLLGrpMedicalFeeInpBL.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
String busiName="grpLLGrpMedicalFeeInpBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLGrpMedicalFeeInpSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content =  Content + "信息提交保存，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
