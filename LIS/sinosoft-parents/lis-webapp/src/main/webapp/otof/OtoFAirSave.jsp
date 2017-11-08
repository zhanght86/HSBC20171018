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
	  String tManageCom = request.getParameter("ManageCom");
	  String tAccountDate = request.getParameter("AccountDate"); //记账日期
	  String tFlag="1";       //手工提取
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
		    loggerDebug("OtoFAirSave",Content);
	  }

		loggerDebug("OtoFAirSave","开始提取凭证");

		VData vData = new VData();
	  OtoFAirPolBL tOtoFAirPolBL = new OtoFAirPolBL();
	  if (flag.equals("true"))
	  {
				//"Air"
				Integer itemp = new Integer(tVouchNo) ;
				vData = new VData();
        TransferData tTransferData = new TransferData();
        vData.addElement(tG);
				tTransferData.setNameAndValue("bdate", bdate);
				tTransferData.setNameAndValue("edate", edate);
				tTransferData.setNameAndValue("itemp", itemp);
				tTransferData.setNameAndValue("DateFlag", tFlag);
				tTransferData.setNameAndValue("accountdate", tAccountDate); //记帐日期
				tTransferData.setNameAndValue("managecom", tManageCom); //管理机构
				vData.addElement(tTransferData);
				loggerDebug("OtoFAirSave","加载完成,begin OtoFAirPolBL---");
		  	if( !tOtoFAirPolBL.submitData(vData, "AirPol") )
		  	{
						FlagStr = "Fail";
						Content = "提数失败，原因是：" + tOtoFAirPolBL.mErrors.getFirstError();
		  	}
		  	else
		  	{
		    		FlagStr = "True";
		    		Content = "提数成功！";
				}
			
		}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

