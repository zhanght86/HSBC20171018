<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FinFeeSureChk.jsp
//�����ܣ�����ȷ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("FinFeeSureChk","Auto-begin:");

%>
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
	LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();

	String tTempFeeNo[] = request.getParameterValues("PolGrid1");
	String tPayintv[] = request.getParameterValues("PolGrid3");
	String tDate[] = request.getParameterValues("PolGrid7");
	String tChk[] = request.getParameterValues("InpPolGridChk");
	String tChequeNo[] = request.getParameterValues("PolGrid2");
	boolean flag = false;
	int feeCount = tTempFeeNo.length;
	loggerDebug("FinFeeSureChk","feecout:"+feeCount);
	

	for (int i = 0; i < feeCount; i++)
	{
		if (!tTempFeeNo.equals("") && tChk[i].equals("1"))
		{
			if (tDate[i].equals(""))
			{
				Content = " ��¼�뵽������! ";
			}
		else if(tDate[i].length()!=10 )
			{
		  	Content = " �������ڸ�ʽ���������YYYY-MM-DD��ʽ! ";
			}
			else
			{
				loggerDebug("FinFeeSureChk","TempFeeNo:"+i+":"+tTempFeeNo[i]);
	  			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		    		tLJTempFeeClassSchema.setTempFeeNo( tTempFeeNo[i] );
		    		tLJTempFeeClassSchema.setPayMode( tPayintv[i] );
		    		tLJTempFeeClassSchema.setEnterAccDate( tDate[i] );
	          tLJTempFeeClassSchema.setChequeNo( tChequeNo[i] ); 
		    		tLJTempFeeClassSet.add( tLJTempFeeClassSchema );
		    		flag = true;
		    	}
		}
	}
	loggerDebug("FinFeeSureChk","tLJTempFeeClassSet:" + tLJTempFeeClassSet.encode());

try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLJTempFeeClassSet );
		tVData.add( tG );
		
		// ���ݴ���
		//FinFeeSureUI tFinFeeSureUI   = new FinFeeSureUI();
	
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	
		if (tBusinessDelegate.submitData(tVData,"INSERT","FinFeeSureUI") == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			loggerDebug("FinFeeSureChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " ����ȷ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����ȷ�ϳɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
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
	parent.fraInterface.initPolGrid();
</script>
</html>
