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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>  
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
  String tSubMissionID = request.getParameter("SubConfirmMissionID");	    //�������������
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

//  loggerDebug("UWManuNormChk","UWIDEA:"+tUWIdea);
//  loggerDebug("UWManuNormChk","ContNo:"+tContNo);
//  loggerDebug("UWManuNormChk","uwflag+"+tUWFlag);
  

  try
  {
       
      FlagStr = "Succ";		
      String tUWSendFlag;
      String tUserCode;

			
      // ���ݺ˱����۽��в���
      if(tUWFlag.equals("b"))
      {
        tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
        tLCContSchema.setContNo(request.getParameter("ContNo")); 
        tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
        tLCContSchema.setManageCom(request.getParameter("ManageCom"));  
        tLCContSchema.setRemark(tChangeIdea);   
        
        tLCContSet.add(tLCContSchema);
        tVData.add(tLCContSet);
        tVData.add(tG);
        //UWManuChgPlanConclusionChkUI tUWManuChgPlanConclusionChkUI = new UWManuChgPlanConclusionChkUI();
        String busiName="cbcheckgrpUWManuChgPlanConclusionChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
        
        
        if (tBusinessDelegate.submitData(tVData, "",busiName) == false)
			  {
			    int n = tBusinessDelegate.getCErrors().getErrorCount();
			    for (int i = 0; i < n; i++)
			    Content = " �б��ƻ����ԭ��¼��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			    FlagStr = "Fail";
			  }
			  else
			  {
			  	 Content = " �б��ƻ�����ɹ���";
			  	 FlagStr = "Succ";
			  }
			}
			else if(tUWFlag.equals("1")||tUWFlag.equals("2")||tUWFlag.equals("4")||tUWFlag.equals("9"))
			{
        FlagStr = "Succ";
        loggerDebug("UWManuNormChk","#################1");
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
        loggerDebug("UWManuNormChk","#################1");
        
        
        nTransferData.setNameAndValue("UWFlag",tUWFlag);
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
        loggerDebug("UWManuNormChk","#################1");
        
        
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport);
        loggerDebug("UWManuNormChk","#################1");
        //nTransferData.setNameAndValue("UWPopedom",tuwPopedom);
        //nTransferData.setNameAndValue("UWPer",tuwPer);
        
//        if((tuwUpReport!=null)&&!(tuwUpReport.equals("0"))){
        
          nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
          nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        loggerDebug("UWManuNormChk","#################1");
          //�˱��ϱ��켣��
          tLCUWSendTraceSchema.setOtherNo(tContNo);		
          tLCUWSendTraceSchema.setOtherNoType("1");                   //���� 
          tLCUWSendTraceSchema.setUWFlag(tUWFlag);
          tLCUWSendTraceSchema.setUWIdea(tUWIdea);
//        tLCUWSendTraceSchema.setYesOrNo(tYesOrNo);
        loggerDebug("UWManuNormChk","#################1");
          nTransferData.setNameAndValue("LCUWSendTraceSchema",tLCUWSendTraceSchema);
//        }
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tG);
        String busiName="tbTbWorkFlowUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
        //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
        if (tBusinessDelegate.submitData(tVData,"0000001110",busiName) == false)
        {
          int n = tBusinessDelegate.getCErrors().getErrorCount();
          for (int i = 0; i < n; i++)
          Content = "�˹��˱�ʧ�ܣ�ԭ���ǣ�" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
          FlagStr = "Fail";
        }
        else{
			    	Content = "�˹��˱������ɹ�!";
			    	FlagStr = "Succ";
        }
                
			  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			  if (FlagStr == "Fail") //if 5
			  {
			   
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {
			    	Content = "�˹��˱������ɹ�!";
			    	FlagStr = "Succ";
			    }
			    else
			    {
			 	    FlagStr = "Fail";
			    }
			  }  //end of if 5
		  
		  }
		  else{
		    
			 	    FlagStr = "Fail";
			    	Content = "��ѡ����ȷ�ĺ˱����۴���!";
		  
		  }
		  
		  
			
			
  } //END OF TRY
  catch(Exception e)
  {
	  e.printStackTrace();
	  Content = Content.trim()+".��ʾ���쳣��ֹ!";
  }

  loggerDebug("UWManuNormChk","flag="+FlagStr);
  loggerDebug("UWManuNormChk","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
