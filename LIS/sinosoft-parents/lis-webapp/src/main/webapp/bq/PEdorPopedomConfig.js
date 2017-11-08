//程序名称：PEdorPopedomConfig.js
//程序功能：保全权限配置
//创建日期：2006-01-09 15:13:22
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
//
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql=new SqlClass();
/**
 * 提交权限配置数据
 * XinYQ rewrote on 2006-12-23
 */

function saveGradePopedom()
{

    if (!verifyInput2())            return;
    
    if (!SelfGrid.checkValue2())    return;
    

    var tManageCom = fm.ManageCom.value;
    var tLimitMoneyI = fm.LimitGetMoneyI.value;
    var tLimitMoneyY = fm.LimitGetMoneyY.value;
    var tLimitMoneyL = fm.LimitGetMoneyL.value;
    var tLimitMoneyM = fm.LimitGetMoneyM.value;
    var tModifyAppDateNum = fm.ModifyAppDateNum.value;

    //if(tManageCom == null || tManageCom == "")
    //{
    //  alert("请选择管理机构！");
    //  return;
    //}
    
    if(tLimitMoneyI != null && trim(tLimitMoneyI) != "" && (isNaN(parseFloat(tLimitMoneyI)) || !isNumeric(tLimitMoneyI)))
    {
        alert("个人付费金额上限不是有效数字！");
        fm.LimitMoneyI.focus();
        return;
    }
    if(tLimitMoneyY != null && trim(tLimitMoneyY) != "" && (isNaN(parseFloat(tLimitMoneyY)) || !isNumeric(tLimitMoneyY)))
    {
        alert("代理付费金额上限不是有效数字！");
        fm.LimitMoneyY.focus();
        return;
    }
    if(tLimitMoneyL != null && trim(tLimitMoneyL) != "" && (isNaN(parseFloat(tLimitMoneyL)) || !isNumeric(tLimitMoneyL)))
    {
        alert("代理付费金额上限不是有效数字！");
        fm.LimitMoneyY.focus();
        return;
    }
    if(tLimitMoneyM != null && trim(tLimitMoneyM) != "" && (isNaN(parseFloat(tLimitMoneyM)) || !isNumeric(tLimitMoneyM)))
    {
        alert("代理付费金额上限不是有效数字！");
        fm.LimitMoneyY.focus();
        return;
    }
    if(tModifyAppDateNum != null && trim(tModifyAppDateNum) != "" && (!isNumeric(tModifyAppDateNum) || !isInteger(tModifyAppDateNum)))
    {
        alert("修改柜面受理日期权限不是有效天数！");
        fm.ModifyAppDateNum.focus();
        return;
    }

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
    document.all("fm").action = "PEdorPopedomConfigSave.jsp";
    document.all("fm").submit();
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
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            //nothing
        }
        catch (ex) {}
    }
}

function queryEdorPopedom()
{
    var tGrade = fm.EdorPopedom.value;
    var tManageCom = fm.ManageCom.value;
    var brr;

	  mySql=new SqlClass();
	  
 mySql.setResourceName("bq.PEdorPopedomConfig");
 mySql.setSqlId("PEdorPopedomConfigSql1");
 mySql.addSubPara(tManageCom);
 mySql.addSubPara(tGrade);
 mySql.addSubPara(tManageCom);
 mySql.addSubPara(tGrade);
 mySql.addSubPara(tManageCom);
 mySql.addSubPara(tGrade);

    brr = easyExecSql(mySql.getString());
    if (brr)
    {
        displayMultiline(brr, SelfGrid);
    }
}

function queryGEdorPopedom()
{
    var tGrade = fm.GEdorPopedom.value;
    var tManageCom = fm.ManageCom.value;
    var brr;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorPopedomConfig");
    mySql.setSqlId("PEdorPopedomConfigSql2");
    mySql.addSubPara(tManageCom);
    mySql.addSubPara(tGrade);
    mySql.addSubPara(tManageCom);
    mySql.addSubPara(tGrade);
    mySql.addSubPara(tManageCom);
    mySql.addSubPara(tGrade);

    brr = easyExecSql(mySql.getString());
    if (brr)
    {
        displayMultiline(brr, SelfGrid);
    }
}

/**
 * showCodeList 的回调函数, 权限类别层的显示或隐藏
 * XinYQ modified on 2006-12-23
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorpopedomtype")
    {
        if (oCodeListField.value == "1")
        {
            try
            {
                document.all("divEdorPopedomTitle").style.display = "block";
                document.all("divEdorPopedomInput").style.display = "block";
                document.all("divGEdorPopedomTitle").style.display = "none";
                document.all("divGEdorPopedomInput").style.display = "none";
                document.all("divEdorPopedomMoney").style.display = "none";
                document.all("divGEdorPopedomMoney").style.display = "none";
            }
            catch (ex) {}
        }
        else if (oCodeListField.value == "2")
        {
            try
            {
                document.all("divEdorPopedomTitle").style.display = "none";
                document.all("divEdorPopedomInput").style.display = "none";
                document.all("divGEdorPopedomTitle").style.display = "block";
                document.all("divGEdorPopedomInput").style.display = "block";
                document.all("divEdorPopedomMoney").style.display = "none";
                document.all("divGEdorPopedomMoney").style.display = "none";
            }
            catch (ex) {}
        }
        
        //清空权限属性
        fm.LimitGetMoneyI.value = "";
        fm.LimitGetMoneyY.value = "";
        fm.LimitGetMoneyL.value = "";
        fm.LimitGetMoneyM.value = "";
        fm.ModifyAppDateNum.value = "";
        
        SelfGrid.clearData();
    }
    else if (sCodeListType == "edorpopedom")
    {
        queryEdorPopedom();
    }
    else if (sCodeListType == "gedorpopedom")
    {
        queryGEdorPopedom();
    }
}
