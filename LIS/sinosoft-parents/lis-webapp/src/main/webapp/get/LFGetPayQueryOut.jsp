<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：PayPlanQueryOut.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
	
    VData tVData = new VData();
    LJSGetSchema tLJSGetSchema=new LJSGetSchema();
    tLJSGetSchema.setOtherNo(request.getParameter("bmcert"));
    tLJSGetSchema.setGetNoticeNo(request.getParameter("bmtz"));
    tVData.addElement(tLJSGetSchema);
    tVData.addElement(tGlobalInput);
    
    LFGetPayQueryUI tLFGetPayQueryUI = new LFGetPayQueryUI();
  	if (!tLFGetPayQueryUI.submitData(tVData,"QUERY||MAIN"))
	{
     		 Content = " 查询失败，原因是: " + tLFGetPayQueryUI.mErrors.getError(0).errorMessage;
     		 FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tLFGetPayQueryUI.getResult();

		// 显示

		LJSGetSet mLJSGetSet = new LJSGetSet();
		LCPolSet mLCPolSet=new LCPolSet();
		LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
		TransferData aTransferData = new TransferData();
		
		mLJSGetSet.set((LJSGetSet)tVData.getObjectByObjectName("LJSGetSet",0));
		mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
		mLJSGetDrawSet.set((LJSGetDrawSet)tVData.getObjectByObjectName("LJSGetDrawSet",0));
		aTransferData = (TransferData)tVData.getObjectByObjectName("TransferData",0);
		
		loggerDebug("LFGetPayQueryOut","===1==========="+mLCPolSet.get(1).getPolNo());
	%>
	<script language="javascript">
		 parent.fraInterface.document.all("PolNo").value="<%=mLCPolSet.get(1).getPolNo()%>";
              	 parent.fraInterface.document.all("ContNo").value="<%=mLCPolSet.get(1).getContNo()%>"; 
                 parent.fraInterface.document.all("InsuredNo").value="<%=mLCPolSet.get(1).getInsuredNo()%>"; 
                 parent.fraInterface.document.all("InsuredName").value="<%=mLCPolSet.get(1).getInsuredName()%>";
                 parent.fraInterface.document.all("InsuredSex").value="<%=mLCPolSet.get(1).getInsuredSex()%>";
                 parent.fraInterface.document.all("InsuredBirthday").value="<%=mLCPolSet.get(1).getInsuredBirthday()%>"; 

                 parent.fraInterface.document.all("ValiDate").value="<%=mLCPolSet.get(1).getCValiDate()%>"; 
                 parent.fraInterface.document.all("PaytoDate").value="<%= mLCPolSet.get(1).getPaytoDate()%>";
                 parent.fraInterface.document.all("GetStartDate").value="<%=mLCPolSet.get(1).getGetStartDate()%>"; 
                 parent.fraInterface.document.all("StandPrem").value="<%=mLCPolSet.get(1).getStandPrem()%>";
                 parent.fraInterface.document.all("LastGetDate").value="<%= mLCPolSet.get(1).getLastGetDate()%>";
                 parent.fraInterface.document.all("Mult").value="<%=mLCPolSet.get(1).getMult()%>";
                 parent.fraInterface.document.all("LiveTimes").value="<%= mLCPolSet.get(1).getLiveTimes()%>";
	  	 					 parent.fraInterface.document.all("RiskCode").value="<%= mLCPolSet.get(1).getRiskCode()%>";  
          	 
          	 		parent.fraInterface.document.all("GetDate").value="<%=aTransferData.getValueByName("GetDate")%>";    
          	 		parent.fraInterface.document.all("SumGetMoney").value="<%= aTransferData.getValueByName("SumGetMoney")%>";   
	
	
	</script>
	%>
	<%
		LJSGetDrawSchema mLJSGetDrawSchema = new LJSGetDrawSchema() ;
		for (int i = 1; i <=mLJSGetDrawSet.size(); i++)
		{
		  	mLJSGetDrawSchema = mLJSGetDrawSet.get(i);
		  	//loggerDebug("LFGetPayQueryOut","----"+mLJSGetDrawSchema.getLastGettoDate());
		  		//	  	loggerDebug("LFGetPayQueryOut","----"+mLJSGetDrawSchema.getCurGetToDate());

		    	%>
		   	<script language="javascript">
		   	  parent.fraInterface.SubPayGrid.addOne();
	        parent.fraInterface.fm.SubPayGrid1[<%=i-1%>].value="<%=mLJSGetDrawSchema.getGetNoticeNo()%>";
 	        parent.fraInterface.fm.SubPayGrid2[<%=i-1%>].value="<%=mLJSGetDrawSchema.getDutyCode()%>";
		   		parent.fraInterface.fm.SubPayGrid3[<%=i-1%>].value="<%=mLJSGetDrawSchema.getGetDutyKind()%>";
		   		parent.fraInterface.fm.SubPayGrid4[<%=i-1%>].value="<%=mLJSGetDrawSchema.getGetDutyCode()%>";
		   		parent.fraInterface.fm.SubPayGrid5[<%=i-1%>].value="<%=mLJSGetDrawSchema.getLastGettoDate()%>";
		   		parent.fraInterface.fm.SubPayGrid6[<%=i-1%>].value="<%=mLJSGetDrawSchema.getCurGetToDate()%>";
		   		parent.fraInterface.fm.SubPayGrid7[<%=i-1%>].value="<%=mLJSGetDrawSchema.getGetMoney()%>";
			</script>
			<%
		} // end of for
	} // end of if

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tLFGetPayQueryUI.mErrors;
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
loggerDebug("LFGetPayQueryOut","------end------");
loggerDebug("LFGetPayQueryOut",FlagStr);
loggerDebug("LFGetPayQueryOut",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

