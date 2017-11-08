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
  ItemReset();
  ItemQuery();
}

function ItemReset()
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

function ItemUpdate()
{
	//check!!!!!!
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先查询/选择，再进行修改!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "ITEM";
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

function ItemSubmit()
{
	//check!!!!!!
	fm.Flag.value = "ITEM";
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

function ItemDelete()
{
	//check!!!!!!
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先查询/选择，再进行删除!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(tSel-1,1);
	fm.Flag.value = "ITEM";
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

function ShowItem()
{
	var arrResult = new Array();
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	    fm.ItemID.value = LogItemGrid.getRowColData(tSel-1,1);
	    fm.ItemDes.value = LogItemGrid.getRowColData(tSel-1,2);
	    fm.KeyType.value = LogItemGrid.getRowColData(tSel-1,3);
	    fm.Grammar.value = LogItemGrid.getRowColData(tSel-1,4);
	    fm.ClassName.value = LogItemGrid.getRowColData(tSel-1,5);
	    fm.Func.value = LogItemGrid.getRowColData(tSel-1,6);                         
	    fm.Remark.value = LogItemGrid.getRowColData(tSel-1,7);
	    fm.ItemSwitch.value = LogItemGrid.getRowColData(tSel-1,8);
	    //showCodeListKey('logtype', document.getElementsByName("LogType"), null, null, null, null, 0);
	}
}

function ItemQuery()
{
	// 初始化表格
	initLogItemGrid();

	// 书写SQL语句
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql1");

	    mySql.addSubPara(fm.SubIDForItem.value);
	    mySql.addSubPara("'"+fm.SubIDForItem.value+"'");
	    mySql.addSubPara(fm.SubIDForItem.value);

	turnPage.queryModal(mySql.getString(), LogItemGrid);
	if(LogItemGrid.mulLineCount <= 0){
		alert("未查询到该主题对应控制点信息！");
		return false;
	}
	divLogItemMain.style.display = "";

}

function AddItem()
{
	//check!!!!!!
	
	//mulLineNum
	
	//alert(LogItemGrid.mulLineCount);
	for(i=0;i<LogItemGrid.mulLineCount;i++)
	{
		var KeyType=LogItemGrid.getRowColData(i,4);
		var switchType = LogItemGrid.getRowColData(i,5);
		//alert(i+":"+KeyType+":"+switchType);
		if(KeyType==null||KeyType==''
		   ||switchType==null||switchType=='')
		   {
		   	alert('第'+eval(i+1)+'行数据不全!');
		   	return false;
		   }
	}
	//return ;
	/*
	var sSel = LogItemGrid.getSelNo();	
	if( sSel == 0 || sSel == null )
	{
		alert( "请先选择需要添加的控制点!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(sSel-1,1);
	*/
	fm.Flag.value = "DOMAIN";
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

function DelItem()
{
	//check!!!!!!	
	var sSel = LogItemGrid.getSelNo();	
	if( sSel == 0 || sSel == null )
	{
		alert( "请先选择需要删除的控制点!" );
		return;
	}	
	fm.ItemID.value = LogItemGrid.getRowColData(sSel-1,1);
	fm.Flag.value = "DOMAIN";
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

function returnParent()
{
    //top.opener.initSelfGrid();
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}
