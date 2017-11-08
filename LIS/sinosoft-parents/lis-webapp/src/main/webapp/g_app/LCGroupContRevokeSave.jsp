<%
/***************************************************************
 * <p>ProName：LCGroupContRevokeSave.jsp</p>
 * <p>Title：投保单撤销</p>
 * <p>Description：投保单撤销</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : withxiaoqi
 * @version  : 8.0
 * @date     : 2014-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LCGrpContSchema"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
			String tOperate = request.getParameter("Operate");
			String tGrpPropNo = request.getParameter("GrpContNo");
			String tRemark = request.getParameter("Remark");
			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("GrpPropNo", tGrpPropNo);	
			mLCGrpContSchema.setRemark(tRemark);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(mTransferData);
			tVData.add(mLCGrpContSchema);

			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCGroupContRevokeUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
