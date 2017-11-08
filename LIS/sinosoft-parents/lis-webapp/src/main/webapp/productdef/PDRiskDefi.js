//程序名称：PDRiskDefi.js
//程序功能：产品基础信息录入
//创建日期：2009-3-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass();
var Mulline12GridTurnpage = new turnPageClass();
var Mulline13GridTurnpage = new turnPageClass();
var Mulline14GridTurnpage = new turnPageClass();
var showInfo;
var whichForm = '';
//定义sql配置文件
var tResourceName = "properties.PDRiskDefiInputSql";

function submitForm(){
	if(document.getElementById("IsReadOnly").value == "1"){
	  	myAlert(""+"您无权执行此操作"+"");
	  	return;
	}
	if(fmF.all("operator").value!="del"){
		if(!verifyForm('fmF'))
		{
			return false;
		}
	}
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//IE11-大小写
	//fmF.riskCodeF.value=document.getElementById("riskCode").value;
	fmF.riskCodeF.value=document.getElementById("RiskCode").value;
	fmF.action="./PDRiskDefiSave.jsp";
	whichForm = 'F';
	fmF.submit();
}



function submitFormNoCheck(){
	if(fmF.all("IsReadOnly").value == "1"){
		myAlert(""+"您无权执行此操作"+"");
		return;
 	}
	if(!verifyForm('fmF')){
		return false;
	}
	
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//IE11-大小写   
	//fmF.all('RiskCode').value=document.getElementById("riskCode").value;
	
	fmF.all('RiskCode').value=document.getElementById("RiskCode").value;
	fmF.all('RequDate').value=document.getElementById("RequDate").value;
	
	fmF.all('RiskCodem').value=document.getElementById("RiskCode").value;
	fmF.all('RequDatem').value=document.getElementById("RequDate").value;
	//alert("@@@"+fmF.all('RiskCodem').value+"@@@"+fmF.all('RequDatem').value);
	fmF.submit();
}


function afterSubmit( FlagStr, content ){
	showInfo.close();
	if (FlagStr == "Fail" ){             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
    	if(whichForm == 'F' && fmF.all("operator").value=="del"){
			//var duty = ['RiskName','RiskShortName','Startdate','enddate','ChoDutyFlag','riskType','riskProp','RISKTYPE1','RiskPeriod','PolType','SubRiskFlag'];
			
			//-------update by jucy
			//增加：删除后，页面部分输入框没有设置为初值。
			initPayPlan();
			initGetDuty();
			var duty = ['RiskName','riskProp','RISKTYPE1','RiskPeriod','SubRiskFlag','RiskTypeDetail','BonusFlag','InsuredFlag','RiskType3','SpecFlag','RiskFlag','RiskType2','RiskType4','RiskType7','RiskType9','AutoETIType','AutoPayType','CancleForeGetSpecFlag','SaleFlag'];
			for(var i=0;i<duty.length;i++){
				document.getElementById(duty[i]).value = '';
				if(i>0){
					document.getElementById(duty[i]+'S').value = '';
				}
			}	
			//-------end
		
			document.getElementById("RiskShortName").value ='';
			document.getElementById("RiskEnName").value ='';
			document.getElementById("RiskEnShortName").value ='';
			//document.getElementById("OrigRiskCode").value ='';
			//document.getElementById("StartDate").value ='';
			document.getElementById("EndDate").value ='';
			document.getElementById("MinAppntAge").value ='';
			document.getElementById("MaxAppntAge").value ='';
			document.getElementById("MaxInsuredAge").value ='';
			document.getElementById("MinInsuredAge").value ='';
		  	fmF.GetFlag[0].checked=false;
		 	fmF.GetFlag[1].checked="checked";
		 	fmF.RnewFlag[0].checked=false;
		 	fmF.RnewFlag[1].checked="checked";
		 	fmF.InsuAccFlag[0].checked=false;
		 	fmF.InsuAccFlag[1].checked="checked";
		 	fmF.ChoDutyFlag[0].checked=false;
		 	fmF.ChoDutyFlag[1].checked="checked";
		 	fmF.CPayFlag[0].checked=false;
		 	fmF.CPayFlag[1].checked="checked";
		 	fmF.RinsFlag[0].checked=false;
		 	fmF.RinsFlag[1].checked="checked";
		 	fmF.AutoPayFlag[0].checked=false;
		 	fmF.AutoPayFlag[1].checked="checked";
		 	fmF.InvestFlag[0].checked=false;
		 	fmF.InvestFlag[1].checked="checked";
		 	fmF.CutAmntStopPay[0].checked=false;
		 	fmF.CutAmntStopPay[1].checked="checked";
		 	
		 	fmF.LoanFlag[0].checked=false;
		 	fmF.LoanFlag[1].checked="checked";
		 	
		 	fmF.MortagageFlag[0].checked=false;
		 	fmF.MortagageFlag[1].checked="checked";
		 	
		 	fmF.AutoETIFlag[0].checked=false;
		 	fmF.AutoETIFlag[1].checked="checked";
		 	
		 	fmF.AutoCTFlag[0].checked=false;
		 	fmF.AutoCTFlag[1].checked="checked";
		 	
		 	fmF.NonParFlag[0].checked=false;
		 	fmF.NonParFlag[1].checked="checked";
		 	
		 	fmF.BackDateFlag[0].checked=false;
		 	fmF.BackDateFlag[1].checked="checked";
		 	
		 	fmF.CutAmntStopPay[0].checked=false;
		 	fmF.CutAmntStopPay[1].checked="checked";
		 	
		 	fmF.prodDisFlag[0].checked=false;
		 	fmF.prodDisFlag[1].checked="checked";
	    }else if(whichForm == 'pay' &&   fm.all("operator").value=="del"){
    		fm.all('payDutyCode').value='';
 			fm.all('payDutyName').value='';
 			fm.all('choFlag').value='';
 			fm.all('choFlagS').value='';
	 		fm.all('payPlanCode').value='';
	 		fm.all('payPlanName').value='';
	 		fm.all('zeroFlag').value='';
	 		fm.all('zeroFlagS').value='';
	 		fm.all('payCalType').value = '';
	 		fm.all('payCalTypeS').value = '';
	 		fm.all('payCalCode').value = '';
	 		fm.all('needAccPay').value = '';
	 		fm.all('needAccPayS').value = '';
		}else if( whichForm == 'get' &&   fm.all("operator").value=="del"){
    		fm.all('dutyCode1').value='';
 			fm.all('dutyName1').value='';
 			fm.all('AddAmntFlag').value='';
 			fm.all('AddAmntFlagS').value='';
		 	fm.all('getDutyCode').value='';
		 	fm.all('getDutyname').value='';
		 	fm.all('type').value='';
	 		fm.all('typeS').value='';
	 		fm.all('getCalType').value= '';
	 		fm.all('getCalTypeS').value= '';
		 	fm.all('getCalCode').value = '';
		 	fm.all('needAccGet').value = '';
		 	fm.all('needAccGetS').value = '';
  		}else if (whichForm == 'A' && fmA.all('operatorS').value == "del"){
		  	fmA.all('DutyName').value ="";
		  	fmA.all('choFlag').value ="";
		  	fmA.all('choFlagS').value ="";
		  	fmA.all('InsuYear').value ="";
		  	fmA.all('InsuYearFlag').value ="";
		  	fmA.all('InsuYearFlagS').value ="";
		  	fmA.all('InsuYearRela').value ="";
		  	fmA.all('InsuYearRelaS').value ="";
		  	fmA.all('PayEndDateCalRef').value ="";
		  	fmA.all('PayEndDateCalRefS').value ="";
		  	fmA.all('PayEndDateCalMode').value ="";
		  	fmA.all('PayEndDateCalModeS').value ="";
		  	fmA.all('PayEndYearRela').value ="";
		  	fmA.all('PayEndYearRelaS').value ="";
		  	fmA.all('GetYear').value ="";
		  	fmA.all('GetYearFlag').value ="";
		  	fmA.all('GetYearFlagS').value ="";
		  	fmA.all('GetYearRela').value ="";
		  	fmA.all('GetYearRelaS').value ="";
		  	fmA.all('CalMode').value ="";
		  	fmA.all('CalModeS').value ="";
		  	//-------- add by jucy
		  	//建议书计算方法
		  	fmA.all('PCalMode').value ="";
		  	fmA.all('PCalModeS').value ="";
		  	//-------- end
		  	
		  	//-------- update by jucy
		  	//BASICCALCODE 字段，默认存储为”000003"
		  	fmA.all('BasicCalCode').value ="000003";
		  	//-------- end
		  	fmA.all('AmntFlag').value ="";
		  	fmA.all('AmntFlagS').value ="";
		  	fmA.all('VPU').value ="";
		  	fmA.IsInsuYear.checked=false;
		  	closeDivInsuYear();
		  	document.getElementById("DutyShowPart").style.display = "none";
			fm.all('getDutyName').value='';
			fm.all('AddAmntFlag').value='';
			fm.all('AddAmntFlagS').value='';
			fm.all('type').value='';
			fm.all('typeS').value='';
			fm.all('getCalType').value='';
			fm.all('getCalTypeS').value='';
			fm.all('getCalCode').value='';
			fm.all('GetIntv').value= '';
			fm.all('payPlanName').value='';
			fm.all('zeroFlag').value='';
			fm.all('zeroFlagS').value='';
			fm.all('payPlanCode').value='';
			fm.all('payCalType').value='';
			fm.all('payCalCode').value='';
			fm.all('GracePeriod').value='';
			fm.all('payCalTypeS').value='';
			fm.all('needAccPay').value = '';
			fm.all('needAccPayS').value = '';
			fm.all('needAccGet').value = '';
			fm.all('needAccGetS').value = '';
		}
		initForm();
	}
	//--------add by jucy7
	//添加基础信息录入完毕后，关闭页面并刷新前页面功能
	if(fmF.all("operator").value == "workflow"){
		//top.opener.document.reload();
		top.opener.queryDefining( );
		top.close();
	}
	//--------end
}

