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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
  	loggerDebug("UWManuHealthQuery","----------------------------------qqq-------------------");
 	LCPolSchema tLCPolSchema = new LCPolSchema();
	LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");

	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String thealthcode[] = request.getParameterValues("HealthGrid1");
	String thealthname[] = request.getParameterValues("HealthGrid2");
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
		tLCPolSchema.setContNo(tContNo);
		tLCPolSchema.setInsuredNo(tInsureNo);
				    
	    	//tLCPolSet.add( tLCPolSchema );
       loggerDebug("UWManuHealthQuery","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSchema );
		tVData.add( tG );		
				
		// ���ݴ���
		 String busiName="cbcheckgrpUWAutoHealthQueryUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		UWAutoHealthQueryUI tUWAutoHealthQueryUI   = new UWAutoHealthQueryUI();
		if (tUWAutoHealthQueryUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWAutoHealthQueryUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tUWAutoHealthQueryUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
				tVData.clear();
				tVData = tUWAutoHealthQueryUI.getResult();
		
				// ��ʾ
				LDHealthSet mLDHealthSet = new LDHealthSet(); 
				mLDHealthSet.set((LDHealthSet)tVData.getObjectByObjectName("LDHealthSet",0));
				
				loggerDebug("UWManuHealthQuery","size:"+mLDHealthSet.size());
%>
				<script language="javascript">					
					//parent.fraInterface.HealthGrid.clearData ();				
				</script>         				
<%
				for (int j = 1;j <= mLDHealthSet.size();j++)
				{
					LDHealthSchema mLDHealthSchema = new LDHealthSchema();			
					mLDHealthSchema = mLDHealthSet.get(j);
												
					loggerDebug("UWManuHealthQuery","code:"+mLDHealthSchema.getHealthCode().trim());
					loggerDebug("UWManuHealthQuery","name:"+mLDHealthSchema.getHealthName().trim());
					loggerDebug("UWManuHealthQuery","j:"+j);
%>
					<script language="javascript">					
						parent.fraInterface.HealthGrid.addOne();
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,1,"<%=mLDHealthSchema.getHealthCode()%>");
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,2,"<%=mLDHealthSchema.getHealthName()%>");
                    			</script>         
<%
				}
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWAutoHealthQueryUI.mErrors;
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

