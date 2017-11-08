//程序名称：PDCalFactor.js
//程序功能：算法要素库
//创建日期：2009-3-17
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
	if( verifyInput() != true ) {
		return;
	}
	
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
  
  fm.submit();
}

function afterQueryFactor(dicSelectedFactors)
{
	try
	{       
	  	var factorCount = dicSelectedFactors.Count;
		a = (new VBArray(dicSelectedFactors.Items())).toArray(); 
		// 如果选择了多行，只显示最后一行的要素信息
		var index = factorCount - 1;
		
		fm.all("FactorType").value = a[index][6];
	   	fm.all("Module").value = a[index][7];
		fm.all("FactorCode").value = a[index][3];
		fm.all("FactorValType").value = a[index][4];
		fm.all("FactorDesc").value = a[index][5];
		fm.all("Kind").value = a[index][8];
		
		fm.all("FactorCode").readOnly = true;
		fm.all("btnSave").disabled = true;
		
		fm.ModuleName.value = "";
		fm.PropertyName.value = "";
		fm.FactorTypeName.value = "";
		
		showCodeName();
   }
   catch(ex)
   {
   	   myAlert(""+"返回时错误"+""+ex);
   }
}

function afterSubmit( FlagStr, content )
{
  if(showInfo != null)
  {
  	showInfo.close();
  }
  
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
  window.open("PDCalFactorQueryMain.jsp?algotype=null&selectTable=PD_CalFactor_Lib");
}
function del()
{
	if(window.confirm(""+"确定要删除本条算法要素吗?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
 	}
}

