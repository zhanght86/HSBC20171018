<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWCho.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%
//1-�õ����м�¼����ʾ��¼ֵ
  int index=0;
  int i = 0;
  int TempCount=0;
  int GTempCount = 0;
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tGrpPolNo = "";
  String tPolType = "";
  tPolType = request.getParameter("PolTypeHide");
  //ȡ�ֵ�
  String tGRadio[] = request.getParameterValues("InpGrpGridSel");
  String tGrpCode[] = request.getParameterValues("GrpGrid1");
  String tGClassNum[] = request.getParameterValues("GrpGridNo");
  GTempCount = tGClassNum.length;
  loggerDebug("GroupUWCho","Gcount:"+GTempCount);
  if (GTempCount > 0&&tPolType.equals("2"))
  {
  	for ( i = 0; i < GTempCount; i++)
  	{
  		loggerDebug("GroupUWCho","gradio:"+tGRadio[i]);
  		loggerDebug("GroupUWCho","grpcode:"+tGrpCode[i]);
  		if(tGRadio[i].equals("1")&&!tGrpCode[i].equals(""))
  		{
  			loggerDebug("GroupUWCho","ok");
  			tGrpPolNo = tGrpCode[i];
%>
		<script language="javascript">
			parent.fraInterface.fmQuery.all('GrpProposalNo').value="<%=tGrpPolNo%>";
			parent.fraInterface.queryPol();
		</script>
<%
		}
	}
  }
  
  //�õ�radio�е�����
  loggerDebug("GroupUWCho","---4----");  
  
  String tRadio[] = request.getParameterValues("InpPolGridSel");  
  String tTempClassNum[] = request.getParameterValues("PolGridNo");
  String tPolCode[] = request.getParameterValues("PolGrid1");
  String tProposalCode[] = request.getParameterValues("PolGrid2");
  
  //�õ�check�е�����
  //String tChk[] = request.getParameterValues("PolGridChk");  
  
  int temp = tRadio.length;
  loggerDebug("GroupUWCho","radiolength:"+temp);
  
  //������ 
  LCPolSchema mLCPolSchema = new LCPolSchema();
  
  if(tTempClassNum!=null&&tPolType.equals("1")) //������ǿռ�¼	
  {
  	TempCount = tTempClassNum.length; //��¼��      
   	loggerDebug("GroupUWCho","Start query Count="+TempCount);   
  	while(index<TempCount)
  	{
  		loggerDebug("GroupUWCho","----3-----");
  		loggerDebug("GroupUWCho","polcode:"+tPolCode[index]);
  		loggerDebug("GroupUWCho","radio:"+tRadio[index]);
  		if (!tPolCode[index].equals("")&&tRadio[index].equals("1"))
  		{
  			loggerDebug("GroupUWCho","GridNO="+tTempClassNum[index]);
  			loggerDebug("GroupUWCho","Grid 1="+tPolCode[index]);
  			loggerDebug("GroupUWCho","Grid 2="+tProposalCode[index]);
  			loggerDebug("GroupUWCho","Radio:"+tRadio[index]);
  			
  			//if(tRadio[index].equals("1"))
    			loggerDebug("GroupUWCho","this radio is selected");
    			
    			//��ѯ����ʾ������Ϣ
    			LCPolSchema tLCPolSchema = new LCPolSchema();
    			
    			tLCPolSchema.setPolNo(tPolCode[index]);
    			
    			// ׼���������� VData
			VData tVData = new VData();
			tVData.addElement(tLCPolSchema);

			// ���ݴ���
  			//ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  			String busiName="tbgrpProposalQueryUI";
  		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData,"QUERY||MAIN",busiName))
			{
      				Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      				FlagStr = "Fail";
			}
			else
			{
				tVData.clear();
				tVData = tBusinessDelegate.getResult();
		
				// ��ʾ
				LCPolSet mLCPolSet = new LCPolSet(); 
				mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
				
				
				if (mLCPolSet.size() == 1)
				{
					mLCPolSchema = mLCPolSet.get(1);
					loggerDebug("GroupUWCho","---1---");
				}
				
				loggerDebug("GroupUWCho","proposalno:"+mLCPolSchema.getProposalNo().trim());
					
%>
			<script language="javascript">
				parent.fraInterface.fm.ProposalNo.value="<%=mLCPolSchema.getProposalNo()%>";
          			parent.fraInterface.fm.RiskCode.value="<%=mLCPolSchema.getRiskCode()%>";
         	 		parent.fraInterface.fm.RiskVersion.value="<%=mLCPolSchema.getRiskVersion()%>";
                    		parent.fraInterface.fm.ManageCom.value="<%=mLCPolSchema.getManageCom()%>";
                    		parent.fraInterface.fm.AppntNo.value="<%=mLCPolSchema.getAppntNo()%>";
                    		parent.fraInterface.fm.AppntName.value="<%=mLCPolSchema.getAppntName()%>";
                    		parent.fraInterface.fm.InsuredNo.value="<%=mLCPolSchema.getInsuredNo()%>";
                    		parent.fraInterface.fm.InsuredName.value="<%=mLCPolSchema.getInsuredName()%>";
                    		parent.fraInterface.fm.InsuredSex.value="<%=mLCPolSchema.getInsuredSex()%>";
                    		parent.fraInterface.fm.Mult.value="<%=mLCPolSchema.getMult()%>";
                    		parent.fraInterface.fm.Prem.value="<%=mLCPolSchema.getPrem()%>";
                    		parent.fraInterface.fm.Amnt.value="<%=mLCPolSchema.getAmnt()%>";
                    		parent.fraInterface.fm.PrtNoHide.value="<%=mLCPolSchema.getPrtNo()%>";
                    	</script>
         
<%
			} // end of if
  			loggerDebug("GroupUWCho","---2---");
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError())
				{                          
			    		Content = " ��ѯ�ɹ�! ";
			    		FlagStr = "Succ";
				}
			 	else                                                                           
 			 	{
 			   		Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
  			  		FlagStr = "Fail";
  			 	}
 			}
    			
    			if(tRadio[index].equals("1"))
      				loggerDebug("GroupUWCho","the "+index+" line is checked!");
    			else
      				loggerDebug("GroupUWCho","the "+index+" line is not checked!");
      		}
    		
    		index=index+1;
    		loggerDebug("GroupUWCho","index:"+index);          
	}
}
   
%>  
