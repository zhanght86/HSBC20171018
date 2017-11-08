<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDRiskDefiInit.jsp
  //程序功能：产品基础信息录入
  //创建日期：2009-3-12
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：PDRiskDefiSave.jsp
	//程序功能：产品基础信息录入
	//创建日期：2009-3-12
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.proddef.*"%>
<%@page import="java.util.*"%>
 <%@page import="com.sinosoft.service.*" %>  
<%
	//接收信息，并作校验处理。
	//输入参数

	System.out.println("---------into PDRiskDefiSave.jsp-------------------------");

	//PDRiskDefiBL pDRiskDefiBL = new PDRiskDefiBL();

	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String operator = "";
	String riskCode = "";
	TransferData transferData = new TransferData();
	//-------- add by jucy
	//增加riskVer字段录入为当前年。
	String CurrentDate = PubFun.getCurrentDate();
	String riskVer = CurrentDate.substring(0,4);
	//-------- end
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	operator = request.getParameter("operator");

	if (operator.equals("workflow")) {
		transferData.setNameAndValue("RiskCode", request.getParameter("RiskCodem"));
		transferData.setNameAndValue("RequDate", request.getParameter("RequDatem"));
		System.out.println("####"+request.getParameter("RequDatem"));
		System.out.println("####"+request.getParameter("RiskCodem"));
		transferData.setNameAndValue("Operator", tG.Operator);
		transferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
		transferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));
		//MissionProp3
		transferData.setNameAndValue("MissionProp8", "1");
		transferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
		transferData.setNameAndValue("IsBaseInfoDone", "1");

		//ProdDefWorkFlowBL tProdDefWorkFlowBL = new ProdDefWorkFlowBL();
		String busiName="ProdDefWorkFlowBL";
		    
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		try {
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tG);
			tVData.add(transferData);
			boolean flag = tBusinessDelegate.submitData(tVData,operator,busiName);
			System.out.println("工作流跳转处理结果："+flag);
			//tProdDefWorkFlowBL.submitData(tVData, operator);
		} catch (Exception ex) {
			Content = ""+"保存失败，原因是:"+"" + ex.toString();
			FlagStr = "Fail";
		}

		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "") {
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
		Content = " "+"保存成功!"+" ";
		FlagStr = "Success";
			} else {
		Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
		FlagStr = "Fail";
			}
		}
	}

	else {
		String tRiskCode = request.getParameter("riskCodeF");
		try {
			// 准备传输数据 VData
			VData tVData = new VData();

			//是子险情况
			String isSubRisk = request.getParameter("isSubRisk");
			ArrayList mainRiskCode = new ArrayList();
			ArrayList subRiskCode = new ArrayList();
			String riskCodesM[] =request.getParameterValues("Mulline12Grid1");;
			String riskCodesS[] =request.getParameterValues("Mulline13Grid1");;
			if(riskCodesM !=null){
				for (int i = 0; i < riskCodesM.length; i++) {
					mainRiskCode.add(riskCodesM[i]);
				}
				transferData.setNameAndValue("mainRiskCode",mainRiskCode);
			}
			if(riskCodesS !=null){
				for (int i = 0; i < riskCodesS.length; i++) {
					subRiskCode.add(riskCodesS[i]);
				}
				transferData.setNameAndValue("subRiskCode",subRiskCode);
			}		
			
		    if (isSubRisk.equals("S")) {
				transferData.setNameAndValue("isSubRisk", "S");
			}else{
				transferData.setNameAndValue("isSubRisk", "M");				
			}		

			
			riskCode = request.getParameter("riskCodeF");
			transferData.setNameAndValue("riskCode",riskCode);
		
			PD_LMRiskSchema tPD_LMRiskSchema = new PD_LMRiskSchema();
			tPD_LMRiskSchema.setRiskCode(riskCode);
			tPD_LMRiskSchema.setRiskName(request.getParameter("RiskName"));
			tPD_LMRiskSchema.setRiskStatName(request.getParameter("RiskName"));
			tPD_LMRiskSchema.setRiskShortName(request.getParameter("RiskShortName"));
			
			String tChoDutyFlag = "";
			//责任可选问题
			if(request.getParameter("ChoDutyFlag") == null || request.getParameter("ChoDutyFlag").trim().equals("")){
				tChoDutyFlag = "Y";
			}
			tPD_LMRiskSchema.setChoDutyFlag(request.getParameter("ChoDutyFlag"));
			tPD_LMRiskSchema.setInsuAccFlag(request.getParameter("InsuAccFlag"));
			tPD_LMRiskSchema.setGetFlag(request.getParameter("GetFlag"));
			tPD_LMRiskSchema.setRnewFlag(request.getParameter("RnewFlag"));
			tPD_LMRiskSchema.setCPayFlag(request.getParameter("CPayFlag"));
			tPD_LMRiskSchema.setRinsFlag(request.getParameter("RinsFlag"));
			tPD_LMRiskSchema.setRiskShortName(request.getParameter("RiskShortName"));
			tPD_LMRiskSchema.setRiskEnName(request.getParameter("RiskEnName"));
			tPD_LMRiskSchema.setRiskEnShortName(request.getParameter("RiskEnShortName"));
			tPD_LMRiskSchema.setOrigRiskCode(request.getParameter("OrigRiskCode"));
			//-------- add by jucy
			//加入险种版本 
			tPD_LMRiskSchema.setRiskVer(riskVer);
			//-------- end
			tPD_LMRiskSchema.setRiskVer(riskVer);
			PD_LMRiskAppSchema tPD_LMRiskAppSchema = new PD_LMRiskAppSchema();
			tPD_LMRiskAppSchema.setRiskCode(riskCode);
			String kindCode = request.getParameter("riskType");
			tPD_LMRiskAppSchema.setKindCode(kindCode);
			tPD_LMRiskAppSchema.setRiskFlag(request.getParameter("RiskFlag"));
			if("A".equals(kindCode)){
				tPD_LMRiskAppSchema.setRiskType("A");
				tPD_LMRiskAppSchema.setRiskType4("6");
			}else if("L".equals(kindCode)){
				tPD_LMRiskAppSchema.setRiskType("L");
				tPD_LMRiskAppSchema.setRiskType4("7");
			}else if("U".equals(kindCode)){
				tPD_LMRiskAppSchema.setRiskType("U");
				tPD_LMRiskAppSchema.setRiskType4("7");
			}else{
				tPD_LMRiskAppSchema.setRiskType("H");
				if("S".equals(kindCode)){
					tPD_LMRiskAppSchema.setRiskType4("5");
				}else{
					tPD_LMRiskAppSchema.setRiskType4("7");
				}
			}
			tPD_LMRiskAppSchema.setRiskProp(request.getParameter("riskProp"));
			tPD_LMRiskAppSchema.setRiskType1(request.getParameter("RISKTYPE1"));
			tPD_LMRiskAppSchema.setRiskTypeDetail(request.getParameter("RiskTypeDetail"));			
			String riskPeriod = request.getParameter("RiskPeriod");
			tPD_LMRiskAppSchema.setRiskPeriod(riskPeriod);
			/*if("L".equals(riskPeriod)){
				tPD_LMRiskAppSchema.setRiskType5("2");
			}else{
				tPD_LMRiskAppSchema.setRiskType5("1");
			}*/
			
			
			tPD_LMRiskAppSchema.setPolType(request.getParameter("PolType"));
			tPD_LMRiskAppSchema.setV("riskName",request.getParameter("RiskName"));
			//tPD_LMRiskAppSchema.setStartDate("1900-1-1");
			//tongmeng 2011-02-09 modify
			tPD_LMRiskAppSchema.setBonusFlag(request.getParameter("BonusFlag"));
			tPD_LMRiskAppSchema.setListFlag(request.getParameter("ListFlag"));
			tPD_LMRiskAppSchema.setSignDateCalMode(request.getParameter("SignDateCalMode"));
			tPD_LMRiskAppSchema.setStartDate(request.getParameter("StartDate"));
			tPD_LMRiskAppSchema.setMngCom(request.getParameter("MngCom"));
			tPD_LMRiskAppSchema.setBonusMode(request.getParameter("BonusMode"));
			tPD_LMRiskAppSchema.setMinAppntAge(request.getParameter("MinAppntAge"));
			tPD_LMRiskAppSchema.setMaxAppntAge(request.getParameter("MaxAppntAge"));
			tPD_LMRiskAppSchema.setMaxInsuredAge(request.getParameter("MaxInsuredAge"));
			tPD_LMRiskAppSchema.setMinInsuredAge(request.getParameter("MinInsuredAge"));
			//SaleFlag
			tPD_LMRiskAppSchema.setSaleFlag(request.getParameter("SaleFlag"));
			tPD_LMRiskAppSchema.setAutoPayType(request.getParameter("AutoPayType"));
			tPD_LMRiskAppSchema.setAutoETIFlag(request.getParameter("AutoETIFlag"));
			tPD_LMRiskAppSchema.setAutoETIType(request.getParameter("AutoETIType"));
			tPD_LMRiskAppSchema.setAutoCTFlag(request.getParameter("AutoCTFlag"));
			tPD_LMRiskAppSchema.setNonParFlag(request.getParameter("NonParFlag"));
			tPD_LMRiskAppSchema.setBackDateFlag(request.getParameter("BackDateFlag"));			
			tPD_LMRiskAppSchema.setInsuredFlag(request.getParameter("InsuredFlag"));
			tPD_LMRiskAppSchema.setEndDate(request.getParameter("EndDate"));
			tPD_LMRiskAppSchema.setSubRiskFlag(request.getParameter("SubRiskFlag"));			
			tPD_LMRiskAppSchema.setRiskType2(request.getParameter("RiskType2"));
			tPD_LMRiskAppSchema.setRiskType3(request.getParameter("RiskType3"));
			tPD_LMRiskAppSchema.setRiskType4(request.getParameter("RiskType4"));
			tPD_LMRiskAppSchema.setRiskType5(request.getParameter("RiskType5"));
			tPD_LMRiskAppSchema.setRiskType7(request.getParameter("RiskType7"));
			tPD_LMRiskAppSchema.setRiskType9(request.getParameter("RiskType9"));
			tPD_LMRiskAppSchema.setCancleForeGetSpecFlag(request.getParameter("CancleForeGetSpecFlag"));
			tPD_LMRiskAppSchema.setRiskTypeAcc(request.getParameter("RiskTypeAcc"));
			tPD_LMRiskAppSchema.setNeedGetPolDate(request.getParameter("NeedGetPolDate"));
			tPD_LMRiskAppSchema.setSpecFlag(request.getParameter("SpecFlag"));			
			tPD_LMRiskAppSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
			tPD_LMRiskAppSchema.setInvestFlag(request.getParameter("InvestFlag"));
			tPD_LMRiskAppSchema.setCutAmntStopPay(request.getParameter("CutAmntStopPay"));			
			tPD_LMRiskAppSchema.setLoanFlag(request.getParameter("LoanFlag"));
			tPD_LMRiskAppSchema.setMortagageFlag(request.getParameter("MortagageFlag"));
			//-------- add by jucy
			//加入险种版本 加入计算精确位 加入计算取舍方法 加入暂缴费标记
			tPD_LMRiskAppSchema.setRiskVer(riskVer);
			tPD_LMRiskAppSchema.setCalDigital("2");
			tPD_LMRiskAppSchema.setCalChoMode("1");
			tPD_LMRiskAppSchema.setTempPayFlag("N");
			//-------- end
			if(!"del".equals(operator)){
				String[] payintvArray = request.getParameterValues("payintv");
				if(payintvArray!=null)
				{
				for(int i = 0; i < payintvArray.length; i ++){
					PD_LMRiskPayIntvSchema tPD_LMRiskPayIntvSchema = new PD_LMRiskPayIntvSchema();
					tPD_LMRiskPayIntvSchema.setPayIntv(payintvArray[i]);
					tPD_LMRiskPayIntvSchema.setRiskCode(riskCode);
					transferData.setNameAndValue("PD_LMRiskPayIntvSchema" + i,tPD_LMRiskPayIntvSchema);
				}
			
				transferData.setNameAndValue("PD_LMRiskPayIntvSchemaLength", payintvArray.length);
				}
			}
			
			tVData.add(tPD_LMRiskSchema);
			tVData.add(tPD_LMRiskAppSchema);
			tVData.add(tG);
			tVData.add(transferData);
			String busiName="PDRiskDefiBL";
		    
		    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		    //String tDiscountCode = "";
		    if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
		  	  VData rVData = tBusinessDelegate.getResult();
		      Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
		    	FlagStr = "Fail";
		    }
		    else {
		      Content = " "+"处理成功!"+" ";
		    	FlagStr = "Success";
		    }
		   
		   }
		   catch(Exception ex)
		   {
		    Content = ""+"保存失败，原因是:"+"" + ex.toString();
		    FlagStr = "Fail";
		   }

			//pDRiskDefiBL.submitData(tVData, operator);
		/*} catch (Exception ex) {
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}

		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "") {
			tError = pDRiskDefiBL.mErrors;
			if (!tError.needDealError()) {
				Content = " 保存成功! ";
				FlagStr = "Success";
		 		RiskState.setState(tRiskCode, "产品条款定义->基本信息", "1");
			} else {
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}*/

	}
	//添加各种预处理
