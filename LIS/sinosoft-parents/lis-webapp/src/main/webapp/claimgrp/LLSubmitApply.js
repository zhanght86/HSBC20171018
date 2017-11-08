var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var mySql = new SqlClass(); 
//保存按钮
function saveClick()
{ 
	if(fm.SubDesc.value=="" || fm.SubDesc.value ==null )
	{
		alert("请填写呈报描述");
		return;
	}
	
	//根据 呈报人所在机构 查询 分公司主任核赔员、及其机构代码
	var tManageCom=fm.ManageCom.value ;
	fm.MngCom.value = tManageCom.substring(0,4); //登陆信息中的管理机构的前四位代码，用于填充呈报节点中机构    

	fm.FilialeDirector.value ="";    //分公司主任核赔员     
    //补充需要提交字段，通过此页面提交字段默认字段值   
    fm.SubCount.value = "0";    //呈报次数     
    fm.InitPhase.value = "01";   //提起阶段
    fm.SubState.value = "0";   //呈报状态    　
    fm.fmtransact.value = "INSERT"; //提交
    submitForm();
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

    fm.action = './LLSubmitApplySave.jsp';
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
    tSaveFlag ="0";
}

//初始化时查询--为解决汉字传输乱码问题
function initQuery()
{
   /* var tSql = "select name from ldperson where "
             + " customerno = '" + document.all('CustomerNo').value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLSubmitApplyInputSql");
	mySql.setSqlId("LLSubmitApplySql1");
	mySql.addSubPara(document.all('CustomerNo').value );          
    var tName = easyExecSql(mySql.getString());
    if (tName)
    {
        document.all('CustomerName').value = tName;
    }
}
