<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�WFEdorApproveSave.jsp
//�����ܣ���ȫ��������������У��
//�������ڣ�2005-12-17 15:10:36
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
   String busiName="bqPEdorPopedomCheckBL";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null)
  {
    System.out.println("session has expired");
    return;
  }

  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
  String sEdorAcceptNo = request.getParameter("EdorAcceptNo");

  TransferData mTransferData = new TransferData();
  mTransferData.setNameAndValue("MissionID", sMissionID);
  mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
  mTransferData.setNameAndValue("ActivityID", sActivityID);
  mTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);

    // ׼���������� VData
    VData tVData = new VData();
    tVData.add( mTransferData );
    tVData.add( tG );


  try
  {
      
      if (!tBusinessDelegate.submitData(tVData, "GApprove",busiName))
      {
        tError = tBusinessDelegate.getCErrors();
        Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
        FlagStr = "Fail";
      }
      else
      {
        String busiNameUWApply="cbcheckUWApplyUI";
  		BusinessDelegate tBusinessDelegateUWApply=BusinessDelegate.getBusinessDelegate();
        // ���ݴ���
       
        if (tBusinessDelegateUWApply.submitData(tVData,"",busiNameUWApply) == false)
        {
            int n = tBusinessDelegateUWApply.getCErrors().getErrorCount();
            for (int i = 0; i < n; i++)
            {
              System.out.println("Error: "+tBusinessDelegateUWApply.getCErrors().getError(i).errorMessage);
              Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegateUWApply.getCErrors().getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
        //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegateUWApply.getCErrors();
            System.out.println("tError.getErrorCount:"+tError.getErrorCount());
            if (!tError.needDealError())
            {
                Content = " ����ɹ�! ";
                FlagStr = "Succ";
            }
            else
            {
                Content = " ����ʧ�ܣ�ԭ����:";
                int n = tError.getErrorCount();
                if (n > 0)
                {
                  for(int i = 0;i < n;i++)
                  {
                    //tError = tErrors.getError(i);
                    Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
                  }
                    }
                FlagStr = "Fail";
            }
        }
    }
  }
  catch(Exception e)
  {
    e.printStackTrace();
    Content = Content.trim()+".��ʾ���쳣��ֹ!";
  }

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
