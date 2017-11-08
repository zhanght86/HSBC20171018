/*
  //创建人：刘岩松
  //修改人：刘岩松
  //修改内容：

  //该文件中包含客户端需要处理的函数和事件
*/
var showInfo;
var mDebug="0";
var FlagDel;//在delete函数中判断删除的是否成功
var turnPage = new turnPageClass();

function displayQueryResult(strResult) {
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  
  strResult = Conversion(strResult);
  var filterArray          = new Array(0, 1, 13, 14);
  
  //保存查询结果字符串
  turnPage.strQueryResult  = strResult;
  
  //使用模拟数据源
  turnPage.useSimulation   = 1;
  
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  //alert(turnPage.arrDataCacheSet);
  
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = BillGrid;             
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { alert(ex);}
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { alert(ex);}
  }
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
  
}


function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
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
    //	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    	showDiv(inputButton,"false");

    	//执行下一步操作
    }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
			parent.fraMain.rows = "0,0,0,0,*";
    }
    else
    {
  		parent.fraMain.rows = "0,0,0,0,*";
    }
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

//根据起始日期进行查询出要该日期范围内的批次号码
function showSerialNo()
{
	if ((fm.StartDate.value == "") || (fm.EndDate.value == "")
			|| (fm.StartDate.value == "null") || (fm.EndDate.value == "null")) {
		alert("请您输入起始日期和结束日期！");
	}
	fm.Flag.value = "YS";
	fm.TFFlag.value = "F";
	var i = 0;
	var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	initBillGrid();
	fm.action = './ShowBill.jsp';
	document.getElementById("fm").submit(); // 提交
}

// 根据选中的批次号、银行编码、管理机构进行查询并且执行打印功能；
function PrintBill()
{
	var tRow=BillGrid.getSelNo();
  	if (tRow==0)
   	{
   		alert("请您先进行选择");
  		return;
  	}
    else
    {
    	var tCol=1;
    	tBillNo = BillGrid.getRowColData(tRow-1,tCol);
    	fm.BillNo.value=tBillNo;
    	fm.selBankCode.value=BillGrid.getRowColData(tRow-1,tCol+1);
    	fm.action = "./PrintBill.jsp";
    	fm.target="f1print";
    	showInfo.close();
    	document.getElementById("fm").submit();
    }
}
