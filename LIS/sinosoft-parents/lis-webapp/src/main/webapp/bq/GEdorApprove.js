//程序名称：EdorApprove.js
//程序功能：保全复核

var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var arrDataSet;
/**
 *  查询保全申请信息
 *  描述: 查询保全申请信息
 */
function initQuery()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var MissionID       = document.all('MissionID').value
    var SubMissionID    = document.all('SubMissionID').value

    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("保全受理号为空！");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("任务号为空！");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("子任务号为空！");
        return;
    }

    var strSQL;

    //查询保全申请信息
    var sqlid902160512="DSHomeContSql902160512";
var mySql902160512=new SqlClass();
mySql902160512.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902160512.setSqlId(sqlid902160512);//指定使用的Sql的id
mySql902160512.addSubPara(EdorAcceptNo);//指定传入的参数
strSQL=mySql902160512.getString();
    
//    strSQL =  " select OtherNo, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'gedornotype' and code = OtherNoType), "
//            + " GetMoney,EdorAppName, "
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapptype' and code = Apptype), "
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and code = ManageCom),edorstate,othernotype, "
//            + " GetInterest,Apptype,ManageCom "
//            + " from LPEdorApp "
//            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";
    var brr = easyExecSql(strSQL);
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.GetMoney.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorAppName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.ApptypeName.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.ManageComName.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.EdorState.value   = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.OtherNoType.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.GetInterest.value = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.Apptype.value     = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.ManageCom.value   = brr[0][10];
    }
    else
    {
        alert("保全申请查询失败！");
        return;
    }

    //查询保全申请信息
    var sqlid902160633="DSHomeContSql902160633";
var mySql902160633=new SqlClass();
mySql902160633.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902160633.setSqlId(sqlid902160633);//指定使用的Sql的id
mySql902160633.addSubPara(MissionID);//指定传入的参数
mySql902160633.addSubPara(SubMissionID);//指定传入的参数
strSQL=mySql902160633.getString();
    
//    strSQL =  " select missionprop11, missionprop12 from lwmission where activityid = '0000008007' and missionid = '" + MissionID + "' and submissionid = '" + SubMissionID + "'  ";
    var drr = easyExecSql(strSQL);

    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.AppntName.value     = drr[0][0];
        drr[0][1]==null||drr[0][1]=='null'?'0':fm.PaytoDate.value = drr[0][1];
    }
    else
    {
        fm.AppntName.value = "";
        fm.PaytoDate.value = "";
    }
    showEdorMainList();

}

/**
 *  查询保全项目列表
 *  描述: 查询保全项目列表
 */
function showEdorItemListAuto()
{
    var tSel = 0;

    var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
    var GrpContNo = EdorMainGrid.getRowColData(tSel, 2);
    var EdorMainState = EdorMainGrid.getRowColData(tSel, 9);
    document.all('EdorNo').value = EdorNo;
    document.all('GrpContNo').value = GrpContNo;
    document.all('EdorMainState').value = EdorMainState;

    var strSQL;
    
    var sqlid902160722="DSHomeContSql902160722";
var mySql902160722=new SqlClass();
mySql902160722.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902160722.setSqlId(sqlid902160722);//指定使用的Sql的id
mySql902160722.addSubPara(EdorNo);//指定传入的参数
strSQL=mySql902160722.getString();

    
//    strSQL =  " select EdorNo, "
//            + " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//            + " GrpContNo, "
//            + " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//            + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//            + " EdorState, EdorAppDate, EdorType,EdorTypeCal "
//            + " from LPGrpEdorItem "
//            + " where EdorNo = '" + EdorNo + "'" ;
    var drr = easyExecSql(strSQL);
    if ( !drr )
    {
        alert("申请批单下没有保全项目！");
        return;
    }
    else
    {
        turnPage.queryModal(strSQL, EdorItemGrid);
        divEdorItemInfo.style.display='';
    }
}

/**
 *  查询保全项目列表
 *  描述: 查询保全项目列表
 */
