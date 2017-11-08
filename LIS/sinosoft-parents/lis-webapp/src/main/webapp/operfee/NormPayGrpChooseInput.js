
var turnPage = new turnPageClass();
var showInfo;
var importPath;
var importConfigFile;

// 普通缓存查询
function easyQueryClick() {
/*	
  var strSql = "select SysVar, SysVarType, SysVarValue from ldsysvar where 1=1 "
	       + getWherePart( 'SysVar' )
	       + getWherePart( 'SysVarType' )
	       + getWherePart( 'SysVarValue' );
*/
  var strSql="";
  //strSql="select Prem,PaytoDate,ManageFeeRate,GrpName,GrpContNo from LCGrpPol where GRPCONTNO='"+document.all('GrpPolNo').value+"' and payintv=-1";   
  strSql=wrapSql('LCGrpPol1',[document.all('GrpPolNo').value]);
  var arrResult=easyExecSql(strSql);
  if(arrResult==null)
  {
  alert("无符合条件的集体保单：请确认是续期不定期缴费");
  NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid"); 
  return false;	
  }
  document.all('SumDuePayMoney').value=arrResult[0][0]||"";
  document.all('PaytoDate').value=arrResult[0][1]||"";
  document.all('ManageFeeRate').value=arrResult[0][2]||"";
  document.all('GrpName').value=arrResult[0][3]||"";  
  //保存团体保单号 ljspayperson表中的GRPCONTNO不能为空
  document.all('GrpContNo').value = arrResult[0][4];
  
  //strSql = "select LCPrem.ContNo,LCPrem.DutyCode,LCPrem.PayPlanCode,(select payplanname from lmdutypay where payplancode=lcprem.payplancode ),LCPol.InsuredName,LCPrem.Prem,LJSPayPerson.Sumactupaymoney,LJSPayPerson.InputFlag,decode(LCPol.InsuredSex,0,'男',1,'女',2,'不祥',LCPol.InsuredSex),LCPol.InsuredBirthday from LCPrem,LJSPayPerson,LCPol where LCPol.GRPCONTNO='"+document.all('GrpPolNo').value+"' ";
  //strSql =strSql+" and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";			
  //strSql =strSql+" and LCPrem.PolNo=LJSPayPerson.PolNo";
  //strSql =strSql+" and LCPrem.PolNo=LCPol.PolNo";
  //strSql =strSql+" and LCPrem.DutyCode=LJSPayPerson.DutyCode";
  //strSql =strSql+" and LCPrem.PayPlanCode=LJSPayPerson.PayPlanCode";
  //strSql =strSql+" and LCPol.appflag='1'";
  //strSql =strSql+" and LCPol.paytodate<LCPol.payenddate and LJSPayPerson.paytype in ('ZC','TM')";
  //strSql =strSql+" "+getWherePart('lcpol.PolNo','PolNo1','like');   
  
  strSql=wrapSql('LCPrem1',[document.all('GrpPolNo').value,document.all('PolNo1').value]);
  
  /*
  strSql =strSql+" UNION ";
  strSql =strSql+"select LCPrem.PolNo,LCPrem.DutyCode,LCPrem.PayPlanCode,LMDutyPay.PayPlanName,LCPol.InsuredName,LCPrem.Prem,LCPrem.Prem ,'0',decode(LCPol.InsuredSex,0,'男',1,'女',2,'不祥',LCPol.InsuredSex),LCPol.InsuredBirthday,lcpol.SequenceNo from LCPrem,lcpol,LMDutyPay where LCPrem.GrpPolNo='"+document.all('GrpPolNo').value+"' ";
  strSql =strSql+" and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";
  strSql =strSql+" and LCPrem.PolNo=LCPol.PolNo";		
  strSql =strSql+" and LCPrem.PayPlanCode=LMDutyPay.PayPlanCode";
  strSql =strSql+" and 0=(select count(*) from LJSPayPerson where PolNo=LCPrem.PolNo and DutyCode=LCPrem.DutyCode and PayPlanCode=LCPrem.PayPlanCode)";
  strSql =strSql+" and LCPol.appflag='1'";
  strSql =strSql+" and LCPol.paytodate<LCPol.payenddate";
  strSql =strSql+" "+getWherePart('lcpol.PolNo','PolNo1','like');
  */
  //strSql =strSql+" order by 11,5,3 "	
  
    

	//查询SQL，返回结果字符串
  //prompt('',strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid");  	
    alert("没有查询到数据！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = NormPayGrpChooseGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function fmSubmit()
{
    if(checkValue())
    {   
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.getElementById("fm").submit();    
    }
    	
}

function ToExcel()
{
    if(fmFileImport.GrpPolNo1.value=="")
    {
    	alert("请输入要团单号!");
    	return;
    }
    var GrpPolNo1=fmFileImport.GrpPolNo1.value;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
  parent.fraInterface.fmToExcel.action = "./NormPayToExcel.jsp?GrpPolNo1="+GrpPolNo1
  ;
  document.getElementById("fmToExcel").submit();    	
}

function checkValue()
{
   if(!verifyInput())
     return false;
  
   return true;
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
   
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//    NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid");   
  }
}

function afterSubmit( FlagStr, content ,operator,file1)
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(operator=="download")
	{
  		parent.fraInterface.fmToExcel.action = "./download.jsp?file="+file1;
  		document.getElementById("fmToExcel").submit(); 
  	}
  }

}



