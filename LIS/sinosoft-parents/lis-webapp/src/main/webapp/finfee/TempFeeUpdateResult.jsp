<%
//�������ƣ�TempFeeQureyResult.jsp
//�����ܣ�
//�������ڣ�
//������  ��yaory
//���¼�¼��  ������    ��������     ����ԭ��/����
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.service.*" %>
	
<%
  
  CErrors tError = null;  
  String Content = "";
  String FlagStr = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
 
  String TempFeeNo   = request.getParameter("TempFeeNo");
  String TOtherNo     = request.getParameter("OtherNo");
  
  
  TransferData mTransferData = new TransferData();

  
  int recordCount = 0;
  String tOpt=request.getParameter("Opt");

  LJTempFeeSet  mLJTempFeeSet=new LJTempFeeSet(); 
  LJTempFeeClassSet mLJTempFeeClassSet=new LJTempFeeClassSet(); 
  LJTempFeeSchema tLJTempFeeSchema=new LJTempFeeSchema();
  LJTempFeeClassSchema tLJTempFeeClassSchema=new LJTempFeeClassSchema();
  VData tVData = new VData();

  if (tOpt.equals("QUERY")) 
  { 
  //�ݽ��ѱ�  
	  tVData.addElement(tG);
	  tLJTempFeeSchema = new LJTempFeeSchema(); 
	  tLJTempFeeSchema.setTempFeeNo(TempFeeNo); 
	  tLJTempFeeSchema.setOtherNo(TOtherNo);
	  tLJTempFeeSchema.setOtherNoType("4");
	  tVData.add(tLJTempFeeSchema);
	  tVData.addElement(mTransferData);
  
  	//TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();

		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

   if (!tBusinessDelegate.submitData(tVData,"QUERYUpd","TempFeeQueryUI"))
    {
	    Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	    FlagStr = "Fail";
    }   
   else
    {   
		tVData.clear();
		mLJTempFeeSet = new LJTempFeeSet();
		mLJTempFeeClassSet = new LJTempFeeClassSet();
		tVData = tBusinessDelegate.getResult();
	    mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0)); //�����������    
		mLJTempFeeClassSet.set((LJTempFeeClassSet)tVData.getObjectByObjectName("LJTempFeeClassSet",0));                     
       
       //�ݽ��ѱ�õ���¼���ݣ�
      //�õ����ϲ�ѯ�����ļ�¼����Ŀ������ѭ��ʱ�õ�
       recordCount=mLJTempFeeSet.size();
       if (recordCount>0)
       {
      %>
      <script language="javascript">
   		  parent.fraInterface.document.all("Frame1").style.display= "";
		  parent.fraInterface.document.all("Frame2").style.display= "";
		  parent.fraInterface.document.all("ManageCom").value="<%=mLJTempFeeSet.get(1).getManageCom()%>"; 
		  parent.fraInterface.document.all("PayDate").value="<%=mLJTempFeeSet.get(1).getPayDate()%>";
		  parent.fraInterface.document.all("AgentCode").value="<%=mLJTempFeeSet.get(1).getAgentCode()%>";
		  parent.fraInterface.document.all("AgentGroup").value="<%=mLJTempFeeSet.get(1).getAgentGroup()%>";
		  parent.fraInterface.document.all("TempFeeType").value="<%=mLJTempFeeSet.get(1).getTempFeeType()%>";	
		  parent.fraInterface.document.all("TempFeeType").value="<%=mLJTempFeeSet.get(1).getTempFeeType()%>";	
      </script>
 <%      
         String strRecord="0|"+recordCount+"^";
         strRecord=strRecord+mLJTempFeeSet.encode();       
%>
      <script language="javascript">
        //����js�ļ�����ʾ���ݵĺ���
          parent.fraInterface.showRecord("<%=strRecord%>"); 
      </script>                
<%
		recordCount=mLJTempFeeClassSet.size();
		strRecord="0|"+recordCount+"^";
        strRecord=strRecord+mLJTempFeeClassSet.encode();       
%>
      <script language="javascript">
        //����js�ļ�����ʾ���ݵĺ���
        parent.fraInterface.showRecord1("<%=strRecord%>"); 
      </script>                
<%
    	}
    }   

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " ��ѯ�ɹ�";
      FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
   }
 }

  if (tOpt.equals("SAVE")) 
  { 
		        
	        //���ǰ����е����ݶ���ʾ����(��̨������Ȼ��ֱ��DELETE||INSERT
	      String TempGridNo[] = request.getParameterValues("TempGridNo");	        
        String TempFeeNo2[]  = request.getParameterValues("TempGrid1");
        String RiskCode[]     = request.getParameterValues("TempGrid2");
        String PayMoney[]   = request.getParameterValues("TempGrid3");
       // String PayDate[]     = request.getParameterValues("TempGrid4");
        String OtherNo[] = request.getParameterValues("TempGrid5");
        String PayIntv[] = request.getParameterValues("TempGrid6");
        String PayEndYear[] = request.getParameterValues("TempGrid7");
        String tManageCom = request.getParameter("ManageCom");
				String tPayDate = request.getParameter("PayDate");
				String tAgentCode=request.getParameter("AgentCode");
				String tAgentGroup=request.getParameter("AgentGroup");
				String tTempFeeType=request.getParameter("TempFeeType");

        
        loggerDebug("TempFeeUpdateResult","tempfee no ======="+TempGridNo.length);
        mLJTempFeeSet = new LJTempFeeSet();

        for (int i = 0; i < TempGridNo.length; i++)
        {
        
	        tLJTempFeeSchema=new LJTempFeeSchema();
	        tLJTempFeeSchema.setTempFeeNo(TempFeeNo2[i]);
	        tLJTempFeeSchema.setTempFeeType(tTempFeeType);
	        tLJTempFeeSchema.setRiskCode(RiskCode[i]);
	        tLJTempFeeSchema.setPayMoney(PayMoney[i]);
	        tLJTempFeeSchema.setPayDate(tPayDate);
	        tLJTempFeeSchema.setOtherNo(OtherNo[i]);
	        tLJTempFeeSchema.setPayIntv(PayIntv[i]);
	        tLJTempFeeSchema.setPayYears(PayEndYear[i]);
	        tLJTempFeeSchema.setManageCom(tManageCom);
					tLJTempFeeSchema.setAgentCode(tAgentCode);
					tLJTempFeeSchema.setAgentGroup(tAgentGroup);
          mLJTempFeeSet.add(tLJTempFeeSchema);       
        }
        
        String LJTempFeeClassNum[] = request.getParameterValues("TempClassToGridNo");
        String TempFeeNo1[] = request.getParameterValues("TempClassToGrid1");
        String PayMode[] = request.getParameterValues("TempClassToGrid2");
        String PayMoney1[] = request.getParameterValues("TempClassToGrid3");
        String ChequeNo[] = request.getParameterValues("TempClassToGrid5");
        String EnterAccDate[] = request.getParameterValues("TempClassToGrid6");
        String InBankCode[] = request.getParameterValues("TempClassToGrid7");
        String InBankAccNo[] = request.getParameterValues("TempClassToGrid8");
        String InAccName[] = request.getParameterValues("TempClassToGrid9");	
        String IDType[] = request.getParameterValues("TempClassToGrid12");
        String IDNo[] = request.getParameterValues("TempClassToGrid13");	
        
        mLJTempFeeClassSet = new LJTempFeeClassSet();

        for (int j = 0; j < LJTempFeeClassNum.length; j++)
        {
        
          tLJTempFeeClassSchema=new LJTempFeeClassSchema();
          tLJTempFeeClassSchema.setTempFeeNo(TempFeeNo1[j]);
          tLJTempFeeClassSchema.setPayMode(PayMode[j]);
          tLJTempFeeClassSchema.setPayMoney(PayMoney1[j]);
          tLJTempFeeClassSchema.setPayDate(tPayDate);
          tLJTempFeeClassSchema.setChequeNo(ChequeNo[j]);
          tLJTempFeeClassSchema.setEnterAccDate(EnterAccDate[j]);
          tLJTempFeeClassSchema.setBankCode(InBankCode[j]);
          tLJTempFeeClassSchema.setBankAccNo(InBankAccNo[j]);
          tLJTempFeeClassSchema.setAccName(InAccName[j]);
          //tLJTempFeeClassSchema.setOtherNo(OtherNoClass[j]);
          //tLJTempFeeClassSchema.setOtherNoType(OtherNoTypeClass[j]);
          tLJTempFeeClassSchema.setIDType(IDType[j]);
          tLJTempFeeClassSchema.setIDNo(IDNo[j]);
					tLJTempFeeClassSchema.setManageCom(tManageCom);
					
          mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
        
        }
        
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("orign", mLJTempFeeSet);  
        loggerDebug("TempFeeUpdateResult","kkkkkkkkkkkkkkkkk");
        tVData.clear();
        tVData.addElement(tTransferData); 
        tVData.addElement(tG);
         
        tVData.add(mLJTempFeeSet);  //��Ҫ�޸ĵ�����
        tVData.add(mLJTempFeeClassSet); //��Ҫ�޸ĵ����� 
           
  
		  //TempFeeUpdateUI tTempFeeUpdateUI = new TempFeeUpdateUI();
		  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 
		  if (!tBusinessDelegate.submitData(tVData,"UPDATE","TempFeeUpdateUI"))
		  {
		     loggerDebug("TempFeeUpdateResult","�޸�ʧ��!");
		     Content = " �޸�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		     FlagStr = "Fail";
		   }   
		   else
		    {  
				  Content="�����ɹ�!"; 
				  FlagStr = "Succ";
					loggerDebug("TempFeeUpdateResult","�����ɹ�!");
		}
 }
%>                                        
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