function showEdorItemList()
{
    var tSel = EdorMainGrid.getSelNo() - 1;

    var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
    var GrpContNo = EdorMainGrid.getRowColData(tSel, 2);
    var EdorMainState = EdorMainGrid.getRowColData(tSel, 9);
    document.all('EdorNo').value = EdorNo;
    document.all('GrpContNo').value = GrpContNo;
    document.all('EdorMainState').value = EdorMainState;

    var strSQL;
    
    var sqlid902160800="DSHomeContSql902160800";
var mySql902160800=new SqlClass();
mySql902160800.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902160800.setSqlId(sqlid902160800);//指定使用的Sql的id
mySql902160800.addSubPara(EdorNo);//指定传入的参数
strSQL=mySql902160800.getString();
    
//    strSQL =  " select EdorNo, "
//            + " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//            + " GrpContNo, "
//            + " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//            + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//            + " EdorState "
//            + " from LPGrpEdorItem "
//            + " where EdorNo = '" + EdorNo + "'" ;
    var drr = easyExecSql(strSQL);
    if ( !drr )
    {
        alert("申请批单下没有保全项目！");
        return;
    }
    else
    {
        turnPage.queryModal(strSQL, EdorItemGrid);
        divEdorItemInfo.style.display='';
    }
}

/**
 *  查询保全批单列表
 *  描述: 查询保全批单列表
 */
function showEdorMainList()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;

    //查询保全批改信息
    
    var sqlid902160848="DSHomeContSql902160848";
var mySql902160848=new SqlClass();
mySql902160848.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902160848.setSqlId(sqlid902160848);//指定使用的Sql的id
mySql902160848.addSubPara(EdorAcceptNo);//指定传入的参数
strSQL=mySql902160848.getString();
    
//    strSQL =  " select a.EdorNo, a.GrpContNo, '',"
//                + " a.EdorAppDate, a.EdorValiDate, nvl(a.GetMoney,0), nvl(GetInterest,0), "
//                + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(a.EdorState)), "
//                + " a.EdorState "
//                + " from LPGrpEdorMain a "
//                + " where a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
    var crr = easyExecSql(strSQL);
    if ( !crr )
    {
        alert("保全申请下没有批单！");
        return;
    }
    else
    {
        turnPage.queryModal(strSQL, EdorMainGrid);
        divEdorMainInfo.style.display='';
        showEdorItemListAuto();
    }
}

/**
 *  取出保全项目信息
 *  描述: 取出保全项目信息
 */
function getEdorItemInfo()
{
    var tSel = EdorItemGrid.getSelNo() - 1;

    fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
    fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 10);
    fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
    //alert(fm.EdorType.value);
    //fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
    //fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
    fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 8);
    fm.EdorItemAppDate.value       = EdorItemGrid.getRowColData(tSel, 9);
    fm.EdorAppDate.value           = EdorItemGrid.getRowColData(tSel, 9);
    fm.EdorValiDate.value          = EdorItemGrid.getRowColData(tSel, 4);
    fm.EdorTypeCal.value          = EdorItemGrid.getRowColData(tSel, 11);
    fm.ContNoApp.value            = EdorItemGrid.getRowColData(tSel, 3);
    
    var sqlid902160938="DSHomeContSql902160938";
var mySql902160938=new SqlClass();
mySql902160938.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902160938.setSqlId(sqlid902160938);//指定使用的Sql的id
mySql902160938.addSubPara(fm.EdorType.value);//指定传入的参数
var strSQL=mySql902160938.getString();
    
//    var strSQL =  " select edorname from lmedoritem where appobj='G' and edorcode='"+fm.EdorType.value+"' ";
    var tResult = easyExecSql(strSQL);
    if(!tResult)
    {
    	alert("没有查询到项目名称") ;
    	return;
    }
    fm.EdorTypeName.value= tResult[0][0] ;
    
    //fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 11);
    //fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 12);

    //增加补退费类型
    
}

/**
 *  保全复核提交
 *  描述: 保全撤销提交
 */
