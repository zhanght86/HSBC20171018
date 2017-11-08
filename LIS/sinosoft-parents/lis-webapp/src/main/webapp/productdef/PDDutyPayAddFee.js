//程序名称：PDDutyPayAddFee.js
//程序功能：险种责任加费定义
//创建日期：2009-3-13
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var tResourceName = "productdef.PDDutyPayAddFeeInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{
	/*
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
  		
  			alert("\"" + PropName + "\"不能为空");
  			
  			return false;
  		}
  	}
  }
  */
   if(!verifyInput())
  {
  	return false;
  }  
  return true;
}

function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
  
	if(!checkNullable())
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
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
     if(fm.all("operator").value!="del")
    {
    	InputCalCodeDefFace('99');
  	}
  	if(fm.all("operator").value=="del")
  	{
  		initDetail();
  	}
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
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
function button135()
{
  var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中算法行再点击"+"");
		return;
	}
	else
	{
		var calcode = Mulline10Grid.getRowColData(selNo-1,5); 
		if( calcode==null||calcode=="" )
		{
			myAlert(""+"必须录入算法编码"+"");
			return;
		}
	}
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&algocode=" + Mulline10Grid.getRowColData(selNo-1,5));
}	
		
function afterQueryAlgo(AlgoCode)
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"选中行的焦点丢失"+"");
		return;
	}
	
	Mulline9Grid.setRowColDataCustomize(selNo-1,4,AlgoCode);
}
function button136()
{
  showInfo = window.open("PDRateCashValueMain.jsp?riskcode=" + fm.all("RiskCode").value+"&payplancode="+fm.all("PayPlanCode").value);
}
function button137()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
  // strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMDutyPayAddFee') and isdisplay = '1' order by displayorder";Mulline9GridTurnPage.pageLineNum  = 3215;
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyPayAddFeeInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("0");//指定传入的参数
   strSQL = mySql.getString();
    //tongmeng 2011-07-13 modify
   // 
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
}
function queryMulline10Grid()
{
   var strSQL = "";
   //strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMDutyPayAddFee') and isdisplay = '1' order by displayorder";Mulline10GridTurnPage.pageLineNum  = 3215;
   //strSQL = "select RISKCODE,DUTYCODE,ADDFEETYPE,ADDFEEOBJECT,ADDFEECALCODE,ADDPOINTLIMIT from PD_LMDutyPayAddFee where dutycode = '"+fm.all('DutyCode').value +"' and riskcode = '"+fm.all('RiskCode').value+"'";

   var mySql=new SqlClass();
	 var sqlid = "PDDutyPayAddFeeInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all('DUTYCODE').value);//指定传入的参数
	 mySql.addSubPara(fm.all('RISKCODE').value);//指定传入的参数
	
   strSQL = mySql.getString();
 // alert(strSQL);
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryDutyCode()
{
	var strSQL = "";
	//strSQL = "select dutycode from PD_LMDutypay where payplancode = '"+fm.all('PayPlanCode').value +"'";
	  var mySql=new SqlClass();
	 var sqlid = "PDDutyPayAddFeeInputSql4";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all('PayPlanCode').value);//指定传入的参数
	// mySql.addSubPara(fm.all('RISKCODE').value);//指定传入的参数
	
   strSQL = mySql.getString();
  // alert(strSQL);
	tnumber = easyExecSql(strSQL);
	fm.all('DUTYCODE').value=tnumber[0];
}

function initDetail()
{
	try{

		document.getElementById('ADDFEETYPE').value="";
		document.getElementById('ADDFEEOBJECT').value="";
		document.getElementById('ADDFEECALCODE').value="";
		document.getElementById('ADDPOINTLIMIT').value="";
		
		document.getElementById('ADDFEETYPEName').value="";
		document.getElementById('ADDFEEOBJECTName').value="";
		var radio = fm.all("CalCodeSwitchFlag");

		radio[0].checked = true;
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
	document.getElementById('ADDFEECALCODE').value = tCalCode;
}



function getOneData()
{
	var selNo = Mulline10Grid.getSelNo()-1;
	//alert(selNo);
	if(selNo>=0){

		var RISKCODE = Mulline10Grid.getRowColData(selNo,1);
		var DUTYCODE = Mulline10Grid.getRowColData(selNo,2);
		var ADDFEETYPE = Mulline10Grid.getRowColData(selNo,3);
		var ADDFEEOBJECT = Mulline10Grid.getRowColData(selNo,4);
	
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyPayAddFeeInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(RISKCODE);//指定传入的参数
	 mySql.addSubPara(DUTYCODE);//指定传入的参数
	  mySql.addSubPara(ADDFEETYPE);//指定传入的参数
	  mySql.addSubPara(ADDFEEOBJECT);//指定传入的参数
	   
   strSQL = mySql.getString();
   //Mulline9GridTurnPage.pageLineNum  = 3215;
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
    var tContent = easyExecSql(strSQL);
    
    
    
    //
   var mySql2=new SqlClass();
	 var sqlid2 = "PDDutyPayAddFeeInputSql1";
	 mySql2.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	 mySql2.addSubPara('0');//指定传入的参数
	
	   
   var sql = mySql2.getString();
 
 var rowcode = easyExecSql(sql,1,1,1);
    
    for(i=0;i<rowcode.length;i++)
    {
    	try{
    	var tCodeName = rowcode[i][1];
    	var tValue = tContent[0][i];
    	document.getElementById(tCodeName).value = tValue;
    	}
    	catch(ex)
    	{
    		 
    	}
    	
    }
    
   	document.getElementById('ADDFEETYPEName').value="";
		document.getElementById('ADDFEEOBJECTName').value="";
		
    showOneCodeName('pd_addfeetype','ADDFEETYPE','ADDFEETYPEName');
    showOneCodeName('pd_addfeeobject','ADDFEEOBJECT','ADDFEEOBJECTName');
   
  //  alert(document.getElementById('RISKCODE').value+":"+document.getElementById('ADDFEECALCODE').value);
    
    initCalCodeMain(document.getElementById('RISKCODE').value,document.getElementById('ADDFEECALCODE').value);
 
	}
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('savabuttonid').style.display = 'none';
		document.getElementById('checkFunc').style.display = '';
	}else{
		document.getElementById('savabuttonid').style.display = '';
		document.getElementById('checkFunc').style.display = 'none';
	}

}
//-------- add by jucy
function InputCalCodeDefFace2(){
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请先选择一条算法信息，再点击【查看算法内容】。"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end



