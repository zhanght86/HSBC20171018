<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuReportChk.jsp
//�����ܣ��˹��˱��˱�����¼��
//�������ڣ�
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//�������
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) {
    out.println("session has expired");
    return;
  }

  //У�鴦��
  //���ݴ����

  //������Ϣ
  // Ͷ�����б�

  LCUWReportSchema tLCUWReportSchema = new LCUWReportSchema(); //����һ��Schema
  LCUWReportSet tLCUWReportSet = new LCUWReportSet(); //����һ��Set

  String tContNoHide = request.getParameter("ProposalNoHide"); //���������ȡֵ
  String tContNo = request.getParameter("ContNo");
  String tOperator = request.getParameter("Operator");
  String tContent = request.getParameter("Content");

  loggerDebug("UWManuReportChk","ContNo:"+tContNo);    //��Ҫ��ʾ�Ĳ���
  loggerDebug("UWManuReportChk","UWOperator:"+tOperator);
  loggerDebug("UWManuReportChk","Contente:"+tContent);

  boolean flag = true;


  tLCUWReportSchema.setContNo(tContNo); //��t...Schema��ֵ
  tLCUWReportSchema.setUWOperator(tOperator);

  //����DB����֤��ѯ����
  LCUWReportDB tLCUWReportDB=new LCUWReportDB(); //��xxxΪ����������
  tLCUWReportDB.setSchema(tLCUWReportSchema); // txxxSchema �е����Ը�����Ҫ�Ѿ���ֵ
  tLCUWReportSet=tLCUWReportDB.query();    //����txxxSchema�в�Ϊ�յ����Բ�ѯ������xxxSet����
  if(tLCUWReportDB.mErrors.needDealError())
  {
    Content = "��ѯʧ��!";
    flag = false;
  }
  else
  {
    if(tLCUWReportSet.size() > 0)
    {
      tLCUWReportSchema=tLCUWReportSet.get(1);
      flag = true;
    }
  }

  if (flag == true)
  {      // ׼���������� VData
    VData tVData = new VData();
    tVData.add( tLCUWReportSet);
    tVData.add( tLCUWReportSchema);
    tVData.add( tContent);
    tVData.add( tG );

    // ���ݴ���
    UWReportUI tUWReportUI   = new UWReportUI();
    if (tUWReportUI.submitData(tVData,"INSERT") == false)
    {
      int n = tUWReportUI.mErrors.getErrorCount();
      for (int i = 0; i < n; i++)
        Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tUWReportUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
    }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
      tError = tUWReportUI.mErrors;
      if (!tError.needDealError())
      {
        Content = " �˹��˱��ɹ�! ";
        FlagStr = "Succ";
      }
      else
      {
        Content = " �˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
        FlagStr = "Fail";


      }
    }
  }

%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>






