<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
//程序名称：PDRiskDutyRelaSave.jsp
//程序功能：险种关联责任
//创建日期：2009-3-12
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
 //接收信息，并作校验处理。
 //输入参数
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 String payOrGet = request.getParameter("payOrGet");

String riskCode = request.getParameter("riskCodeS");
String busiName="PDRiskDutyRelaBL";

BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//PDRiskDutyRelaBL tPDRiskDutyRelaBL = new PDRiskDutyRelaBL();
 	try
 	{
  	// 准备传输数据 VData
  	VData tVData = new VData();
  	
  	if("Pay".equals(payOrGet)){
  		String tCalCodeType = request.getParameter("DutyPayCalCodeSwitchFlag"); 
  		transferData.setNameAndValue("CalCodeType",tCalCodeType);
  		
  		transferData.setNameAndValue("CalCodeTypeBack",request.getParameter("DutyPayCalCodeBackSwitchFlag"));
  		if(operator.trim().equals("save")){
  	 		PD_LMDutyPaySchema pd_LMDutyPaySchema = new PD_LMDutyPaySchema();
  	  		String dutyCode = request.getParameter("DutyCodeS");
  	  		if(dutyCode == null || dutyCode.trim().equals("")){
  	  			FlagStr = "Fail";
  	  			Content = ""+"保存失败，原因是:责任编码为空"+"";
  	  			%>
  	  			<!--  script language="javascript">
  				 	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
  				</script>-->
  	  			<%
  	  		}
  	  		
  	  		//String maxdutysel = "select code from ldcode where codetype = 'dutygetsel'";
  	  		//ExeSQL ttExeSQL = new ExeSQL();
  	  		//SSRS ttSSRS = ttExeSQL.execSQL(maxdutysel);
  	  		//
  	  		//String maxSel = ttSSRS.GetText(1, 1);
  	  		//int maxSelInt = Integer.parseInt(maxSel);
  	  		//
  	  		//int temp = maxSelInt + 1;
  	  		//String sql = "update ldcode set code = '" + temp + "' where codetype = 'dutygetsel'";
  	  		//ttExeSQL.execUpdateSQL(sql);
  	  		//
  	  		//String tempStr = temp + "";
  	  		//if(tempStr.length() < 3){
  	  		//	int tempStrL = tempStr.length();
  	  		//	for(int i = tempStrL; i < 3; i ++){
  	  		//		tempStr = "0" + tempStr;
  	  		//	}
  	  		//}
  	  		//String tPayPlanCode = request.getParameter("DutyCodeS").substring(0, 3);
  	  		//tPayPlanCode = tPayPlanCode + tempStr;
  	  		//
  	 		  //System.out.println(" +++++++++++++++++++ " + tPayPlanCode);
  	  		
  	  		pd_LMDutyPaySchema.setDutyCode(request.getParameter("DutyCodeS"));
  	  		//pd_LMDutyPaySchema.setPayPlanCode(tPayPlanCode.substring(tPayPlanCode.length() - 6));

  	  		pd_LMDutyPaySchema.setPayPlanName(request.getParameter("payPlanName"));
  	  		pd_LMDutyPaySchema.setZeroFlag(request.getParameter("zeroFlag"));
  	  		pd_LMDutyPaySchema.setCalCode(request.getParameter("payCalCode"));
  	  		pd_LMDutyPaySchema.setNeedAcc(request.getParameter("needAccPay"));
  	  		
  	  		pd_LMDutyPaySchema.setPayEndYear(request.getParameter("PayEndYear"));
  	  		pd_LMDutyPaySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
  	  		pd_LMDutyPaySchema.setPayEndDateCalMode(request.getParameter("PayEndDateCalMode"));
  	  		pd_LMDutyPaySchema.setPayIntv(request.getParameter("PayIntv"));
  	  		pd_LMDutyPaySchema.setUrgePayFlag(request.getParameter("UrgePayFlag"));
  	  		pd_LMDutyPaySchema.setPayAimClass(request.getParameter("PayAimClass"));
  	  		//-------- add by jucy
  	  		//InvestType字段，在界面选择录入但是系统统一默认存储为“1”，应该根据界面录入值存储数据。如果界面没有录入，则后台自动存储为1
  	  		String InvestType = request.getParameter("InvestType");
  	  		if(InvestType!=null && !"".equals(InvestType)){
  	  			pd_LMDutyPaySchema.setInvestType(InvestType);
  	  		}else{
  	  			pd_LMDutyPaySchema.setInvestType("1");
  	  		}
  	  		//-------- end
  	  		pd_LMDutyPaySchema.setPCalCode(request.getParameter("PCalCode"));
  	  		pd_LMDutyPaySchema.setRCalPremCode(request.getParameter("RCalPremCode"));
  	  		//-------- update by jucy
  	  		//RCALPREMFLAG，如果界面没有选择录入，则后台自动存储为N
  	  		String RCalPremFlag = request.getParameter("RCalPremFlag");
  	  		if(RCalPremFlag!=null && !"".equals(RCalPremFlag)){
  	  			pd_LMDutyPaySchema.setRCalPremFlag(RCalPremFlag);
  	  		}else{
  	  			pd_LMDutyPaySchema.setRCalPremFlag("N");
  	  		}
  	  		//-------- end 
  	  		System.out.println("InvestType:"+pd_LMDutyPaySchema.getInvestType());
  	  		//宽限期
  	  		pd_LMDutyPaySchema.setGracePeriod(request.getParameter("GracePeriod"));
  	  		//增加缴费起期等字段
  	  		String ISPayStartYear = request.getParameter("ISPayStartYear");
	  		if(ISPayStartYear!=null && "Y".equals(ISPayStartYear)){
		  	  	pd_LMDutyPaySchema.setPayStartYear(request.getParameter("PayStartYear"));
		  	  	pd_LMDutyPaySchema.setPayStartYearFlag(request.getParameter("PayStartYearFlag"));
		  	  	pd_LMDutyPaySchema.setPayStartDateCalRef(request.getParameter("PayStartDateCalRef"));
		  	  	pd_LMDutyPaySchema.setPayStartDateCalMode(request.getParameter("PayStartDateCalMode"));
	  	  	}
	  	  	
  	  		PD_LMDutyPayRelaSchema pd_LMDutyPayRelaSchema = new PD_LMDutyPayRelaSchema();
  	  		pd_LMDutyPayRelaSchema.setDutyCode(request.getParameter("DutyCodeS"));
  	  		pd_LMDutyPayRelaSchema.setPayPlanName(request.getParameter("payPlanName"));
  	  		
  	  		transferData.setNameAndValue("payCalCode",request.getParameter("payCalCode"));
  	  		transferData.setNameAndValue("payCalCodeBack",request.getParameter("payCalCodeBack"));
  	  		transferData.setNameAndValue("whichAlgo",request.getParameter("payCalType"));
  	  		tVData.add(pd_LMDutyPaySchema);
  	  		tVData.add(pd_LMDutyPayRelaSchema);
  		}else if(operator.trim().equals("del")){
  	     	 String tRadio[] = request.getParameterValues("InpMulline9GridSel");  
          //参数格式=” Inp+MulLine对象名+Sel” 
			for(int index=0; index< tRadio.length;index++)
			{	
				String tGrid2[] = request.getParameterValues("Mulline9Grid2");
				if(tRadio[index].equals("1")){
					String tPayPlanCode = tGrid2[index];
					PD_LMDutyPaySchema tPd_LMDutyPaySchema = new PD_LMDutyPaySchema();
					
					tPd_LMDutyPaySchema.setPayPlanCode(tPayPlanCode);
					tPd_LMDutyPaySchema.setDutyCode(request.getParameter("DutyCodeS"));
					
					PD_LMDutyPayRelaSchema tPd_LMDutyPayRelaSchema = new PD_LMDutyPayRelaSchema();
					tPd_LMDutyPayRelaSchema.setPayPlanCode(tPayPlanCode);
					tPd_LMDutyPayRelaSchema.setDutyCode(request.getParameter("DutyCodeS"));
					tVData.add(tPd_LMDutyPaySchema);
					tVData.add(tPd_LMDutyPayRelaSchema);
				}
			}
  		}else if(operator.trim().equals("update")){
	  		PD_LMDutyPaySchema pd_LMDutyPaySchema = new PD_LMDutyPaySchema();
	  		pd_LMDutyPaySchema.setDutyCode(request.getParameter("DutyCodeS"));
			pd_LMDutyPaySchema.setPayPlanCode(request.getParameter("payPlanCode"));
	  		pd_LMDutyPaySchema.setPayPlanName(request.getParameter("payPlanName"));
	  		pd_LMDutyPaySchema.setZeroFlag(request.getParameter("zeroFlag"));
	  		pd_LMDutyPaySchema.setCalCode(request.getParameter("payCalCode"));
	  		pd_LMDutyPaySchema.setNeedAcc(request.getParameter("needAccPay"));
	  		
	  		pd_LMDutyPaySchema.setPayEndYear(request.getParameter("PayEndYear"));
  	  		pd_LMDutyPaySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
  	  		pd_LMDutyPaySchema.setPayEndDateCalMode(request.getParameter("PayEndDateCalMode"));
  	  		pd_LMDutyPaySchema.setPayIntv(request.getParameter("PayIntv"));
  	  		pd_LMDutyPaySchema.setUrgePayFlag(request.getParameter("UrgePayFlag"));
  	  		pd_LMDutyPaySchema.setPayAimClass(request.getParameter("PayAimClass"));
	  		//宽限期
	  		pd_LMDutyPaySchema.setGracePeriod(request.getParameter("GracePeriod"));
	  		//增加缴费起期等字段
	  		String ISPayStartYear = request.getParameter("ISPayStartYear");
	  		if(ISPayStartYear!=null && "Y".equals(ISPayStartYear)){
		  	  	pd_LMDutyPaySchema.setPayStartYear(request.getParameter("PayStartYear"));
		  	  	pd_LMDutyPaySchema.setPayStartYearFlag(request.getParameter("PayStartYearFlag"));
		  	  	pd_LMDutyPaySchema.setPayStartDateCalRef(request.getParameter("PayStartDateCalRef"));
		  	  	pd_LMDutyPaySchema.setPayStartDateCalMode(request.getParameter("PayStartDateCalMode"));
  	  		}
  	  		//-------- add by jucy
  	  		//InvestType字段，在界面选择录入但是系统统一默认存储为“1”，应该根据界面录入值存储数据。如果界面没有录入，则后台自动存储为1
  	  		String InvestType = request.getParameter("InvestType");
  	  		if(InvestType!=null && !"".equals(InvestType)){
  	  			pd_LMDutyPaySchema.setInvestType(InvestType);
  	  		}else{
  	  			pd_LMDutyPaySchema.setInvestType("1");
  	  		}
  	  		//-------- end
	  		PD_LMDutyPayRelaSchema pd_LMDutyPayRelaSchema = new PD_LMDutyPayRelaSchema();
	  		pd_LMDutyPayRelaSchema.setPayPlanName(request.getParameter("payPlanName"));
	  		pd_LMDutyPaySchema.setPCalCode(request.getParameter("PCalCode"));
  	  		pd_LMDutyPaySchema.setRCalPremCode(request.getParameter("RCalPremCode"));
  	  		//-------- update by jucy
  	  		//RCALPREMFLAG，如果界面没有选择录入，则后台自动存储为N
  	  		String RCalPremFlag = request.getParameter("RCalPremFlag");
  	  		if(RCalPremFlag!=null && !"".equals(RCalPremFlag)){
  	  			pd_LMDutyPaySchema.setRCalPremFlag(RCalPremFlag);
  	  		}else{
  	  			pd_LMDutyPaySchema.setRCalPremFlag("N");
  	  		}
  	  		//-------- end 
	  		transferData.setNameAndValue("payCalCode",request.getParameter("payCalCode"));
	  		transferData.setNameAndValue("payCalCodeBack",request.getParameter("payCalCodeBack"));
	  		transferData.setNameAndValue("whichAlgo",request.getParameter("payCalType"));
	  		tVData.add(pd_LMDutyPaySchema);
	  		tVData.add(pd_LMDutyPayRelaSchema);
	  		
	  	}
 
  	}else if("Get".equals(payOrGet)){
  		String tCalCodeType = request.getParameter("DutyGetCalCodeSwitchFlag"); 
  		transferData.setNameAndValue("CalCodeType",tCalCodeType);
  		transferData.setNameAndValue("CalCodeTypeBack",request.getParameter("DutyGetCalCodeSwitchFlagBack"));
 		if(operator.trim().equals("save")){
			PD_LMDutyGetSchema pd_LMDutyGetSchema = new PD_LMDutyGetSchema();
  	  		String dutyCode = request.getParameter("DutyCodeS");
  	  		if(dutyCode == null || dutyCode.trim().equals("")){
  	  			FlagStr = "Fail";
  	  			Content = ""+"保存失败，原因是:责任编码为空"+"";
  	  			%>
  	  		<!--  <script type="text/javascript">
  				 	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
  				</script>-->	
  	  			<%
  	  		}
  	  		/*
  	  		String maxdutysel = "select code from ldcode where codetype = 'dutypaysel'";
  	  		ExeSQL ttExeSQL = new ExeSQL();
  	  		SSRS ttSSRS = ttExeSQL.execSQL(maxdutysel);
  	  		
  	  		String maxSel = ttSSRS.GetText(1, 1);
  	  		int maxSelInt = Integer.parseInt(maxSel);
  	  		
  	  		int temp = maxSelInt + 1;
  	  		String sql = "update ldcode set code = '" + temp + "' where codetype = 'dutypaysel'";
  	  		ttExeSQL.execUpdateSQL(sql);
  	  		
  	  		String tempStr = temp + "";
  	  		if(tempStr.length() < 3){
  	  			int tempStrL = tempStr.length();
  	  			for(int i = tempStrL; i < 3; i ++){
  	  				tempStr = "0" + tempStr;
  	  			}
  	  		}
  	  		String tPayPlanCode = request.getParameter("DutyCodeS").substring(0, 3);
  	  		tPayPlanCode = tPayPlanCode + tempStr;
  	  		
  	  		System.out.println(" --------------- " + tPayPlanCode);
  	  		*/
  			pd_LMDutyGetSchema.setDutyCode(request.getParameter("DutyCodeS"));
  			//pd_LMDutyGetSchema.setGetDutyCode(tPayPlanCode);
  			pd_LMDutyGetSchema.setGetDutyName(request.getParameter("getDutyName"));
  			pd_LMDutyGetSchema.setGetDutyKind(request.getParameter("getDutyKind"));
  			pd_LMDutyGetSchema.setAddAmntFlag(request.getParameter("AddAmntFlag"));
  			pd_LMDutyGetSchema.setCalCode(request.getParameter("getCalCode"));
  			pd_LMDutyGetSchema.setNeedAcc(request.getParameter("needAccGet"));
  			pd_LMDutyGetSchema.setGetYear(request.getParameter("GetYear"));
  			pd_LMDutyGetSchema.setGetYearFlag(request.getParameter("GetYearFlag1"));
  			pd_LMDutyGetSchema.setStartDateCalRef(request.getParameter("StartDateCalRef"));
  			pd_LMDutyGetSchema.setStartDateCalMode(request.getParameter("StartDateCalMode"));
  			pd_LMDutyGetSchema.setGetEndPeriod(request.getParameter("GetEndPeriod"));
  			pd_LMDutyGetSchema.setGetEndUnit(request.getParameter("GetEndUnit"));
  			pd_LMDutyGetSchema.setEndDateCalRef(request.getParameter("EndDateCalRef"));
  			pd_LMDutyGetSchema.setEndDateCalMode(request.getParameter("EndDateCalMode"));
  			pd_LMDutyGetSchema.setType(request.getParameter("type"));
  			pd_LMDutyGetSchema.setCanGet(request.getParameter("CanGet"));
  			pd_LMDutyGetSchema.setGetIntv(request.getParameter("GetIntv"));
  			pd_LMDutyGetSchema.setNeedCancelAcc(request.getParameter("NeedCancelAcc"));
  			pd_LMDutyGetSchema.setZeroFlag(request.getParameter("zeroGetFlag"));
  			pd_LMDutyGetSchema.setGetType1(request.getParameter("GetType1"));
  			pd_LMDutyGetSchema.setUrgeGetFlag(request.getParameter("UrgeGetFlag"));
  			pd_LMDutyGetSchema.setGetType3(request.getParameter("GetType3"));
  			pd_LMDutyGetSchema.setPCalCode(request.getParameter("PCalCodeAmnt"));
  			pd_LMDutyGetSchema.setRCalAmntCode(request.getParameter("RCalAmntCode"));
  			pd_LMDutyGetSchema.setRCalAmntFlag(request.getParameter("RCalAmntFlag"));
  			PD_LMDutyGetRelaSchema pd_LMDutyGetRelaSchema = new PD_LMDutyGetRelaSchema();
  			pd_LMDutyGetRelaSchema.setDutyCode(request.getParameter("DutyCodeS"));
  			pd_LMDutyGetRelaSchema.setGetDutyName(request.getParameter("getDutyName"));
  			
  			transferData.setNameAndValue("payCalCode",request.getParameter("getCalCode"));
  			transferData.setNameAndValue("payCalCodeBack",request.getParameter("getCalCodeBack"));
  			transferData.setNameAndValue("whichAlgo",request.getParameter("getCalType"));
  			tVData.add(pd_LMDutyGetSchema);
  			tVData.add(pd_LMDutyGetRelaSchema);
 		}else if(operator.trim().equals("del")){
			String tRadio[] = request.getParameterValues("InpMulline11GridSel");  
			//参数格式=” Inp+MulLine对象名+Sel” 
			for(int index=0; index< tRadio.length;index++)
			{	
				String tGrid2[] = request.getParameterValues("Mulline11Grid2");
				if(tRadio[index].equals("1")){
					String tPayPlanCode = tGrid2[index];
					PD_LMDutyGetSchema tPd_LMDutyGetSchema = new PD_LMDutyGetSchema();
					
					tPd_LMDutyGetSchema.setGetDutyCode(tPayPlanCode);
					tPd_LMDutyGetSchema.setDutyCode(request.getParameter("DutyCodeS"));
					
					PD_LMDutyGetRelaSchema tPd_LMDutyGetRelaSchema = new PD_LMDutyGetRelaSchema();
					
					tPd_LMDutyGetRelaSchema.setGetDutyCode(tPayPlanCode);
					tPd_LMDutyGetRelaSchema.setDutyCode(request.getParameter("DutyCodeS"));
					tVData.add(tPd_LMDutyGetSchema);
					tVData.add(tPd_LMDutyGetRelaSchema);
				}
			}
 		}else if(operator.trim().equals("update")){
 			PD_LMDutyGetSchema pd_LMDutyGetSchema = new PD_LMDutyGetSchema();
 			pd_LMDutyGetSchema.setDutyCode(request.getParameter("DutyCodeS"));
 			pd_LMDutyGetSchema.setGetDutyCode(request.getParameter("getDutyCode"));
			pd_LMDutyGetSchema.setGetDutyName(request.getParameter("getDutyName"));
			pd_LMDutyGetSchema.setGetDutyKind(request.getParameter("getDutyKind"));
			pd_LMDutyGetSchema.setAddAmntFlag(request.getParameter("AddAmntFlag"));
			pd_LMDutyGetSchema.setNeedAcc(request.getParameter("needAccGet"));
			pd_LMDutyGetSchema.setType(request.getParameter("type"));
			pd_LMDutyGetSchema.setGetYear(request.getParameter("GetYear"));
			pd_LMDutyGetSchema.setCalCode(request.getParameter("getCalCode"));
  			pd_LMDutyGetSchema.setGetYearFlag(request.getParameter("GetYearFlag1"));
  			pd_LMDutyGetSchema.setStartDateCalRef(request.getParameter("StartDateCalRef"));
  			pd_LMDutyGetSchema.setStartDateCalMode(request.getParameter("StartDateCalMode"));
  			pd_LMDutyGetSchema.setGetEndPeriod(request.getParameter("GetEndPeriod"));
  			pd_LMDutyGetSchema.setGetEndUnit(request.getParameter("GetEndUnit"));
  			pd_LMDutyGetSchema.setEndDateCalRef(request.getParameter("EndDateCalRef"));
  			pd_LMDutyGetSchema.setEndDateCalMode(request.getParameter("EndDateCalMode"));
  			pd_LMDutyGetSchema.setZeroFlag(request.getParameter("zeroGetFlag"));
  			pd_LMDutyGetSchema.setGetType1(request.getParameter("GetType1"));
  			pd_LMDutyGetSchema.setUrgeGetFlag(request.getParameter("UrgeGetFlag"));
  			PD_LMDutyGetRelaSchema pd_LMDutyGetRelaSchema = new PD_LMDutyGetRelaSchema();
 			pd_LMDutyGetRelaSchema.setDutyCode(request.getParameter("DutyCodeS"));
 			pd_LMDutyGetRelaSchema.setGetDutyCode(request.getParameter("getDutyCode"));
			pd_LMDutyGetRelaSchema.setGetDutyName(request.getParameter("getDutyName"));
			pd_LMDutyGetSchema.setGetType3(request.getParameter("GetType3"));
			pd_LMDutyGetSchema.setCanGet(request.getParameter("CanGet"));
			pd_LMDutyGetSchema.setGetIntv(request.getParameter("GetIntv"));
  			pd_LMDutyGetSchema.setNeedCancelAcc(request.getParameter("NeedCancelAcc"));
  			pd_LMDutyGetSchema.setPCalCode(request.getParameter("PCalCodeAmnt"));
  			pd_LMDutyGetSchema.setRCalAmntCode(request.getParameter("RCalAmntCode"));
  			pd_LMDutyGetSchema.setRCalAmntFlag(request.getParameter("RCalAmntFlag"));
			transferData.setNameAndValue("payCalCode",request.getParameter("getCalCode"));
			transferData.setNameAndValue("payCalCodeBack",request.getParameter("getCalCodeBack"));
			transferData.setNameAndValue("whichAlgo",request.getParameter("getCalType"));
			tVData.add(pd_LMDutyGetSchema);
			tVData.add(pd_LMDutyGetRelaSchema);
 		}
  
  	}
  	
  	transferData.setNameAndValue("riskCode",riskCode);
  	transferData.setNameAndValue("payOrGet",payOrGet);
  	transferData.setNameAndValue("getDutyKind",request.getParameter("getDutyKind"));
  	tVData.add(tG);
  	tVData.add(transferData);
  	//tPDRiskDutyRelaBL.submitData(tVData,operator);
  	
	
    //String tDiscountCode = "";
   if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
  	  if("".equals(Content))
      Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"处理成功!"+" ";
    	FlagStr = "Success";
    }
   /*  
  	if(request.getParameter("getDutyKind") != null){ 		
  		if(request.getParameter("getDutyKind").trim().equals("0")){
  			new RiskState().addNode(riskCode,"产品条款定义->算法定义","责任生存给付");
  	  	}	
  	}*/
  
  	//更新calmode流水号
  	//获得当前的流水号
  	/*
  	String updateSql = "select codename from ldcode where codetype = 'calmodesel' ";
  	ExeSQL tlExecSQL = new ExeSQL();
  	
  	if(payOrGet.trim().equals("Pay")){
	  	SSRS tSSRS = tlExecSQL.execSQL(updateSql);
	  	int currentSel = Integer.parseInt(tSSRS.GetText(1, 1));
	  	int temp = currentSel + 1;
	  	updateSql = "update ldcode set codename = '" + temp + "' where codetype = 'calmodesel'";
	  	tlExecSQL.execUpdateSQL(updateSql);
  	}else{
	  	updateSql = "select codename from ldcode where codetype = 'calmodegetsel' ";
	  	SSRS tSSRS = tlExecSQL.execSQL(updateSql);
	  	
	  	int currentGetSel = Integer.parseInt(tSSRS.GetText(1, 1));
	  	int temp = currentGetSel + 1;
	  	updateSql = "update ldcode set codename = '" + temp + "' where codetype = 'calmodegetsel'";
	  	tlExecSQL.execUpdateSQL(updateSql);
  	}
  	*/
 }
 catch(Exception ex)
 {
 if("".equals(Content))
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 /*
 if (FlagStr=="")
 {
  tError = tBusinessDelegate.getErrors();
  if (!tError.needDealError())
  {                          
   Content = " 保存成功! ";
   FlagStr = "Success";
   if("Pay".equals(payOrGet)){
	   RiskState.setState(riskCode, "产品条款定义->缴费责任信息", "1");
   }
   else
   {
	   RiskState.setState(riskCode, "产品条款定义->给付责任信息", "1");
   }
  }
  else                                                                           
  {
   Content = " 保存失败，原因是:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }
*/
 //添加各种预处理
%>
<%=Content%>
<html>
	<script type="text/javascript">
 parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>", "<%=request.getParameter("DutyCodeS")%>");
</script>
</html>

