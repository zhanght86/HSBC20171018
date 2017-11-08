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
  } 
}

function save()
{
  if(!verifyInput2()){
  	return false;
  }
  if(fm.all("Id").value!=""){
		myAlert(""+"对查询后返回的结果只能进行修改！"+"")
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
 	if(fm.all("Id").value==null||fm.all("Id").value==""){
		myAlert(""+"只能对查询后返回的结果进行修改！"+"")
		return false;
	}
 	fm.all("operator").value="update";
 	submitForm();
}
function query()
{
  showInfo = window.open("PDTestPointClewQueryMain.jsp");
}
function del()
{
 	if(!verifyInput2()){
 		return false;
 	}
 	if(fm.all("Id").value==null||fm.all("Id").value==""){
		myAlert(""+"只能对查询后返回的结果进行删除！"+"")
		return false;
	}
	if(window.confirm(""+"确定要删除本条测试要点提示吗?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
	}
}
function afterQuery(TableCode,FieldCode,Id)
{
	try
	{
	   var strSQL = "";
	   strSQL = "select tablecode,fieldcode,clewcontent from Pd_Testpointclew_Lib where tablecode = '"
	   + TableCode +"' and fieldcode = '" + FieldCode +"' and Id = '" + Id +"'";

	   var arr = easyExecSql(strSQL);
	   fm.all("TableCode").value = arr[0][0];
	   fm.all("FieldCode").value = arr[0][1];
	   fm.all("Id").value = Id;
	   fm.all("ClewContent").value = arr[0][2];
	   showCodeName();
	   showOneCodeNameEx('pdfieldname','FieldCode','FieldName','tablecode',fm.all('TableCode').value);
   }
   catch(ex)
   {
   	   myAlert(""+"返回时错误"+""+ex);
   }
}

