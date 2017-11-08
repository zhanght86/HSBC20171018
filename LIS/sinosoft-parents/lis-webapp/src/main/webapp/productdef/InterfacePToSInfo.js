//程序名称：RiskCalDef.js
//程序功能：规则引擎算法控制
//创建日期：2011-10-19
//该文件中包含客户端需要处理的函数和事件
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
var Mulline9GridTurnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass();
var tResourceName = "productdef.RiskCalModeInputSql";
var turnPage = new turnPageClass();
function blockChar(oText){
     sChar = oText.getAttribute("validchar");
     ddd = String.fromCharCode(window.event.keyCode);
     var res = sChar.indexOf(ddd) > -1;
     return res || window.event.ctrlKey;
}
function submitForm()
{
	if(!verifyForm('fm'))
	{
		return false;
	}
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 

  fm.submit();
}
function showList()
{
	var selNo = MullineGrid.getSelNo()-1;
	if(selNo==-1){
		myAlert(""+"请先选择一条记录！"+"");
		return false;
	}
	fm.all("ProductCode").value=MullineGrid.getRowColData(selNo,1);
	fm.all("ProductCodeS").value=MullineGrid.getRowColData(selNo,4);
	fm.all("ProductChName").value=MullineGrid.getRowColData(selNo,3);
	fm.all("ProductEnName").value=MullineGrid.getRowColData(selNo,4);
	fm.all("IProductCode").value=MullineGrid.getRowColData(selNo,2);
	fm.all("IProductChName").value=MullineGrid.getRowColData(selNo,5);
	fm.all("IProductEnName").value=MullineGrid.getRowColData(selNo,6);
	fm.all("EffectiveEndDate").value=MullineGrid.getRowColData(selNo,9);
	fm.all("IsEffectiveDate").value=MullineGrid.getRowColData(selNo,7);
	fm.all("EffectiveDate").value=MullineGrid.getRowColData(selNo,8);
	fm.all("IsSpouseCode").value=MullineGrid.getRowColData(selNo,10);
	fm.all("SpouseCode").value=MullineGrid.getRowColData(selNo,11);
	fm.all("IsStaffCode").value=MullineGrid.getRowColData(selNo,12);
	fm.all("StaffCode").value=MullineGrid.getRowColData(selNo,13);
	fm.all("IsPremPayPeriod").value=MullineGrid.getRowColData(selNo,14);
	fm.all("PremPayPeriodType").value=MullineGrid.getRowColData(selNo,15);
	fm.all("PremPayPeriod").value=MullineGrid.getRowColData(selNo,16);
	fm.all("IsBenefitOption").value=MullineGrid.getRowColData(selNo,17);
	fm.all("BenefitOptionType").value=MullineGrid.getRowColData(selNo,18);
	fm.all("BenefitOption").value=MullineGrid.getRowColData(selNo,19);
	fm.all("IsBenefitPeriod").value=MullineGrid.getRowColData(selNo,20);
	fm.all("BenefitPeriodType").value=MullineGrid.getRowColData(selNo,21);
	fm.all("BenefitPeriod").value=MullineGrid.getRowColData(selNo,22);
	fm.all("IsChannel").value=MullineGrid.getRowColData(selNo,23);
	fm.all("Channel").value=MullineGrid.getRowColData(selNo,24);
	fm.all("IsCurrency").value=MullineGrid.getRowColData(selNo,25);
	fm.all("Currency").value=MullineGrid.getRowColData(selNo,26);
	fm.all("IsPar").value=MullineGrid.getRowColData(selNo,27);
	fm.all("Par").value=MullineGrid.getRowColData(selNo,28);
	fm.all("IsUreUWType").value=MullineGrid.getRowColData(selNo,29);
	fm.all("IsJoinCode").value=MullineGrid.getRowColData(selNo,30);
	fm.all("BatchNo").value=MullineGrid.getRowColData(selNo,31);
	fm.all("JoinCode").value=MullineGrid.getRowColData(selNo,32);
	fm.all("UreUWType").value=MullineGrid.getRowColData(selNo,33);
	showOneCodeName('pd_currency','Currency','CurrencyName');
	showOneCodeName1('yesorno','IsEffectiveDate','IsEffectiveDateS');
	showOneCodeName1('yesorno','IsSpouseCode','IsSpouseCodeS');
	showOneCodeName1('yesorno','IsStaffCode','IsStaffCodeS');
	showOneCodeName1('yesorno','IsPremPayPeriod','IsPremPayPeriodS');
	showOneCodeName1('yesorno','IsBenefitOption','IsBenefitOptionS');
//	showOneCodeName1('yesorno','WithoutPolicyIssued','WithoutPolicyIssuedS');
	showOneCodeName1('yesorno','IsBenefitPeriod','IsBenefitPeriodS');
	showOneCodeName1('yesorno','IsChannel','IsChannelS');
	showOneCodeName1('yesorno','IsCurrency','IsCurrencyS');
	showOneCodeName1('yesorno','IsPar','IsParS');	
	
	
	showOneCodeName1('yesorno','IsUreUWType','IsUreUWTypeS');
	showOneCodeName1('yesorno','IsJoinCode','IsJoinCodeS');
	showOneCodeName2('pars','Par','ParS');
	showOneCodeName1('riskperiodunit','PremPayPeriodType','PremPayPeriodTypeS');
	showOneCodeName1('benefitoptiontype','BenefitOptionType','BenefitOptionTypeS');
	showOneCodeName1('riskperiodunit','BenefitPeriodType','BenefitPeriodTypeS');
	showOneCodeName1('needflag','SpouseCode','SpouseCode');
	showOneCodeName1('needflag','StaffCode','StaffCodeS');
	showOneCodeName1('needflag','JoinCode','JoinCodeS');
	showOneCodeName1('uwtype3','UreUWType','UreUWTypeS');
	showOneCodeName1('pd_salechnl','Channel','ChannelS');
	
	
	
	query2();
}
function query3(){
var sqlid = "InterfacePToSInfoQuerysql4";
	var mySql=new SqlClass();
	mySql.setSqlId(sqlid);
	mySql.setResourceName(tResourceName);
	mySql.addSubPara(fm.all("ProductCode").value);
	var strSQL = mySql.getString();
	try{
		var algos = easyExecSql(strSQL,1,1,1);		
		if(algos == null)return "";
		fm.all("ProductChName").value=algos[0][0];
		fm.all("ProductEnName").value=algos[0][1];		
	}catch(ex){}	
}
function query2()
{
var mRiskCode = fm.all("ProductCode").value;
var mPlanCode = fm.all("IProductCode").value;	
var sqlid = "InterfacePToSInfoQuerysql5";
var mySql=new SqlClass();
mySql.setSqlId(sqlid);
mySql.setResourceName(tResourceName);
mySql.addSubPara(mRiskCode);
mySql.addSubPara(mPlanCode);

var strSQL = mySql.getString();

turnPage1.pageLineNum  = 10;
turnPage1.queryModal(strSQL,SubRiskMullineGrid);

}

