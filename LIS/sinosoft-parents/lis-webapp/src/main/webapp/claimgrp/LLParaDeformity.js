var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";

/*[公共函数]提交到后台*/
function submitDeformityForm()
{
  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLParaDeformitySave.jsp";
    fm.submit(); //提交

}


/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterDeformitySubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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

        mOperate = '';
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

        if ( mOperate=="Deformity||INSERT"){
            DeformityAddAfterClick();
        }
        if ( mOperate=="Deformity||DELETE"){
            DeformityDeleteAfterClick();
        }
        if ( mOperate=="Deformity||UPDATE"){
            DeformityEditAfterClick();
        }
    }

}


/*[增加]按钮对应操作*/
function DeformityAddClick()
{
	if (beforeDeformitySubmit())
	{
    mOperate="Deformity||INSERT";
    submitDeformityForm();
  }
}
/*[修改]按钮对应操作*/
function DeformityEditClick()
{
    mOperate="Deformity||UPDATE";
    submitDeformityForm();
}
/*[删除]按钮对应操作*/
function DeformityDeleteClick()
{
    if (confirm("您确实想删除该记录吗?")){
        mOperate="Deformity||DELETE";
        submitDeformityForm();
    }else{
        mOperate="";
    }
}
/*[重置]按钮对应操作*/
function DeformityResetClick()
{
    //清空录入信息
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoName.value = '';
    fm.DefoRate.value = '';
    
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
}


/*[增加]按钮后台成功执行后对应的操作*/
function DeformityAddAfterClick()
{
    /*//需要返回成功标志后，才进行以下操作
    var tNo = ParaDeformityGrid.mulLineCount;
    if (tNo<=0)
    {
        tNo = 0;
    }

    ParaDeformityGrid.addOne();
    ParaDeformityGrid.setRowColData(tNo, 1,fm.DefoType.value);           	//伤残类型
    ParaDeformityGrid.setRowColData(tNo, 2,fm.DefoGrade.value);						//伤残级别
    ParaDeformityGrid.setRowColData(tNo, 3,fm.DefoCode.value);						//伤残代码
    ParaDeformityGrid.setRowColData(tNo, 4,fm.DefoName.value);						//伤残级别名称
    ParaDeformityGrid.setRowColData(tNo, 5,fm.DefoRate.value);						//残疾给付比例*/
    
    var strSql="";
    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
  
    //显示所有数据
    turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(strSql,ParaDeformityGrid);
  

    //清空界面录入信息
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoName.value = '';
    fm.DefoRate.value = '';

    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
}

/*[删除]按钮后台成功执行后对应的操作*/
function DeformityDeleteAfterClick()
{
        //需要返回成功标志后，才进行以下操作
        //ParaDeformityGrid.delRadioTrueLine();
        var strSql="";
    		strSql = "select * from LLParaDeformity order by DefoType,DefoCode" ;
  
    		//显示所有数据
    		turnPage.pageLineNum=10;    //每页10条
    		turnPage.queryModal(strSql,ParaDeformityGrid);
    
        //清空录入信息
		    fm.DefoType.value = '';
		    fm.DefoTypeName.value = '';
		    fm.DefoGrade.value = '';
		    fm.DefoGradeName.value = '';
		    fm.DefoCode.value = '';
		    fm.DefoName.value = '';
		    fm.DefoRate.value = '';
}


/*理赔权限信息MulLine]的触发函数*/
function getParaDeformityGrid(spanID,parm)
{

    //得到MulLine的理赔权限信息
    var tNo = ParaDeformityGrid.getSelNo();

    //赋值信息
    fm.DefoType.value = ParaDeformityGrid.getRowColData(tNo - 1,1);
    fm.DefoGrade.value = ParaDeformityGrid.getRowColData(tNo - 1,2);
    fm.DefoGradeName.value = ParaDeformityGrid.getRowColData(tNo - 1,3);
    fm.DefoCode.value = ParaDeformityGrid.getRowColData(tNo - 1,4);
    fm.DefoName.value = ParaDeformityGrid.getRowColData(tNo - 1,5);
    fm.DefoRate.value = ParaDeformityGrid.getRowColData(tNo - 1,6);
  
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = false;

}

//理赔权限信息查询
function queryParaDeformity()
{
  	var strSql="";
    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
  
    //显示所有数据
    turnPage.pageLineNum=10;    //每页10条
    turnPage.queryModal(strSql,ParaDeformityGrid);
}

/*提交后台前的页面数据校验*/
function beforeDeformitySubmit()
{
		var strDefoType = fm.DefoType.value;//伤残类型
		var strDefoCode = fm.DefoCode.value;//伤残代码
	
    
    if (strDefoType == null || strDefoType == '')
    {
        alert("伤残类型不能为空！");
        return false;
    }
    if (strDefoCode == null || strDefoCode == '')
    {
        alert("伤残代码不能为空！");
        return false;
    }
    return true;
}




