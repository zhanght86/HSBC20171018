<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuUWChk.jsp
//�����ܣ������˹��˱�
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
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
	LCContSet tLCContSet = new LCContSet();

	String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tContNo = request.getParameter("ContNo");
  String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;
  TransferData tTransferData = new TransferData();
  LCContSchema tLCContSchema = new LCContSchema();
if (!tContNo.equals("") )
{
			loggerDebug("ManuUWChk","ContNo:"+tContNo);
	  		
		    tLCContSchema.setContNo( tContNo );
		    tTransferData.setNameAndValue("ContNo",tContNo);
		    tTransferData.setNameAndValue("PrtNo",tPrtNo);
	    	tTransferData.setNameAndValue("MissionID",tMissionID);
	     	tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		    flag = true;

}
		try
		{
		loggerDebug("ManuUWChk","flag=="+flag);
		  	if (flag == true)
		  	{
		  String tSQL =
		        //ȡͶ��������	
		        " select appntno,'A' from lcappnt a where contno='"+tContNo+"' "
//				+ " and not exists " 
//				+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid='0000001105' "
//				+ " and missionprop16='A' and missionprop15=a.appntno ) " 
				+ " union "
                //ȡ����������
				+ " select insuredno,'I' from lcinsured a where contno='"+tContNo+"' "
//				+ " and not exists " 
//				+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid='0000001105' "
//				+ " and missionprop16='I' and missionprop15=a.insuredno ) "
				;
    loggerDebug("ManuUWChk","####tSQL:"+tSQL);
	SSRS tSSRS = new SSRS();
	ExeSQL tExeSQL = new ExeSQL();
//	tSSRS = tExeSQL.execSQL(tSQL);
	TransferData sTransferData3=new TransferData();
    sTransferData3.setNameAndValue("SQL", tSQL);
    VData sVData3 = new VData();
    sVData3.add(sTransferData3);
    BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate3.submitData(sVData3, "execSQL", "ExeSQLUI"))
    {
    	tSSRS = (SSRS)tBusinessDelegate3.getResult().getObjectByObjectName("SSRS", 0);
    }
	 for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				// ׼���������� VData
				String tCustomerNo = tSSRS.GetText(i,1);
				String tCustomerNoType = tSSRS.GetText(i,2);
				tTransferData.removeByName("CustomerNo");
				tTransferData.removeByName("CustomerNoType");
				tTransferData.setNameAndValue("CustomerNo", tCustomerNo);
				tTransferData.setNameAndValue("CustomerNoType", tCustomerNoType);
				VData tVData = new VData();
				tVData.add( tTransferData );
				tVData.add( tG );
				// ���ݴ���
//				TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
				String busiName="tbTbWorkFlowUI";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//				if (tTbWorkFlowUI.submitData(tVData,"0000001100") == false)
				if (tBusinessDelegate.submitData(tVData,"0000001100",busiName) == false)
				{
//					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("ManuUWChk","n=="+n);
					for (int j = 0; j < n; j++)
//					loggerDebug("ManuUWChk","Error: "+tTbWorkFlowUI.mErrors.getError(j).errorMessage);
//					Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
                    loggerDebug("ManuUWChk","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
					Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
//				    tError = tTbWorkFlowUI.mErrors;
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = " �Զ��˱��ɹ�! ";
				    if (!tError.needDealError())
				    {                          
				    	//Content = "�Զ��˱��ɹ�!";
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{    			      
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
					    }
		
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	int n = tError.getErrorCount();
				    	//Content = "�Զ��˱�ʧ��!";
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
							}
				    	FlagStr = "Fail";
				    }
				}
			}
 	      if(tSSRS.getMaxRow()==0)
 	      {
 	    	 FlagStr = "Succ";
 	      }
			}
			else
			{
				Content = "��ѡ�񱣵���";
			}  
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" ��ʾ:�쳣�˳�.";
		}
	
	
loggerDebug("ManuUWChk","AUTO UW END!");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initPolGrid();
	<%
	//parent.fraInterface.fm.submit();
	%>
</script>
</html>