%>
<html>
	<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*"%>
<script type="text/javascript">
var tResourceName = "productdef.PDRiskDefiInputSql";
function initForm() 
{
	try{
	    isshowbutton();
		document.getElementById("RiskCode").value = "<%=request.getParameter("riskcode")%>";

		document.getElementById("RequDate").value = "<%=request.getParameter("requdate")%>";
		document.getElementById("MissionID").value = "<%=request.getParameter("missionid")%>";
    	document.getElementById("SubMissionID").value = "<%=request.getParameter("submissionid")%>";
		document.getElementById("ActivityID").value = "<%=request.getParameter("activityid")%>";
		fmF.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";	
		
		if(fmF.all("IsReadOnly").value == '1'){
			document.getElementById('productCopy').disabled = true;
		}
		
		//updateDisplayState();

		initMulline9Grid();
		//queryMulline9Grid();
		
		
		initMulline11Grid();
	//	queryMulline11Grid();
	
		
		initMulline12Grid(); 
		
		initMulline13Grid();
	//	fmF.all("riskType").value = '';
		initMulline14Grid();

		//queryMulline12Grid();

		// fm.btnRiskAccountDefi.style.display = "none";
		// fm.btnIssueQuery.disabled = false;
		// updateDisplay();

		initRisk();
		queryMulline14Grid();
		initRadio();
		//initCheckBox(); 
		initRiskPay();
		queryRiskPay();
		
		//处理红利处理方式和医疗险分类
		if(document.getElementById('riskType').value=='L')
		{
			document.getElementById('MedicareDefi').style.display = "none";
		}
		else
		{
			document.getElementById('MedicareDefi').style.display = "";
		}
			if(document.getElementById('BonusFlag').value=='0')
		{
			document.getElementById('BonusDefi').style.display = "none";
		}
		else
		{
			document.getElementById('BonusDefi').style.display = "";
		}
	
	}catch(ex){
		myAlert("initForm()方法出现错误");
	}
}

function updateDisplay()
{
	var paras = new Array();
	paras[0] = new Array();
	paras[0][0] = "RiskCode"; // sql语句中"@@"中间的参数名称
	paras[0][1] = fm.all("RiskCode").value; // 参数值
			
	// pageNo:页面的编号，来唯一确定该页面; eleType:要校验的元素的类型，可为空; paras:sql语句中参数名称和值的二维数组
	customDisplay(fm.PageNo.value, "button", paras);
}