function showOneCodeName1(type,m1,m2)
{
	try{
	if(type==''||m1==''||m2=='')return;
	if(type=='yesorno'&&fm.all(m1).value=='Y'){
		fm.all(m2).value=''+"是"+'';
	}else if(type=='yesorno'&&fm.all(m1).value=='N'){
		fm.all(m2).value=''+"否"+'';
	}else if(type=='riskperiodunit'&&fm.all(m1).value=='A'){
		fm.all(m2).value=''+"岁"+'';
	}else if(type=='riskperiodunit'&&fm.all(m1).value=='Y'){
		fm.all(m2).value=''+"年"+'';
	}else if(type=='benefitoptiontype'&&fm.all(m1).value=='A'){
		fm.all(m2).value=''+"保额"+'';
	}else if(type=='benefitoptiontype'&&fm.all(m1).value=='D'){
		fm.all(m2).value=''+"责任类型"+'';
	}else if(type=='benefitoptiontype'&&fm.all(m1).value=='P'){
		fm.all(m2).value=''+"保障计划"+'';
	}else if(type=='needflag'&&fm.all(m1).value=='0'){
		fm.all(m2).value=''+"否"+'';
	}else if(type=='needflag'&&fm.all(m1).value=='1'){
		fm.all(m2).value=''+"是"+'';
	}else if(type=='needflag'&&fm.all(m1).value=='E'){
		fm.all(m2).value=''+"简易核保"+'';
	}else if(type=='needflag'&&fm.all(m1).value=='F'){
		fm.all(m2).value=''+"免核保"+'';
	}else if(type=='needflag'&&fm.all(m1).value=='N'){
		fm.all(m2).value=''+"正常核保"+'';
	//[Start] Kathy modify to retrieve pd_salechnl name thru I18N
		/*	
	}else if(type=='pd_salechnl'&&fm.all(m1).value=='03'){
		fm.all(m2).value='Bancassurance';
	}else if(type=='pd_salechnl'&&fm.all(m1).value=='08'){
		fm.all(m2).value='Brokerage';
	}else if(type=='pd_salechnl'&&fm.all(m1).value=='04'){
		fm.all(m2).value='Telemarketing';
	}else if(type=='pd_salechnl'&&fm.all(m1).value=='11'){
		fm.all(m2).value='Corporate Distribution';
	}*/
	}else if(type=='pd_salechnl') {
		fm.all(m2).value=''+I18NMsg("ldcode_" + type + "_" + fm.all(m1).value)+'';
	}
	//[End] Kathy
	}catch(ex){}
}
function showOneCodeName3(type,m1,m2)
{
	try{
	if(type==''||m1==''||m2=='')return;
	if(type=='benefitoptiontype'&&fm.all(m1).value=='A'){
		fm.all(m2).value=''+"保额"+'';
	}else if(type=='benefitoptiontype'&&fm.all(m1).value=='D'){
		fm.all(m2).value=''+"责任类型"+'';
	}else if(type=='benefitoptiontype'&&fm.all(m1).value=='P'){
		fm.all(m2).value=''+"保障计划"+'';
	}
	}catch(ex){}
}
function showOneCodeName2(type,m1,m2)
{
	try{
	if(type==''||m1==''||m2=='')return;
	if(type=='pars'&&fm.all(m1).value=='Y'){
		fm.all(m2).value=''+"清缴不分红"+'';
	}else if(type=='pars'&&fm.all(m1).value=='N'){
		fm.all(m2).value=''+"正常分红"+'';
	}
	}catch(ex){}
}
function afterSubmit( FlagStr, content )
{
  if(showInfo != null)
  {
	  showInfo.close();
  }

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }else if(FlagStr == "Successs"){
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
  }
  query();
}

