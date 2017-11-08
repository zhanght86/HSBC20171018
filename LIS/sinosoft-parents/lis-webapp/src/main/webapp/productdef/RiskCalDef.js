
//程序功能：规则引擎算法控制
//创建日期：2011-10-19
//该文件中包含客户端需要处理的函数和事件
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
//var Mulline9GridTurnPage = new turnPageClass(); 
var tResourceName = "productdef.RiskCalModeInputSql";
var turnPage = new turnPageClass();
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
	fm.all("CalModeType").value=MullineGrid.getRowColData(selNo,1);
	fm.all("NewCalModeType").value=MullineGrid.getRowColData(selNo,1);
	fm.all("CalCode").value=MullineGrid.getRowColData(selNo,2);
	fm.all("RiskCode").value=MullineGrid.getRowColData(selNo,3);
	fm.all("OldCalCode").value=MullineGrid.getRowColData(selNo,2);
	fm.all("Des").value=MullineGrid.getRowColData(selNo,4);
	fm.all("ID").value=MullineGrid.getRowColData(selNo,6);
	showOneCodeName('calmodetype','CalModeType','CalModeTypeS');
	showOneCodeName('calmodetype','NewCalModeType','NewCalModeTypeS');
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
  initForm();
}
function query()
{
	 ///var riskcode = document.getElementById('riskcode').value;
	 var mySql=new SqlClass();
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 var sqlid = "PDUMInputSql1";
	 var CalModeType=fm.all("CalModeType").value;
	 var CalCode=fm.all("CalCode").value;
	 var riskcode=fm.all("RiskCode").value;
	 //-------- add by jucy 
	 //增加算法编码和险种编码不能同时为空校验
	 if(CalCode.trim()==""&&riskcode.trim()==""){
	 	myAlert(""+"请至少输入【算法编码】或【险种编码】进行查询！"+"");
	 	return false;
	 }
	 //-------- end
	if(riskcode=='old'){
		sqlid = "PDUMInputSql2";
		 mySql.addSubPara("");
	}else if(CalCode.substring(0,1)=="#"){
		CalCode=CalCode.substring(1,CalCode.length);
		sqlid = "PDUMInputSql5";
		mySql.addSubPara(CalCode);
	}else{
		sqlid = "PDUMInputSql1";
		 mySql.addSubPara(CalCode);
		 mySql.addSubPara(CalModeType);
		 mySql.addSubPara(riskcode);
		 mySql.addSubPara(CalCode);
		 mySql.addSubPara(CalModeType);
		 mySql.addSubPara(riskcode);
		 mySql.addSubPara(CalCode);
		 mySql.addSubPara(CalModeType);
		 mySql.addSubPara(riskcode);

	}
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
//指定传入的参数
//	 mySql.addSubPara(document.getElementById('DutyCode').value);//指定传入的参数
   var strSQL = mySql.getString();
   turnPage.pageLineNum  = 10;
   turnPage.queryModal(strSQL,MullineGrid);

}
function save()
  {fm.all("Operator").value = "save";
  submitForm(); 
  }
function del()
  {
	  fm.all("Operator").value = "del";
	  submitForm();  
  }
function def()
{
	var selNo = MullineGrid.getSelNo()-1;
	if(selNo==-1){
		myAlert(""+"请先选择一条记录！"+"");
		return false;
	}
	var RiskCode=MullineGrid.getRowColData(selNo,3);
	var CalCode=MullineGrid.getRowColData(selNo,2);
	var tUrl = "";
	if(CalCode.substring(0,2)=='RU')
	{
		tUrl= "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+RiskCode
    + "&RuleName="+CalCode
    + "&RuleDes="+RiskCode
    + "&Creator=1"
     + "&CreateModul=1"
    + "&Business=99&State=0&RuleType=1&pdflag=1";
	showInfo = window.open(tUrl);
	}
  else
	{
		//tUrl= "../productdef/PDAlgoDefiMain.jsp?riskcode="+RiskCode+"&AlgoCode="+CalCode+"";
	}	
}
function def_sql()
{
	var selNo = MullineGrid.getSelNo()-1;
	if(selNo==-1){
		myAlert(""+"请先选择一条记录！"+"");
		return false;
	}
	var RiskCode=MullineGrid.getRowColData(selNo,3);
	var CalCode=MullineGrid.getRowColData(selNo,2);
	var tUrl = "";
	if(CalCode.substring(0,2)=='RU')
	{
		
	}
  else
	{
		tUrl= "../productdef/PDAlgoDefiMain.jsp?riskcode="+RiskCode+"&pdflag=1&AlgoCode="+CalCode+"";
	showInfo = window.open(tUrl);
	}	
}
function def_sql_data()
{
	var selNo = MullineGrid.getSelNo()-1;
	if(selNo==-1){
		myAlert(""+"请先选择一条记录！"+"");
		return false;
	}
	var RiskCode=MullineGrid.getRowColData(selNo,3);
	var CalCode=MullineGrid.getRowColData(selNo,2);
	var tUrl = "";
	if(CalCode.substring(0,2)=='RU')
	{
		
	}
  else
	{
		//http://192.168.2.104:8084/bi/productdef/PDRateCashValueMain.jsp?riskcode=IBU03&payplancode=BU0301
		tUrl= "../productdef/PDRateCashValueMain.jsp?riskcode="+RiskCode+"&pdflag=1&payplancode=";
	showInfo = window.open(tUrl);
	}	
}
function showbotton()
{
	if(document.getElementById("CalCodeCopyDiv").style.display == ""){
	document.getElementById("CalCodeCopyDiv").style.display = "none";
	}else{
		document.getElementById("CalCodeCopyDiv").style.display = "";

	}
}
function copy()
{
	  fm.all("Operator").value = "copy";
	  submitForm();
}
function afterCodeSelect( cCodeName, Field )
{
	 var CalModeType=fm.all("CalModeType").value;
	 if(CalModeType=='PC'||CalModeType=='SC'){
		 fm.all("RiskCode").value='000000';
	 }else if(CalModeType=='RC'&&fm.all("RiskCode").value=='000000'){
		 
		 fm.all("RiskCode").value='';
		 
	 }
}
function deploy()
{
	  fm.all("Operator").value = "deploy";
	  submitForm();
}
function modify()
{
	  fm.all("Operator").value = "modify";
	  submitForm();
}