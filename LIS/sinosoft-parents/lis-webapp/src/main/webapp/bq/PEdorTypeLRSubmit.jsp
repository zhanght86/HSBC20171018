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
String getMoney = request.getParameter("GetMoney");
String edoracceptNo = request.getParameter("EdorAcceptNo");

String grpContNo = request.getParameter("GrpContNo");
String appObj = request.getParameter("AppObj");

// ׼���������� VData
LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
tLPEdorItemSchema.setEdorNo(edorNo);
tLPEdorItemSchema.setContNo(contNo);
tLPEdorItemSchema.setPolNo("000000");
tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
tLPEdorItemSchema.setEdorAcceptNo(edoracceptNo);
tLPEdorItemSchema.setEdorType(edorType);
tLPEdorItemSchema.setEdorReasonCode(request.getParameter("GoonGetMethod1"));
tLPEdorItemSchema.setEdorReason(request.getParameter("GoonGetMethod1Name"));
tLPEdorItemSchema.setGetMoney(getMoney);


System.out.println("==="+tLPEdorItemSchema.encode());
System.out.println("����LR��Ҫ��������:"+tLPEdorItemSchema.getGetMoney());

VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPEdorItemSchema);

if(appObj != null && appObj.trim().equalsIgnoreCase("G")){
    LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    tLPGrpEdorItemSchema.setEdorNo(edorNo);
    tLPGrpEdorItemSchema.setGrpContNo(grpContNo);
    tLPGrpEdorItemSchema.setEdorAcceptNo(edoracceptNo);
    tLPGrpEdorItemSchema.setEdorType(edorType);
    tVData.add(tLPGrpEdorItemSchema);

//    GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
     String GbusiName="GEdorDetailUI";
	 BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    System.out.println("--==  GEdorTypeGRSubmit  ==--");
//    if (!tGEdorDetailUI.submitData(tVData, transact))
    if (!GBusinessDelegate.submitData(tVData, transact, GbusiName))
    {
//        VData rVData = tGEdorDetailUI.getResult();
        VData rVData = GBusinessDelegate.getResult();
//        Content = "����ʧ�ܣ�ԭ����:" + tGEdorDetailUI.mErrors.getFirstError();
        Content = "����ʧ�ܣ�ԭ����:" + GBusinessDelegate.getCErrors().getFirstError();
        FlagStr = "Fail";
    }
    else
    {
        Content = "����ɹ�";
        FlagStr = "Success";
    }
}
else{
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
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

