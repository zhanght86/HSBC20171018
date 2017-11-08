
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;

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
  	alert("在CertifyQuery.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

function returnParent()
{
    tRow=PolGrid.getSelNo();
    tCol=1;
    tPolNo = PolGrid.getRowColData(tRow-1,tCol);
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
    top.location.href="./CertifyQueryDetail.jsp?PolNo="+tPolNo;
}

// 在查询结束时，触发这个事件。
function onShowResult(result)
{
	var strSQL;
	
	// useSimulationEasyQueryClick(result[0]);
	strSQL = result[0];
	fm.SumCount.value = result[1];
	
	// 调用EasyQuery的功能
	turnPage.strQueryResult = easyQueryVer3(strSQL);
  
    //判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("没有查询到数据");
  	return false;
	}

	//查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CardInfo;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //tArr = chooseArray(arrDataSet,[0,1,3,4]) 
  //调用MULTILINE对象显示查询结果
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


function useSimulationEasyQueryClick(strData) {
  //保存查询结果字符串
  turnPage.strQueryResult  = strData;
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //拆分字符串，返回二维数组
  var tArr                 = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray          = new Array(0, 6, 7, 4, 5, 8, 22);

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
		if( cCodeName == 'State' ) {
			var trObj = tbInfo.rows(1);
			var tdObj;
			
			if( Field.value == '1' ) {  // 库存
				trObj.cells(0).innerHTML = "";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom' type='hidden'>";
        trObj.cells(2).innerHTML = "统计机构";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";
        
			} else if( Field.value == '2' ) {  // 已发放
				trObj.cells(0).innerHTML = "从";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "发放到";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			} else if( Field.value == '3' ) {  // 未核销
				trObj.cells(0).innerHTML = "从";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "发放到";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			} else if( Field.value == '4' ) {  // 入库总量
				trObj.cells(0).innerHTML = "";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom' type='hidden'>";
        trObj.cells(2).innerHTML = "统计机构";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}else if( Field.value == '5' ) {  // 正常作废
				trObj.cells(0).innerHTML = "从";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "回收到";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}else if( Field.value == '6' ) {  // 遗失被盗
				trObj.cells(0).innerHTML = "从";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "回收到";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}else if( Field.value == '7' ) {  // 缴销
				trObj.cells(0).innerHTML = "从";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "缴销到";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}
		}
	} catch(ex) {
		alert("在afterCodeSelect中发生异常");
	}
}

function easyPrint()
{
	easyQueryPrint(2,'CardInfo','turnPage');
}