function updateDisplayState()
{
 // rowCount:显示的字段数量
 //var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 
 var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql16";
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.all("tableName").value);//指定传入的参数
	var sql = mySql1.getString();
	
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:下拉项对应的selectcode的数组
 //sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 
 	sqlid1 = "PDRiskDefiInputSql17";
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.all("tableName").value);//指定传入的参数
	sql = mySql1.getString();
	
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 
 
 
 for(var i = 0; i < rowCount; i++)
 {

     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("riskcode").value,null,"readonly"); 		 
	 }
	 else
	 {
	 	 var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where RiskCode = '"+fm.RiskCode.value +"'";
   	 var tContent = easyExecSql(tDefaultValueSQL);
   	 
   	 var cData = null;
   	 if(tContent!=null&&tContent[0][0]!="null")
   	 {
   	 	 cData = tContent[0][0];
   	 }
   	 
     Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
    }
  }
}


function afterRadioSelect(){
	//-------- add by jucy3
	//调用新增方法刷新缴费属性定义输入框。
	//增加原因：如果选中险种责任没有定义缴费信息，清空此区域输入框。
	initPayPlan();
	//-------- end
	var selNo = Mulline9Grid.getSelNo()-1;
	var dutyCode = Mulline9Grid.getRowColData(selNo,4);
	var payplancode = Mulline9Grid.getRowColData(selNo,2);
	/*var sql="select payplancode,payplanname,c.zeroflag, c.calcode,c.cntercalcode,c.othcalcode,c.GracePeriod,c.PayEndYear,c.PayIntv,";
	sql += "c.paystartyear,c.paystartyearflag,(select codename from ldcode where codetype='pd_paystartyearflag' and code=c.paystartyearflag),";
	sql += "c.paystartdatecalref,(select codename from ldcode where codetype='pd_paystdatecalref' and code=c.paystartdatecalref),";
	sql += "c.paystartdatecalmode,(select codename from ldcode where codetype='pd_paystdatecalmode' and code=c.paystartdatecalmode),";
	sql += "c.payendyearflag,(select codename from ldcode where codetype='pd_payendyearflag' and code=c.payendyearflag),";
	sql += "c.payenddatecalmode,(select codename from ldcode where codetype='pd_payenddatecalmode' and code=c.payenddatecalmode),";
	sql += "c.urgepayflag,(select codename from ldcode where codetype='pd_urgepayflag' and code=c.urgepayflag),";
	sql += "c.PayAimClass,(select codename from ldcode where codetype='pd_payaimclass' and code=c.PayAimClass),";
	sql += "c.needacc,(select codename from ldcode where codetype='pd_needacc' and code=c.needacc) from pd_lmduty a,pd_lmdutypay c,pd_lmriskduty b "
	sql +=  "where a.dutycode=b.dutycode and b.dutycode = c.dutycode and a.dutycode = '"+dutyCode+"' and c.payplancode = '"+payplancode+"' ";
*/
	var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql18";
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(dutyCode);//指定传入的参数
		mySql1.addSubPara(payplancode);//指定传入的参数
	var sql = mySql1.getString();
	var result = easyExecSql(sql,1,1,1);
	//alert('stop');
	
	//-----add by jucy3---
	//添加校验：如果没有定义险种责任的缴费责任，重新初始化MulLine
	if(result==""||result==null){
		initMulline9Grid();
	}
	//-----end
	
	
	if(result){		
	 	fm.all('payPlanCode').value=result[0][0];
	 	fm.all('payPlanName').value=result[0][1];
	 	fm.all('zeroFlag').value=result[0][2];
	 	fm.all('payCalCode').value = result[0][3];
	 	
	 	fm.all('GracePeriod').value = result[0][6];
	 	fm.all('PayEndYear').value = result[0][7];
	 	fm.all('PayIntv').value = result[0][8];

	 	fm.all('PayStartYear').value = result[0][9];
		fm.all('PayStartYearFlag').value = result[0][10];
		fm.all('PayStartDateCalRef').value = result[0][12];
		fm.all('PayStartDateCalMode').value = result[0][14];
		fm.all('PayStartFlagName').value = result[0][11];
		fm.all('PaySDCalRefName').value = result[0][13];
		fm.all('PaySDCalModeName').value = result[0][15];
		fm.all('PayEndYearFlag').value = result[0][16];
		fm.all('PayEndYearFlagS').value = result[0][17];
		fm.all('PayEndDateCalMode').value = result[0][18];
		fm.all('PayEndDateCalModeS').value = result[0][19];
		fm.all('UrgePayFlag').value = result[0][20];
		fm.all('UrgePayFlagS').value = result[0][21];
		fm.all('PayAimClass').value = result[0][22];
		fm.all('PayAimClassS').value = result[0][23];
		fm.all('needAccPay').value = result[0][24];
		fm.all('needAccPayS').value = result[0][25];
		fm.all('InvestType').value = result[0][26];
		fm.all('PCalCode').value = result[0][27];
		fm.all('RCalPremFlag').value = result[0][28];
		fm.all('RCalPremCode').value = result[0][29];
		/*
	 	sql =  "select trim(codename) from ldcode where trim(code)='"+fm.all('zeroFlag').value+"' and codetype='pd_yesno'";
	 	var resultS =  easyExecSql(sql,1,1,1);
	 	if(resultS){
	 		fm.all('ZeroFlagS').value =resultS[0][0];
	 	}*/
	 	showOneCodeName('pd_yesno', 'zeroFlag', 'ZeroFlagS')
	 	showOneCodeName('pd_yesno', 'RCalPremFlag', 'RCalPremFlagS')
	 	showOneCodeName('pd_investtypes', 'InvestType', 'InvestTypeS')
	 	if (result[0][3] != ""){
			fm.all('payCalType').value= '1';
	 		fm.payCalTypeS.value='保额算保费';
	 		fm.all('payCalCode').value = result[0][3];
	 		fm.all('payCalCodeBack').value = result[0][4];
	 	}
	 	else if(result[0][4] != "" ){
	 		fm.all('payCalType').value= '2';
	 		fm.payCalTypeS.value='保费算保费';
	 		fm.all('payCalCode').value = result[0][4];
	 		fm.all('payCalCodeBack').value = result[0][3];
	 	}else if(result[0][5] != "" ){
	 		fm.all('payCalType').value='3';
	 		fm.payCalTypeS.value='其他算保费';
	 		fm.all('payCalCode').value = result[0][5];
	 		
	 		fm.all('payCalCodeBack').value = result[0][4];
	 	}	
 		
 		
 		initDutyPayCalCodeMain(document.getElementById("RiskCode").value,fm.all('payCalCode').value);
 		
 		
 		initDutyPayCalCodeBackMain(document.getElementById("RiskCode").value,fm.all('payCalCodeBack').value);
 		
 		if(result[0][9] !=null && result[0][9]>"0"){
 			fm.all("ISPayStartYear")[0].checked = true;
 			showPayStartCtrl();
 		}
 		else
 		{
 			fm.all("ISPayStartYear")[1].checked = true;
 			closePayStartCtrl();
 		}
	} 
	

}
//--------add by jucy3
//增加点击单选框，同步刷新用一层级下所有输入框方法
//1.缴费属性定义区域
function initPayPlan(){
	//fm.all('DutyCodeS').value="";
	fm.all('payPlanName').value="";
	fm.all('zeroFlag').value="";
	fm.all('ZeroFlagS').value="";
	//fm.all('payPlanCode').value="";
	fm.all('GracePeriod').value="";
	fm.all('needAccPay').value="";
	fm.all('needAccPayS').value="";
	fm.all('payCalType').value="";
	fm.all('payCalTypeS').value="";
	fm.all('payCalCode').value="";
	
	fm.all('ISPayStartYear')[0].checked = false;
	fm.all('ISPayStartYear')[1].checked = false;
	fm.all('isAccRela')[0].checked = false;
	fm.all('isAccRela')[1].checked = false;
	
	fm.all('PayEndYear').value="";
	fm.all('PayEndYearFlag').value="";
	fm.all('PayEndYearFlagS').value="";
	fm.all('PayEndDateCalMode').value="";
	fm.all('PayEndDateCalModeS').value="";
	fm.all('PayIntv').value="";
	fm.all('UrgePayFlag').value="";
	fm.all('UrgePayFlagS').value="";
	fm.all('PayAimClass').value="";
	fm.all('PayAimClassS').value="";
	fm.all('InvestType').value="";
	fm.all('InvestTypeS').value="";
	fm.all('RCalPremFlag').value="";
	fm.all('RCalPremFlagS').value="";
	fm.all('PayStartYear').value="";
	fm.all('PayStartYearFlag').value="";
	fm.all('PayStartFlagName').value="";
	fm.all('PayStartDateCalRef').value="";
	fm.all('PaySDCalRefName').value="";
	fm.all('PayStartDateCalMode').value="";
	fm.all('PaySDCalModeName').value="";
	
}
//2.给付属性定义区域
function initGetDuty(){

	//fm.all('getDutyCode').value="";
	fm.all('getDutyName').value="";
	fm.all('AddAmntFlag').value="";
	fm.all('AddAmntFlagS').value="";
	fm.all('needAccGet').value="";
	fm.all('needAccGetS').value="";
	fm.all('type').value="";
	fm.all('typeS').value="";
	fm.all('GetType1').value="";
	fm.all('GetType1S').value="";
	fm.all('GetType3').value="";
	fm.all('GetType3S').value="";
	fm.all('getCalType').value="";
	fm.all('getcaltypeS').value="";
	fm.all('getCalCode').value="";
	fm.all('zeroGetFlag').value="";
	fm.all('zeroGetFlagS').value="";
	fm.all('UrgeGetFlag').value="";
	fm.all('UrgeGetFlagS').value="";
	fm.all('NeedCancelAcc').value="";
	fm.all('NeedCancelAccS').value="";
	fm.all('CanGet').value="";
	fm.all('CanGetS').value="";
	fm.all('GetIntv').value="";
	fm.all('GetYear').value="";
	fm.all('GetYearFlag1').value="";
	fm.all('GetYearFlag1S').value="";
	fm.all('StartDateCalRef').value="";
	fm.all('StartDateCalRefS').value="";
	fm.all('StartDateCalMode').value="";
	fm.all('StartDateCalModeS').value="";
	fm.all('GetEndPeriod').value="";
	fm.all('GetEndUnit').value="";
	fm.all('GetEndUnitS').value="";
	fm.all('EndDateCalRef').value="";
	fm.all('EndDateCalRefS').value="";
	fm.all('EndDateCalMode').value="";
	fm.all('EndDateCalModeS').value="";
	fm.all('RCalAmntFlag').value="";
	fm.all('RCalAmntFlagS').value="";
	
	fm.all('IsGetYearFlag')[0].checked = false;
	fm.all('IsGetYearFlag')[1].checked = false;
	fm.all('IsGetEndPeriodFlag')[0].checked = false;
	fm.all('IsGetEndPeriodFlag')[1].checked = false;
	document.getElementById('getInvName').style.display = "none";
	document.getElementById('getInvValue').style.display = "none";
	document.getElementById('GetYearPartId').style.display = "none";
	document.getElementById('GetEndPeriodPartId').style.display = "none";
	
}
//--------end

