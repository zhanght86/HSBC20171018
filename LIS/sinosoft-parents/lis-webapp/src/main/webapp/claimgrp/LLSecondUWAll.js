//程序名称：LLSecondUWAllAll.js
//程序功能：理赔人工核保获取队列
//创建日期：2005-1-28 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人 yuejw   更新日期     更新原因/内容
var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


//查询按钮------查询[理赔人工核保队列]
function queryClick()
{
	initLLCUWBatchGridQuery();
}
//重置按钮------
function cancleClick()
{
	fm.QCaseNo.value="";
	fm.QInsuredNo.value="";
	fm.QInsuredName.value="";
	fm.QClaimRelFlag.value="";
	fm.QClaimRelFlagName.value="";
	fm.QMngCom.value="";
	
}

//查询[理赔人工核保队列]------共享池
function initLLCUWBatchGridQuery()
{
//	var strSQL="";   	
	var strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,case missionprop5 when '0' then '0--相关' when '1' then '1--不相关'   end,missionprop20,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000005505' "
           + " and processid = '0000000005'"
           + " and DefaultOperator is null"
           + getWherePart( 'missionprop1' ,'QCaseNo')
           + getWherePart( 'missionprop3' ,'QInsuredNo')
           + getWherePart( 'missionprop4' ,'QInsuredName','like')
           + getWherePart( 'missionprop5' ,'QClaimRelFlag')
           + getWherePart( 'missionprop20' ,'QMngCom')  //<机构----是否需要，待定>
           + " order by missionprop1";	
    turnPage.pageLineNum=5;
	turnPage.queryModal(strSQL, LLCUWBatchGrid);
}

//查询[理赔人工核保队列]------个人队列
function initSelfLLCUWBatchGridQuery()
{
//	var strSQL="";   	
	var strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop20,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000005505' "
           + " and processid = '0000000005'"
           + " and DefaultOperator ='"+fm.Operator.value+"'"
           + " order by missionprop1";	
    turnPage2.pageLineNum=5;
	turnPage2.queryModal(strSQL, SelfLLCUWBatchGrid);
}

//[申请任务]按钮
function applyClick()
{
	var tSel = LLCUWBatchGrid.getSelNo()-1;
	if(tSel<0)
	{
		alert("请选择一条任务记录");
		return;	
	}
	fm.MissionID.value = LLCUWBatchGrid.getRowColData(tSel, 7);
	fm.SubMissionID.value=LLCUWBatchGrid.getRowColData(tSel, 8);
	fm.ActivityID.value= LLCUWBatchGrid.getRowColData(tSel, 9);
	fm.action = "./LLSecondUWAllSave.jsp";
	submitForm(); //提交
}
/*********************************************************************
 *  描述:进入核保界面
 *********************************************************************
 */
function SelfLLCUWBatchGridClick()
{
	var tSel = SelfLLCUWBatchGrid.getSelNo()-1;
	var tCaseNo = SelfLLCUWBatchGrid.getRowColData(tSel,1); 
	var tBatNo = SelfLLCUWBatchGrid.getRowColData(tSel,2); 	
	var tInsuredNo = SelfLLCUWBatchGrid.getRowColData(tSel,3)  
	var tClaimRelFlag = SelfLLCUWBatchGrid.getRowColData(tSel,5)  	
	var tMissionid = SelfLLCUWBatchGrid.getRowColData(tSel,7)  	
	var tSubmissionid = SelfLLCUWBatchGrid.getRowColData(tSel,8)  	
	var tActivityid = SelfLLCUWBatchGrid.getRowColData(tSel,9)  	
	
	var strURL="./LLSecondUWALLInputMain.jsp";
	strURL += "?CaseNo=" + tCaseNo ;	//赔案号
    strURL += "&BatNo=" + tBatNo ; //批次号			    
	strURL += "&InsuredNo=" + tInsuredNo ;   //出险人客户号 
	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //赔案相关标志 	
	strURL += "&Missionid=" + tMissionid ;   //任务ID 
	strURL += "&Submissionid=" + tSubmissionid ;   //子任务ID 
	strURL += "&Activityid=" + tActivityid ;   //节点号 	
	window.location=strURL;
//	window.location="./LLSecondUWALLInputMain.jsp?CaseNo="+tCaseNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo; 
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

    fm.submit(); //提交
    tSaveFlag ="0";
}

/*********************************************************************
 *  提交后操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	
	if (FlagStr == "Fail" )
	{                 
		alert(content);
	}
	else
	{ 
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		window.location="LLSecondUWAllInput.jsp";
	}
}

