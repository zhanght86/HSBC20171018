<%
/***************************************************************
 * <p>ProName��LFGetPayConfSave.jsp</p>
 * <p>Title����ȡȷ��</p>
 * <p>Description����ȡȷ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJSGetDrawSchema"%>
<%@page import="com.sinosoft.lis.vschema.LJSGetDrawSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
			
			if ("GETCONF".equals(tOperate)) {
				
				String tChk[]=request.getParameterValues("InpGetGridChk");
				String tGetNoticeNo [] = request.getParameterValues("GetGrid1");
				
				for (int index=0;index<tChk.length;index++) {
					
					if ("1".equals(tChk[index])) {
						
						LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
						tLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo[index]);
						tLJSGetDrawSet.add(tLJSGetDrawSchema);
					}
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJSGetDrawSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LFGetPayUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
