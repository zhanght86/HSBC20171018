<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//name :RateCardQueryDetail.jsp
//Creator :zz
//date :2008-06-20
//卡单费率表查询功能
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.list.*"%>

<%
    loggerDebug("RateCardQueryDetail","开始执行RateCardQueryDetail.jsp");  
    
    //输出参数:错误对象，字符串判断标志，显示成功或失败的文字内容
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";


    //保单信息部分，操作类型：返回数据
    String mOperateType="RETURNDATA";
	loggerDebug("RateCardQueryDetail","现在的操作标志是"+mOperateType);
		
	//建立LZCardFeeSchema,获得前一界面传入的单证编码
    RateCardSchema tRateCardSchema = new RateCardSchema();
    tRateCardSchema.setRiskCode(request.getParameter("RiskCode"));
    tRateCardSchema.setPrem(request.getParameter("Prem"));
    
    
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.addElement(tRateCardSchema);
    
    // 数据传输
    RateCardBL tRateCardBL = new RateCardBL();
    if (!tRateCardBL.submitData(tVData,mOperateType))
    {
    	Content = " 查询失败，原因是: " + tRateCardBL.mErrors.getError(0).errorMessage;
      	FlagStr = "Fail";
    }
    else
    {
		tVData.clear();
		tVData = tRateCardBL.getResult();
		RateCardSchema yRateCardSchema = new RateCardSchema();
		RateCardSet yRateCardSet = new RateCardSet();
		yRateCardSet = (RateCardSet)tVData.getObjectByObjectName("RateCardSet",0);
		yRateCardSchema=yRateCardSet.get(1);
		%>
    	<script language="javascript">
	       top.opener.document.all("Riskcode").value = "<%=yRateCardSchema.getRiskCode()%>";
	       top.opener.document.all("ProductPlan").value = "<%=yRateCardSchema.getProductPlan()%>";
	       top.opener.document.all("InsuYear").value = "<%=yRateCardSchema.getInsuYear()%>";
	       top.opener.document.all("InsuYearFlag").value = "<%=yRateCardSchema.getInsuYearFlag()%>";
	       top.opener.document.all("Prem").value = "<%=yRateCardSchema.getPrem()%>";
	       top.opener.document.all("Mult").value = "<%=1%>";
	       
	       top.opener.document.all("HiddenRiskCode").value = "<%=yRateCardSchema.getRiskCode()%>";
	       top.opener.document.all("HiddenPrem").value = "<%=yRateCardSchema.getPrem()%>";
    	</script>
		<%
	}
  if (FlagStr.equals("Fail"))
  {
    tError = tRateCardBL.mErrors;
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
loggerDebug("RateCardQueryDetail","------end------");
loggerDebug("RateCardQueryDetail",FlagStr);
loggerDebug("RateCardQueryDetail",Content);
out.println("<script language=javascript>");
out.println("top.close();");
out.println("</script>");
%>
