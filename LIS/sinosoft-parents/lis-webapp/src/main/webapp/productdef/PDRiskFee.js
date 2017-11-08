//程序名称：PDRiskFee.js
//程序功能：账户管理费定义
//创建日期：2009-3-14
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDRiskFeeInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
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
 var selNo = Mulline10Grid.getSelNo();
 if( selNo ==0 || selNo == null )
 {
 	myAlert(""+"请选择一条记录！"+"");
 	return;
 }		
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 var selNo = Mulline10Grid.getSelNo();
 if( selNo ==0 || selNo == null )
 {
 	myAlert(""+"请选择一条记录！"+"");
 	return;
 }		
 fm.all("operator").value="del";
 submitForm();
}
function button179()
{
  showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button183()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql1";
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
   //Mulline9GridTurnPage.pageLineNum  = 3215;
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function queryMulline10Grid()
{
	 var mySql=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql2";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all("RiskCode").value);//指定传入的参数
   var strSQL = mySql.getString();
//   alert('strSQL:'+strSQL);
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

function getOneData()
{
	var selNo = Mulline10Grid.getSelNo()-1;
	//alert(selNo);
	if(selNo < 0)
	{
	
	}
	else
	{
	    initCalCodeMain(mJSRiskCode,Mulline10Grid.getRowColData(selNo,7));
		var FEECODE = Mulline10Grid.getRowColData(selNo,1);
		var insuAccNo = Mulline10Grid.getRowColData(selNo,2);
		var PAYPLANCODE = Mulline10Grid.getRowColData(selNo,8);
		var FEECALMODE = Mulline10Grid.getRowColData(selNo,6);
		/*
		PDRiskFeeInputSql3_0= and FEECODE= ''{0}'' 
PDRiskFeeInputSql3_1= and PAYPLANCODE= ''{0}'' 
PDRiskFeeInputSql3_1= and INSUACCNO= ''{0}'' 
PDRiskFeeInputSql3_1= and FEECALMODE= ''{0}'' 
		*/
	var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDRiskFeeInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(FEECODE);//指定传入的参数
	 mySql.addSubPara(PAYPLANCODE);//指定传入的参数
	  mySql.addSubPara(insuAccNo);//指定传入的参数
	  mySql.addSubPara(FEECALMODE);//指定传入的参数
	   
   strSQL = mySql.getString();
   //Mulline9GridTurnPage.pageLineNum  = 3215;
   //Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
    var tContent = easyExecSql(strSQL);
    
    
    
    //
   var mySql2=new SqlClass();
	 var sqlid2 = "PDRiskFeeInputSql1";
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
    
   	document.getElementById('INSUACCNOName').value="";
	document.getElementById('PAYPLANCODEName').value="";
	document.getElementById('FEEKINDName').value="";
	document.getElementById('FEECALMODEName').value="";
	document.getElementById('FEEITEMTYPEName').value="";
	document.getElementById('FEETAKEPLACEName').value="";
	document.getElementById('FEEPERIODName').value="";
	document.getElementById('FEECALMODETYPEName').value="";
	
    showOneCodeName('pd_riskinsuacc','INSUACCNO','INSUACCNOName');
    if(fm.INSUACCNO.value !="000000"){
    	var mySqlk=new SqlClass();
    	mySqlk.setResourceName(tResourceName); //指定使用的properties文件名
	 	mySqlk.setSqlId("PDRiskFeeInputSql6");//指定使用的Sql的id
	 	mySqlk.addSubPara(fm.INSUACCNO.value);//指定传入的参数
	 	var strSQLk = mySqlk.getString();
	 	var tContentk = easyExecSql(strSQLk);
	 	fm.INSUACCNOName.value =tContentk[0][0];
    }
    showOneCodeName('pd_dutypaygetcode','PAYPLANCODE','PAYPLANCODEName');
    showOneCodeName('pd_feekind','FEEKIND','FEEKINDName');
	showOneCodeName('pd_feecalmode','FEECALMODE','FEECALMODEName');
	showOneCodeName('pd_feeitemtype','FEEITEMTYPE','FEEITEMTYPEName');
	showOneCodeName('pd_feetakeplace','FEETAKEPLACE','FEETAKEPLACEName'); 
    showOneCodeName('pd_feeperiod','FEEPERIOD','FEEPERIODName');
    showOneCodeName('pd_feecalmodetype','FEECALMODETYPE','FEECALMODETYPEName');
    
    
   /* 
    
    for(i=0;i<Mulline9Grid.mulLineCount;i++)
    {
    	Mulline9Grid.setRowColData(i, 4, tContent[0][i], Mulline9Grid);

    }
   
   */
	}
}

function initDetail()
{
	try{
		fm.CalCodeSwitchFlag[0].checked=true;
 		fm.CalCodeSwitchFlag[1].checked=false;
		document.getElementById('INSUACCNO').value="";
		document.getElementById('PAYINSUACCNAME').value="";
		document.getElementById('FEECODE').value="";
		document.getElementById('FEENAME').value="";
		document.getElementById('PAYPLANCODE').value="";
		document.getElementById('FEEKIND').value="";
		document.getElementById('FEECALMODE').value="";
		document.getElementById('FEEITEMTYPE').value="";
		document.getElementById('FEETAKEPLACE').value="";
		document.getElementById('FEEPERIOD').value="";
		document.getElementById('FEESTARTDATE').value="";
		document.getElementById('FEECALMODETYPE').value="";
		document.getElementById('FEECALCODE').value="";
		
		document.getElementById('INSUACCNOName').value="";
		document.getElementById('PAYPLANCODEName').value="";
		document.getElementById('FEEKINDName').value="";
		document.getElementById('FEECALMODEName').value="";
		document.getElementById('FEEITEMTYPEName').value="";
		document.getElementById('FEETAKEPLACEName').value="";
		document.getElementById('FEEPERIODName').value="";
		document.getElementById('FEECALMODETYPEName').value="";
		document.getElementById('ShowFEESTARTDATE').value="";
		
		initMulline10Grid();
		queryMulline10Grid();
	}
	catch(Ex)
	{
		}
}

function setCalCode(tCalCode)
{
	initCalCodeMain(mJSRiskCode,tCalCode);	
	document.getElementById('FEECALCODE').value = tCalCode;
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
		myAlert(""+"请先选择一条账户管理费信息，再点击【查看算法内容】。"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end