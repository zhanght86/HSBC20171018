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
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        content = "操作成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
       // showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        fm.ValiFlagCode.value           = "";
        fm.ValiFlagName.value         = "";
    }
    catch(ex)
    {
    }   
    mySql = new SqlClass();
    mySql.setResourceName("workflow.DemoTestSql");
    mySql.setSqlId("DemoTestSQL1");
    mySql.addSubPara(document.all('BusiTypeQ').value);   	
	  mySql.addSubPara(document.all('ActivityIDQ').value);      
    mySql.addSubPara(document.all('PrtNo').value); 
    //whc start
    mySql.addSubPara("001");
    //end
	 
	    
    /*turnPage.queryModal(mySql.getString(), TICollectGrid);*///DemoWorkFlowGrid
    //whc start
    turnPage.queryModal(mySql.getString(), DemoWorkFlowGrid);
    //end
}

function ModifyClick()
{
    if (verifyInput2() == false) return false;
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("没有选中数据!");
        return false;
    }
    var activityid = TICollectGrid.getRowColData(selno, 3);
    if (activityid == null || "" == activityid)
    {
        alert("没有查询到活动信息!");
        return false;
    }
    var missionid = TICollectGrid.getRowColData(selno, 9);
    if (missionid == null || "" == missionid)
    {
        alert("没有查询到活动信息!");
        return false;
    }
    var submissionid = TICollectGrid.getRowColData(selno, 10);
    if (submissionid == null || "" == submissionid)
    {
        alert("没有查询到活动信息!");
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
    fm.action = "MissionCtrlSave.jsp?OperFlag=MODIFY&MissionID="+missionid+"&SubMissionID="+submissionid+"&ActivityID="+activityid+"&UserCode="+document.all('UserCode').value;
    document.getElementById("fm").submit();
}