function afterSubmit1( FlagStr, content, dutycode )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
    
    if(whichForm == 'F' && fmF.all("operator").value=="del"){
    			var duty = ['RiskName','RiskShortName','enddate','ChoDutyFlag','riskProp','RISKTYPE1','RiskPeriod','SubRiskFlag'];
				for(var i=0;i<duty.length;i++){
						document.getElementById(duty[i]).value = '';
						if(i>3){
							document.getElementById(duty[i]+'S').value = '';
						}
				}	
    }else if(whichForm == 'pay' &&   fm.all("operator").value=="del"){
    		fm.all('payPlanName').value='';
 			fm.all('zeroFlag').value='';
 			fm.all('zeroFlagS').value='';
 			fm.all('payPlanCode').value='';
 			fm.all('payCalType').value='';
	 		fm.all('payCalCode').value='';
	 		fm.all('GracePeriod').value='';
	 		fm.all('payCalTypeS').value='';
	 		fm.all('needAccPay').value = '';
 			fm.all('needAccPayS').value = '';
 			
 			fm.all('InvestType').value='';
 			fm.all('InvestTypeS').value='';
 			fm.all('PayEndYear').value='';
 			fm.all('PayEndYearFlag').value='';
 			fm.all('PayEndYearFlagS').value='';
 			fm.all('PayEndDateCalMode').value='';
 			fm.all('PayEndDateCalModeS').value='';
	 		fm.all('PayIntv').value='';
	 		fm.all('UrgePayFlag').value='';
	 		fm.all('UrgePayFlagS').value='';
	 		fm.all('PayAimClass').value = '';
 			fm.all('PayAimClassS').value = '';
    }else if( whichForm == 'get' &&   fm.all("operator").value=="del"){

    		fm.all('getDutyName').value='';
 			fm.all('AddAmntFlag').value='';
 			fm.all('AddAmntFlagS').value='';
 			fm.all('type').value='';
 			fm.all('typeS').value='';
 			fm.all('getCalType').value='';
 			fm.all('getCalTypeS').value='';
 			fm.all('getCalCode').value='';
 			fm.all('GetIntv').value= '';
			fm.all('needAccGet').value = '';
 			fm.all('needAccGetS').value = '';
 			fm.all('GetYear').value='';
 			fm.all('GetYearFlag1').value='';
 			fm.all('GetYearFlag1S').value='';
 			fm.all('GetType1').value='';
 			fm.all('GetType1S').value='';
 			
 			fm.all('UrgeGetFlag').value='';
 			fm.all('UrgeGetFlagS').value='';
 			fm.all('NeedCancelAcc').value='';
 			fm.all('NeedCancelAccS').value='';
 			fm.all('zeroGetFlag').value='';
 			fm.all('zeroGetFlagS').value='';
 			fm.all('CanGet').value='';
 			fm.all('CanGetS').value='';
 									
 			fm.all('StartDateCalRef').value='';
 			fm.all('StartDateCalRefS').value='';
 			fm.all('StartDateCalMode').value= '';
			fm.all('StartDateCalModeS').value = '';
 			fm.all('GetEndPeriod').value = '';
 			fm.all('GetEndUnit').value='';
 			fm.all('GetEndUnitS').value='';
 			fm.all('EndDateCalRef').value='';
 			fm.all('EndDateCalRefS').value='';
 			fm.all('EndDateCalMode').value= '';
			fm.all('EndDateCalModeS').value = '';
  }
  
		
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
		initCheckBox(); 
		
		 

    
	var rowNum=Mulline14Grid.mulLineCount;
	
	for(var i = 0; i < rowNum; i ++){
		var tempdata = Mulline14Grid.getRowColData(i, 1);
		if(tempdata == dutycode){
			Mulline14Grid.selOneRow(i + 1);
		}
	}
	
	if(whichForm == 'pay' &&   fm.all("operator").value=="save"){
		    		fm.all('payPlanName').value='';
 			fm.all('zeroFlag').value='';
 			fm.all('zeroFlagS').value='';
 			fm.all('payPlanCode').value='';
 			fm.all('payCalType').value='';
	 		fm.all('payCalCode').value='';
	 		fm.all('GracePeriod').value='';
	 		fm.all('payCalTypeS').value='';
	 		fm.all('needAccPay').value = '';
 			fm.all('needAccPayS').value = '';
 			fm.all('PayEndYear').value='';
 			fm.all('PayEndYearFlag').value='';
 			fm.all('PayEndYearFlagS').value='';
 			fm.all('PayEndDateCalMode').value='';
 			fm.all('PayEndDateCalModeS').value='';
	 		fm.all('PayIntv').value='';
	 		fm.all('UrgePayFlag').value='';
	 		fm.all('UrgePayFlagS').value='';
	 		fm.all('PayAimClass').value = '';
 			fm.all('PayAimClassS').value = '';
		//initCalmode();

	}
	
	if(whichForm == 'get' &&   fm.all("operator").value=="save"){
		    fm.all('getDutyName').value='';
 			fm.all('AddAmntFlag').value='';
 			fm.all('AddAmntFlagS').value='';
 	
 			fm.all('type').value='';
 			fm.all('typeS').value='';
 			fm.all('getCalType').value='';
 			fm.all('getCalTypeS').value='';
 			fm.all('getCalCode').value='';
 			fm.all('GetIntv').value= '';
 			fm.all('needAccGet').value = '';
 			fm.all('needAccGetS').value = '';
		//initGetCalMode();
	}
  } 
}