function afterRadioSelect2(){

	//-------- add by jucy3
	//调用新增方法刷新缴费属性定义输入框。
	//增加原因：如果选中险种责任没有定义缴费信息，清空此区域输入框。
	initGetDuty();
	//-------- end
	
	var selNo = Mulline11Grid.getSelNo()-1;
	var dutyCode = Mulline11Grid.getRowColData(selNo,4);
	var getplancode = Mulline11Grid.getRowColData(selNo,2);
	/*var sql = "select c.AddAmntFlag,getDutyCode,getDutyname,c.type,c.calcode,c.cntercalcode,c.othcalcode,c.needacc,(select codename from ldcode where codetype='pd_needacc' and code=c.needacc),c.getyear,c.getendperiod,"
	    sql+="c.GetYearFlag,(select codename from ldcode where codetype='insuyearflag' and code=c.GetYearFlag),"
	    sql+="c.StartDateCalRef,(select codename from ldcode where codetype='startdatecalref' and code=c.StartDateCalRef),"
	    sql+="c.StartDateCalMode,(select codename from ldcode where codetype='startdatecalmode' and code=c.StartDateCalMode),"
	    sql+="c.GetEndUnit,(select codename from ldcode where codetype='insuyearflag' and code=c.GetEndUnit),"
	    sql+="c.EndDateCalRef,(select codename from ldcode where codetype='startdatecalref' and code=c.EndDateCalRef),"
	    sql+="c.EndDateCalMode,(select codename from ldcode where codetype='enddatecalmode' and code=c.EndDateCalMode)"
	    sql +=" from pd_lmduty a, pd_lmdutyget c, pd_lmriskduty b where a.dutycode = b.dutycode and b.dutycode = c.dutycode and a.dutycode = '"+dutyCode+"' and c.getdutycode = '"+getplancode+"'";
  */
   var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql19";
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(dutyCode);//指定传入的参数
		mySql1.addSubPara(getplancode);//指定传入的参数
	var sql = mySql1.getString();
  var result = easyExecSql(sql,1,1,1);
 

if(result){
 	fm.all('AddAmntFlag').value=result[0][0];
 /*	 sql =  "select trim(codename) from ldcode where trim(code)='"+fm.all('AddAmntFlag').value+"' and codetype='pd_yesno'";
 	 var resultF  = easyExecSql(sql,1,1,1);
 	if(resultF){
 			fm.all('AddAmntFlagS').value=resultF[0][0];
 	}*/
 		showOneCodeName('pd_yesno', 'AddAmntFlag', 'AddAmntFlagS');
 	
 	fm.all('getDutyCode').value=result[0][1];
 	fm.all('getDutyname').value=result[0][2];
 	fm.all('type').value=result[0][3];
/* sql =  "select trim(codename) from ldcode where trim(code)='"+fm.all('type').value+"' and codetype='pd_dutygettype'";
 var resultS = easyExecSql(sql,1,1,1);
 		 if(resultS){
 		fm.all('typeS').value =resultS[0][0];
 	}*/
 	showOneCodeName('pd_dutygettype', 'type', 'typeS');
 		
 		if (result[0][4] != ""){
			fm.all('getCalType').value= '1';
			fm.all('getCalCode').value=result[0][4];
			fm.all('getCalCodeBack').value=result[0][5];
			fm.getcaltypeS.value='保费算保额';
 		}
 		else if(result[0][5] !=""){
 			fm.all('getCalType').value= '2';
 			fm.all('getCalCode').value=result[0][5];
			fm.all('getCalCodeBack').value=result[0][4];
			fm.getcaltypeS.value='保额算保额';
 		}else if(result[0][6] !=""){
 			//--------add by jucy5 
 			fm.all('getCalType').value= '3';
 			//--------end
 			fm.all('getCalCode').value=result[0][6];
			fm.all('getCalCodeBack').value=result[0][5];
			fm.getcaltypeS.value='其他算保额';
 		}	
 		fm.all('needAccGet').value=result[0][7];
 		fm.all('needAccGetS').value=result[0][8];
 		//根据给付类型隐藏/显示生存给付要素的录入按钮
 		/*
 		if(result[0][3]=="0"){
 			document.getElementById('divGetAlive').style.display = "";
 		}else{
 			document.getElementById('divGetAlive').style.display = "none";
 		}*/
 			if(result[0][3]=='0' ){
 			document.getElementById('divGetAlive').style.display = "";
 			document.getElementById('getInvName').style.display = "";
 			document.getElementById('getInvValue').style.display = "";
 		}else
 		{
 			document.getElementById('divGetAlive').style.display = "none";
 			document.getElementById('getInvName').style.display = "none";
 			document.getElementById('getInvValue').style.display = "none";
 		}
 		
 		
 		initDutyGetCalCodeMain(document.getElementById("RiskCode").value,fm.all('getCalCode').value);
		initDutyGetCalCodeMainBack(document.getElementById("RiskCode").value,fm.all('getCalCodeBack').value);
		//判断起领期间等等
		
		//document.getElementById("GetYear").value=result[0][9];
		fm.all('GetYear').value=result[0][9];
		if(result[0][9]!=null&&result[0][9]>0)
		{
			//alert(result[0][9]);
			document.getElementById('GetYearPartId').style.display = "";
			fm.all("IsGetYearFlag")[0].checked = true;
			
		}
		else
		{
			document.getElementById('GetYearPartId').style.display = "none";
			fm.all("IsGetYearFlag")[1].checked = true;
		}
		
		document.getElementById("GetYearFlag1").value=result[0][11];
		document.getElementById("StartDateCalRef").value=result[0][13];
		document.getElementById("StartDateCalMode").value=result[0][15];				
		document.getElementById("GetEndPeriod").value=result[0][10];
		
		if(result[0][10]!=null&&result[0][10]>0)
		{
			document.getElementById('GetEndPeriodPartId').style.display = "";
			fm.all("IsGetEndPeriodFlag")[0].checked = true;
			
		}
		else
		{
			document.getElementById('GetEndPeriodPartId').style.display = "none";
			fm.all("IsGetEndPeriodFlag")[1].checked = true;
		}
		
    	document.getElementById("GetEndUnit").value=result[0][17];
		document.getElementById("EndDateCalRef").value=result[0][19];
		document.getElementById("EndDateCalMode").value=result[0][21];
		
		document.getElementById("GetYearFlagS").value=result[0][12];
		document.getElementById("StartDateCalRefS").value=result[0][14];
		document.getElementById("StartDateCalModeS").value=result[0][16];
		
		document.getElementById("GetEndUnitS").value=result[0][18];
		document.getElementById("EndDateCalRefS").value=result[0][20];
		document.getElementById("EndDateCalModeS").value=result[0][22];
		
		document.getElementById("zeroGetFlag").value=result[0][23];
		document.getElementById("UrgeGetFlag").value=result[0][24];
		document.getElementById("GetType1S").value = "";
		document.getElementById("GetType1").value=result[0][25];
		document.getElementById("GetType3").value=result[0][26];
		document.getElementById("GetIntv").value=result[0][27];
		document.getElementById("CanGet").value=result[0][28];
		document.getElementById("NeedCancelAcc").value=result[0][29];
		document.getElementById("PCalCodeAmnt").value=result[0][30];
		document.getElementById("RCalAmntCode").value=result[0][32];
		document.getElementById("RCalAmntFlag").value=result[0][31];
		document.getElementById("zeroGetFlagS").value = "";
		document.getElementById("UrgeGetFlagS").value = "";
		showOneCodeName('pd_yesno', 'zeroGetFlag', 'zeroGetFlagS');
		showOneCodeName('pd_yesno', 'RCalAmntFlag', 'RCalAmntFlagS');
		showOneCodeName('pd_urgegetflag', 'UrgeGetFlag', 'UrgeGetFlagS');
		showOneCodeName('pd_gettype1', 'GetType1', 'GetType1S');
		showOneCodeName('pd_gettype3', 'GetType3', 'GetType3S');
		showOneCodeName('pd_canget', 'CanGet', 'CanGetS');
		showOneCodeName('pd_needcancelacc', 'NeedCancelAcc', 'NeedCancelAccS');
		showOneCodeName('pd_paystartyearflag', 'GetYearFlag1', 'GetYearFlag1S');
		/*  
		''showOneCodeName('pd_needacc', 'needAccGet', 'needAccGetS');
		showOneCodeName('insuyearflag', 'GetYearFlag', 'GetYearFlagS');
		showOneCodeName('startdatecalref', 'StartDateCalRef', 'StartDateCalRefS');
		showOneCodeName('startdatecalmode', 'StartDateCalMode', 'StartDateCalModeS');
		showOneCodeName('insuyearflag', 'GetEndUnit', 'GetEndUnitS');
		showOneCodeName('startdatecalref', 'EndDateCalRef', 'EndDateCalRefS');
		showOneCodeName('enddatecalmode', 'EndDateCalMode', 'EndDateCalModeS');
		*/
	}
}

