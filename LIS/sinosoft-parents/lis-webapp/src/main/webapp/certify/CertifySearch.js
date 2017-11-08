
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;

// 一些状态的含义
var vStateFlag = new ActiveXObject("Scripting.Dictionary");

vStateFlag.Add("0", "未用");
vStateFlag.Add("1", "正常回收");
vStateFlag.Add("2", "作废");
vStateFlag.Add("3", "挂失");
vStateFlag.Add("4", "销毁");

var vOperateFlag = new ActiveXObject("Scripting.Dictionary");

vOperateFlag.Add("0", "发放");
vOperateFlag.Add("1", "回收");
vOperateFlag.Add("2", "发放回退");
vOperateFlag.Add("3", "回收回退");


//提交，保存按钮对应操作
function submitForm()
{
	if( verifyInput() == true ) {
	  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='提示';   //网页名称，可为空; 
	  var iWidth=550;      //弹出窗口的宽度; 
	  var iHeight=250;     //弹出窗口的高度; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

  	fm.submit(); //提交
	}
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();

  CardInfo.clearData();  // 清空原来的数据

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
    //执行下一步操作
  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在CertifySearch.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

// 在查询结束时，触发这个事件。
function onShowResult(result)
{
	
	if( result[0].charAt(2) == '0' ) {
		alert('没有查询到数据');
	} else {
		useSimulationEasyQueryClick(result[0]);
	}
}

function useSimulationEasyQueryClick(strData) {
  //保存查询结果字符串
  turnPage.strQueryResult  = strData;
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //拆分字符串，返回二维数组
  var tArr    = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray  = new Array(0, 6, 7, 4, 5, 8,16, 22, 23, 24);

  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CardInfo
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}

// 事件响应函数，当用户改变CodeSelect的值时触发
function afterCodeSelect(cCodeName, Field)
{
	try {
	} catch(ex) {
		alert("在afterCodeSelect中发生异常");
	}
}

// 查询单证状态表的信息
function searchState()
{
	fm.State.value = "0";
	submitForm();
}

// 查询单证轨迹表的信息
function searchTrack()
{
	fm.State.value = "1";
	submitForm();
}

function putList()
{
	var vRow = CardInfo.getSelNo();

	if( vRow == null || vRow == 0 ) {
		alert("请先选择一条查询结果");
		return;
	}

	vRow = vRow - 1;
	
	var vColIndex = 0;
	var vMaxRow = CardListInfo.mulLineCount;
	
	CardListInfo.addOne();
	
	for(vColIndex = 1; vColIndex <=10; vColIndex++) {
		CardListInfo.setRowColData(vMaxRow, vColIndex, CardInfo.getRowColData(vRow, vColIndex));
	}
}

function printList()
{
	fm_print.submit();
}

function boxEventHandler(parm1, parm2)
{
	var vRow = CardInfo.getSelNo();
	
	if( vRow == null || vRow == 0 ) {
		return;
	}
	
	vRow = vRow - 1;

	var strSQL = "";
	
	if( fm.State.value == "0" )
	 {
//		strSQL = "SELECT * FROM LZCard";
		
		var sqlid1="CertifySearchSql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("certify.CertifySearchSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		
	} 
	else 
	{
//		strSQL = "SELECT * FROM LZCardTrack";
		var sqlid2="CertifySearchSql1";
		var mySql2=new SqlClass();
		mySql2.setResourceName("certify.CertifySearchSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	}

//	strSQL = strSQL + " WHERE CertifyCode = '" + CardInfo.getRowColData(vRow, 1) +
//	          "' AND SendOutCom = '" + CardInfo.getRowColData(vRow, 2) +
//	          "' AND ReceiveCom = '" + CardInfo.getRowColData(vRow, 3) +
//	          "' AND StartNo >= '" + CardInfo.getRowColData(vRow, 4) +
//	          "' AND EndNo <= '" + CardInfo.getRowColData(vRow, 5) +
//	          "' AND MakeDate = '" + CardInfo.getRowColData(vRow, 9) +
//	          "' AND MakeTime = '" + CardInfo.getRowColData(vRow, 10) + "'";
	     
		mySql2.setResourceName("certify.CertifySearchSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 1));//指定传入的参数
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 2));//指定传入的参数
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 3));//指定传入的参数
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 4));//指定传入的参数
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 5));//指定传入的参数
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 9));//指定传入的参数
		mySql2.addSubPara(CardInfo.getRowColData(vRow, 10));//指定传入的参数
		var strSQL=mySql2.getString();
		
	// Use my docode function
	var myResult = myDecodeEasyQueryResult(easyQueryVer3(strSQL));
	
	fm.CertifyCode.value 		= myResult[0][0];
	fm.SendOutCom.value 		= myResult[0][6];
	fm.ReceiveCom.value 		= myResult[0][7];
	fm.Operator.value 			= myResult[0][22];
	fm.Handler.value 				= myResult[0][11];
	fm.HandleDateB.value 		= myResult[0][12];
	fm.HandleDateE.value 		= "";
	fm.MakeDateB.value 			= myResult[0][23];
	fm.MakeDateE.value 			= "";
	fm.TakeBackNo.value 		= myResult[0][14];
	fm.SumCount.value 			= myResult[0][8];
	fm.StartNo.value 				= myResult[0][4];
	fm.EndNo.value 					= myResult[0][5];
	
	fm.CertifyState.value 	= vStateFlag.Item(myResult[0][16]);
	fm.OperateFlag.value 		= vOperateFlag.Item(myResult[0][17]);
}

// EasyQuery 的这个函数会和 turnPage对象相互影响，所以就自己照着写了一个
function myDecodeEasyQueryResult(strResult) {
	var arrEasyQuery = new Array();
	var arrRecord = new Array();
	var arrField = new Array();
	var recordNum, fieldNum, i, j;

	if (typeof(strResult) == "undefined" || strResult == "" || strResult == false)	{
		return null;
	}

	//公用常量处理，增强容错性
	if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
	if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";

	try {
	  arrRecord = strResult.split(RECORDDELIMITER);      //拆分查询结果，得到记录数组
	  
	  recordNum = arrRecord.length;
	  for(i=1; i<recordNum; i++) {
	  	arrField = arrRecord[i].split(FIELDDELIMITER); //拆分记录，得到字段数组
	  	
	  	fieldNum = arrField.length;
	  	arrEasyQuery[i - 1] = new Array();
	  	for(j=0; j<fieldNum; j++) {
		  	arrEasyQuery[i - 1][j] = arrField[j];          //形成以行为记录，列为字段的二维数组
		  }
	  }		
	} 
	catch(ex) {
	  alert("拆分数据失败！" + "\n错误原因是：" + ex);
	  return null;  
	}
  
	return arrEasyQuery;
}
