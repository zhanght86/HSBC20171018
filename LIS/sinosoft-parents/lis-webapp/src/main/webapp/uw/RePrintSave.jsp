<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PEdorReprintSava.jsp
//�����ܣ���ȫ�˹��˱�����֪ͨ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
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
	TransferData tTransferData = new TransferData();
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	String tPrtSeq = request.getParameter("PrtSeq");
	String tCode = request.getParameter("Code");
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");	
	String tActivityID = request.getParameter("ActivityID");
	
	loggerDebug("RePrintSave","ContNo:"+tContNo);
	loggerDebug("RePrintSave","tMissionID:"+tMissionID);
	loggerDebug("RePrintSave","tSubMissionID:"+tSubMissionID);
	loggerDebug("RePrintSave","tCode:"+tCode);
	boolean flag = true;
	if (!tContNo.equals("")&& !tMissionID.equals("") && !tSubMissionID.equals(""))
	{
		//׼������������Ϣ
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("GrpContNo",tContNo);
		tTransferData.setNameAndValue("Code",tCode);
		tTransferData.setNameAndValue("PrtNo",tPrtNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);	
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema);	
	}
	else
	{
		flag = false;
		Content = "���ݲ�����!";
	}	
	loggerDebug("RePrintSave","flag:"+flag);
	loggerDebug("RePrintSave","tCode:"+tCode);
try
{
  
  	if(tCode.equals("TB89")||tCode.equals("TB90")||tCode.equals("03") || tCode.equals("04")||tCode.equals("05")||tCode.equals("14")||tCode.equals("17") || tCode.equals("85")||tCode.equals("81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("86")||tCode.equals("87")||tCode.equals("88")||tCode.trim().equals("89")) //edity by yaory
	   {
		// ׼���������� VData
		String tOperate = new String();
		if(tCode.trim().equals("03"))	
	      tOperate = "0000001114"; //��ӡ���֪ͨ������ڵ����
	//   if(tCode.trim().equals("04"))	
	//      tOperate = "0000001116";  //��ӡ���֪ͨ������ڵ����
	   if(tCode.trim().equals("05"))	
	      tOperate = "0000001115"; //��ӡ�˱�֪ͨ������ڵ���� 
	   if(tCode.trim().equals("54")) //add by yaory	
	      tOperate = "0000002210"; //��ӡ�ŵ����������ڵ����  
	   if(tCode.trim().equals("14"))	
	      tOperate = "0000001018"; //��ӡ����Լ�����֪ͨ������ڵ���� 
	   if(tCode.trim().equals("17"))	
	      tOperate = "0000001240"; //��ӡ����Լ�ͻ��ϲ�֪ͨ������ڵ����
	      //��ӡҵ��Ա֪ͨ��
	  			  if(tCode.equals("14"))
	  			  	tOperate = "0000001018";
	  			  //��ӡ�˱�֪ͨ��(��ӡ��)
	  			  if(tCode.equals("TB89"))
	  			  	//tOperate = "0000001115";
	  				tOperate=tActivityID;
	  			  //�˱�֪ͨ��(�Ǵ�ӡ��)
	  			  if(tCode.equals("TB90"))
	  			  	//tOperate = "0000001301";
	  				tOperate=tActivityID;
	  			  	//����֪ͨ��
	  			  if(tCode.equals("04"))
	  			  	tOperate = "0000001116";
	   if(tCode.trim().equals("00")||tCode.trim().equals("06")||tCode.trim().equals("81")||tCode.trim().equals("82")||tCode.trim().equals("83")||tCode.trim().equals("84")||tCode.trim().equals("85")||tCode.trim().equals("86")||tCode.trim().equals("89")||tCode.trim().equals("87")||tCode.trim().equals("88")) 
	      tOperate = "0000001290";	    
	    loggerDebug("RePrintSave","tOperate"+tOperate);
	    //��ѯbusitype
	    ExeSQL tExeSQL=new ExeSQL();
	    String tBusiType=tExeSQL.getOneValue("select busitype from lwactivity where activityid='"+tOperate+"'");
	    tTransferData.setNameAndValue("BusiType", tBusiType);
	    tTransferData.setNameAndValue("ActivityID",tOperate);
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		String busiName="tWorkFlowUI";
		

		// ���ݴ���
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"execute",busiName)==false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = "����֪ͨ�������ύ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
		}
		}
  	/**
		else if(tCode.equals("76") || tCode.equals("75"))
		{
		loggerDebug("RePrintSave","kaishi");
			String tOperate = new String();
		if(tCode.trim().equals("76"))	
	      tOperate = "0000002114"; //��ӡ�ŵ��˱�����֪ͨ������ڵ����
	  if (tCode.trim().equals("75"))
	     tOperate = "0000002314"; //��ӡ�ŵ��˱�Ҫ��֪ͨ������ڵ����    
	 
	    loggerDebug("RePrintSave","tOperate"+tOperate);
	      
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
		if (!tGrpTbWorkFlowUI.submitData(tVData,tOperate))
		{
			int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
			Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpTbWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = "����֪ͨ�������ύ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
		}
		}
  	*/
		//00 06 07 09 
		//add 21 ,hanbin 2010-05-11
	  else if(tCode.equals("08")||tCode.equals("00") || tCode.equals("06")||tCode.equals("07") || tCode.equals("09") ||tCode.equals("21"))
	  {
	  	VData tVData = new VData();
			tVData.add( tTransferData);
			tVData.add( tG );
			tVData.add(tLOPRTManagerSchema);
	      RePrintUI tRePrintUI = new RePrintUI();
     try
  	{
    	tRePrintUI.submitData(tVData,"CONFIRM");
    	 tError = tRePrintUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = "����֪ͨ�������ύ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
  	}
  	catch(Exception ex)
  	{
 	   	Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
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
