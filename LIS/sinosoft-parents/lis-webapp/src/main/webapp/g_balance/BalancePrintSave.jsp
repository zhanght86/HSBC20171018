<%
/***************************************************************
 * <p>ProName：BalancePrintSave.jsp</p>
 * <p>Title：结算单打印</p>
 * <p>Description：结算单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	String tFileName="";
	String tFilePath1="";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			TransferData mTransferData = new TransferData();
			
			String mGrpContNo = request.getParameter("GrpContNo");
			String mBalanceNo = request.getParameter("BalanceNo");
			
			mTransferData.setNameAndValue("GrpContNo",mGrpContNo);
			mTransferData.setNameAndValue("BalanceNo",mBalanceNo);
			
			tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "BalancePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
				if("PRINT".equals(tOperate)){
					
					TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tFilePath1 = (String) trd.getValueByName("FilePath");
					tFileName = (String) trd.getValueByName("FileName");
					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
