<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupContDeleteChk.jsp
//�����ܣ��ŵ�����ɾ��
//�������ڣ�2004-12-06 11:10:36
//������  ��Zhangrong
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("GroupContDeleteChk","Auto-begin:");
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
	loggerDebug("GroupContDeleteChk","session has expired");
	return;
   }
   
 	// Ͷ�����б�
	String tGrpContNo = request.getParameter("GrpContNo");
//	String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;

	loggerDebug("GroupContDeleteChk","ready for UWCHECK GrpContNo: " + tGrpContNo);
	
	LCGrpContSet tLCGrpContSet = null;
	LCGrpContDB tLCGrpContDB = new LCGrpContDB();
	tLCGrpContDB.setGrpContNo(tGrpContNo);
	tLCGrpContSet = tLCGrpContDB.query();
	if (tLCGrpContSet == null)
	{
		loggerDebug("GroupContDeleteChk","���壨Ͷ��������Ϊ" + tGrpContNo + "�ĺ�ͬ����ʧ�ܣ�");
		return;
	}
	LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);

	
try{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSchema );
		tVData.add( tG );
		
		// ���ݴ���
		GroupContDeleteUI tGroupContDeleteUI = new GroupContDeleteUI();
		if (tGroupContDeleteUI.submitData(tVData,"DELETE") == false)
		{
			FlagStr = "Fail";
		}
		else
			FlagStr = "Succ";
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGroupContDeleteUI.mErrors;
		    loggerDebug("GroupContDeleteChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����ɾ���ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ɾ��ʧ�ܣ�ԭ����:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {
			        //tError = tErrors.getError(i);
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
				}
		    	FlagStr = "Fail";
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
