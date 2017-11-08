<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLRgtMAffixListSaveAll.jsp
//程序功能：单证回销
//创建日期：2005-05-25 12:06
//创建人  ：yuejw
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
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("Operate");
    if(tG == null)
    {
        //loggerDebug("LLRgtMAffixListSaveAll","登录信息没有获取!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLRgtMAffixListSaveAll","没有获取操作符!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    loggerDebug("LLRgtMAffixListSaveAll",tClmNo);
    String tCustomerNo=request.getParameter("CustomerNo");


 String sql = "select customerno, affixno,affixcode,affixname,subflag, needflag ,readycount,shortcount, property , returnflag , affixconclusion ,affixreason from llaffix where  rgtno='"+ tClmNo + "' and (SubFlag is null or SubFlag='1')";
            ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sql);
			int m = tSSRS.getMaxRow();
    //准备数据容器信息
        LLAffixSet tLLAffixSet=new LLAffixSet(); //附件表
    
  

    
	    for(int index=1;index<=m;index++)
	    {
	            
              LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		      tLLAffixSchema.setRgtNo(tClmNo); //赔案号
	          tLLAffixSchema.setCustomerNo(tSSRS.GetText(index, 1)); //客户号   	 
		      tLLAffixSchema.setAffixNo(tSSRS.GetText(index, 2)); //附件号码
		      tLLAffixSchema.setAffixCode(tSSRS.GetText(index, 3)); //单证代码
		      tLLAffixSchema.setReadyCount(tSSRS.GetText(index, 7)); //单证件数
		      tLLAffixSchema.setShortCount(tSSRS.GetText(index, 8)); //缺少件数
		      tLLAffixSchema.setProperty(tSSRS.GetText(index, 9)); //提交形式
		      tLLAffixSchema.setReturnFlag(tSSRS.GetText(index, 10)); //是否退还原件
		      tLLAffixSchema.setAffixConclusion(tSSRS.GetText(index,11)); //齐全标志
		      tLLAffixSchema.setAffixReason(tSSRS.GetText(index, 12)); //不齐全原因
		      tLLAffixSchema.setSubFlag(tSSRS.GetText(index, 5)); //是否提交     
	          tLLAffixSet.add(tLLAffixSchema);              
	         	
    }
         
    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLAffixSet);

//        LLRgtMAffixListUI tLLRgtMAffixListUI = new LLRgtMAffixListUI();
//       if (tLLRgtMAffixListUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLRgtMAffixListUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "单证信息保存错误，原因是: " + tLLRgtMAffixListUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        } 
String busiName="grpLLRgtMAffixListUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLRgtMAffixListSaveAll","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content =  Content + "单证信息保存错误，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "单证信息保存错误";
		   FlagStr = "Fail";				
		} 
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
parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>
</html>    