function afterRadioM14Select(){
	//-------- add by jucy
	//选择不同的险种责任，清空此层级下的所有对应的区域
	initPayPlan();
	initGetDuty();
	//-------- end
	
	document.getElementById("DutyShowPart").style.display = "";
	
	var selNo = Mulline14Grid.getSelNo()-1;
	var dutyCode = Mulline14Grid.getRowColData(selNo,1);
	var dutyName = Mulline14Grid.getRowColData(selNo,2);
	fmA.all('DutyCode').value=dutyCode;
	fmA.all('DutyName').value=dutyName;
	
	fm.all('DutyCodeS').value = dutyCode;
	
	queryMulline11Grid();
	queryMulline9Grid();//险种缴费
	
	//var sqlStr = "select ChoFlag from pd_lmriskduty where dutycode = '" + dutyCode + "' and riskcode = '" + document.getElementById("RiskCode").value + "'";
   
	var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql20";
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(dutyCode);//指定传入的参数
		mySql1.addSubPara(document.getElementById("RiskCode").value);//指定传入的参数
	var sqlStr = mySql1.getString();
	
   var result = easyExecSql(sqlStr,1,1,1);
  // alert('1');
	if(result != null){
		fmA.all('choFlag').value=result[0][0];
		if(fmA.all('choFlag').value == ''){
			myAlert(fmA.all('choFlag').value);
		}else if(fmA.all('choFlag').value == 'M'){
			fmA.all('choFlagS').value= '必选';
		}else if(fmA.all('choFlag').value == 'C'){
			fmA.all('choFlagS').value= '可选';
		}
	}
		
		//var sqlStr1 = "select GetYear,BasicCalCode,VPU,InsuYear,PayEndDateCalRef,PayEndDateCalMode,PayEndYearRela,GetYearFlag,GetYearRela,CalMode,AmntFlag,InsuYearFlag,InsuYearRela from pd_lmduty where dutycode = '" + dutyCode + "'";
	  var mySql2=new SqlClass();
	var sqlid2 = "PDRiskDefiInputSql21";
	mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(dutyCode);//指定传入的参数
	var sqlStr1 = mySql2.getString();
	var result1 = easyExecSql(sqlStr1,1,1,1);
	var duty = ['GetYear','BasicCalCode','VPU','InsuYear','PayEndDateCalRef','PayEndDateCalMode','PayEndYearRela','GetYearFlag','GetYearRela','CalMode','AmntFlag','InsuYearFlag','InsuYearRela'];
	var codetype = ['GetYear','BasicCalCode','VPU','InsuYear','pd_payenddatecalref','pd_payenddatecalmode','payendyearrela','getyearflag','getyearrela','pd_calmode','pd_amntflag','insuyearflag', 'insuyearrela'];
	var resultS ;
	if(result1){
		fmA.all('GetYear').value=result1[0][0];
		fmA.all('BasicCalCode').value=result1[0][1];
		fmA.all('VPU').value=result1[0][2];
		fmA.all('InsuYear').value=result1[0][3];
		for( var i=4;i<duty.length;i++){
			if(result1[0][i]=='')continue;
			document.getElementById(duty[i]).value= result1[0][i];
			//  var sql = "select trim(codename) from ldcode where trim(code)='"+result1[0][i]+"' and codetype='"+codetype[i]+"'";
			var mySql3=new SqlClass();
			var sqlid3 = "PDRiskDefiInputSql22";
			mySql3.setResourceName(tResourceName); //指定使用的properties文件名
			mySql3.setSqlId(sqlid3);//指定使用的Sql的id
			mySql3.addSubPara(result1[0][i]);//指定传入的参数
			mySql3.addSubPara(codetype[i]);//指定传入的参数
			var sql = mySql3.getString();
			resultS = easyExecSql(sql,1,1,1);
			if(resultS){
				document.getElementById(duty[i]+'S').value= resultS[0][0];
			}			   
		}
		
		//-------- add by jucy
		if(result1[0][13] == "Y"){
			fmA.all('PCalMode').value=result1[0][13];
			fmA.all('PCalModeS').value='需要进行互算';
		}else if(result1[0][13] == "N"){
			fmA.all('PCalMode').value=result1[0][13];
			fmA.all('PCalModeS').value='不需要进行互算';
		}else{
			fmA.all('PCalMode').value="";
			fmA.all('PCalModeS').value="";
		}
		//-------- end
		//-------- delete by jucy
		//if(result1[0][7]!=null&&result1[0][7]!=''||result1[0][8]!=null||result1[0][8]!=''){
		//-------- end
		if(result1[0][7]!=null&&result1[0][7]!=''){
			fmA.all("IsInsuYear")[0].checked=true;
			showDivInsuYear();
		}else{
			fmA.all("IsInsuYear")[1].checked=true;
			closeDivInsuYear();
		}
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种代码";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="缴费责任代码";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="缴费责任名称";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="责任代码";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=3;
		
		iArray[5]=new Array();
		iArray[5][0]="责任名称";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		/*
		iArray[5]=new Array();
		iArray[5][0]="可选标记";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=2;
		iArray[5][10]="pdriskduty";
		iArray[5][11]="0|^M|必须^C|可选";
		*/
		
		Mulline9Grid= new MulLineEnter( "fm" , "Mulline9Grid" ); 
		
		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName ="afterRadioSelect";
		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}


function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="缴费编码";
		iArray[1][1]="100px";
		iArray[1][2]=80;
		iArray[1][3]=3;
		
		iArray[1]=new Array();
		iArray[1][0]="缴费间隔";
		iArray[1][1]="100px";
		iArray[1][2]=80;
		iArray[1][3]=0;

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=0;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline11Grid()
{	

	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种代码";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="给付责任代码";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="给付责任名称";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="责任代码";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=3;
		
		iArray[5]=new Array();
		iArray[5][0]="责任名称";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.selBoxEventFuncName ="afterRadioSelect2";

		Mulline11Grid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}


function initMulline12Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="主险编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="主险名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;


		Mulline12Grid= new MulLineEnter( "fmF" , "Mulline12Grid" ); 

		Mulline12Grid.mulLineCount=1;
		Mulline12Grid.displayTitle=1;
		Mulline12Grid.canSel=0;
		Mulline12Grid.canChk=0;
		Mulline12Grid.hiddenPlus=0;
		Mulline12Grid.hiddenSubtraction=0; 
		//Mulline12Grid.selBoxEventFuncName ="afterRadioSelect";
		Mulline12Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline13Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="附加险编码";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="附加险名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;


		Mulline13Grid= new MulLineEnter( "fmF" , "Mulline13Grid" ); 

		Mulline13Grid.mulLineCount=1;
		Mulline13Grid.displayTitle=1;
		Mulline13Grid.canSel=0;
		Mulline13Grid.canChk=0;
		Mulline13Grid.hiddenPlus=0;
		Mulline13Grid.hiddenSubtraction=0; 
		//Mulline12Grid.selBoxEventFuncName ="afterRadioSelect";
		Mulline13Grid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}


