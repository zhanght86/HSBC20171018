//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();

//"查询"按钮
function LLClaimAuditGridQueryClick()
{
	LLClaimAuditGridQuery();
}
// 初始化表格LLClaimAuditGrid查询
function LLClaimAuditGridQuery()
{
    initLLClaimAuditGrid();
    var strSQL = "";
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop11,missionprop12,missionprop19,missionprop20,missionid,submissionid,activityid,missionprop18 from lwmission where 1=1 "
           + getWherePart( 'missionprop1','RptNo' )
		   + getWherePart( 'missionprop3','CustomerNo' )
           + getWherePart( 'missionprop4','CustomerName' )
           + " and activityid = '0000005035'"
           + " and processid = '0000000005'"
           + " and missionprop12 = 'A'"  //来自报案
           + " and (missionprop11 = '0' or missionprop11 is null)" //  未预付       
           + " and DefaultOperator is null "   //必须需要该条件
           + " order by missionprop1";
//    alert(strSQL);       
    turnPage.queryModal(strSQL,LLClaimAuditGrid);
}

// 初始化表格LLClaimPrepayGrid查询预付处理队列，用于做“预付处理”
function LLClaimPrepayGridQuery()
{
    //initLLClaimPrepayGrid();
    var strSQL = "";
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000005025'"
           + " and processid = '0000000005'"
           + " and missionprop11 = '" + fm.Operator.value +"'"
           + " order by missionprop1";     
    turnPage2.queryModal(strSQL,LLClaimPrepayGrid);
}


//LLClaimAuditGrid表格响应的函数名
function LLClaimAuditGridClick()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	fm.tRptNo.value = LLClaimAuditGrid.getRowColData(selno, 1);//"赔案号" 
	fm.tRptorState.value = LLClaimAuditGrid.getRowColData(selno, 2);//"报案状态"
	fm.tCustomerNo.value = LLClaimAuditGrid.getRowColData(selno, 3);//"出险人编码"
	fm.tCustomerName.value = LLClaimAuditGrid.getRowColData(selno, 4);//"出险人姓名 
	fm.tCustomerSex.value = LLClaimAuditGrid.getRowColData(selno, 5);//"出险人性别"
	fm.tAccDate.value = LLClaimAuditGrid.getRowColData(selno, 6);//"出险日期" 
	fm.tRgtClass.value = LLClaimAuditGrid.getRowColData(selno, 7);//"申请类型" 
	fm.tRgtObj.value = LLClaimAuditGrid.getRowColData(selno, 8);//"号码类型" 
	fm.tRgtObjNo.value = LLClaimAuditGrid.getRowColData(selno, 9);//"其他号码" 
	fm.tPopedom.value = LLClaimAuditGrid.getRowColData(selno, 10);//"核赔师权限" 
	fm.tPrepayFlag.value = LLClaimAuditGrid.getRowColData(selno, 11);//"预付标志" 
	fm.tComeWhere.value = LLClaimAuditGrid.getRowColData(selno, 12);//"来自" 
	fm.tAuditer.value = LLClaimAuditGrid.getRowColData(selno, 13);//"审核操作人" 
	fm.tMngCom.value = LLClaimAuditGrid.getRowColData(selno, 14);//"" 	
	fm.tComFlag.value = LLClaimAuditGrid.getRowColData(selno, 18);//"" 	权限跨级标志
	fm.tMissionID.value = LLClaimAuditGrid.getRowColData(selno, 15);
	fm.tSubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 16);
	fm.tActivityID.value = LLClaimAuditGrid.getRowColData(selno,17);	
}

//LLClaimPrepayGrid表格响应的函数名
function LLClaimPrepayGridClick()
{

	var selno = LLClaimPrepayGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要作预付处理的赔案！");
	      return;
	}		
	var tClmNO = LLClaimPrepayGrid.getRowColData(selno,1);//赔案号（立案号）
	var tCustomerNo=LLClaimPrepayGrid.getRowColData(selno,3);//客户号
	var tMissionID=LLClaimPrepayGrid.getRowColData(selno,7);//任务ID
	var tSubMissionID=LLClaimPrepayGrid.getRowColData(selno,8);//子任务ID
	var tActivityID=LLClaimPrepayGrid.getRowColData(selno,9);//当前活动ID	
	var strUrl="LLClaimPrepayInput.jsp?ClmNo="+tClmNO+"&CustomerNo="+tCustomerNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
//    location.href="LLClaimPrepayInput.jsp?";
    location.href=strUrl;

}

//"申请"按钮，从待审核队列中提起任务后，则将待审核节点消亡，产生预付节点，准备进行预付处理
function ApplyLLClaimAudit()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}
	fm.action = "./LLClaimPrepayMissSave.jsp";
	submitForm(); //提交
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

//提交后操作,返回
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
    LLClaimAuditGridQuery();    // 初始化表格LLClaimAuditGrid查询
    LLClaimPrepayGridQuery();//刷新查询预付处理队列，用于做“预付处理”
}
