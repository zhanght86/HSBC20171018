<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorApproveSave.jsp
//�����ܣ���ȫ����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

   String busiName="EdorWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  String FlagStr = "";
  String Content = ""; 
  String Flag = "0";
  
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
  String tMissionID 	= request.getParameter("MissionID");
  String tSubMissionID 	= request.getParameter("SubMissionID");  
  String tApproveFlag 	= request.getParameter("ApproveFlag");
  String tApproveContent= request.getParameter("ApproveContent");
  
  String tEdorAcceptNo	= request.getParameter("EdorAcceptNo");
  String tOtherNo		= request.getParameter("OtherNo");
  String tOtherNoType	= request.getParameter("OtherNoType");
  String tEdorAppName	= request.getParameter("EdorAppName");
  String tApptype		= request.getParameter("Apptype");
  String tManageCom		= request.getParameter("ManageCom");
  String tAppntName		= request.getParameter("AppntName");
  String tPaytoDate		= request.getParameter("PaytoDate");
  String sModifyReason  = request.getParameter("ModifyReason");    //XinYQ addedon 2005-11-28

  tTransferData.setNameAndValue("MissionID", tMissionID);
  tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
  
  //������ȫȷ�ϡ������޸Ľڵ������Ԫ��
  tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
  tTransferData.setNameAndValue("OtherNo", tOtherNo);
  tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
  tTransferData.setNameAndValue("EdorAppName", tEdorAppName);
  tTransferData.setNameAndValue("Apptype", tApptype);
  tTransferData.setNameAndValue("ManageCom", tManageCom);
  tTransferData.setNameAndValue("AppntName", tAppntName);
  tTransferData.setNameAndValue("PaytoDate", tPaytoDate);
  tTransferData.setNameAndValue("ApproveFlag", tApproveFlag);
  tTransferData.setNameAndValue("ApproveContent", tApproveContent);    //������
  tTransferData.setNameAndValue("CheckFlag", "N");
  if (sModifyReason != null) tTransferData.setNameAndValue("ModifyReason", sModifyReason);    //XinYQ addedon 2005-11-28

 
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);
		tBusinessDelegate.submitData(tVData, "0000008007",busiName);
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
				
				FlagStr = "";
