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
//            ln      2008-10-17  ��������ͬ�½���
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
  String FBFlagStr = "Succ";  //�ֱ���־
  GlobalInput tG = new GlobalInput();
  ExeSQL tExeSQL=new ExeSQL();
  SSRS tSSRS=new SSRS();
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
      out.println("session has expired");
      return;
  }
  
  	String tContNo = request.getParameter("ContNo");
  	String tFBFlag = request.getParameter("FBFlag");
  	String tAddFeeFlag = request.getParameter("AddFeeFlag");
  	//tongmeng 2007-12-14 Add
  	//�����ϱ��û�,�ϱ��ĺ˱�ʦ����
  	String tUWPopedomCode = request.getParameter("TUWPopedomCode");
  	String tUWPopedomGrade = request.getParameter("TUWPopedomGrade");
	loggerDebug("UWManuNormChk","FB ContNo=="+tContNo);
	loggerDebug("UWManuNormChk","FB Flag=="+tFBFlag);
	loggerDebug("UWManuNormChk","FB AddFeeFlag=="+tAddFeeFlag);
	if(tAddFeeFlag.equals("Y"))
	{
		try
		{	
			VData tVData = new VData();
   			LCContSchema tLCContSchema = new LCContSchema();
   			tLCContSchema.setContNo(tContNo);
   			tVData.add(tLCContSchema);
   			//UWFBCal tUWFBCal = new UWFBCal();
   			
   		   String busiName1="cbcheckUWFBCal";
   		   BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
   			
   			if(!tBusinessDelegate1.submitData(tVData,"",busiName1))
   			{
   		    	loggerDebug("UWManuNormChk","����Ҫ�ٷ�");
   		    	FBFlagStr = "Succ";
   			}
   			else
   			{
   		    	loggerDebug("UWManuNormChk","��Ҫ�ٷ�");
   		    	FBFlagStr = "FB";
   		    	Content = "��Ҫ�����ٱ��ʱ�����������к˱�ȷ�ϣ�";
   			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" ��ʾ:�쳣�˳�.";
		}
	}
	loggerDebug("UWManuNormChk","-----------------flagStr="+FlagStr);
  if(FBFlagStr.equals("Succ"))
  {
  // Ͷ�����б�
  LCContSet tLCContSet = new LCContSet();
  LCContSchema tLCContSchema=new LCContSchema();
  LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();                  //���˺�ͬ�˱���������
  LCUWSendTraceSchema tLCUWSendTraceSchema = new LCUWSendTraceSchema();   //�˱��ϱ��켣��
  
  String tUWFlag = request.getParameter("uwState");		                    //�˱�����
  String tUWIdea = request.getParameter("UWIdea");                        //�˱����
  String tvalidate = request.getParameter("UWDelay");                     //�ӳ�ʱ��
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
//ln 2008-10-17 add �ϱ�ԭ�� ����ԭ�� ����ԭ�� �ܱ�ԭ�� Ͷ������
  String tUpReason = request.getParameter("UWUpperReason");
  //modify by ln 2009-2-9 ��¼����ԭ�� ����ԭ�� �ܱ�ԭ�����
  String tWithDReasonCode = request.getParameter("UWWithDReasonCode");
  String tWithDReason = request.getParameter("UWWithDReason");
  String tDelayReasonCode = request.getParameter("DelayReasonCode");
  String tDelayReason = request.getParameter("UWDelayReason");
  String tRefuReasonCode = request.getParameter("UWRefuReasonCode"); 
  String tRefuReason = request.getParameter("UWRefuReason");  
  String tSuggestCont = request.getParameter("SuggestCont");
  String tSugContInput = "";
  if(tSuggestCont!=null && tSuggestCont.equals("1")){
	  tSugContInput = request.getParameter("SugContInput");
    }
  //end add ln 2008-10-17
            //�ϱ��˱�����
  VData tVData = new VData();



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
        
           	  String busiName2="cbcheckUWManuChgPlanConclusionChkUI";
   		      BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
        
			  if (tBusinessDelegate2.submitData(tVData, "",busiName2) == false)
			  {
			    int n = tBusinessDelegate2.getCErrors().getErrorCount();
			    for (int i = 0; i < n; i++)
			    Content = " �б��ƻ����ԭ��¼��ʧ�ܣ�ԭ����: " + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
			    FlagStr = "Fail";
			  }
			  else
			  {
			  	 Content = " �б��ƻ�����ɹ���";
			  	 FlagStr = "Succ";
			  }
	  }
			//tongmeng 2007-10-30 modify
			//���ӳ�������
			//tongmeng 2009-01-15 modify
			//��� �ϱ��Ļ�,����ʹ��5����
	  else if((tUWFlag.equals("1")||tUWFlag.equals("2")||tUWFlag.equals("4")||tUWFlag.equals("9")||tUWFlag.equals("a"))
	  	      ||(!tuwUpReport.equals("0")&&tUWFlag.equals("5"))
	  	
	  	)
	  {
        FlagStr = "Succ";
        loggerDebug("UWManuNormChk","#################1");
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("ContNo",tContNo);
        nTransferData.setNameAndValue("PrtNo",tPrtNo);
        nTransferData.setNameAndValue("ValiDate",tvalidate);
        loggerDebug("UWManuNormChk","#################1");
		//����Ƿ������·��������֪ͨ��
        String SendFlag=tExeSQL.getOneValue("select count(*) from lcissuepol where contno='"+tContNo+"' and state='0'");
        String ReportFlag=tExeSQL.getOneValue("select count(*) from LCRReport where contno='"+tContNo+"' and ReplyFlag='0'");
        String PeFlag=tExeSQL.getOneValue("select count(*) from LCPENotice where contno='"+tContNo+"' and PrintFlag='0'");
        if(!"0".equals(SendFlag) || !"0".equals(ReportFlag) || !"0".equals(PeFlag)){
        	nTransferData.setNameAndValue("UWFlag","5");//��������·����������֪ͨ��˱�����Ĭ��Ϊ��5��	
        }else{
        	nTransferData.setNameAndValue("UWFlag",tUWFlag);	
        }
        
        nTransferData.setNameAndValue("UWIdea",tUWIdea);
        loggerDebug("UWManuNormChk","#################1");
        
        
        nTransferData.setNameAndValue("MissionID",tMissionID);
        nTransferData.setNameAndValue("SubMissionID",tSubMissionID);
        //���ӹ���������
        nTransferData.setNameAndValue("BusiType", "1001");
        //String sqlStr="select activityid from lwmission where MissionID = '"+tMissionID+"'  and activityid in(select activityid from lwactivity where functionid='10010028') ";//and missionprop1='"+tPrtNo+"'
        //String ActivityID=tExeSQL.getOneValue(sqlStr);
        String ActivityID = request.getParameter("ActivityID");//add by lzf
    	nTransferData.setNameAndValue("ActivityID",ActivityID); 
        String UWAuthority = request.getParameter("UWAuthority");
        nTransferData.setNameAndValue("UWAuthority", UWAuthority);
        System.out.println("UWAuthority==="+UWAuthority);
        
        nTransferData.setNameAndValue("UWUpReport",tuwUpReport);
        loggerDebug("UWManuNormChk","#################1");
        nTransferData.setNameAndValue("UWPopedomCode",tUWPopedomCode);
 				nTransferData.setNameAndValue("UWPopedomGrade",tUWPopedomGrade);
        //nTransferData.setNameAndValue("UWPopedom",tuwPopedom);
        //nTransferData.setNameAndValue("UWPer",tuwPer);
        
//        if((tuwUpReport!=null)&&!(tuwUpReport.equals("0"))){
        
          nTransferData.setNameAndValue("SugIndUWFlag",tSugIndUWFlag);
          nTransferData.setNameAndValue("SugIndUWIdea",tSugIndUWIdea);
        //ln 2008-10-17 add ����ԭ�� ����ԭ�� �ܱ�ԭ�� Ͷ������
          nTransferData.setNameAndValue("WithDReasonCode",tWithDReasonCode);
          nTransferData.setNameAndValue("DelayReasonCode",tDelayReasonCode);
          nTransferData.setNameAndValue("RefuReasonCode",tRefuReasonCode);
          nTransferData.setNameAndValue("WithDReason",tWithDReason);
          nTransferData.setNameAndValue("DelayReason",tDelayReason);
          nTransferData.setNameAndValue("RefuReason",tRefuReason);
          nTransferData.setNameAndValue("SugContInput",tSugContInput);          
          
        loggerDebug("UWManuNormChk","#################1");
          //�˱��ϱ��켣��
          tLCUWSendTraceSchema.setOtherNo(tContNo);		
          tLCUWSendTraceSchema.setOtherNoType("1");                   //���� 
          tLCUWSendTraceSchema.setUWFlag(tUWFlag);
          tLCUWSendTraceSchema.setUWIdea(tUWIdea);
         // ln 2008-10-17 add �ϱ�ԭ��
          tLCUWSendTraceSchema.setUpReason(tUpReason);
//        tLCUWSendTraceSchema.setYesOrNo(tYesOrNo);
        loggerDebug("UWManuNormChk","#################1");
          //nTransferData.setNameAndValue("LCUWSendTraceSchema",tLCUWSendTraceSchema);
//        }
        tVData.clear();
        tVData.add(nTransferData);
        tVData.add(tLCUWSendTraceSchema);
        tVData.add(tG);

        //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
        //String busiName3="tbTbWorkFlowUI";
        String busiName3="tWorkFlowUI";
   		BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
   		      
        //if (tBusinessDelegate3.submitData(tVData,"0000001110",busiName3) == false)
        if (tBusinessDelegate3.submitData(tVData,"excute",busiName3) == false)
        {
          int n = tBusinessDelegate3.getCErrors().getErrorCount();
          for (int i = 0; i < n; i++)
          Content = "�˹��˱�ʧ�ܣ�ԭ���ǣ�" + tBusinessDelegate3.getCErrors().getError(0).errorMessage;
          FlagStr = "Fail";
        }
        else{
			    	Content = "�˹��˱������ɹ�!";
			    	FlagStr = "Succ";
        }
                
			  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			  if (FlagStr == "Fail") //if 5
			  {
			   
			    tError = tBusinessDelegate3.getCErrors();
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
  }

  loggerDebug("UWManuNormChk","flag="+FlagStr);
  loggerDebug("UWManuNormChk","Content="+Content);

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
