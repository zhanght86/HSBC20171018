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
	  String Flag="0";       //�ֹ���ȡ

	  if (bdate.equals("") && edate.equals(""))
	  {
	      flag = "false";
	      Content="��¼����ʼ������ֹ����!";
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
		    Content="��ֹ���ڴ�����ʼ����!";
		    loggerDebug("OtoFWTSave",Content);
	  }


	  if (flag.equals("true"))
	  {
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
		        loggerDebug("OtoFWTSave","�������");
            OtoFWTBL tOtoFWTBL = new OtoFWTBL();
				  	if( !tOtoFWTBL.submitData(vData, "Buss") )
				  	{
								FlagStr = "Fail";
								Content = "����ʧ�ܣ�ԭ���ǣ�" + tOtoFWTBL.mErrors.getFirstError();
				  	}
				  	else
				  	{
				    		FlagStr = "True";
				    		Content = "�����ɹ���";
						}
				}
		}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

