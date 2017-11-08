

//程序名称：PDContDefiEntry.js
//程序功能：契约信息定义入口
//创建日期：2009-3-13
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDContDefiEntryInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content ){
	showInfo.close();
	if (FlagStr == "Fail" ){             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		//-------- add by jucy
		//添加新契约录入完毕后关闭页面，初始化父菜单查询刷新，
		//添加简易产品录入完毕后关闭页面，初始化父菜单查询刷新
		if(fm.all("IsDealWorkflow").value == "1" && fm.all("operator").value == "cont"){
			top.opener.queryDefing();
			top.close();
		}else if(fm.all("operator").value == "simplecont" && fm.all("SimpleContPara").value == "111"){
			top.opener.queryDefing();
			top.close();
		}
		//-------- end
		
		initForm();
	}
}
var Mulline9GridTurnPage = new turnPageClass(); 
function button100()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRateCashValueMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button101()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}	
  showInfo = window.open("PDDutyPayMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}

//-------- add by jucy
//新增【员工折扣比例定义功能
function staffrateDefi(){
	//IE11-大小写
	//showInfo = window.open("PD_RiskStaffRateMain.jsp?RiskCode=" + fm.all("riskcode").value+"&pdflag="+ fm.all("PdFlag").value);
	showInfo = window.open("PD_RiskStaffRateMain.jsp?RiskCode=" + fm.all("RiskCode").value+"&pdflag="+ fm.all("PdFlag").value);
}

//-------- end


function button102()
{
//	var selNo = Mulline9Grid.getSelNo();
	
//  showInfo = window.open("PDRiskParamsDefMain.jsp?riskcode=" + fm.all("RiskCode").value + "&type=1&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));

	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskParamsDefMain.jsp?riskcode=" + fm.all("RiskCode").value + "&pdflag="+fm.all("PdFlag").value+"&type=1&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));

}
function button103() // 与102同
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskParamsDefMain.jsp?riskcode=" + fm.all("RiskCode").value + "&type=2&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button104()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDDutyPayAddFeeMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3)+"&pdflag="+ fm.all("PdFlag").value);
}
function button105()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskDutyFactorMain.jsp?riskcode=" + fm.all("RiskCode").value+"&pdflag="+ fm.all("PdFlag").value+"&payplancode="+Mulline9Grid.getRowColData(selNo-1,3));
}
function button106()
{
  showInfo = window.open("PDRiskRoleMain.jsp?riskcode=" + fm.all("RiskCode").value+"&pdflag="+ fm.all("PdFlag").value);
}
function button107()
{
  showInfo = window.open("PDRiskPrintMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button108()
{
  showInfo = window.open("PDRiskPayMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button109()
{
  showInfo = window.open("PDRiskPayIntvMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button110()
{
  showInfo = window.open("PDCheckFieldMain.jsp?riskcode=" + fm.all("RiskCode").value+"&Type=TB");
}
function button111()
{
  showInfo = window.open("PDUMMain.jsp?riskcode=" + fm.all("RiskCode").value+"&Type=TB"+"&pdflag="+ fm.all("PdFlag").value);
}

function pubUWRule()
{
  showInfo = window.open("PDUMMain.jsp?riskcode=000000&Type=TB&pdflag="+ fm.all("PdFlag").value);
}

function button112()
{
  showInfo = window.open("");
}
function button113()
{
  showInfo = window.open("");
}
function button114()
{
  showInfo = window.open("PDSaleControlMain.jsp?flag=0&riskcode=" + fm.all("RiskCode").value+"&pdflag="+ fm.all("PdFlag").value);
}
function button115()
{
  showInfo = window.open("PDRiskRelaMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button116()
{
  showInfo = window.open("PDRiskAccPayMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button117()
{
	//-------- update by jucy 
	//增加+"&requdate="+fm.all("RequDate").value)，解决初始化日期为null问题。
	showInfo = window.open("PDRiskDefiMain.jsp?LoadFlagButton=0&pdflag=0&riskcode=" + fm.all("RiskCode").value+"&requdate="+fm.all("RequDate").value);
	//-------- end
}
function button118()
{
  showInfo = window.open("");
}
function button119()
{
  fm.all("IsDealWorkflow").value = "1";
  fm.all("operator").value = "cont";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

  submitForm();
  
}
function button120()
{window.open("PDRiskFeeMain.jsp");
	window.opener=null;
window.open("","_self");
window.close(); 

       
}
function button121()
{
  fm.all("operator").value = "simplecont";
  fm.all("SimpleContPara").value = "111";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  submitForm();
}
function button122()
{
  showInfo = window.open("PDContDefiConfigInput.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button123()
{
  showInfo = window.open("PDContDefiPayInput.jsp?riskcode=" + fm.all("RiskCode").value);
}
function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDContDefiEntryInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function IssueQuery(){
	//IE11-大小写
	//showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("riskcode").value +"&postcode=10&issuetype=0"+"&pdflag="+fm.all("PdFlag").value);
	showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + fm.all("RiskCode").value +"&postcode=10&issuetype=0"+"&pdflag="+fm.all("PdFlag").value);
}

// modify by nicole 将MissionID等参数传递到问题件录入页面
function IssueInput(){
	//IE11-大小写
	//showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("riskcode").value +"&pdflag="+fm.all("PdFlag").value+ "&postcode=10&issuetype=0&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);
	showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + fm.all("RiskCode").value +"&pdflag="+fm.all("PdFlag").value+ "&postcode=10&issuetype=0&missionid="+fm.all("MissionID").value+"&submissionid="+fm.all("SubMissionID").value+"&activityid="+fm.all("ActivityID").value);

}

function showButtons(riskCode){
	var mySql=new SqlClass();
	var sqlid = "PDContDefiEntryInputSql2";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(riskCode);//指定传入的参数
	var strSql = mySql.getString();
	
	var crr = easyExecSql(strSql);
	var insuAccFlag = crr[0][0];
	//当账户标记为N时，隐藏账户型险种定义按钮
	if(insuAccFlag == 'N'){
//		document.getElementById('btnRiskAcc').style.display = 'none';
	}
	
	var mySql2=new SqlClass();
	var sqlid2 = "PDContDefiEntryInputSql3";
	mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(riskCode);//指定传入的参数
	strSql = mySql2.getString();
	var crr2 = easyExecSql(strSql);
	var riskProp = crr2[0][0];
	
	//if(riskProp == 'I'){
		//document.getElementById('riskPropId').style.display = 'none';
	//}
	
	//if(riskProp == 'G'){
		//document.getElementById('btnPayFieldCtrl').style.display = 'none';
	//}
}
function queryLMDutyParam(){
	var mySql=new SqlClass();
	var sqlid = "PDContDefiEntryInputSql4";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
	var strSql = mySql.getString();
	turnPage.queryModal(strSql,LMDutyParamGrid);
}

function button82(){
	//IE11-大小写
	//showInfo = window.open("PDRiskSortDefiMain.jsp?riskcode=" + fm.all("riskcode").value + "&pdflag="+ fm.all("PdFlag").value+"&requdate=" + fm.all("RequDate").value);
	showInfo = window.open("PDRiskSortDefiMain.jsp?riskcode=" + fm.all("RiskCode").value + "&pdflag="+ fm.all("PdFlag").value+"&requdate=" + fm.all("RequDate").value);
}

function buttonRiskFace(){
	//IE11-大小写
	//showInfo = window.open("PD_RiskFaceDefMain.jsp?RiskCode=" + fm.all("riskcode").value+"&StandbyFlag1=01");
	showInfo = window.open("PD_RiskFaceDefMain.jsp?RiskCode=" + fm.all("RiskCode").value+"&StandbyFlag1=01");
}

function commission(){
	//IE11-大小写
	//showInfo = window.open("PD_RiskCommissionMain.jsp?RiskCode=" + fm.all("riskcode").value+"&pdflag="+ fm.all("PdFlag").value);
	showInfo = window.open("PD_RiskCommissionMain.jsp?RiskCode=" + fm.all("RiskCode").value+"&pdflag="+ fm.all("PdFlag").value);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('savabuttonid').style.display = 'none';
	document.getElementById('savabutton1').disabled=true;
	document.getElementById('savabutton2').disabled=true;
	document.getElementById('savabutton3').disabled=true;
	}

}
function button127()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("PDRiskParamsDetailDefMain.jsp?riskcode=" + fm.all("RiskCode").value+"&pdflag="+fm.all("PdFlag").value+"&dutycode="+Mulline9Grid.getRowColData(selNo-1,3));
}


function button130(){
	showInfo = window.open("../sys/PD_PrintConfigMain.jsp?flag=0&riskcode=" + fm.all("RiskCode").value+"&pdflag="+fm.all("PdFlag").value);
}


//================================modify by pangyingjie 2015/07/03 险种名称配置  start===========================
function button128()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条险种缴费责任"+" "+"！"+"");
		return;
	}
  showInfo = window.open("RiskSaleNameMain.jsp?flag=0&riskcode=" + fm.all("RiskCode").value+"&pdflag="+fm.all("PdFlag").value+"&dutycode="+Mulline9Grid.getRowColData(selNo-1,3));
}
//================================modify by pangyingjie 2015/07/03 险种名称配置  end===========================

function buttonIncome(){
	//IE11-大小写
	//showInfo = window.open("PDSugIncomeDataMain.jsp?riskcode=" + fm.all("riskcode").value +"&contopt="+fm.all("ContOpt").value );
	showInfo = window.open("PDSugIncomeDataMain.jsp?riskcode=" + fm.all("RiskCode").value +"&contopt="+fm.all("ContOpt").value );
}

function buttonStatic(){
	//IE11-大小写
	//showInfo = window.open("PDSugStaticDocumentMain.jsp?riskcode=" + fm.all("riskcode").value );
	showInfo = window.open("PDSugStaticDocumentMain.jsp?riskcode=" + fm.all("RiskCode").value );
}

function buttonRate(){
	//IE11-大小写
	//showInfo = window.open("PDSugRateMain.jsp?riskcode=" + fm.all("riskcode").value +"&contopt="+fm.all("ContOpt").value );
	showInfo = window.open("PDSugRateMain.jsp?riskcode=" + fm.all("RiskCode").value +"&contopt="+fm.all("ContOpt").value );
}

function buttonSugFace(){
	//IE11-大小写
	//showInfo = window.open("PD_RiskFaceDefMain.jsp?RiskCode=" + fm.all("riskcode").value+"&StandbyFlag1=02&contopt="+fm.all("ContOpt").value );
	showInfo = window.open("PD_RiskFaceDefMain.jsp?RiskCode=" + fm.all("RiskCode").value+"&StandbyFlag1=02&contopt="+fm.all("ContOpt").value );
}

