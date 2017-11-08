<%
/***************************************************************
 * <p>ProName：LSQuotOfferPrintSave.jsp</p>
 * <p>Title：报价单打印</p>
 * <p>Description：报价单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tPath = "";
	String tOfferListNo = "";
	
	ExeSQL tExeSQL = new ExeSQL();
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			String tDate = PubFun.getCurrentDate();
			String tYear = tDate.substring(0,4);
			String tMonth = tDate.substring(5,7);
			String tDay = tDate.substring(8);
			
			tPath = tExeSQL.getOneValue("select sysvarvalue from ldsysvar where sysvar = 'PrintPath'"); // /app/print/
			tPath = tPath + "quot/offerlist/"+tYear+"/"+tMonth+"/"+tDay+"/";
			String tOperate = request.getParameter("Operate");
			
			tOfferListNo = request.getParameter("OfferListNo");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tProdType = request.getParameter("ProdType");
			String tPrintType = request.getParameter("PrintType");
			
			TransferData nTransferData = new TransferData();
			nTransferData.setNameAndValue("OfferListNo", tOfferListNo);
			nTransferData.setNameAndValue("QuotNo", tQuotNo);
			nTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			nTransferData.setNameAndValue("QuotType", tQuotType);
			nTransferData.setNameAndValue("ProdType", tProdType);
			nTransferData.setNameAndValue("Path",tPath);
			nTransferData.setNameAndValue("PrintType",tPrintType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(nTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotOfferPrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				String tFileName =(String)tTransferData.getValueByName("FileName");
				
				tFlagStr = "Succ";
				tContent = tPath + tFileName;
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tOfferListNo%>");
</script>
</html>