function save()
{
	fmF.all("operator").value="save";
	if(!checkInsuFlag()){
		myAlert(""+"请先选择帐户型险种标记"+"");
		return;
	}
	/*if(!riskPayIntvCheck()){
		alert("请先选择险种缴费间隔");
		return;
	}*/

  submitForm();
}

function update()
{
 fmF.all("operator").value="update";
 /*if(!riskPayIntvCheck()){
		alert("请先选择险种缴费间隔");
		return;
	}*/
 submitForm();
}
function del()
{
	//------add by jucy-1 ---
	//添加原因：增加是否保存过的校验，修改直接点击【删除】按钮时出现删除成功问题。
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql32";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(document.getElementById('RiskCode').value);//指定传入的参数
	var riskCountSQL1 = mySql.getString();
	var arr1 = easyExecSql(riskCountSQL1);
	
	var mySql2=new SqlClass();
	var sqlid2 = "PDRiskDefiInputSql33";
	mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	mySql2.setSqlId(sqlid);//指定使用的Sql的id
	mySql2.addSubPara(document.getElementById('RiskCode').value);//指定传入的参数
	var riskCountSQL2 = mySql.getString();
	var arr2 = easyExecSql(riskCountSQL2);
	if(arr1=="0" || arr2=="0"){
		myAlert(""+"请先保存险种基本信息，再做删除操作！"+"");
		return false;
	}
	//------end
	
	fmF.all("operator").value="del";
 	submitForm();
}

//产品复制功能
function productCopy()
{
  if( document.getElementById('RiskName2').value == null || document.getElementById('RiskName2').value == "" )
  {
  	 myAlert(""+"请选择已上线待复制产品！"+"");
  	 return;
  }
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fmF.action=("./PDRiskCopySave.jsp?RiskCode="+document.getElementById('RiskCode').value+"&RiskCode2=")+document.getElementById('RiskCode2').value;
  fmF.submit();
}

function button81(){
	//IE11-大小写
	//showInfo = window.open("PDRiskAppDefiMain.jsp?riskcode=" + fm.all("riskcode").value + "&requdate=" + fm.all("RequDate").value);
	showInfo = window.open("PDRiskAppDefiMain.jsp?riskcode=" + fm.all("RiskCode").value + "&requdate=" + fm.all("RequDate").value);
}

function button100()
{
  if (document.getElementById("payPlanName").value == null || document.getElementById("payPlanName").value ==''){
      myAlert(""+"请输入缴费名称"+"");
      //return;
  }else{
      showInfo = window.open("PDRateCashValueMain.jsp?riskcode=" + document.getElementById("RiskCode").value+"&payplancode="+document.getElementById("payPlanCode").value );
  }
}
function button82(){
	//IE11-大小写
	//showInfo = window.open("PDRiskSortDefiMain.jsp?riskcode=" + fm.all("riskcode").value + "&requdate=" + fm.all("RequDate").value);
	showInfo = window.open("PDRiskSortDefiMain.jsp?riskcode=" + fm.all("RiskCode").value + "&requdate=" + fm.all("RequDate").value);
}
function button83(){
	//IE11-大小写
	//showInfo = window.open("PDRiskDutyRelaMain.jsp?riskcode=" + fm.all("riskcode").value + "&requdate=" + fm.all("RequDate").value);
	showInfo = window.open("PDRiskDutyRelaMain.jsp?riskcode=" + fm.all("RiskCode").value + "&requdate=" + fm.all("RequDate").value);
}
function button84(){
	//IE11-大小写
	//showInfo = window.open("PDRiskInsuAccMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById("riskcode").value + "&requdate=" + document.getElementById("RequDate").value);
	showInfo = window.open("PDRiskInsuAccMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById("RiskCode").value + "&requdate=" + document.getElementById("RequDate").value);
}
function button85()
{
  showInfo = window.open("");
}





function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById("RiskCode").value + "&postcode=00&issuetype=0");
}
function IssueInput()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("RiskCode").value + "&postcode=00&issuetype=0&missionid=" + fm.all("MissionID").value +"&submissionid=" + fm.all("SubMissionID").value + "&activityid=" + fm.all("ActivityID").value);
}


function returnParent()
{
	top.opener.focus();
	top.close();
}


function checkRules(type)
{
	var mySql1=new SqlClass();
	var sqlid1 = "PDRiskDefiInputSql1";
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(type);//指定传入的参数
	var sql = mySql1.getString();

	try
	{
		var algos = easyExecSql(sql,1,1,1);
		
		if(algos == null)
			return "";
		
		var alStr = "";
		
		for(var i = 0; i < algos.length; i++)
		{
			var algoSql = algos[i][0];
			algoSql = algoSql.replace("@RiskCode@", fmF.RiskCode.value);
			var result = easyExecSql(algoSql);
			
			if(result != null && result[0][0] == "1")
			{
				alStr += algos[i][1] + "\r\n";
			}
		}
		if(alStr != "")
		{
			alStr += ""+"\r\n\r\n提示：可使用Ctrl+C复制提示信息，Ctrl+V粘贴到记事本"+"";
		}
		
		return alStr;
	}
	catch(ex)
	{
		return(""+"校验失败"+"" + ex); 
	}
}

function baseInfoDone(){
	fmF.operator.value="workflow";   
	var alStr = checkRules("00");
	if(alStr != "")
	{
		myAlert(alStr);
		return;
	}
	
	if(!checkIsHaveIssue())
	{
		return ;
	}
	fmF.action = 'PDRiskDefiSave.jsp';
	submitFormNoCheck();
}

function checkIsHaveIssue(){
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql2";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(document.getElementById('RiskCode').value);//指定传入的参数
	var issueCountSQL = mySql.getString();
	var arr = easyExecSql(issueCountSQL);
	if(arr[0][0] > 0)
	{
		myAlert(""+"请先将问题件处理完毕后再进行此操作！"+"");
		return false;
	}
	return true;
}

