//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var  turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  showSubmitFrame(mDebug);
  fm.action = './CasePolicySave.jsp';
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

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

function ShowCheckInfo()
{ 
	var RgtNo = fm.RgtNo.value;
	fm.action = "./ShowCheckInfo.jsp?RgtNo="+RgtNo;
	fm.submit();
}

function ShowCaseDetail()
{
	var varSrc ;
	var rowNum=CheckGrid.mulLineCount ;
	var tNum;
	if(rowNum!=0)
	{
		tNum = CheckGrid.getSelNo();
		if(tNum<1)
		{
			alert("请您选中一条记录");
			return;
		}
		if(tNum>=1)
		{
			var varSrc 	= "&ClmUWNo=" + CheckGrid. getRowColData(tNum-1,4);
	    varSrc += "&InsuredName=" + fm.InsuredName.value;
    	varSrc += "&PolNo=" + fm.PolNo.value;
    	varSrc += "&RgtNo=" + fm.RgtNo.value;
    	varSrc += "&Clmer="      + CheckGrid.getRowColData(tNum-1,3);
    	varSrc += "&ClmUWer="    + CheckGrid.getRowColData(tNum-1,5);
			varSrc += "&CheckType="  + CheckGrid.getRowColData(tNum-1,1);
			varSrc += "&LPJC="  + CheckGrid.getRowColData(tNum-1,2);
			var newWindow = window.open("./FrameCaseDetail.jsp?Interface=CaseDetailInput.jsp"+varSrc,"CaseDetailInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
//			fm.submit();	
		}
	}
	else
	{
		
			var varSrc 	= "";
	    varSrc += "&InsuredName=" + fm.InsuredName.value;
  	  varSrc += "&RgtNo=" + fm.RgtNo.value;
    	varSrc += "&PolNo=" + fm.PolNo.value;
    	varSrc += "&RgtNo=" + fm.RgtNo.value;
    	varSrc += "&Conclusion=" + "";
    	varSrc += "&ClmUWer=" + "";
			var newWindow = window.open("./FrameCaseDetail.jsp?Interface=CaseDetailInput.jsp"+varSrc,"CaseDetailInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');

	}
}

function displayQueryResult1(strResult)
{
//	alert("开始执行");
  strResult = Conversion(strResult);
  var filterArray          = new Array(14,4,3,0,2);
  turnPage.strQueryResult  = strResult;
  turnPage.useSimulation   = 1;
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  turnPage.pageDisplayGrid = CheckGrid;
  turnPage.pageIndex       = 0;
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}
