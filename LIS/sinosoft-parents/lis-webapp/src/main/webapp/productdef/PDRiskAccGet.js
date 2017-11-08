//程序名称：PDRiskAccGet.js
//程序功能：给付账户定义
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var tResourceName = "productdef.PDRiskAccGetInputSql";
var Mulline11GridTurnPage = new turnPageClass(); 
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

function submitForm()
{
if(fm.IsReadOnly.value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
	if(!verifyInput())
	{
		return false;
	}
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
     if(fm.operator.value!="del")
    {
    	InputCalCodeDefFace('99');
  	}
  	if(fm.operator.value=="del")
  	{
  		initDetail();
  	}
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function save()
{
 fm.operator.value="save";
 submitForm();
}
function update()
{
 fm.operator.value="update";
 submitForm();
}
function del()
{
 fm.operator.value="del";
 submitForm();
}
function button265()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
  /* var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMRiskAccGet') and isdisplay = '1' order by displayorder";Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);*/
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccGetInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara('0');//指定传入的参数
   strSQL = mySql.getString();
   //Mulline9GridTurnPage.pageLineNum  = 3215;
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
   
  // Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{/*
   var strSQL = "";
   strSQL = "";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
   */
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccGetInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数
   strSQL = mySql.getString();
   //Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

function queryMulline11Grid()
{
	//alert('1');
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskAccGetInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数
   strSQL = mySql.getString();
   //alert(strSQL);
   //Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}

function getOneData()
{
	var selNo = Mulline10Grid.getSelNo()-1;
	if(selNo>=0){
		var RiskCode = Mulline10Grid.getRowColData(selNo,3);
		var insuAccNo = Mulline10Grid.getRowColData(selNo,2);
		var payPlanCode = Mulline10Grid.getRowColData(selNo,1);
		fm.RISKCODE.value =	RiskCode;
		fm.INSUACCNO.value =insuAccNo;
		fm.GETDUTYCODE.value=payPlanCode;
	    var strSQL = "";
	    var mySql=new SqlClass();
		var sqlid = "PDRiskAccGetInputSql5";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(fm.RISKCODE.value);//指定传入的参数
		mySql.addSubPara(fm.INSUACCNO.value);//指定传入的参数
	   	strSQL = mySql.getString();
	  	var temp = easyExecSql(strSQL,1,1,1);
		document.getElementById('INSUACCNOName').value = temp[0][2];
		
	  	var strSQL1 = "";
	    var mySql1=new SqlClass();
		var sqlid1 = "PDRiskAccGetInputSql6";
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.GETDUTYCODE.value);//指定传入的参数
	   	strSQL1 = mySql1.getString();
	   	var temp1 = easyExecSql(strSQL1);
  		fm.GETDUTYNAME.value=temp1[0][0];
	}
}


function initDetail()
{
	try{

//

		document.getElementById('GETDUTYCODE').value="";
		document.getElementById('INSUACCNO').value="";
		//document.getElementById('RISKCODE').value="";
		document.getElementById('DEFAULTRATE').value="";
		document.getElementById('NEEDINPUT').value="";
		document.getElementById('CALCODEMONEY').value="";
		document.getElementById('DEALDIRECTION').value="";
		document.getElementById('CALFLAG').value="";
		document.getElementById('ACCCREATEPOS').value="";
		document.getElementById('GETDUTYNAME').value="";
		
		document.getElementById('INSUACCNOName').value="";
		document.getElementById('NEEDINPUTName').value="";
		document.getElementById('DEALDIRECTIONName').value="";
		document.getElementById('CALFLAGName').value="";
		document.getElementById('ACCCREATEPOSName').value="";
		
		fm.CalCodeSwitchFlag[0].checked=true;
 		fm.CalCodeSwitchFlag[1].checked=false;
		
		initMulline10Grid();
		queryMulline10Grid();
	}
	catch(Ex)
	{
		}
}

function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RISKCODE').value,tCalCode);	
	document.getElementById('CALCODEMONEY').value = tCalCode;
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	document.getElementById('savabuttonid').style.display = 'none';
	}

}