function queryMulline9Grid(){
	//IE11-大小写
	//var riskcode = document.getElementById('riskcode').value;
	var riskcode = document.getElementById('RiskCode').value;
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql3";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(riskcode);//指定传入的参数
	mySql.addSubPara(document.getElementById('DutyCode').value);//指定传入的参数
	var strSQL = mySql.getString();
	Mulline9GridTurnPage.pageLineNum  = 3215;
	Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline14Grid()
{

   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskDefiInputSql4";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(document.getElementById('RiskCode').value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline14GridTurnpage.pageLineNum  = 3215;
   Mulline14GridTurnpage.queryModal(strSQL,Mulline14Grid);
}


function queryMulline11Grid(){
	//IE11-大小写
	//var riskcode = document.getElementById('riskcode').value;
	var riskcode = document.getElementById('RiskCode').value;
	 var mySql=new SqlClass();
	 var sqlid = "PDRiskDefiInputSql5";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(riskcode);//指定传入的参数
	 mySql.addSubPara(document.getElementById('DutyCode').value);//指定传入的参数
   var strSQL = mySql.getString();
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
   
}

function queryMulline12Grid(){
		var sql="";
		var mySql=new SqlClass();
		var sqlid = "PDRiskDefiInputSql6";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara("sqlid");//指定传入的参数
		sql = mySql.getString();
		Mulline12GridTurnpage.pageLineNum = 10;
		Mulline12GridTurnpage.queryModal(sql,Mulline12Grid);
}


function queryMulline10Grid(){
		var sql = "";
		var mySql=new SqlClass();
		var sqlid = "PDRiskDefiInputSql7";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara("sqlid");//指定传入的参数
		sql = mySql.getString();
		Mulline10GridTurnpage.queryModal(sql,Mulline10Grid);
		
}

function queryRisk(){
	var URL = "PDRiskQueryInput.jsp?riskcode="+document.getElementById("RiskCode").value+"&IsDefin=1";
	open(URL);
}


function afterCodeSelect(cCodeName,Field){
	var riskcode = document.getElementById('RiskCode').value;
// 	if(cCodeName=='PD_SUBRISKFLAG1'){
//			
// 			if(Field.value=='S' ){
// 					document.getElementById('SubRiskFlagDiv').style.display = "";
// 					document.getElementById('MainRiskFlagDiv').style.display = "none";
// 					fmF.isSubRisk.value="S";
// 			}else if(Field.value=='M'|| Field.value=='A'){
// 					document.getElementById('MainRiskFlagDiv').style.display = "";
// 					document.getElementById('SubRiskFlagDiv').style.display = "none";
// 					fmF.isSubRisk.value="M";
// 			}
// 				addMainOrSubRisk(riskcode);
// 	}
	if(cCodeName=='PD_SUBRISKFLAG'){
		
			if(Field.value=='S' ){
					document.getElementById('SubRiskFlagDiv').style.display = "";
					document.getElementById('MainRiskFlagDiv').style.display = "none";
					fmF.isSubRisk.value="S";
			}else if(Field.value=='M'|| Field.value=='A'){
					document.getElementById('MainRiskFlagDiv').style.display = "";
					document.getElementById('SubRiskFlagDiv').style.display = "none";
					fmF.isSubRisk.value="M";
			}
				addMainOrSubRisk(riskcode);
	}
 	if(cCodeName=='pd_insuaccflag'){
 		if(Field.value=='Y' ){
 			document.getElementById('DivAccInsu').style.display = "";
 		}else
 		{
 			document.getElementById('DivAccInsu').style.display = "none";
 		}
 	}
 	if(cCodeName=='pd_dutygettype'){
 		if(Field.value=='0' ){
 			document.getElementById('divGetAlive').style.display = "";
 			//document.getElementById('getInvName').style.display = "";
 			//document.getElementById('getInvValue').style.display = "";
 			//-------- add by jucy
 			fm.all("NeedCancelAcc").value="1";
	 		fm.all("NeedCancelAccS").value=""+"必须要账户结清后才能进行领取"+"";
	 		
 			fm.all("CanGet").value="1";
	 		fm.all("CanGetS").value=""+"需要申请后才可领取"+"";
	 		//-------- end
 		}else{
 			document.getElementById('divGetAlive').style.display = "none";
 			//document.getElementById('getInvName').style.display = "none";
 			//document.getElementById('getInvValue').style.display = "none";
 			//-------- add by jucy
 			fm.all("NeedCancelAcc").value="0";
	 		fm.all("NeedCancelAccS").value=""+"不需要账户结清"+"";
	 		
 			fm.all("CanGet").value="0";
	 		fm.all("CanGetS").value=""+"需要申请后才可领取"+"";
	 		//-------- end
 		}
 		
 	}
 	if(cCodeName == 'pd_kindcode'){
 		if(Field.value == 'L'){
 			document.getElementById('lifeDefi').style.display = "";
 			document.getElementById('MedicareDefi').style.display = "none";
 		}else if(Field.value == 'H'){	
 			document.getElementById('MedicareDefi').style.display = "";
 			document.getElementById('lifeDefi').style.display = "";
 		}else{
 			document.getElementById('lifeDefi').style.display = "";
 			document.getElementById('MedicareDefi').style.display = "none";
 		}
 	}
 	
 	if(cCodeName == 'LifeType'){		
 		if(Field.value == '2'){
 			document.getElementById('BonusDefi').style.display = '';
 		}else{
 			document.getElementById('BonusDefi').style.display = 'none';
 		}		
 	}
 	//
//	if(cCodeName == 'PD_BonusFlag1'){		
// 		if(Field.value != '0'){
// 			document.getElementById('BonusDefi').style.display = '';
// 		}else{
// 			document.getElementById('BonusDefi').style.display = 'none';
// 		}		
// 	}
 	if(cCodeName == 'PD_BonusFlag'){		
 		if(Field.value != '0'){
 			document.getElementById('BonusDefi').style.display = '';
 		}else{
 			document.getElementById('BonusDefi').style.display = 'none';
 		}		
 	}
 	
 	if(cCodeName == 'pd_gettype3'){
 		if(Field.value != '0'){
	 		fm.all("NeedCancelAcc").value="0";
	 		fm.all("fm.NeedCancelAccS").value=""+"不需要账户结清"+"";
	 	}else if(Field.value != '1'){
	 		
	 	}
 	}
 	
}

function DutyDefi()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
	window.open("PDLMDutyMain.jsp?riskcode=" + document.getElementById("RiskCode").value+"&dutycode=" + Mulline9Grid.getRowColData(selNo-1,4));
}


function QueryPayLib()
{
	window.open("PDDutyPayLibInput.jsp?type=1");
}

function QueryGetLib()
{
	window.open("PDDutyGetLibInput.jsp?type=1");
}



