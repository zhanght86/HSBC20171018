/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

/*声明翻页类*/
var turnPage = new turnPageClass();
/*系统当前操作人员*/
var mOperate = "";
/*声明mysql查询类*/
var mySql = new SqlClass();
/*声明消息变量*/
var showInfo;
function goBack()
{
    if (document.all("ReturnFlag").value == "N")
    {
        window.returnValue = "true|1|1";
    }
    else
    {
        opener.fileQuery();
    }
    top.close();
}

function showData()
{
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("没有选中数据!");
        return false;
    }
    var tempActivityID = TICollectGrid.getRowColData(selno,1);
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL2");
    mySql.addSubPara(tempActivityID);  
 
    var arr = new Array();
    arr = easyExecSql(mySql.getString());
    try
    {
        fm.ActivityID.value           = arr[0][0];
        fm.hiddenActivityID.value     = arr[0][0];
        fm.ActivityName.value         = arr[0][1];
        fm.BusiType.value             = arr[0][2];
        fm.BusiTypeName.value         = arr[0][3];
        fm.ActivityFlag.value         = arr[0][4];
        fm.ActivityFlagName.value     = arr[0][5];
        fm.IsNeed.value               = arr[0][6];
        fm.IsNeedName.value           = arr[0][7];
        
        fm.BeforeInitType.value       = arr[0][8]; 
        fm.BeforeInitTypeName.value   = arr[0][9];
        fm.BeforeInit.value           = arr[0][10];
        fm.AfterInitType.value        = arr[0][11];
        fm.AfterInitTypeName.value    = arr[0][12];
        fm.AfterInit.value            = arr[0][13];
        fm.BeforeEndType.value        = arr[0][14];
        fm.BeforeEndTypeName.value    = arr[0][15];
        fm.BeforeEnd.value            = arr[0][16];
        fm.AfterEndType.value         = arr[0][17];
        fm.AfterEndTypeName.value     = arr[0][18];
        fm.AfterEnd.value             = arr[0][19];
        
        fm.ImpDegree.value            = arr[0][20];
        fm.ImpDegreeName.value        = arr[0][21];    
        fm.FunctionID.value            = arr[0][22];
        fm.FunctionIDName.value        = arr[0][23];
        //增加了创建、分配、删除   jiyongtan begin
        fm.CreateActionType.value      = arr[0][24];
        fm.CreateActionTypeName.value  = arr[0][25];    
        fm.CreateAction.value          = arr[0][26];
        fm.ApplyActionType.value       = arr[0][27];
        fm.ApplyActionTypeName.value   = arr[0][28];    
        fm.ApplyAction.value           = arr[0][29];
        fm.DeleteActionType.value      = arr[0][30];
        fm.DeleteActionTypeName.value  = arr[0][31];    
        fm.DeleteAction.value          = arr[0][32];
        fm.Together.value              = arr[0][33];
        fm.TogetherName.value          = arr[0][34];    

        //end
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
        fm.ActivityID.value           = "";
        fm.ActivityName.value         = "";
        fm.BusiType.value             = "";
        fm.BusiTypeName.value         = "";
        fm.ActivityFlag.value         = "";
        fm.ActivityFlagName.value     = "";
        fm.IsNeed.value               = "";
        fm.IsNeedName.value           = "";
        
        fm.BeforeInitType.value       = ""; 
        fm.BeforeInitTypeName.value   = "";
        fm.BeforeInit.value           = "";
        fm.AfterInitType.value        = "";
        fm.AfterInitTypeName.value    = "";
        fm.AfterInit.value            = "";
        fm.BeforeEndType.value        = "";
        fm.BeforeEndTypeName.value    = "";
        fm.BeforeEnd.value            = "";
        fm.AfterEndType.value         = "";
        fm.AfterEndTypeName.value     = "";
        fm.AfterEnd.value             = "";
        
        fm.ImpDegree.value            = "";
        fm.ImpDegreeName.value        = "";        
        fm.FunctionID.value           = "";
        fm.FunctionIDName.value       = ""; 
        
        fm.CreateActionType.value      = "";
        fm.CreateActionTypeName.value  = ""; 
        fm.CreateAction.value          = "";
        fm.ApplyActionType.value       = ""; 
        fm.ApplyActionTypeName.value   = "";
        fm.ApplyAction.value           = ""; 
        fm.DeleteActionType.value      = ""; 
        fm.DeleteActionTypeName.value  = "";
        fm.DeleteAction.value          = ""; 
        fm.Together.value              = ""; 
        fm.TogetherName.value          = ""; 
    }
    catch(ex)
    {
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSql1");   
    mySql.addSubPara(document.all('ActivityIDQ').value);    
    mySql.addSubPara(document.all('BusiTypeQ').value);
 
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}
function SaveClick()
{
    if (verifyInput2() == false) return false;
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);      
    var arr = easyExecSql(mySql.getString());
    if (arr>0)
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
    fm.action = "ActivityDefSave.jsp?OperFlag=INSERT||Activity";
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
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
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
    fm.action = "ActivityDefSave.jsp?OperFlag=MODIFY||Activity";
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
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
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
    fm.action = "ActivityDefSave.jsp?OperFlag=DELETE||Activity";
    document.getElementById("fm").submit();
}
function DefParamClick()
{
    if(document.all('ActivityID').value=="")
    {
        alert("请先保存数据或选择一条已有数据");
        return false;
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("数据库不存在该记录,请先保存活动节点信息!");
        return false;
    }
    var dialogURL="CommonMain.jsp?PageNo=ParamDef.jsp&Comment=参数定义&ActivityID="+document.all('ActivityID').value;
   // showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
   window.open (dialogURL,"newwindow","height=700;width=1000;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;  resizable=no;alwaysRaised:yes;");

}
function DefConditionClick()
{
    if(document.all('ActivityID').value=="")
    {
        alert("请先保存数据或选择一条已有数据");
        return false;
    }
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ActivityDefSQL3");
    mySql.addSubPara(document.all('ActivityID').value);  
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("数据库不存在该记录,请先保存活动节点信息!");
        return false;
    }
    window.open("CommonMain.jsp?PageNo=ConditionDef.jsp&Comment=转移条件定义");
}

function checkBusiType()
{
	if (document.all('BusiTypeQ').value==""||document.all('BusiTypeQ').value==null)
	{
		alert("请先选择一个业务类型")
		return false;
	}
}

function afterCodeSelect( cCodeName, Field )
{
    if (cCodeName == "functionid")
    {
        mySql = new SqlClass();
        mySql.setResourceName("workflow.ActivityDefSql");
        mySql.setSqlId("ActivityDefSQL4");
        mySql.addSubPara(Field.value);      	
        
        var  arr = easyExecSql(mySql.getString());
        fm.BusiType.value           = arr[0][0];
        fm.BusiTypeName.value       = arr[0][1];
    }  
}
