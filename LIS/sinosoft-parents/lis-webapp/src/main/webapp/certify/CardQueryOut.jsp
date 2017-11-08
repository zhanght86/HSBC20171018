<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：CertifyQueryOut.jsp
//程序功能：
//创建日期：2002-08-18
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
    tLCPolSchema.setRiskVersion(request.getParameter("RiskVersion"));
    tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
    tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
    tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
    tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
	// 没有签单的部分
    tLCPolSchema.setAppFlag( "0" ); 
    
  // 准备传输数据 VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // 数据传输
  CertifyQueryUI tCertifyQueryUI   = new CertifyQueryUI();
	if (!tCertifyQueryUI.submitData(tVData,"QUERY||MAIN"))
	{
      Content = " 查询失败，原因是: " + tCertifyQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tCertifyQueryUI.getResult();
		
		// 显示
		LCPolSet mLCPolSet = new LCPolSet(); 
		mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
		int n = mLCPolSet.size();
		for (int i = 1; i <= n; i++)
		{
		  	LCPolSchema mLCPolSchema = mLCPolSet.get(i);
		   	%>
		   	<script language="javascript">
		   	  parent.fraInterface.PolGrid.addOne("PolGrid")
		   		parent.fraInterface.fm.PolGrid1[<%=i-1%>].value="<%=mLCPolSchema.getProposalNo()%>";
		   		parent.fraInterface.fm.PolGrid2[<%=i-1%>].value="<%=mLCPolSchema.getPrtNo()%>";
		   		parent.fraInterface.fm.PolGrid3[<%=i-1%>].value="<%=mLCPolSchema.getRiskCode()%>";
		   		parent.fraInterface.fm.PolGrid4[<%=i-1%>].value="<%=mLCPolSchema.getAppntName()%>";
		   		parent.fraInterface.fm.PolGrid5[<%=i-1%>].value="<%=mLCPolSchema.getInsuredName()%>";
			</script>
			<%
		} // end of for
	} // end of if
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tCertifyQueryUI.mErrors;
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
loggerDebug("CardQueryOut","------end------");
loggerDebug("CardQueryOut",FlagStr);
loggerDebug("CardQueryOut",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

