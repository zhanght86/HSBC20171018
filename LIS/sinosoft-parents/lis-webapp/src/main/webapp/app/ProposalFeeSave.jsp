<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ProposalFeeSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
  
	String tPolNo = "";
	tPolNo = request.getParameter("PolNo");
  String polType = request.getParameter("polType");
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 暂交费信息
	LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();

	String tTempFeeNo[] = request.getParameterValues("FeeGrid1");

	boolean flag = false;
	int feeCount = tTempFeeNo.length;
	for (int i = 0; i < feeCount; i++)
	{
		if (tTempFeeNo[i] != null && tTempFeeNo[i].trim().length() > 0)
		{
	  		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	
		    tLJTempFeeSchema.setTempFeeNo( tTempFeeNo[i]);
		    tLJTempFeeSchema.setOtherNo( tPolNo );  
		    tLJTempFeeSet.add( tLJTempFeeSchema );
		    flag = true;
		}
	}
	
	if (flag == false)
	{
  		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	    tLJTempFeeSchema.setTempFeeNo( "NULL" );
	    tLJTempFeeSchema.setOtherNo( tPolNo );
	    tLJTempFeeSet.add( tLJTempFeeSchema );
	}	
	
  	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add( tLJTempFeeSet );

  	// 数据传输
	//ProposalFeeUI tProposalFeeUI   = new ProposalFeeUI();
  	String busiName="tbProposalFeeUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (tBusinessDelegate.submitData(tVData,"INSERT||" + polType,busiName) == false)
	{
int n = tBusinessDelegate.getCErrors().getErrorCount();
for (int i = 0; i < n; i++)
loggerDebug("ProposalFeeSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
      Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tBusinessDelegate.getResult();
		
		// 显示
		%>
		<script language='javascript'>
			parent.fraInterface.initFeeGrid();
		</script>
		<%
loggerDebug("ProposalFeeSave","---NOW YOUR CAN DISPLAY IT---");
		LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); 
		mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0));
		int n = mLJTempFeeSet.size();
loggerDebug("ProposalFeeSave","n:"+n);
		for (int i = 1; i <= n; i++)
		{
		  	LJTempFeeSchema mLJTempFeeSchema = mLJTempFeeSet.get(i);
		   	%>
			<script language='javascript'>
		   		parent.fraInterface.fm.FeeGrid1[<%=i-1%>].value="<%=mLJTempFeeSchema.getTempFeeNo()%>";
		   		parent.fraInterface.fm.FeeGrid2[<%=i-1%>].value="<%=mLJTempFeeSchema.getPayMoney()%>";
		   		parent.fraInterface.fm.FeeGrid3[<%=i-1%>].value="<%=mLJTempFeeSchema.getPayDate()%>";
		   		parent.fraInterface.fm.FeeGrid4[<%=i-1%>].value="<%=mLJTempFeeSchema.getEnterAccDate()%>";
			</script>
			<%
		} // end of for
	}	
  
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
	
	loggerDebug("ProposalFeeSave","ProposalFeeSave End:" + Content + "\n" + FlagStr);
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
