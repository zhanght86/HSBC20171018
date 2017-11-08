<%
/***************************************************************
 * <p>ProName��LSQuotProjQuerySave.jsp</p>
 * <p>Title����Ŀѯ��¼���ѯ</p>
 * <p>Description����Ŀѯ��¼���ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tQuotNo = "";
	String tQuotBatNo = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tQuotType = request.getParameter("QuotType");
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			
			if ("APPLYET".equals(tOperate)) {
				
				tLWMissionSchema.setMissionProp3(tQuotType);
			} else if ("AGAINET".equals(tOperate)) {
			
			} else if ("PREAPPLYET".equals(tOperate)) {
				
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotProjQueryUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
				tQuotNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
				tQuotBatNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tQuotNo%>", "<%=tQuotBatNo%>");
</script>
</html>
