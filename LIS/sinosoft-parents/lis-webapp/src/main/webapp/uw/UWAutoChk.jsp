<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.workflowengine.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
	LCContSchema tLCContSchema = new LCContSchema();

    String tChk[] = request.getParameterValues("InpPublicWorkPoolGridChk");
    
	String tContNo[] = request.getParameterValues("PublicWorkPoolGrid1");
	String tMissionID[] = request.getParameterValues("PublicWorkPoolGrid6");
	String tSubMissionID[] = request.getParameterValues("PublicWorkPoolGrid7");
    String tActivityID[] = request.getParameterValues("PublicWorkPoolGrid8");

	boolean flag = false;
	int tcontCount = tChk.length;	
	int totalCount = 0;
	GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot.getInstance();
	String tSql = "select sysvarvalue,remark from ldsysvar where sysvar='UwSampling'";
	SSRS tSSRS = new SSRS();
	//ExeSQL tExeSQL = new ExeSQL();
	TransferData sTransferData3=new TransferData();
    sTransferData3.setNameAndValue("SQL", tSql);
    VData sVData3 = new VData();
    sVData3.add(sTransferData3);
    BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate3.submitData(sVData3, "execSQL", "ExeSQLUI"))
    {
    	tSSRS = (SSRS)tBusinessDelegate3.getResult().getObjectByObjectName("SSRS", 0);
    }
//	tSSRS = tExeSQL.execSQL(tSql);
	String tDate = tSSRS.GetText(1, 2);
	String tCurrentDate = PubFun.getCurrentDate();
	if(0!=PubFun.calInterval(tDate, tCurrentDate, "D")){
		totalCount = 0;
	} else {
		int tCurrentCount = Integer.parseInt(tSSRS.GetText(1, 1));
//		tCurrentCount = tCurrentCount+1;
		totalCount = tCurrentCount;
	}
	String updateSql = "update ldsysvar set sysvarvalue='"+totalCount+"' , "
					+ " remark='"+tCurrentDate+"' where sysvar='UwSampling'";
