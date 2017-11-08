//程序名称：PDDutyGetClm.js
//程序功能：责任给付生存
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDDutyGetClmInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function checkNullable()
{
  if(!verifyInput()) 
  {
  	return false;
  }
  
  return true;
}

var dic;

function afterQuery(getDutyCode2)
{
	fm.all("getDutyCode2").value = getDutyCode2;

 var mySql=new SqlClass();
 var sqlid = "PDDutyGetClmInputSql1";
 mySql.setResourceName(tResourceName); //指定使用的properties文件名
 mySql.setSqlId(sqlid);//指定使用的Sql的id
 mySql.addSubPara(fm.all("tableName").value);//指定传入的参数
 var sql = mySql.getString();
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 if(dic == null || dic("GETDUTYCODE2") != fm.all("getDutyCode2").value)
 {
 	var arr = new Array();
 	arr[0] = new Array();
 	arr[0][0] = "GETDUTYCODE2";
 	arr[0][1] = getDutyCode2;
 	
	dic = GetDicOfTableByKeys(fm.all("tableName2").value, arr);
 }

 for(var i = 0; i < rowCount; i++)
 {
 	if(Mulline9Grid.getRowColData(i,2).toUpperCase() != "GETDUTYCODE")
 	{
		var tContent = dic((Mulline9Grid.getRowColData(i,2)).toUpperCase());
	
		Mulline9Grid.setRowColDataCustomize(i,4,tContent,null);
	}
 }
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
	
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    if(fm.all("operator").value!="del")
    {
    	InputCalCodeDefFace('99');
  	}
  	//else
  	{
  		initForm(); 
  	}
  	if(fm.all("operator").value=="del")
  	{
  		initGetClaimDetail();
  	}
  	
    //nitForm();    
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 

function queryDutyGetClmLib()
{
  showInfo = window.open("PDDutyGetClmLibInput.jsp");
}
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
	 var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中一条记录再删除"+"");
		return;
	}
	
 fm.all("operator").value="del";
 submitForm();
}
function button245()
{
  var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中算法行再点击"+"");
		return;
	}
	else
	{
		var name = Mulline9Grid.getRowColData(selNo-1,1); 
		if(name.indexOf(""+"算法"+"") == -1)
		{
			myAlert(""+"请选中算法行"+","+"其它行无需定义算法"+"");
			return;
		}
	}
	
	//showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&AlgoCode=" + Mulline9Grid.getRowColData(selNo-1,4));
		var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDDutyGetClmInputSql4";
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
             + "&RuleName="+Mulline9Grid.getRowColData(selNo-1,4)
             + "&RuleDes="+Mulline9Grid.getRowColData(selNo-1,4)
             + "&Creator="+mOperator
             + "&RuleStartDate="+arrResult[0][0]
             + "&CreateModul=1"
             + "&Business=99&State=0";
   			 showInfo = window.open(tURL);
   	}
   	else
   	{
   		myAlert(''+"获取产品申请时间出错!"+'');
   	}
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
function button246()
{
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中一行已有责任给付赔付项再点击！"+"");
		return;
	}
  showInfo = window.open("PDRelaBillMain.jsp?riskcode=" + fm.RiskCode.value+"&pdflag="+fm.PdFlag.value+"&GetDutyCode="+fm.GetDutyCode.value+"&GetDutyKind="+Mulline10Grid.getRowColData(selNo-1,3));
}
function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
   var sqlid = "PDDutyGetClmInputSql2";
   mySql.setResourceName(tResourceName); //指定使用的properties文件名
   mySql.setSqlId(sqlid);//指定使用的Sql的id
   mySql.addSubPara("sqlid");//指定传入的参数
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
  // Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{ 
	var strSQL = "";
	var mySql=new SqlClass();
  var sqlid = "PDDutyGetClmInputSql3";
  mySql.setResourceName(tResourceName); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(fm.all("GetDutyCode").value);//指定传入的参数
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  = 10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
   
}


function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CALCODE').value = tCalCode;
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
		myAlert(""+"请先选择一条责任给付赔付项，再点击【查看算法内容】。"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end