/*******************************************************************************
 * @direction: 团单保全人工核保整单层主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                    //全局变量, 弹出的提示窗口, 必须有
var turnPage = new turnPageClass();              //全局变量, 翻页必须有
var turnPageGrpPolGrid = new turnPageClass();    //全局变量, 团单险种队列, 翻页必须有
var turnPage2 = new turnPageClass();
var arrDataSet;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
/*============================================================================*/

function initEdorType(cObj,dObj)
{
	mEdorType = " #1# and (not exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#) or  code<>#S# and exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#  ) ) ";
	showCodeList('edorgrpuwstate',[cObj,dObj],[0,1],null,mEdorType,1,1,207);
}

function actionKeyUp(cObj,dObj)
{	
	mEdorType = " #1# and (not exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#) or  code<>#S# and exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#  ) ) ";
	showCodeListKey('edorgrpuwstate',[cObj,dObj],[0,1],null,mEdorType,1,1,207);
}

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
            //queryGrpPolGrid();
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
            top.opener.querySelfGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询保全申请信息
 */
function queryEdorAppInfo()
{
    var QuerySQL, arrResult;
    
    var sqlid824092232="DSHomeContSql824092232";
var mySql824092232=new SqlClass();
mySql824092232.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824092232.setSqlId(sqlid824092232);//指定使用的Sql的id
mySql824092232.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
QuerySQL=mySql824092232.getString();
    
//    QuerySQL = "select a.OtherNo, "
//             +        "a.OtherNoType, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where 1 = 1 "
//             +            "and CodeType = 'gedornotype' "
//             +            "and Code = a.OtherNoType), "
//             +        "a.EdorAppName, "
//             +        "a.AppType, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where 1 = 1 "
//             +            "and CodeType = 'edorapptype' "
//             +            "and Code = a.AppType), "
//             +        "a.ManageCom, "
//             +        "(select Name from LDCom where ComCode = a.ManageCom), "
//             +        "a.GetMoney, "
//             +        "a.GetInterest "
//             +   "from LPEdorApp a "
//             +  "where 1 = 1 "
//             + getWherePart("a.EdorAcceptNo", "EdorAcceptNo");
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保全申请信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("OtherNo")[0].value = arrResult[0][0];
            document.getElementsByName("OtherNoType")[0].value = arrResult[0][1];
            document.getElementsByName("OtherNoTypeName")[0].value = arrResult[0][2];
            document.getElementsByName("EdorAppName")[0].value = arrResult[0][3];
            document.getElementsByName("AppType")[0].value = arrResult[0][4];
            document.getElementsByName("AppTypeName")[0].value = arrResult[0][5];
            document.getElementsByName("ManageCom")[0].value = arrResult[0][6];
            document.getElementsByName("ManageComName")[0].value = arrResult[0][7];
            document.getElementsByName("GetMoney")[0].value = arrResult[0][8];
            document.getElementsByName("GetInterest")[0].value = arrResult[0][9];
        }
        catch (ex) {}
    }
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
    window.open("./EdorGrpUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1",sFeatures);
}
/*============================================================================*/

/**
 * 查询团体保单信息
 */
