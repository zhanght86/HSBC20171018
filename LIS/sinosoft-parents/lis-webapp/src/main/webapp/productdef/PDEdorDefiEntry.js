//程序名称：PDEdorDefiEntry.js
//程序功能：保全信息定义
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDEdorDefiEntryInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}

function submitForm() 
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  //var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  lockPage(""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"");
  
  fm.submit();
}
function submitFormNoCheck()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }

  //var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  lockPage(""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"");
  fm.submit();
}


function afterSubmit( FlagStr, content )
{
  //showInfo.close();
	unLockPage();
	if (FlagStr == "Fail" ){             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		if(fm.all("operator").value=="savecal"){
    		InputCalCodeDefFace('99');
    		var selNo = Mulline10Grid.getSelNo();
			if(selNo>0){
				var tEdorType = Mulline10Grid.getRowColData(selNo-1,2);
				document.getElementById('EdorType').value = tEdorType;
				getEdorItem();
			}
  		}else if(fm.all("operator").value=="delcal"){
			initEdorDetail('1');
		//-------- add by jucy
		//添加点击保全信息定义完毕后的，关闭页面，刷新父页面功能。
		}else if(fm.all("operator").value == "edor"){
			top.opener.queryDefing();
			top.close();
		//-------- end 
		}else{
    		initForm();    
    	}
	} 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function saveEdorItem()
{
 fm.all("operator").value="saveedoritem";
 fm.action = "./PDEdorDefiEntrySave.jsp";
 submitForm();
}

function saveCAL()
{
 if(!verifyInput())
  {
  	return false;
  }
 fm.all("operator").value="savecal";
 fm.action = "./PDEdorDefiEntrySave.jsp?edortype="+document.getElementById('EdorType').value;
 submitForm();
}

function NotNull(vName, strValue)
{
	if (strValue == "")
	{
		myAlert(vName + ""+"不能为空！\n"+"");
		return false;
	}
	return true;
}
function defineCal()
{
	var selNo = Mulline11Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中一条已添加的算法再点击"+"");
		return;
	}
	var tCalCode=Mulline11Grid.getRowColData(selNo-1,2);
	// 根据保全类型，打开对应定义页面
	//var tCalCode = Mulline9Grid.getRowColData(selNo-1,3);
	//window.open("PDEdorCalMain.jsp?riskcode=" + fm.all("RiskCode").value+"&edortype="+tEdorType);
	//window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&AlgoCode="+tCalCode);
	
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDEdorDefiEntryInputSql5";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
	 var arrResult = easyExecSql(strSQL);
	 if(arrResult!=null)
	 {
	 //tongmeng 2011-03-01 modify
		 var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+document.getElementById("RiskCode").value
             + "&RuleName="+tCalCode
             + "&RuleDes="+tCalCode
             + "&Creator="+mOperator
              + "&CreateModul=1"
             + "&RuleStartDate="+arrResult[0][0]
             + "&Business=99&State=0";
   			 showInfo = window.open(tURL);
   	}
   	else
   	{
   		myAlert(''+"获取产品申请时间出错!"+'');
   	}
}

function defineDetail()
{
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中一条已添加的保全项再点击"+"");
		return;
	}
	// 根据保全类型，打开对应定义页面
	var tEdorType = Mulline10Grid.getRowColData(selNo-1,2);
	if(tEdorType=="ZT"||tEdorType=="CT"){
		window.open("PDEdorZTMain.jsp?riskcode=" + fm.all("RiskCode").value+"&edortype="+tEdorType);
	}else if (tEdorType=="CF"){
		window.open("PDEdorWTMain.jsp?riskcode=" + fm.all("RiskCode").value+"&edortype="+tEdorType);	
	}else if (tEdorType=="PL"){
		window.open("PDLoanMain.jsp?riskcode=" + fm.all("RiskCode").value+"&edortype="+tEdorType);
	}else{
		//window.open("PDEdorCalMain.jsp?riskcode=" + fm.all("RiskCode").value+"&edortype="+tEdorType);
		myAlert(""+"该保全项目不需执行该项定义！"+"");
	}
	//window.open("PDEdorZTAccMain.jsp?riskcode=" + fm.all("RiskCode").value);
}