function ApproveSubmit()
{
    if (fm.EdorAcceptNo.value == "" )
    {
        alert("保全申请号为空！");
        return;
    }
		var sqlid902161051="DSHomeContSql902161051";
var mySql902161051=new SqlClass();
mySql902161051.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161051.setSqlId(sqlid902161051);//指定使用的Sql的id
mySql902161051.addSubPara(fm.OtherNo.value);//指定传入的参数
mySql902161051.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var tSQL=mySql902161051.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='2' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("本次审批操作有未回复的问题件,请等待回复后再下核保结论!");
  		return;
    }
    var tApproveFlag        = fm.ApproveFlag.value;
    var tApproveContent     = fm.ApproveContent.value;
    var sModifyReason       = fm.ModifyReason.value;

    if(tApproveFlag == null || tApproveFlag == "")
    {
        alert("请录入保全审批结论!");
        return ;
    }

    var tApproveStateName = fm.edorapproveideaName.value;

    if(!confirm("确定核保结论为："+tApproveFlag+"-"+tApproveStateName+"？\n一经确认将不可进行修改！"))
    {
    	return
    }
    //<!-- XinYQ added on 2005-11-28 : 复核修改原因 : BGN -->
    if (tApproveFlag == "2" && (sModifyReason == null || sModifyReason == ""))
    {
        alert("请录入审批修改原因！ ");
        return;
    }
    
    if (tApproveFlag == "2" && sModifyReason=="2" && (tApproveContent== null || tApproveContent == ""))
    {
        alert("请录入审批修改原因！ ");
        return;
    }
    if (!verifyInput2()) return;
    //<!-- XinYQ added on 2005-11-28 : 复核修改原因 : END -->

    //if (tApproveContent == null || tApproveContent == "")
    //{
    //  alert("请录入保全复核意见!");
    //  return ;
    //}

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('ActionFlag').value = "APPROVESUBMIT";
    fm.action = "GEdorApproveSave.jsp";
    fm.target="fraSubmit";
    fm.submit();
}


function sendAskMsg()
{
		var sqlid902161152="DSHomeContSql902161152";
var mySql902161152=new SqlClass();
mySql902161152.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161152.setSqlId(sqlid902161152);//指定使用的Sql的id
mySql902161152.addSubPara(fm.OtherNo.value);//指定传入的参数
mySql902161152.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var tSQL=mySql902161152.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='2' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("本次审批操作已经有下发的问题件,不能再次下发!");
  		return;
    }
    
    var tMyReply = fm.AskContent.value;
		if(tMyReply==null ||trim(tMyReply) =="")
		{
			alert("请录入问题件内容!");
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
	showInfo = window.open (MagPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms(0).action = "GEdorNoticeSave.jsp?AskOperate=INSERT";
    document.forms(0).target = "fraSubmit";
    document.forms(0).submit();
}

function queryAskMsg()
{
		// 初始化表格
	fm.AskInfo.value="";        
  fm.ReplyInfo.value="";
	initAgentGrid();
	
	// 书写SQL语句
	var strSQL = "";

	
	var sqlid902161248="DSHomeContSql902161248";
var mySql902161248=new SqlClass();
mySql902161248.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161248.setSqlId(sqlid902161248);//指定使用的Sql的id
mySql902161248.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
strSQL=mySql902161248.getString();
	
//	strSQL = "select PrtSeq,OtherNo,StandbyFlag1,StandbyFlag2,StandbyFlag5,decode(StandbyFlag3,'1','人工核保问题件','2','保全审批问题件','其它'),MakeDate,StandbyFlag7,StandbyFlag4,StandbyFlag6 from LOPRTManager where 1=1 "
//					+ " and othernotype='04' and StandbyFlag1 = '"+fm.EdorAcceptNo.value+"' and StandbyFlag3 = '2' order by PrtSeq";
					
		turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage2.strQueryResult) {
    alert("无记录!");
    divAgentGrid.style.display="none";
    return;
    }
//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage2.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage2.arrDataCacheSet = arrDataSet;
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage2.pageDisplayGrid = AgentGrid;    
          
  //保存SQL语句
  turnPage2.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage2.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage2.getData(turnPage2.arrDataCacheSet, turnPage2.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage2.pageDisplayGrid);
   divAgentGrid.style.display="";
  
}


function ShowAskInfo()
{
    var i = AgentGrid.getSelNo();

    if (i != '0')
    {
        i = i - 1;
        
        var tAskInfo = AgentGrid.getRowColData(i,9); 
        fm.AskInfo.value=tAskInfo;
        var tReplyInfo = AgentGrid.getRowColData(i,10);
         fm.ReplyInfo.value=tReplyInfo;
    }	
} 
/**
 *  保全复核取消
 *  描述: 保全撤销取消
 */