//=========���˳ɹ����ж��������Ҫ���񽻷����Զ����б�ȫȷ��================BGN======================

				VData mResult = tBusinessDelegate.getResult();
				
               
                
				TransferData mTransferData = 
					(TransferData) mResult.getObjectByObjectName("TransferData", 0);
                
                String sCheckFlag = 
                	(String) mTransferData.getValueByName("CheckFlag");
                loggerDebug("GEdorApproveSave","==============CheckFlag=" + sCheckFlag);
                if (sCheckFlag.equals("Y"))
                {
					Content = "�ñ�ȫ��Ҫ��죬��ȴ���������������!";
					FlagStr = "Fail";
                }
                else
                {
                		String sNeedPayFlag = 
                			(String) mTransferData.getValueByName("NeedPayFlag");
                		loggerDebug("GEdorApproveSave","==============NeedPayFlag=" + sNeedPayFlag);
                		if(sNeedPayFlag==null || sNeedPayFlag.equals("N"))
                		{
                			Flag = "0"; //���շ�
                		}else{
                			Flag = "1"; //�շ�
                		}
                		//����ͨ�����Ҳ���Ҫ���񽻷�
                		if (tApproveFlag.equals("1") && (sNeedPayFlag==null || sNeedPayFlag.equals("N")))  
                		{
                			Content = "�����ɹ���";
					    	FlagStr = "Succ";
					    	
//				        	//��Ҫ���²�ѯ��ȫȷ�Ͻڵ��������ID
//						      String sql = " select submissionid from lwmission " +
//						                   " where activityid = '0000008009' " +
//						                   " and missionid = '" + tMissionID + "'";
//				
//						      ExeSQL exeSQL = new ExeSQL();
//						      String sNewSubMissionID = exeSQL.getOneValue(sql);
//						      tTransferData.removeByName("SubMissionID");
//							  tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);
//
//						      tTransferData.removeByName("ActivityID");
//							  tTransferData.setNameAndValue("ActivityID", "0000008009");
//							  
//							String sTemplatePath = application.getRealPath("xerox/printdata") + "/";
//						    tTransferData.setNameAndValue("TemplatePath", sTemplatePath);
//							
//							tEdorWorkFlowUI = new EdorWorkFlowUI(); 
//						    
//							try
//							{
//								tVData.clear();
//								tVData.add(tG);
//								tVData.add(tTransferData);
//								tEdorWorkFlowUI.submitData(tVData, "0000008009");
//							}
//							catch(Exception ex)
//							{
//							      Content = "�����ɹ������Ǳ�ȫȷ��ʧ�ܣ�ԭ����:" + ex.toString();
//							      FlagStr = "Fail";
//							}
//							if ("".equals(FlagStr))
//							{
//								    tError = tEdorWorkFlowUI.mErrors;
//								    if (!tError.needDealError())
//								    {
//										
////=========��ȫȷ�ϳɹ����Զ�ִ��ȷ����Ч����================BGN======================
//										GEdorValidBL tGEdorValidBL = new GEdorValidBL();
//										try
//										{
//											tGEdorValidBL.submitData(tVData, "");
//										}
//										catch(Exception ex)
//										{
//										      Content = "���˳ɹ������ұ�ȫȷ�ϳɹ������Ǳ�ȫȷ����Чʧ�ܣ�ԭ����:" + ex.toString();
//										      FlagStr = "Fail";
//										}
//										if ("".equals(FlagStr))
//										{
//											    tError = tGEdorValidBL.mErrors;
//											    if (!tError.needDealError())
//											    {
//											    	Content ="�����ɹ������ұ�ȫȷ�ϳɹ���";
//											    
//											    	//ȡ���Ƿ���Ҫ���ѱ�־
//													sql = " select 1 from dual where exists (select 'X' from LJaGet " +
//														  " where othernotype = '10' and otherno = '" + tEdorAcceptNo + 
//														  "' and sumgetmoney <> 0)";
//													exeSQL = new ExeSQL();
//													loggerDebug("GEdorApproveSave","-----------------------------\n"+sql);
//													String sNeedGetFlag = exeSQL.getOneValue(sql);
//													if (sNeedGetFlag == null) sNeedGetFlag = "";
//													loggerDebug("GEdorApproveSave","-----------------------------\n"+sNeedGetFlag);
//													if (sNeedGetFlag.equals("1"))
//													{
//														Content += "���ӡ����֪ͨ�飡";
//													}
//											    	FlagStr = "Succ";
//											    	Flag = "1";
//											    }
//											    else                                                                           
//											    {
//											    	Content = "�����ɹ������ұ�ȫȷ�ϳɹ������Ǳ�ȫȷ����Чʧ�ܣ�ԭ����:" + tError.getFirstError();
//											    	FlagStr = "Fail";
//											    }
//										}
////=========��ȫȷ�ϳɹ����Զ�ִ��ȷ����Ч����================END======================
//								    }
//								    else                                                                           
//								    {
//								    	Content = "�����ɹ������Ǳ�ȫȷ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
//								    	FlagStr = "Fail";
//								    }
//							}
                
                		}
                		else
                		{
                			Content = "�����ɹ���";
                			FlagStr = "Succ";
                			if (tApproveFlag.equals("1"))
                			{
                				Content += "���ӡ����֪ͨ�飬��ȥ���񽻷�";
                			}
                			
                		}
//=========���˳ɹ����ж��������Ҫ���񽻷����Զ����б�ȫȷ��================END======================
				}
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Flag%>");
</script>
</html>
   
   
   
 
