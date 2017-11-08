//程序名称：UserPopedom.js
//程序功能：用户权限配置
//创建日期：2006-01-09 15:13:22
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
//
var showInfo;
var mySql=new SqlClass();
/**
 * 提交权限配置数据
 * XinYQ rewrote on 2006-12-23
 */
function saveUserPopedom()
{
    if (!verifyInput2())      return;
    if (!sureDelPopedom())    return;
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fm').action = "UserPopedomSave.jsp";
    document.all('fm').submit();
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
}

/**
 * 确认删除保全权限操作
 * XinYQ added on 2006-12-25
 */
function sureDelPopedom()
{
    var sEdorPopedom, sGEdorPopedom, sUserCode, sErrorMsg, needConfirmFlag;
    try
    {
        sUserCode = document.getElementsByName("UserCode")[0].value;
        sEdorPopedom = document.getElementsByName("EdorPopedom")[0].value;
        sGEdorPopedom = document.getElementsByName("GEdorPopedom")[0].value;
    }
    catch (ex) {}
    needConfirmFlag = false;
    sErrorMsg = "您没有为用户 " + sUserCode + " 指定";
    if ((sEdorPopedom == null || sEdorPopedom.trim() == "") && (sGEdorPopedom != null && sGEdorPopedom.trim() != ""))
    {
        sErrorMsg += "个险";
        needConfirmFlag = true;
    }
    else if ((sEdorPopedom != null && sEdorPopedom.trim() != "") && (sGEdorPopedom == null || sGEdorPopedom.trim() == ""))
    {
        sErrorMsg += "团体";
        needConfirmFlag = true;
    }
    else if ((sEdorPopedom == null || sEdorPopedom.trim() == "") && (sGEdorPopedom == null || sGEdorPopedom.trim() == ""))
    {
        sErrorMsg += "任何";
        needConfirmFlag = true;
    }
    sErrorMsg += "保全权限级别。\n\n如果该用户原来具有该权限，继续操作将删除相应权限。\n\n要继续吗？";
    if (needConfirmFlag && !confirm(sErrorMsg))
    {
        return false;
    }
    return true;
}

//进入用户信息查询页面
function queryUser()
{
    var newWindow = window.open("../sys/UsrCommonQueryMain.jsp?ManageCom="+manageCom+"&UserCode="+fm.UserCode.value,"UsrCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/*********************************************************************
 *  回车实现查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function QueryOnKeyDown()
{
    var keyCode = event.keyCode;

    //回车的ascii码是13
    if (keyCode=="13")
    {
    	QueryUserInfo(fm.UserCode.value);
    }
}

function QueryUserInfo(UserCode)
{
	 mySql=new SqlClass();
			//清空数据
    	fm.UserCode_read.value = "";
    	fm.UserName.value = "";
    	fm.ComName.value = "";
    	fm.EdorPopedom.value = "";
    	fm.GEdorPopedom.value = "";
    	fm.EdorPopedomName.value = "";
    	fm.GEdorPopedomName.value = "";
    		
		fm.UserCode.value = UserCode;
		/*
    var strSQL = "select usercode,username," 
    				 + " a.comcode," 
    				 + " (select edorpopedom from ldedoruser e where e.usercode = a.usercode and e.usertype = '1') edorpopedom, " 
    				 + " (select edorpopedom from ldedoruser e where e.usercode = a.usercode and e.usertype = '2') gedorpopedom"
    				 + " from lduser a where usercode = '" + UserCode + "' ";
   */
 mySql.setResourceName("bq.UserPopedom");
 mySql.setSqlId("UserPopedomSql1");
 mySql.addSubPara(UserCode);

   
    var brr = easyExecSql(mySql.getString());

    if ( brr ) 
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.UserCode_read.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.UserName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.ComName.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorPopedom.value     = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.GEdorPopedom.value = brr[0][4];
        showOneCodeName('EdorPopedom','EdorPopedom','EdorPopedomName');
        showOneCodeName('GEdorPopedom','GEdorPopedom','GEdorPopedomName');
    }  	
}