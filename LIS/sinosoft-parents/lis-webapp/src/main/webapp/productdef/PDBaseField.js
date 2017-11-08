//程序名称：PDBaseField.js
//程序功能：基础信息字段描述
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
  } 
}
function update()
{
 fm.all("operator").value="update";
 if(!verifyInput2()){
		return false;
	}
 submitForm();
}
function query()
{
  showInfo = window.open("PDBaseFieldQueryMain.jsp");
}
function afterReturnParent(arrReturn)
{
	fm.all('TableCode').value = arrReturn[0];
	//fm.all('TableName').value = arrReturn[1];
	fm.all('FieldCode').value = arrReturn[2];
	//fm.all('FieldName').value = arrReturn[3];
	fm.all('FieldType').value = arrReturn[4];
	fm.all('IsDisplayCode').value = arrReturn[5];
	fm.all('OfficialDesc').value = arrReturn[6];
	fm.all('BusiDesc').value = arrReturn[7];
	fm.all('FieldTypeName').value = arrReturn[8];
	showCodeName();
	//showOneCodeName('pdtablename','TableCode','TableName');
	showOneCodeNameEx('pdfieldname','FieldCode','FieldName','tablecode',fm.all('TableCode').value);
}


