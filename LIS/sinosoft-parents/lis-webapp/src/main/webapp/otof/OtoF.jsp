<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.otof.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import="java.util.Date"%>

<%

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");


	String flag = "true";
	String FlagStr = "";
	String Content = "";
	String bdate = request.getParameter("Bdate");
	String edate = request.getParameter("Edate");
	String tOpt = request.getParameter("Opt");
	String tVouchNo = request.getParameter("VouchNo");
	String tManageCom = request.getParameter("ManageCom");
	CErrors tError = null;
	if (bdate.equals("") && edate.equals(""))
	{
	    flag = "false";
	    Content="��¼������1!";
	}
	
	if (!bdate.equals("") && edate.equals(""))
	{
	    edate = bdate;
	}
	
	if (bdate.equals("") && !edate.equals(""))
	{
	    bdate = edate;
	}
	
	FDate chgdate = new FDate();
	Date dbdate = chgdate.getDate(bdate);
	Date dedate = chgdate.getDate(edate);
	
	if(dbdate.compareTo(dedate) > 0)
	{
	   flag = "false";
	   Content="����¼�����1!";
	   loggerDebug("OtoF",Content);
	
	}

	loggerDebug("OtoF","��ʼ��ȡƾ֤");
	String tFlag = "0"; //�ֹ���ȡ

	VData vData = new VData();
	OtoFUI tOtoFUI = new OtoFUI();
	if (flag.equals("true"))
	{
	  if (tOpt.equals("Buss"))
	  {
	    String tAccountDate = request.getParameter("AccountDate");
			 
	    int vouchMin = 1;        //û��¼��ƾ֤���������£�Ĭ����ȡҵ��ƾ֤1��9
	    int vouchMax = 8;
	    if (tVouchNo != null && tVouchNo.trim().length()!=0)
	    {
	     	vouchMax = Integer.parseInt(tVouchNo);
	     	vouchMin = Integer.parseInt(tVouchNo);
	    }
	    
	    for (int i = vouchMin ; i <= vouchMax ; i++)
	    {  
	       Integer itemp = new Integer(i) ;
	   	   vData = new VData();
   		   TransferData tTransferData = new TransferData();
	   	   tTransferData.setNameAndValue("mStartDate",bdate);
	   	   tTransferData.setNameAndValue("mEndDate",edate);
	   	   tTransferData.setNameAndValue("itemp",itemp);
	   	   tTransferData.setNameAndValue("DateFlag",tFlag);
	   	   tTransferData.setNameAndValue("mInputDate",tAccountDate);
	   	   tTransferData.setNameAndValue("cManageCom",tManageCom);
	       vData.addElement(tG);
	       vData.addElement(tTransferData);
	
	       loggerDebug("OtoF","�������");
	
		   if( !tOtoFUI.submitData(vData, tOpt) )
		   {
				FlagStr = "Fail";
				Content = "����ʧ�ܣ�ԭ���ǣ�" + tOtoFUI.mErrors.getFirstError();
		   }
		   else
		   {
	    		FlagStr = "True";
	    		Content = "�����ɹ���";
		   }
	    }
	  }	  
	}


%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