//	tExeSQL.execUpdateSQL(updateSql);
	TransferData sTransferData2=new TransferData();
    sTransferData2.setNameAndValue("SQL", updateSql);
    VData sVData2 = new VData();
    sVData2.add(sTransferData2);
    BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
    boolean dealFlag = tBusinessDelegate2.submitData(sVData2, "execUpdateSQL", "ExeSQLUI");
    if(dealFlag)
    {
    	loggerDebug("UWAutoChk","exec ok");
    }
	//PubConcurrencyLock mPubLock = new PubConcurrencyLock();
    loggerDebug("UWAutoChk","--------------------------------自动核保-----开始--------------------------------");
    try{
    	String busiName4="PubConcurrencyLockUI";
    	TransferData sTransferData4=new TransferData();
        //sTransferData4.setNameAndValue("OperatedNo", "0000001003");
        //SYY BEGIN
        sTransferData4.setNameAndValue("OperatedNo", "10010005");
        //SYY END
        sTransferData4.setNameAndValue("LockModule", "LC3001");
        sTransferData4.setNameAndValue("Operator", tG.Operator);
        VData sVData4 = new VData();
        sVData4.add(sTransferData4);
    	BusinessDelegate tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
//   	    if (!mPubLock.lock("0000001003", "LC3001")) {
	    if (!tBusinessDelegate4.submitData(sVData4, "lock3String",busiName4)) {
	    	Content = " 提示信息: 当前业务组已被锁定";
			FlagStr = "Fail";
		}else{
			for (int i = 0; i < tcontCount; i++) {
		        loggerDebug("UWAutoChk","--------------------------------------------------------------------------");
				loggerDebug("UWAutoChk","----自动核保--正在处理第["+i+"]个合同，合同号为:" + tContNo[i]);
		        
		        
		        TransferData tTransferData = new TransferData();
				if (!tContNo[i].equals("") && tChk[i].equals("1"))
				{
		
		            tTransferData.setNameAndValue("ContNo",tContNo[i]);
		                        
			    	tTransferData.setNameAndValue("MissionID",tMissionID[i]);
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID[i]);
		            tTransferData.setNameAndValue("ActivityID", tActivityID[i]);
		            
		            //SYY BEGIN
		            String tSQL_a = " select A.y from (select '9999999999' x,(case when defaultoperator is not null then defaultoperator else MissionProp20 end) y from lwmission where  activityid='0000001100' "
							+ " and missionid = '"+ tMissionID[i]+ "' "
							+ " union "
							+ " select serialno x,(case when defaultoperator is not null then defaultoperator else MissionProp20 end) y from lbmission where  activityid='0000001100' "
							+ " and missionid = '" + tMissionID[i] + "' "
							+ " ) A where A.y is not null order by A.x desc ";
				ExeSQL tExeSQL = new ExeSQL();
				String lastUserCode = tExeSQL.getOneValue(tSQL_a);
				if (lastUserCode == null || lastUserCode.equals("")) {
					lastUserCode = null;
				}
				System.out.println("sql="+tSQL_a);
				System.out.println("LastUserCode="+lastUserCode);
				tTransferData.setNameAndValue("LastUserCode",lastUserCode);
		            //SYY END
		
		            loggerDebug("UWAutoChk","Missionid:"+tMissionID[i]);
		            loggerDebug("UWAutoChk","SubMissionID:"+tSubMissionID[i]);
		            loggerDebug("UWAutoChk","ActivityID:"+tActivityID[i]);
		            
				    flag = true;
				}
		        
				try
				{
		            loggerDebug("UWAutoChk","flag=="+flag);
				  	if (flag == true && !tContNo[i].equals("") && tChk[i].equals("1") )
				  	{
						// 准备传输数据 VData
						VData tVData = new VData();
						tVData.add( tTransferData );
						tVData.add( tG );
		                
						/*
						//TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
						String busiName="tbTbWorkFlowUI";
                        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		                
						if (tBusinessDelegate.submitData(tVData,"0000001003",busiName) == false)
						{
							int n = tBusinessDelegate.getCErrors().getErrorCount();
							loggerDebug("UWAutoChk","n=="+n);
							for (int j = 0; j < n; j++)
							loggerDebug("UWAutoChk","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
							Content = " 提示信息: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
							FlagStr = "Fail";
							*/
						
							//SYY BEGIN
							String busiName = "WorkFlowUI";
							BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
							if (tBusinessDelegate.submitData(tVData, "execute", busiName)) {
								FlagStr = "Succ";
							//SYY END
						}else{
							tCurrentDate = PubFun.getCurrentDate();
							totalCount = mGlobalCheckSpot.getCurrentCheckNum();
							updateSql = "update ldsysvar set sysvarvalue='"+totalCount+"', "
												+ " remark='"+tCurrentDate+"' where sysvar='UwSampling'";
							//tExeSQL = new ExeSQL();
		//					tExeSQL.execUpdateSQL(updateSql);
							TransferData sTransferData5=new TransferData();
						    sTransferData5.setNameAndValue("SQL", updateSql);
						    VData sVData5 = new VData();
						    sVData5.add(sTransferData5);
						    BusinessDelegate tBusinessDelegate5=BusinessDelegate.getBusinessDelegate();
						    boolean dealFlag5 = tBusinessDelegate5.submitData(sVData5, "execUpdateSQL", "ExeSQLUI");
						    if(dealFlag5)
						    {
						    	loggerDebug("UWAutoChk","dealFlag5 exec ok");
						    }
						}
		
						//如果在Catch中发现异常，则不从错误类中提取错误信息
						if (FlagStr == "Fail")
						{
						    tError = tBusinessDelegate.getCErrors();
						    //tErrors = tTbWorkFlowUI.mErrors;
						    
						    if (!tError.needDealError())
						    {                          
						    	Content = "";
						    	FlagStr = "Succ";
						    }
						    else                                                                           
						    {
						    	Content = " 提示信息：";
						    
						    	int n = tError.getErrorCount();
						    	//Content = "自动核保失败!";
				    			if (n > 0)
				    			{
							      for(int j = 0;j < n;j++)
							      {
							        //tError = tErrors.getError(j);
							        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
							      }
									}
						    	FlagStr = "Fail";
						    }
						}
					}
				} //try end
				catch(Exception e)
				{
					e.printStackTrace();
					Content = Content.trim() +" 提示:异常退出.";
				}
			}
	        loggerDebug("UWAutoChk","--------------------------------------------------------------------------");
		}
    }catch(Exception e){
    	e.printStackTrace();
    	Content = Content.trim() +" 提示:异常退出.";
    }finally{
//    	mPubLock.unLock();
    	String busiName6="PubConcurrencyLockUI";
    	TransferData sTransferData6=new TransferData();
        VData sVData6 = new VData();
        sVData6.add(sTransferData6);
    	BusinessDelegate tBusinessDelegate6=BusinessDelegate.getBusinessDelegate();
	    if(!tBusinessDelegate6.submitData(sVData6, "unLock", busiName6))
		{
			Content = "解锁失败，原因是:"+ tBusinessDelegate6.getCErrors().getFirstError();
		    FlagStr = "Fail";
		}
    } //for end
    loggerDebug("UWAutoChk","--------------------------------自动核保-----结束--------------------------------");

%>
<html>
<script language="javascript">
	parent.fraInterface.afterAutoChk("<%=FlagStr%>","<%=Content%>");
</script>
</html>
