<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FinFeeSureChk.jsp
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("CustomerHBChk","Auto-begin:");

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.customer.*"%>
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
	FICustomerAccSet tFICustomerAccSet = new FICustomerAccSet();
	
	//tongmeng 2012-02-15 modify
	FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
	
	FICustomerAccSchema tFICustomerAccSchema1 = new FICustomerAccSchema();
	    	
	    		
	String tRadio[] = request.getParameterValues("InpCustomerGridSel");  
	
	
	String tInsuAccNo[] = request.getParameterValues("CustomerGrid1");
	
	String tCustomerNo[] = request.getParameterValues("CustomerGrid2");
	
	
                         //������ʽ=�� Inp+MulLine������+Sel�� 
      for (int index=0; index< tRadio.length;index++)
      {
        if(tRadio[index].equals("1"))
        {
        	tFICustomerAccSchema.setInsuAccNo( tInsuAccNo[index] );
    		tFICustomerAccSchema.setCustomerNo( tCustomerNo[index] );
    		break;
        }
      }

    String tRadio1[] = request.getParameterValues("InpCustomer1GridSel");  
    String tInsuAccNo1[] = request.getParameterValues("Customer1Grid1");
    String tCustomerNo1[] = request.getParameterValues("Customer1Grid2");	
    
    for (int index=0; index< tRadio1.length;index++)
    {
      if(tRadio1[index].equals("1"))
      {
    	   tFICustomerAccSchema1.setInsuAccNo( tInsuAccNo1[index] );
    	   tFICustomerAccSchema1.setCustomerNo( tCustomerNo1[index] );
    	   break;
      }
    }
	
	
	

	  		
	

	try
	{
			// ׼���������� VData
			VData tVData = new VData();
			tVData.add( tFICustomerAccSchema );
			tVData.add( tFICustomerAccSchema1 );
			tVData.add( tG );			
			// ���ݴ���
			
			 //��ӿͻ��˻�����
            //FICustomerMain tFICustomerMain = new FICustomerMain();
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            //���ÿͻ��˻��շѽӿڣ������־HB
            
            //if(tFICustomerMain.submitData(tVData, "HB"))
            //{
            //}
            //else
            //{FlagStr = "Fail";
            //	Content = " �ͻ��˻��ϲ�ʧ�ܣ�ԭ����: " + tFICustomerMain.mErrors.getError(0).errorMessage;
            //}
            if(tBusinessDelegate.submitData(tVData, "HB","FICustomerMain"))
            {
            }
            else
            {FlagStr = "Fail";
            	Content = " �ͻ��˻��ϲ�ʧ�ܣ�ԭ����: " +  tBusinessDelegate.getCErrors().getError(0).errorMessage;
            }
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
			    //tError = tFICustomerMain.mErrors;
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
			    	Content = " �ͻ��˻��ϲ��ɹ�! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
			    	Content = " �ͻ��˻��ϲ�ʧ�ܣ�ԭ���ǣ�";
			    	int n = tError.getErrorCount();
	    			if (n > 0)
	    			{
						for(int i = 0;i < n;i++)
						{
						  //tError = tErrors.getError(i);
						  Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
						}
					}
			    	FlagStr = "Fail";
			    }
			}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Content = Content.trim() +" ��ʾ:�쳣�˳�.";
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
