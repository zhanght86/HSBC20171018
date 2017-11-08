<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLRptMAffixListSave.jsp
//程序功能：报案单证信息保存
//创建日期：2005-05-25 12:06
//创建人  ：潘志豪
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
    String tOperate = request.getParameter("Operate");
    if(tG == null)
    {
        loggerDebug("LLRptMAffixListSave","登录信息没有获取!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        loggerDebug("LLRptMAffixListSave","没有获取操作符!!!");
        return;
    }

    String tCaseNo=request.getParameter("RptNo");
    String tCustomerNo=request.getParameter("customerNo");

    //准备数据容器信息
    LLReportAffixSet tLLReportAffixSet=new LLReportAffixSet(); //报案附件表
     //接收MulLine表格中数据集合   
     String tGridNo[] = request.getParameterValues("AffixGridNO");  //得到序号列的所有值
     String tGrid1[] = request.getParameterValues("AffixGrid1"); //得到第1列的所有值
     String tGrid2[] = request.getParameterValues("AffixGrid2"); //得到第2列的所有值
     String tGrid3[] = request.getParameterValues("AffixGrid3"); //得到第2列的所有值
     String tGrid4[] = request.getParameterValues("AffixGrid4"); //得到第2列的所有值
     String tGrid5[] = request.getParameterValues("AffixGrid5"); //得到第2列的所有值

     String tChk[] = request.getParameterValues("InpAffixGridChk");; //参数格式=” Inp+MulLine对象名+Chk”
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
		      LLReportAffixSchema tLLReportAffixSchema=new LLReportAffixSchema();
	          tLLReportAffixSchema.setRptNo(tCaseNo); //赔案号
	          tLLReportAffixSchema.setCustomerNo(tCustomerNo); //客户号   
	          tLLReportAffixSchema.setAffixCode(tGrid1[index]); //单证代码
	          tLLReportAffixSchema.setAffixName(tGrid2[index]); //单证名称
	          tLLReportAffixSchema.setNeedFlag(tGrid3[index]); //是否必需标志           
	          tLLReportAffixSchema.setReadyCount(tGrid4[index]); //单证件数  
	          tLLReportAffixSchema.setProperty(tGrid5[index]); //单证属性标志（提交形式1--原件，2--复印件）
	          tLLReportAffixSet.add(tLLReportAffixSchema);              
	      }
	    }          	
    }
         
    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLReportAffixSet);

//        LLMAffixListUI tLLMAffixListUI = new LLMAffixListUI();
//       if (tLLMAffixListUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMAffixListUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "单证信息保存错误，原因是: " + tLLMAffixListUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        } 
		String busiName="grpLLMAffixListUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLRptMAffixListSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = Content + "单证信息保存错误，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
        loggerDebug("LLRptMAffixListSave","----------LLRptMAffixListSave.jsp End----------");
    
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
