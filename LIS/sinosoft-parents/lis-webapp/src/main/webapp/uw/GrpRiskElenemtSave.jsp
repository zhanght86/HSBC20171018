<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%
  String rate1=request.getParameter("coorate1");
  String orirate=request.getParameter("orirate");
  if ("".equals(orirate))
  {
  orirate="0";
  }
  if(rate1.equals(""))
  {
  rate1="0";
  }
  String rate2=request.getParameter("coorate2");
  if(rate2.equals(""))
  {
  rate2="0";
  }
  String rate3=request.getParameter("coorate3");
  if(rate3.equals(""))
  {
  rate3="0";
  }
  String rate4=request.getParameter("coorate4");
  if(rate4.equals(""))
  {
  rate4="0";
  }
  String rate5=request.getParameter("coorate5");
  if(rate5.equals(""))
  {
  rate5="0";
  }
  String rate6=request.getParameter("coorate6");
  if(rate6.equals(""))
  {
  rate6="0";
  }
  loggerDebug("GrpRiskElenemtSave",rate1+"-"+rate2+"-"+rate3+"-"+rate4+"-"+rate5+"-"+rate6);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();  
	tTransferData.setNameAndValue("rate1",rate1);
	tTransferData.setNameAndValue("rate2",rate2);
	tTransferData.setNameAndValue("rate3",rate3);
	tTransferData.setNameAndValue("rate4",rate4);
	tTransferData.setNameAndValue("rate5",rate5);
	tTransferData.setNameAndValue("rate6",rate6);
	tTransferData.setNameAndValue("orirate",orirate);
	String riskcode=request.getParameter("Risk");
	String grpcontno=request.getParameter("GrpCont");
	loggerDebug("GrpRiskElenemtSave",riskcode+"-"+grpcontno);
	tTransferData.setNameAndValue("riskcode",riskcode);
	tTransferData.setNameAndValue("grpcontno",grpcontno);
	tTransferData.setNameAndValue("GrpPolNo",request.getParameter("GrpPolNo"));
	tTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
	loggerDebug("GrpRiskElenemtSave",request.getParameter("GrpPolNo"));
	VData tVData = new VData();
	tVData.add(tG); 
	tVData.addElement(tTransferData);
  GrpRiskElenemtAdjust tGrpRiskElenemtAdjust=new GrpRiskElenemtAdjust();
  if( tGrpRiskElenemtAdjust.submitData( tVData, "Insert" ) == false )                       
	{                                                                               
		loggerDebug("GrpRiskElenemtSave","failed ");
		%>
		<script language="javascript">
		alert("����ʧ�ܣ�ԭ���ǣ�"+"<%=tGrpRiskElenemtAdjust.mErrors.getError(0).errorMessage%>"+";�������Ѿ������������ֵĵ�������");
	</script>
		<%
	}
	else
	{
	loggerDebug("GrpRiskElenemtSave","�����ɹ��� ");
	%>
	<script language="javascript">
		alert("����ɹ���");
        top.close();
	</script>
	<%
	}
%>
