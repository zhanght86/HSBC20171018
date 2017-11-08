//程序名称：PDAlgoTempLib.js
//程序功能：算法模板库
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
  showInfo = window.open("PDAlgoTempLibQueryMain.jsp");
}
function del()
{
	if(window.confirm(""+"确定要删除本算法模板吗?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
 	}
}

function afterQueryAlgo(code)
{
	try
	{
	   var strSQL = "";
	   strSQL = "select code,name,type,content,description from pd_algotemplib where code = '" + code + "'";
	   var arr = easyExecSql(strSQL);
	   fm.all("AlgoTempCode").value = arr[0][0];
	   fm.all("AlgoTempType").value = arr[0][2];
	   fm.all("AlgoTempName").value = arr[0][1];
	   fm.all("AlgoTempContent").value = arr[0][3];
	   fm.all("AlgoTempDescription").value = arr[0][4];
	   
	   fm.all("AlgoTempCode").readOnly = true;
	   fm.all("btnSave").disabled = true;	
		
	   var sql = "select codename from ldcode where codetype = 'algotemptype' and code = '" + arr[0][2] +"'";
	   var arr2 = easyExecSql(sql);
	   fm.all("AlgoTempTypeName").value = arr2[0][0];
   }
   catch(ex)
   {
   	   myAlert(""+"返回时错误"+""+ex);
   }
}


