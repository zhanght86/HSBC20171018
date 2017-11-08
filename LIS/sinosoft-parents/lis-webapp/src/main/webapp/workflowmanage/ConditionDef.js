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
    var BusiType        = TICollectGrid.getRowColData(selno, 1);
    var TransitionStart = TICollectGrid.getRowColData(selno, 3);
    var TransitionEnd   = TICollectGrid.getRowColData(selno, 5);
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionConverSQL1");
    mySql.addSubPara(BusiType);  
    mySql.addSubPara(TransitionStart); 
    mySql.addSubPara(TransitionEnd);     
 
    var arr = new Array();
    arr = easyExecSql( mySql.getString() );
    document.all('TransitionCond').value = arr[0][0];
   // document.all('CondDesc').value = arr[0][1];
    try
    {
        fm.BusiType.value            = TICollectGrid.getRowColData(selno, 1);
        fm.BusiTypeName.value        = TICollectGrid.getRowColData(selno, 2);
        fm.StartActivityID.value     = TICollectGrid.getRowColData(selno, 3);
        fm.StartActivityName.value   = TICollectGrid.getRowColData(selno, 4);
        fm.EndActivityID.value       = TICollectGrid.getRowColData(selno, 5);
        fm.EndActivityName.value     = TICollectGrid.getRowColData(selno, 6);
        fm.TransitionCondT.value     = TICollectGrid.getRowColData(selno, 7);
        fm.TransitionCondTName.value = TICollectGrid.getRowColData(selno, 8);
        //fm.TransitionCond.value      = TICollectGrid.getRowColData(selno, 9);
        fm.CondDesc.value            = TICollectGrid.getRowColData(selno, 10);

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
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        queryClick();
    }

}

function queryClick()
{
//    if (document.all('ActivityIDQ').value == "")
//    {
//        alert("请先择活动代码!");
//        return false;
//    }
//    if (document.all('BusiTypeQ').value == "")
//    {
//        alert("请先择业务类型!");
//        return false;
//    }
    try
    {
        fm.BusiType.value            = "";
        fm.BusiTypeName.value        = "";
        fm.StartActivityID.value     = "";
        fm.StartActivityName.value   = "";
        fm.EndActivityID.value       = "";
        fm.EndActivityName.value     = "";
        fm.TransitionCondT.value     = "";
        fm.TransitionCondTName.value = "";
        fm.TransitionCond.value      = "";
        fm.CondDesc.value            = "";

    }
    catch(ex)
    {
    }
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL1");
    mySql.addSubPara(document.all('BusiTypeQ').value);  
    mySql.addSubPara(document.all('StartActivityIDQ').value); 
    mySql.addSubPara(document.all('EndActivityIDQ').value);         
 
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}
function SaveClick()
{

    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL2");
    mySql.addSubPara(document.all('BusiType').value);  
    mySql.addSubPara(document.all('StartActivityID').value); 
    mySql.addSubPara(document.all('EndActivityID').value);     
 
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
    fm.action = "ActivityDefSave.jsp?OperFlag=INSERT||Condition";
     document.getElementById("fm").submit();
}
function ModifyClick()
{
//    if(document.all('BusiType').value==""||document.all('TransitionStart').value==""||document.all('TransitionEnd').value=="")
//    {
//        alert("请录入必录项进行保存后进行此操作或请选择已有数据!");
//        return false;
//    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL2");
    mySql.addSubPara(document.all('BusiType').value);  
    mySql.addSubPara(document.all('StartActivityID').value); 
    mySql.addSubPara(document.all('EndActivityID').value);   
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("数据库不存在该记录,请选择保存操作!");
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
    fm.action = "ActivityDefSave.jsp?OperFlag=MODIFY||Condition";
     document.getElementById("fm").submit();
}
function DeleteClick()
{
//    if(document.all('BusiType').value==""||document.all('TransitionStart').value==""||document.all('TransitionEnd').value=="")
//    {
//        alert("请录入必录项进行保存后进行此操作或请选择已有数据!");
//        return false;
//    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL2");
    mySql.addSubPara(document.all('BusiType').value);  
    mySql.addSubPara(document.all('StartActivityID').value); 
    mySql.addSubPara(document.all('EndActivityID').value);   
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
    fm.action = "ActivityDefSave.jsp?OperFlag=DELETE||Condition";
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
function testQuery()
{
    mySql = new SqlClass();
    mySql.setJspName("../../workflowmanage/ActivityDefSql.jsp");
    mySql.setSqlId("TestSQL1");
    var arr = easyExecSql( mySql.getString() );
    document.all('TransitionCond').value = arr;
}