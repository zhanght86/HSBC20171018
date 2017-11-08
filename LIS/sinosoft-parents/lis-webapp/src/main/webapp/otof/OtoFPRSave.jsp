<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.lis.otof.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import="java.util.Date"%>

<%

	 GlobalInput tG1 = new GlobalInput();
	 tG1 = (GlobalInput)session.getValue("GI");
	 GlobalInput tG = new GlobalInput();
	 tG.setSchema(tG1);
		
	 String flag = "true";
	 String FlagStr = "";
	 String Content = "";
	 String bdate = request.getParameter("Bdate");
	 String edate = request.getParameter("Edate");
	 String tOpt = request.getParameter("Opt");
   String tVouchNo = request.getParameter("VouchNo");
	 Integer itemp = new Integer(tVouchNo) ;
	 String tManageCom = request.getParameter("ManageCom");
	 String rType = request.getParameter("RType");
	 String tAccountDate = request.getParameter("AccountDate");
	 String Flag="1";       //手工提取
    
	  if (bdate.equals("") && edate.equals(""))
	  {
	      flag = "false";
	      Content="请录入起始或者终止日期!";
	      return ;
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
		    Content="终止日期大于起始日期!";
		    loggerDebug("OtoFPRSave",Content);
	  }

	  loggerDebug("OtoFPRSave","开始提取凭证");
	  String tFlag = "0"; //手工提取

	  VData vData = new VData();
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("bDate", bdate);
	  tTransferData.setNameAndValue("eDate", edate);
    tTransferData.setNameAndValue("accountDate", tAccountDate);
    tTransferData.setNameAndValue("itemp", itemp);
    tTransferData.setNameAndValue("Managecom", tManageCom);
    tTransferData.setNameAndValue("Flag", Flag);
	  OtoFReceiPremBL tOtoFReceiPremBL = new OtoFReceiPremBL();
	  if (flag.equals("true"))
	  {
		  if (rType.equals("1"))
		  {  			   			 				
				vData = new VData();
				vData.addElement(tG);
        vData.addElement(tTransferData);

			  loggerDebug("OtoFPRSave","加载完成");
		  	if( !tOtoFReceiPremBL.submitData(vData, "Prem"))
		  	{
				FlagStr = "Fail";
				Content = "提数失败，原因是：" + tOtoFReceiPremBL.mErrors.getFirstError();
		  	}
		  	else
		  	{
		        FlagStr = "True";
		    	Content = "提数成功！";
			}			  
		  }
		  else
		  {

				vData = new VData();
				vData.addElement(tG);
        vData.addElement(tTransferData);

				loggerDebug("OtoFPRSave","加载完成");
		  	if( !tOtoFReceiPremBL.submitData(vData, "PRINT"))
		  	{
					FlagStr = "Fail";
					Content = "提数失败，原因是：" + tOtoFReceiPremBL.mErrors.getFirstError();
		  	}
		  	else
		  	{
		    	FlagStr = "True";
		    	Content = "提数成功！";
			}
		  }
		}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

