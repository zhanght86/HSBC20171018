
<%@page import="com.itextpdf.text.log.SysoLogger"%><%
/***************************************************************
 * <p>ProName：LLModifyconfigSave.jsp</p>
 * <p>Title：保项修改规则配置界面</p>
 * <p>Description：保项修改规则配置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王志豪
 * @version  : 8.0
 * @date     : 2014-06-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
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
			
			String tOperate = request.getParameter("Operate");
			LLModifyConfigSet tLLModifyConfigSet = new LLModifyConfigSet();
			LLModifyConfigSchema tLLModifyConfigSchema = new LLModifyConfigSchema();
			if("INSERT1".equals(tOperate)){
				
				tLLModifyConfigSchema.setReasonNo(request.getParameter("ReasonNo"));
				tLLModifyConfigSchema.setSubValue(request.getParameter("RuleType"));
			}else if("INSERT2".equals(tOperate)){
				
				tLLModifyConfigSchema.setReasonNo(request.getParameter("ReasonNo"));
				tLLModifyConfigSchema.setSubValue(request.getParameter("PolType"));
			}else if("INSERT3".equals(tOperate)){
				
				tLLModifyConfigSchema.setReasonNo(request.getParameter("ReasonNo"));
				tLLModifyConfigSchema.setSubValue(request.getParameter("TiaoZhengType"));
				System.out.println(tLLModifyConfigSchema.getSubValue());
			}else if("DELETE1".equals(tOperate)){
				
				String[] tGridNo = request.getParameterValues("InpRuleTypeGridChk");//复选框
				String[] tReasonNo = request.getParameterValues("RuleTypeGrid1");
				String[] tRuleType = request.getParameterValues("RuleTypeGrid3");
				for (int i = 0;i < tGridNo.length;i++) {
					   if("1".equals(tGridNo[i])){
					    LLModifyConfigSchema eLLModifyConfigSchema = new LLModifyConfigSchema();
					    eLLModifyConfigSchema.setReasonNo(tReasonNo[i]);
					    eLLModifyConfigSchema.setSubValue(tRuleType[i]);
					    tLLModifyConfigSet.add(eLLModifyConfigSchema);
					   }
				}
			}else if("DELETE2".equals(tOperate)){
				String[] tGridNo = request.getParameterValues("InpPolTypeGridChk");//复选框
				String[] tReasonNo = request.getParameterValues("PolTypeGrid1");
				String[] tPolType = request.getParameterValues("PolTypeGrid3");
				for (int i = 0;i < tGridNo.length;i++) {
					   if("1".equals(tGridNo[i])){
					    LLModifyConfigSchema eLLModifyConfigSchema = new LLModifyConfigSchema();
					    eLLModifyConfigSchema.setReasonNo(tReasonNo[i]);
					    eLLModifyConfigSchema.setSubValue(tPolType[i]);
					    tLLModifyConfigSet.add(eLLModifyConfigSchema);
					   }
				}
			}else if("DELETE3".equals(tOperate)){
				String[] tGridNo = request.getParameterValues("InpTiaoZhengGridChk");//复选框
				String[] tReasonNo = request.getParameterValues("TiaoZhengGrid1");
				String[] tTiaoZhengType = request.getParameterValues("TiaoZhengGrid3");
				for (int i = 0;i < tGridNo.length;i++) {
					   if("1".equals(tGridNo[i])){
					    LLModifyConfigSchema eLLModifyConfigSchema = new LLModifyConfigSchema();
					    eLLModifyConfigSchema.setReasonNo(tReasonNo[i]);
					    eLLModifyConfigSchema.setSubValue(tTiaoZhengType[i]);
					    tLLModifyConfigSet.add(eLLModifyConfigSchema);
					   }
				}
			}
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLModifyConfigSchema);
			tVData.add(tLLModifyConfigSet);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLModifyconfigUI")) {
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
