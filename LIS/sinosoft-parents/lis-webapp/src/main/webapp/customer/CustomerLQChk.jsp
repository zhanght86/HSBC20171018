<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FinFeeSureChk.jsp
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("CustomerLQChk","Auto-begin:");

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
	FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
	String tChk[] = request.getParameterValues("InpCustomerGridChk");
	String tSequence[] = request.getParameterValues("CustomerGrid12");
	String tOperationType[] = request.getParameterValues("CustomerGrid6");	
	String tOperationNo[] = request.getParameterValues("CustomerGrid5");
	
	boolean flag = false;
	int Count = tSequence.length;
	loggerDebug("CustomerLQChk","cout:"+Count);

	for (int i = 0; i < Count; i++)
	{
		if (!tSequence.equals("") && tChk[i].equals("1"))
		{
	  			FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
	    		tFICustomerAccTraceSchema.setSequence( tSequence[i] );
	    		tFICustomerAccTraceSchema.setOperationType( tOperationType[i] );
			  	tFICustomerAccTraceSchema.setOperationNo( tOperationNo[i] );
	    		tFICustomerAccTraceSet.add( tFICustomerAccTraceSchema );
	    		flag = true;
		    
		}
	}
	loggerDebug("CustomerLQChk","tFICustomerAccTraceSet:" + tFICustomerAccTraceSet.encode());

	try
	{
	  	if (flag == true)
	  	{
			// ׼���������� VData
			VData tVData = new VData();
			tVData.add( tFICustomerAccTraceSet );
			tVData.add( tG );			
			// ���ݴ���
			
			 //��ӿͻ��˻�����
            //FICustomerMain tFICustomerMain = new FICustomerMain();
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            //���ÿͻ��˻��շѽӿڣ������־LQ
            //if(tFICustomerMain.submitData(tVData, "LQ"))
            //{
            //}
            //else
            //{FlagStr = "Fail";
            //	Content = " �����ȡʧ�ܣ�ԭ����: " + tFICustomerMain.mErrors.getError(0).errorMessage;
            //}
            if(tBusinessDelegate.submitData(tVData, "LQ","FICustomerMain"))
            {
            }
            else
            {
              FlagStr = "Fail";
            	Content = " �����ȡʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            }
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
			    //tError = tFICustomerMain.mErrors;
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
			    	Content = " �����ȡ�ɹ�! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
			    	Content = " �����ȡʧ�ܣ�ԭ���ǣ�";
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