function ApproveCancel()
{
    document.all('ApproveFlag').value = "";
    document.all('ApproveContent').value = "";
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent, OtherFlag)
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
            top.opener.easyQueryClickSelf();
            showEdorMainList();
            if (OtherFlag == "1")//不直接确认，判断显示哪个打印通知书
            {
                //checkNotice();
                //checkEdorPrint();
                divGetNotice.style.display = '';
            }
            else
            {
                //document.all("divPayNotice").style.display = "";
            }
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
* showCodeList 的回调函数
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorapproveidea")
    {
        if (oCodeListField.value == "2")
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "";
                document.all("divApproveMofiyReasonInput").style.display = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "none";
                document.all("divApproveMofiyReasonInput").style.display = "none";
            }
            catch (ex) {}
        }
    } //EdorApproveReason
}

/*============================================================================*/

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/**
 * 扫描件明细
 */
function ScanDetail()
{
	var EdorAcceptNo    = document.all('EdorAcceptNo').value;
	var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
	OpenWindowNew(tUrl,"保全扫描影像","left");
}

/*****************************************************************************
 *  根据EdorAcceptNo查阅批单信息
 *****************************************************************************/
function EndorseDetail()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
        document.forms[0].action = "../f1print/AppEndorsementF1PJ1.jsp";
        document.forms[0]).target = "_blank";
        document.forms[0].submit();
    }
}

/**
 * 根据 EdorNo 查阅人名清单信息
 * Added by XinYQ on 2006-09-21
 */
function NamesBill()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
        var sEdorNo;
        try
        {
            sEdorNo = document.getElementsByName("EdorNo")[0].value;
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
        {
            alert("无法获取批单号。查询人名清单失败！ ");
            return;
        }
        else
        {
            var QuerySQL, arrResult;
            
            var sqlid902161353="DSHomeContSql902161353";
var mySql902161353=new SqlClass();
mySql902161353.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161353.setSqlId(sqlid902161353);//指定使用的Sql的id
mySql902161353.addSubPara(sEdorNo);//指定传入的参数
QuerySQL=mySql902161353.getString();
            
//            QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + sEdorNo + "'";
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("警告：查询人名清单信息出现异常！ ");
                return;
            }
            if (arrResult == null)
            {
                alert("该保单此次批改项目没有人名清单信息！ ");
                return;
            }
            else
            {
                document.forms(0).action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + sEdorNo + "&type=Bill";
                document.forms(0).target = "_blank";
                document.forms(0).submit();
            }
        }
    } //nSelNo != null
}