function checkUrgePayFlag()
{
	/*
	var rowCount = Mulline9Grid.mulLineCount;
	
	for(var i = 0; i < rowCount; i++)
 	{
	 	if(Mulline9Grid.getRowColData(i,2) == "URGEPAYFLAG")
	 	{
	 		var sql = "select CPAYFLAG from pd_lmrisk where riskcode = '" + fm.all("RiskCode").value + "'";
			var arr = easyExecSql(sql);	 		
			
			if(arr[0][0] != Mulline9Grid.getRowColData(i,4))
			{
				alert("【催缴标记】的值必须与该险种【续期收费标记】描述一样,\r\n请去产品基础信息录入界面查看");
				return false;
			}
			
			break;
	 	}
 	}
 	*/
 	
 	return true;
}


function checkPay(){

 		if(trim(fm.all('payPlanName').value)==''){
 			myAlert(''+"缴费名称不能为空"+'');
 			return false;
 		}
 		
 		if(trim(fm.all('UrgePayFlag').value)==''){
 			myAlert(''+"催缴标记不能为空"+'');
 			return false;
 		}
 		

 		if(trim(fm.all('payCalType').value)==''){
 			myAlert(''+"算法类型不能为空"+'');
 			return false;
 		}
 		if(fm.all('ISPayStartYear')[0].checked && !isNumeric(fm.all('PayStartYear').value)){
 			myAlert(''+"缴费起始期间处请输入合法数字"+'');
 			fm.all('PayStartYear').focus();
 			return false;
 		}
 		if(fm.all('needAccPay').value==null || fm.all('needAccPay').value==""){
 			myAlert(''+"是否和账户相关不能为空"+'');
 			return false;
 		}

 	return true;
}


function checkGet(){

		if(trim(fm.all('AddAmntFlag').value)==''){
			myAlert(''+"算入保额标记不能为空"+'');
			return false;
		}if(trim(fm.all('getDutyName').value)==''){
			myAlert(''+"给付名称不能为空"+'');
			return false;
		}if(trim(fm.all('type').value)==''){
			myAlert(''+"给付类型不能为空"+'');
			return false;
		}
		if(fm.all('needAccGet').value==null || fm.all('needAccGet').value==""){
 			myAlert(''+"是否和账户相关不能为空"+'');
 			return false;
 		}
	return true;
}

function Paysave(){
	
	fm.all("operator").value="save";
	fm.all("payOrGet").value="Pay";
	
	submitDutyForm();
}

function Payinit()
{
	initMulline9Grid();
	afterRadioM14Select();
	
	fm.all('payPlanCode').value='';
 	fm.all('payPlanName').value='';
 	fm.all('zeroFlag').value='';
 	fm.all('payCalCode').value = '';
 	fm.all('payCalCodeBack').value = '';
 	initDutyPayCalCodeMain('','');
 	initDutyPayCalCodeBackMain('','');
 		
 	fm.all('GracePeriod').value = '';
 	fm.all('PayEndYear').value = '';
 	fm.all('PayIntv').value = '';
 	fm.all('PayStartYear').value = '';
	fm.all('PayStartYearFlag').value = '';
	fm.all('PayStartFlagName').value = '';
	fm.all('PayStartDateCalRef').value = '';
	fm.all('PaySDCalRefName').value = '';
	fm.all('PayStartDateCalMode').value = '';
	fm.all('PaySDCalModeName').value = '';
	fm.all('PayEndYearFlag').value = '';
	fm.all('PayEndYearFlagS').value ='';
	fm.all('PayEndDateCalMode').value = '';
	fm.all('PayEndDateCalModeS').value = '';
	fm.all('UrgePayFlag').value = '';
	fm.all('UrgePayFlagS').value = '';
	fm.all('PayAimClass').value = '';
	fm.all('PayAimClassS').value = '';
	fm.all('needAccPay').value = '';
	fm.all('needAccPayS').value = '';
 
 	fm.all('ZeroFlagS').value ='';
 
 		
 
 	fm.all('payCalType').value= '';
 	fm.payCalTypeS.value='';
 
 
 	fm.all("ISPayStartYear")[1].checked = true;
 	closePayStartCtrl();
}

function Getinit()
{
	initMulline11Grid();
	afterRadioM14Select();
	
	fm.all('AddAmntFlag').value='';
 
 	fm.all('AddAmntFlagS').value='';
 	
 	
 	fm.all('getDutyCode').value='';
 	fm.all('getDutyname').value='';
 	fm.all('type').value='';

 	fm.all('typeS').value ='';
 
 		
 	fm.all('getCalType').value= '';
 	fm.all('getCalCode').value='';
 	fm.all('needAccGet').value='';
 	fm.all('needAccGetS').value='';
 		

 	document.getElementById('divGetAlive').style.display = "none";
 		
 	initDutyGetCalCodeMain('','');

}

function Dutysave(){
	if(fmA.all("GetYearRela").value==''){
		myAlert(""+"起领期间关系不能为空！"+"");	
		return ;
	}
	//-------- add by jucy
	//ADDAMNTFLAG，如果险种是非万能或非投连，则系统自动存储为1；如果险种是万能或投连，则系统自动存储为2
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql34";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(document.getElementById('RiskCode').value);//指定传入的参数
	var riskSQL = mySql.getString();
	var arr = easyExecSql(riskSQL);
	if(arr == "" || arr == null ){
		fmA.all("AddAmntFlagDuty").value = "1";
	}else{
		fmA.all("AddAmntFlagDuty").value = "2";
	}
	//-------- end
	fmA.all("operatorS").value = "save";
	fmA.all("riskCodeS").value = document.getElementById("RiskCode").value;
	submitDutyFormA();
}

function Dutyupdate()
{
	//-------- add by jucy
	//增加修改是是否选择一条责任的校验
	if(Mulline14Grid.getSelNo()< 1){
		myAlert(''+"请先选择一条责任信息"+'');
		return;
	}
	//-------- end	
	//-------- add by jucy
	//ADDAMNTFLAG，如果险种是非万能或非投连，则系统自动存储为1；如果险种是万能或投连，则系统自动存储为2
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql34";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(document.getElementById('RiskCode').value);//指定传入的参数
	var riskSQL = mySql.getString();
	var arr = easyExecSql(riskSQL);
	if(arr == "" || arr == null ){
		fmA.all("AddAmntFlagDuty").value = "1";
	}else{
		fmA.all("AddAmntFlagDuty").value = "2";
	}
	//-------- end
	fmA.all("operatorS").value = "update";
	fmA.all("riskCodeS").value = document.getElementById("RiskCode").value;
	submitDutyFormA();
}

function Dutydel()
{
	//-------- add by jucy
	//增加删除是是否选择一条责任的校验
	if(Mulline14Grid.getSelNo()< 1){
		myAlert(''+"请先选择一条责任信息"+'');
		return;
	}
	//-------- end	
	fmA.all("operatorS").value = "del";
	fmA.all("riskCodeS").value = document.getElementById("RiskCode").value;
	submitDutyFormA();
}


function Payupdate()
{
 if(!checkSame1())
 {
 	return;
 }
 
   fm.all("operator").value="update";
   fm.all("payOrGet").value="Pay";
  submitDutyForm();
}


function Paydel()
{

 if(Mulline9Grid.getSelNo()< 1){
				myAlert(''+"请先选择一条要删除的缴费信息。"+'');
				return;
		}
  fm.all("operator").value="del";
  fm.all("payOrGet").value="Pay";
 submitDutyForm();
}


