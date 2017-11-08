//程序名称：PDScheRateCalFactor.js
//程序功能：费率表要素库
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
	if( verifyInput2() != true ) 
	{
	return;
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
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
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
function query()
{
   var strSQL = "";
   strSQL = "select factorname,factorcode,factordatatype,factortype from Pd_Scheratecalfactor_Lib where 1=1 "
   + getWherePart('factorname','FactorName') + getWherePart('factorcode','FactorCode')
   + getWherePart('Factordatatype','FactorDataType') + getWherePart('factortype','FactorType')
   + " order by factortype,factorcode ";
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function del()
{
	if(window.confirm(""+"确定要删除本条费率表要素吗?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
	}
}

function reset()
{myAlert(2);
	initForm();
}
