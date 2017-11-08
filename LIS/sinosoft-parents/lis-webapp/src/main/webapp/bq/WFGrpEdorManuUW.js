/*******************************************************************************
 * @direction: 团单保全人工核保申请任务主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                  //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();            //全局变量, 查询结果翻页, 必须有
var turnPageAllGrid = new turnPageClass();     //全局变量, 共享工作队列查询结果翻页
var turnPageSelfGrid = new turnPageClass();    //全局变量, 我的任务队列查询结果翻页
var sqlresourcename = "bq.WFGrpEdorManuUWInputSql";
/*============================================================================*/

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            //MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            queryAllGrid();
            querySelfGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询共享工作队列
 */
function queryAllGrid()
{
/*
    var QuerySQL = "select a.MissionID, "
                 +        "a.SubMissionID, "
                 +        "a.MissionProp1, "
                 +        "a.MissionProp2, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'gedornotype' "
                 +            "and trim(Code) = a.MissionProp3), "
                 +        "a.MissionProp4, "
                 +        "a.MissionProp7, "
                 +        "a.ActivityStatus, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'uwactivitystatus' "
                 +            "and trim(Code) = a.ActivityStatus), "
                 +        "a.CreateOperator, "
                 +        "a.MakeDate "
                 +   "from LWMission a "
                 +  "where 1 = 1 "
                 +    "and a.ProcessID = '0000000000' "
                 +    "and a.DefaultOperator is null "    //没有操作员
                 //判断当前操作员是否有等于任务的核保权限，是则允许查询
                 +" and exists (select 1 from LDUWUser b, LPEdorApp c where b.usercode = '"+document.all("LoginOperator").value+"' "
					       +" and b.UWtype = '3' and c.edoracceptno=a.missionprop1 and b.UWPopedom =c.uwgrade) "
                 +  getWherePart("a.ActivityID", "ActivityID")
                 +  getWherePart("a.MissionProp1", "EdorAcceptNo1")
                 +  getWherePart("a.MissionProp2", "OtherNo1")
                 +  getWherePart("a.MissionProp3", "OtherNoType1")
                 +  getWherePart("a.MissionProp4", "EdorAppName1")
                 +  getWherePart("a.MissionProp7", "ManageCom1", "like")
                 +  getWherePart("a.MissionProp7", "LoginManageCom", "like")
                 +  getWherePart("a.ActivityStatus", "UWState1")
                 +  getWherePart("a.MakeDate", "MakeDate1")
                 +  "order by a.MissionProp1 desc, a.MakeDate desc";
                 
              */   
        var sqlid1="WFGrpEdorManuUWInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(document.all("LoginOperator").value);
		mySql1.addSubPara(fm.ActivityID.value);
		mySql1.addSubPara(fm.EdorAcceptNo1.value);
		mySql1.addSubPara(fm.OtherNo1.value);
		mySql1.addSubPara(fm.OtherNoType1.value);
		mySql1.addSubPara(fm.EdorAppName1.value);
		mySql1.addSubPara(fm.ManageCom1.value);
		mySql1.addSubPara(fm.LoginManageCom.value);
		mySql1.addSubPara(fm.UWState1.value);
		mySql1.addSubPara(fm.MakeDate1.value);
                 
         var QuerySQL =   mySql1.getString();      
                 
                 
    //alert(QuerySQL);
    try
    {
        turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
        turnPageAllGrid.queryModal(QuerySQL, AllGrid);
    }
    catch (ex)
    {
        alert("警告：查询共享工作队列保全信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 查询我的任务队列
 */
function querySelfGrid()
{
/*
    var QuerySQL = "select a.MissionID, "
                 +        "a.SubMissionID, "
                 +        "a.MissionProp1, "
                 +        "a.MissionProp2, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'gedornotype' "
                 +            "and trim(Code) = a.MissionProp3), "
                 +        "a.MissionProp4, "
                 +        "a.MissionProp7, "
                 +        "a.ActivityStatus, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'uwactivitystatus' "
                 +            "and trim(Code) = a.ActivityStatus), "
                 +        "a.CreateOperator, "
                 +        "a.MakeDate "
                 +   "from LWMission a "
                 +  "where 1 = 1 "
                 +    "and a.ProcessID = '0000000000' "
                 +  getWherePart("a.DefaultOperator", "LoginOperator")    //当前操作员
                 +  getWherePart("a.ActivityID", "ActivityID")
                 +  getWherePart("a.MissionProp1", "EdorAcceptNo2")
                 +  getWherePart("a.MissionProp2", "OtherNo2")
                 +  getWherePart("a.MissionProp3", "OtherNoType2")
                 +  getWherePart("a.MissionProp4", "EdorAppName2")
                 +  getWherePart("a.MissionProp7", "ManageCom2", "like")
                 +  getWherePart("a.MissionProp7", "LoginManageCom", "like")
                 +  getWherePart("a.ActivityStatus", "UWState2")
                 +  getWherePart("a.MakeDate", "MakeDate2")
                 +  "order by a.MissionProp1 desc, a.MakeDate desc";
   */
   var sqlid2="WFGrpEdorManuUWInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.LoginOperator.value);
		mySql2.addSubPara(fm.ActivityID.value);
		mySql2.addSubPara(fm.EdorAcceptNo2.value);
		mySql2.addSubPara(fm.OtherNo2.value);
		mySql2.addSubPara(fm.OtherNoType2.value);
		mySql2.addSubPara(fm.EdorAppName2.value);
		mySql2.addSubPara(fm.ManageCom2.value);
		mySql2.addSubPara(fm.LoginManageCom.value);
		mySql2.addSubPara(fm.UWState2.value);
		mySql2.addSubPara(fm.MakeDate2.value);
                 
         var QuerySQL =   mySql2.getString();  
   
    //alert(QuerySQL);
    try
    {
        turnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
        turnPageSelfGrid.queryModal(QuerySQL, SelfGrid);
    }
    catch (ex)
    {
        alert("警告：查询我的任务队列保全信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 检查操作员是否具有申请核保权限
 */
function hasEdorPopedom()
{
    var sOperator;
    try
    {
        sOperator = document.getElementsByName("LoginOperator")[0].value;
    }
    catch (ex) {}
    if (sOperator == null || trim(sOperator) == "")
    {
        alert("无法获取当前登录用户信息。检查保全核保权限失败！ ");
        return false;
    }
    else
    {

        var QuerySQL, arrResult;
        /*
        QuerySQL = "select EdorPopedom "
                 +   "from LDUWUser "
                 +  "where UserCode = '" + sOperator + "' and uwtype = '3'";
       */
       var sqlid3="WFGrpEdorManuUWInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(sOperator);

       QuerySQL =mySql3.getString();
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询当前登录用户保全核保权限出现异常！ ");
            return false;
        }
        if (arrResult == null || trim(arrResult[0][0]) != "1")
        {
            alert("对不起，您没有保全核保权限！ ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/**
 * 申请共享任务到我的任务
 */
function applyTheMission()
{
    if (!hasEdorPopedom()) return;
    //如果具有保全核保权限,申请任务
    var nSelNo;
    try
    {
        nSelNo = AllGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要申请的任务！ ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sEdorAcceptNo, sActivityID;
        try
        {
            sMissionID = AllGrid.getRowColData(nSelNo, 1);
            sSubMissionID = AllGrid.getRowColData(nSelNo, 2);
            sEdorAcceptNo = AllGrid.getRowColData(nSelNo, 3);
            sActivityID = document.getElementsByName("ActivityID")[0].value;
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("请先查询并选择一条任务！ ");
            return;
        }
        var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
		var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
        //调用通用申请 Mission 页面
        document.forms[0].action = "MissionApply.jsp?MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
        document.forms[0].submit();
    } //nSelNo > 0
}

/*============================================================================*/

/**
 * 进入人工核保
 */
function gotoManuUWDeal()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要处理的任务！ ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sEdorAcceptNo, sActivityStatus;
        try
        {
            sMissionID = SelfGrid.getRowColData(nSelNo, 1);
            sSubMissionID = SelfGrid.getRowColData(nSelNo, 2);
            sEdorAcceptNo = SelfGrid.getRowColData(nSelNo, 3);
            sActivityStatus = SelfGrid.getRowColData(nSelNo, 8);
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("请先查询并选择一条任务！ ");
            return;
        }
        var sOpenWinURL = "GEdorAppManuUWFrame.jsp?Interface=GEdorAppManuUWInput.jsp";
        var sParameters = "EdorAcceptNo=" + sEdorAcceptNo + "&MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityStatus=" + sActivityStatus;
        OpenWindowNew(sOpenWinURL + "&" + sParameters, "团单保全人工核保整单", "left");
    }
}

/*============================================================================*/

/**
 * 保全明细查询
 */
function showEdorDetail()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查询的任务！ ");
        return;
    }
    else
    {
        var sEdorAcceptNo;
        try
        {
            sEdorAcceptNo = SelfGrid.getRowColData(nSelNo, 3);
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("请先查询并选择一条任务！ ");
            return;
        }
        //var sOpenWinURL = "../sys/BqDetailQueryFrame.jsp?Interface=../sys/BqDetailQuery.jsp";
        //var sParameters = "EdorAcceptNo=" + sEdorAcceptNo;
        //OpenWindowNew(sOpenWinURL + "&" + sParameters, "保全明细查询", "left");
    }
}

/*============================================================================*/

/**
 * 记事本查看
 */
function showNotePad()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的任务！ ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sOtherNo, sActivityID;
        try
        {
            sMissionID = SelfGrid.getRowColData(nSelNo, 1);
            sSubMissionID = SelfGrid.getRowColData(nSelNo, 2);
            sOtherNo = SelfGrid.getRowColData(nSelNo, 4);
            sActivityID = document.getElementsByName("ActivityID")[0].value;
        }
        catch (ex) {}
        if (sOtherNo == null || trim(sOtherNo) == "")
        {
            alert("请先查询并选择一条任务！ ");
            return;
        }
        var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
        var sParameters = "MissionID="+ sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID + "&PrtNo="+ sOtherNo + "&NoType=1";
        OpenWindowNew(sOpenWinURL + "&" + sParameters, "工作流记事本查看", "left");
    }
}

/*============================================================================*/


//<!-- JavaScript Document END -->