/*********************************************************************
 *  核保查询
 *  描述: 核保状态查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorGrpUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1");
}

//<!-- XinYQ modified on 2006-09-18 : 直接进入保全项目明细 : BGN -->
function EdorDetailQuery()
{
    var nSelNo, sEdorType, sEdorState;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
        var sEdorType, sEdorState;
        try
        {
            sEdorType = document.getElementsByName("EdorType")[0].value;
            sEdorState = document.getElementsByName("EdorState")[0].value;
        }
        catch (ex) {}
        if (sEdorType == null || trim(sEdorType) == "")
        {
            alert("警告：无法获取保全批改项目类型信息。查询保全明细失败！ ");
            return;
        }
        //if (sEdorState != null && trim(sEdorState) == "0")
        //{
        //    OpenWindowNew("../bqs/GEdorType" + trim(sEdorType) + ".jsp", "保全项目明细查询", "left");    //bqs
        //}
        //else
        //{
        //    OpenWindowNew("../bq/GEdorType" + trim(sEdorType) + ".jsp", "保全项目明细查询", "left");    //bq
        //}
 
        detailEdorType();
    }
}
//<!-- XinYQ modified on 2006-09-18 : 直接进入保全项目明细 : END -->

function GetNotice()
{
   var EdorAcceptNo    = document.all('EdorAcceptNo').value;
   
   var sqlid902161448="DSHomeContSql902161448";
var mySql902161448=new SqlClass();
mySql902161448.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161448.setSqlId(sqlid902161448);//指定使用的Sql的id
mySql902161448.addSubPara(EdorAcceptNo);//指定传入的参数
var strSQL=mySql902161448.getString();
   
//   var strSQL = "select prtseq from loprtmanager where code = 'BQ51' and otherno = '"+EdorAcceptNo+"'";
   var sResult;
   sResult = easyExecSql(strSQL);
   if(sResult == null){
       alert("查询付费通知书信息失败！");
       return;
   }
   document.all('PrtSeq').value = sResult[0][0];
   fm.action = "./EdorNoticePrintSave.jsp";
   fm.target = "f1print";
   fm.submit();
}

function PayNotice()
{
     var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     
     var sqlid902161539="DSHomeContSql902161539";
var mySql902161539=new SqlClass();
mySql902161539.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161539.setSqlId(sqlid902161539);//指定使用的Sql的id
mySql902161539.addSubPara(EdorAcceptNo);//指定传入的参数
var strSQL=mySql902161539.getString();

     
//   var strSQL = "select prtseq from loprtmanager where code = 'BQ51' and otherno = '"+EdorAcceptNo+"'";
   var sResult;
   sResult = easyExecSql(strSQL);
   if(sResult == null){
       alert("查询补费费通知书信息失败！");
       return;
   }
   document.all('PrtSeq').value = sResult[0][0];
   fm.action = "./EdorNoticePrintSave.jsp?";
   fm.target = "f1print";
   fm.submit();
}

function checkNotice()
{
   var EdorAcceptNo    = document.all('EdorAcceptNo').value;
   
   var sqlid902161626="DSHomeContSql902161626";
var mySql902161626=new SqlClass();
mySql902161626.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161626.setSqlId(sqlid902161626);//指定使用的Sql的id
mySql902161626.addSubPara(EdorAcceptNo);//指定传入的参数
var strSQL=mySql902161626.getString();
   
//   var strSQL = "select getmoney from lpgrpedoritem where getmoney <> 0 and getmoney is not null and edoracceptno = '" + EdorAcceptNo + "'";
   var sResult;
   sResult = easyExecSql(strSQL);
   if (sResult != null)
   {
      divGetNotice.style.display = '';
   }
}

/**
 *  保全操作轨迹查询
 *  描述: 保全操作轨迹查询
 */
function MissionQuery()
{
    var pMissionID=fm.MissionID.value;
    window.open("./EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/**
 * 检查是否需要打印批单
 * XinYQ added on 2005-11-09
 */
function checkEdorPrint()
{
    var QuerySQL, arrResult;
    
    var sqlid902161727="DSHomeContSql902161727";
var mySql902161727=new SqlClass();
mySql902161727.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161727.setSqlId(sqlid902161727);//指定使用的Sql的id
mySql902161727.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
QuerySQL=mySql902161727.getString();

    
//    QuerySQL = "select EdorNo "
//             +   "from LPEdorPrint "
//             +  "where 1 = 1 "
//             +    "and EdorNo in "
//             +        "(select EdorNo "
//             +           "from LPGrpEdorItem "
//             +          "where 1 = 1 "
//             +             getWherePart("EdorAcceptNo", "EdorAcceptNo")
//             +        ")";
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查是否需要打印批单信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        if (confirm("是否打印本次保全的批单？ "))
        {
            document.forms(0).action = "../f1print/AppEndorsementUptF1PJ1.jsp";
            document.forms(0).target="f1print";
            document.forms(0).submit();
        }
    }
}

function GrpContInfoQuery()
{
		var cPrtNo = "";
		var cGrpContNo=fm.OtherNo.value;
		var QuerySQL, arrResult;
		
		var sqlid902161824="DSHomeContSql902161824";
var mySql902161824=new SqlClass();
mySql902161824.setResourceName("bq.GEdorApproveInputSql");//指定使用的properties文件名
mySql902161824.setSqlId(sqlid902161824);//指定使用的Sql的id
mySql902161824.addSubPara(cGrpContNo);//指定传入的参数
QuerySQL=mySql902161824.getString();
		
//    QuerySQL = "select PrtNo "
//             +   "from LCGrpCont "
//             +  "where 1 = 1 "
//             +    "and GrpContNo = '"+cGrpContNo+"'";
    arrResult = easyExecSql(QuerySQL, 1, 0);
    cPrtNo = arrResult[0][0];
		try
		{
			window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + cPrtNo + "&GrpContNo=" + cGrpContNo ,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
}