function initMulline14Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="50px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="责任代码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="责任名称";
		iArray[2][1]="50px";
		iArray[2][2]=100;
		iArray[2][3]=0;
	
		iArray[3]=new Array();
		iArray[3][0]="可选标记";
		iArray[3][1]="50px";
		iArray[3][2]=100;
		iArray[3][3]=2;
		iArray[3][10]="pdriskduty";
		iArray[3][11]="0|^M|必须^C|可选";
		
		Mulline14Grid= new MulLineEnter( "fmA" , "Mulline14Grid" ); 

		Mulline14Grid.mulLineCount=0;
		Mulline14Grid.displayTitle=1;
		Mulline14Grid.canSel=1;
		Mulline14Grid.canChk=0;
		Mulline14Grid.hiddenPlus=1;
		Mulline14Grid.hiddenSubtraction=1;
		Mulline14Grid.selBoxEventFuncName ="afterRadioM14Select";
		Mulline14Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}


var mainSubRiskResult = '';

function initRisk(){
	var riskcode ="<%=request.getParameter("riskcode")%>";
	
	//var sql = "select a.riskname, kindcode,riskProp,RISKTYPE1,RiskTypeDetail,RiskPeriod,PolType,SubRiskFlag,BonusFlag,ListFlag,SignDateCalMode,InsuredFlag,MngCom,RiskType3,NeedGetPolDate,SpecFlag,riskflag,bonusmode,RiskType4,RiskType5,RiskType7,RiskTypeAcc from pd_lmrisk a inner join pd_lmriskapp b on a.riskcode=b.riskcode and a.riskcode='"+"<%=request.getParameter("riskcode")%>"+"'";

 var mySql2=new SqlClass();
	var sqlid2 = "PDRiskDefiInputSql23";
	mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(riskcode);//指定传入的参数
	var sql = mySql2.getString();
	mainSubRiskResult = easyExecSql(sql,1,1,1);

	var duty = ['RiskName','riskType','riskProp','RISKTYPE1','RiskTypeDetail','RiskPeriod','PolType','SubRiskFlag','BonusFlag','ListFlag','SignDateCalMode','InsuredFlag','MngCom','RiskType3','NeedGetPolDate','SpecFlag','RiskFlag','BonusMode','RiskType4','RiskType5','RiskType7','RiskTypeAcc','SaleFlag','AutoPayType','AutoETIType','RiskType2','RiskType9','CancleForeGetSpecFlag'];
	
	var codetype = ['pd_yesno','pd_kindcode','pd_riskprop','pd_risktype1','pd_risktypedetail','pd_riskperiod1','pd_poltype','subriskflag1','pd_bonusflag1','pd_listflag','pd_signdatecalmode','pd_insuredflag','pd_mngcom','pd_risktype3','pd_needgetpoldate','pd_specflag','pd_riskflag','bonusgetmode','pd_risktype4','pd_risktype5','pd_risktype71','pd_risktypeacc','pd_saleflag','pd_autopaytype','pd_autoetitype','pd_risktype2','pd_risktype91','pd_nforegetspecflag1'];
	
	var resultS ;
	if(mainSubRiskResult){
		for( var i=0;i<duty.length;i++){
			//alert(duty[i]+":"+mainSubRiskResult[0][i]);
			if(mainSubRiskResult[0][i]!=null&&mainSubRiskResult[0][i]!='')
			{
		   document.getElementById(duty[i]).value= mainSubRiskResult[0][i];   
		   if(i>0&&i<codetype.length){
			   //var sql = "select trim(codename) from ldcode where trim(code)='"+mainSubRiskResult[0][i]+"' and codetype='"+codetype[i]+"'";
			   var mySql3=new SqlClass();
				var sqlid3 = "PDRiskDefiInputSql22";
				mySql3.setResourceName(tResourceName); //指定使用的properties文件名
				mySql3.setSqlId(sqlid3);//指定使用的Sql的id
				mySql3.addSubPara(mainSubRiskResult[0][i]);//指定传入的参数
				mySql3.addSubPara(codetype[i]);//指定传入的参数
					var sql = mySql3.getString();
			   resultS = easyExecSql(sql,1,1,1);
			   if(resultS){
			   	document.getElementById(duty[i]+'S').value= resultS[0][0];
			   }
		   }
		  } 
		}
	}	
	//初始化产品折扣标记
//	sql = "select 1 from pd_lmdiscount where riskcode='" + riskcode + "'";
 var mySql4=new SqlClass();
				var sqlid4 = "PDRiskDefiInputSql24";
				mySql4.setResourceName(tResourceName); //指定使用的properties文件名
				mySql4.setSqlId(sqlid4);//指定使用的Sql的id
				mySql4.addSubPara(riskcode);//指定传入的参数
			
				sql = mySql4.getString();
	resultS = easyExecSql(sql,1,1,1);
	if(resultS){
		fmF.all('prodDisFlag')[0].checked = true;
		showDivProdDis();
	}
	//alert('stop');
	addMainOrSubRisk(riskcode);
	
	//	var sql = "select RiskShortName,RiskEnName,RiskEnShortName,OrigRiskCode from pd_lmrisk where riskcode='"+"<%=request.getParameter("riskcode")%>"+"'";
var mySql5=new SqlClass();
				var sqlid5 = "PDRiskDefiInputSql25";
				mySql5.setResourceName(tResourceName); //指定使用的properties文件名
				mySql5.setSqlId(sqlid5);//指定使用的Sql的id
				mySql5.addSubPara(riskcode);//指定传入的参数
			
				sql = mySql5.getString();

	var result1 = easyExecSql(sql,1,1,1);
	 if(result1){
	document.getElementById("RiskShortName").value =result1[0][0];
	document.getElementById("RiskEnName").value =result1[0][1];
	document.getElementById("RiskEnShortName").value =result1[0][2];
	document.getElementById("OrigRiskCode").value =result1[0][3];

}

	//var sql = "select StartDate,EndDate,MinAppntAge,MaxAppntAge,MaxInsuredAge,MinInsuredAge from pd_lmriskapp where riskcode='"+"<%=request.getParameter("riskcode")%>"+"'";
var mySql6=new SqlClass();
				var sqlid6 = "PDRiskDefiInputSql26";
				mySql6.setResourceName(tResourceName); //指定使用的properties文件名
				mySql6.setSqlId(sqlid6);//指定使用的Sql的id
				mySql6.addSubPara(riskcode);//指定传入的参数
			
				sql = mySql6.getString();
	var result1 = easyExecSql(sql,1,1,1);
	 if(result1){
	document.getElementById("StartDate").value =result1[0][0];
	document.getElementById("EndDate").value =result1[0][1];
	document.getElementById("MinAppntAge").value =result1[0][2];
	document.getElementById("MaxAppntAge").value =result1[0][3];
	document.getElementById("MaxInsuredAge").value =result1[0][4];
	document.getElementById("MinInsuredAge").value =result1[0][5];
}
//alert('stop');
	
}

