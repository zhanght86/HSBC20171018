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
	  String tAccountDate = request.getParameter("AccountDate"); //��������
	  String tFlag="1";       //�ֹ���ȡ
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
		    loggerDebug("OtoFAirSave",Content);
	  }

		loggerDebug("OtoFAirSave","��ʼ��ȡƾ֤");

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
				tTransferData.setNameAndValue("accountdate", tAccountDate); //��������
				tTransferData.setNameAndValue("managecom", tManageCom); //�������
				vData.addElement(tTransferData);
				loggerDebug("OtoFAirSave","�������,begin OtoFAirPolBL---");
		  	if( !tOtoFAirPolBL.submitData(vData, "AirPol") )
		  	{
						FlagStr = "Fail";
						Content = "����ʧ�ܣ�ԭ���ǣ�" + tOtoFAirPolBL.mErrors.getFirstError();
		  	}
		  	else
		  	{
		    		FlagStr = "True";
		    		Content = "�����ɹ���";
				}
			
		}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

