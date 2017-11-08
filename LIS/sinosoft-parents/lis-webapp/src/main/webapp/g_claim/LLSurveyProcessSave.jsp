<%
/***************************************************************
 * <p>ProName：LLClaimSurveyInput.jsp</p>
 * <p>Title：调查录入</p>
 * <p>Description：调查过程录入</p>
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
<%@page import="com.sinosoft.lis.schema.LLInqCourseSchema"%>
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
			String  tOperate = request.getParameter("fmtransact");//获得操作状态
				//调查过程表 
			LLInqCourseSchema tLLInqCourseSchema = new LLInqCourseSchema(); 
			tLLInqCourseSchema.setClmNo(request.getParameter("ClmNo"));//赔案号
			tLLInqCourseSchema.setInqNo(request.getParameter("InqNo"));//调查序号
			tLLInqCourseSchema.setInqDate(request.getParameter("InqDate"));//调查日期
			tLLInqCourseSchema.setInqMode(request.getParameter("InqMode")==null?null:request.getParameter("InqMode").trim());//调查方式
			tLLInqCourseSchema.setInqSite(request.getParameter("InqSite"));//调查地点
			tLLInqCourseSchema.setInqByPer(request.getParameter("InqByPer"));//被调查人
			tLLInqCourseSchema.setInqDept(request.getParameter("InqDept"));//调查机构
			tLLInqCourseSchema.setInqPer1(request.getParameter("InqPer1")); //第一调查人
			tLLInqCourseSchema.setInqPer2(request.getParameter("InqPer2"));//第二调查人
			tLLInqCourseSchema.setCouNo(request.getParameter("CourseCouNo"));//调查过程序号	
			tLLInqCourseSchema.setInqCourse(request.getParameter("InqCourse"));//调查过程	
			System.out.println("AffixNumber:"+request.getParameter("AffixNumber"));
	// 准备传输数据 VData
			VData tVData = new VData();	
			tVData.add(tGI);
			tVData.add(tLLInqCourseSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSurveyCourseUI")) {
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
