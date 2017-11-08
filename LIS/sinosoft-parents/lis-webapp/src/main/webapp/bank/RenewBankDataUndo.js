//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();

var tResourceName="bank.RenewBankDataUndoSql";
var tResourceSQL1="RenewBankDataUndoSql1";
var tResourceSQL2="RenewBankDataUndoSql2";
var tResourceSQL3="RenewBankDataUndoSql3";
var tResourceSQL4="RenewBankDataUndoSql4"; 

//简单查询
function easyQueryClick()
{
	// 初始化表格
	initCodeGrid();

	// 书写SQL语句
//	var strSQL = "select prtno,ContNo,appntname,riskcode,paytodate,getnoticeno,serialno "
//						 + "from lyrenewbankinfo a "
//						 + "where state = '0' "
//						 + getWherePart('prtno', 'PrtNo') 
//						 + getWherePart('ContNo', 'ContNo') 
//						 + getWherePart('managecom', 'ManageCom','like','0') 
//						 + getWherePart('appntname', 'AppntName') 
//						 + getWherePart('paytodate','StartDate','>=','0')
//						 + getWherePart('paytodate','EndDate','<=','0') 
//						 + " and not exists(select 1 from ljspay where getnoticeno = a.getnoticeno and otherno = a.ContNo and startpaydate = a.paytodate and bankonthewayflag = '1') "
//						 + "order by managecom,1,2 ";
	//alert(strSQL);
	var strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.PrtNo.value,fm.ContNo.value,fm.ManageCom.value,fm.AppntName.value,fm.StartDate.value,fm.EndDate.value]); 
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查到数据：\n请确认转帐状态，本界面只能查询已作转帐确认但未报盘的有效续保保单！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CodeGrid;    
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//提交，保存按钮对应操作
function submitForm()
{
	var tChk = CodeGrid.getChkCount();	
	if( tChk == null || tChk <= 0 )
	{
		alert( "请先选择记录，再点击撤销确认按钮。" );
		return false;
	}else{      
  		var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		document.all('undo').disabled = true;
  		document.getElementById("fm").submit(); //提交
  	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	document.all('undo').disabled = false;
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  
  easyQueryClick();
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}