function Getsave()
{
  fm.all("operator").value="save";
  fm.all("payOrGet").value="Get";
  submitDutyForm();
}

function Getupdate()
{
	/*if(!checkSame2())
	{
		return;
	}*/
	//-------- add by jucy
	//增加点击修改时的校验
	if(Mulline11Grid.getSelNo() <1){
		myAlert(''+"请先选择一条给付记录"+'');
		return;
  	}
	//-------- end
	fm.all("operator").value="update";
	fm.all("payOrGet").value="Get";
	submitDutyForm();
}

function Getdel()
{	
 	if(Mulline11Grid.getSelNo() <1){
		myAlert(''+"请先选择一条给付记录"+'');
		return;
  	}
	fm.all("operator").value="del";
	fm.all("payOrGet").value="Get";
	submitDutyForm();
}

function submitDutyForm(){
	if(document.getElementById("IsReadOnly").value == "1"){
  		myAlert(""+"您无权执行此操作"+"");
  		return;
  	}

  	if( fm.all("payOrGet").value=="Get"){
  		whichForm = 'get';
  		if(!checkGet()){
  			return;
  		}
  	}

  	if( fm.all("payOrGet").value=="Pay"){
  		whichForm = 'pay';
  		if(!checkPay()){
  			return;
  		}
  	}

  	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  	//IE11-大小写	
	//fm.riskCodeS.value = document.getElementById('riskCode').value;
	fm.riskCodeS.value = document.getElementById('RiskCode').value;
  	fm.submit();
}

function submitDutyFormA(){
	if(document.getElementById("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  whichForm = 'A';
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  fmA.submit();
}


function checkMullineNullable(GridObject)
{
  var rowCount = GridObject.mulLineCount;
  for(var i = 0; i < rowCount; i++)
  {
    var PropName = GridObject.getRowColData(i,1);
    var PropValue = GridObject.getRowColData(i,4);
    
  	if(PropName.indexOf("[*]") > -1)
  	{
  		if(PropValue == null || PropValue == "")
  		{
  			PropName = PropName.substring(3,PropName.length);
  		
  			myAlert("\"" + PropName + ""+"\"不能为空"+"");
  			
  			return false;
  		}
  	}
  }
  
  return true;
}

function enterAlgoDefi(so)
{
	if(so == 'pay'){
		 if (document.getElementById("payCalCode").value == null || document.getElementById("payCalCode").value ==''){
			     myAlert(""+"请输入算法编码！"+"");
		 }else{
			// showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + document.getElementById("RiskCode").value+"&AlgoCode="+document.getElementById('payCalCode').value)+"IsReadOnly="+fmF.all("IsReadOnly").value;
		 		//tongmeng 2011-03-01
		 		 var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+document.getElementById("RiskCode").value
             + "&RuleName="+document.getElementById("payCalCode").value
             + "&RuleDes="+document.getElementById("RiskCode").value
             + "&Creator="+mOperator
             + "&CreateModul=1"
             + "&RuleStartDate="+document.getElementById("RequDate").value
             + "&Business=99&State=0";
   			 showInfo = window.open(tURL);
		 }
	}else if (so =='get'){
		if (document.getElementById("getCalCode").value == null || document.getElementById("getCalCode").value ==''){
		    myAlert(""+"请输入算法"+" "+"编码！"+" ");	
		}else{
		   //howInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + document.getElementById("RiskCode").value+"&AlgoCode="+document.getElementById('getCalCode').value)+"IsReadOnly="+fmF.all("IsReadOnly").value;
		    var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+document.getElementById("RiskCode").value
             + "&RuleName="+document.getElementById("getCalCode").value
             + "&RuleDes="+document.getElementById("RiskCode").value
             + "&Creator="+mOperator
             + "&CreateModul=1"
             + "&RuleStartDate="+document.getElementById("RequDate").value
             + "&Business=99&State=0";
   			 showInfo = window.open(tURL);
		}
	}else if (so =='riskPay'){
		if(fmP.all('GraceCalCode').value==null || fmP.all('GraceCalCode').value==""){
			myAlert(""+"无需定义宽限期算法！"+" ");
			return;
		}
		var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+document.getElementById("RiskCode").value
         + "&RuleName="+fmP.all('GraceCalCode').value
         + "&RuleDes="+document.getElementById("RiskCode").value
         + "&Creator="+mOperator
         + "&CreateModul=1"
         + "&RuleStartDate="+document.getElementById("RequDate").value
         + "&Business=99&State=0";
		 showInfo = window.open(tURL);
	}
}	


function checkSame1(){
		var selNo = Mulline9Grid.getSelNo()-1;
		if(selNo==-1){
			myAlert(""+"请先选中需要修改的缴费项责任。"+"");
			return false;
		}

		var dutyCode = Mulline9Grid.getRowColData(selNo,4);
		var payplancode = Mulline9Grid.getRowColData(selNo,2);
		if(selNo > -1){
			//if(dutyCode != fm.all('payDutyCode').value || payplancode != fm.all('payPlanCode').value){
			if(payplancode != fm.all('payPlanCode').value){
				myAlert(''+"请保持文本框记录与所选记录缴费编码和责任代码与一致后再操作"+'');
				return false;
		}
	}
	return true;
}

function checkSame2(){
		var selNo = Mulline11Grid.getSelNo()-1;

		var dutyCode = Mulline11Grid.getRowColData(selNo,4);
		var getplancode = Mulline11Grid.getRowColData(selNo,2);
		if(selNo > -1){
			if(dutyCode != fm.all('dutyCode1').value || getplancode != fm.all('getDutyCode').value){
				myAlert(''+"请保持文本框记录与所选记录给付编码和责任代码一致后再操作"+'');
				return false;
		}
	}
	return true;
}

//查询缴费库后
function afterQueryPayLib(PayPlanCode2){
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql8";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(PayPlanCode2);//指定传入的参数
	var sql = mySql.getString();
	var result = easyExecSql(sql,1,1,1);

	if(result){
	 	// fm.all('payDutyCode').value=result[0][0];
	 	// fm.all('payDutyName').value=result[0][1];
	 //	fm.all('choFlag').value=result[0][2];
	 	fm.all('payPlanCode').value=result[0][0];
	 	fm.all('payPlanName').value=result[0][1];
	 	
	 	fm.all('zeroFlag').value=result[0][2];
	 	var mySql2=new SqlClass();
		var sqlid2 = "PDRiskDefiInputSql9";
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.all('zeroFlag').value);//指定传入的参数
	 	sql =  mySql2.getString();
	 	var resultS =  easyExecSql(sql,1,1,1);
	 	if(resultS){
	 		fm.all('ZeroFlagS').value =resultS[0][0];
	 	}
	 	 		
	 	if (result[0][7] != ""){
	 			fm.all('payCalType').value= '2';
	 		  fm.all('payCalCode').value=result[0][7];
	 		  fm.payCalTypeS.value=''+"保费算保费"+'';
	 		}
	 		else if(result[0][6] != "" ){
	 			fm.all('payCalType').value= '1';
	 			fm.all('payCalCode').value=result[0][6];
	 			fm.payCalTypeS.value=''+"保额算保费"+'';
	 		}else if(result[0][8] != "" ){
	 			fm.all('payCalType').value='3';
	 			fm.all('payCalCode').value=result[0][8];
	 			fm.payCalTypeS.value=''+"其他算保费"+'';
	 		}	
	} 
	
}
function getAliveDif()
{
	if(fm.all('getDutyCode').value==''){
		myAlert(""+"给付代码不能为空，请先保存给付属性定义信息！"+"");
		return false;
	}
  showInfo = window.open("PDDutyGetAliveMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById('RiskCode').value+"&getdutycode="+fm.all('getDutyCode').value+"&requdate="+document.getElementById('RequDate').value);
}

function afterQueryGetLib(getdutycode2){
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql10";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(getdutycode2);//指定传入的参数
	var sql = mySql.getString();
	
	var result = easyExecSql(sql,1,1,1);

	if(result){
	 	//fm.all('dutyCode1').value=result[0][0];
	 	//fm.all('dutyName1').value=result[0][1];
	 	fm.all('AddAmntFlag').value=result[0][2];
	 	var mySql2=new SqlClass();
		var sqlid2 = "PDRiskDefiInputSql11";
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.all('AddAmntFlag').value);//指定传入的参数
	 	sql = mySql2.getString();
	 	resultF = easyExecSql(sql,1,1,1);
	 	if(resultF){
	 				fm.all('AddAmntFlagS').value=resultF[0][0];
	 	}
	 	
	 	fm.all('getDutyCode').value=result[0][3];
	 	fm.all('getDutyname').value=result[0][4];
	 	
	 		
	 		if (result[0][7] != ""){
	 			fm.all('getCalType').value= '2';
	 		  fm.all('getCalCode').value=result[0][7];
	 		  fm.getcaltypeS.value=''+"保费算保费"+'';
	 		}
	 		else if(result[0][6] !=""){
	 			fm.all('getCalType').value= '1';
	 			fm.all('getCalCode').value=result[0][6];
	 			 fm.getcaltypeS.value=''+"保额算保费"+'';
	 		}else if(result[0][8] !=""){
	 			fm.all('getCalType').value='3';
	 			fm.all('getCalCode').value=result[0][8];
	 			 fm.getcaltypeS.value=''+"其他算保费"+'';
	 		}	
	}
}

function showDivAccInsu(){
	document.getElementById("DivAccInsu").style.display = '';
	//document.getElementById('isAccRela').style.display = '';
}

function closeDiveAccInsu(){
	document.getElementById("DivAccInsu").style.display = 'none';
///	document.getElementById('isAccRela').style.display = 'none';
}

function showDivInsuYear(){
	document.getElementById("InsuYearFlagId").style.display = '';
	
}

function closeDivInsuYear(){
	//-------- add by jucy
	fmA.all('InsuYear').value = "";
	fmA.all('InsuYearFlag').value = "";
	fmA.all('InsuYearFlagS').value = "";
	fmA.all('InsuYearRela').value = "";
	fmA.all('InsuYearRelaS').value = "";
	//-------- end
	document.getElementById("InsuYearFlagId").style.display = 'none';
}

function button109()
{
  showInfo = window.open("PDRiskPayIntvMain.jsp?riskcode=" + document.getElementById('RiskCode').value);
}

function button102()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}

  showInfo = window.open("PDRiskParamsDefMain.jsp?riskcode=" + document.getElementById('RiskCode').value + "&type=1&payplancode="+Mulline9Grid.getRowColData(selNo-1,2));
}

