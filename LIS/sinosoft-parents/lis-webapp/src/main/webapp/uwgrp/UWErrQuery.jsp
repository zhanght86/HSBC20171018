<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWErrQuery.jsp
//程序功能：人工核保未过原因查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  //tPolNo = (String)session.getValue("PolNo");
  loggerDebug("UWErrQuery","----");
  tPolNo = request.getParameter("ProposalNoHide");
  loggerDebug("UWErrQuery","--PolNo:---"+tPolNo);

  //核保标志
  //Flag = request.getParameter("Flag");
  //Flag = parent.fm.all("Flag").value;
  //loggerDebug("UWErrQuery","flag:"+Flag);
  
  //核保轨迹表
  //LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
  //tLCUWSubSchema.setProposal(request.getParameter("ProposalNo"));  
  //VData tVData = new VData();  
  //tVData.addElement(tLCUWSubSchema);
  
  
  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    //tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setProposalNo(tPolNo);

  // 准备传输数据 VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // 数据传输
  Flag = "QUERY||LCUWERROR";
  //UWQueryUI tUWQueryUI   = new UWQueryUI();
  String busiName="cbcheckgrpUWQueryUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData,Flag,busiName))
	{
      		Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
		tVData.clear();
		tVData = tBusinessDelegate.getResult();
		
		// 显示
		LCUWSubSet mLCUWSubSet = new LCUWSubSet();
		LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();		
		
		
			mLCUWErrorSet.set((LCUWErrorSet)tVData.getObjectByObjectName("LCUWErrorSet",0));
			int n = mLCUWErrorSet.size();
			loggerDebug("UWErrQuery","n:"+n);
			if (n > 1)
			{
				for (int i = 1; i <= n; i++)
				{
					LCUWErrorSchema mLCUWErrorSchema = mLCUWErrorSet.get(i);
					if (mLCUWErrorSchema.getUWNo() != n)
					{
		  			
%>
		   			<script language="javascript">
		   				parent.fraInterface.fm.UWErrGrid1[<%=i-1%>].value="<%=mLCUWErrorSchema.getProposalNo()%>";
				   		parent.fraInterface.fm.UWErrGrid2[<%=i-1%>].value="<%=mLCUWErrorSchema.getUWNo()%>";
				   		parent.fraInterface.fm.UWErrGrid3[<%=i-1%>].value="<%=mLCUWErrorSchema.getUWError()%>";
				   		parent.fraInterface.fm.UWErrGrid4[<%=i-1%>].value="<%=mLCUWErrorSchema.getModifyDate()%>";
				 
					</script>
<%
					}
				} // end of for
			}
		
	} // end of if
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tBusinessDelegate.getCErrors();
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
loggerDebug("UWErrQuery","------end------");
loggerDebug("UWErrQuery",FlagStr);
loggerDebug("UWErrQuery",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

