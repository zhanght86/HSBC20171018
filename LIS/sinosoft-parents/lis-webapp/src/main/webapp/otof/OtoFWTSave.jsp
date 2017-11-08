<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.lis.otof.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import="java.util.Date"%>

<%
    loggerDebug("OtoFWTSave","begin");
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
	  String tManageCom = request.getParameter("ManageCom");
	  String tAccountDate = request.getParameter("AccountDate");
	  String Flag="0";       //手工提取

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
		    loggerDebug("OtoFWTSave",Content);
	  }


	  if (flag.equals("true"))
	  {
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
		        TransferData tTransferData = new TransferData();
					  tTransferData.setNameAndValue("mStartDate", bdate);
					  tTransferData.setNameAndValue("mEndDate", edate);
					  tTransferData.setNameAndValue("itemp", itemp);
				    tTransferData.setNameAndValue("mInputDate", tAccountDate);
				    tTransferData.setNameAndValue("cManageCom", tManageCom);
				    tTransferData.setNameAndValue("DateFlag", Flag);
				    VData vData = new VData();
				    vData.addElement(tG);
            vData.addElement(tTransferData);	
		        loggerDebug("OtoFWTSave","加载完成");
            OtoFWTBL tOtoFWTBL = new OtoFWTBL();
				  	if( !tOtoFWTBL.submitData(vData, "Buss") )
				  	{
								FlagStr = "Fail";
								Content = "提数失败，原因是：" + tOtoFWTBL.mErrors.getFirstError();
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