function addMainOrSubRisk(riskcode){
	if(document.getElementById('SubRiskFlag').value == 'S'){
		document.getElementById('SubRiskFlagDiv').style.display = "";
		document.getElementById('MainRiskFlagDiv').style.display = "none";
		//var	sql = "select riskcode from pd_lmriskrela where relariskcode ='"+riskcode+"'";	
		var mySql7=new SqlClass();
		var sqlid7 = "PDRiskDefiInputSql27";
		mySql7.setResourceName(tResourceName); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(riskcode);//指定传入的参数
			
		var	sql = mySql7.getString();
					
		mainSubRiskResult = easyExecSql(sql,1,1,1);
		var result2 ='';
		if(mainSubRiskResult){	
			for(var  i =0; i<mainSubRiskResult.length; i++){
				Mulline12Grid.setRowColData(i,1,mainSubRiskResult[i][0]);
				//	var sql = "select riskname from pd_lmrisk where riskcode ='"+mainSubRiskResult[i][0]+"'";
				var mySql8=new SqlClass();
				var sqlid8 = "PDRiskDefiInputSql28";
				mySql8.setResourceName(tResourceName); //指定使用的properties文件名
				mySql8.setSqlId(sqlid8);//指定使用的Sql的id
				mySql8.addSubPara(mainSubRiskResult[i][0]);//指定传入的参数
				var	sql = mySql8.getString();
				result2 = easyExecSql(sql,1,1,1);
				if(result2){
					Mulline12Grid.setRowColData(i,2,result2[0][0]);
				}
				Mulline12Grid.addOne();
			}
			Mulline12Grid.delBlankLine();		
		}
	}
	
	//alert('stop1');
	
	if(document.getElementById('SubRiskFlag').value == 'M' || document.getElementById('SubRiskFlag').value == 'A'){
		document.getElementById('MainRiskFlagDiv').style.display = "";
		document.getElementById('SubRiskFlagDiv').style.display = "none";
		
		//alert('1111')
		//var sql = "select relariskcode from pd_lmriskrela where riskcode ='"+riskcode+"'";
		var mySql9=new SqlClass();
		var sqlid9 = "PDRiskDefiInputSql29";
		mySql9.setResourceName(tResourceName); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(riskcode);//指定传入的参数
		var	sql = mySql9.getString();		
		mainSubRiskResult = easyExecSql(sql,1,1,1);
		if(mainSubRiskResult){	
			for(var  i =0; i<mainSubRiskResult.length; i++){
				Mulline13Grid.setRowColData(i,1,mainSubRiskResult[i][0]);
				//sql = "select riskname from pd_lmrisk where riskcode ='"+mainSubRiskResult[i][0]+"'";
				var mySql8=new SqlClass();
				var sqlid8 = "PDRiskDefiInputSql28";
				mySql8.setResourceName(tResourceName); //指定使用的properties文件名
				mySql8.setSqlId(sqlid8);//指定使用的Sql的id
				mySql8.addSubPara(mainSubRiskResult[i][0]);//指定传入的参数
				var	sql = mySql8.getString();
				var result2 = easyExecSql(sql,1,1,1);
				if(result2){					
					Mulline13Grid.setRowColData(i,2,result2[0][0]);
				}
					Mulline13Grid.addOne();
			}		
			Mulline13Grid.delBlankLine();		
		}
	}
		//alert('stop2');
}

