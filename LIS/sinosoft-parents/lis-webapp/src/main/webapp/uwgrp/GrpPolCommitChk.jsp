<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuRReportChk.jsp
//�����ܣ�����Լ�˹��˱��Լ��������½���
//�������ڣ�2002-06-19 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
<%
  //�������
  CErrors tError = null;
 String FlagStr = "Fail";
 String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//������Ϣ
  
  LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
  tLCGrpPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
  tLCGrpPolSchema.setUWFlag(request.getParameter("GUWState"));
  tLCGrpPolSchema.setRemark(request.getParameter("GUWIdea"));
  tLCGrpPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCGrpPolSchema.setRiskCode(request.getParameter("temriskcode"));
 
	boolean flag = true;
	
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCGrpPolSchema);
		tVData.add( tG );
		
		GrpUWManuNormGChkUI tGrpUWManuNormGChkUI   = new GrpUWManuNormGChkUI();
		if (tGrpUWManuNormGChkUI.submitData(tVData,"") == false)
		{
			int n = tGrpUWManuNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �¼������ֱ�������ʧ��,ԭ����: " + tGrpUWManuNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWManuNormGChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �¼������ֱ�������ʧ��,ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+".��ʾ���쳣��ֹ!";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
