<%
//�������ƣ�PEdorTypeAGSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������  Nicholas  ��������  2005-06   ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ

  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  LJSGetDrawSchema tLJSGetDrawSchema = null;
  LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
  LPPolSchema tLPPolSchema = new LPPolSchema();
  //PEdorAGDetailUI tPEdorAGDetailUI   = new PEdorAGDetailUI();
  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();

  CErrors tError = null;
  //����Ҫִ�еĶ��������

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String Result="";
  String AppObj = "";

    AppObj = request.getParameter("AppObj");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");


  //���˱���������Ϣ
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));

    //����������Ϣ��ֻ���޸���ȡ��ʽ���ʻ���Ϣ
    tLPPolSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPPolSchema.setEdorType(request.getParameter("EdorType"));
    tLPPolSchema.setPolNo(request.getParameter("PolNo"));
    tLPPolSchema.setGetForm(request.getParameter("GoonGetMethod"));
    if ("0".equals(request.getParameter("GoonGetMethod")))
    {
        tLPPolSchema.setGetBankCode(request.getParameter("GetBankCode"));
        tLPPolSchema.setGetBankAccNo(request.getParameter("GetBankAccNo"));
        tLPPolSchema.setGetAccName(request.getParameter("GetAccName"));
    }

    //������������������Ϣ�ɺ�̨���
  String tGridNo[] = request.getParameterValues("PolGridNo");
  String tGetNoticeNo[] =request.getParameterValues("PolGrid4"); //����֪ͨ�����
  String tDutyCode[] =request.getParameterValues("PolGrid5"); //���α���
  String tGetDutyCode[] =request.getParameterValues("PolGrid6"); //�������α���
  String tGetDutyKind[] =request.getParameterValues("PolGrid7"); //������������

    int iCount = tGridNo.length;
    System.out.println("--icount:"+iCount);

    try{
      for (int i=0;i<iCount;i++)
      {
        tLJSGetDrawSchema = new LJSGetDrawSchema();
        tLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo[i]);
        tLJSGetDrawSchema.setDutyCode(tDutyCode[i]);
        tLJSGetDrawSchema.setGetDutyKind(tGetDutyKind[i]);
        tLJSGetDrawSchema.setGetDutyCode(tGetDutyCode[i]);
        tLJSGetDrawSchema.setPolNo(request.getParameter("PolNo"));

      tLJSGetDrawSet.add(tLJSGetDrawSchema);
      }
  }
  catch (Exception e)
    {
        System.out.println(e.toString());
    }

try
  {
     //ͳһ�������ڣ�ʱ��
     String theCurrentDate = PubFun.getCurrentDate();
     String theCurrentTime = PubFun.getCurrentTime();
     //tLPEdorItemSchema.setMakeDate(theCurrentDate);
     //tLPEdorItemSchema.setMakeTime(theCurrentTime);
     tLPEdorItemSchema.setModifyDate(theCurrentDate);
     tLPEdorItemSchema.setModifyTime(theCurrentTime);

     //׼���������� VData
     VData tVData = new VData();

        //������˱�����Ϣ(��ȫ)
        tVData.add(tG);
        tVData.add(tLPEdorItemSchema);
        tVData.add(tLJSGetDrawSet);
        tVData.add(tLPPolSchema);

       //boolean tag = tPEdorAGDetailUI.submitData(tVData,"");
       //boolean tag = tEdorDetailUI.submitData(tVData,"");
        boolean tag;
        if("G".equals(AppObj))
        {
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
        tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tVData.addElement(tLPGrpEdorItemSchema);
        System.out.println("Group submitData");
        tag = tGEdorDetailUI.submitData(tVData, "OPERATION");
        }
        else
        {
        System.out.println("Person submitData");
        tag = tEdorDetailUI.submitData(tVData, "OPERATION");
        }
       if (tag)
     {
          System.out.println("Successful");
     }
     else
     {
          System.out.println("Fail");
     }
    }
catch(Exception ex)
{
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
}
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr.equals(""))
  {
    //tError = tPEdorAGDetailUI.mErrors;
    //tError = tEdorDetailUI.mErrors;
    if("G".equals(AppObj))
        tError = tGEdorDetailUI.mErrors;
    else
        tError = tEdorDetailUI.mErrors;
    if (!tError.needDealError())
    {
        FlagStr = "Success";
        Content = " ����ɹ�";
    }
    else
    {
        FlagStr = "Fail";
        Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    }
  }

%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
