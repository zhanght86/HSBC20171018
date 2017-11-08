<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLRgtMAffixListSave.jsp
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
        //loggerDebug("LLRgtMAffixListSave","登录信息没有获取!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLRgtMAffixListSave","没有获取操作符!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    String tCustomerNo=request.getParameter("CustomerNo");

    //准备数据容器信息
        LLAffixSet tLLAffixSet=new LLAffixSet(); //附件表
     //接收MulLine表格中数据集合   
     String tGridNo[] = request.getParameterValues("AffixGridNO");  //得到序号列的所有值
     String tGrid1[] = request.getParameterValues("AffixGrid1"); //附件号码
     String tGrid2[] = request.getParameterValues("AffixGrid2"); //单证代码
     String tGrid4[] = request.getParameterValues("AffixGrid4"); //是否提交       
     String tGrid6[] = request.getParameterValues("AffixGrid6"); //单证件数
     String tGrid7[] = request.getParameterValues("AffixGrid7"); //缺少件数
     String tGrid8[] = request.getParameterValues("AffixGrid8"); //提交形式
     String tGrid9[] = request.getParameterValues("AffixGrid9"); //是否退还原件
     String tGrid10[] = request.getParameterValues("AffixGrid10"); //齐全标志
     String tGrid11[] = request.getParameterValues("AffixGrid11"); //不齐全原因
  

     String tChk[] = request.getParameterValues("InpAffixGridChk");; //参数格式=” Inp+MulLine对象名+Chk”
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
              LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		      tLLAffixSchema.setRgtNo(tClmNo); //赔案号
	          tLLAffixSchema.setCustomerNo(tCustomerNo); //客户号   	 
		      tLLAffixSchema.setAffixNo(tGrid1[index]); //附件号码
		      tLLAffixSchema.setAffixCode(tGrid2[index]); //单证代码
		      tLLAffixSchema.setReadyCount(tGrid6[index]); //单证件数
		      tLLAffixSchema.setShortCount(tGrid7[index]); //缺少件数
		      tLLAffixSchema.setProperty(tGrid8[index]); //提交形式
		      tLLAffixSchema.setReturnFlag(tGrid9[index]); //是否退还原件
		      tLLAffixSchema.setAffixConclusion(tGrid10[index]); //齐全标志
		      tLLAffixSchema.setAffixReason(tGrid11[index]); //不齐全原因
		      tLLAffixSchema.setSubFlag(tGrid4[index]); //是否提交     
	          tLLAffixSet.add(tLLAffixSchema);              
	      }
	    }          	
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
	            //loggerDebug("LLRgtMAffixListSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>    