function button203()
{
  showInfo = window.open("");
}
function button204()
{
  showInfo = window.open("");
}
function button205()
{
  showInfo = window.open("");
}
function btnEdorDone()
{
  var alStr = checkRules("11");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

	fm.all("operator").value = "edor";
	fm.action = "./PDContDefiEntrySave.jsp";
	submitFormNoCheck();
}
function button207()
{
  showInfo = window.open("PDRiskBonusMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button208()
{
  showInfo = window.open("PDRiskAddAmntMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button209()
{
  showInfo = window.open("PDEdorGAMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button210()
{
  showInfo = window.open("");
}

function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDEdorDefiEntryInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
   
}
function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDEdorDefiEntryInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
   
}
function queryMulline11Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDEdorDefiEntryInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline11GridTurnPage.pageLineNum  = 10;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
   
}
function IssueQuery()
{
  showInfo = window.open("PDIssueQueryMain.jsp?riskcode=" + document.getElementById("RiskCode").value + "&pdflag="+document.getElementById("PdFlag").value +"&postcode=11&issuetype=0");
}
function IssueInput()
{
  showInfo = window.open("PDIssueInputMain.jsp?riskcode=" + document.getElementById("RiskCode").value + "&pdflag="+document.getElementById("PdFlag").value +"&postcode=11&issuetype=0&missionid="+document.getElementById("MissionID").value+"&submissionid="+document.getElementById("SubMissionID").value+"&activityid="+document.getElementById("ActivityID").value);
}

function searchDetail()
{
	//-------- update by jucy
	//基础信息查看页面显示为null
	window.open("PDRiskDefiMain.jsp?LoadFlagButton=0&pdflag=0&riskcode=" + fm.all("RiskCode").value+"&requdate="+fm.all("RequDate").value);
	//-------- end
}

function button110()
{
		var selNo = Mulline10Grid.getSelNo();
	if(selNo<=0)
	{
		myAlert(''+"请先选择需要定义规则的保全项目"+'');
		return false;
	}
	else
	{
		var tEdorType = Mulline10Grid.getRowColData(selNo-1,2);
  	showInfo = window.open("PDCheckFieldMain.jsp?riskcode=" + fm.all("RiskCode").value+"&pdflag="+fm.all("PdFlag").value +"&Type=EDOR&EdorType="+tEdorType);
	}
}

function button111()
{
	var selNo = Mulline10Grid.getSelNo();
	if(selNo<=0)
	{
		myAlert(''+"请先选择需要定义规则的保全项目"+'');
		return false;
	}
	else
	{
		var tEdorType = Mulline10Grid.getRowColData(selNo-1,2);
  	showInfo = window.open("PDUMMain.jsp?riskcode=" + fm.all("RiskCode").value+"&pdflag="+fm.all("PdFlag").value +"&Type=EDOR&EdorType="+tEdorType);
  }
}

function getEdorItem()
{
	var selNo = Mulline10Grid.getSelNo();
	if(selNo>0)
	{
		
		
		
	var mySql=new SqlClass();
	var sqlid = "PDEdorDefiEntryInputSql4";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
//	mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
	mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
	mySql.addSubPara(Mulline10Grid.getRowColData(selNo-1,2));//指定传入的参数
	var strSQL = mySql.getString();
	Mulline9GridTurnPage.pageLineNum  = 10;
	Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
	initEdorDetail();
	document.getElementById('EdorType').value = Mulline10Grid.getRowColData(selNo-1,2);
	document.getElementById('allDefEdorCal').innerHTML=''+"保全项目:"+''+Mulline10Grid.getRowColData(selNo-1,2)+""+"已定义算法"+"";
	showDefineDetail(document.getElementById('EdorType').value);
	showCycPayIntvType(document.getElementById('EdorType').value);
	
	
	
}
}



function showCycPayIntvType(type)
{
	if(type=='CT'){
		document.getElementById('CycPayIntvTypeSelID').style.display = '';
		document.getElementById('CycPayIntvTypeSelID1').style.display = '';
		CycPayIntvTypeSelID1
		document.getElementById('OthersCTID').style.display = '';
	}else{
		document.getElementById('CycPayIntvTypeSelID').style.display = 'none';
		document.getElementById('OthersCTID').style.display = 'none';
		document.getElementById('CycPayIntvTypeSelID1').style.display = 'none';
	}
	
}
function getCalCode()
{
	var selNo = Mulline9Grid.getSelNo();
	fm.all('hideEdorType').value = Mulline9Grid.getRowColData(selNo-1,1);
	fm.all('hideCalType').value = Mulline9Grid.getRowColData(selNo-1,2);
	fm.all('hideCalCode').value = Mulline9Grid.getRowColData(selNo-1,3);
}
function getEdorType()
{
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		Mulline9Grid.delBlankLine("Mulline9Grid");
		myAlert(""+"请选中一条已添加的保全项再点击"+"");
		return;
	}
	for(var i=0;i<Mulline9Grid.mulLineCount;i++){
  	Mulline9Grid.setRowColData(i,1,Mulline10Grid.getRowColData(selNo-1,2));
  }
}

function setEdorDetail()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		
		return;
	}
	else
	{
		document.getElementById('EdorType').value = Mulline9Grid.getRowColData(selNo-1,1);
		document.getElementById('DutyCodeName').value = Mulline9Grid.getRowColData(selNo-1,9);
		document.getElementById('DutyCode').value = Mulline9Grid.getRowColData(selNo-1,2);
		document.getElementById('EdorCalType').value = Mulline9Grid.getRowColData(selNo-1,4);
		document.getElementById('CalCode').value = Mulline9Grid.getRowColData(selNo-1,5);
		document.getElementById('CalDescibe').value = Mulline9Grid.getRowColData(selNo-1,8);
		document.getElementById('EdorCalTypeName').value = '';
		document.getElementById('DutyCode1').value = document.getElementById('DutyCode').value;
		
	
		displayCalDescibe(document.getElementById('EdorCalType').value);
	
		var  tRiskCode=fm.all("RiskCode").value;
		initCalCodeMain(tRiskCode,Mulline9Grid.getRowColData(selNo-1,5));
		showOneCodeName('edorcaltype','EdorCalType','EdorCalTypeName');
		showOneCodeName('pd_caldescibe','CalDescibe','CalDescibeName');
		var tCalCode=Mulline9Grid.getRowColData(selNo-1,5);
		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
		{
			document.all("CalCodeSwitchFlag")[0].checked =true;
		}else{
			document.all("CalCodeSwitchFlag")[1].checked =true;
			
		}
	}
}

