<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpCutPersonBonusChk.jsp
//�����ܣ��ŵ��¸��˷ֺ촦��
//�������ڣ�2005-08-16
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
		//�������
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		try
		{
				GlobalInput tG = (GlobalInput)session.getValue("GI");
				if(tG == null) 
				{
					out.println("session has expired");
					return;
				}

				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("FiscalYear",request.getParameter("FiscalYear"));
				tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
				tTransferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
				tTransferData.setNameAndValue("BDate","");
				tTransferData.setNameAndValue("EDate","");
				tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));

				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tG );
				tVData.add( tTransferData );
				
				// ���ݴ���
				GrpAssignBonus tGrpAssignBonus = new GrpAssignBonus();
				if (!tGrpAssignBonus.submitData(tVData,"ASSIGN||PART"))
				{			
						Content = "����������ɣ���������������鿴������Ϣ��";
						FlagStr = "Fail";
				}
				else
				{
						Content = "�����������! ";
						FlagStr = "Succ";
				}
		}
		catch(Exception e)
		{
				e.printStackTrace();
				Content = Content.trim() +" ��ʾ:�쳣�˳�.";
		}
%>                      
<html>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
//		parent.fraInterface.queryGrpPol();
</script>
</html>
