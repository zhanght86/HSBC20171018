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

//add by liuyuxiao 2011-05-24
function afterCodeSelect( cCodeName, Field ){
	if(cCodeName=="querycodetype"){
		if(fm.CodeType.value == 'functionid'){
			document.getElementById('menuNod').style.display = '';
			fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
			fm.OtherSignCode.style.display = '';
        	fm.OtherSignName.style.display = '';
		}
		else{
			fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
        	document.getElementById('menuNod').style.display = 'none';
			fm.OtherSignCode.style.display = 'none';
        	fm.OtherSignName.style.display = 'none';
		}
	}
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
        fm.CodeType.value = TICollectGrid.getRowColData(selno, 1);
        fm.CodeTypeName.value = TICollectGrid.getRowColData(selno, 2);
        fm.Code.value = TICollectGrid.getRowColData(selno, 3);
        fm.CodeName.value = TICollectGrid.getRowColData(selno, 4);
        fm.CodeAlias.value = TICollectGrid.getRowColData(selno, 5);
        //add by liuyuxiao 2011-05-23 当点击radioButton后对页面中OtherSign相关组件的值和显示的操作
        if(fm.CodeTypeQ.value == 'functionid'){
        	document.getElementById('menuNod').style.display = '';
        	fm.OtherSignCode.style.display = '';
        	fm.OtherSignName.style.display = '';
        	fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
        	fm.OtherSignCode.value = TICollectGrid.getRowColData(selno, 6);
        	showOneCodeName('querymenu','OtherSignCode','OtherSignName');	//公用方法，根据code查name
        }
        else{
        	document.getElementById('menuNod').style.display = 'none';
        	fm.OtherSignCode.value = '';
        	fm.OtherSignName.value = '';
        	fm.OtherSignCode.style.display = 'none';
        	fm.OtherSignName.style.display = 'none';
        }
        //end add by liuyuxiao
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
    
    document.all('CodeType').value = "";
    document.all('CodeTypeName').value = "";
    document.all('Code').value = "";
    document.all('CodeName').value = "";
    document.all('CodeAlias').value = "";
}

function queryClick()
{
    if (document.all('CodeTypeQ').value == "")
    {
        alert("请先择代码类型!");
        return false;
    }
 
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql1");
    mySql.addSubPara(document.all('CodeTypeQ').value);    
    
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}
function SaveClick()
{
    if (verifyInput2() == false) return false;
    if(fm.CodeType.value=="busitype"&&fm.Code.value.length>4)
    {
       alert("长度不能超过4");
       return false;	
    }
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql2");
    mySql.addSubPara(document.all('CodeType').value); 
    mySql.addSubPara(document.all('Code').value);      
    
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

    fm.action = "BaseDataManageSave.jsp?OperFlag=INSERT";
    document.getElementById("fm").submit();
}
function ModifyClick()
{
    if (verifyInput2() == false) return false;
    if(fm.CodeType.value=="busitype"&&fm.Code.value.length>4)
    {
       alert("长度不能超过4");
       return false;	
    }    
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql2");
    mySql.addSubPara(document.all('CodeType').value); 
    mySql.addSubPara(document.all('Code').value); 
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
    fm.action = "BaseDataManageSave.jsp?OperFlag=MODIFY";
    document.getElementById("fm").submit();
}
function DeleteClick()
{
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.BaseDataSql");
    mySql.setSqlId("BaseDataSql2");
    mySql.addSubPara(document.all('CodeType').value); 
    mySql.addSubPara(document.all('Code').value); 
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
    fm.action = "BaseDataManageSave.jsp?OperFlag=DELETE";
    document.getElementById("fm").submit();
}