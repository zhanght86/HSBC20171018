//程序名称：WFEdorCancel.js
//程序功能：保全工作流-保全撤销
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPageAllGrid = new turnPageClass();
var divTurnPageSelfGrid = new turnPageClass();


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
            //MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
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

/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *********************************************************************
 */
function queryAllGrid()
{
    // 书写SQL语句
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.WFGrpEdorCancelInputSql");//指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(manageCom+"%%");//指定传入的参数
		mySql1.addSubPara(fm.OtherNo.value);//指定传入的参数
		mySql1.addSubPara(fm.OtherNoType.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorAppName.value);//指定传入的参数
		mySql1.addSubPara(fm.AppType.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.MakeDate.value);//指定传入的参数
		
		
		mySql1.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
		
		strSQL=mySql1.getString();
    
//    strSQL =  " select missionprop1, missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//            + " missionprop11, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//            + " missionprop7, "
//            + " createoperator, makedate, missionid, submissionid, activityid "
//            + " from lwmission a where 1=1  "
//            + " and activityid = '0000008008' "  //工作流保全-保全撤销
//            + " and processid = '0000000000' "
//            + " and defaultoperator is null "
//            + getWherePart('missionprop1', 'EdorAcceptNo')
//            + getWherePart('missionprop2', 'OtherNo')
//            + getWherePart('missionprop3', 'OtherNoType')
//            + getWherePart('missionprop4', 'EdorAppName')
//            + getWherePart('missionprop5', 'AppType')
//            + getWherePart('missionprop7', 'ManageCom')
//            + getWherePart('MakeDate', 'MakeDate')
//            + " and missionprop7 like '" + manageCom + "%%'"
//            //XinYQ added on 2007-05-17 : 逾期终止 核保终止 复核终止 的保全不显示
//            + " and not exists (select 'X' from LPEdorApp where 1 = 1 and EdorAcceptNo = a.MissionProp1 and EdorState in ('4', '8', '9'))"
//            + " order by ModifyDate desc, ModifyTime desc";
    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);
}

/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *********************************************************************
 */
function querySelfGrid()
{
    // 书写SQL语句
    var strSQL = "";
    
    var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.WFGrpEdorCancelInputSql");//指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(operator);//指定传入的参数
		strSQL=mySql2.getString();
    
//    strSQL =  " select missionprop1, missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//            + " missionprop11, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//            + " missionprop7, "
//            + " createoperator, makedate, missionid, submissionid, activityid "
//            + " from lwmission where 1=1  "
//            + " and activityid = '0000008008' "  //工作流保全-保全撤销
//            + " and processid = '0000000000' "
//            + " and defaultoperator ='" + operator + "'"
//            + " order by ModifyDate desc, ModifyTime desc";
    divTurnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    divTurnPageSelfGrid.queryModal(strSQL, SelfGrid);
}

/*********************************************************************
 *  跳转到具体的业务处理页面
 *  描述: 跳转到具体的业务处理页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GoToBusiDeal()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }
    var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
    var tMissionID = SelfGrid.getRowColData(selno, 9);
    var tSubMissionID = SelfGrid.getRowColData(selno, 10);
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID;
    var newWindow = OpenWindowNew("../bq/EdorCancelFrame.jsp?Interface= ../bq/GEdorCancelInput.jsp" + varSrc,"保全申请撤销","left");
}

/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *********************************************************************
 */
function applyMission()
{

    var selno = AllGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要申请的任务！");
          return;
    }
    fm.MissionID.value = AllGrid.getRowColData(selno, 9);
    fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
    fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("选择任务为空！");
          return;
    }
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  打开工作流记事本查看页面
 *  描述: 打开工作流记事本查看页面
 *********************************************************************
 */
function showNotePad()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }
    var MissionID = SelfGrid.getRowColData(selno, 9);
    var SubMissionID = SelfGrid.getRowColData(selno, 10);
    var ActivityID = SelfGrid.getRowColData(selno, 11);
    var OtherNo = SelfGrid.getRowColData(selno, 1);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}
