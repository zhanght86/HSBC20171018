<%
//�������ƣ�PEdorTypeHJSubmit.jsp
//�����ܣ�
//�������ڣ�2008-11-23 16:49:22
//������  ��CrtHtml���򴴽� LH

%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.lang.*"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=gb2312" %>

<%
  //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  CErrors tErrors = new CErrors();
  //����Ҫִ�еĶ�������ӣ��޸�

  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";

  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
   transact = request.getParameter("fmtransact");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");

    //���˱���������Ϣ
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    try
    {
       // ׼���������� VData
       VData tVData = new VData();
       //������˱�����Ϣ(��ȫ)
       tVData.addElement(tG);
       tVData.addElement(tLPEdorItemSchema);
       PEdorHJDetailBL tPEdorHJDetailBL = new PEdorHJDetailBL();
       if (!tPEdorHJDetailBL.submitData(tVData,transact))
       {
          tErrors.copyAllErrors(tPEdorHJDetailBL.mErrors);
       }
    }
    catch(Exception ex)
    {ex.printStackTrace();
        Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
   //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
   //ҳ�淴����Ϣ
  if (!tErrors.needDealError())
  {
      FlagStr = "Success";
      Content = "�����ɹ���";
  }
  else
  {
      FlagStr = "Fail";
      Content = "����ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
  }
  tErrors = null;
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