function displayCalDescibe(EdorCalType){
	if(EdorCalType=="CashValue"){
		$('#CalDescibeDivA').css('display','block'); 
		$('#CalDescibeDivB').css('display','block'); 
	}else{
		$('#CalDescibeDivA').css('display','none'); 
		$('#CalDescibeDivB').css('display','none'); 
		document.getElementById('CalDescibe').value = ''
		document.getElementById('CalDescibeName').value = ''
	}
}

function initEdorDetail(tVar)
{
		document.getElementById('EdorType').value = '';
		document.getElementById('EdorCalType').value = '';
		document.getElementById('CalCode').value = '';  
		document.getElementById('DutyCode').value = '';
		document.getElementById('DutyCodeName').value = '';
		document.getElementById('CalDescibe').value = '';
		document.getElementById('EdorCalTypeName').value = '';
		document.getElementById('CalDescibe').value = '';
		document.getElementById('CalDescibeName').value = '';
		
		
		document.getElementById('DutyCode1').value = '';
		document.getElementById('CycPayIntvType').value = '';
		document.getElementById('CycPayIntvTypeName').value = '';
		initCalCodeMain('','');
		var selNo = Mulline10Grid.getSelNo();
	if(selNo>0)
	{
		var tEdorType = Mulline10Grid.getRowColData(selNo-1,2);
		document.getElementById('EdorType').value = tEdorType;
		if(tVar!=null&&tVar=='1')
		{
			getEdorItem();
		}
	}
}

function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CalCode').value = tCalCode;
}

function delCAL()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中一条已定义的保全算法再点击"+"");
		return;
	}


 fm.all("operator").value="delcal";
 fm.action = "./PDEdorDefiEntrySave.jsp?edortype="+Mulline9Grid.getRowColData(selNo-1,1);
 submitForm();
}
function showDefineDetail(type)
{
	if(type==null||type=='null'||type=='undefined')return ;
	//if(type=='ZT'||type=='CT'){
	//	document.getElementById('defineDetailDivZT').style.display = '';
	//	document.getElementById('defineDetailDivCF').style.display = 'none';
	//	document.getElementById('defineDetailDivPL').style.display = 'none';
	//	}
	//else 
	
	if(type=='CF'){
		document.getElementById('defineDetailDivZT').style.display = 'none';
		document.getElementById('defineDetailDivCF').style.display = '';
		document.getElementById('defineDetailDivPL').style.display = 'none';
	}
	else if(type=='PL'){
		document.getElementById('defineDetailDivZT').style.display = 'none';
		document.getElementById('defineDetailDivCF').style.display = 'none';
		document.getElementById('defineDetailDivPL').style.display = '';
	}else{
		document.getElementById('defineDetailDivZT').style.display = 'none';
		document.getElementById('defineDetailDivCF').style.display = 'none';
		document.getElementById('defineDetailDivPL').style.display = 'none';
	}

}
function isshowbutton()
{   
	var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('savabuttonid').style.display = 'none';
		document.getElementById('savabutton1').disabled=true;
		document.getElementById('savabutton2').disabled=true;
		document.getElementById('savabutton3').disabled=true;
		document.getElementById('checkFunc').style.display = '';
	}else{
		document.getElementById('savabuttonid').style.display = '';
		document.getElementById('savabutton1').disabled=false;
		document.getElementById('savabutton2').disabled=false;
		document.getElementById('savabutton3').disabled=false;
		document.getElementById('checkFunc').style.display = 'none';
	}
}

//-------- add by jucy
function InputCalCodeDefFace2(){
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请先选择一条算法信息，再点击【查看算法内容】。"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end