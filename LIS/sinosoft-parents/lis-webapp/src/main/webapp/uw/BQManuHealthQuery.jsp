<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealthQuery.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
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
  	// Ͷ�����б�
  	loggerDebug("BQManuHealthQuery","----------------------------------qqq-------------------");
	LPPENoticeSet tLPPENoticeSet = new LPPENoticeSet();
	LPPENoticeItemSet tLPPENoticeItemSet = new LPPENoticeItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");

	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	loggerDebug("BQManuHealthQuery","Contno:"+tContNo);
	loggerDebug("BQManuHealthQuery","hospital:"+tHospital);
	loggerDebug("BQManuHealthQuery","note:"+tNote);
	loggerDebug("BQManuHealthQuery","ifempty:"+tIfEmpty);
	loggerDebug("BQManuHealthQuery","insureno:"+tInsureNo);
	
	boolean flag = true;
	int ChkCount = 0;
	//ChkCount = tChk.length;
	//loggerDebug("BQManuHealthQuery","chkcount:"+ChkCount);
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ContNo",tContNo);
	tTransferData.setNameAndValue("InsuredNo",tInsureNo);
	if ( tContNo.equals("")||tInsureNo.equals(""))
	{
		Content = "����¼�벻����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("BQManuHealthQuery","111");
	}
	else
	{
		loggerDebug("BQManuHealthQuery","222");
		
 		LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
 			
		//����
				   
	    	//tLCPolSet.add( tLCPolSchema );
       loggerDebug("BQManuHealthQuery","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		//tVData.add( tLCPolSchema );
		tVData.add(tTransferData);
		tVData.add( tG );		
				
		// ���ݴ���
		BQAutoHealthQueryUI tBQAutoHealthQueryUI   = new BQAutoHealthQueryUI();
		if (tBQAutoHealthQueryUI.submitData(tVData,"INSERT") == false)
		{
			int n = tBQAutoHealthQueryUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tBQAutoHealthQueryUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
				tVData.clear();
				tVData = tBQAutoHealthQueryUI.getResult();
		
				// ��ʾ
				LDHealthSet mLDHealthSet = new LDHealthSet(); 
				mLDHealthSet.set((LDHealthSet)tVData.getObjectByObjectName("LDHealthSet",0));
				
				loggerDebug("BQManuHealthQuery","size:"+mLDHealthSet.size());
%>
      				
<%
				String tHealthSubCodeAll = ""; 
				String tHealthMainCode = "";
				for (int j = 1;j <= mLDHealthSet.size();j++)
				{
					LDHealthSchema mLDHealthSchema = new LDHealthSchema();			
					mLDHealthSchema = mLDHealthSet.get(j);
					if(mLDHealthSchema.getHealthCode()==null||mLDHealthSchema.getHealthCode().trim().equals(""))
					{
						continue;
					}
					loggerDebug("BQManuHealthQuery","code:"+mLDHealthSchema.getHealthCode().trim());
					loggerDebug("BQManuHealthQuery","name:"+mLDHealthSchema.getHealthName().trim());
					loggerDebug("BQManuHealthQuery","j:"+j);
					tHealthSubCodeAll = tHealthSubCodeAll + mLDHealthSchema.getSubHealthCode();
					if(j==1)
					{
						tHealthMainCode = mLDHealthSchema.getHealthCode();
						tHealthSubCodeAll = tHealthSubCodeAll + "#";
					}
%>						
					<%
				}
				//tHealthMainCode = "PEG001";
				//tHealthSubCodeAll = "PE001#PE002#PE003";
				if(!tHealthMainCode.equals(""))
				{
				%>
				<script language="javascript">
						parent.fraInterface.checkMainHealthCode('<%=tHealthMainCode%>');				
						parent.fraInterface.showBodyCheck('<%=tHealthSubCodeAll%>');
					//	parent.fraInterface.getAllChecked();
                    			</script>        
				<%
				}
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBQAutoHealthQueryUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �˹��˱��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	}
	} 
%>

