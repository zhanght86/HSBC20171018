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
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
  	loggerDebug("UWManuHealthQuery","----------------------------------qqq-------------------");
	LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");

	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	loggerDebug("UWManuHealthQuery","Contno:"+tContNo);
	loggerDebug("UWManuHealthQuery","hospital:"+tHospital);
	loggerDebug("UWManuHealthQuery","note:"+tNote);
	loggerDebug("UWManuHealthQuery","ifempty:"+tIfEmpty);
	loggerDebug("UWManuHealthQuery","insureno:"+tInsureNo);
	
	boolean flag = true;
	int ChkCount = 0;
	//ChkCount = tChk.length;
	//loggerDebug("UWManuHealthQuery","chkcount:"+ChkCount);
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ContNo",tContNo);
	tTransferData.setNameAndValue("InsuredNo",tInsureNo);
	if ( tContNo.equals("")||tInsureNo.equals(""))
	{
		Content = "����¼�벻����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("UWManuHealthQuery","111");
	}
	else
	{
		loggerDebug("UWManuHealthQuery","222");
		
 		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
 			
		//����
				   
	    	//tLCPolSet.add( tLCPolSchema );
       loggerDebug("UWManuHealthQuery","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		//tVData.add( tLCPolSchema );
		tVData.add(tTransferData);
		tVData.add( tG );		
				
		// ���ݴ���
		//UWAutoHealthQueryUI tUWAutoHealthQueryUI   = new UWAutoHealthQueryUI();
		String busiName="cbcheckUWAutoHealthQueryUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
				tVData.clear();
				tVData = tBusinessDelegate.getResult();
		
				// ��ʾ
				LDHealthSet mLDHealthSet = new LDHealthSet(); 
				mLDHealthSet.set((LDHealthSet)tVData.getObjectByObjectName("LDHealthSet",0));
				
				loggerDebug("UWManuHealthQuery","size:"+mLDHealthSet.size());
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
					loggerDebug("UWManuHealthQuery","code:"+mLDHealthSchema.getHealthCode().trim());
					loggerDebug("UWManuHealthQuery","name:"+mLDHealthSchema.getHealthName().trim());
					loggerDebug("UWManuHealthQuery","j:"+j);
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
		    tError = tBusinessDelegate.getCErrors();
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
<script language="javascript">
	//alert('1');
	parent.fraInterface.unlockscreen();
	//alert('2');
</script>   