function query()
{
	
var sqlid = "InterfacePToSInfoQuerysql3";
var mySql=new SqlClass();
mySql.setSqlId(sqlid);
mySql.setResourceName(tResourceName);
mySql.addSubPara(fm.all("QProductCode").value);
mySql.addSubPara(fm.all("QIProductCode").value);
if(fm.all("CIsEffectiveDate").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsSpouseCode").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsStaffCode").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsPremPayPeriod").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsBenefitPeriod").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsBenefitOption").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsChannel").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsCurrency").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
if(fm.all("CIsPar").checked==true){
	mySql.addSubPara("Y");
}else{
	mySql.addSubPara("");
}
var strSQL = mySql.getString();
turnPage.pageLineNum  = 10;
turnPage.queryModal(strSQL,MullineGrid);

}
function modify()
{
	fm.all("Operator").value = "modify";
	  submitForm(); 
}
function save()
  {
	var sqlid = "InterfacePToSInfoQuerysql4";
	var mySql=new SqlClass();
	mySql.setSqlId(sqlid);
	mySql.setResourceName(tResourceName);
	mySql.addSubPara(fm.all("ProductCode").value);
	var strSQL = mySql.getString();
	try{
		var algos = easyExecSql(strSQL,1,1,1);		
		if(algos == null)return "";
		fm.all("ProductChName").value=algos[0][0];
		fm.all("ProductEnName").value=algos[0][1];		
	}catch(ex){}
	fm.all("Operator").value = "save";
  submitForm(); 
  }
function del()
  {
	  fm.all("Operator").value = "del"; 
	  submitForm();  
  }
function afterCodeSelect( cCodeName, Field )
{	try{
	if(cCodeName=='pd_lmriskinfo'){
		query2();
		query3();
	}
}catch(ex){}
}