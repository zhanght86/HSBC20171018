<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorConfirmSave.jsp
//�����ܣ���������ȫ��ȫȷ��
//�������ڣ�2005-05-08 20:02:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.db.LDSysVarDB"%>
  <%@page import="com.sinosoft.lis.operfee.IndiDueFeePartUI"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
	String sTemplatePath = application.getRealPath("xerox/printdata") + "/";
	String tEdorAcceptNo=request.getParameter("EdorAcceptNo");
	
	TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));
    tTransferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
    tTransferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));
    tTransferData.setNameAndValue("TemplatePath", sTemplatePath);
    tTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
    tTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo1"));
    tTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType1"));
    tTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName1"));
    tTransferData.setNameAndValue("Apptype", request.getParameter("Apptype1"));
    tTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom1"));
    tTransferData.setNameAndValue("AppntName", request.getParameter("AppntName1"));
    tTransferData.setNameAndValue("PaytoDate", request.getParameter("PaytoDate1"));
 //   tTransferData.setNameAndValue("CustomerNo", request.getParameter("CustomerNo1"));
 //   tTransferData.setNameAndValue("InsuredName", request.getParameter("InsuredName1"));
 //   tTransferData.setNameAndValue("InsuredName", request.getParameter("InsuredName2"));
 //   tTransferData.setNameAndValue("InsuredName", request.getParameter("InsuredName3"));
 //   tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType1"));
 //   tTransferData.setNameAndValue("theCurrentDate", request.getParameter("theCurrentDate1"));
 //   tTransferData.setNameAndValue("BackDate", request.getParameter("BackDate1"));
 //   tTransferData.setNameAndValue("VIP", request.getParameter("VIP1"));
 //   tTransferData.setNameAndValue("StarAgent", request.getParameter("StarAgent1"));
    
    String fmtransact = request.getParameter("fmtransact");
    if(fmtransact.equals("INSERT||SUBMITUW"))
    {
    	tTransferData.setNameAndValue("UWState",request.getParameter("uWstate1"));
    	tTransferData.setNameAndValue("CustomerIdea",request.getParameter("CustomerIdea"));
    	tTransferData.setNameAndValue("UWActivityStatus","4");
    	
    	// add by jiaqiangli 2009-04-03 ����ǿ���˹��˱�ԭ��
    	tTransferData.setNameAndValue("MandatoryManuUWReason",request.getParameter("MMUWReason"));
    }
  
  System.out.println("=========+++" + request.getParameter("EdorAcceptNo"));
  VData tVData = new VData();        
