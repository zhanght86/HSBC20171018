<%
//�������ƣ�PEdorTypeLRSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����

//���¼�¼��  ������ ����    �������� 2006-4-14     ����ԭ��/���� ���屣ȫ����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %> 

<%

CErrors tError = null;
String tRela  = "";
String FlagStr = "";
String Content = "";
String transact = "";

GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");

transact = request.getParameter("fmtransact");
String edorNo = request.getParameter("EdorNo");
String edorType = request.getParameter("EdorType");
String contNo = request.getParameter("ContNo");

String edoracceptNo = request.getParameter("EdorAcceptNo");



// ׼���������� VData
LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
tLPEdorItemSchema.setEdorNo(edorNo);
tLPEdorItemSchema.setContNo(contNo);
tLPEdorItemSchema.setPolNo("000000");
tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
tLPEdorItemSchema.setEdorAcceptNo(edoracceptNo);
tLPEdorItemSchema.setEdorType(edorType);
tLPEdorItemSchema.setStandbyFlag1(request.getParameter("RemindMode")); //�����Ľ������ѷ�ʽ;


VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPEdorItemSchema);


//    EdorDetailUI tEdorDetailUI = new EdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
    System.out.println("--==  PEdorTypeLRSubmit  ==--");
//    if (!tEdorDetailUI.submitData(tVData, transact))
    if (!tBusinessDelegate.submitData(tVData, transact ,busiName))
    {
//        VData rVData = tEdorDetailUI.getResult();
        VData rVData = tBusinessDelegate.getResult();
//        Content = "����ʧ�ܣ�ԭ����:" + tEdorDetailUI.mErrors.getFirstError();
        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
        FlagStr = "Fail";
    }
    else
    {
        Content = "����ɹ�";
        FlagStr = "Success";
    }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

