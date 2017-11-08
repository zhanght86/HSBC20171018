
//程序功能：保全工作流-自动核保
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
/*********************************************************************
 *  查询工作流共享池
 *  描述:查询工作流共享池
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickAll()
{
	// 初始化表格
	initAllGrid();

	// 书写SQL语句
	var strSQL = "";
	
	
	var sqlid1="WFGEdorAutoUWInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.WFGEdorAutoUWInputSql");//指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara( manageCom + "%%");//指定传入的参数
	mySql1.addSubPara(fm.OtherNo.value);//指定传入的参数
	mySql1.addSubPara(fm.OtherNoType.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorAppName.value);//指定传入的参数
	mySql1.addSubPara(fm.AppType.value);//指定传入的参数
	mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
	mySql1.addSubPara(fm.MakeDate.value);//指定传入的参数
	
	mySql1.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
	strSQL=mySql1.getString();
	
//	strSQL =  " select missionprop1, missionprop2, " 
//			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
//			+ " missionprop11,"
//			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//			+ " createoperator, makedate, missionid, submissionid, activityid, "
//			+ " MissionProp3, MissionProp5, MissionProp7, MissionProp12 "
//			+ " from lwmission where 1=1  "
//			+ " and activityid = '0000008004' "  //工作流保全-自动核保
//			+ " and processid = '0000000000' "
//			+ " and defaultoperator is null "
//			+ getWherePart('missionprop1', 'EdorAcceptNo')
//			+ getWherePart('missionprop2', 'OtherNo')
//			+ getWherePart('missionprop3', 'OtherNoType')	
//			+ getWherePart('missionprop4', 'EdorAppName')
//			+ getWherePart('missionprop5', 'AppType')
//			+ getWherePart('missionprop7', 'ManageCom','like')	
//			+ getWherePart('MakeDate', 'MakeDate')
//			+ " and missionprop7 like '" + manageCom + "%%'"	
//			+ " order by MakeDate, MakeTime ";
		
	turnPage.queryModal(strSQL, AllGrid);
	
	return true;
}

/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSelf()
{
	// 初始化表格
	initSelfGrid();

	// 书写SQL语句
	var strSQL = "";
	
	var sqlid2="WFGEdorAutoUWInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.WFGEdorAutoUWInputSql");//指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(operator);//指定传入的参数
	strSQL=mySql2.getString();
	
//	strSQL =  " select missionprop1, missionprop2, " 
//			+ " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
//			+ " missionprop11, "
//			+ " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//			+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//			+ " createoperator, makedate, missionid, submissionid, activityid,"
//			+ " MissionProp3, MissionProp5, MissionProp7, MissionProp12 "
//			+ " from lwmission where 1=1  "
//			+ " and activityid = '0000008004' "  //工作流保全-自动核保
//			+ " and processid = '0000000000' "
//			+ " and defaultoperator ='" + operator + "'"
//			+ " order by MakeDate, MakeTime ";
	
	turnPage2.queryModal(strSQL, SelfGrid);
	
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
	
	var checkAcount = 0;
	for (i = 0; i < SelfGrid.mulLineCount; i++) 
	{
		if (SelfGrid.getChkNo(i)) 
		{ 
	    	checkAcount++;
	    	break;
	    }
	}
	if (checkAcount < 1)
	{
		alert("请至少选择一条要处理的任务！");
		return; 
	}

	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	document.getElementById("fm").submit()('fmtransact').value = "INSERT||AUTOUWENDORSE";
	fm.action="./WFGEdorAutoUWSave.jsp";
	fm.submit();

}

/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *  参数  ：  无
 *  返回值：  无
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
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "../bq/MissionApply.jsp";
	fm.submit(); //提交
}

/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();

	if (FlagStr == "Succ" )	
	{
	  	//刷新查询结果
		easyQueryClickAll();
		easyQueryClickSelf();		
	}
    else
    {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
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
	var selno;
	var checkAcount = 0;
	for (i = 0; i < SelfGrid.mulLineCount; i++) 
	{
		if (SelfGrid.getChkNo(i)) 
		{ 
	    	checkAcount++;
	    	selno = i;
	    }
	}
	if (checkAcount != 1)
	{
		alert("请选择一条任务！");
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
