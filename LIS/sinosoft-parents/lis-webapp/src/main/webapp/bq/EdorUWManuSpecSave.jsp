
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWManuSpecSave.jsp
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�2005-06-24 17:10:36
//������  ��liurongxiao
//���¼�¼��  ������ Ǯ����   ��������  2006-10-27   ����ԭ��/���� ����

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
  //�������
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";

    boolean flag = true;
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    if(tG == null) {
        out.println("session has expired");
        return;
    }
    //������Ϣ
    LPSpecSchema tLPSpecSchema = new LPSpecSchema();
    LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
    TransferData tTransferData = new TransferData();

  String tContNo = request.getParameter("ContNo");
    String tRemark = request.getParameter("Remark");
    String tSpecReason = request.getParameter("SpecReason");
    String tEdorNo = request.getParameter("EdorNo");
    String tEdorType = request.getParameter("EdorType");
    String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tPolNo = request.getParameter("PolNo");
    String tPrtNo = request.getParameter("PrtNo");
  String tOperateType = request.getParameter("operate");
  String tSerialNo = request.getParameter("serialno");

    loggerDebug("EdorUWManuSpecSave","EdorNo:"+tEdorNo);
    loggerDebug("EdorUWManuSpecSave","EdorType:"+tEdorType);
    loggerDebug("EdorUWManuSpecSave","ContNo:"+tContNo);
    loggerDebug("EdorUWManuSpecSave","remark:"+tRemark);
    loggerDebug("EdorUWManuSpecSave","PolNo:"+tPolNo);
  loggerDebug("EdorUWManuSpecSave","PrtNo:"+tPrtNo);

    if (tContNo == "" || (tRemark == "" ) )
    {
        Content = "��¼���ر�Լ����Ϣ����������ע��Ϣ!";
        FlagStr = "Fail";
        flag = false;
    }

          if(tContNo != null && tEdorNo != null && tMissionID != null && tSubMissionID != null)
          {
            //׼����Լ��Ϣ
            tLPSpecSchema.setContNo(tContNo);
            tLPSpecSchema.setEdorNo(tEdorNo);
            tLPSpecSchema.setEdorType(tEdorType);
            tLPSpecSchema.setPolNo(tPolNo);

            tLPSpecSchema.setSpecContent(tRemark);
            tLPSpecSchema.setSpecType("1");
            tLPSpecSchema.setSpecCode("1");
            //׼����Լԭ��
            tLPUWMasterSchema.setContNo(tContNo);
            tLPUWMasterSchema.setEdorNo(tEdorNo);
            tLPUWMasterSchema.setEdorType(tEdorType);
            tLPUWMasterSchema.setPolNo(tPolNo);

            tLPUWMasterSchema.setSpecReason(tSpecReason);


           }
           else
           {
             Content = "��������ʧ��!";
             flag = false;
           }

try
{
    if (flag == true)
    {
        // ׼���������� VData

        VData tVData = new VData();

          tTransferData.setNameAndValue("ContNo",tContNo);
        tTransferData.setNameAndValue("EdorNo",tEdorNo);
        tTransferData.setNameAndValue("EdorType",tEdorType);
        tTransferData.setNameAndValue("OperateType",tOperateType);
        tTransferData.setNameAndValue("SerialNo",tSerialNo);
      tTransferData.setNameAndValue("PolNo",tPolNo);
      tTransferData.setNameAndValue("PrtNo",tPrtNo);
        tTransferData.setNameAndValue("MissionID",tMissionID );
        tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
      tTransferData.setNameAndValue("LPSpecSchema",tLPSpecSchema);
      tTransferData.setNameAndValue("LPUWMasterSchema",tLPUWMasterSchema);

        tVData.add( tTransferData );
        tVData.add( tG );

        // ���ݴ���
        //EdorUWSpecUI tEdorUWSpecUI   = new EdorUWSpecUI();
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        //if (!tEdorUWSpecUI.submitData(tVData,""))
        if(!tBusinessDelegate.submitData(tVData, "", "EdorUWSpecUI"))
          {

            int n = tBusinessDelegate.getCErrors().getErrorCount();
            Content = " �˱���Լʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            FlagStr = "Fail";
        }
        //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content = " �˱���Լ�ɹ�! ";
                FlagStr = "Succ";
            }
            else
            {
                FlagStr = "Fail";
            }
        }
    }
}
catch(Exception e)
{
    e.printStackTrace();
    Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

