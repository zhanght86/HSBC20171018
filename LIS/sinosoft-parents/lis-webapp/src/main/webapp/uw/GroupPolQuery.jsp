<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuUWCho.jsp
//程序功能：续保个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.workflowengine.*"%>
<%
//1-得到所有纪录，显示纪录值
int index=0;
int TempCount=0;
//输出参数
CErrors tError = null;
String FlagStr = "Succ";
String Content = "";

GlobalInput tGlobalInput = new GlobalInput();

tGlobalInput=(GlobalInput)session.getValue("GI");	  
if(tGlobalInput == null) {
	out.println("session has expired");
	return;
}
loggerDebug("GroupPolQuery","GlobalInput.Operator:"+tGlobalInput.Operator);

String tRadio[] = request.getParameterValues("InpPolAddGridSel");  
String tTempClassNum[] = request.getParameterValues("PolAddGridNo");
String tPrtNo[] = request.getParameterValues("PolAddGrid3");
String tPolCode[] = request.getParameterValues("PolAddGrid1");
String tMissionID = request.getParameter("MissionID");
String tSubMissionID = request.getParameter("SubMissionID");

int temp = tRadio.length;
loggerDebug("GroupPolQuery","radiolength:"+temp);
loggerDebug("GroupPolQuery","MissionID:"+tMissionID);

//保单表 
LCPolSchema mLCPolSchema = new LCPolSchema();
LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
if(tTempClassNum!=null) //如果不是空纪录	
{
  	TempCount = tTempClassNum.length; //记录数      
   	loggerDebug("GroupPolQuery","Start query Count="+TempCount);   
  	while(index<TempCount)
  	{
  		loggerDebug("GroupPolQuery","PolNo:"+tPolCode[index]);
  		loggerDebug("GroupPolQuery","radio:"+tRadio[index]);
  		if (!tPolCode[index].equals("")&&tRadio[index].equals("1"))
  		{
  			loggerDebug("GroupPolQuery","GridNO="+tTempClassNum[index]);
  			loggerDebug("GroupPolQuery","Grid 1="+tPolCode[index]);
  			loggerDebug("GroupPolQuery","Radio:"+tRadio[index]);
  			loggerDebug("GroupPolQuery","PrtNo:"+tPrtNo[index]);
  			//查询并显示单条信息
    		LCPolSchema tLCPolSchema = new LCPolSchema();
    		tLCPolSchema.setPolNo(tPolCode[index]);
    		
    		// 准备传输数据 VData2
			VData tVData2 = new VData();
		
			tVData2.addElement(tLCPolSchema);
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.addElement(tLCPolSchema);
			// 数据传输
  			ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  			LCUWMasterQueryUI tLCUWMasterQueryUI = new LCUWMasterQueryUI();  
			if (!tProposalQueryUI.submitData(tVData,"QUERY||MAIN"))
			{
				Content = " 续保人工核保查询失败，原因是: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
      			FlagStr = "Fail";
			}
			else if (!tLCUWMasterQueryUI.submitData(tVData2,"QUERY||MAIN"))
			 {
      			Content = " 续保人工核保查询失败，原因是: " + tLCUWMasterQueryUI.mErrors.getError(0).errorMessage;
      			FlagStr = "Fail2";
      			loggerDebug("GroupPolQuery","查询失败tLCUWMasterQueryUI");
			 }
			else
			{
			     loggerDebug("GroupPolQuery","after all !!!!");			
				tVData.clear();
				tVData = tProposalQueryUI.getResult();
		        tVData2 = tLCUWMasterQueryUI.getResult();
				// 显示
				LCPolSet mLCPolSet = new LCPolSet(); 
				LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
				mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
				mLCUWMasterSet.set((LCUWMasterSet)tVData2.getObjectByObjectName("LCUWMasterSet",0));				
				if (mLCPolSet.size() == 1)
				{
					mLCPolSchema = mLCPolSet.get(1);
					loggerDebug("GroupPolQuery","---LCPolSchema ok ---");
				}
				loggerDebug("GroupPolQuery","---LCUWMasterSet.size()---"+mLCUWMasterSet.size());
				if (mLCUWMasterSet.size() == 1)
				{
					mLCUWMasterSchema = mLCUWMasterSet.get(1);
					loggerDebug("GroupPolQuery","---LCUWMasterSchema ok---");
				}
				loggerDebug("GroupPolQuery","proposalno:"+mLCPolSchema.getProposalNo().trim());
				loggerDebug("GroupPolQuery","mainpolno:"+mLCPolSchema.getMainPolNo());
				
				if(mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo()))
				{
%>
<html>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="ManuUW.js"></SCRIPT>
</head>
<script language="javascript">
//showAddButton();
</script>
</html>			
<%
				}
				else
				{
%>
<html>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupPolQuery.js"></SCRIPT>
</head>
<script language="javascript">
//hideAddButton();
</script>
</html>				
<%
				}									
%>
<script language="javascript">
parent.fraInterface.fm.ProposalNo.value="<%=mLCPolSchema.getPolNo()%>";
parent.fraInterface.fm.RiskCode.value="<%=mLCPolSchema.getRiskCode()%>";
parent.fraInterface.fm.RiskVersion.value="<%=mLCPolSchema.getRiskVersion()%>";
parent.fraInterface.fm.InsuredNo.value="<%=mLCPolSchema.getInsuredNo()%>";
parent.fraInterface.fm.InsuredName.value="<%=mLCPolSchema.getInsuredName()%>";
parent.fraInterface.fm.InsuredSex.value="<%=mLCPolSchema.getInsuredSex()%>";
parent.fraInterface.fm.Mult.value="<%=mLCPolSchema.getMult()%>";
parent.fraInterface.fm.Prem.value="<%=mLCPolSchema.getPrem()%>";
parent.fraInterface.fm.Amnt.value="<%=mLCPolSchema.getAmnt()%>";
parent.fraInterface.fm.PolNo.value="<%=mLCPolSchema.getPolNo()%>";
//parent.fraInterface.fm.PrtNoHide.value="<%=mLCPolSchema.getPrtNo()%>";
//parent.fraInterface.fm.MainPolNoHide.value="<%=mLCPolSchema.getMainPolNo()%>";
//parent.fraInterface.fm.UWGrade.value="<%=mLCUWMasterSchema.getUWGrade()%>";
//parent.fraInterface.fm.AppGrade.value="<%=mLCUWMasterSchema.getAppGrade()%>";
</script>
<%
			} // end of if
  			loggerDebug("GroupPolQuery","---2---"+FlagStr);
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (!FlagStr.equals("Succ"))
			{   
			    loggerDebug("GroupPolQuery","---3---"+FlagStr);
				if(FlagStr.equals("Fail") )
				   tError = tProposalQueryUI.mErrors;
				if(FlagStr.equals("Fail2") )
				   tError = tLCUWMasterQueryUI.mErrors;
				
				if (!tError.needDealError())
				{                          
			    		Content = " 查询成功! ";
			    		FlagStr = "Succ";
				}		
			 	else                                                                           
 			 	{
 			   		Content = " 查询失败，原因是:" + tError.getFirstError();
  			  		FlagStr = "Fail";
  			 	}
 			} 			
    			if(tRadio[index].equals("1"))
      				loggerDebug("GroupPolQuery","the "+index+" line is checked!");
    			else
      				loggerDebug("GroupPolQuery","the "+index+" line is not checked!");
      		}    		
    		index=index+1;
    		loggerDebug("GroupPolQuery","index:"+index);          
	}
}   
%> 
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html> 
