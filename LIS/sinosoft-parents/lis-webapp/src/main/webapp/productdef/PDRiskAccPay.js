//程序名称：PDRiskAccPay.js
//程序功能：账户型险种定义
//创建日期：2009-3-14
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDRiskAccPayInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{
  var GridObject = Mulline9Grid;
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

function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
	  	myAlert(""+"您无权执行此操作"+"");
	  	return;
	}
	if(!verifyInput()){
		return false;
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
	    initForm(); 
	    if(fm.all("operator").value!="del")
	    {
	    	//InputCalCodeDefFace('99');
	  	}
	  	
	  	if(fm.all("operator").value=="del")
	  	{
	  		initDetail();
	  	}
	} 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
var Mulline12GridTurnPage = new turnPageClass(); 
function save()
{
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}
function button184()
{  
	showInfo = window.open("PDRiskFeeMain.jsp?riskcode=" + fm.RISKCODE.value);
}
function button185()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccPayInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("0");//指定传入的参数
   strSQL = mySql.getString();
    var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   	for(i=0;i<arrInfo.length;i++)
   	{
   		try{
   		var tFieldName = arrInfo[i][1];
   		var tTitle = arrInfo[i][4];
   		if(tTitle!=null&&tTitle!='')
   		{
   			// tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('title',\"" + newData + "\")";
   			document.getElementById(tFieldName).title = tTitle;
   		}
   		}
   		catch(Ex)
   		{
   			}
   	}
   }
   
   //Mulline9GridTurnPage.pageLineNum  = 3215;
  // Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccPayInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

function queryMulline11Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccPayInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
function queryMulline12Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccPayInputSql6";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数s
   strSQL = mySql.getString();
   Mulline12GridTurnPage.pageLineNum  = 3215;
   Mulline12GridTurnPage.queryModal(strSQL,Mulline12Grid);
}
function getOneData12()
{
	var selNo = Mulline12Grid.getSelNo()-1;
	fm.all("PayPlanCode000").value=Mulline12Grid.getRowColData(selNo,2);
	fm.all("PayPlanCodeName000").value=Mulline12Grid.getRowColData(selNo,3);
	fm.all("AccountCode000").value=Mulline12Grid.getRowColData(selNo,4);
	fm.all("AccountCodeName000").value=Mulline12Grid.getRowColData(selNo,5);
	fm.all("AccountType000").value=Mulline12Grid.getRowColData(selNo,6);
	showOneCodeName('pd_accounttype','AccountType000','AccountTypeName000');
	}
function getOneData()
{
	var selNo = Mulline10Grid.getSelNo()-1;
	
	if(selNo>=0){
		var RiskCode = Mulline10Grid.getRowColData(selNo,1);
		var insuAccNo = Mulline10Grid.getRowColData(selNo,2);
		var payPlanCode = Mulline10Grid.getRowColData(selNo,6);
		var RISKACCPAYNAME = Mulline10Grid.getRowColData(selNo,8);
   	fm.PAYPLANCODE.value =payPlanCode;
   	showOneCodeName('pd_payplancode','PAYPLANCODE','PAYPLANNAME');
 	fm.INSUACCNO.value=insuAccNo;
 	fm.RISKACCPAYNAME.value =RISKACCPAYNAME;
   	var strSQL = "";
  	
 	var mySql=new SqlClass();
	var sqlid = "PDRiskAccPayInputSql5";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数
	mySql.addSubPara(fm.all("INSUACCNO").value);//指定传入的参数
   	strSQL = mySql.getString();
  	var temp = easyExecSql(strSQL,1,1,1);
	if(insuAccNo!="000000"){  	
  		document.getElementById('INSUACCNOName').value = temp[0][2];
  		
  	}else{
  		document.getElementById('INSUACCNOName').value ="000000";
  	}

		mySql=new SqlClass();
		sqlid = "PDRiskAccPayInputSql7";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(fm.all("PAYPLANCODE").value);//指定传入的参数
	   	strSQL = mySql.getString();
	  	temp = easyExecSql(strSQL,1,1,1);
  		document.getElementById('PAYPLANNAME').value = temp[0][0];
   	
   
	}
}



function initDetail()
{
	try{

		document.getElementById('PAYPLANCODE').value="";
		document.getElementById('INSUACCNO').value="";
		//document.getElementById('RISKCODE').value="";
		//document.getElementById('DEFAULTRATE').value="";
		//document.getElementById('NEEDINPUT').value="";
		//document.getElementById('CALCODEMONEY').value="";
		//document.getElementById('PAYNEEDTOACC').value="";
		//document.getElementById('CALFLAG').value="";
		//document.getElementById('ACCCREATEPOS').value="";
		document.getElementById('PAYPLANNAME').value="";
		
		document.getElementById('RISKACCPAYNAME').value="";
		document.getElementById('ASCRIPTION').value="";

		
		
		//document.getElementById('ACCCREATEPOSName').value="";
		//document.getElementById('CALFLAGName').value="";
		document.getElementById('INSUACCNOName').value="";
		document.getElementById('ASCRIPTIONName').value="";
		//document.getElementById('NEEDINPUTName').value="";
		//document.getElementById('PAYNEEDTOACCName').value="";
		
		initMulline10Grid();
		queryMulline10Grid();
	}
	catch(Ex)
	{
		}
}

function setCalCode(tCalCode)
{
//	initCalCodeMain(document.getElementById('RISKCODE').value,tCalCode);	
//	document.getElementById('CALCODEMONEY').value = tCalCode;
}
function reset()
{
	fm.all("PayPlanCode000").value="";
	fm.all("PayPlanCodeName000").value="";
	fm.all("AccountCode000").value="";
	fm.all("AccountCodeName000").value="";
	fm.all("AccountType000").value="";
	fm.all("AccountTypeName000").value="";
}

function resetfund()
{
	fm.all("PayPlanCode000").value="";
	fm.all("PayPlanCodeName000").value="";
	fm.all("AccountCode000").value="";
	fm.all("AccountCodeName000").value="";
	fm.all("AccountType000").value="";
	fm.all("AccountTypeName000").value="";
}
function hand(operator1)
{
	//alert(fm.all("AccountCode000").value.length);
	if(fm.all("AccountCode000").value.length>=7){
		myAlert(""+"账户缴费编码应小于7位"+"");
			return;
		}
	fm.all("operator").value=operator1;
	if("save"==operator1){
		if(fm.all("AccountCode000").value==""||fm.all("AccountCodeName000").value==""){
			myAlert(""+"请输入必录项"+"");
			return;
			}
		}
	fm.action="./PDRiskAccFundSave.jsp";
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 	 
	fm.submit();
}
function afterSubmit1( FlagStr, content )
{
	showInfo.close();         
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('savabuttonid1').style.display = 'none';
		document.getElementById('savabuttonid2').style.display = 'none';
	//document.getElementById('savabutton1').disabled=true;
	}else{
		document.getElementById('savabuttonid1').style.display = '';
		document.getElementById('savabuttonid2').style.display = '';
	}
}