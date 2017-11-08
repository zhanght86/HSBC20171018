//该文件中包含客户端需要处理的函数和事件
var mDebug  = "0";
var mOperate="";
var showInfo;
var arrDataSet;
var mSwitch  = parent.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult  = "";
var QueryCount   = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch   = 0;
window.onfocus=myonfocus;

//数据校验
function beforeSubmit(){
	if (document.all('GrpName').value == ""){
		alert("请输入单位名称！");
		document.all('GrpName').focus();
		return false;
	}
	if (document.all('BusinessType').value == ""){
		alert("请输入行业类别！");
		document.all('BusinessType').focus();
		return false;
	}
	return true;
   
}


//新增子公司
function addRecord()
{

  if(verifyInput() == false)
     return false;
	if (!beforeSubmit())
	{
		return false;
	}	
	document.all('mOperate').value = "INSERT||ACCOUNT";
	if (document.all('mOperate').value == "INSERT||ACCOUNT")
	{
		if (!confirm('子公司 '+document.all('GrpName').value+' 下的全部信息是否已录入完毕，您是否要确认操作？'))
		{
			return false;
		}
	}
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
	QueryCount = 0;	//重新初始化查询次数
	fm.action="../appgrp/AccountInfoSave.jsp";
	fm.submit(); //提交
}

//删除子公司
function deleteRecord()
{  
		document.all('mOperate').value = "DELETE||ACCOUNT";
    AccountInfoGrid.delBlankLine();
    var showStr="正在删除险种信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    lockScreen('lkscreen');
		fm.action="../appgrp/AccountInfoSave.jsp";
    fm.submit();  
	
}


function easyQueryClick(){
	//查询该险种下的险种计算要素
	strSQL = "select b.customerno, b.GrpName, b.BusinessType, b.GrpNature, b.Peoples, a.AddressNo  from LDGrp b , LCGrpAppnt a "
	   			+" where b.SupCustoemrno = a.CustomerNo and a.GrpContNo = '" +document.all('GrpContNo').value+ "'"
	   			+" order by  customerno"
	   			  
	//alert ( "strSQL:"+ strSQL ) ;
	
  var str  = easyQueryVer3(strSQL, 1, 0, 1);

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//如果没数据也无异常
	if (!turnPage.strQueryResult) {
		//return false;
	}
	else{
		//QueryCount = 1;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = AccountInfoGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
}


function afterSubmit(FlagStr,content){
	showInfo.close();
	unlockScreen('lkscreen');
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	else{
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initAccountInfoGrid();
	    easyQueryClick();
	    tSearch = 0;
	    QueryCount = 0;
	}
	document.all('mOperate').value = "";
}

function ChooseAccount(parm1,parm2)
{

//alert( document.all(parm1).all('InpAccountInfoGridSel').value )
//alert( document.all(parm1).all('AccountInfoGrid1').value )

	if ( document.all(parm1).all('InpAccountInfoGridSel').value != "" )
	{
	  	fm.CustomerNo.value = document.all(parm1).all('AccountInfoGrid1').value;
	  	fm.AddressNo.value = document.all(parm1).all('AccountInfoGrid6').value
	}
//  alert(fm.AddressNo.value);
  
}

//返回上一步
function returnparent()
{
	top.close();
}
