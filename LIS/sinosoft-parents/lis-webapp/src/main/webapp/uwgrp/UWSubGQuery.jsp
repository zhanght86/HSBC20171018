<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSubGQuery.jsp
//程序功能：核保轨迹查询
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

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  //tPolNo = (String)session.getValue("PolNo");
  loggerDebug("UWSubGQuery","----");
  tPolNo = request.getParameter("ProposalNoHide");
  loggerDebug("UWSubGQuery","--PolNo:---"+tPolNo);

  //核保标志
  //Flag = request.getParameter("Flag");
  //Flag = parent.fm.all("Flag").value;
  //loggerDebug("UWSubGQuery","flag:"+Flag);
  
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
  Flag = "QUERY||LCUWSUB";
  UWQueryUI tUWQueryUI   = new UWQueryUI();
	if (!tUWQueryUI.submitData(tVData,Flag))
	{
      		Content = " 查询失败，原因是: " + tUWQueryUI.mErrors.getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
		tVData.clear();
		tVData = tUWQueryUI.getResult();
		
		// 显示
		LCUWSubSet mLCUWSubSet = new LCUWSubSet();
		LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();		
		
		
			mLCUWSubSet.set((LCUWSubSet)tVData.getObjectByObjectName("LCUWSubSet",0));
			int n = mLCUWSubSet.size();
			loggerDebug("UWSubGQuery","n:"+n);
			if (n > 1)
			{
				for (int i = 1; i <= n; i++)
				{
					LCUWSubSchema mLCUWSubSchema = mLCUWSubSet.get(i);
					if (mLCUWSubSchema.getUWNo() != n)
					{
		  			
%>
		   			<script language="javascript">
		   				<!--parent.fraInterface.fm.PolGrid1[<%=i-1%>].value="<%=mLCUWSubSchema.getProposalNo()%>";-->
		   				parent.fraInterface.fm.UWSubGrid1[<%=i-1%>].value="<%=mLCUWSubSchema.getProposalNo()%>"
				   		parent.fraInterface.fm.UWSubGrid2[<%=i-1%>].value="<%=mLCUWSubSchema.getUWNo()%>";
				   		parent.fraInterface.fm.UWSubGrid3[<%=i-1%>].value="<%=mLCUWSubSchema.getState()%>";
				   		parent.fraInterface.fm.UWSubGrid4[<%=i-1%>].value="<%=mLCUWSubSchema.getUWIdea()%>";
				   		parent.fraInterface.fm.UWSubGrid5[<%=i-1%>].value="<%=mLCUWSubSchema.getOperator()%>";
				   		parent.fraInterface.fm.UWSubGrid6[<%=i-1%>].value="<%=mLCUWSubSchema.getModifyDate()%>";
					</script>
<%
					}
				} // end of for
			}
		
		/**
		else
		{
			mLCUWErrorSet.set((LCUWErrorSet)tVData.getObjectByObjectName("LCUWErrorSet",0));
			int n = mLCPolSet.size();
			if (n>0)
			{
				for (int i = 1;i <= n;i++)
				{
				
					LCUWErrorSchema mLCUWErrorSchema = mLCUWErrorSet.get(i);
					
					//输出
				}
			}
		}
		**/
	} // end of if
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tUWQueryUI.mErrors;
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
loggerDebug("UWSubGQuery","------end------");
loggerDebug("UWSubGQuery",FlagStr);
loggerDebug("UWSubGQuery",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

