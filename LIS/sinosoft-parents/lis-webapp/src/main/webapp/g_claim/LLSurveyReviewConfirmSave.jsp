<%
/***************************************************************
 * <p>ProName：LLSurveyReviewConfirmSave.jsp</p>
 * <p>Title：调查结论录入</p>
 * <p>Description：调查结论录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLInqConclusionSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
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

			TransferData mTransferData = new TransferData(); 
			String  tOperate = request.getParameter("fmtransact");//获得操作状态
			System.out.println("tOperate:"+tOperate);
				//调查过程表 
			LLInqConclusionSchema tLLInqConclusionSchema=new LLInqConclusionSchema();
			tLLInqConclusionSchema.setClmNo(request.getParameter("ClmNo"));//赔案号
			tLLInqConclusionSchema.setBatNo(request.getParameter("InqNo"));//调查序号
			tLLInqConclusionSchema.setConNo(request.getParameter("ConNo"));//结论序号
			tLLInqConclusionSchema.setInqConclusion(request.getParameter("InqConclusion"));
			tLLInqConclusionSchema.setFiniFlag(request.getParameter("CasetypeCode"));
	//	mTransferData.setNameAndValue("FeeTypeOld",request.getParameter("FeeTypeOld"));//调查费用类型，未修改前的
	//		System.out.println("FeeTypeOld"+request.getParameter("FeeTypeOld"));
	// 准备传输数据 VData
			VData tVData = new VData();	

			tVData.add(tGI);
			tVData.add(tLLInqConclusionSchema);
			tVData.add(mTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSurveyReviewConfirmUI")) {
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