function queryGrpContInfo()
{
    var QuerySQL, arrResult;
    
    var sqlid824092352="DSHomeContSql824092352";
var mySql824092352=new SqlClass();
mySql824092352.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824092352.setSqlId(sqlid824092352);//指定使用的Sql的id
mySql824092352.addSubPara(fm.OtherNo.value);//指定传入的参数
QuerySQL=mySql824092352.getString();
    
//    QuerySQL = "select a.GrpContNo, "
//             +        "a.AppntNo, "
//             +        "a.GrpName, "
//             +        "a.Peoples2, "
//             +        "a.ManageCom, "
//             +        "(select Name from LDCom where ComCode = a.ManageCom), "
//             +        "a.SaleChnl, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where CodeType = 'salechnl' "
//             +            "and Code = a.SaleChnl), "
//             +        "a.AgentCode, "
//             +        "(select Name from LAAgent where AgentCode = trim(a.AgentCode)), "
//             +        "a.AgentGroup, "
//             +        "(select name from labranchgroup where agentgroup=a.agentgroup), "
//             +        "(select VIPValue from LDGrp where CustomerNo = a.AppntNo), "
//             +        "decode((select VIPValue from LDGrp where CustomerNo = a.AppntNo), "
//             +               "'0', "
//             +               "'非VIP客户', "
//             +               "'1', "
//             +               "'VIP客户', "
//             +               "''), "
//             +        "(select BlacklistFlag from LDGrp where CustomerNo = a.AppntNo), "
//             +        "decode((select BlacklistFlag from LDGrp where CustomerNo = a.AppntNo), "
//             +               "'0', "
//             +               "'正常', "
//             +               "'1', "
//             +               "'黑名单', "
//             +               "''), "
//             +         "a.prtno"
//             +  " from LCGrpCont a"
//             +  " where 1 = 1 "
//             + getWherePart("a.GrpContNo", "OtherNo");
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询团体保单信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("GrpContNo")[0].value = arrResult[0][0];
            document.getElementsByName("AppntNo")[0].value = arrResult[0][1];
            document.getElementsByName("AppntName")[0].value = arrResult[0][2];
            document.getElementsByName("Peoples2")[0].value = arrResult[0][3];
            document.getElementsByName("ManageCom")[1].value = arrResult[0][4];
            document.getElementsByName("ManageComName")[1].value = arrResult[0][5];
            document.getElementsByName("SaleChnl")[0].value = arrResult[0][6];
            document.getElementsByName("SaleChnlName")[0].value = arrResult[0][7];
            document.getElementsByName("AgentCode")[0].value = arrResult[0][8];
            document.getElementsByName("AgentName")[0].value = arrResult[0][9];
            document.getElementsByName("AgentGroup")[0].value = arrResult[0][10];
            document.getElementsByName("AgentGroupName")[0].value = arrResult[0][11];
            document.getElementsByName("VIPValue")[0].value = arrResult[0][12];
            document.getElementsByName("VIPValueName")[0].value = arrResult[0][13];
            document.getElementsByName("BlacklistFlag")[0].value = arrResult[0][14];
            document.getElementsByName("BlacklistFlagName")[0].value = arrResult[0][15];
            document.getElementsByName("PrtNo")[0].value = arrResult[0][16];
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询团单险种队列
 */
function queryGrpPolGrid()
{
    
    var sqlid824092636="DSHomeContSql824092636";
var mySql824092636=new SqlClass();
mySql824092636.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824092636.setSqlId(sqlid824092636);//指定使用的Sql的id
mySql824092636.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
mySql824092636.addSubPara(fm.OtherNo.value);//指定传入的参数
var QuerySQL=mySql824092636.getString();
    
//    var QuerySQL = "select a.GrpPolNo, "
//                 +        "a.EdorNo, "
//                 +        "a.EdorType, "
//                 +        "a.RiskCode, "
//                 +        "(select distinct RiskName from LMRisk where RiskCode = a.RiskCode), "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where CodeType = 'payintv' "
//                 +            "and Code = a.PayIntv), "
//                 +        "a.Peoples2, "
//                 +        "a.PayToDate, "
//                 +        "a.CValiDate, "
//                 +        "a.UWFlag "
//                 +   "from LPGrpPol a "
//                 +  "where 1 = 1 "
//                 +    "and a.EdorNo in (select EdorNo from LPGrpEdorItem where 1 = 1 " + getWherePart("EdorAcceptNo", "EdorAcceptNo") + ") "
//                 + getWherePart("a.GrpContNo", "OtherNo")
//                 +  "order by a.GrpPolNo asc, a.RiskCode asc";
    //alert(QuerySQL);
    try
    {
    	  turnPageGrpPolGrid.pageDivName = "divGrpPolTP";
        turnPageGrpPolGrid.queryModal(QuerySQL, GrpPolGrid);
    }
    catch (ex)
    {
        alert("警告：查询保全团单险种信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 查询上次核保的结论
 */
function queryLastUWState()
{
    var QuerySQL, arrResult;

    //保全申请核保结论
    var sqlid824092731="DSHomeContSql824092731";
var mySql824092731=new SqlClass();
mySql824092731.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824092731.setSqlId(sqlid824092731);//指定使用的Sql的id
mySql824092731.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
QuerySQL=mySql824092731.getString();
    
    
//    QuerySQL = "select a.PassFlag, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where 1 = 1 "
//             +            "and CodeType = 'edorappuwstate' "
//             +            "and Code = a.PassFlag), "
//             +        "a.UWIdea "
//             +   "from LPAppUWMasterMain a "
//             +  "where 1 = 1 "
//             +  getWherePart("a.EdorAcceptNo", "EdorAcceptNo");
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询上次保存的保全申请核保结论出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("AppUWState")[0].value = arrResult[0][0];
            document.getElementsByName("AppUWStateName")[0].value = arrResult[0][1];
            document.getElementsByName("AppUWIdea")[0].value = arrResult[0][2];
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 进入团单下的个单人工核保
 */
function gotoContUW()
{
    var sMissionID, sSubMissionID, sActivityStatus, sEdorAcceptNo, sGrpContNo;
    try
    {
        sMissionID = document.getElementsByName("MissionID")[0].value;
        sSubMissionID = document.getElementsByName("SubMissionID")[0].value;
        sActivityStatus = document.getElementsByName("ActivityStatus")[0].value;
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
    {
        alert("无法获取保全受理号。进入团单下的个单人工核保失败！ ");
        return;
    }
    var sOpenWinURL = "GEdorManuUWInsuredFrame.jsp?Interface=GEdorManuUWInsuredInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityStatus=" + sActivityStatus + "&EdorAcceptNo=" + sEdorAcceptNo + "&GrpContNo=" + sGrpContNo;
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "团单分单人工核保", "left");
}

/*============================================================================*/

/**
 * 发团体体检通知书
 */
function noticeHealthInspect()
{
    if (!isEdorPopedom())    return;
    var sGrpContNo;
    try
    {
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sGrpContNo == null || sGrpContNo == "")
    {
        alert("无法获取团体合同号。发团体体检通知书失败！ ");
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
    document.forms[0].action = "../uw/UWManuSendBodyCheckChk.jsp?GrpContNo=" + sGrpContNo;
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * 发团体生调通知书
 */
function noticeLiveInquiry()
{
    if (!isEdorPopedom())    return;
    var sGrpContNo;
    try
    {
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sGrpContNo == null || sGrpContNo == "")
    {
        alert("无法获取团体合同号。发团体生调通知书失败！ ");
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
    document.forms[0].action = "../uw/UWManuSendRReportChk.jsp?GrpContNo=" + sGrpContNo;
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * 发核保结论通知书
 */
function noticeEdorUWResult()
{
    if (!isEdorPopedom())    return;
    var sMissionID, sSubMissionID, sEdorAcceptNo, sGrpContNo;
    try
    {
        sMissionID = document.getElementsByName("MissionID")[0].value;
        sSubMissionID = document.getElementsByName("SubMissionID")[0].value;
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sGrpContNo == null || sGrpContNo == "")
    {
        alert("无法获取团体合同号。发核保结论通知书失败！ ");
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
    document.forms[0].action = "../uw/UWSendNoticeChk.jsp?MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&GrpContNo=" + sGrpContNo;
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * 检查操作员是否具有申请核保权限
 */
function isEdorPopedom()
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
        
        var sqlid824092901="DSHomeContSql824092901";
var mySql824092901=new SqlClass();
mySql824092901.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824092901.setSqlId(sqlid824092901);//指定使用的Sql的id
mySql824092901.addSubPara(sOperator);//指定传入的参数
QuerySQL=mySql824092901.getString();
        
//        QuerySQL = "select EdorPopedom "
//                 +   "from LDUWUser "
//                 +  "where UserCode = '" + sOperator + "'";
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
 * 保存团体保单核保结论
 */
function saveGrpContUW()
{
    if (!isEdorPopedom())    return;
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
    try
    {
        document.getElementsByName("ActionFlag")[0].value = "UWMANUSUBMIT";
        document.getElementsByName("UWType")[0].value = "GrpCont";
    }
    catch (ex) {}
    document.forms[0].action = "GEdorAppManuUWSave.jsp";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * 重置团体保单核保结论
 */
function resetGrpContUW()
{
    try
    {
        document.getElementsByName("GrpUWState")[0].value = "";
        document.getElementsByName("GrpUWStateName")[0].value = "";
        document.getElementsByName("GrpUWIdea")[0].value = "";
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * 保存保全申请核保结论
 */
function saveEdorAppUW()
{
		var sqlid824093114="DSHomeContSql824093114";
var mySql824093114=new SqlClass();
mySql824093114.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824093114.setSqlId(sqlid824093114);//指定使用的Sql的id
mySql824093114.addSubPara(fm.OtherNo.value);//指定传入的参数
mySql824093114.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var tSQL=mySql824093114.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='1' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("本次核保操作有未回复的问题件,请等待回复后再下核保结论!");
  		return;
    }
    if (!isEdorPopedom())    return;
    var tAppUWState = fm.AppUWState.value;
    var tAppUWStateName = fm.AppUWStateName.value;
    if(tAppUWState == null || tAppUWState == "")
    {
    	alert("请录入核保结论！");
    	return;
    }
    if(!confirm("确定核保结论为："+tAppUWState+"-"+tAppUWStateName+"？\n一经确认将不可进行修改！"))
    {
    	return
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
    try
    {
        document.getElementsByName("ActionFlag")[0].value = "UWMANUSUBMIT";
        document.getElementsByName("UWType")[0].value = "GEdorApp";
    }
    catch (ex) {}
    document.forms[0].action = "GEdorAppManuUWSave.jsp";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * 重置保全申请核保结论
 */
function resetEdorAppUW()
{
    try
    {
        document.getElementsByName("AppUWState")[0].value = "";
        document.getElementsByName("AppUWStateName")[0].value = "";
        document.getElementsByName("AppUWIdea")[0].value = "";
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * 记事本查看
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo, sActivityID;
    try
    {
        sMissionID = document.getElementsByName("MissionID")[0].value;
        sSubMissionID = document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sActivityID = document.getElementsByName("ActivityID")[0].value;
    }
    catch (ex) {}
    if (sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("无法获取保全申请号。查看记事本失败！ ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID + "&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "工作流记事本查看", "left");
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

/*============================================================================*/

//==============add================liuxiaosong==========2006-10-25=============start==============
/*
 *发送核保结论通知书
 */
function noticeEdorUWResult(){document.getElementsByName("MissionID")[0].value
	
	var missionID=document.getElementsByName("MissionID")[0].value; //获得missionID号
	var grpcontno=fm.GrpContNo.value;//团体保单号
	var EdorNo=fm.EdorNo.value;//批单号
	var EdorType=GrpPolGrid.getRowColData(0,3);//批改类型
	
	
	var sqlid824093243="DSHomeContSql824093243";
var mySql824093243=new SqlClass();
mySql824093243.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824093243.setSqlId(sqlid824093243);//指定使用的Sql的id
mySql824093243.addSubPara(grpcontno);//指定传入的参数
var QuerySQL=mySql824093243.getString();
	
//	var QuerySQL="select prtno from lcgrpcont where grpcontno='"+grpcontno+"'";
	var brr = easyExecSql(QuerySQL, 1, 0,"","",1);
	var PrtNo=brr[0][0];//印刷号prtNo
	fm.PrtNo.value=(PrtNo);
 
	var sqlid824093416="DSHomeContSql824093416";
var mySql824093416=new SqlClass();
mySql824093416.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824093416.setSqlId(sqlid824093416);//指定使用的Sql的id
mySql824093416.addSubPara(PrtNo);//指定传入的参数
mySql824093416.addSubPara(missionID);//指定传入的参数
var strsql=mySql824093416.getString();
	
//	var strsql = "select LWMission.SubMissionID from LWMission where "
//	+ " LWMission.MissionProp1 = '" +PrtNo+"'"
//	+ " and LWMission.ActivityID = '0000002101'"
//	+ " and LWMission.ProcessID = '0000000000'"
//	+ " and LWMission.MissionID = '" +missionID +"'";

	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);

	//判断是否已经存在该单的核保通知书
	if (!turnPage.strQueryResult) {

		alert("不容许发放新的核保结论通知书,原因是:1.已发核保通知书,但未打印.");
		fm.SubMissionID.value = "";
		return ;
	}
    
	var arrSelected = new Array();
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	fm.SubMissionID.value = arrSelected[0][0];
	//alert(fm.SubMissionID.value);
	cProposalNo=fm.GrpContNo.value;
	//alert(cProposalNo);

	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    //alert(001);
    
	if (cProposalNo != "")
	{   
		//复用新契约的 发核保通知书页面,注意新契约在使用时不需要传参数过去，保全必须传
		//alert(EdorNo);
		//alert(EdorType);
		fm.action = "../uw/UWSendNoticeChk.jsp?EdorNo="+EdorNo+"&EdorType="+EdorType;
		
		document.getElementById("fm").submit();
	}
	Else
	{
		showInfo.close();
		alert("请先选择保单!");
	}

}
//==============add================liuxiaosong==========2006-10-25=============end==============
//==============add================liuxiaosong==========2006-10-27=============start============
/********************************
 *发送核保要求通知书
 *
 ********************************/
function sendUWRequest(){
	var grpcontno=fm.GrpContNo.value;
	
	var sqlid824093637="DSHomeContSql824093637";
var mySql824093637=new SqlClass();
mySql824093637.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824093637.setSqlId(sqlid824093637);//指定使用的Sql的id
mySql824093637.addSubPara(grpcontno);//指定传入的参数
var QuerySQL=mySql824093637.getString();
	
//  var QuerySQL="select prtno from lcgrpcont where grpcontno='"+grpcontno+"'";
	var brr = easyExecSql(QuerySQL, 1, 0,"","",1);
	var PrtNo=brr[0][0];//印刷号prtNo
	fm.PrtNo.value=(PrtNo);
	
var GrpContNo=fm.GrpContNo.value;
//alert(GrpContNo);
var MissionID=fm.MissionID.value;
//alert(MissionID);
var EdorNo=fm.EdorNo.value;

var EdorType=fm.EdorType.value;


 //复用新契约中的“修改事项索要材料录入“
window.open("../uw/UWGChangeResultMain.jsp?GrpContNo=" +GrpContNo+ "&MissionID="+MissionID+ "&EdorNo="+EdorNo+ "&EdorType="+EdorType+"&PrtNo="+PrtNo,"",sFeatures);
}
//==============add================liuxiaosong==========2006-10-27=============end==============

//==============add================liuxiaosong==========2006-10-30=============start============
/***
 *投保单位基本情况
 *风险要索查看
 *说明：该功能复用新契约人工核保的“风险要素评估（风险要索查看）“功能页面，因此在本页中传递
 *      业务种类为”保全“，以便在复用的页面中实现不同逻辑
 ***/
function grpRiskElementView()
{
	
	var selno = GrpPolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert("请选择集体险种单");
		return;
	}
	var tGrpContNo = fm.GrpContNo.value;//团单号
	/*//从lcgrppol中查出
	var QuerySQL="select RiskCode,GrpPolNo from lcgrppol where GrpContNo='"+tGrpContNo+"'";
	var brr=easyExecSql(QuerySQL, 1, 0,"","",1);

	var RiskCode=brr[0][0];//险种编码
	var tGrpPolNo=brr[0][1];//集体险种号码
	*/
	var tRiskCode=GrpPolGrid.getRowColData(selno,4);//险种编码
	var tGrpPolNo=GrpPolGrid.getRowColData(selno,1);//集体险种号码	
	var tBusinessType="bq";//复用新契约页面，此标志为保全应用
							
	window.open("../uw/GrpRiskElenemtMain.jsp?GrpContNo="+tGrpContNo+"&riskcode="+tRiskCode+"&businesstype="+tBusinessType,"window1",sFeatures);
}

//==============add=================liuxiaosong===========2006-10-30============end===============

/**
 * 团体保单明细查询
 */
function queryContDetail(){
    var sGrpContNo, sPrtNo;
    try
    {
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
        sPrtNo = document.getElementsByName("PrtNo")[0].value;
    }
    catch (ex) {}
    var sOpenWinURL = "../sys/GrpPolDetailQueryMain.jsp";
    var sParameters ="GrpContNo=" + sGrpContNo + "&PrtNo=" + sPrtNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "团体保单明细查询", "left");
}

//保全操作轨迹查询
function missionQuery()
{
	try{
	var EdorAcceptNo = document.all("EdorAcceptNo").value;
	
	var sqlid824093811="DSHomeContSql824093811";
var mySql824093811=new SqlClass();
mySql824093811.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824093811.setSqlId(sqlid824093811);//指定使用的Sql的id
mySql824093811.addSubPara(EdorAcceptNo);//指定传入的参数
mySql824093811.addSubPara(EdorAcceptNo);//指定传入的参数
var strSql=mySql824093811.getString();
	
//	var strSql = "select missionid from lbmission where missionprop1 = '"+EdorAcceptNo+"' union select missionid from lwmission where missionprop1 = '"+EdorAcceptNo+"' ";
	var brr =  easyExecSql(strSql);
  }catch(ex){}
	if(!brr)
	{
		alert("该保全受理轨迹信息不存在！");
	}
	var pMissionID = brr[0][0];
	window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3",sFeatures);
}

function sendAskMsg()
{
		var sqlid824094021="DSHomeContSql824094021";
var mySql824094021=new SqlClass();
mySql824094021.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824094021.setSqlId(sqlid824094021);//指定使用的Sql的id
mySql824094021.addSubPara(fm.OtherNo.value);//指定传入的参数
mySql824094021.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var tSQL=mySql824094021.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='1' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("本次核保操作已经有下发的问题件,不能再次下发!");
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms[0].action = "GEdorNoticeSave.jsp?AskOperate=INSERT";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

function queryAskMsg()
{
		// 初始化表格
	fm.AskInfo.value="";        
  fm.ReplyInfo.value="";
	initAgentGrid();
	
	// 书写SQL语句
	var strSQL = "";

	
	var sqlid824094154="DSHomeContSql824094154";
var mySql824094154=new SqlClass();
mySql824094154.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824094154.setSqlId(sqlid824094154);//指定使用的Sql的id
mySql824094154.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
strSQL=mySql824094154.getString();

	
//	strSQL = "select PrtSeq,OtherNo,StandbyFlag1,StandbyFlag2,StandbyFlag5,decode(StandbyFlag3,'1','人工核保问题件','2','保全审批问题件','其它'),MakeDate,StandbyFlag7,StandbyFlag4,StandbyFlag6 from LOPRTManager where 1=1 "
//					+ " and othernotype='04' and StandbyFlag1 = '"+fm.EdorAcceptNo.value+"' and StandbyFlag3 = '1' order by PrtSeq";
					
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

//投保单位已承保保单查询
function queryProposal(){
  try{
  	var appntno = document.all("AppntNo").value;
	}catch(ex){}
    window.open("../uw/GroupProposalQueryMain.jsp?AppntNo="+appntno,"window1",sFeatures);
}
//投保单位未承保保单查询
function queryNotProposal(){
  try{
  	var appntno = document.all("AppntNo").value;
	}catch(ex){}
	window.open("../uw/GroupNotProposalQueryMain.jsp?AppntNo="+appntno,"window1",sFeatures);
}

//投保单影像查询
function scanQuery()
{
	try{
	   var prtNo=document.all("PrtNo").value;
	  }catch(ex){}
	if(prtNo==""||prtNo==null)
	{
	  	alert("请先选择一个团体投保单!");
  		return ;
  	}
	window.open("../uw/ImageQueryMain.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
}

//保全扫描影像查询
function scanDetail()
{
	 var EdorAcceptNo    = document.all('EdorAcceptNo').value;
	 var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
	 OpenWindowNew(tUrl,"保全扫描影像","left");
}
//核保影像查询
function UWScanQuery()
{
      var ContNo = document.getElementsByName("ContNo")[0].value;
      if (ContNo == "" || ContNo == null){
        return;
      }
      window.open("../uw/EdorUWImageQueryMain.jsp?ContNo=" + ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}
//保全明细查询
function edorDetailQuery()
{
      //var sEdorType, sEdorState;
      //try
      //  {
      //      sEdorType = document.getElementsByName("EdorType")[0].value;
      //      sEdorState = document.getElementsByName("EdorState")[0].value;
      //  }
      //  catch (ex) {}
      //  if (sEdorType == null || trim(sEdorType) == "")
      //  {
      //      alert("警告：无法获取保全批改项目类型信息。查询保全明细失败！ ");
      //      return;
      //  }
      //  detailEdorType();
      var tEdorAcceptNo = fm.EdorAcceptNo.value;
      var tOtherNoType  = fm.OtherNoType.value;	
	    var varSrc = "&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType=4";
	    var newWindow = OpenWindowNew("../sys/GBqDetailQueryFrame.jsp?Interface= ../sys/GBqDetailQuery.jsp" + varSrc,"保全查询明细","left");	
      
}
//查询该受理号下保全项目信息
function queryEdorItemInfo()
{
   try{
   	var edorAcceptNo = document.all("EdorAcceptNo").value;
    var strSQL;
    
    var sqlid824094725="DSHomeContSql824094725";
var mySql824094725=new SqlClass();
mySql824094725.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824094725.setSqlId(sqlid824094725);//指定使用的Sql的id
mySql824094725.addSubPara(edorAcceptNo);//指定传入的参数
strSQL=mySql824094725.getString();
    
//    strSQL =  " select EdoracceptNo, "
//            + " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//            + " GrpContNo, "
//            + " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//            + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//            + " EdorState, EdorAppDate, EdorType "
//            + " from LPGrpEdorItem "
//            + " where EdorAcceptNo = '" + edorAcceptNo + "'" ;
    var drr = easyExecSql(strSQL);
  }catch(ex){}
    if ( !drr )
    {
        alert("申请批单下没有保全项目！");
        return;
    }
    else
    {
    	try{
        turnPage.queryModal(strSQL, EdorItemGrid);
        document.getElementsByName("EdorNo")[0].value = drr[0][0];
        document.getElementsByName("EdorValiDate")[0].value = drr[0][3];
        document.getElementsByName("EdorState")[0].value = drr[0][7];
        document.getElementsByName("EdorItemAppDate")[0].value = drr[0][8];
        document.getElementsByName("EdorType")[0].value = drr[0][9];
      }catch(ex){}
    }
}

//查询团体单核保记录
function queryGrpAutoUWTrack()
{
	try{
      var cGrpContNo=fm.GrpContNo.value;
  }catch(ex){
    alert("无法获得团单号！");
    return;
  }
  if(cGrpContNo==""||cGrpContNo==null)
  {
  	alert("请先选择一个团体投保单!");
  	return ;
  	}
  	    
  	    var sqlid824094725="DSHomeContSql824094725";
var mySql824094725=new SqlClass();
mySql824094725.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824094725.setSqlId(sqlid824094725);//指定使用的Sql的id
mySql824094725.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var tsql=mySql824094725.getString();
  	    
//  	    var tsql="select edorno from lpgrpedoritem where edoracceptno='"+fm.EdorAcceptNo.value+"'" ;
        var tResult=easyExecSql(tsql, 1, 0);
        var sEdorNo=tResult[0][0];

  window.open("../uw/UWGErrMain.jsp?GrpContNo="+cGrpContNo+"&EdorNo="+sEdorNo+"&BqFlag=1","window1",sFeatures);
}

//投保单位既往保全查询
function queryAgoEdor()
{
	try{
      var tAppntNo=fm.AppntNo.value;
  }catch(ex){
    alert("无法获得团体客户号！");
    return;
  }
  if(tAppntNo==""||tAppntNo==null)
  {
  	alert("请先选择一个团体投保单!");
  	return ;
  	}
  window.open("./GEdorAgoQueryMain.jsp?CustomerNo="+tAppntNo,"window1",sFeatures);
}

//投保单位既往理赔查询
function queryAgoClaim()
{
	try{
        var tAppntNo=fm.AppntNo.value;
    }catch(ex){
      alert("无法获得团体客户号！");
      return;
    }
    if(tAppntNo==""||tAppntNo==null)
    {
    	alert("请先选择一个团体投保单!");
    	return ;
    	}
  window.open("../uw/ClaimGrpQueryInput.jsp?CustomerNo="+tAppntNo,"window1",sFeatures);
}

//已发送核保通知书查询
function queryAgoNotice()
{
	try{
        var tGrpContNo=fm.GrpContNo.value;
    }catch(ex){
      alert("无法获得团体保单号！");
      return;
    }
    if(tGrpContNo==""||tGrpContNo==null)
    {
    	alert("请先选择一个团体投保单!");
    	return ;
    }
  window.open("../uw/QueryNotice.jsp?GrpContNo="+tGrpContNo,"window1",sFeatures);
}

/*============================================================================*/
function initGrpPolInfo()
{
    var nSelNo;
    try
    {
        nSelNo = GrpPolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var GrpPolNo, UWState;
        try
        {
            GrpPolNo = GrpPolGrid.getRowColData(nSelNo, 1);
            UWState = GrpPolGrid.getRowColData(nSelNo, 10);
        }
        catch (ex) {}
        if (GrpPolNo != null && GrpPolNo != "")
        {
            try
            {
                document.getElementsByName("GrpPolNo")[0].value = GrpPolNo;
                document.getElementsByName("GrpUWState")[0].value = UWState;
                showOneCodeName('gedorgrppoluwstate','GrpUWState','GrpUWStateName');  
            }
            catch (ex) {}
				    //团体险种核保意见
				    var sqlid824095813="DSHomeContSql824095813";
var mySql824095813=new SqlClass();
mySql824095813.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824095813.setSqlId(sqlid824095813);//指定使用的Sql的id
mySql824095813.addSubPara(fm.EdorNo.value);//指定传入的参数
mySql824095813.addSubPara(fm.EdorType.value);//指定传入的参数
mySql824095813.addSubPara(fm.GrpPolNo.value);//指定传入的参数
QuerySQL=mySql824095813.getString();

				    
//				    QuerySQL = "select a.UWIdea "
//				             +   "from LPGUWMaster a "
//				             +  "where 1 = 1 "
//				             +    "and a.EdorNo = '" + fm.EdorNo.value + "' and a.EdorType = '" + fm.EdorType.value + "' and a.GrpPolNo = '" + fm.GrpPolNo.value + "' "
				    //alert(QuerySQL);
				    try
				    {
				        arrResult = easyExecSql(QuerySQL, 1, 0);
				    }
				    catch (ex)
				    {
				        alert("警告：查询上次保存的团体险种核保意见出现异常！ ");
				        return;
				    }
				    if (arrResult != null)
				    {
				        try
				        {
				            document.getElementsByName("GrpUWIdea")[0].value = arrResult[0][0];
				        }
				        catch (ex) {}
				    }
        }
    }
}
/*****************************************************************************
 *  根据EdorAcceptNo查阅批单信息
 *****************************************************************************/
function EndorseDetail()
{
    //var nSelNo;
    //try
    //{
    //    nSelNo = EdorItemGrid.getSelNo() - 1;
    //}
    //catch (ex) {}
    //if (nSelNo == null || nSelNo < 0)
    //{
    //    alert("请先选择您要查看的申请项目！ ");
    //    return;
    //}
    //else
    //{
        document.forms(0).action = "../f1print/AppEndorsementF1PJ1.jsp";
        document.forms(0).target = "_blank";
        document.forms(0).submit();
    //}
}

/**
 * 根据 EdorNo 查阅人名清单信息
 */
function NamesBill()
{
    //var nSelNo;
    //try
    //{
    //    nSelNo = EdorItemGrid.getSelNo() - 1;
    //}
    //catch (ex) {}
    //if (nSelNo == null || nSelNo < 0)
    //{
    //    alert("请先选择您要查看的申请项目！ ");
    //    return;
    //}
    //else
    //{
        
        var sqlid824095912="DSHomeContSql824095912";
var mySql824095912=new SqlClass();
mySql824095912.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824095912.setSqlId(sqlid824095912);//指定使用的Sql的id
mySql824095912.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var tsql=mySql824095912.getString();

//        var tsql="select edorno from lpgrpedoritem where edoracceptno='"+fm.EdorAcceptNo.value+"'" ;
        var tResult=easyExecSql(tsql, 1, 0);
        var sEdorNo=tResult[0][0];

            var QuerySQL, arrResult;
            
            var sqlid824095956="DSHomeContSql824095956";
var mySql824095956=new SqlClass();
mySql824095956.setResourceName("bq.GEdorAppManuUWInputSql");//指定使用的properties文件名
mySql824095956.setSqlId(sqlid824095956);//指定使用的Sql的id
mySql824095956.addSubPara(sEdorNo);//指定传入的参数
QuerySQL=mySql824095956.getString();
            
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
        
    //} //nSelNo != null
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
//<!-- JavaScript Document END -->
