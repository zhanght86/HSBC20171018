<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuRReportChk.jsp
//�����ܣ�����Լ�˹��˱����ͺ˱�֪ͨ�鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
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
  
    LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
  
	String tGrpContNo = request.getParameter("GrpContNo");
	
	tLCGrpContSchema.setGrpContNo(tGrpContNo);
	tLCGrpContSet.add(tLCGrpContSchema);
	
	boolean flag = true;
	
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSet);
		tVData.add( tG );
		
		GrpUWRReportUI tGrpUWRReportUI   = new GrpUWRReportUI();
		if (tGrpUWRReportUI.submitData(tVData,"INSERT") == false)
		{
			int n = tGrpUWRReportUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����������֪ͨ��ʧ�ܣ�ԭ����: " + tGrpUWRReportUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWRReportUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����������֪ͨ��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����������֪ͨ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
