<%
//�������ƣ�CreatBankCode.jsp
//�����ܣ�
//�������ڣ�2007-01-11
//������  ��Lujun
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
  	<%@page import="com.sinosoft.utility.*"%>
  	<%@page import="com.sinosoft.lis.tbgrp.*"%>
  	<%@page import="com.sinosoft.lis.pubfun.*"%>
  	  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	String FlagStr = "";
	String Content = "";
	String BankCode="";
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
  	
	String ManageCom = request.getParameter("ManageCom");
	String BankName = request.getParameter("BankCodeName");
	String BankType = request.getParameter("BankType");
	loggerDebug("CreatBankCode","��������============"+ BankName);
	 String busiName="tbgrpCreatBankCodeUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//CreatBankCodeUI tCreatBankCodeUI = new CreatBankCodeUI();
	
	tTransferData.setNameAndValue("ManageCom", ManageCom);
	tTransferData.setNameAndValue("BankName", BankName);
	tTransferData.setNameAndValue("BankType", BankType);
	loggerDebug("CreatBankCode","�������============"+ ManageCom);
	loggerDebug("CreatBankCode","��������============"+ BankType);
	
	tVData.add(tTransferData);
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	{
  		FlagStr = "Fail";
  		Content = "���б�������ʧ�ܣ�";
	}
	else
	{
		tVData.clear();
  		tVData = tBusinessDelegate.getResult();

  		BankCode = (String)tVData.getObjectByObjectName("String",0);

  		if(BankCode==null)
  		{
  			FlagStr = "Fail";
  			Content = "���б�������ʧ�ܣ�";
  		}
	}

	if (FlagStr=="")
	{
		FlagStr = "Succ";
	}

	if(FlagStr.equals("Fail"))
	{	
		%>
			<script language="javascript">
				parent.fraInterface.alert("���б�������ʧ�ܣ�");
			</script>
		<%
	}
	else
	{
		%>
			<script language="javascript">
				parent.fraInterface.fm.all('BankCode').value = "<%=BankCode%>";
				parent.fraInterface.fm.all("Confirm").disabled=true;
			</script>
		<%
	}
%>
