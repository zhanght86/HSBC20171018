//程序名称：WFEdorApprove.js
//程序功能：保全工作流-保全复核
//创建日期：2005-04-30 11:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPageAllGrid = new turnPageClass();
var turnPageSelfGrid = new turnPageClass();


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
			var iHeight=250;     //弹出窗口的高度; 
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
            easyQueryClickAll();
            easyQueryClickSelf(true);
        }
        catch (ex) {}
    }
}

/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickAll()
{
    // 书写SQL语句
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.WFGrpEdorApproveInputSql");//指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(manageCom+ "%%");//指定传入的参数
		mySql1.addSubPara(fm.OtherNo.value);//指定传入的参数
		mySql1.addSubPara(fm.OtherNoType.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorAppName.value);//指定传入的参数
		mySql1.addSubPara(fm.AppType.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.MakeDate.value);//指定传入的参数
		
		mySql1.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
		
		strSQL=mySql1.getString();

//    strSQL =  " select a.missionprop1, a.missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(a.missionprop3) ), "
//            + " a.missionprop11, a.missionprop12, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(a.missionprop5) ), "
//            + " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(a.missionprop7) ), "
//            + " b.operator, a.makedate, a.missionid, a.submissionid, a.activityid "
//            + " from lwmission a,lpedorapp b where 1=1  "
//            + " and a.activityid = '0000008007' "  //工作流保全-保全复核
//            + " and a.processid = '0000000000' "
//            + " and a.missionprop1 = b.edoracceptno "
//            + " and a.defaultoperator is null "
//            + getWherePart('a.missionprop1', 'EdorAcceptNo')
//            + getWherePart('a.missionprop2', 'OtherNo')
//            + getWherePart('a.missionprop3', 'OtherNoType')
//            + getWherePart('a.missionprop4', 'EdorAppName')
//            + getWherePart('a.missionprop5', 'AppType')
//            + getWherePart('a.missionprop7', 'ManageCom')
//            + getWherePart('a.MakeDate', 'MakeDate')
//            + " and a.missionprop7 like '" + manageCom + "%%'"
//            + " order by a.MakeDate, a.MakeTime ";

    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);

    return true;
}

/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSelf(isClickedApply)
{
    // 书写SQL语句
    var strSQL = "";
    
    var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.WFGrpEdorApproveInputSql");//指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(operator);//指定传入的参数
		strSQL=mySql2.getString();

//    strSQL =  " select missionprop1, missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//            + " missionprop11, missionprop12, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//            + " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//            + " createoperator, makedate, missionid, submissionid, activityid "
//            + " from lwmission where 1=1  "
//            + " and activityid = '0000008007' "  //工作流保全-保全复核
//            + " and processid = '0000000000' "
//            + " and defaultoperator ='" + operator + "'"
//            + " order by ModifyDate desc, ModifyTime desc";

            //+ " order by MakeDate, MakeTime ";

    turnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    turnPageSelfGrid.queryModal(strSQL, SelfGrid);

    if (SelfGrid.mulLineCount > 0 && isClickedApply)
    {
        SelfGrid.selOneRow(1);
    }

    return true;
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
    var tMissionID = SelfGrid.getRowColData(selno, 10);
    var tSubMissionID = SelfGrid.getRowColData(selno, 11);

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID;
    var newWindow = OpenWindowNew("../bq/EdorApproveFrame.jsp?Interface=../bq/GEdorApproveInput.jsp" + varSrc,"保全复核","left");

}

/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *  XinYQ rewrote on 2007-04-03
 *********************************************************************
 */
function applyMission()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要申请的任务！ ");
        return;
    }
    else
    {
        var sEdorAcceptNo = AllGrid.getRowColData(nSelNo, 1);
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("请先查询并选择一条任务！ ");
            return;
        }
        else
        {
            var sMissionID = AllGrid.getRowColData(nSelNo, 10);
            var sSubMissionID = AllGrid.getRowColData(nSelNo, 11);
            var sActivityID = AllGrid.getRowColData(nSelNo, 12);
            if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "")
            {
                alert("警告：无法获取工作流任务节点信息！ ");
                return;
            }
            else
            {
            	
            		
            		var tOperter = AllGrid.getRowColData(nSelNo, 8);
							  
								if(operator == tOperter)
								{
									alert("对不起,你不能审批自己的任务!");
									return;
								}
                var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
                var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
                //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
				var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
                document.forms(0).action = "WFGrpEdorApproveSave.jsp?EdorAcceptNo=" + sEdorAcceptNo + "&MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
                document.forms(0).submit();
            }
        }
    } //nSelNo > 0
}

/*********************************************************************
 *  打开工作流记事本查看页面
 *  描述: 打开工作流记事本查看页面
 *  参数  ：  无
 *  返回值：  无
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

    var MissionID = SelfGrid.getRowColData(selno, 10);
    var SubMissionID = SelfGrid.getRowColData(selno, 11);
    var ActivityID = SelfGrid.getRowColData(selno, 12);
    var OtherNo = SelfGrid.getRowColData(selno, 1);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}