//磁盘导入
function fileImport()
{
if(fmFileImport.FileName.value=="")
  {
  	alert("请上传Excel文件！");
  	return ;
  }
  getImportPath();
  getConfigFileName();
// var importConfigFile="ExcelImportNormPayCollConfig.xml";
 
 fmFileImport.ImportConfigFile.value=importConfigFile;

 var GrpPolNo1=fmFileImport.GrpPolNo1.value;
 //var GrpContNo=document.all('GrpContNo').value;
 
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  var importFile = fmFileImport.FileName.value;
  parent.fraInterface.fmFileImport.action = "./FileImportNormPayCollSaveAll.jsp?ImportPath="+importPath
  +"&ImportFile="+importFile
  +"&ImportConfigFile="+importConfigFile
  +"&GrpPolNo1="+GrpPolNo1
  +"&GrpContNo="+GrpPolNo1
  ;
 document.getElementById("fmFileImport").submit(); //提交
	
}

function getConfigFileName ()
{
		// 书写SQL语句
	var strSQL = "";

	//strSQL = "select SysvarValue from ldsysvar where sysvar ='PrePayPerson_Config'";
	strSQL=wrapSql('LDSysVar',[]);	
			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	alert("未找到上传路径，请到ldsysvar表查看sysvar ='PrePayPerson_Config'的数据记录信息");
    return;
	}
	
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  importConfigFile = turnPage.arrDataCacheSet[0][0];
}


function getImportPath ()
{
		// 书写SQL语句
	var strSQL = "";

//	strSQL = "select SysvarValue from ldsysvar where sysvar ='PrePayPerson'";PrePayPerson_File
	//strSQL = "select SysvarValue from ldsysvar where sysvar ='PrePayPerson_File'";			 
	strSQL=wrapSql('LDSysVar1',[]);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	alert("未找到上传路径，请到ldsysvar表查看sysvar ='TranDataPath'的数据记录信息");
    return;
	}
	
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

	//查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  importPath = turnPage.arrDataCacheSet[0][0];
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在LLReport.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}   
        
//提交当前页数据
function submitCurData()
{
  if(NormPayGrpChooseGrid.mulLineCount==0)
    alert("当前页没有可以提交的数据");
  else{      
      fmSubmit();
      }
}

//提交所有选中数据，后台事务处理
function verifyChooseRecord()
{
    if(checkValue())
    { 
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fmSubmitAll.SubmitGrpPolNo.value=document.all('GrpPolNo').value;
    fmSubmitAll.SubmitPayDate.value=document.all('PayDate').value;
    fmSubmitAll.SubmitManageFeeRate.value=document.all('ManageFeeRate').value;
    fmSubmitAll.SubmitGrpContNo.value=document.all('GrpContNo').value;
         
    document.getElementById("fmSubmitAll").submit();
    }
}

//直接提交所有数据，后台事务处理
function submitCurDataAll()
{
	var GrpPolNo1=fmFileImport.GrpPolNo1.value;
	if(GrpPolNo1=="")
	{
		alert("请输入团单号!");
		return false;
	}
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");      	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    parent.fraInterface.fmFileImport.action = "./NormPayGrpChooseSaveAll.jsp?GrpPolNo1="+GrpPolNo1;
    document.getElementById("fmFileImport").submit();	
}

//查询数据
function queryRecord()
{
   if(checkValue())
   { 
    document.all('PolNo').value=document.all('GrpPolNo').value;
    fmFileImport.GrpPolNo1.value=document.all('GrpPolNo').value;	  
    easyQueryClick();
//    fmQuery.submit();
   }
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	
	try
	{
		arrReturn = getQueryResult();
		top.opener.afterQuery( arrReturn );
	}
	catch(ex)
	{
		alert( "没有发现父窗口的afterQuery接口。" + ex );
	}
	top.close();
}


/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.NormPayGrpChooseInput");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}

