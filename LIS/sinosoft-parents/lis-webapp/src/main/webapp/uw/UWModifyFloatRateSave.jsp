<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ProposalSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//Modify by niuzj,2006-08-23,英大需要在录入受益人信息时增加一个“性别”字段
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%
CErrors tError = null;
String FlagStr = "";
String Content = "";
int k = 0; //标志录入特约
String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //数字转换对象
String ttDutyCode = "";
String tContNo = request.getParameter("ContNo");
//String tPolNo = request.getParameter("PolNo");
ttDutyCode = request.getParameter( "DutyCode" );
String tOperate = "UPDATE||PROPOSAL";
GlobalInput tG = new GlobalInput();
tG = ( GlobalInput )session.getValue( "GI" );
//获取相应的数据
String approveFlag = "";
String UWFlag = "";
String approveCode = "";
String approveDate = "";

	LCPremToAccDB tLCPremToAccDB =new LCPremToAccDB();
	LCPremToAccSet tLCPremToAccSet =new LCPremToAccSet();
	LCPremSchema tLCPremSchema = new LCPremSchema();
	LCPremDB tLCPremDB =new LCPremDB();
	LCPremSet tLCPremSet = new LCPremSet();
	LCDutySchema tLCDutySchema =new LCDutySchema();
	LCDutyDB tLCDutyDB =new LCDutyDB();
	LCDutySet tLCDutySet = new LCDutySet();
	LCSpecDB tLCSpecDB = new LCSpecDB();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema(); //员工合同特约
	LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
	LCCustomerImpartDB tLCCustomerImpartDB =new LCCustomerImpartDB();
	LCCustomerImpartSet tLCCustomerImpartSet =new LCCustomerImpartSet();
	LCBnfDB tLCBnfDB = new LCBnfDB();
	LCBnfSet tLCBnfSet =new LCBnfSet();
	LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
	LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
	LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	LCInsuredDB tLCInsuredDB = new LCInsuredDB();
	LCInsuredSet tLCInsuredSet = new LCInsuredSet();
	LCGrpAppntSchema tLCGrpAppntSchema =new LCGrpAppntSchema();
	LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
	LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
	LCAppntSchema tLCAppntSchema =new LCAppntSchema();
	LCAppntDB tLCAppntDB = new LCAppntDB();
	LCAppntSet tLCAppntSet = new LCAppntSet();
	LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	LCGrpPolDB tLCGrpPolDB =new LCGrpPolDB();
	LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	LCPolSchema tLCPolSchema = new LCPolSchema();
	LCPolDB tLCPolDB = new LCPolDB();
	LCPolSet tLCPolSet = new LCPolSet();
	LCContSchema tLCContSchema = new LCContSchema();
	LCContDB tLCContDB = new LCContDB();
	LCContSet tLCContSet = new LCContSet();
	LCGetDB tLCGetDB = new LCGetDB();
	LCGetSet tLCGetSet = new LCGetSet();
	String tChk[] = request.getParameterValues("InpRiskFloatRateGridChk");
	String tPolNo[] = request.getParameterValues("RiskFloatRateGrid5");
	String tGetDutyKind[] = request.getParameterValues("RiskFloatRateGrid6");
	String tNewFloatRate[] = request.getParameterValues("RiskFloatRateGrid4");
	//String tRadio[] = request.getParameterValues("InpObjGridSel"); 
	String tSpecOperate = request.getParameter("SpecOperate");
	//String tGetDutyKind = request.getParameter("GetDutyKind");
	String tInsuredNo = request.getParameter("InsuredNo"); //add ln 2008-12-6 被保人客户号
	loggerDebug("UWModifyFloatRateSave","有些险种需要GetDutyKind："+tGetDutyKind);
	//tongmeng 2009-05-09 增加删除员工特约
	if(tSpecOperate!=null&&!tSpecOperate.equals("DELETE&&YGSPEC"))
	{
	for(int j=0;j<tChk.length;j++)
	{
		if(tChk[j].equals("1"))
		{
			//改行被选中
	try{
		loggerDebug("UWModifyFloatRateSave","ContNo: "+tContNo);
		loggerDebug("UWModifyFloatRateSave","PolNo: "+tPolNo);
		//根据合同号查询LCPremToAccSchema表
		tLCPremToAccDB.setPolNo(tPolNo[j]);
	    tLCPremToAccSet = tLCPremToAccDB.query();
		//LCPrem
		tLCPremDB.setPolNo(tPolNo[j]);
		tLCPremSet =tLCPremDB.query();
		LCPremSet tLCPremSet1 =new LCPremSet();
		for(int i=1;i<=tLCPremSet.size();i++){
			tLCPremSchema =new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			tLCPremSchema.setPrem(0);
			//tLCPremSchema.setSumPrem(0);
			//tLCPremSchema.setStandPrem(0);
			tLCPremSet1.add(tLCPremSchema);
		}
		
		//合同级的员工特约录入
		tLCCSpecSchema.setSerialNo(request.getParameter("SerialNo"));
		tLCCSpecSchema.setContNo(request.getParameter("ContNo"));
		tLCCSpecSchema.setGrpContNo("00000000000000000000");
		tLCCSpecSchema.setProposalContNo(request.getParameter("ContNo"));
		tLCCSpecSchema.setOperator(tG.Operator);
		tLCCSpecSchema.setPrtFlag("1");
		tLCCSpecSchema.setNeedPrint("N");
		tLCCSpecSchema.setSpecCode(request.getParameter("SpecCode"));
		tLCCSpecSchema.setSpecType(request.getParameter("SpecType"));
		tLCCSpecSchema.setSpecReason(request.getParameter("SpecReason"));
		//loggerDebug("UWModifyFloatRateSave","特约原因：" + request.getParameter("SpecReason"));
		tLCCSpecSchema.setSpecContent(request.getParameter("FloatRateIdea"));
		tLCCUWMasterSchema.setSpecReason(request.getParameter("SpecReason"));
		//LCDuty   FloatRate
		tLCDutyDB.setPolNo(tPolNo[j]);
		tLCDutySet = tLCDutyDB.query();
		if(tLCDutySet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","查询LCDuty表失败！");
		}else{
			tLCDutySchema =tLCDutySet.get(1);
			tLCDutySchema.setFloatRate(tNewFloatRate[j]);
			tLCDutySchema.setPrem(0);
			tLCDutySchema.setCalRule("2");
			
			//tLCDutySchema.setCalRule("0");
			//tLCDutySchema.setSumPrem(0);
			//tLCDutySchema.setStandPrem(0);
		}
		
		//LCSpec 
		tLCSpecDB.setPolNo(tPolNo[j]);
		tLCSpecSet = tLCSpecDB.query();
		//LCCustomerImpart
		tLCCustomerImpartDB.setContNo(tContNo);
		tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		//LCBnf
		tLCBnfDB.setPolNo(tPolNo[j]);
		tLCBnfSet = tLCBnfDB.query();
		//LCInsuredRelated
		tLCInsuredRelatedDB.setPolNo(tPolNo[j]);
		tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
		//LCInsured
		tLCInsuredDB.setContNo(tContNo);
		tLCInsuredSet = tLCInsuredDB.query();
		if(tLCInsuredSet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","查询LCInsured表错误！");
		}else{
			tLCInsuredSchema = tLCInsuredSet.get(1);
		}
		//LCGrpAppnt
		tLCGrpAppntDB.setPrtNo(tContNo);
		tLCGrpAppntSet = tLCGrpAppntDB.query();
		if(tLCGrpAppntSet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","查询LCGrpAppnt表错误！");
		}else{
			tLCGrpAppntSchema = tLCGrpAppntSet.get(1);
		}
		//LCAppnt
		tLCAppntDB.setContNo(tContNo);
		tLCAppntSet = tLCAppntDB.query();
		if(tLCAppntSet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","查询LCAppnt表错误！");
		}else{
			tLCAppntSchema =tLCAppntSet.get(1);
		}
		//LCGrpPol
		tLCGrpPolDB.setGrpPolNo(tPolNo[j]);
		if(tLCGrpPolDB.getInfo()==true){
			tLCGrpPolSchema = tLCGrpPolDB.getSchema();
		}
		//LCPol
		tLCPolDB.setPolNo(tPolNo[j]);
		if(tLCPolDB.getInfo()==true){
			tLCPolSchema =tLCPolDB.getSchema();
			tLCPolSchema.setFloatRate(tNewFloatRate[j]);
			tLCPolSchema.setPrem(0);
			//tLCPolSchema.setSumPrem(0);
			//tLCPolSchema.setStandPrem(0);
			approveFlag = tLCPolSchema.getApproveFlag();
			UWFlag = tLCPolSchema.getUWFlag();
			approveCode = tLCPolSchema.getApproveCode();
			approveDate = tLCPolSchema.getApproveDate();
			
			
		}
		//LCCont
		tLCContDB.setContNo(tContNo);
		if(tLCContDB.getInfo()==true){
			tLCContSchema = tLCContDB.getSchema();
		}
		
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("SavePolType",tLCPolSchema.getAppFlag());
        tTransferData.setNameAndValue("samePersonFlag", request.getParameter("SamePersonFlag"));
        tTransferData.setNameAndValue("deleteAccNo", "0");
        tTransferData.setNameAndValue("ChangePlanFlag", "0");
        tTransferData.setNameAndValue("GetDutyKind",tGetDutyKind[j]);
		VData tVData = new VData();
		  tVData.addElement(tLCContSchema);
		  tVData.addElement(tLCGrpPolSchema);
		  tVData.addElement(tLCPolSchema);
		  tVData.addElement(tLCAppntSchema);
		  tVData.addElement(tLCGrpAppntSchema);
		  tVData.addElement(tLCInsuredSchema);
		  tVData.addElement(tLCInsuredRelatedSet);
		  tVData.addElement(tLCBnfSet);
		  //tVData.addElement(tLCCustomerImpartSet);
		  //tVData.addElement(tLCCSpecSchema);
		  tVData.addElement(tLCSpecSet);
		  tVData.addElement(tLCDutySet);
		  //tVData.addElement(tLCPremSet1);
	      tVData.addElement(tLCPremToAccSet);
		  tVData.addElement(tG);
		  tVData.addElement(tTransferData);
		  
		  ProposalUI tProposalUI = new ProposalUI();
		  
		  if( !tProposalUI.submitData( tVData, tOperate ) ) {
			    tError = tProposalUI.mErrors;
			    Content += tError.getFirstError();
			    FlagStr = "Fail";
			  }
			  else {
				  if(k==0){
					  
					/*

			approveFlag = (String)tTransferData.getValueByName("ApproveFlag");
			                UWFlag = (String)tTransferData.getValueByName("UWFlag");
			                approveCode = (String)tTransferData.getValueByName("ApproveCode");
			                approveDate = (String)tTransferData.getValueByName("ApproveDate");
			
					*/
					 //tongmeng 2008-12-18 add
					  //修改投保单,需要恢复险种数据
					
					  {
						  	LCPolSchema tempLCPolSchema = new LCPolSchema();
						    tLCPolSchema.setPolNo(tPolNo[j]);
						    tLCPolSchema.setApproveFlag(approveFlag);
						    tLCPolSchema.setUWFlag(UWFlag);
						    tLCPolSchema.setApproveCode(approveCode);
						    tLCPolSchema.setApproveDate(approveDate);

						    VData VData3 = new VData();
						    VData3.add(tLCPolSchema);
							 	VData3.add(tG);
							 	QueryPolInfoBL tQueryPolInfoBL = new QueryPolInfoBL();
						    if (!tQueryPolInfoBL.submitData(VData3, "INSERT")) {
						      Content = "操作失败，原因是: " + tQueryPolInfoBL.mErrors.getError(0).errorMessage;
						      FlagStr = "Fail";
						      throw new Exception();
						    }
					  }
					
					  
					tVData = new VData();
					tVData.add(tLCContSchema);
					tVData.add(tTransferData);
				  	tVData.add(tLCCSpecSchema);
				  	tVData.add( tLCCUWMasterSchema );
					//tTransferData.setNameAndValue("PolNo",tPolNo);
					//tTransferData.setNameAndValue("Operatetype",tOperatetype);
					//tTransferData.setNameAndValue("Proposalno",tProposalno);
					//tTransferData.setNameAndValue("Serialno",tSerialno);					
				tVData.add( tG );
		
				// 数据传输
				UWSpecInputUI tUWSpecInputUI   = new UWSpecInputUI();
				if (!tUWSpecInputUI.submitData(tVData,tSpecOperate))
				  {     
		        
					int n = tUWSpecInputUI.mErrors.getErrorCount();
					Content = " 员工特约保存失败，原因是: " + tUWSpecInputUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				   }
//					如果在Catch中发现异常，则不从错误类中提取错误信息
					if (FlagStr.equals("Fail"))
					{
					    tError = tUWSpecInputUI.mErrors;
					    if (!tError.needDealError())
					    {                          
					    	Content = " 员工特约保存成功! ";
					    	FlagStr = "Succ";
					    }
					    else                                                                           
					    {
						    FlagStr = "Fail";
					    }
					}
				  }
				if (!FlagStr.equals("Fail"))
				{
			   	 	Content = "保存成功!"; 
			    	FlagStr = "Succ";
				}

			    tVData.clear();
			    tVData = tProposalUI.getResult();
			   
			  
			    /////////end add by yaory
			  }  
		  
		  
	      }catch(Exception ex){
		    continue;
	      }
		}
	}
	}
	else
	{
		//删除 
		loggerDebug("UWModifyFloatRateSave","ContNo: "+tContNo);
		loggerDebug("UWModifyFloatRateSave","PolNo: "+tPolNo);
		VData tVData = new VData();
		tVData.add(tG);
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tVData.add(tTransferData);
		// 数据传输
		UWSpecInputUI tUWSpecInputUI   = new UWSpecInputUI();
		if (!tUWSpecInputUI.submitData(tVData,tSpecOperate))
		  {     
        
			int n = tUWSpecInputUI.mErrors.getErrorCount();
			Content = " 员工特约保存失败，原因是: " + tUWSpecInputUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		   }
//			如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr.equals("Fail"))
			{
			    tError = tUWSpecInputUI.mErrors;
			    if (!tError.needDealError())
			    {                          
			    	Content = " 员工特约保存成功! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
				    FlagStr = "Fail";
			    }
			}

			if (!FlagStr.equals("Fail"))
			{
		   	 	Content = "保存成功!"; 
		    	FlagStr = "Succ";
			}
	}

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>



  

