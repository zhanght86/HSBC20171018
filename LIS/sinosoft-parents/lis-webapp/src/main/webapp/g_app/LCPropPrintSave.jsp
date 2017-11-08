<%
/***************************************************************
 * <p>ProName：LCPropPrintSave.jsp</p>
 * <p>Title：投保书生成</p>
 * <p>Description：投保书生成</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCPrintAppntSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	String tGrpPropNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPrintAppntSchema mLCPrintAppntSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			TransferData mTransferData = new TransferData();
			tVData.add(tGI);
			if("CREATE".equals(tOperate)){
				String mOfferListNo = request.getParameter("OfferListNo");
				mLCPrintAppntSchema = new LCPrintAppntSchema();
				mLCPrintAppntSchema.setOfferListNo(mOfferListNo);
				mTransferData.setNameAndValue("GrpIDType","");
				mTransferData.setNameAndValue("GrpID","");
				mTransferData.setNameAndValue("GrpName","");
				tVData.add(mLCPrintAppntSchema);
				tVData.add(mTransferData);
				BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPrintAppntUI")) {
					tContent = tBusinessDelegate.getCErrors().getFirstError();
					tFlagStr = "Fail";
				} else {
					tGrpPropNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tFlagStr = "Succ";
					tContent = "处理成功！";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tGrpPropNo%>");
</script>
</html>
