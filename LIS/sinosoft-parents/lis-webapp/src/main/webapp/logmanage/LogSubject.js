//               该文件中包含客户端需要处理的函数和事件
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sqlresourcename = "logmanage.LogComponentSql";
//提交，保存按钮对应操作

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  LogReset();
  LogQuery();
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function LogReset()
{
  try
  {
	  initInpBox();
  }
  catch(re)
  {
  	alert("LogComponent.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

function LogUpdate()
{
	//check!!!!!!
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先查询/选择，再进行修改!" );
		return;
	}	
	//fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "LOG";
    document.all('Transact').value ="UPDATE";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     document.getElementById("fm").submit(); //提交
}

function LogSubmit()
{
	//check!!!!!!
	fm.Flag.value = "LOG";
    document.all('Transact').value ="INSERT";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     document.getElementById("fm").submit(); //提交
}

function LogDelete()
{
	//check!!!!!!
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先查询/选择，再进行删除!" );
		return;
	}	
	fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "LOG";
    document.all('Transact').value ="DELETE";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     document.getElementById("fm").submit(); //提交
}

function ShowLog()
{
	var arrResult = new Array();
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	    fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	    fm.LogSubjectDes.value = LogSubjectGrid.getRowColData(tSel-1,2);
	    fm.LogDept.value = LogSubjectGrid.getRowColData(tSel-1,3);
	    fm.LogServiceModule.value = LogSubjectGrid.getRowColData(tSel-1,4);
	    fm.TaskCode.value = LogSubjectGrid.getRowColData(tSel-1,5);
	    fm.LogType.value = LogSubjectGrid.getRowColData(tSel-1,7);                         
	    fm.Switch.value = LogSubjectGrid.getRowColData(tSel-1,8);
	    showCodeListKey('logtype', document.getElementsByName("LogType"), null, null, null, null, 0);
	}
}

function LogQuery()
{
	// 初始化表格
	//加载页面的时候已经初始化了该MuLine此处不需要重复初始化(浪费不必要的等待时间)
	//initLogSubjectGrid();

	// 书写SQL语句
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql0");
	    mySql.addSubPara(fm.LogSubjectID.value);
	    mySql.addSubPara(fm.LogSubjectDes.value);
	    mySql.addSubPara(fm.LogDept.value);
	    mySql.addSubPara(fm.LogServiceModule.value);
	    mySql.addSubPara(fm.TaskCode.value);
	    mySql.addSubPara(fm.LogType.value);
	    mySql.addSubPara(fm.Switch.value);

	turnPage.queryModal(mySql.getString(), LogSubjectGrid);
	if(LogSubjectGrid.mulLineCount <= 0){
		alert("没有符合条件的数据！");
		return false;
	}
	divLogSubject.style.display = "";

}


function LogToItem()
{
	//check!!!!!!
	var tSel = LogSubjectGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条日志主题记录!" );
		return;
	}
	fm.LogSubjectID.value = LogSubjectGrid.getRowColData(tSel-1,1);
	var tLogSubjectID = LogSubjectGrid.getRowColData(tSel-1,1);
	var varSrc="&SubjectID=" + tLogSubjectID;
	var newWindow = OpenWindowNew("../logmanage/LogItemInputFrame.jsp?Interface=../logmanage/LogItemInput.jsp" + varSrc,"","left");

}
