var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
//初始化查询
function initQuery()
{
	var strSQL="";
	strSQL="select t.usercode,t.username,t.comcode,t.userdescription,t.userstate from LDUser t";     
	turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(strSQL, LLQueryUserGrid);
}
//LLQueryUserGrid的响应函数
function LLQueryUserGridClick()
{
	//var i = LLQueryUserGrid.getSelNo();
    //i = i - 1;
    //fm.ContNo.value=LLQueryUserGrid.getRowColData(i,2);//合同号
	return true;
}

//“保存”按钮响应函数
function saveClick()
{
    fm.isReportExist.value="false";
	fm.fmtransact.value="update";
	submitForm();
}
//“查询”按钮
function queryClick()
{       
    // 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	strSQL = "select t.usercode,t.username,t.comcode,t.userdescription,t.userstate from LDUser t where 1=1 "
				 + getWherePart( 't.usercode','UserCodeQ',strOperate )
				 + getWherePart( 't.username','UserNameQ',strOperate )
				 + getWherePart( 't.comcode','ComCodeQ',strOperate );
//alert(strSQL);

	turnPage.queryModal(strSQL, LLQueryUserGrid);  
}

// 数据返回父窗口
function returnParent()
{
	//alert("aaa="+top.opener.location);
	var arrReturn = new Array();
	var tSel = LLQueryUserGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击返回按钮。" );
	}
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
			top.close();
		}
		catch(ex)
		{
//			alert( "请先选择一条非空记录，再点击返回按钮。");
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResult()
{
	//获取正确的行号
	var tRow = LLQueryUserGrid.getSelNo() - 1;

	var arrSelected = new Array();
	//设置需要返回的数组
	strSql = "select t.usercode,t.username,t.comcode,t.userdescription,t.userstate from LDUser t where 1=1"
	       + " and t.usercode = '" + LLQueryUserGrid.getRowColData(tRow, 1) + "'";
	
	//alert(strSql);
	var arrResult = easyExecSql(strSql);
	
	return arrResult;
}

//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action = './LLLcContSuspendSave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0"; 
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    //tSaveFlag ="0";
    initQuery();//刷新
}

