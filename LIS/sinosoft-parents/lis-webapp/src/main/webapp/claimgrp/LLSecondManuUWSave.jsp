<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLSecondManuUWChk.jsp
//程序功能：理赔个人人工核保-----合同或保单核保结论数据提交
//创建日期：2005-01-19 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人 yuejw   更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String transact = request.getParameter("fmtransact");    
    if(tG == null) 
    {
        System.out.println("登录信息没有获取!!!");
        return;
     }
    LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
	tLLCUWMasterSchema.setCaseNo(request.getParameter("tCaseNo"));
	tLLCUWMasterSchema.setBatNo(request.getParameter("tBatNo"));
	tLLCUWMasterSchema.setContNo(request.getParameter("tContNo"));	
	tLLCUWMasterSchema.setPassFlag(request.getParameter("uwState"));
	tLLCUWMasterSchema.setUWIdea(request.getParameter("UWIdea"));
	tLLCUWMasterSchema.setClaimRelFlag(request.getParameter("tClaimRelFlag"));	
	try
	{
        // 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(transact);
		tVData.add(tLLCUWMasterSchema);
        LLSecondManuUWUI tLLSecondManuUWUI = new LLSecondManuUWUI();
       if (tLLSecondManuUWUI.submitData(tVData,transact) == false)
        {
          Content = Content + "信息处理失败，原因是: " + tLLSecondManuUWUI.mErrors.getError(0).errorMessage;
          FlagStr = "FAIL";
        } 
        else 
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }		
	}
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }


%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
