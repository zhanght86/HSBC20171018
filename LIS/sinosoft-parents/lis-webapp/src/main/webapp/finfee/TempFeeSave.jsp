 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�TempFeeSave.jsp
//�����ܣ�
//�������ڣ�2002-07-22 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>  
<%@page import="com.sinosoft.service.*" %>
<%
// �������
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
   String CurrentDate = PubFun.getCurrentDate();
    String ShowFinance="N"; //��ʾ���ɽ����Ϣ�ı�ǣ���������շ���ȫ��ȷ������ʾ������ϸ��Ϣ�� 
	 String Enteraccdate = CurrentDate;
	double CashValue=0;
	double ChequeValue=0;
	loggerDebug("TempFeeSave","111111111111111111111");
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("TempFeeSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
	   String Operator  = tGI.Operator ;  //�����½����Ա�˺�
	   //String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
	   String CERTIFY_XQTempFee=request.getParameter("CERTIFY_XQTempFee");
	   
	   LJTempFeeSchema tLJTempFeeSchema;
		 LJTempFeeClassSchema tLJTempFeeClassSchema;
	   LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet(); 
	       
		 String tTempClassNum[]   = request.getParameterValues("TempClassToGridNo");
	   String tCTempFeeNo[]     = request.getParameterValues("TempClassToGrid1");
		 String tCPayMode[]       = request.getParameterValues("TempClassToGrid2");
		 String tCPayDate[]       = request.getParameterValues("TempClassToGrid3");
		 String tCCurrency[]      = request.getParameterValues("TempClassToGrid4");
		 String tCPayMoney[]      = request.getParameterValues("TempClassToGrid5");
		 String tCEnterAccDate[]  = request.getParameterValues("TempClassToGrid6");
		 String tCManageCom[]     = request.getParameterValues("TempClassToGrid7");
		
		 String tCChequeNo[]      = request.getParameterValues("TempClassToGrid8");
		 String tChequeDate[]     = request.getParameterValues("TempClassToGrid9");
		 String tBankcode[]       = request.getParameterValues("TempClassToGrid10");
		 String tBankaccno[]      = request.getParameterValues("TempClassToGrid11");
		 String tAccname[]        = request.getParameterValues("TempClassToGrid12");
			//MS���������еĻ������⣬����ֻ��Ҫ��¼�շ����м���	 		
		 //ֻ�����շѱ��¼�����ڼ�ͽ������ޣ�����¼���ѵ�λ��Ϣ	
		 String tIDType[]         = request.getParameterValues("TempClassToGrid19");
		 String tIDNo[]           = request.getParameterValues("TempClassToGrid20");
		 String tTempfeeNoType[]           = request.getParameterValues("TempClassToGrid21");
					
		 int TempClassCount = tTempClassNum.length;	
		
		 for (int i = 0; i < TempClassCount; i++)
	   {
	      tLJTempFeeClassSchema   = new LJTempFeeClassSchema();
		    tLJTempFeeClassSchema.setTempFeeNo(tCTempFeeNo[i]);
		    tLJTempFeeClassSchema.setPayMode(tCPayMode[i]);	    	    
		    tLJTempFeeClassSchema.setPayDate(tCPayDate[i]);	 
		    tLJTempFeeClassSchema.setCurrency(tCCurrency[i]);
		    tLJTempFeeClassSchema.setPayMoney(tCPayMoney[i]);
		    tLJTempFeeClassSchema.setChequeNo(tCChequeNo[i]);
		    tLJTempFeeClassSchema.setChequeDate(tChequeDate[i]);
		    tLJTempFeeClassSchema.setEnterAccDate(tCEnterAccDate[i]);   
		    tLJTempFeeClassSchema.setManageCom(tGI.ManageCom);
		    tLJTempFeeClassSchema.setPolicyCom(tCManageCom[i]);
		    tLJTempFeeClassSchema.setBankCode(tBankcode[i]);
		    tLJTempFeeClassSchema.setBankAccNo(tBankaccno[i]);
		    tLJTempFeeClassSchema.setAccName(tAccname[i]);	
		    tLJTempFeeClassSchema.setIDType(tIDType[i]);
		    tLJTempFeeClassSchema.setIDNo(tIDNo[i]);	            	    
		    tLJTempFeeClassSchema.setOperator(Operator);	
		    tLJTempFeeClassSchema.setOperState("0");	
		    tLJTempFeeClassSchema.setTempFeeNoType(tTempfeeNoType[i]);	    	    
		    tLJTempFeeClassSet.add(tLJTempFeeClassSchema);		          		      
		 } 
	
	 loggerDebug("TempFeeSave","tLJTempFeeSchema:");
	
	  // ���ձ���Ϣ��¼��
	   LJTempFeeSet tLJTempFeeSet   = new LJTempFeeSet();
		 String tTempNum[]      = request.getParameterValues("TempToGridNo");
		 String tTempFeeNo[]    = request.getParameterValues("TempToGrid1");
		 String tTempFeeType[]  = request.getParameterValues("TempToGrid2");
		 String tPayDate[]      = request.getParameterValues("TempToGrid3");
		 String tCurrency[]     = request.getParameterValues("TempToGrid4");
		 String tPayMoney[]     = request.getParameterValues("TempToGrid5");
		 String tEnterAccDate[] = request.getParameterValues("TempToGrid6");
		 String tManageCom[]    = request.getParameterValues("TempToGrid7"); 	
		 String tRiskCode[]     = request.getParameterValues("TempToGrid8");
	 	 String tAgentGroup[]   = request.getParameterValues("TempToGrid9");					
	   String tAgentCode[]    = request.getParameterValues("TempToGrid10");
		 String tOtherNo[]      = request.getParameterValues("TempToGrid11");
	   String tPayIntv[]      = request.getParameterValues("TempToGrid13");
		 String tPayYears[]     = request.getParameterValues("TempToGrid14");	
	   String tComment[]      = request.getParameterValues("TempToGrid17");	
	   String tCertifyFlag  = request.getParameter("CertifyFlagHidden");
	   String tClaimFeeType  = request.getParameter("ClaimFeeTypeHidden");
	   String tCardNo[]  = request.getParameterValues("TempToGrid15");

		 int TempCount = tTempNum.length; 		
		 loggerDebug("TempFeeSave","TempCount:"+TempCount);
		for (int i = 0; i < TempCount; i++)
		{   
	      tLJTempFeeSchema   = new LJTempFeeSchema();
		    tLJTempFeeSchema.setTempFeeNo(tTempFeeNo[i]);
		    tLJTempFeeSchema.setTempFeeType(tTempFeeType[i]);
		    tLJTempFeeSchema.setRiskCode(tRiskCode[i]);	    	    	    	    	    	    	    	    		    	    
		    tLJTempFeeSchema.setAgentGroup(tAgentGroup[i]);
		    tLJTempFeeSchema.setAgentCode(tAgentCode[i]);
		    tLJTempFeeSchema.setPayDate(tPayDate[i]);
		    tLJTempFeeSchema.setEnterAccDate(tEnterAccDate[i]); //�������� 	
		    tLJTempFeeSchema.setCurrency(tCurrency[i]);
		    tLJTempFeeSchema.setPayMoney(tPayMoney[i]);
		    tLJTempFeeSchema.setManageCom(tGI.ManageCom);	    	    	    
		    tLJTempFeeSchema.setPolicyCom(tManageCom[i]);
		    tLJTempFeeSchema.setOtherNo(tOtherNo[i]);
		    tLJTempFeeSchema.setOperator(Operator);	
			  tLJTempFeeSchema.setPayIntv(tPayIntv[i]);
			  tLJTempFeeSchema.setPayYears(tPayYears[i]);    	 
			  tLJTempFeeSchema.setOperState("0"); 	//����ŵ�ǩ������������Ϊ0 
			  tLJTempFeeSchema.setSerialNo(tCardNo[i]);  //�����վݺ�	    
			  tLJTempFeeSchema.setState(request.getParameter("CardCode"));
			  tLJTempFeeSchema.setRemark(tCardNo[i]); //�����վݺ�	 zy 2009-06-29 18:06 ���������վݺű����ǣ����Դ�����µ��ֶ���

		    if(tTempFeeType[i].equals("5")||tTempFeeNo[i]=="5")
		    {    
		     tLJTempFeeSchema.setEnterAccDate(Enteraccdate);
		     tLJTempFeeSchema.setRemark(tComment[i]);
		    }
		    if("1".equals(tTempFeeType[i]))
		    {
		    	 tLJTempFeeSchema.setTempFeeNoType(tCertifyFlag);  
		    }
		    if("6".equals(tTempFeeType[i]))
		    {
		    	 tLJTempFeeSchema.setTempFeeNoType(tClaimFeeType);  
		    }
		    tLJTempFeeSet.add(tLJTempFeeSchema);
		} 
	

	  
	  
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("Type","0"); //Ϊ���ж��ǽ���¼�뻹�����¼�룬0�����ֹ�¼��
	   TransferData tTransferData1 = new TransferData();
	   tTransferData1.setNameAndValue("CERTIFY_XQTempFee",CERTIFY_XQTempFee);   
	   // ׼���������� VData
	   VData tVData = new VData();
		 tVData.addElement(tLJTempFeeSet);
		 tVData.addElement(tLJTempFeeClassSet);
	     tVData.add(tTransferData1);
		 tVData.addElement(tGI); 	    
	      // ���ݴ���
	   //TempFeeUI tTempFeeUI   = new TempFeeUI();
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   
	   loggerDebug("TempFeeSave","go to bl !!!!!!!!!");
	    
	  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	   try
	    {
		   if(!tBusinessDelegate.submitData(tVData,"INSERT","TempFeeUI"))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = "ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content ="ʧ��";
						FlagStr = "Fail";				
				   }
			  }
		   else
			  {
			     	Content = "�����ɹ�! ";
			      	FlagStr = "Succ";
			      	
			      	String []errTempFeeNo=(String[])tBusinessDelegate.getResult().getObjectByObjectName("String",0);
			        if(errTempFeeNo!=null)
			        {
			          for(int n=0;n<errTempFeeNo.length;n++)
			          {
			            if(errTempFeeNo[n]==null) 
			            {
			              if(n==0) //���û�м�¼ֱ���˳�
			              {
			                ShowFinance="Y"; //��ʾ���ɽ����Ϣ�ı��Ϊ��
			                break; 
			              }
			              Content=Content+" )  ԭ������ǣ�1-�ü�¼���Ѿ�¼�룻2-�ݽ��ѺŶ�Ӧ�����ֱ���ѡ������";
			              Content=Content+". ����ȷ�ϻ��ѯ�����²�����Щ��¼!";
			              break;          //�˳�
			                             
			            }
			            if(n==0)
			            {
			             Content="�������,���������ݽ��Ѽ�¼�Ѿ�����,���ܱ��� ( ";
			            }
			            Content=Content+(n+1)+": "+errTempFeeNo[n]+" | ";
			            
			            if(n==errTempFeeNo.length-1) //�������������
			            {
			              Content=Content+" ) ����ȷ�ϻ��ѯ�����²�����Щ��¼! ";
			            }
			            
			          } //end for
			          
			     	 } //end if
				     else
				     	{
				     	  ShowFinance="Y"; //��ʾ���ɽ����Ϣ�ı��Ϊ��
				     	}
			  }	      
	    } 
	   catch(Exception ex)
	    {
	       Content = " ʧ�ܣ�ԭ��:" + ex.toString();
	       FlagStr = "Fail";    
	    }    
} //ҳ����Ч��
%>                      
<html>
<script language="javascript">
       if("<%=FlagStr%>"=="Succ") //�����ɹ�
       {
           parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");          

       }
       else//����ʧ��
       {
        parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
       }
</script>
</html>

