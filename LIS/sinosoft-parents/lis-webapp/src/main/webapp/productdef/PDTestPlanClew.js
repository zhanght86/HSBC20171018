//程序名称：PDTestPointClew.js
//程序功能：测试要点提示
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
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
    queryMulline9Grid(); 
  } 
}

function save()
{
  if(!verifyInput2()){
  	return false;
  }

  fm.all("operator").value="save";
  submitForm();
}
function update()
{
  if(!verifyInput2()){
 		return false;
 	}

 	fm.all("operator").value="update";
 	submitForm();
}
function query()
{
  showInfo = window.open("PDTestPlanClewQueryMain.jsp");
}
function del()
{
 	if(!verifyInput2()){
 		return false;
 	}

	if(window.confirm(""+"确定要删除本条测试要点提示吗?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
	}
}
function afterQuery(Id)
{
	try
	{
	   var strSQL = "";
	   strSQL = "select t.testplankind, t.testplankind, t.clewcontent, t.remark from pd_testplanclew_lib t where t.clewcontentcode = " + Id;

	   var arr = easyExecSql(strSQL);
	   fm.all("plankind").value = arr[0][0];
	   fm.all("riskkind").value = arr[0][1];
	   fm.all("ClewContent").value = arr[0][2];
	   fm.all("remark").value= arr[0][3];
	   fm.all("clewcontentcode").value = Id;
	   document.getElementById("riskKindTitle").style.display='';
  	   document.getElementById("riskKindContent").style.display='';
  	   
	   showCodeName();
	
   }
   catch(ex)
   {
   	   myAlert(""+"返回时错误"+""+ex);
   }
}

/**
 * 在CodeSelect选择完的操作
 */
function afterCodeSelect(cName, Filed)
{	
	showDiv(cName, Filed);
}

/**
 * 显示险种分类输入框
 */
function showDiv(cName, Filed)
{
  if(cName=='planKind')
  {
  	if(document.getElementById("plankind").value == 2){	
  		document.getElementById("riskKindTitle").style.display='';
  		document.getElementById("riskKindContent").style.display='';
  	}
  	
  	if(document.getElementById("plankind").value == 1){	
  		fm.all('riskkind').value = '';
  		document.getElementById("riskKindTitle").style.display='none';
  		document.getElementById("riskKindContent").style.display='none';
  	}
  }
}
var Mulline9GridTurnPage = new turnPageClass(); 
function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select (select d.codename from ldcode d where d.codetype = 'plankind' and d.code = t.testplankind) , (select d.codename from ldcode d where d.codetype = 'riskkind' and d.code = t.testplanriskkind), t.clewcontent, t.remark,t.clewcontentcode from pd_testplanclew_lib t where 1 = 1 "
   + getWherePart('testplankind','plankind')    + getWherePart('testplanriskkind','riskkind')
   + getWherePart('t.Clewcontent','ClewContent','like')
   + " order by t.clewcontentcode";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}

function afterRadioSelect(){
	var selNo = Mulline9Grid.getSelNo()-1;
	
	var clewcontentcode = Mulline9Grid.getRowColData(selNo,5);
	var sql = "select TESTPLANKIND, TESTPLANRISKKIND , ClewContent,Remark from PD_TestPlanClew_Lib t where t.clewcontentcode = '" + clewcontentcode + "'";
	var crr = easyExecSql(sql,1,1,1);
	
	fm.all('planKind').value = crr[0][0];
	fm.all('ClewContent').value = crr[0][2];
	fm.all('Remark').value = crr[0][3];
	
	var queryKindSql = "select codename from ldcode where codetype = 'plankind' and code = '" + crr[0][0] + "'";
	var crr1 = easyExecSql(queryKindSql,1,1,1);
	
	fm.all('planKindName').value = crr1[0][0];
	fm.all('riskkind').value = crr[0][1];
	fm.all('clewcontentcode').value = clewcontentcode;
	
	if(crr[0][0] == '2'){
		document.getElementById("riskKindTitle").style.display='';
  		document.getElementById("riskKindContent").style.display='';
  		var queryKindSql1 = "select codename from ldcode where codetype = 'riskkind' and code = '" + crr[0][1] + "'";
		var crr2 = easyExecSql(queryKindSql1,1,1,1);
		fm.all('riskKindName').value = crr2[0][0];
	
	}else if(crr[0][0] == '1'){
		document.getElementById("riskKindTitle").style.display='none';
  		document.getElementById("riskKindContent").style.display='none';
	}

}