function showGetYearPart(){
	document.getElementById("GetYearPartId").style.display = '';
}

function closeGetYearPart(){
	document.getElementById("GetYearPartId").style.display = 'none';
	//-------- add by jucy
	//选否 清空输入框
	fm.all('GetYear').value="";
	fm.all('GetYearFlag1').value="";
	fm.all('GetYearFlag1S').value="";
	fm.all('StartDateCalRef').value="";
	fm.all('StartDateCalRefS').value="";
	fm.all('StartDateCalMode').value="";
	fm.all('StartDateCalModeS').value="";
	//-------- end
}

function showGetEndPeriodPart(){
	document.getElementById("GetEndPeriodPartId").style.display = '';
}

function closeGetEndPeriodPart(){
	document.getElementById("GetEndPeriodPartId").style.display = 'none';
	//-------- add by jucy
	//选否 清空输入框
	fm.all('GetEndPeriod').value="";
	fm.all('GetEndUnit').value="";
	fm.all('GetEndUnitS').value="";
	fm.all('EndDateCalRef').value="";
	fm.all('EndDateCalRefS').value="";
	fm.all('EndDateCalMode').value="";
	fm.all('EndDateCalModeS').value="";
	//-------- end
}

function queryProductElement(content){
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql12";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(content);//指定传入的参数
	var strSql = mySql.getString();
	 
	turnPage.pageDivName = "divPage";
	turnPage.queryModal(strSql, ElementGrid);
	return;
}

function checkInsuFlag()
{
	var insuRadio = fmF.all("InsuAccFlag");
	for(var i=0; i<insuRadio.length; i++){
		if(insuRadio[i].checked){
			return true;
		}
	}
	return false;
}

//显示缴费起期控制栏
function showPayStartCtrl()
{
	document.getElementById("isPayStartCtrl").style.display = '';
}

//隐藏缴费起期控制栏
function closePayStartCtrl()
{
	document.getElementById("isPayStartCtrl").style.display = 'none';
}

//保存(更新/删除)宽限期信息
function submitRiskPay(operate){
	fmP.all('gOperator').value=operate;
	//IE11-大小写
	//fmP.all('gRiskcode').value=document.getElementById("riskCode").value;
	fmP.all('gRiskcode').value=document.getElementById("RiskCode").value;
	if(operate=="save" || operate=="update"){
		if(fmP.all('GracePeriod2').value!=null && fmP.all('GracePeriod2').value!="" && !isNumeric(fmP.all('GracePeriod2').value)){
	 	  	myAlert(''+"缴费宽限期处请输入合法数字"+'');
	 	  	fmP.all('GracePeriod2').focus();
	 		return false;
	 	}
	 	if(fmP.all('GracePeriod2').value!=null && fmP.all('GracePeriod2').value!="" && fmP.all('GracePeriod2').value!="0"){
	 		if(fmP.all('GracePeriodUnit').value==null || fmP.all('GracePeriodUnit').value==""){
	 			myAlert(''+"录入了缴费宽限期后请录入宽限期单位"+'');
	 			return false;
	 		}
	 	}
	 	
	 	if(fmP.UrgePayFlag.value==null || fmP.UrgePayFlag.value==""){
	 		myAlert(''+"请录入催缴标记"+'');
	 		return false;
 		}
  	}
 	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
  	fmP.action="./PDRiskGraceSave.jsp";
  	fmP.submit();
}

function afterSubmitRiskPay(FlagStr, content, Operator){
	showInfo.close();

  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    if(Operator=="del"){
    	initRiskPay();
    }
    else
    {
    	initDutyGraceCalCodeMain(document.getElementById('RiskCode').value,fmP.all('GraceCalCode').value);
    }
  }
}

