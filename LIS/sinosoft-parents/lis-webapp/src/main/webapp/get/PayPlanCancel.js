//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 

var tResourceName="get.PayPlanCancelSql";
var tResourceSQL1="PayPlanCancelSql1";

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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else 
  {
    parent.fraMain.rows = "0,0,0,82,*";
  }
  parent.fraMain.rows = "0,0,0,0,*";
}


// 查询按钮
function easyQueryClick()
{
	
	if(trim(fm.GrpContNo.value)==""){
 		alert("请录入团单号");
 		return;
 	}
  // 初始化表格
  initLJSGetGrid();  
  
  divLCGet.style.display ='';
	
  // 书写SQL语句
  var strSQL = "";	
 // strSQL = "select ljsget.getnoticeno,ljsget.otherno,LCCont.GrpContNo,LCCont.insuredname,ljsget.sumgetmoney,ljsget.getdate,ljsget.makedate,ljsget.operator "
 //        + "from ljsget,LCCont "
 //        + "where ljsget.otherno=LCCont.ContNo and ljsget.othernotype='2' and ljsget.managecom like '"+manageCom+"%%' and LCCont.GrpContNo!='00000000000000000000'"
 //        + getWherePart( 'LJSGet.GetDate','GetDate' )
 //        + getWherePart( 'LCCont.GrpContNo','GrpContNo' )
 //        + " order by ljsget.getnoticeno";
	strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.manageCom.value,fm.GetDate.value,fm.GrpContNo.value]); 
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败,没有符合条件的数据!");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = LJSGetGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//撤销催付
function CancelCommit()
{      
  var tSel = LJSGetGrid.getSelNo();
  if( tSel == 0 || tSel == null )
    alert( "请先选择一条记录，再点击申请撤销按钮!" );
  else if(trim(fm.all('CancelReason').value)=="")
    alert( "请输入撤消原因!" );
  else
  {
    fm.all('OutGetNoticeNo').value = LJSGetGrid.getRowColData(tSel-1,1);	
    fm.all("fmtransact").value = "DELETE||LFGET";
    // showSubmitFrame(mDebug);
    var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit();
  }
}

function afterSubmit(FlagStr, content)
{
  showInfo.close();
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
  }
}
