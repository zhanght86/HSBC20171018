
<jsp:directive.page import="com.sinosoft.lis.db.LPEdorAppDB"/>
<jsp:directive.page import="com.sinosoft.lis.vschema.LPEdorItemSet"/><%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorOverDueSave.jsp
//�����ܣ���������ȫ��ȫ�ֶ���ֹ
//�������ڣ�2005-12-19 20:02:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//modify    zbx     20110902   
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
	String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
	
	LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
	tLPEdorAppDB.setEdorAcceptNo(sEdorAcceptNo);
	LPEdorAppSchema tOldLPEdorAppSchema = tLPEdorAppDB.query().get(1);
	String tNewEdorAcceptNo = "";
	String tNewMissionID = "";
	String tNewSubMissionID = "";
    String tContNo = request.getParameter("ContNo");  
	PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL(tG);
    
	try
	{		   

		//modify by jiaqiangli ��ȫȷ���µ�ǿ����ֹ �������� ������������ֹ
		if (!tPEdorOverDueBL.setOverDue(sEdorAcceptNo, "d"))
		{
			tError = tPEdorOverDueBL.mErrors;
	    	Content = "�ܱ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
	    	FlagStr = "Fail";
		}else{
			
			//��ȫ����
//			String sOperate = "9999999999"; 
//			String busiName="EdorWorkFlowUI";
//			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			//EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ICFlag", "");
			tTransferData.setNameAndValue("BusiType", "1002");
			String strLimit = PubFun.getNoLimit(tG.ManageCom);
			String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo",
					strLimit);
			if (StrTool.compareString(strEdorAcceptNo, "")) {
				Content = "�ܱ�ʧ�ܣ�ԭ����: ���ɱ�ȫ����Ŵ���";
		    	FlagStr = "Fail";
			} else {
				tTransferData.setNameAndValue("EdorAcceptNo", strEdorAcceptNo);
				tTransferData.setNameAndValue("ManageCom",
						tG.ManageCom);
				tTransferData.setNameAndValue("AppntName", "");
				tTransferData.setNameAndValue("PaytoDate", "");
				tTransferData.setNameAndValue("OtherNo", "");
				tTransferData.setNameAndValue("OtherNoType", "");
				tTransferData.setNameAndValue("EdorAppName", "");
				tTransferData.setNameAndValue("Apptype", "");
				tTransferData.setNameAndValue("EdorAppDate", "");
				tTransferData.setNameAndValue("DefaultOperator", tG.Operator);
				VData tVData = new VData();
				tVData.add(tG);		
				tVData.add(tTransferData);
				
				String busiName="WorkFlowUI";
		 		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 		if(tBusinessDelegate.submitData(tVData, "create",busiName)){
		 			VData mResult =tBusinessDelegate.getResult();	
					TransferData rTransferData = (TransferData)mResult.getObjectByObjectName("TransferData", 0);	
					tNewEdorAcceptNo = (String)rTransferData.getValueByName("EdorAcceptNo");
					String tMissionSql = "select missionid,submissionid from lwmission where  activityid = '0000000002' and missionprop1 = '"+tNewEdorAcceptNo+"'";
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tMisSSRS = tExeSQL.execSQL(tMissionSql);
					if(tMisSSRS.MaxRow>0){
						tNewMissionID = tMisSSRS.GetText(1,1);
						tNewSubMissionID = tMisSSRS.GetText(1,2);
						
					    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
					    tLPEdorAppSchema.setEdorAcceptNo(tNewEdorAcceptNo); //��ȫ�����
					    tLPEdorAppSchema.setOtherNo(tContNo); //�������
					    tLPEdorAppSchema.setOtherNoType("3"); //�����������
					    tLPEdorAppSchema.setEdorAppName(tOldLPEdorAppSchema.getEdorAppName()); //����������
					    tLPEdorAppSchema.setAppType(tOldLPEdorAppSchema.getAppType()); //���뷽ʽ    
						tLPEdorAppSchema.setEdorAppDate(PubFun.getCurrentDate()); //������������
						tLPEdorAppSchema.setBankCode(tOldLPEdorAppSchema.getBankCode());
						tLPEdorAppSchema.setBankAccNo(tOldLPEdorAppSchema.getBankAccNo());
						tLPEdorAppSchema.setAccName(tOldLPEdorAppSchema.getAccName());
						tLPEdorAppSchema.setBehalfName(tOldLPEdorAppSchema.getBehalfName());
						tLPEdorAppSchema.setBehalfIDType(tOldLPEdorAppSchema.getBehalfIDType());
						tLPEdorAppSchema.setBehalfIDNo(tOldLPEdorAppSchema.getBehalfIDNo());
						tLPEdorAppSchema.setBehalfPhone(tOldLPEdorAppSchema.getBehalfPhone());
						tLPEdorAppSchema.setBehalfCode(tOldLPEdorAppSchema.getBehalfCode());
						tLPEdorAppSchema.setEdorAppPhone(tOldLPEdorAppSchema.getEdorAppPhone());
						tLPEdorAppSchema.setBehalfCodeCom(tOldLPEdorAppSchema.getBehalfCodeCom());
						tLPEdorAppSchema.setSwitchChnlType(tOldLPEdorAppSchema.getSwitchChnlType());
						tLPEdorAppSchema.setSwitchChnlName(tOldLPEdorAppSchema.getSwitchChnlName());
						tLPEdorAppSchema.setPayGetName(tOldLPEdorAppSchema.getPayGetName());  //���˷���ȡ��
						tLPEdorAppSchema.setPersonID(tOldLPEdorAppSchema.getPersonID());  //���˷���ȡ�����֤��
						
						tLPEdorAppSchema.setManageCom(tG.ManageCom); //�������	
						tLPEdorAppSchema.setEdorState("3");						
						
						tLPEdorAppSchema.setOperator(tG.Operator);
						tLPEdorAppSchema.setMakeDate(PubFun.getCurrentDate());
						tLPEdorAppSchema.setMakeTime(PubFun.getCurrentTime());
						tLPEdorAppSchema.setModifyDate(PubFun.getCurrentDate());
						tLPEdorAppSchema.setModifyTime(PubFun.getCurrentTime());

						VData tInputData = new VData();
						MMap mMap = new MMap();
						mMap.put(tLPEdorAppSchema, "INSERT");
						tInputData.add(mMap);
						PubSubmit tPubSubmit = new PubSubmit();
						tPubSubmit.submitData(tInputData, "");
						
						TransferData mTransferData = new TransferData();
						
						mTransferData.setNameAndValue("EdorAcceptNo", tNewEdorAcceptNo);   			
						mTransferData.setNameAndValue("MissionID", tNewMissionID);
						mTransferData.setNameAndValue("SubMissionID", tNewSubMissionID);	
						mTransferData.setNameAndValue("ActivityID", "0000000002");
						mTransferData.setNameAndValue("OtherNo", tContNo);
						mTransferData.setNameAndValue("OtherNoType", "3");
						mTransferData.setNameAndValue("EdorAppName", tOldLPEdorAppSchema.getEdorAppName());
						mTransferData.setNameAndValue("Apptype", tOldLPEdorAppSchema.getAppType());
						mTransferData.setNameAndValue("EdorAppDate", PubFun.getCurrentDate());	
						mTransferData.setNameAndValue("ManageCom",tG.ManageCom );          
						mTransferData.setNameAndValue("EdorState","3");	
						mTransferData.setNameAndValue("NodeID","0000000002");		
						mTransferData.setNameAndValue("Transact","INSERT||EDORAPP");
						String tAppntNameSQL = "select AppntName from lccont  where contno = '"+tContNo+"' and appflag in ( '1', '4')";
						String tPayToDateSQL = "select PaytoDate from lcpol where polno = mainpolno and contno = '"+tContNo+"'";
						
						String tAppntName = tExeSQL.getOneValue(tAppntNameSQL);
						String tPaytoDate = tExeSQL.getOneValue(tPayToDateSQL);
						mTransferData.setNameAndValue("AppntName", tAppntName);
						mTransferData.setNameAndValue("PaytoDate", tPaytoDate);
      
						//��ӳɹ�
						//��ӱ�ȫ��
						//���������ʶΪ�ܱ��ģ�ֻ����XT��CT
						String tSQL = "update lwmission set missionprop24 = 'XC' where missionid ='"+tNewMissionID+"'";
						tExeSQL.execUpdateSQL(tSQL);
						LPEdorItemSet mLPEdorItemSet =new LPEdorItemSet();
		  				TransferData tEdorTransferData = new TransferData(); 
						String tEdortransact = "INSERT||EDORITEM";
						String tLoadFlag = "edorApp";
						String displayType = "1";
		  					
		  				tEdorTransferData.setNameAndValue("DisplayType", displayType);
		  				tEdorTransferData.setNameAndValue("MissionID", tNewMissionID);
		  				tEdorTransferData.setNameAndValue("SubMissionID", tNewSubMissionID);
		  				tEdorTransferData.setNameAndValue("LoadFlag", tLoadFlag);
		  					
						VData tEdorItemVData = new VData();
						PEdorAppItemUI tPEdorAppItemUI   = new PEdorAppItemUI();
						LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
						tLPEdorItemSchema.setEdorAcceptNo(tNewEdorAcceptNo);
						tLPEdorItemSchema.setDisplayType(displayType); 
						tLPEdorItemSchema.setEdorType("XT");
						tLPEdorItemSchema.setEdorAppDate(PubFun.getCurrentDate());
				    	tLPEdorItemSchema.setAppReason("1");
				    	tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
						tLPEdorItemSchema.setInsuredNo("000000");
						tLPEdorItemSchema.setPolNo("000000");
						mLPEdorItemSet.add(tLPEdorItemSchema); 
							
						tEdorItemVData.add(mLPEdorItemSet);
						tEdorItemVData.add(tEdorTransferData);
						tEdorItemVData.add(tG);
							
						if(!tPEdorAppItemUI.submitData(tEdorItemVData,tEdortransact)){
							Content = "���Э���˱���ȫ��ʧ�ܣ�ԭ����:"+tPEdorAppItemUI.mErrors.getError(0).errorMessage+",���ֶ����Э���˱���ȫ��";
							FlagStr = "Fail";
						}						
					}else{
						Content = "�ܱ�ʧ�ܣ�ԭ����:��ѯ������ʧ��!" ;
					    FlagStr = "Fail";
					}
		 		}
			}
//			VData tVData = new VData();
//			tVData.add(tG);		
//			tVData.add(tTransferData);
//			tBusinessDelegate.submitData(tVData, sOperate,busiName);
/* 			VData mResult =tBusinessDelegate.getResult();
			//modify    zbx     20110902 
			//VData mINResult =(VData)mResult.getObjectByObjectName("VData", 0);
			//TransferData rTransferData = (TransferData)mINResult.getObjectByObjectName("TransferData", 0);	
			TransferData rTransferData = (TransferData)mResult.getObjectByObjectName("TransferData", 0);	
			tNewEdorAcceptNo = (String)rTransferData.getValueByName("EdorAcceptNo");
			String tMissionSql = "select missionid,submissionid from lwmission where  activityid = '0000000002' and missionprop1 = '"+tNewEdorAcceptNo+"'";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tMisSSRS = tExeSQL.execSQL(tMissionSql);
			if(tMisSSRS.MaxRow>0){
				tNewMissionID = tMisSSRS.GetText(1,1);
				tNewSubMissionID = tMisSSRS.GetText(1,2);
				
			    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
			    tLPEdorAppSchema.setEdorAcceptNo(tNewEdorAcceptNo); //��ȫ�����
			    tLPEdorAppSchema.setOtherNo(tContNo); //�������
			    tLPEdorAppSchema.setOtherNoType("3"); //�����������
			    tLPEdorAppSchema.setEdorAppName(tOldLPEdorAppSchema.getEdorAppName()); //����������
			    tLPEdorAppSchema.setAppType(tOldLPEdorAppSchema.getAppType()); //���뷽ʽ    
				tLPEdorAppSchema.setEdorAppDate(PubFun.getCurrentDate()); //������������
				tLPEdorAppSchema.setBankCode(tOldLPEdorAppSchema.getBankCode());
				tLPEdorAppSchema.setBankAccNo(tOldLPEdorAppSchema.getBankAccNo());
				tLPEdorAppSchema.setAccName(tOldLPEdorAppSchema.getAccName());
				tLPEdorAppSchema.setBehalfName(tOldLPEdorAppSchema.getBehalfName());
				tLPEdorAppSchema.setBehalfIDType(tOldLPEdorAppSchema.getBehalfIDType());
				tLPEdorAppSchema.setBehalfIDNo(tOldLPEdorAppSchema.getBehalfIDNo());
				tLPEdorAppSchema.setBehalfPhone(tOldLPEdorAppSchema.getBehalfPhone());
				tLPEdorAppSchema.setBehalfCode(tOldLPEdorAppSchema.getBehalfCode());
				tLPEdorAppSchema.setEdorAppPhone(tOldLPEdorAppSchema.getEdorAppPhone());
				tLPEdorAppSchema.setBehalfCodeCom(tOldLPEdorAppSchema.getBehalfCodeCom());
				tLPEdorAppSchema.setSwitchChnlType(tOldLPEdorAppSchema.getSwitchChnlType());
				tLPEdorAppSchema.setSwitchChnlName(tOldLPEdorAppSchema.getSwitchChnlName());
				tLPEdorAppSchema.setPayGetName(tOldLPEdorAppSchema.getPayGetName());  //���˷���ȡ��
				tLPEdorAppSchema.setPersonID(tOldLPEdorAppSchema.getPersonID());  //���˷���ȡ�����֤��
				
				tLPEdorAppSchema.setManageCom(tG.ManageCom); //�������	
				tLPEdorAppSchema.setEdorState("3");
				
				TransferData mTransferData = new TransferData();
				
				mTransferData.setNameAndValue("EdorAcceptNo", tNewEdorAcceptNo);   			
				mTransferData.setNameAndValue("MissionID", tNewMissionID);
				mTransferData.setNameAndValue("SubMissionID", tNewSubMissionID);	
				mTransferData.setNameAndValue("ActivityID", "0000000002");
				mTransferData.setNameAndValue("OtherNo", tContNo);
				mTransferData.setNameAndValue("OtherNoType", "3");
				mTransferData.setNameAndValue("EdorAppName", tOldLPEdorAppSchema.getEdorAppName());
				mTransferData.setNameAndValue("Apptype", tOldLPEdorAppSchema.getAppType());
				mTransferData.setNameAndValue("EdorAppDate", PubFun.getCurrentDate());	
				mTransferData.setNameAndValue("ManageCom",tG.ManageCom );          
				mTransferData.setNameAndValue("EdorState","3");	
				mTransferData.setNameAndValue("NodeID","0000000002");		
				mTransferData.setNameAndValue("Transact","INSERT||EDORAPP");
				String tAppntNameSQL = "select AppntName from lccont  where contno = '"+tContNo+"' and appflag in ( '1', '4')";
				String tPayToDateSQL = "select PaytoDate from lcpol where polno = mainpolno and contno = '"+tContNo+"'";
				
				String tAppntName = tExeSQL.getOneValue(tAppntNameSQL);
				String tPaytoDate = tExeSQL.getOneValue(tPayToDateSQL);
				mTransferData.setNameAndValue("AppntName", tAppntName);
				mTransferData.setNameAndValue("PaytoDate", tPaytoDate);
				
				VData tContAppVData = new VData();       

				//EdorWorkFlowUI tContAppEdorWorkFlowUI = new EdorWorkFlowUI();
				busiName="EdorWorkFlowUI";
				tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				tContAppVData.addElement(tG);
				tContAppVData.addElement(tLPEdorAppSchema);
				tContAppVData.add(mTransferData);
				
				if(tBusinessDelegate.submitData(tContAppVData, "0000000002",busiName)){
					//��ӳɹ�
					//��ӱ�ȫ��
					//���������ʶΪ�ܱ��ģ�ֻ����XT��CT
					String tSQL = "update lwmission set missionprop24 = 'XC' where missionid ='"+tNewMissionID+"'";
					tExeSQL.execUpdateSQL(tSQL);
					LPEdorItemSet mLPEdorItemSet =new LPEdorItemSet();
  					TransferData tEdorTransferData = new TransferData(); 
					String tEdortransact = "INSERT||EDORITEM";
					String tLoadFlag = "edorApp";
					String displayType = "1";
  					
  					tEdorTransferData.setNameAndValue("DisplayType", displayType);
  					tEdorTransferData.setNameAndValue("MissionID", tNewMissionID);
  					tEdorTransferData.setNameAndValue("SubMissionID", tNewSubMissionID);
  					tEdorTransferData.setNameAndValue("LoadFlag", tLoadFlag);
  					
					VData tEdorItemVData = new VData();
					PEdorAppItemUI tPEdorAppItemUI   = new PEdorAppItemUI();
					LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
					tLPEdorItemSchema.setEdorAcceptNo(tNewEdorAcceptNo);
					tLPEdorItemSchema.setDisplayType(displayType); 
					tLPEdorItemSchema.setEdorType("XT");
					tLPEdorItemSchema.setEdorAppDate(PubFun.getCurrentDate());
		    		tLPEdorItemSchema.setAppReason("1");
		    		tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
					tLPEdorItemSchema.setInsuredNo("000000");
					tLPEdorItemSchema.setPolNo("000000");
					mLPEdorItemSet.add(tLPEdorItemSchema); 
					
					tEdorItemVData.add(mLPEdorItemSet);
					tEdorItemVData.add(tEdorTransferData);
					tEdorItemVData.add(tG);
					
					if(!tPEdorAppItemUI.submitData(tEdorItemVData,tEdortransact)){
						Content = "���Э���˱���ȫ��ʧ�ܣ�ԭ����:"+tPEdorAppItemUI.mErrors.getError(0).errorMessage+",���ֶ����Э���˱���ȫ��";
					    FlagStr = "Fail";
					}
					
					
				}else{
					Content = "�ܱ�ʧ�ܣ�ԭ����:"+tBusinessDelegate.getCErrors().getFirstError() ;
				    FlagStr = "Fail";
				}
				
			}else{
				Content = "�ܱ�ʧ�ܣ�ԭ����:��ѯ������ʧ��!" ;
			    FlagStr = "Fail";
			} */
		}
	}
	catch(Exception ex)
	{
	      Content = "�ܱ�ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tPEdorOverDueBL.mErrors;
		    if (!tError.needDealError())
		    { 
		    	Content = "�Ѿܱ����뵽��ɨ�����뱣ȫ�����в���Э���˱�/�˱�!";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "�ܱ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	} 
%>
   
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 