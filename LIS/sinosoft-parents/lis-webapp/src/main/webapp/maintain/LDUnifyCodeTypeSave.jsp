<%
//-------------------------------------------------
//�������ƣ�LDUnifyCodeTypeInit.jsp
//�����ܣ�ϵͳͳһ����ά��
//�������ڣ�2012-01-01
//������  ��������
//�޸���  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//-------------------------------------------------
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String mtransact = "";//����״̬

	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");
	
	//�жϽ����Ƿ�ʧЧ
	if(tGI == null){
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
		System.out.println("ҳ��ʧЧ,�����µ�½");    
	}
	//��ý��洫���ֵ

	mtransact = request.getParameter("fmtransact");
	//��ñ�������
	LDUnifyCodeTypeSchema tLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
	tLDUnifyCodeTypeSchema.setSysCode(request.getParameter("SysCode"));
	tLDUnifyCodeTypeSchema.setCodeType(request.getParameter("CodeType").trim());
	tLDUnifyCodeTypeSchema.setCodeTypeName(request.getParameter("CodeTypeName").trim());
	tLDUnifyCodeTypeSchema.setMaintainFlag(request.getParameter("MaintainFlag").trim());
	tLDUnifyCodeTypeSchema.setCodeTypeDescription(request.getParameter("CodeTypeDescription"));
	
	LDUnifyCodeSchema tLDUnifyCodeSchema = new LDUnifyCodeSchema();
	
	tLDUnifyCodeSchema.setSysCode(request.getParameter("SysCode1").trim());
	tLDUnifyCodeSchema.setCodeType(request.getParameter("CodeType1").trim());
	tLDUnifyCodeSchema.setCode(request.getParameter("Code").trim());
	tLDUnifyCodeSchema.setCodeName(request.getParameter("CodeName").trim());
	tLDUnifyCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
	// ׼���������� VData
	VData tVData = new VData();	
	tVData.add(tGI);
	tVData.add(tLDUnifyCodeTypeSchema);
	tVData.add(tLDUnifyCodeSchema);
	try {
		// ���ݴ���
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, mtransact, "LDUnifyCodeTypeUI")) {
			Content = tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}else {
			Content = " �����ύ�ɹ�! ";
			FlagStr = "Succ";
		} 
	}catch(Exception ex)  {
		Content = "�����ύtComBLʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