//  EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
	// String busiName="tWorkFlowUI";
	 String busiName="WorkFlowUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //add by jiaqiangli 2009-06-10 Ϊ��ֹ����
  
  

	try
	{
		  TransferData lockTransferData=new TransferData();
		  lockTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
		  lockTransferData.setNameAndValue("LockModule", "LF0001");
		  lockTransferData.setNameAndValue("Operator",  tG.Operator);
		  VData lockVData = new VData();
		  lockVData.add(lockTransferData);			
		  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(tBusinessDelegate.submitData(lockVData, "lock3String", "PubConcurrencyLockUI")) {
			  lockTransferData=new TransferData();
			  lockTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
			  lockTransferData.setNameAndValue("LockModule", "BQCF01");
			  lockTransferData.setNameAndValue("Operator",  tG.Operator);
			  lockVData = new VData();
			  lockVData.add(lockTransferData);			
			  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  
			  
			  if(tBusinessDelegate.submitData(lockVData, "lock3String", "PubConcurrencyLockUI")) {
				  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				  
				      
				 			
  						tTransferData.setNameAndValue("BusiType", "1002");
				  		tVData.add(tG);		
				  		tVData.add(tTransferData);
				 		  tBusinessDelegate.submitData(tVData, "create",busiName);
				  		if(tBusinessDelegate.getCErrors().needDealError())
				  		{
					  		Content = tBusinessDelegate.getCErrors().getFirstError();
					    	FlagStr = "Fail";
				  		}
			  }else{
		   			Content = "ϵͳæ����������ʧ�ܣ������ظ������";
		   		    FlagStr = "Fail";
			  }
			}else {
				Content = "�������ڽ��������˷ѣ��뷵��";
			    FlagStr = "Fail";
			}
		//LF0001 ��ȫȷ���������˷ѵ���䲢������
//		if (mPubLock.lock(tEdorAcceptNo, "LF0001", tG.Operator) == true) {
//           System.out.println("��ȫȷ���������˷ѵ���䲢�����ƴ���ɹ�");           
//   		//BQCF01 ��ȫȷ�ϲ���������
 //  		if (mPubLock.lock(tEdorAcceptNo, "BQCF01", tG.Operator) == true) {
 //  			tVData.add(tG);		
 //  			tVData.add(tTransferData);
//   			tEdorWorkFlowUI.submitData(tVData, "0000000009");
 //  			tBusinessDelegate.submitData(tVData, "0000000009",busiName);
//   		}
//   		else {
//   			Content = "ϵͳæ����������ʧ�ܣ������ظ������";
 //  		    FlagStr = "Fail";
 //  		}
//		}
//		else {
//			Content = "�������ڽ��������˷ѣ��뷵��";
//		    FlagStr = "Fail";
//		}

	}
	catch(Exception ex)
	{
		if(fmtransact.equals("INSERT||SUBMITUW"))
		{
		     Content = "ǿ���ύ�˱�ʧ�ܣ�ԭ����:" + ex.toString();
		     FlagStr = "Fail";
		}
		else
		{
	      Content = "ȷ��ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
		}
	}
	finally {
		//ע�����
		  TransferData lockTransferData=new TransferData();
		  VData lockVData = new VData();
		 		

		  lockTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
		  lockTransferData.setNameAndValue("LockModule", "BQCF01");

		  lockVData.add(lockTransferData);	
	      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(lockVData, "unLockJSP", "PubConcurrencyLockUI"))
				{
					Content = "����ʧ�ܣ�ԭ����:"+ tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
				}
		lockTransferData=new TransferData();
		lockVData = new VData();
				 		

		lockTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
		lockTransferData.setNameAndValue("LockModule", "LF0001");

		lockVData.add(lockTransferData);	
		tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(lockVData, "unLockJSP", "PubConcurrencyLockUI"))
		{
			Content = "����ʧ�ܣ�ԭ����:"+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
				
	}
	if ("".equals(FlagStr))
	{
		if(fmtransact.equals("INSERT||SUBMITUW"))
		{
			Content = "ǿ���ύ�˱��ɹ���";
	    	FlagStr = "Succ";
		}
		else
		{
			String sql = " select edorstate from lpedorapp " +
						 " where edoracceptno = '" + request.getParameter("EdorAcceptNo") + "' ";
			
		       TransferData sTransferData=new TransferData();
		       sTransferData.setNameAndValue("SQL", sql);
		       VData sVData = new VData();
	         sVData.add(sTransferData);
	         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	         String sOverDueFlag = "";
	         if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
	         {
	        	 sOverDueFlag = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
	         }
			//ExeSQL exeSQL = new ExeSQL();
			//String sOverDueFlag = exeSQL.getOneValue(sql);
			
			if (sOverDueFlag.equals("4"))  
			{
				Content = "��ȫȷ�ϳɹ��������˲���ֹ�ڣ���ȫ����������ֹ��";
				FlagStr = "Succ";
			}
			else
			{
//			    tError = tEdorWorkFlowUI.mErrors;
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
	//=========��ȫȷ�ϳɹ����Զ�ִ��ȷ����Ч����================BGN======================
					//PEdorValidBL tPEdorValidBL = new PEdorValidBL();
					//try
					//{
					//	tPEdorValidBL.submitData(tVData, "");
					//}
					//catch(Exception ex)
					//{
					//      Content = "��ȫȷ�ϳɹ������Ǳ�ȫȷ����Чʧ�ܣ�ԭ����:" + ex.toString();
					//      FlagStr = "Fail";
					//}
					if ("".equals(FlagStr))
					{
						
						   // tError = tPEdorValidBL.mErrors;
						   // if (!tError.needDealError())
						   // {
								Content ="��ȫȷ�ϳɹ���";
						    	FlagStr = "Succ";
						    	//ȡ���Ƿ���Ҫ���ѱ�־
								sql = " select 1 from dual where exists (select 'X' from LJaGet " +
									  " where othernotype = '10' and otherno = '" + tEdorAcceptNo + 
									  "' and sumgetmoney <> 0)";
								//exeSQL = new ExeSQL();
								System.out.println("-----------------------------\n"+sql);
								//String sNeedGetFlag = exeSQL.getOneValue(sql);
								String sNeedGetFlag = "";
								sTransferData=new TransferData();
							       sTransferData.setNameAndValue("SQL", sql);
							       sVData = new VData();
						         sVData.add(sTransferData);
						         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

						         if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
						         {
						        	 sNeedGetFlag = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
						         }
								if (sNeedGetFlag == null) sNeedGetFlag = "";
								System.out.println("-----------------------------\n"+sNeedGetFlag);
								if (sNeedGetFlag.equals("1"))
								{
									Content += "���ӡ����֪ͨ�飡";
								}
								
								//add by jiaqiangli 2009-04-27 paytodate+60d<=sysdate ���´��մ���
								sql = "select sysvarvalue from LDSysVar where SysVar = 'aheaddays'";
								
								sTransferData=new TransferData();
							       sTransferData.setNameAndValue("SQL", sql);
							       sVData = new VData();
						         sVData.add(sTransferData);
						         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
						         int tAheadDays = 0;
						         if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
						         {
						        	 String ret = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
						        	 if(ret != null && !"".equals(ret)){
						        		 tAheadDays = Integer.parseInt(ret);
						        	 }else{
						        		 tAheadDays = 60; //Ĭ��Ϊ60��
						        	 }
						         }else{
						        	 tAheadDays = 60; //Ĭ��Ϊ60��
						         }
								//LDSysVarDB tLDSysVarDB = new LDSysVarDB();
								//tLDSysVarDB.setSysVar("aheaddays");
								
								
								
								
								//if (tLDSysVarDB.getInfo() == false) {
								//	tAheadDays = 60; //Ĭ��Ϊ60��
								//} 
								//else {
								//	tAheadDays = Integer.parseInt(tLDSysVarDB.getSysVarValue());
								//}
								String tLJSPaySQL="select contno from lcpol where contno=(select contno from lpedoritem where EDORacceptno='"+request.getParameter("EdorAcceptNo")+"') and appflag='1' "
								                 +"and polno=mainpolno and paytodate+"+tAheadDays+" <= to_date('"+PubFun.getCurrentDate()+"','YYYY-MM-DD') ";
								System.out.println("tLJSPaySQL"+tLJSPaySQL);
								
								sTransferData=new TransferData();
							       sTransferData.setNameAndValue("SQL", tLJSPaySQL);
							       sVData = new VData();
						         sVData.add(sTransferData);
						         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
						         if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
						         {
						        	 
								//ExeSQL tExeSQL = new ExeSQL();
								String tLJSPaySQLFlag =  (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
								if (tLJSPaySQLFlag != null && !"".equals(tLJSPaySQLFlag)) {
									String tStartDate = "";
									String tEndDate = PubFun.calDate(PubFun.getCurrentDate(), -tAheadDays, "D", null);
									TransferData tVTransferData = new TransferData();
									tVTransferData.setNameAndValue("StartDate", tStartDate);
									tVTransferData.setNameAndValue("EndDate", tEndDate);
									tVTransferData.setNameAndValue("Contno", tLJSPaySQLFlag);		
									System.out.println("StartDate==" + tStartDate);
									System.out.println("EndDate==" + tEndDate);
									System.out.println("ContNo==" + tLJSPaySQLFlag);
									
									VData tTVData = new VData();
									tTVData.add(tG);
									tTVData.add(tVTransferData);
									
									//IndiDueFeePartUI tIndiDueFeePartUI = new IndiDueFeePartUI();
									//tIndiDueFeePartUI.submitData(tTVData, "ZC"); //������ڴ��ձ��
									tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
									
									if(!tBusinessDelegate.submitData(tTVData,"ZC","IndiDueFeePartUI")){
										Content += "�����������ڴ���ʧ�ܣ�ԭ����"+tBusinessDelegate.getCErrors().getFirstError()+"!";
									}
									//if (tIndiDueFeePartUI.mErrors.needDealError() == true) {
									//	Content += "�����������ڴ���ʧ�ܣ�ԭ����"+tIndiDueFeePartUI.mErrors.getFirstError()+"��";
									//}
								}
						         }
								//add by jiaqiangli 2009-04-27 paytodate+60d<=sysdate ���´��մ���
						    //}
						   // else                                                                           
						    //{
						   // 	Content = "��ȫȷ�ϳɹ������Ǳ�ȫȷ����Чʧ�ܣ�ԭ����:" + tError.getFirstError();
						   // 	FlagStr = "Fail";
						   // }
					}
	//=========��ȫȷ�ϳɹ����Զ�ִ��ȷ����Ч����================END======================
			    }
			    else                                                                           
			    {
			    	Content = "��ȫȷ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
			    	FlagStr = "Fail";
			    }
			}
		}
	} 
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 