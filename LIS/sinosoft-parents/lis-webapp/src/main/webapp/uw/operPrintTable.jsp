<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
 
<!--用户校验类-->
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>  
  <%@page import="com.sinosoft.service.*" %>

<%
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
// 输出参数
   String FlagStr = "";
   String Content = "";
   String PrintNo= (String)session.getValue("PrintNo");
   String tCode = (String)session.getValue("Code");
   String tPrtNo = (String)session.getValue("PrtNo");
   String tContNo = (String)session.getValue("ContNo");
   String tMissionID = (String)session.getValue("MissionID");
   String tSubMissionID = (String)session.getValue("SubMissionID");
   String tGrpContNo = (String)session.getValue("GrpContNo");
   String t11GrpContNo = (String)session.getValue("11GrpContNo");
   String tEdorNo = (String)session.getValue("EdorNo");
   String tEdorType = (String)session.getValue("EdorType");
   String tClmNo = (String)session.getValue("ClmNo");
   String tBatNo = (String)session.getValue("BatNo");
   /******预备工作流整体改造完成时调用，不用输写tOperate
   String tActivityID = (String)session.getValue("ActivityID");
   
   */
   String tAlertFlag = "";
   loggerDebug("operPrintTable","/***********************/");
   loggerDebug("operPrintTable","/***********************/");
   loggerDebug("operPrintTable","PrintNo:"+PrintNo); 
   loggerDebug("operPrintTable","tCode:"+tCode+"HOHO"); 
   loggerDebug("operPrintTable","tPrtNo:"+tPrtNo);
   loggerDebug("operPrintTable","tContNo:"+tContNo);
   loggerDebug("operPrintTable","tMissionID:"+tMissionID);
   loggerDebug("operPrintTable","tSubMissionID:"+tSubMissionID);
   loggerDebug("operPrintTable","put session value,tGrpContNo:"+tGrpContNo);

   if(PrintNo==null || tCode == null || "".equals(tCode))
   {
          FlagStr="Succ";
         	Content="打印数据完毕！";
        tAlertFlag = "Succ";
   }
   //tongmeng 2007-10-30 add
   //如果tMissionID = "TBJB" 则为承保直接下结论系统自动发放的单证，不需要做工作流扭转。直接打印
  else if("TBJB".equals(tMissionID)||tCode.equals("BQ84"))
  {
  	loggerDebug("operPrintTable","TBJB 默认打印");
	  LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	  loggerDebug("operPrintTable","TBJB 进入更新的PrintNo:"+PrintNo);
	  tLOPRTManagerSchema.setPrtSeq(PrintNo);
	   
	   VData tVData = new VData();
	   tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);

       PrintManagerBL tPrintManagerBL = new PrintManagerBL();
       if(!tPrintManagerBL.submitData(tVData,"CONFIRM"))
       {
         FlagStr="Fail:"+tPrintManagerBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr = "Nothing";
         Content="更新打印数据成功！";
// 	     session.putValue("PrintNo",null );

       }
       loggerDebug("operPrintTable","TBJB Print:"+FlagStr);
  }
  //xiongzh 2009-2-24 add
   //如果tMissionID = "XBJB" 则为续保直接下结论系统自动发放的单证，不需要做工作流扭转。直接打印
  else if("XBJB".equals(tMissionID))
  {
  	loggerDebug("operPrintTable","XBJB 默认打印");
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	loggerDebug("operPrintTable","XBJB 进入更新的PrintNo:"+PrintNo);
	tLOPRTManagerSchema.setPrtSeq(PrintNo);
	   
	   VData tVData = new VData();
	   tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);

     RnewRefusePUI tRnewRefusePUI = new RnewRefusePUI();
     if(!tRnewRefusePUI.submitData(tVData,"PRINT"))
     {
       FlagStr="Fail:"+tRnewRefusePUI.mErrors.getFirstError().toString();
     }
     else
     {
       FlagStr = "Nothing";
       Content="更新打印数据成功！";
// 	     session.putValue("PrintNo",null );

     }
     loggerDebug("operPrintTable","XBJB Print:"+FlagStr);
  }
   
  //保 险 费 转 账 不 成 功 通 知 书 Hanbin 2010-05-10 
  // 如果tMissionID = "TBBANK" 则为续保直接下结论系统自动发放的单证，不需要做工作流扭转。直接打印
  else if("TBBANK".equals(tMissionID))
  {
  	loggerDebug("operPrintTable","TBBANK 默认打印");
	LCContSchema tLCContSchema = new LCContSchema();
	loggerDebug("operPrintTable","TBBANK 保存的Contno:"+tContNo);
	tLCContSchema.setContNo(tContNo);
	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	loggerDebug("operPrintTable","TBBANK 进入更新的PrintNo:"+PrintNo);
	tLOPRTManagerSchema.setPrtSeq(PrintNo);
	   
	   VData tVData = new VData();
	   tVData.add(tLCContSchema);
	   tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);

	   BankF1PUI tBankF1PUI = new BankF1PUI();
     if(!tBankF1PUI.submitData(tVData,"PRINT"))
     {
       FlagStr="Fail:"+tBankF1PUI.mErrors.getFirstError().toString();
     }
     else
     {
       FlagStr = "Nothing";
       Content="生成打印数据成功！";
// 	     session.putValue("PrintNo",null );

     }
     loggerDebug("operPrintTable","TBBANK Print:"+FlagStr);
  } 
  else 
   {
   	System.out.println(tCode+"<><><><><><<<><<<<<<<<<<<<<<<<<<<<<<<<<<"); 
   	 if(tCode.equals("TB90")||tCode.equals("TB89")||tCode.equals("14")
   			 ||tCode.equals("03")||tCode.equals("LP03")|| tCode.equals("23") 
   			 || tCode.equals("04")||tCode.equals("LP04")||tCode.equals("05")
   			 ||tCode.equals("14")||tCode.equals("17") || tCode.equals("85")
   			 ||tCode.equals("00")||tCode.equals("06")||tCode.equals("81")
   			 ||tCode.equals("LP81")||tCode.equals("82")||tCode.equals("83")
   			 ||tCode.equals("84")||tCode.equals("86")||tCode.equals("87")
   			 ||tCode.equals("88")||tCode.equals("89")||tCode.equals("LP89")
   			 ||tCode.equals("BQ80")||tCode.equals("BQ81")||tCode.equals("BQ82")
   			 ||tCode.equals("BQ85")||tCode.equals("BQ86")
   			 ||tCode.equals("BQ87")||tCode.equals("BQ89")||tCode.equals("BQ88")
   			 ||tCode.equals("24")||tCode.equals("25")||tCode.equals("27")||tCode.equals("43")
   			||tCode.equals("44")||tCode.equals("45")||tCode.equals("LP90")||tCode.equals("LP99")) //edity by yaory
	   {
	   System.out.println(tCode+"<><><><><><<<><<<<<<<<<<<<<<<<<<<<<<<<<<"); 
	   			//准备数据
	   			loggerDebug("operPrintTable","开始打印后台处理");
					//准备公共传输信息
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("ContNo",tContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
					tTransferData.setNameAndValue("EdorNo",tEdorNo) ;
					tTransferData.setNameAndValue("EdorType",tEdorType) ;
					if(tClmNo!=null&&!"".equals(tClmNo))
					{
						tTransferData.setNameAndValue("ClmNo",tClmNo) ;
					}
					if(tBatNo!=null&&!"".equals(tBatNo))
					{
						tTransferData.setNameAndValue("BatNo",tBatNo) ;
					}
	  				//打印业务员通知书
	  			  if(tCode.equals("14"))
	  			  	tOperate = "0000001017";
	  			  //打印核保通知书(打印类)
	  			  if(tCode.equals("TB89")){//核保通知书打印现在区分投保人，第一被保人、第二被保人、第三被保人，所以这里不能写死activityid，需要查询
	  				  String activitySQL="select missionid,submissionid,activityid from lwmission where missionid='"+tMissionID+"' and missionprop3='"+PrintNo+"' and activityid in(select activityid from lwactivity where functionid in('10010026','10010063','10010064','10010065'))";
	  				  ExeSQL tExeSQL=new ExeSQL();
	  				  SSRS tSSRS=tExeSQL.execSQL(activitySQL);
	  				  if(tSSRS!=null && tSSRS.getMaxRow()>0){
	  					tTransferData.removeByName("SubMissionID");
	  					tTransferData.setNameAndValue("SubMissionID",tSSRS.GetText(1, 2)) ;
	  					tOperate=tSSRS.GetText(1, 3);
	  				  }
	  			  }
	  			  	//tOperate = "0000001107";
	  			  //核保通知书(非打印类)
	  			  if(tCode.equals("TB90")){
	  				String activitySQL="select missionid,submissionid,activityid from lwmission where missionid='"+tMissionID+"' and missionprop3='"+PrintNo+"' and activityid in(select activityid from lwactivity where functionid in('10010058','10010066','10010067','10010068'))";
	  				  ExeSQL tExeSQL=new ExeSQL();
	  				  SSRS tSSRS=tExeSQL.execSQL(activitySQL);
	  				  if(tSSRS!=null && tSSRS.getMaxRow()>0){
	  					tTransferData.removeByName("SubMissionID");
	  					tTransferData.setNameAndValue("SubMissionID",tSSRS.GetText(1, 2)) ;
	  					tOperate=tSSRS.GetText(1, 3);
	  				  }
	  			  }
	  			  	//tOperate = "0000001302";
	  			 //打印生调通知书
	  			  if(tCode.equals("04"))
	  			    tOperate = "0000001108";
	  			 //打印体检通知书
	  			  if(tCode.equals("03"))
	  			    tOperate = "0000001106";	  	
	  			 //保全体检
	  			  if(tCode.equals("23"))
		  			tOperate = "0000000302";	 
	  			  //保全生调
	  			  if(tCode.equals("24"))
	  				tOperate = "0000000312";	
	  			  //保全核保通知书
	  			  if(tCode.equals("25"))
	  				tOperate = "0000000320";	
	  			//保全补充资料通知书
	  			  if(tCode.equals("27"))
	  				tOperate = "0000000351";
	  				//续保二核体检
	  			  if(tCode.equals("43"))
		  			tOperate = "0000007007";	 
	  			  //续保二核生调
	  			  if(tCode.equals("44"))
	  				tOperate = "0000007010";	
	  			  //续保二核核保通知书
	  			  if(tCode.equals("45"))
	  				tOperate = "0000007004";	  
	  			  	
	  		//	if(tCode == "85")
	  			//    tOperate = "0000001023";  //打印业务员通知书任务节点编码
	  			if(tCode.equals("17"))
	  			    tOperate = "0000001230";  //打印客户合并通知书任务节点编码

	  			//打印理赔体检通知书
	  			if(tCode.equals("LP03")){
	  				tOperate = "0000005531";
	  			}    
	  			//打印理赔核保通知书
	  			if(tCode.equals("LP90")){
	  				String activitySQL="select missionid,submissionid,activityid from lwmission where missionid='"+tMissionID+"' and missionprop3='"+PrintNo+"' and activityid in(select activityid from lwactivity where functionid in('10030029'))";
	  				  ExeSQL tExeSQL=new ExeSQL();
	  				  SSRS tSSRS=tExeSQL.execSQL(activitySQL);
	  				  if(tSSRS!=null && tSSRS.getMaxRow()>0){
	  					tTransferData.removeByName("SubMissionID");
	  					tTransferData.setNameAndValue("SubMissionID",tSSRS.GetText(1, 2)) ;
	  					tOperate=tSSRS.GetText(1, 3);
	  				  }
	  			  
					//tOperate = "0000005551";
				}
	  			if(tCode.equals("LP99")){
					tOperate = "0000005561";
				}
	  			if(tCode.equals("00")||tCode.equals("06")||tCode.equals("81")||tCode.equals("LP81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("00")||tCode.equals("86")||tCode.equals("85")||tCode.equals("87")||tCode.equals("88")||tCode.equals("89")||tCode.equals("LP89")||tCode.equals("BQ80")||tCode.equals("BQ81")||tCode.equals("BQ82")||tCode.equals("BQ85")||tCode.equals("BQ84")||tCode.equals("BQ86")||tCode.equals("BQ87")||tCode.equals("BQ89")||tCode.equals("BQ88"))
	  			  tOperate = "0000001280"; //核保通知书任务节点编码
	  			 //if(tCode.equals("87")||tCode.equals("88"))
	  			  // tOperate = "0000001108";   //新华面见通知书任务节点编码
				
				if(tOperate==null ||"".equals(tOperate)){
					Content = "查询打印节点工作流失败";
					FlagStr = "Fail";	
				}else{
					//因新契约工作流未做升级，保全工作流通知书临时做特殊处理，后期因该保持一致（删除）
					LWActivityDB tLWActivityDB=new LWActivityDB();
					tLWActivityDB.setActivityID(tOperate);
					LWActivitySet tLWActivitySet=tLWActivityDB.query();
					String ErrorMessage = "";
				/********新契约工作流升级  modify by LIJIAN  20130307**********/
					//tTransferData.removeByName("ClmNo") ;
					//tTransferData.removeByName("BatNo") ;
					tTransferData.setNameAndValue("ActivityID", tOperate);
					tTransferData.setNameAndValue("BusiType", tLWActivitySet.get(1).getBusiType());
					VData tVData = new VData();
		 			tVData.add(tTransferData);
		 			tVData.add(tGI);
					loggerDebug("operPrintTable","tCode:"+tCode+"tOperate:"+tOperate);
					
					 String busiName="WorkFlowUI";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
					{    
						 if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
					{ 
							ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
	   							Content = "更新打印数据失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
	   							FlagStr = "Fail";
							}
							else
							{
	   							Content = "更新打印数据失败";
	   							FlagStr = "Fail";				
							}		

						}
					else
						{
	  					Content ="更新打印数据成功！";
						FlagStr = "Succ";	
						}
				}
					
						 
					
		
					/*if(tLWActivitySet.get(1).getFunctionID()!=null&&tLWActivitySet.get(1).getFunctionID().indexOf("10020")!=-1)
					{
								tTransferData.removeByName("ClmNo") ;
								tTransferData.removeByName("BatNo") ;
								tTransferData.setNameAndValue("ActivityID", tOperate);
  							tTransferData.setNameAndValue("BusiType", "1002");
								VData tVData = new VData();
	  			 			tVData.add(tTransferData);
	  			 			tVData.add(tGI);
								loggerDebug("operPrintTable","tCode:"+tCode+"tOperate:"+tOperate);
								
								 String busiName="tWorkFlowUI";
    						BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    						if(!tBusinessDelegate.submitData(tVData,"create",busiName))
   							{    
       							 if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        						{ 
           							ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
				   							Content = "更新打印数据失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   							FlagStr = "Fail";
										}
										else
										{
				   							Content = "更新打印数据失败";
				   							FlagStr = "Fail";				
										}		
	 
	 							}
								else
	 							{
				  					Content ="更新打印数据成功！";
		    						FlagStr = "Succ";	
	 							}	 
								
					}
				else{
					 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);
					loggerDebug("operPrintTable","tCode:"+tCode+"tOperate:"+tOperate);
    			   // 数据传输
					TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
					if (!tTbWorkFlowUI.submitData(tVData,tOperate)) //执行保全核保生调工作流节点0000000004
					{
						int n = tTbWorkFlowUI.mErrors.getErrorCount();
						Content = " 更新打印数据失败，原因是: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			else
    			{
    			    FlagStr="Succ";
    			    Content="更新打印数据成功！";
    			}
    			loggerDebug("operPrintTable","Print:"+FlagStr);
    		}*/
	 }
	 else if(tCode.trim().equals("bqnotice")) //保全通知书及收据，added by liurx
	 {
	 	 System.out.println(tCode+"<><><><><><<<><<<<<<<<<<<<<<<<<<<<<<<<<<"); 
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("OtherNo",PrintNo);	   
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tGI);

       PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
       if(!tPrtUpdateBL.submitData(tVData,"NOTICE"))
       {
           FlagStr="Fail:"+tPrtUpdateBL.mErrors.getFirstError().toString();
       }
       else
       {
           FlagStr="Succ";
           Content="更新打印数据成功！";
       }   
	 }
	 else if(tCode.trim().equals("BQCHECK")) //保全通知书及收据，added by liurx
	 {
	  System.out.println(tCode+"<><><><><><<<><<<<<<<<<<<<<<<<<<<<<<<<<<"); 
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("SendOutCom",String.valueOf(session.getValue("ssManageCom")));
	   tTransferData.setNameAndValue("ReceiveCom",String.valueOf(session.getValue("ssAgentCode")));
	   tTransferData.setNameAndValue("CertifyCode",(String)session.getValue("certifycode"));
	   tTransferData.setNameAndValue("StartNo",String.valueOf(session.getValue("ChequNo")));
	   tTransferData.setNameAndValue("EndNo",String.valueOf(session.getValue("ChequNo")));
	   tTransferData.setNameAndValue("Handler",tGI.Operator);
	   tTransferData.setNameAndValue("OtherNo",PrintNo);
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tGI);

       PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
       if(!tPrtUpdateBL.submitData(tVData,"BQCHECK"))
       {
           FlagStr="Fail:"+tPrtUpdateBL.mErrors.getFirstError().toString();
       }
       else
       {
           FlagStr="Succ";
           Content="更新打印数据成功！";
       }   
	 }
	 else if(tCode.trim().equals("endorsement")) //保全批单，added by liurx
	 {
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("OtherNo",PrintNo);	   
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tGI);

       PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
       if(!tPrtUpdateBL.submitData(tVData,"EDORNO"))
       {
         FlagStr="Fail:"+tPrtUpdateBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr="Succ";
         Content="更新打印数据成功！";
       }   
	 }
	 else if(tCode.trim().equals("appendorsement")) //保全批单，added by liurx
	 {
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("OtherNo",PrintNo);	   
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tGI);

       PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
       if(!tPrtUpdateBL.submitData(tVData,"EDORACCEPTNO"))
       {
         FlagStr="Fail:"+tPrtUpdateBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr="Succ";
         Content="更新打印数据成功！";
       }   
	 }
	 else if(tCode.trim().equals("EdorNameList")) //团体保全人名清单，added by QianLy
	 {
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("OtherNo",PrintNo);	   
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tGI);

       PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
       if(!tPrtUpdateBL.submitData(tVData,"GRPEDORNO"))
       {
         FlagStr="Fail:"+tPrtUpdateBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr="Succ";
         Content="更新打印数据成功！";
       }   
	 }
	 else if(tCode.trim().equals("G03") || tCode.trim().equals("G04"))
	 {
	    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
      tLOPRTManagerSchema.setPrtSeq(PrintNo);
      loggerDebug("operPrintTable","通知书流水号码："+PrintNo);
      VData tVData1 = new VData();
      tVData1.add(tLOPRTManagerSchema);
      tVData1.add(tGI );

      PrintSendOutSysCertBL tPrintSendOutSysCertBL = new PrintSendOutSysCertBL();
      if(!tPrintSendOutSysCertBL.submitData(tVData1,""))
      {
        FlagStr="Fail:"+tPrintSendOutSysCertBL.mErrors.getFirstError().toString();
      }
      else
      {
        //FlagStr1=tAutoSysCertSendOutBL.getResult();
        FlagStr = "Succ";
        Content="更新打印数据成功！";
        session.putValue("PrintNo",null );
      }
      loggerDebug("operPrintTable","SysCert:"+FlagStr);
    }
   	
	 else if(tCode.equals("54")||tCode=="54")
	 	{
		 	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		   loggerDebug("operPrintTable","进入更新的PrintNo:"+PrintNo);
		   tLOPRTManagerSchema.setPrtSeq(PrintNo);
		   
			    VData tVData = new VData();
			   tVData.add(tLOPRTManagerSchema);
			   tVData.add(tGI);
			   
			   PrtDealBL tPrtDealBL=new PrtDealBL();
			   if(!tPrtDealBL.submitData(tVData,"CONFIRM"))
			   {
			   	FlagStr="Fail:"+tPrtDealBL.mErrors.getFirstError().toString();
			  }
			else
				{
						 FlagStr = "Succ";
	         Content="更新打印数据成功！";
					}
					
	 	}
	 else
     {
     	loggerDebug("operPrintTable","默认打印");
	   LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	   loggerDebug("operPrintTable","进入更新的PrintNo:"+PrintNo);
	   tLOPRTManagerSchema.setPrtSeq(PrintNo);
	   
	   VData tVData = new VData();
	   tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);

       PrintManagerBL tPrintManagerBL = new PrintManagerBL();
       if(!tPrintManagerBL.submitData(tVData,"CONFIRM"))
       {
         FlagStr="Fail:"+tPrintManagerBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr = "Nothing";
         Content="更新打印数据成功！";
// 	     session.putValue("PrintNo",null );

       }
       loggerDebug("operPrintTable","Print:"+FlagStr);

   }
   
   
  }

   // 移除session中的变量，防止其它无需工作流的打印出错
   session.removeValue("Code");
   session.removeValue("PrtNo");

%>
<html>
<script language="javascript">

	
if("<%=FlagStr%>"=='Fail')
{
alert("此单证不能发送！");
}

if('<%=tAlertFlag%>'!=null&&'<%=tAlertFlag%>'!='')
{
	<%FlagStr = "Nothing";%>
}
window.close();

top.opener.afterSubmit("<%=FlagStr%>");
</script>
</html>


