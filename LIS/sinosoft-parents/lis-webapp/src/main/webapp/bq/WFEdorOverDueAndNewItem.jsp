
<jsp:directive.page import="com.sinosoft.lis.db.LPEdorAppDB"/>
<jsp:directive.page import="com.sinosoft.lis.vschema.LPEdorItemSet"/><%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorOverDueSave.jsp
//程序功能：工作流保全保全手动终止
//创建日期：2005-12-19 20:02:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//modify    zbx     20110902   
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
<%

  //输出参数
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

		//modify by jiaqiangli 保全确认下的强制终止 增加类型 以区分逾期终止
		if (!tPEdorOverDueBL.setOverDue(sEdorAcceptNo, "d"))
		{
			tError = tPEdorOverDueBL.mErrors;
	    	Content = "拒保失败，原因是:" + tError.getFirstError();
	    	FlagStr = "Fail";
		}else{
			
			//保全申请
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
				Content = "拒保失败，原因是: 生成保全申请号错误！";
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
					    tLPEdorAppSchema.setEdorAcceptNo(tNewEdorAcceptNo); //保全受理号
					    tLPEdorAppSchema.setOtherNo(tContNo); //申请号码
					    tLPEdorAppSchema.setOtherNoType("3"); //申请号码类型
					    tLPEdorAppSchema.setEdorAppName(tOldLPEdorAppSchema.getEdorAppName()); //申请人名称
					    tLPEdorAppSchema.setAppType(tOldLPEdorAppSchema.getAppType()); //申请方式    
						tLPEdorAppSchema.setEdorAppDate(PubFun.getCurrentDate()); //批改申请日期
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
						tLPEdorAppSchema.setPayGetName(tOldLPEdorAppSchema.getPayGetName());  //补退费领取人
						tLPEdorAppSchema.setPersonID(tOldLPEdorAppSchema.getPersonID());  //补退费领取人身份证号
						
						tLPEdorAppSchema.setManageCom(tG.ManageCom); //管理机构	
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
      
						//添加成功
						//添加保全项
						//打上特殊标识为拒保的，只能做XT和CT
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
							Content = "添加协议退保保全项失败，原因是:"+tPEdorAppItemUI.mErrors.getError(0).errorMessage+",请手动添加协议退保保全项";
							FlagStr = "Fail";
						}						
					}else{
						Content = "拒保失败，原因是:查询工作流失败!" ;
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
			    tLPEdorAppSchema.setEdorAcceptNo(tNewEdorAcceptNo); //保全受理号
			    tLPEdorAppSchema.setOtherNo(tContNo); //申请号码
			    tLPEdorAppSchema.setOtherNoType("3"); //申请号码类型
			    tLPEdorAppSchema.setEdorAppName(tOldLPEdorAppSchema.getEdorAppName()); //申请人名称
			    tLPEdorAppSchema.setAppType(tOldLPEdorAppSchema.getAppType()); //申请方式    
				tLPEdorAppSchema.setEdorAppDate(PubFun.getCurrentDate()); //批改申请日期
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
				tLPEdorAppSchema.setPayGetName(tOldLPEdorAppSchema.getPayGetName());  //补退费领取人
				tLPEdorAppSchema.setPersonID(tOldLPEdorAppSchema.getPersonID());  //补退费领取人身份证号
				
				tLPEdorAppSchema.setManageCom(tG.ManageCom); //管理机构	
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
					//添加成功
					//添加保全项
					//打上特殊标识为拒保的，只能做XT和CT
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
						Content = "添加协议退保保全项失败，原因是:"+tPEdorAppItemUI.mErrors.getError(0).errorMessage+",请手动添加协议退保保全项";
					    FlagStr = "Fail";
					}
					
					
				}else{
					Content = "拒保失败，原因是:"+tBusinessDelegate.getCErrors().getFirstError() ;
				    FlagStr = "Fail";
				}
				
			}else{
				Content = "拒保失败，原因是:查询工作流失败!" ;
			    FlagStr = "Fail";
			} */
		}
	}
	catch(Exception ex)
	{
	      Content = "拒保失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tPEdorOverDueBL.mErrors;
		    if (!tError.needDealError())
		    { 
		    	Content = "已拒保，请到无扫描申请保全任务中操作协议退保/退保!";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "拒保失败，原因是:" + tError.getFirstError();
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
 