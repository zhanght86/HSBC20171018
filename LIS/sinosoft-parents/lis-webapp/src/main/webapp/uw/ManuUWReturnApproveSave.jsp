<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--�û�У����--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuNormChk.jsp
//�����ܣ��˹��˱����ս���¼�뱣��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������    ����ԭ��/����
//            ����      2005-04-20  �ʱ�
//            ����      2005-06-15  �ʱ�
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>  
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
  
  // Ͷ�����б�
  LCContSet tLCContSet = new LCContSet();
  LCContSchema tLCContSchema=new LCContSchema();
  LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();                  //���˺�ͬ�˱���������
  LCUWSendTraceSchema tLCUWSendTraceSchema = new LCUWSendTraceSchema();   //�˱��ϱ��켣��
  
  String tUWFlag = request.getParameter("uwState");		                    //�˱�����
  String tUWIdea = request.getParameter("UWIdea");                        //�˱����
  //String tvalidate = request.getParameter("UWDelay");                   
  String tMissionID = request.getParameter("MissionID");                  //�����������
  String tPrtNo = request.getParameter("PrtNo");                          //ӡˢ��
  String tSubMissionID = request.getParameter("SubMissionID");	         //�������������
  String tSendFlag = request.getParameter("SendFlag");	                  //�ϱ���־
  String tYesOrNo = request.getParameter("YesOrNo");	                    //�ϼ��ظ������Yͬ�⣬N��ͬ��
  String tuwUpReport = request.getParameter("uwUpReport");                //�˱������ϱ���־�����Ѱ���
  String tuwPopedom = request.getParameter("uwPopedom");                  //�ʱ����ĺ˱�����
  String tuwPer = request.getParameter("uwPer");                          //�ʱ�ָ���ĺ˱�ʦ
  String tUpUserCode = request.getParameter("UWPopedomCode");   
  String tSugIndUWFlag = request.getParameter("SugIndUWFlag");
  String tSugIndUWIdea = request.getParameter("SugIndUWIdea");
  String tChangeIdea = request.getParameter("ChangeIdea"); 
  
            //�ϱ��˱�����
  VData tVData = new VData();
  String tContNo = "";
  tContNo = request.getParameter("ContNo");

//  loggerDebug("ManuUWReturnApproveSave","UWIDEA:"+tUWIdea);
//  loggerDebug("ManuUWReturnApproveSave","ContNo:"+tContNo);
//  loggerDebug("ManuUWReturnApproveSave","uwflag+"+tUWFlag);
  

  try
  {
       
        FlagStr = "Succ";		
        String tUWSendFlag;
        String tUserCode;

        loggerDebug("ManuUWReturnApproveSave","----------------���ظ��˽ڵ�---��ʼ----------------");			


        String tReDisMark = "0000001001";
        tuwUpReport = "9999";
        
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
               
        nTransferData.setNameAndValue("UWFlag",tUWFlag); //׼�����ص����ˣ����ϸ��˵Ľڵ��
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
               
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport); //׼�����ص����ˣ����ϸ��˵Ľڵ��
        
        nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
        nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        //
        nTransferData.setNameAndValue("ReDisMark",tReDisMark);
        nTransferData.setNameAndValue("ReturnCheck","Y"); //Y�����ظ���
        
                
        loggerDebug("ManuUWReturnApproveSave","----��ͬ��:["+tContNo+"]");
        loggerDebug("ManuUWReturnApproveSave","----�����:["+tMissionID+"]");
        loggerDebug("ManuUWReturnApproveSave","----�������:["+tSubMissionID+"]");
        loggerDebug("ManuUWReturnApproveSave","----UWIdea:["+tUWIdea+"]");
        
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);

        TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
       if (tTbWorkFlowUI.submitData(tVData,"0000001110") == false)
        {
          int n = tTbWorkFlowUI.mErrors.getErrorCount();
          for (int i = 0; i < n; i++)
            {
                Content = "���ظ��˸�ʧ�ܣ�ԭ���ǣ�" + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
       else
       {
		    	Content = "���ظ��˸ڲ����ɹ�!";
		    	FlagStr = "Succ";
        }
                					
        loggerDebug("ManuUWReturnApproveSave","----------------���ظ��˽ڵ�---����----------------");
                                     
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".��ʾ���쳣��ֹ!";
  }

  loggerDebug("ManuUWReturnApproveSave","flag="+FlagStr);
  loggerDebug("ManuUWReturnApproveSave","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterReturnApprove("<%=FlagStr%>","<%=Content%>");    
</script>
</html>
