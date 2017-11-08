//程序名称：LLSecondUWRisk.js
//程序功能：险种核保信息界面-------理赔部分
//创建日期：2005-01-06 11:10:36
//创建人  ：HYQ
//更新记录：  更新人 yuejw   更新日期     更新原因/内容

var turnPage = new turnPageClass();
var temp = new Array();
///***************查询核保被保人险种信息*********************/
function queryLLRiskGridInfo()
{
	var tSql = "select lcpol.polno,lcpol.mainpolno,lcpol.riskcode,lmrisk.riskname,lcpol.prem,lcpol.amnt,lcpol.cvalidate,lcpol.enddate,lcpol.payintv,lcpol.payyears from lcpol,lmrisk where 1=1"
			+ " and contno='"+document.all('ContNo').value+"'"							
			+ " and lcpol.riskcode = lmrisk.riskcode";	
//	alert(tSql);					
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
		alert("险种信息查询失败!");
		return "";
	}
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = LLRiskGrid ;    
	//保存SQL语句
	turnPage.strQuerySql = tSql ; 
 	//设置查询起始位置
  	turnPage.pageIndex = 0;  
 	 //在查询结果数组中取出符合页面显示大小设置的数组
  	var arrDataSet  = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	if(arrDataSet.length!=0)
	{
	  for(i=0;i<arrDataSet.length;i++)
	  {
	  	 temp[i] = arrDataSet[i][2];
	  }
	}
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	return true;  					
}


/*********************************************************************
 *  加费承保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function showAdd()
{
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.InsuredNo.value;  	
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	window.open("../uw/UWManuAddMain.jsp?ContNo="+tContNo+"&InsuredNo="+tInsuredNo,"window1"); 
}

/*********************************************************************
 *  特约承保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function showSpec()
{
	var tContNo=fm.ContNo.value;
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	if (tContNo != "" )
	{ 	
		window.open("../uw/UWManuSpecMain.jsp?ContNo="+tContNo,"window1");  	
		showInfo.close();
	}
	else
	{
		showInfo.close();
		alert("数据传输错误!");
	}
}

///***************[取消按钮]*********************/
function cancelClick()
{
	fm.UWState.value="";
	fm.UWStateName.value="";
	fm.UWIdea.value="";
}

///***************[确定按钮]*********************/
function uwSaveClick()
{
//	alert(1);
//	return;
	var tSelNo =LLRiskGrid.getSelNo()-1;
//	alert(tSelNo);
	if(tSelNo < 0)
	{
		alert("请选择保单险种！");
		return;
	}
	if(fm.UWState.value=="")
	{
		alert("请选择险种核保结论!");
		return;
	}
	
	document.all('PolNo').value = LLRiskGrid.getRowColData(tSelNo,1);	//保单号码
	
	fm.action = "./LLSecondUWRiskChk.jsp";
 	submitForm(); //提交
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.submit(); //提交
}
/******************提交后操作,服务器数据返回后执行的操作******************************/ 
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}