//初始化单选框
function initRadio(){
	//document.all("GetFlag")[0].checked=true;
	initISPayStartYear();
	//var selectSQL = "select getflag, RnewFlag, InsuAccFlag,ChoDutyFlag,CPayFlag,RinsFlag from pd_lmrisk where riskcode = '" + document.getElementById("RiskCode").value +"'";
	var mySql9=new SqlClass();
			var sqlid9 = "PDRiskDefiInputSql30";
				mySql9.setResourceName(tResourceName); //指定使用的properties文件名
				mySql9.setSqlId(sqlid9);//指定使用的Sql的id
				mySql9.addSubPara(document.getElementById("RiskCode").value);//指定传入的参数
			var	selectSQL = mySql9.getString();		
	
	
	var crr = easyExecSql(selectSQL);
	if(crr != null){
		var GetFlag = crr[0][0];
		
		//getFlag 生存给付标记
		if(GetFlag == 'N'){
			document.all("GetFlag")[1].checked=true;
		}else if(GetFlag == 'Y'){
			document.all("GetFlag")[0].checked=true;
		}else{
	
		}
		
		//UWFlag 续保标记
		var RnewFlag = crr[0][1];
		if(RnewFlag == 'N'){
			document.all("RnewFlag")[1].checked=true;
		}else if(RnewFlag == 'Y'){
			document.all("RnewFlag")[0].checked=true;
		}else{
		
		}
		
		//InsuAccFlag 账户型险种标记
		var InsuAccFlag = crr[0][2];
		if(InsuAccFlag == 'N'){
			document.all("InsuAccFlag")[1].checked=true;
		}else if(InsuAccFlag == 'Y'){
			document.all("InsuAccFlag")[0].checked=true;
			showDivAccInsu();
		}else{
		
		}
		
			//ChoDutyFlag 责任可选标记
		var ChoDutyFlag = crr[0][3];
		if(ChoDutyFlag == 'N'){
			document.all("ChoDutyFlag")[1].checked=true;
		}else if(ChoDutyFlag == 'Y'){
			document.all("ChoDutyFlag")[0].checked=true;
		}else{
		
		}
		
		//CPayFlag 续期收费标记
		var CPayFlag = crr[0][4];
		if(CPayFlag == 'N'){
			document.all("CPayFlag")[1].checked=true;
		}else if(CPayFlag == 'Y'){
			document.all("CPayFlag")[0].checked=true;
		}else{
		
		}
		
		//RinsFlag 分保标记
		var RinsFlag = crr[0][5];
		if(RinsFlag == 'N'){
			document.all("RinsFlag")[1].checked=true;
		}else if(RinsFlag == 'Y'){
			document.all("RinsFlag")[0].checked=true;
		}else{
		
		}
		
	
		//alert('stop1');
		//var selectSQL = "select AutoPayFlag,InvestFlag,CutAmntStopPay,loanflag,MortagageFlag from pd_lmriskapp where riskcode = '" + document.getElementById("RiskCode").value + "'";	
		
		var mySql10=new SqlClass();
			var sqlid10 = "PDRiskDefiInputSql31";
				mySql10.setResourceName(tResourceName); //指定使用的properties文件名
				mySql10.setSqlId(sqlid10);//指定使用的Sql的id
				mySql10.addSubPara(document.getElementById("RiskCode").value);//指定传入的参数
			var selectSQL1 = mySql10.getString();		

		var crr3 = easyExecSql(selectSQL1);
		//	alert('stop2');
		//var sel = "select lifetype from pd_lmrisk where riskcode = '" + document.getElementById("RiskCode").value + "'";
		//var crr1 = easyExecSql(sel);
		//var Lifttype = crr1[0][0];

		var AutoPayFlag = crr3[0][0];
		//alert(AutoPayFlag);
		if(AutoPayFlag == '1'){
			document.all("AutoPayFlag")[0].checked=true;
		}else{
			document.all("AutoPayFlag")[1].checked=true;
		}
		
		var InvestFlag = crr3[0][1];
		if(InvestFlag == "Y"){
			document.all("InvestFlag")[0].checked=true;
		}else if(InvestFlag == "N"){
			document.all("InvestFlag")[1].checked=true;
		}else{
		
		}
		
		var CutAmntStopPay = crr3[0][2];
		if(CutAmntStopPay == 'Y'){
			document.all("CutAmntStopPay")[0].checked=true;
		}else if(CutAmntStopPay == 'N'){
			document.all("CutAmntStopPay")[1].checked=true;
		}else{
		
		}		
		
		var LoanFlag = crr3[0][3];
		//alert(LoanFlag);
		if(LoanFlag == 'Y'){
			document.all("LoanFlag")[0].checked=true;
		}else if(LoanFlag == 'N'){
			document.all("LoanFlag")[1].checked=true;
		}
		//MortagageFlag
		var MortagageFlag = crr3[0][4];
		//alert(LoanFlag);
		if(MortagageFlag == 'Y'){
			document.all("MortagageFlag")[0].checked=true;
		}else if(MortagageFlag == 'N'){
			document.all("MortagageFlag")[1].checked=true;
		}
		var AutoETIFlag= crr3[0][5];
				if(MortagageFlag == 'Y'){
			document.all("AutoETIFlag")[0].checked=true;
		}else if(MortagageFlag == 'N'){
			document.all("AutoETIFlag")[1].checked=true;
		}
				var AutoCTFlag= crr3[0][6];
				if(AutoCTFlag == 'Y'){
			document.all("AutoCTFlag")[0].checked=true;
		}else if(MortagageFlag == 'N'){
			document.all("AutoCTFlag")[1].checked=true;
		}
				var NonParFlag= crr3[0][7];
				if(NonParFlag == 'Y'){
			document.all("NonParFlag")[0].checked=true;
		}else if(MortagageFlag == 'N'){
			document.all("NonParFlag")[1].checked=true;
		}
				var BackDateFlag= crr3[0][8];
				if(BackDateFlag == 'Y'){
			document.all("BackDateFlag")[0].checked=true;
		}else if(MortagageFlag == 'N'){
			document.all("BackDateFlag")[1].checked=true;
		}
	}
}

function initCheckBox(){
	var riskCode = document.getElementById("RiskCode").value ;
	
	var sql = "select count(*) from pd_lmriskpayintv where riskcode = '" + riskCode + "'";
	var crr = easyExecSql(sql, 1, 1, 1);
	var resultLen = crr[0][0];
	
	sql = "select payintv from pd_lmriskpayintv where riskcode = '" + riskCode + "'";
	var codeSelect = "select count(trim(CodeName)) from ldcode where 1 = 1 and codetype = 'pd_payintv' order by Code";
	var crr3 = easyExecSql(codeSelect, 1, 1, 1);
	var selectLen = crr3[0][0];
	
	codeSelect = "select trim(Code), trim(CodeName), trim(CodeAlias), trim(ComCode), trim(OtherSign) from ldcode where 1 = 1 and codetype = 'pd_payintv' order by to_number(Code)";
	var crr2 = easyExecSql(codeSelect, 1, 1, 1);
	
	var crr1 = easyExecSql(sql, 1, 1, 1);
	for(var i = 0; i < resultLen; i ++ ){
		var result = crr1[i][0];
		for(var j = 0; j < selectLen; j ++){
			if(result == crr2[j][0]){
				document.all("payintv")[j].checked=true;
			}
		}
	}
}

function initCalmode(){
	var sqlStr = "select codename from ldcode where codetype = 'calmodesel'";
	var crr = easyExecSql(sqlStr, 1, 1, 1);
	if(crr)
	{
		var result = crr[0][0];
	
		var resultLen = result.length;
	
		if(resultLen < 6){
			for(var i = resultLen ; i < 6; i ++){
				result = '0' + result;
			}
		}
	
		fm.all('payCalCode').value = result;
	}
}

function initGetCalMode(){
	var sqlStr = "select codename from ldcode where codetype = 'calmodegetsel'";
	var crr1 = easyExecSql(sqlStr, 1, 1, 1);
	if(crr1)
	{
		var result1 = crr1[0][0];
	
		fm.all('getCalCode').value = result1;
	}
}

function initISPayStartYear(){
  fm.all('ISPayStartYear')[0].checked=false;
  fm.all('ISPayStartYear')[1].checked=false;
  fm.all('PayStartYear').value="";
  fm.all('PayStartYearFlag').value="";
  fm.all('PayStartFlagName').value="";
  fm.all('PayStartDateCalRef').value="";
  fm.all('PaySDCalRefName').value="";
  fm.all('PayStartDateCalMode').value="";
  fm.all('PaySDCalModeName').value="";
  closePayStartCtrl();
}

function initRiskPay(){
	fmP.all('GracePeriod2').value="";
	fmP.all('GracePeriodUnit').value="";
	fmP.all('GPeriodUnitName').value="";
	//-------- update by jucy 
	//GRACEDATECALMODE ，无需界面录入，系统自动存储为0
	fmP.all('GraceDateCalMode').value="0";
	fmP.all('GDateCalModeName').value="以计算为准";
	//-------- end
	fmP.all('GraceCalCode').value="";
	fmP.all('OverdueDeal').value="";
	fmP.all('OverdueDealName').value="";
	fmP.all('UrgePayFlag').value="";
	fmP.all('UrgePayFlagName').value="";
}
</script>
