
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PEdorUWManuHealthQuery.jsp
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
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
 	LCPolSchema tLCPolSchema = new LCPolSchema();
	LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
	
	String tProposalNo = request.getParameter("PolNo");
	String tInsureNo = request.getParameter("InsureNo");
	//tInsureNo = "86110020030990000863";
	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String thealthcode[] = request.getParameterValues("HealthGrid1");
	String thealthname[] = request.getParameterValues("HealthGrid2");
	String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	System.out.println("polno:"+tProposalNo);
	System.out.println("hospital:"+tHospital);
	System.out.println("note:"+tNote);
	System.out.println("ifempty:"+tIfEmpty);
	System.out.println("insureno:"+tInsureNo);
	
	boolean flag = true;
	int ChkCount = 0;
	//ChkCount = tChk.length;
	//System.out.println("chkcount:"+ChkCount);
	if ( tProposalNo.equals("")||tInsureNo.equals(""))
	{
		Content = "����¼�벻����!";
		FlagStr = "Fail";
		flag = false;
		System.out.println("111");
	}
	else
	{
		System.out.println("222");
		
 		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
 			
		//����
		tLCPolSchema.setPolNo( tProposalNo);
		tLCPolSchema.setProposalNo(tProposalNo);
		tLCPolSchema.setInsuredNo(tInsureNo);
				    
	    	//tLCPolSet.add( tLCPolSchema );
       System.out.println("flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSchema );
		tVData.add( tG );		
				
		// ���ݴ���
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
				
				System.out.println("size:"+mLDHealthSet.size());
%>
				<script language="javascript">					
					parent.fraInterface.HealthGrid.clearData ();				
				</script>         				
<%
				for (int j = 1;j <= mLDHealthSet.size();j++)
				{
					LDHealthSchema mLDHealthSchema = new LDHealthSchema();			
					mLDHealthSchema = mLDHealthSet.get(j);
												
					System.out.println("code:"+mLDHealthSchema.getHealthCode().trim());
					System.out.println("name:"+mLDHealthSchema.getHealthName().trim());
					System.out.println("j:"+j);
%>
					<script language="javascript">					
						parent.fraInterface.HealthGrid.addOne();
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,1,"<%=mLDHealthSchema.getHealthCode()%>");
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,2,"<%=mLDHealthSchema.getHealthName()%>");
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,3,"N");
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

