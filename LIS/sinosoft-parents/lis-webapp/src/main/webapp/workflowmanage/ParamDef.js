/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

/*声明翻页类*/
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
/*系统当前操作人员*/
var mOperate = "";
/*声明mysql查询类*/
var mySql = new SqlClass();
/*声明消息变量*/
var showInfo;

function afterCodeSelect(cCodeName, Field)
{

}

function showData()
{
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("没有选中数据!");
        return false;
    }
    try
    {
        fm.SourFieldName.value = TICollectGrid.getRowColData(selno, 1);
        fm.SourFieldCName.value = TICollectGrid.getRowColData(selno, 2);
        fm.DestFieldName.value = TICollectGrid.getRowColData(selno, 3);
        fm.DestFieldCName.value = TICollectGrid.getRowColData(selno, 4);
        fm.hiddenActivityID.value = TICollectGrid.getRowColData(selno, 5);
        fm.hiddenFieldOrder.value = TICollectGrid.getRowColData(selno, 6);

    }
    catch(ex)
    {
    }
    return true;
}
/*提交数据*/
function submitForm()
{
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.hideOperate.value = mOperate;
    document.getElementById("fm").submit();
}
function afterSubmit(FlagStr, content)
{
    if (typeof(showInfo) == "object")
    {
        showInfo.close();
        if (typeof(showInfo.parent) == "object" && typeof(showInfo.parent) != "unknown")
        {
            showInfo.parent.focus();
            if (typeof(showInfo.parent.parent) == "object" && typeof(showInfo.parent.parent) != "unknown")
            {
                showInfo.parent.parent.blur();
            }
        }
    }
    if (FlagStr == "Fail")
    {
        content = "操作失败，原因是：" + content;
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
        content = "操作成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        queryClick();
    }

}

function queryClick()
{
    try
    {
        fm.SourFieldName.value = "";
        fm.SourFieldCName.value = "";
        fm.DestFieldName.value = "";
        fm.DestFieldCName.value = "";
        fm.hiddenActivityID.value = "";
        fm.hiddenFieldOrder.value = "";

    }
    catch(ex)
    {
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ParamDefSQL1");
    mySql.addSubPara(document.all('ActivityIDQ').value);        
 
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}

function initQuery()
{
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ParamDefSQL3");
    mySql.addSubPara(document.all('ActivityIDQ').value);        
    var	strSQL=mySql.getString();
    var hrr = easyExecSql(strSQL);
    if ( hrr )
    {
        hrr[0][0]==null||hrr[0][0]=='null'?'0':fm.ActivityNameQ.value = hrr[0][0];
        fm.ActivityName.value = fm.ActivityNameQ.value;
    }
    else
    {
        alert("查找活动名称失败");
        return;
    }
}

function SaveClick()
{
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ParamDefSQL2");
    mySql.addSubPara(document.all('ActivityIDQ').value);     
    mySql.addSubPara(document.all('DestFieldName').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr > 0)
    {
        alert("数据库已经存在该记录,请选择修改或删除操作!");
        return false;
    }
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "ActivityDefSave.jsp?OperFlag=INSERT||Param";
    document.getElementById("fm").submit();
}
function ModifyClick()
{
    if(document.all('hiddenActivityID').value!=document.all('ActivityID').value)
    {
        alert("您不能修改活动代码!");
        return false;
    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ParamDefSQL2");
    mySql.addSubPara(document.all('ActivityIDQ').value);     
    mySql.addSubPara(document.all('DestFieldName').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr > 0)
    {
        alert("数据库已经存在该记录!");
        return false;
    }
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "ActivityDefSave.jsp?OperFlag=MODIFY||Param";
    document.getElementById("fm").submit();
}
function DeleteClick()
{
    if(document.all('hiddenActivityID').value!=document.all('ActivityID').value)
    {
        alert("您不能修改活动代码!");
        return false;
    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ParamDefSQL2");
    mySql.addSubPara(document.all('ActivityIDQ').value);     
    mySql.addSubPara(document.all('DestFieldName').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("数据库不存在该记录,无法进行删除操作!");
        return false;
    }
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "ActivityDefSave.jsp?OperFlag=DELETE||Param";
    document.getElementById("fm").submit();
}
function DefParamClick()
{
    window.open("")
}
function DefConditionClick()
{
    window.open("");
}