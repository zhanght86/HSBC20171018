<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�LCPolQueyr.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��TJJ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Result ="";

  //������Ϣ����
  LPEdorMainSchema tLPEdorMainSchema   = new LPEdorMainSchema();
  try{
  tLPEdorMainSchema.setPolNo(request.getParameter("PolNo"));
  tLPEdorMainSchema.setInsuredNo(request.getParameter("CustomerNo"));
  tLPEdorMainSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
  loggerDebug("LPPolQuerySubmit.jsp",tLPEdorMainSchema.getGrpPolNo());
  tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorMainSchema.setEdorType(request.getParameter("EdorType"));
  }catch(Exception e)
  {}
  // ׼���������� VData
  VData tVData = new VData();
	tVData.addElement(tLPEdorMainSchema);
	String tTransact = request.getParameter("fmtransact");
  // ���ݴ���
  try
  {
  	CPolUI tCPolUI   = new CPolUI();
		if (!tCPolUI.submitData(tVData,tTransact))
		{
      Content = " ��ѯʧ�ܣ�ԭ����: " + tCPolUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
		}
		else
		{
			FlagStr = "Succ";
			Content = "��ѯ�ɹ�!";
			tVData.clear();
			Result = (String)tCPolUI.getResult().get(0);
			//loggerDebug("LPPolQuerySubmit.jsp",Result);
			%>
			<%		
			}
	 }
	catch(Exception ex)
	{
		Content = tTransact+"ʧ�ܣ�ԭ����:" + ex.toString();
    		FlagStr = "Fail";
	}			
  
loggerDebug("LPPolQuerySubmit.jsp","------end------");
%>
 <html>
	<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
	</script>
</html>