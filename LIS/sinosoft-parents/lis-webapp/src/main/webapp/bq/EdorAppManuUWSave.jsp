<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorAppManuUWSave.jsp
//�����ܣ���ȫ�˹��˱�
//�������ڣ�2005-06-02 15:59:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  
  CErrors tError = null;
  
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
    
    String UWType       = request.getParameter("UWType");  //�˱�����
    loggerDebug("EdorAppManuUWSave.jsp","ssssssssssssssssssssssssssssss"+UWType);
    String tEdorAcceptNo= request.getParameter("EdorAcceptNo");
    String tEdorNo      = request.getParameter("EdorNo");
    String tEdorType    = request.getParameter("EdorType");
    String tContNo      = request.getParameter("ContNo");
    String tPolNo       = request.getParameter("PolNo");
    String tInsuredNo   = request.getParameter("InsuredNo");
    String tMissionID   = request.getParameter("MissionID");
    ExeSQL yexeSQL = new ExeSQL();
    SSRS ySSRS = new SSRS();
    String sqlStr="select activityid from lwmission where MissionID = '"+tMissionID+"'  and activityid in(select activityid from lwactivity where functionid='10020004') and missionprop1='"+tEdorAcceptNo+"'";
  	ySSRS = yexeSQL.execSQL(sqlStr);
    String tActivityID   = ySSRS.GetText(1,1);
    String tSubMissionID= request.getParameter("SubMissionID");
    String tOtherNo     = request.getParameter("OtherNo");
    String tOtherNoType = request.getParameter("OtherNoType");
    String tEdorAppName = request.getParameter("EdorAppName");
    String tApptype     = request.getParameter("Apptype");
    String tManageCom   = request.getParameter("ManageCom");
    String tAppntName   = request.getParameter("AppntNamew");
    String tAppntNo     = request.getParameter("AppntNo");
    String tPaytoDate   = request.getParameter("PaytoDate");
    
    String tUWIdea      = request.getParameter("UWIdea");  //�˱����
    String tUWFlag      = request.getParameter("EdorUWState");  //�˱�����
    String tUWDelay     = request.getParameter("UWDelay");      //�ӳ�ʱ��
    String tAppUser     = request.getParameter("UWPopedomCode"); //�ϱ�����
    
    String tAppUWIdea      = request.getParameter("AppUWIdea");  //�˱����
    String tAppUWState      = request.getParameter("AppUWState");  //�˱�����
    String tSendNoticeFlag      = request.getParameter("SendNoticeFlag");  //�Ƿ��ӡ�˱�֪ͨ��
    
  //�ں˱�������ִ������жϱ�־
	String testAflag = "2";
    
    if(tUWFlag.equals("2"))
    {
    	tUWFlag = request.getParameter("FormerUWState"); //ԭ�˱�����
    }

        //�˹��˱���Ϣ        
        tTransferData.setNameAndValue("PostponeDate", tUWDelay);
        tTransferData.setNameAndValue("AppUser", tAppUser);
        
        //�����������ڵ�[��ȫ����]��Ҫ������
        tTransferData.setNameAndValue("MissionID", tMissionID);
        tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
        tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
        tTransferData.setNameAndValue("EdorNo", tEdorNo);
        tTransferData.setNameAndValue("EdorType", tEdorType);
        tTransferData.setNameAndValue("OtherNo", tOtherNo);
        tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
        tTransferData.setNameAndValue("EdorAppName", tEdorAppName);
        tTransferData.setNameAndValue("Apptype", tApptype);
        tTransferData.setNameAndValue("ManageCom", tManageCom);
        tTransferData.setNameAndValue("AppntName", tAppntName);
        tTransferData.setNameAndValue("PaytoDate", tPaytoDate);
        tTransferData.setNameAndValue("ContNo", tContNo);
        tTransferData.setNameAndValue("AppntNo", tAppntNo);       
        tTransferData.setNameAndValue("ActivityID", tActivityID);     
        tTransferData.setNameAndValue("testAflag", testAflag);
        loggerDebug("EdorAppManuUWSave",UWType);        
        
        LPEdorAppSchema tLPEdorAppSchema;
        
        if (UWType.equals("EdorApp"))   //���뼶
        {
        	tTransferData.setNameAndValue("UWFlag", tAppUWState);
            tTransferData.setNameAndValue("UWIdea", tAppUWIdea);
            tTransferData.setNameAndValue("SendNoticeFlag", tSendNoticeFlag); 
            loggerDebug("EdorAppManuUWSave","�Ƿ��ӡ�˱�֪ͨ���־��1��ӡ��0����ӡ����"+tSendNoticeFlag);
            
            tLPEdorAppSchema = new LPEdorAppSchema();
            tLPEdorAppSchema.setEdorAcceptNo(tEdorAcceptNo);

//            EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI(); 
						//String busiName="tWorkFlowUI";
						
						String busiName="WorkFlowUI";
	 					BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            try
            {        
            	//tTransferData.setNameAndValue("UWFlag", tAppUWState);
                //tTransferData.setNameAndValue("UWIdea", tAppUWIdea);
              String sActivityID = "";
  			  tTransferData.setNameAndValue("BusiType", "1002");
              tVData.add(tGlobalInput);
              tVData.add(tTransferData);
              tVData.add(tLPEdorAppSchema);
                
//            tEdorWorkFlowUI.submitData(tVData, "0000000006");
              tBusinessDelegate.submitData(tVData, "execute",busiName);
            }
            
            catch(Exception ex)
            {
                  Content = "��ȫ����˱�ʧ�ܣ�ԭ����:" + ex.toString();
                  FlagStr = "Fail";
            }
            if ("".equals(FlagStr))
            {
//                    tError = tEdorWorkFlowUI.mErrors;
                    tError = tBusinessDelegate.getCErrors() ;
                    if (!tError.needDealError())
                    {                          
                        Content ="��ȫ����˱��ɹ���";
                        FlagStr = "Succ";
                    }
                    else                                                                           
                    {
                        Content = "��ȫ����˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
                        FlagStr = "Fail";
                    }
            }
        }
        else if (UWType.equals("EdorMain"))  //������
        {
            LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
            tLPEdorMainSchema.setEdorNo(tEdorNo);
            tLPEdorMainSchema.setContNo(tContNo);
            tLPEdorMainSchema.setEdorAcceptNo(tEdorAcceptNo);

           // PEdorContManuUWBL tPEdorContManuUWBL = new PEdorContManuUWBL(); 
            String busiName="PEdorContManuUWUI";
        	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 
            try
            {          
            	tTransferData.setNameAndValue("UWFlag", tUWFlag);
                tTransferData.setNameAndValue("UWIdea", tUWIdea);
                tTransferData.setNameAndValue("BusiType", "1002");
                tVData.add(tGlobalInput);
                tVData.add(tTransferData);
                tVData.add(tLPEdorMainSchema);
                
                tBusinessDelegate.submitData(tVData,"",busiName);
            }
            catch(Exception ex)
            {
                  Content = "��ȫ�����˱�ʧ�ܣ�ԭ����:" + ex.toString();
                  FlagStr = "Fail";
            }
            if ("".equals(FlagStr))
            {
                   // tError = tPEdorContManuUWBL.mErrors;
                    tError = tBusinessDelegate.getCErrors();;
                    if (!tError.needDealError())
                    {                          
                            Content ="��ȫ�����˱��ɹ���";
                            FlagStr = "Succ";
//=========�����˱��ɹ����Զ����б�ȫ�����˹��˱�================BGN======================
/*****BGN************
                        VData mResult = tPEdorContManuUWBL.getResult();
                        
                        TransferData mTransferData = 
                            (TransferData) mResult.getObjectByObjectName("TransferData", 0);
                        
                        String sAutoUWFlag = 
                            (String) mTransferData.getValueByName("AutoUWFlag");
                        String sAutoUWState = 
                            (String) mTransferData.getValueByName("AutoUWState");
                            
                        if (sAutoUWFlag.equals("true"))
                        {
                            tLPEdorAppSchema = new LPEdorAppSchema();
                            tLPEdorAppSchema.setEdorAcceptNo(tEdorAcceptNo);
                            
                            tTransferData.removeByName("UWFlag");
                            tTransferData.setNameAndValue("UWFlag", sAutoUWState);
                            loggerDebug("EdorAppManuUWSave","=== sAutoUWState ===" + sAutoUWState);
                           // EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
                           
                                //PEdorAppManuUWBeforeInitService tPEdorAppManuUWBeforeInitService=new PEdorAppManuUWBeforeInitService();                 
//                                tPEdorAppManuUWBeforeInitService.submitData(tVData,"");                                 
//							    VData qVData=tPEdorAppManuUWBeforeInitService.getResult();                                            
//							    MMap pedorMap =(MMap) qVData.getObjectByObjectName("MMap",0);                                         
//							                                                                                                          
//							    PEdorAppManuUWAfterInitService tPEdorAppManuUWAfterInitService=new PEdorAppManuUWAfterInitService();  
//							    tPEdorAppManuUWAfterInitService.submitData(tVData,"");                                                
//							    VData kVData=tPEdorAppManuUWAfterInitService.getResult();                                             
//							    MMap appMap =(MMap) kVData.getObjectByObjectName("MMap",0);                                           
//							    pedorMap.add(appMap);                                                                                 
//							                                                                                                          
//							    VData tResult=new VData();                                                                            
//							    tResult.add(pedorMap);                                                                                
//							    PubSubmit tPubSubmit = new PubSubmit();                                                               
//								if (!tPubSubmit.submitData(tResult, "")) {                                                              
//									// @@������                                                                                         
//									//this.mErrors.copyAllErrors(tPubSubmit.mErrors);                                                     
//									// CError tError = new CError();                                                                      
//									// tError.moduleName = "tPubSubmit";                                                                  
//									// tError.functionName = "submitData";                                                                
//									// tError.errorMessage = "�����ύʧ��!";                                                             
//									// this.mErrors.addOneError(tError);                                                                  
//									 loggerDebug("EdorAppManuUWSave.jsp","ssssssssssssssssssssssssssssss");                                                                             
//									//return false;                                                                                       
//								}                                                                                                       

                           
                           
                             String busiName="WorkFlowUI";
	 					BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
                           
                            try
                            {
                                tVData.clear();
                                tVData.add(tGlobalInput);
                                tVData.add(tTransferData);
                                tVData.add(tLPEdorAppSchema);
                                String sActivityID = "";
          						tTransferData.setNameAndValue("BusiType", "1002");
                               // tEdorWorkFlowUI.submitData(tVData, "0000000006");
          						tBusinessDelegate.submitData(tVData, "execute",busiName);
                            }
                            catch(Exception ex)
                            {
                                  Content = "��ȫ�����˱��ɹ������Ǳ�ȫ����˱�ʧ�ܣ�ԭ����:" + ex.toString();
                                  FlagStr = "Fail";
                            }
                            if ("".equals(FlagStr))
                            {
                                    //tError = tEdorWorkFlowUI.mErrors;
                                     tError = tBusinessDelegate.getCErrors() ;
                                    if (!tError.needDealError())
                                    {                          
                                        Content ="��ȫ�����˱��ɹ������ұ�ȫ����˱��ɹ���";
                                        FlagStr = "Succ";
                                    }
                                    else                                                                           
                                    {
                                        Content = "��ȫ�����˱��ɹ������Ǳ�ȫ����˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
                                        FlagStr = "Fail";
                                    }
                            }
                        }
                        else
                        {
                            Content ="��ȫ�����˱��ɹ���";
                            FlagStr = "Succ";
                        }
************END*****/
//=========�����˱��ɹ����Զ����б�ȫ�����˹��˱�================END======================
                    }
                    else                                                                           
                    {
                        Content = "��ȫ�����˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
                        FlagStr = "Fail";
                    }
            }
        }
        
        else if (UWType.equals("EdorItem"))  //���ּ�
        {
            LPPolSchema mLPPolSchema = new LPPolSchema();
            mLPPolSchema.setEdorNo(tEdorNo);
            mLPPolSchema.setEdorType(tEdorType);
            mLPPolSchema.setContNo(tContNo);
            mLPPolSchema.setPolNo(tPolNo);
            mLPPolSchema.setInsuredNo(tInsuredNo);
            
    
          //  PEdorPolManuUWBL tPEdorPolManuUWBL = new PEdorPolManuUWBL(); 
         	 String busiName="PEdorPolManuUWUI";
        	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 
            try
            {          
            	tTransferData.setNameAndValue("UWFlag", tUWFlag);
                tTransferData.setNameAndValue("UWIdea", tUWIdea);
                tTransferData.setNameAndValue("BusiType", "1002");
                tVData.add(tGlobalInput);
                tVData.add(tTransferData);
                tVData.add(mLPPolSchema);
                
                tBusinessDelegate.submitData(tVData,"",busiName);
            }
            //PEdorPolManuUWUI
          /*  try
            {       
            	tTransferData.setNameAndValue("UWFlag", tUWFlag);
                tTransferData.setNameAndValue("UWIdea", tUWIdea);
            	
                tVData.add(tGlobalInput);
                tVData.add(tTransferData);
                tVData.add(mLPPolSchema);
                
                tPEdorPolManuUWBL.submitData(tVData, "");
            }*/
            catch(Exception ex)
            {
                  Content = "��ȫ���ֺ˱�ʧ�ܣ�ԭ����:" + ex.toString();
                  FlagStr = "Fail";
            }
            if ("".equals(FlagStr))
            {
                    //tError = tPEdorPolManuUWBL.mErrors;
                    tError = tBusinessDelegate.getCErrors();
                    if (!tError.needDealError())
                    {                          
                        Content ="��ȫ���ֺ˱��ɹ���";
                        FlagStr = "Succ";
                    }
                    else                                                                           
                    {
                        Content = "��ȫ���ֺ˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
             
                        FlagStr = "Fail";
                    }
            }
        }       

%>
   
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
