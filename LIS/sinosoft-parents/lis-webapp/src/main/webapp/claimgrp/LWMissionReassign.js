var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//理赔用户查询返回信息（用户编码 、用户姓名、机构编码）
function afterQuery( arrQueryResult ) 
{ 
    var arrResult = new Array();
    if( arrQueryResult != null ) 
    {
        arrResult = arrQueryResult;
		fm.DesignateOperator.value = arrResult[0];
	}
}

//[查询任务]按钮
function LWMissionQueryClick()
{
	fm.DefaultOperator.value="";
	fm.DesignateOperator.value="";	
	var strSQL="select missionprop1,missionprop2,missionprop3,missionprop4,(case missionprop5 when '0' then '0-男' when '1' then '1-女' end),missionprop6,"
			+"(case missionprop7 when '1' then '1-个人' when '2' then '2-团体' when '3' then '3-银代' end),"
			+ "defaultoperator,missionid,submissionid,activityid from lwmission where 1=1 "
			+ " and processid = '0000000005' "
			+ " and missionprop20 like '" +document.all('ComCode').value+ "%%'"
			+ getWherePart( 'ActivityID','ActivityIDQ')
			+ getWherePart( 'missionprop1','ClmNOQ')
			+ getWherePart( 'defaultoperator','OperatorQ')				 
			+ " order by missionid";
	var arrResult = easyExecSql(strSQL);
	if(arrResult==null ||arrResult=="")
	{
		LWMissionGrid.clearData();
		alert("没有查询到任何任务记录！请重新输入条件。");
		fm.ActivityIDQ.value="";
		fm.ActivityIDQName.value="";		
		fm.ClmNOQ.value="";
		fm.OperatorQ.value="";
		return;
	}		
   else
   	{
//   		displayMultiline(arrResult,LWMissionGrid);
		turnPage.queryModal(strSQL, LWMissionGrid);  
   	}
}

//LWMissionGrid的单选钮响应函数
function LWMissionGridClick()
{
	var tRow = LWMissionGrid.getSelNo();
	fm.MissionID.value=LWMissionGrid.getRowColData(tRow-1,9);
	fm.SubMissionID.value=LWMissionGrid.getRowColData(tRow-1,10);
	fm.ActivityID.value=LWMissionGrid.getRowColData(tRow-1,11);
	fm.DefaultOperator.value=LWMissionGrid.getRowColData(tRow-1,8);
}

//[查询理赔用户]按钮
function LLClaimUserQueryClick()
{
	var tRow = LWMissionGrid.getSelNo();
	if( tRow < 1||tRow == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}
	var strUrl="LLClaimUserQueryMain.jsp";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//[指定确认]按钮
function DesignateConfirmClick()
{
	var tRow = LWMissionGrid.getSelNo();
	if( tRow < 1||tRow == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}
//	if(fm.DesignateOperator.value=="" ||fm.DesignateOperator.value==null)
//	{
//		alert( "请选择操作员！" );
//		return;		
//	}
	fm.fmtransact.value="User||UPDATE";
	fm.action="LWMissionReassignSave.jsp";
	submitForm();
}

//确认后刷新页面(默认操作人)
function Renewpage()
{
	var tRow = LWMissionGrid.getSelNo()-1;
	LWMissionGrid.setRowColData(tRow,8,fm.DesignateOperator.value);
	fm.DefaultOperator.value=fm.DesignateOperator.value;
}

//公共提交方法
function submitForm()
{
    //提交数据
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

    fm.submit(); //提交
    tSaveFlag ="0";    
}

//预付保存提交后操作,返回
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
    Renewpage();
}
