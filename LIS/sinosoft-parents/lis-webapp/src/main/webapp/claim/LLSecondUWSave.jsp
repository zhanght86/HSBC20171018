<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLSecondUWSave.jsp
//程序功能：合同单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
  //输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	if(tG == null)
	{
		loggerDebug("LLSecondUWSave","session has expired");
		return;
	}
	
	String tCaseNo = request.getParameter("CaseNo"); //赔案号
	String tInsuredNo = request.getParameter("InsuredNo"); //被保人客户号
	String tSendUW[] = request.getParameterValues("LCContGridChk");
	String tContNo[] = request.getParameterValues("LCContGrid1"); //合同号
	String tAppntNo[] = request.getParameterValues("LCContGrid2"); //投保人客户号码
	String tAppntName[] = request.getParameterValues("LCContGrid3"); //投保人名称
	String tClaimRelFlag[] = request.getParameterValues("LCContGrid7"); //赔案相关标志
	String tHealthImpartNo1[] = request.getParameterValues("LCContGrid8"); //投保书健康告知栏询问号
	String tHealthImpartNo2[] = request.getParameterValues("LCContGrid9"); //体检健康告知栏
	String tNoImpartDesc[] = request.getParameterValues("LCContGrid10"); //对应未告知情况
    String tChk[] = request.getParameterValues("InpLCContGridChk");; //参数格式=” Inp+MulLine对象名+Chk”
	LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
    //从数据库中读取出险人的名称
    SSRS tSSRS = new SSRS();
    //ExeSQL tExeSQL = new ExeSQL();
    String tSql = "";
    tSql = "select name from lcinsured where insuredno = '"+tInsuredNo+"'";
    //tSSRS = tExeSQL.execSQL(tSql);
    TransferData sqlTransferData = new TransferData();
	  VData sqlVData = new VData();
			    sqlTransferData.setNameAndValue("SQL",tSql);
			    sqlVData.add(sqlTransferData);
			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
			  	  {    
			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
			  	       { 
			  				loggerDebug("LLSecondUWSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
			  		   }
			  		   else
			  		   {
			  			 	loggerDebug("LLSecondUWSave","查询失败");				
			  		   }
			  	  }
	tSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
    
    if(tSSRS.getMaxRow()==0)
    {
        return ;
    }
    String tInsuredName  =   tSSRS.GetText(1,1);

    loggerDebug("LLSecondUWSave","-tInsuredName--"+tInsuredName);
     
	if (tChk!=null&&tChk.length>0)
	{
		for(int index=0;index<tChk.length;index++)
		{
			if(tChk[index].equals("1")) 
			{    
			     String mAppntName = "";
			     //ExeSQL tExe = new ExeSQL();
			     SSRS tS = new SSRS();
			     String tSQLPD = " select rgtobj from llregister where rgtno='"+tCaseNo+"'" ;
			     //String tRgtObj = tExe.getOneValue(tSQLPD);
			     TransferData sqlTransferData1 = new TransferData();
	  			VData sqlVData1 = new VData();
			    sqlTransferData1.setNameAndValue("SQL",tSQLPD);
			    sqlVData1.add(sqlTransferData1);
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData1,"getOneValue","ExeSQLUI"))
			  	  {    
			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
			  	       { 
			  				loggerDebug("LLSecondUWSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
			  		   }
			  		   else
			  		   {
			  			 	loggerDebug("LLSecondUWSave","查询失败");				
			  		   }
			  	  }
			  	String tRgtObj =(String)tsqlBusinessDelegate.getResult().get(0);
			     if(tRgtObj.trim().equals("1")||tRgtObj.trim().equals("3"))
			     {			     
				     //从数据库中读取投保人的名称					 				     
				     String tSQL = "";
				     tSQL = "select appntname from lcappnt where appntno = '"+tAppntNo[index]+"'";
				     //tS = tExe.execSQL(tSQL);
				     TransferData sqlTransferData2 = new TransferData();
		  			VData sqlVData2 = new VData();
				    sqlTransferData2.setNameAndValue("SQL",tSQL);
				    sqlVData2.add(sqlTransferData2);
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData2,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLSecondUWSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLSecondUWSave","查询失败");				
				  		   }
				  	  }
				  	tS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
				     if(tS.getMaxRow()==0)
				     {
				        return ;
				     }
				     mAppntName  =   tS.GetText(1,1);
				     loggerDebug("LLSecondUWSave",".................................个险发起二核！...........................");
			     }
			     else
			     {
				     //从数据库中读取投保人的名称					 				     
				     String tSQL = "";
				     tSQL = " select grpname from lcgrpcont a where a.grpcontno in "
				          + " (select grpcontno from lccont b where b.contno = '"+tContNo[index]+"')";
				     //tS = tExe.execSQL(tSQL);
				     TransferData sqlTransferData2 = new TransferData();
		  			VData sqlVData2 = new VData();
				    sqlTransferData2.setNameAndValue("SQL",tSQL);
				    sqlVData2.add(sqlTransferData2);
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData2,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLSecondUWSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLSecondUWSave","查询失败");				
				  		   }
				  	  }
				  	tS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
				     if(tS.getMaxRow()==0)
				     {
				        return ;
				     }
				     mAppntName  =   tS.GetText(1,1);	
				     loggerDebug("LLSecondUWSave","..........................团体赔案下的一个分案发起二核！....................");		     
			     }
			     String tRemark = request.getParameter("Note");	
			     LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();
				 tLLCUWBatchSchema.setCaseNo(tCaseNo); //赔案号
				 tLLCUWBatchSchema.setInsuredNo(tInsuredNo); //被保人客户号
				 tLLCUWBatchSchema.setInsuredName(tInsuredName); //被保人名称
			     tLLCUWBatchSchema.setContNo(tContNo[index]); //合同号
			     tLLCUWBatchSchema.setAppntNo(tAppntNo[index]); //投保人号码			     
			     tLLCUWBatchSchema.setAppntName(mAppntName); //投保人名称
			     tLLCUWBatchSchema.setClaimRelFlag(tClaimRelFlag[index]); //赔案相关标志			     
			     tLLCUWBatchSchema.setHealthImpartNo1(tHealthImpartNo1[index]); //投保书健康告知栏询问号
			     tLLCUWBatchSchema.setHealthImpartNo2(tHealthImpartNo2[index]); //体检健康告知栏
			     tLLCUWBatchSchema.setNoImpartDesc(tNoImpartDesc[index]); //对应未告知情况
			     tLLCUWBatchSchema.setRemark1(tRemark); //出险人目前健康状况介绍 
			     tLLCUWBatchSet.add( tLLCUWBatchSchema );
			}
		}          	
	}
  	
  	//String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("CaseNo",request.getParameter("CaseNo")); //赔案号
    mTransferData.setNameAndValue("ClmNo",request.getParameter("CaseNo")); //赔案号
    mTransferData.setNameAndValue("BatNo",""); //调查批次号 ---后台生成   
    mTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //出险人号码
    mTransferData.setNameAndValue("InsuredName",request.getParameter("InsuredName")); //出险人姓名   
    mTransferData.setNameAndValue("ClaimRelFlag",""); //相关标志----后台处理
    mTransferData.setNameAndValue("MngCom",request.getParameter("ManageCom")); //机构
    mTransferData.setNameAndValue("VIP",""); //VIP标志
    mTransferData.setNameAndValue("StartAgent",""); //星级业务员
    //loggerDebug("LLSecondUWSave","转之前的tRptorName=========>  "+request.getParameter("RptorName"));
    //String tRptorName = StrTool.unicodeToGBK(request.getParameter("RptorName"));
   // String str= new String(request.getParameter("RptorName").toString().getBytes("iso8859_1"), "GBK");
    //tRptorName =  new String(tRptorName.getBytes("ISO-8859-1"),"GBK");
   // loggerDebug("LLSecondUWSave","转换后的1tRptorName=========>  "+str);
   // loggerDebug("LLSecondUWSave","转换后的2tRptorName=========>  "+tRptorName);
    //String tRptorName = request.getParameter("RptorName");
    //xuyunpeng add 为了解决传过来的中文乱码问题
    			     SSRS tS = new SSRS();

    				String tSQL = "";
				     tSQL = " select rgtantname from llregister where rgtno='"+request.getParameter("CaseNo")+"'";
				     //tS = tExe.execSQL(tSQL);
				     TransferData sqlTransferDatax = new TransferData();
		  			VData sqlVDatax = new VData();
				    sqlTransferDatax.setNameAndValue("SQL",tSQL);
				    sqlVDatax.add(sqlTransferDatax);
				  	  if(!tsqlBusinessDelegate.submitData(sqlVDatax,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLSecondUWSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLSecondUWSave","查询失败");				
				  		   }
				  	  }
				  	tS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
				     if(tS.getMaxRow()==0)
				     {
				    	 
				        return ;
				     }
				   String  tRptorName  =   tS.GetText(1,1);	
				     //loggerDebug("LLSecondUWSave","..........................团体赔案下的一个分案发起二核！....................");		     
    
    mTransferData.setNameAndValue("RptorName",tRptorName); //报案人
    mTransferData.setNameAndValue("theCurrentDate",request.getParameter("CurrentDate")); //发起二核日期
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID")); 
    tSql ="select submissionid, activityid from lwmission where activityid in (select activityid from lwactivity where functionid = '10030000') and missionid = '"+request.getParameter("MissionID")+"'";
    TransferData MTransferData = new TransferData();
	VData MVData = new VData();
	MTransferData.setNameAndValue("SQL",tSql);
    MVData.add(MTransferData);
  	  if(!tsqlBusinessDelegate.submitData(MVData,"execSQL","ExeSQLUI"))
  	  {    
  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
  	       { 
  				loggerDebug("LLSecondUWSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
  		   }
  		   else
  		   {
  			 	loggerDebug("LLSecondUWSave","查询失败");				
  		   }
  	  }
  	SSRS MSSRS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
     if(MSSRS.getMaxRow()==0)
     {
    	
        return ;
     }
     String tSubMissionID  =   MSSRS.GetText(1,1);
     String tActivityID  =   MSSRS.GetText(1,2);
     mTransferData.setNameAndValue("ActivityID", tActivityID);
     mTransferData.setNameAndValue("SubMissionID", tSubMissionID);
     
    
	try
	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLLCUWBatchSet);
		tVData.add(mTransferData);		
		tVData.add(tG);
		String wFlag="Create||ScondUWNode"; //创建节点
    	/*ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();		
        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
        {           
            Content = "提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";

        }
		else 
		{
		    Content = " 保存成功! ";
		    FlagStr = "SUCC";
		}*/
		String busiName="tWorkFlowUI";
		//String busiName="CreateLLScondUWNodeBL";
		  String mDescType="保存";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = "提交工作流失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = "提交工作流失败";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"成功! ";
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

