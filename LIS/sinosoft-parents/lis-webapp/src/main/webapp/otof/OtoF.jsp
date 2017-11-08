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
	    Content="请录入日期1!";
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
	   Content="日期录入错误1!";
	   loggerDebug("OtoF",Content);
	
	}

	loggerDebug("OtoF","开始提取凭证");
	String tFlag = "0"; //手工提取

	VData vData = new VData();
	OtoFUI tOtoFUI = new OtoFUI();
	if (flag.equals("true"))
	{
	  if (tOpt.equals("Buss"))
	  {
	    String tAccountDate = request.getParameter("AccountDate");
			 
	    int vouchMin = 1;        //没有录入凭证编码的情况下，默认提取业务凭证1～9
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
	
	       loggerDebug("OtoF","加载完成");
	
		   if( !tOtoFUI.submitData(vData, tOpt) )
		   {
				FlagStr = "Fail";
				Content = "提数失败，原因是：" + tOtoFUI.mErrors.getFirstError();
		   }
		   else
		   {
	    		FlagStr = "True";
	    		Content = "提数成功！";
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

