<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--�û�У����--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�
//�����ܣ��˹��˱����ص�������
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������    ����ԭ��/����
//            ����      2005-04-20  �ʱ�
//            ����      2005-06-15  �ʱ�
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%> 
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
      out.println("session has expired");
      return;
  }
  
  String tMissionID = request.getParameter("MissionID");                  //�����������
  String tPrtNo = request.getParameter("PrtNo");                          //ӡˢ��
  String tSubMissionID = request.getParameter("SubMissionID");	         //�������������
           
  VData tVData = new VData();
  String tContNo = "";
  tContNo = request.getParameter("ContNo");


  try
  {
       
        FlagStr = "Succ";		
        String tUWSendFlag;
        String tUserCode;

        loggerDebug("ManuUWReturnPublicPoolSave","----------------���ع�����---��ʼ----------------");			

        
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
                             
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("ActivityID","0000001100");
               
        loggerDebug("ManuUWReturnPublicPoolSave","----��ͬ��:["+tContNo+"]");
        loggerDebug("ManuUWReturnPublicPoolSave","----�����:["+tMissionID+"]");
        loggerDebug("ManuUWReturnPublicPoolSave","----�������:["+tSubMissionID+"]");
        
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);

        UWReturnPublicPoolUI tUWReturnPublicPoolUI = new UWReturnPublicPoolUI();
       if (tUWReturnPublicPoolUI.submitData(tVData,"0000001100") == false)
        {
          int n = tUWReturnPublicPoolUI.mErrors.getErrorCount();
          for (int i = 0; i < n; i++)
            {
                Content = "���ع�����ʧ�ܣ�ԭ���ǣ�" + tUWReturnPublicPoolUI.mErrors.getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
       else
       {
		    	Content = "���ع����ز����ɹ�!";
		    	FlagStr = "Succ";
        }
                					
        loggerDebug("ManuUWReturnPublicPoolSave","----------------���ع�����---����----------------");
                                     
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".��ʾ���쳣��ֹ!";
  }

  loggerDebug("ManuUWReturnPublicPoolSave","flag="+FlagStr);
  loggerDebug("ManuUWReturnPublicPoolSave","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterReturnApprove("<%=FlagStr%>","<%=Content%>");    
</script>
</html>