function queryRiskPay()
{
	var mySql=new SqlClass();
	var sqlid = "PDRiskDefiInputSql13";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(document.getElementById("RiskCode").value);//指定传入的参数
	var sql = mySql.getString();
	var resultP = easyExecSql(sql,1,1,1);
 	if(resultP){
 		fmP.all('GracePeriod2').value=resultP[0][1];
 		fmP.all('GracePeriodUnit').value=resultP[0][2];
 		fmP.all('GPeriodUnitName').value=resultP[0][3];
 		fmP.all('GraceDateCalMode').value=resultP[0][4];
 		fmP.all('GDateCalModeName').value=resultP[0][5];
 		fmP.all('GraceCalCode').value=resultP[0][6];
 		fmP.all('OverdueDeal').value=resultP[0][7];
 		fmP.all('OverdueDealName').value=resultP[0][8];
 		fmP.all('UrgePayFlag').value=resultP[0][9];
 		fmP.all('UrgePayFlagName').value=resultP[0][10];
 		initDutyGraceCalCodeMain(document.getElementById('RiskCode').value,fmP.all('GraceCalCode').value);
 		
 	}
	
}

function showDivProdDis()
{
	document.getElementById("DivProdDis").style.display = '';
}

function closeDivProdDis()
{
	document.getElementById("DivProdDis").style.display = 'none';
}

function defiProdDis(){
	//IE11-大小写
	//showInfo = window.open("PDDiscountDefiMain.jsp?riskcode=" + document.getElementById("riskcode").value + "&requdate=" + document.getElementById("RequDate").value);
	showInfo = window.open("PDDiscountDefiMain.jsp?riskcode=" + document.getElementById("RiskCode").value + "&requdate=" + document.getElementById("RequDate").value);
}

//险种缴费间隔定义录入控制
function riskPayIntvCheck()
{
	var payIntv = fmF.all('payintv');
	for(var i=0; i<payIntv.length; i++){
		if(payIntv[i].checked){
			return true;
		}
	}
	return false;
}

//增加账户型产品的定义
function button116()
{
  showInfo = window.open("PDRiskAccPayMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById("RiskCode").value);
}

function button231()
{
  showInfo = window.open("PDRiskAccGetMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById("RiskCode").value);
}

function button184()
{  
	showInfo = window.open("PDRiskFeeMain.jsp?pdflag="+mpdflag+"&riskcode=" + document.getElementById("RiskCode").value);
}

function defineMultLanguageDuty()
{
	var tDutyName = document.getElementById('DutyCode').value;
	if(tDutyName=='')
	{
		myAlert(""+"责任代码不能为空！"+"");
		return ;
		
	}
	addMsgResult(tDutyName,1,'Duty');
}


function defineMultLanguagePay()
{
	
	var count = Mulline9Grid.getSelNo();// 获取选中的行
	if(count<=0)
	{
		myAlert(''+"请选中一条缴费信息!"+'');
		return false;
	}
	else{
	  var payplancode = Mulline9Grid.getRowColData(count-1,2);// 获取选中行的数据
	  //alert(payplancode);
	  addMsgResult(payplancode,1,'Pay');
	}
	
}


function defineMultLanguageGet()
{
	
   var tGetDutyCode = document.getElementById('getDutyCode').value;
  // alert(tGetDutyCode);
   if(tGetDutyCode==null||tGetDutyCode=='')
   {
   	myAlert(''+"请先选中一条给付责任!"+'');
   	return;
   }
	  //alert(payplancode);
	  addMsgResult(tGetDutyCode,1,'Get');
	
}

function defineMultLanguageRisk()
{
	
   var tRiskCode = document.getElementById('RiskCode').value;
   //alert(tRiskCode);
   
	  //alert(payplancode);
   if(tRiskCode=='')
   {
	   myAlert(""+"险种代码不能为空！"+"");
	   return ;
   }
	  addMsgResult(tRiskCode,1,'Risk');
	
}

function addMsgResult(tKeyID,tEditFlag,tMsgType)
{
	if(tKeyID=='')
	{
		myAlert("KeyId "+"不能为空！"+"");
		return;
	}
	dialogURL="./LDMsgMain.jsp?KeyID="+tKeyID+"&EditFlag="+tEditFlag+"&MsgType="+tMsgType;  
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
	window.open(dialogURL);
}
function depolyScriptDownload()
{
	var selNo = Mulline9Grid.getSelNo();
	var risk='';
	if(selNo == 0)
	{
		risk=''
	}else{
		risk="&RiskCode="+Mulline9Grid.getRowColData(selNo-1,1);

	}
	//fm.action = 'DepolyScriptDownloadQuery.jsp'+risk;
	//fm.submit();
	showInfo = window.open('DepolyScriptDownloadQuery.jsp?Type=1'+risk);

}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('save1').style.display = 'none';
		document.getElementById('saverp').style.display = 'none';
		document.getElementById('save2').style.display = 'none';
		document.getElementById('save3').style.display = 'none';
		document.getElementById('save4').style.display = 'none';//
		document.getElementById('baseInfoDoneId').disabled=true;
		document.getElementById('copyproductbutton').disabled=true;
		//document.getElementById('savabutton1').disabled=true;
		document.getElementById('savabutton2').disabled=true;
		//document.getElementById('savabutton3').disabled=true;
		//document.getElementById('savabutton4').disabled=true;
		//document.getElementById('savabutton5').disabled=true;
		//document.getElementById('savabutton6').disabled=true;
		return;
	}
}
function calCodedeFined(flag,caltype)
{	var riskcode =document.getElementById('RiskCode').value;
	var CalCode,tUrl,type;
	CalCode=document.getElementById(flag).value;
	type=document.getElementById(caltype).value;
	if(CalCode==""){
		myAlert(""+"算法编码不能为空！"+"");
		return ;
	}

	if((type=='Y'&&CalCode.substring(0,2).toUpperCase() != 'RU')||(type=='N'&&CalCode.substring(0,2).toUpperCase() == 'RU')){
		myAlert(""+"算法编码与算法类型一致！"+"");
		return ;
	}
	//晢时校验
	//------------------------------------------------------------------
	if((flag=='PCalCode'||flag=='PCalCodeAmnt')&&CalCode.substring(0,2).toUpperCase() == 'RU')
	{
		myAlert(""+"建议书算法"+" "+"目前不支持规则引擎算法定义！"+"");
		return ;
	}
	//------------------------------------------------------------------
	if(CalCode.substring(0,2).toUpperCase() == 'RU'){
		tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?pdflag="+mpdflag+"&riskcode="+riskcode
        + "&RuleName="+CalCode
        + "&RuleDes="+riskcode
        + "&Creator="+mOperator
        + "&CreateModul=1"
        + "&RuleStartDate="+getCurrentDate("-")
        + "&Business=99&State=0&RuleType=1";	
	}else{
		tUrl = "PDAlgoDefiMain.jsp?pdflag="+mpdflag+"&riskcode=" + riskcode+ "&algocode=" + CalCode;
	}
	showInfo = window.open(tUrl);
}
function setRadioFlag(radioName,flag,num)
{	
	document.all(radioName)[num].checked=true;
	document.getElementById(radioName).value=flag;		
}