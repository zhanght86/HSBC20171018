<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestQueryUpdatePrintFlag.jsp
//�����ܣ��������ӡ��Ǹ���
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%
//1-�õ����м�¼����ʾ��¼ֵ
  int index=0;
  int TempCount=0;
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "��ѡ�񱣵�";
  boolean flag = false;
  
  LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
  
  //�õ�radio�е�����
  loggerDebug("QuestQueryUpdatePrintFlag","---4----");
  
  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
	out.println("session has expired");
	return;
  }

  
  String tOperatePos = request.getParameter("OperatePos");
  
  String tRadio[] = request.getParameterValues("InpQuestGridSel");  
  String tTempClassNum[] = request.getParameterValues("QuestGridNo");
  String tContNo[] = request.getParameterValues("QuestGrid1");
  String tQuest[] = request.getParameterValues("QuestGrid2");
  String tReply[] = request.getParameterValues("QuestGrid3");
  String tOperate[] = request.getParameterValues("QuestGrid7");
  String tIfPrint[] = request.getParameterValues("QuestGrid9");
  String tSerialNo[] = request.getParameterValues("QuestGrid10");
  int temp = tTempClassNum.length;
  loggerDebug("QuestQueryUpdatePrintFlag","radiolength:"+temp);
  
  //������ 
  
	if(tTempClassNum!=null) //������ǿռ�¼	
	{
		TempCount = tTempClassNum.length; //��¼��      
	 	loggerDebug("QuestQueryUpdatePrintFlag","Start query Count="+TempCount);   
		while(index<TempCount)
		{
			loggerDebug("QuestQueryUpdatePrintFlag","----3-----");
			loggerDebug("QuestQueryUpdatePrintFlag","polcode:"+tContNo[index]);  		
			if (!tContNo[index].equals("")&&!tQuest[index].equals("")&&!tIfPrint[index].equals("")&&!tOperate[index].equals(""))
			{	    					
				LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
				
				tLCIssuePolSchema.setContNo(tContNo[index]);				
				tLCIssuePolSchema.setIssueType(tQuest[index]);
				tLCIssuePolSchema.setOperatePos(tOperate[index]);
				tLCIssuePolSchema.setNeedPrint(tIfPrint[index]);
				tLCIssuePolSchema.setSerialNo(tSerialNo[index]);
				loggerDebug("QuestQueryUpdatePrintFlag","index:"+index);
				loggerDebug("QuestQueryUpdatePrintFlag","Quest:"+tQuest[index]);
				loggerDebug("QuestQueryUpdatePrintFlag","printflag:"+tIfPrint[index]);
				
					
				tLCIssuePolSet.add(tLCIssuePolSchema);			    
				  			  			
				flag = true;    						
	    		}
	    		index = index + 1;
	    	}
	}
      		
	if (flag == true)
	{
		// ׼���������� VData
		VData tVData = new VData();		
		tVData.add( tLCIssuePolSet);
		tVData.add( tG );
		
		// ���ݴ���
		//QuestPrintFlagUI tQuestPrintFlagUI   = new QuestPrintFlagUI();
		
		 String busiName="cbcheckQuestPrintFlagUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �������ӡ����޸�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " �������ӡ����޸ĳɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �������ӡ����޸�ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	} 

    		

   
%>  
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
