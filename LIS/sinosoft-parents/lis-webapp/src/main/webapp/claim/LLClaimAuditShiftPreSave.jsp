<%
//**************************************************************************************************
//�������ƣ�LLClaimAuditShiftPreSave.jsp
//�����ܣ���˹���ת��Ԥ������
//�������ڣ�2008-12-27 
//������  ��zhangzheng
//���¼�¼��
//**************************************************************************************************
%>

<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
	String busiName="tWorkFlowUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	//ҳ����Ч��
	if(tG == null)
	{
	    loggerDebug("LLClaimAuditShiftPreSave","session has expired");
	    return;
	}
	
	
	//�������,�������ɡ�Ԥ���ڵ㡱
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("RptNo",request.getParameter("RptNo")); //"�ⰸ��" 
	mTransferData.setNameAndValue("RptorState","35"); //����״̬:35-Ԥ��
	mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //"�����˱���"
	mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //"���������� 
	mTransferData.setNameAndValue("CustomerSex",request.getParameter("CustomerSex")); //"�������Ա�"
	mTransferData.setNameAndValue("AccDate",request.getParameter("AccidentDate")); //"�¹�����" 
	mTransferData.setNameAndValue("RgtClass",request.getParameter("tRgtClass")); //"��������" 
	mTransferData.setNameAndValue("RgtObj",request.getParameter("tRgtObj")); //"��������" 
	mTransferData.setNameAndValue("RgtObjNo",request.getParameter("RptNo")); //"��������" 
	//mTransferData.setNameAndValue("Popedom",request.getParameter("tPopedom")); //�������⼶��
	mTransferData.setNameAndValue("ComeWhere","B"); //"����" A' then '����' when 'B' then '���' when 'C' then '����' when 'D' then '���װ���'
	mTransferData.setNameAndValue("Auditer",tG.Operator); //"��˲�����" 
	mTransferData.setNameAndValue("MngCom",tG.ManageCom); //"����" 
	mTransferData.setNameAndValue("ComFlag",request.getParameter("tComFlag")); //Ȩ�޿缶��־
	mTransferData.setNameAndValue("BudgetFlag","1");
    mTransferData.setNameAndValue("PopedomPhase","B"); //Ȩ�޽׶α�ʾA:���B:����
	mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
	mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	mTransferData.setNameAndValue("IsRunBL","0"); //�����ж��Ƿ�����BL��0--������
	mTransferData.setNameAndValue("PrepayFlag","1"); //Ԥ������
	mTransferData.setNameAndValue("BusiType","1003");
	String tAuditConclusion=request.getParameter("AuditConclusion"); //��˽���
	loggerDebug("LLClaimAuditShiftPreSave","����"+request.getParameter("RptNo")+"����˽���:"+tAuditConclusion);
	mTransferData.setNameAndValue("AuditConclusion",tAuditConclusion); //��˽���

	try
	{
	    // ׼���������� VData
	    VData tVData = new VData();
	    tVData.add(mTransferData);
	    tVData.add(tG);
	    
	    //Ԥ���ϱ�
	    if(tAuditConclusion.equals("0"))
	    {
		    try
		    {
		        //if(!tClaimWorkFlowUI.submitData(tVData,"0000005035"))
//		        {
//		            Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//		            FlagStr = "Fail";
//		        }
				if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "�ύ������ʧ��";
						FlagStr = "Fail";				
					}
				}
				
		        else
		    	{
		    		//�ӽ������ȡ��ǰ̨��Ҫ����
		            tVData.clear();
		            //tVData = tClaimWorkFlowUI.getResult();
		            //loggerDebug("LLClaimAuditShiftPreSave","tVData="+tVData);	    
		            String sql="select a.missionid,a.submissionid,a.activityid,a.MissionProp1,a.MissionProp3 from lwmission a where a.missionprop1='"+mTransferData.getValueByName("RptNo")+"'";
		            loggerDebug("LLClaimAuditShiftPreSave","��ѯԤ����ǰ�ڵ�sql:"+sql);
		            //ExeSQL tExeSQL=new ExeSQL();
		            SSRS tSSRS=new SSRS();
		            //tSSRS=tExeSQL.execSQL(sql);
		            TransferData sqlTransferData = new TransferData();
	  				VData sqlVData = new VData();
				    sqlTransferData.setNameAndValue("SQL",sql);
				    sqlVData.add(sqlTransferData);
				  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLClaimAuditShiftPreSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLClaimAuditShiftPreSave","��ѯʧ��");				
				  		   }
				  	  }
				  	tSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
		            if(tSSRS.getMaxRow()==0)
		            {
			            Content = " ��ѯ����: " +mTransferData.getValueByName("RptNo")+"��ǰ�������ڵ�ʧ��!";
			            FlagStr = "Fail";
		            }
		            else
		            {
		            	loggerDebug("LLClaimAuditShiftPreSave","����"+tSSRS.GetText(1,4)+",missionid:"+tSSRS.GetText(1,1));
		            	loggerDebug("LLClaimAuditShiftPreSave","����"+tSSRS.GetText(1,4)+",submissionid:"+tSSRS.GetText(1,2));
		            	loggerDebug("LLClaimAuditShiftPreSave","����"+tSSRS.GetText(1,4)+",activityid:"+tSSRS.GetText(1,3));
		            	loggerDebug("LLClaimAuditShiftPreSave","����"+tSSRS.GetText(1,4)+",Customerno:"+tSSRS.GetText(1,5));
	%>
	<html>
	<script language="javascript">
		//fm.MissionID.value=<%=tSSRS.GetText(1,1)%>;
		//fm.SubMissionID.value=<%=tSSRS.GetText(1,2)%>;
		//fm.ActivityID.value=<%=tSSRS.GetText(1,3)%>;
		//fm.ClmNo.value=<%=tSSRS.GetText(1,4)%>;
		//fm.customerNo.value=<%=tSSRS.GetText(1,5)%>;
	</script>
	</html>
	<%	            	
			            Content = "�����ύ�ɹ�";
			            FlagStr = "Succ";  
		            }
	          
		    	}
			}
			catch(Exception ex)
		    {
		    	Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
		    	FlagStr = "Fail";
		    }	
	    }
	    else
	    {
	    	//���˲��߹�����,�ύҵ����
//			LLClaimPrepayAuditRetrunBL tLLClaimPrepayAuditRetrunBL = new LLClaimPrepayAuditRetrunBL();
//			if (!tLLClaimPrepayAuditRetrunBL.submitData(tVData, "INSERT")) {
//				// @@������
//				CError.buildErr(this, "Ԥ������ϱ�ȷ��ʧ��,"+tLLClaimPrepayAuditRetrunBL.mErrors.getLastError());
//	            Content = " Ԥ������ʧ�ܣ�ԭ����: " + tLLClaimPrepayAuditRetrunBL.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//				tVData = null;
//			} 
  
			String busiName1="LLClaimPrepayAuditRetrunBL";
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			if(!tBusinessDelegate.submitData(tVData,"INSERT",busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
			    	CError.buildErr(this, "Ԥ������ϱ�ȷ��ʧ��,"+tBusinessDelegate1.getCErrors().getLastError());
					Content = "Ԥ������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "Ԥ������ʧ��";
					FlagStr = "Fail";				
				}
			}


			else 
			{
	            Content = "�����ύ�ɹ�";
	            FlagStr = "Succ";  
			}
	    }

	}
	catch(Exception ex)
	{
	    Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	}
	loggerDebug("LLClaimAuditShiftPreSave","------LLClaimPrepayMissSave.jsp end------");
	
	%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
