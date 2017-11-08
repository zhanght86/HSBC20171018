<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�GEdorTypeRiskDetailSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	loggerDebug("GEdorTypeRiskDetailSubmit","=====This is GEdorTypeRiskDetailSubmit.jsp=====\n");

	//������Ϣ������У�鴦��
	String FlagStr  = "";
	String Content  = "";
	String transact = "";
	String Result   = "";
  String EdorType = request.getParameter("EdorType");
	GlobalInput tG  = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");  
	TransferData tTransferData = new TransferData();
	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	
	//������Ŀ������Ϣ
	tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	
	//��������������Ϣ
	LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
	tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSet.add(tLPEdorItemSchema);   
	tTransferData.setNameAndValue("ContType", "2");
	transact = request.getParameter("Transact");      // Ӧ�ü��ϰɣ���
	GEdorRiskDetailBL tGEdorRiskDetailBL = new GEdorRiskDetailBL();
	try
	{
		// ׼���������� VData
		VData tVData = new VData();  
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSet);
		tVData.addElement(tLPGrpEdorItemSchema);
		tVData.addElement(tTransferData);

		//������˱�����Ϣ(��ȫ)	
		tGEdorRiskDetailBL.submitData(tVData, transact);
	}
	catch(Exception ex)
	{
		Content = transact + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}			
	
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "")
	{
		CErrors tError = new CErrors(); 
		tError = tGEdorRiskDetailBL.mErrors;
		if (!tError.needDealError())
		{
		  Content = " ����ɹ�";
			FlagStr = "Success";
			
			if (transact.equals("QUERY||MAIN") || transact.equals("QUERY||DETAIL"))
			{
				if (tGEdorRiskDetailBL.getResult() != null && 
				 													tGEdorRiskDetailBL.getResult().size() > 0)
				{
					Result = (String)tGEdorRiskDetailBL.getResult().get(0);					
					if (Result == null || Result.equals(""))	
					{
						FlagStr = "Fail";
						Content = "�ύʧ��!!";
					}
				}
			}
		}
		else
		{
			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript">	
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

