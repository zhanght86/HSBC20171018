<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--�û�У����--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuNormChk.jsp
//�����ܣ��˹��˱����ս���¼�뱣��
//�������ڣ�2002-06-19 11:10:36
//������  ��Ght
//���¼�¼��  ������    ��������    ����ԭ��/����
//            
//            
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>  
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
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
  String tuwPopedom = request.getParameter("uwPopedom");
  String tAgentCode = request.getParameter("AgentCode");  
  String tAgentGroup = request.getParameter("AgentGroup");  
  String tSaleChnl = request.getParameter("SaleChnl"); 
  String tManageCom = request.getParameter("ManageCom"); 
  String tGrpNo = request.getParameter("AppntNo"); 
  String tGrpName = request.getParameter("AppntName");
                
//  String tuwPer = request.getParameter("uwPer");                          //�ʱ�ָ���ĺ˱�ʦ
//  String tUpUserCode = request.getParameter("UWPopedomCode");   
//  String tSugIndUWFlag = request.getParameter("SugIndUWFlag");
//  String tSugIndUWIdea = request.getParameter("SugIndUWIdea");
  String tChangeIdea = request.getParameter("ChangeIdea"); 
  
            //�ϱ��˱�����
  VData tVData = new VData();
  String tContNo = "";
  tContNo = request.getParameter("GrpContNo");

//  loggerDebug("GrpUWReturnApproveSave","UWIDEA:"+tUWIdea);
//  loggerDebug("GrpUWReturnApproveSave","ContNo:"+tContNo);
//  loggerDebug("GrpUWReturnApproveSave","uwflag+"+tUWFlag);
  

  try
  {
       
        FlagStr = "Succ";		
        String tUWSendFlag;
        String tUserCode;

        loggerDebug("GrpUWReturnApproveSave","----------------���ظ��˽ڵ�--GGGG--��ʼ----------------");			


        String tReDisMark = "0000002001";
        tuwUpReport = "9999";
        
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("GrpContNo",tContNo);
        nTransferData.setNameAndValue("ProposalGrpContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
        nTransferData.setNameAndValue("AgentCode",tAgentCode);       
        nTransferData.setNameAndValue("AgentGroup",tAgentGroup); 
        nTransferData.setNameAndValue("SaleChnl",tSaleChnl); 
        nTransferData.setNameAndValue("ManageCom",tManageCom); 
        nTransferData.setNameAndValue("ManageCom",tManageCom); 
        
        nTransferData.setNameAndValue("GrpNo",""); 
        nTransferData.setNameAndValue("GrpName",tGrpName); 
        nTransferData.setNameAndValue("CValiDate",""); 
        nTransferData.setNameAndValue("UWDate",""); 
        nTransferData.setNameAndValue("UWTime",""); 
        nTransferData.setNameAndValue("ReportType",""); 
        nTransferData.setNameAndValue("UWAuthority",""); 
        nTransferData.setNameAndValue("ApproveDate",""); 
        
        nTransferData.setNameAndValue("UWFlag",""); //׼�����ص����ˣ����ϸ��˵Ľڵ��
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
               
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport); //׼�����ص����ˣ����ϸ��˵Ľڵ��
        
        //nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
        //nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        //
        nTransferData.setNameAndValue("ReDisMark",tReDisMark);
        nTransferData.setNameAndValue("ReturnCheck","Y"); //Y�����ظ���
        
                
        loggerDebug("GrpUWReturnApproveSave","----��ͬ��:["+tContNo+"]");
        loggerDebug("GrpUWReturnApproveSave","----�����:["+tMissionID+"]");
        loggerDebug("GrpUWReturnApproveSave","----�������:["+tSubMissionID+"]");
        loggerDebug("GrpUWReturnApproveSave","----UWIdea:["+tUWIdea+"]");
        loggerDebug("GrpUWReturnApproveSave","----PrtNo:["+tPrtNo+"]");
        loggerDebug("GrpUWReturnApproveSave","----UWFlag:["+tUWFlag+"]");
        loggerDebug("GrpUWReturnApproveSave","----UWUpReport:["+tuwUpReport+"]");
        loggerDebug("GrpUWReturnApproveSave","----ReDisMark:["+tReDisMark+"]");
        loggerDebug("GrpUWReturnApproveSave","----ReturnCheck:[Y]");
        loggerDebug("GrpUWReturnApproveSave","----AgentCode:["+tAgentCode+"]");
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);

        //GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
        
        String busiName="tbgrpGrpTbWorkFlowUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        
       if (tBusinessDelegate.submitData(tVData,"0000002010",busiName) == false)
        {
          int n = tBusinessDelegate.getCErrors().getErrorCount();
          for (int i = 0; i < n; i++)
            {
                Content = "���ظ��˸�ʧ�ܣ�ԭ���ǣ�" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            }
            FlagStr = "Fail";
        }
       else
       {
		    	Content = "���ظ��˸ڲ����ɹ�!";
		    	FlagStr = "Succ";
        }
                					
        loggerDebug("GrpUWReturnApproveSave","----------------���ظ��˽ڵ�---����----------------");
                                     
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".��ʾ���쳣��ֹ!";
  }

  loggerDebug("GrpUWReturnApproveSave","flag="+FlagStr);
  loggerDebug("GrpUWReturnApproveSave","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterReturnApprove("<%=FlagStr%>","<%=Content%>");    
</script>
</html>
