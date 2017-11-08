<%
//**************************************************************************************************
//Name：LLBnfSave.jsp
//Function：受益人提交
//Author：zhoulei
//Date: 2005-7-6 16:14
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK"%>
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
LLBnfSet mLLBnfSet = new LLBnfSet();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLBnfSave","页面失效,请重新登陆");    
}
else //页面有效
{
    //获取操作符
    String tClmNo = request.getParameter("ClmNo");
    String tBatNo = request.getParameter("BatNo");
    String tPolNo = request.getParameter("polNo");

    String tGrid1 [] = request.getParameterValues("LLBnfGrid1"); //
    String tGrid2 [] = request.getParameterValues("LLBnfGrid2"); //
    String tGrid3 [] = request.getParameterValues("LLBnfGrid3"); //
    String tGrid4 [] = request.getParameterValues("LLBnfGrid4"); //
    String tGrid5 [] = request.getParameterValues("LLBnfGrid5"); //
    String tGrid6 [] = request.getParameterValues("LLBnfGrid6"); //
    String tGrid7 [] = request.getParameterValues("LLBnfGrid7"); //
    String tGrid8 [] = request.getParameterValues("LLBnfGrid8"); //
    String tGrid9 [] = request.getParameterValues("LLBnfGrid9"); //
    String tGrid10 [] = request.getParameterValues("LLBnfGrid10"); //
    String tGrid11 [] = request.getParameterValues("LLBnfGrid11"); //
    String tGrid12 [] = request.getParameterValues("LLBnfGrid12"); //
    String tGrid13 [] = request.getParameterValues("LLBnfGrid13"); //
    String tGrid14 [] = request.getParameterValues("LLBnfGrid14"); //
    String tGrid15 [] = request.getParameterValues("LLBnfGrid15"); //
    String tGrid16 [] = request.getParameterValues("LLBnfGrid16"); //
    String tGrid17 [] = request.getParameterValues("LLBnfGrid17"); //
    String tGrid18 [] = request.getParameterValues("LLBnfGrid18"); //
    String tGrid19 [] = request.getParameterValues("LLBnfGrid19"); //
    String tGrid20 [] = request.getParameterValues("LLBnfGrid20"); //
    String tGrid21 [] = request.getParameterValues("LLBnfGrid21"); //
    String tGrid22 [] = request.getParameterValues("LLBnfGrid22"); //
    String tGrid23 [] = request.getParameterValues("LLBnfGrid23"); //
    String tGrid24 [] = request.getParameterValues("LLBnfGrid24"); //
    String tGrid25 [] = request.getParameterValues("LLBnfGrid25"); //
    String tGrid26 [] = request.getParameterValues("LLBnfGrid26"); //

	for (int index=0;index < tGrid1.length;index++)
    {
        LLBnfSchema tLLBnfSchema = new LLBnfSchema();
        tLLBnfSchema.setClmNo(tGrid1[index]);
        tLLBnfSchema.setCaseNo(tClmNo);
        tLLBnfSchema.setBatNo(tBatNo);
        tLLBnfSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLLBnfSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
        tLLBnfSchema.setContNo(request.getParameter("ContNo"));
        tLLBnfSchema.setBnfKind(request.getParameter("BnfKind"));
        tLLBnfSchema.setPolNo(tGrid2[index]);
        tLLBnfSchema.setInsuredNo(tGrid3[index]);
        tLLBnfSchema.setBnfNo(tGrid4[index]);
        tLLBnfSchema.setCustomerNo(tGrid5[index]);
        tLLBnfSchema.setName(tGrid6[index]);
        tLLBnfSchema.setPayeeNo(tGrid7[index]);
        tLLBnfSchema.setPayeeName(tGrid8[index]);
        tLLBnfSchema.setBnfType(tGrid9[index]);
        tLLBnfSchema.setBnfGrade(tGrid10[index]);
        tLLBnfSchema.setRelationToInsured(tGrid11[index]);
        tLLBnfSchema.setSex(tGrid12[index]);
        tLLBnfSchema.setBirthday(tGrid13[index]);
        tLLBnfSchema.setIDType(tGrid14[index]);
        tLLBnfSchema.setIDNo(tGrid15[index]);
        tLLBnfSchema.setRelationToPayee(tGrid16[index]);
        tLLBnfSchema.setPayeeSex(tGrid17[index]);
        tLLBnfSchema.setPayeeBirthday(tGrid18[index]);
        tLLBnfSchema.setPayeeIDType(tGrid19[index]);
        tLLBnfSchema.setPayeeIDNo(tGrid20[index]);
        tLLBnfSchema.setGetMoney(tGrid21[index]);
        tLLBnfSchema.setBnfLot(tGrid22[index]);
        tLLBnfSchema.setCasePayMode(tGrid23[index]);
        tLLBnfSchema.setCasePayFlag("0"); //保险金支付标志
        tLLBnfSchema.setBankCode(tGrid24[index]);
        tLLBnfSchema.setBankAccNo(tGrid25[index]);
        tLLBnfSchema.setAccName(tGrid26[index]);
        mLLBnfSet.add(tLLBnfSchema);
    }

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("PolNo", request.getParameter("polNo"));
    mTransferData.setNameAndValue("BnfKind", request.getParameter("BnfKind"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(mLLBnfSet);

    try
    {
//        LLBnfUI tLLBnfUI = new LLBnfUI();
//        //数据提交
//        if (!tLLBnfUI.submitData(tVData,"INSERT"))
//        {
//            Content = " LLBnfUI处理数据失败，原因是: " + tLLBnfUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="grpLLBnfUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"INSERT",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "LLBnfUI处理数据失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "LLBnfUI处理数据失败";
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
        Content = "数据提交LLBnfUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }

}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
