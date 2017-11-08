//程序名称：PEdorTypeRB.js
//程序功能：个人保全-保全回退
//创建日期：2005-09-20 09:05:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeRBInputSql";

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
            top.opener.getEdorItemGrid();
            queryBackFee();
        }
        catch (ex) {}
    }
}

/**
 * 根据操作类型(录入或查询)决定操作按钮是否显示
 */
function EdorQuery()
{
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

/**
 * 提交保全项目结果
 */
function saveEdorTypeRB()
{
    if (!verifyInput2())    return;
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
    document.forms[0].action = "PEdorTypeRBSubmit.jsp";
    document.forms[0].submit();
}

/**
 *  查询保全历史
 */
function getEdorItemGrid()
{
    var tContNo = document.all('ContNo').value;
    if (tContNo != null && tContNo.trim() != "")
    {
         //var strSQL =  " select EdorAcceptNo, EdorNo, EdorType,"
/*                    //+ " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj in ('I', 'B') and trim(m.edorcode) = trim(edortype)), "
                    + " DisplayType, "
                    + " GrpContNo, ContNo, InsuredNo, PolNo, EdorAppDate, EdorValiDate, "
                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
                    + " nvl(GetMoney, 0), nvl(GetInterest, 0), MakeTime, ModifyDate, Operator, "
                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
                    + " EdorState,EdorType "
                    + " from LPEdorItem "
                    + " where EdorType not in ('EB', 'RB') and edorstate = '0' and contno='" + tContNo + "' "
                    + " order by approvedate, approvetime ";
*/         
    var strSQL = "";
	var sqlid1="PEdorTypeRBInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql1.getString();

         
         arrResult = easyExecSql(strSQL);
         if (arrResult)
         {
             displayMultiline(arrResult,EdorItemGrid);
         }
    }
}

/**
 *  查询最近一次保全
 */
function getLastEdorItemGrid()
{
    var tContNo = document.all('ContNo').value;
    var EdorCount = EdorItemGrid.mulLineCount;
    if (EdorCount <= 0)
    {
        try
        {
            document.getElementsByName("btnSaveEdorRB")[0].disabled = true;
        }
        catch (ex) {}
        alert("该保单没有可回退的保全项目！ ");
        return;
    }
    var tLastEdorAcceptNo = EdorItemGrid.getRowColData(EdorCount -1 , 1);
    if(tLastEdorAcceptNo!=null)
    {
/*         var strSQL =  " select EdorAcceptNo, EdorNo, EdorType,"
                    //+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), "
                    + " DisplayType, "
                    + " GrpContNo, ContNo, InsuredNo, PolNo, EdorAppDate, EdorValiDate, "
                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
                    + " nvl(GetMoney, 0), nvl(GetInterest, 0), MakeTime, ModifyDate, Operator, "
                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
                    + " EdorState,EdorType "
                    + " from LPEdorItem "
                    + " where edorstate = '0' and contno = '" + tContNo + "' and edoracceptno='" + tLastEdorAcceptNo + "' "
                    + " order by approvedate, approvetime ";
*/         
    var strSQL = "";
	var sqlid2="PEdorTypeRBInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tLastEdorAcceptNo);
	strSQL=mySql2.getString();

         
         arrResult = easyExecSql(strSQL);
         if (arrResult)
         {
             displayMultiline(arrResult,LastEdorItemGrid);
         }
    }
}

/**
 * 查询上次保存的保全原因
 * XinYQ amodified on 2007-05-25
 */
function queryEdorReason()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select a.EdorReasonCode, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where 1 = 1 "
             +            "and CodeType = 'pedorrbreason' "
             +            "and Code = a.EdorReasonCode), "
             +        "a.EdorReason "
             +   "from LPEdorItem a "
             + "where 1 = 1 "
             +    getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
             +    getWherePart("a.EdorNo", "EdorNo")
             +    getWherePart("a.EdorType", "EdorType");
    //alert(QuerySQL);
*/
    
	var sqlid3="PEdorTypeRBInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
	mySql3.addSubPara(fm.EdorNo.value);
	mySql3.addSubPara(fm.EdorType.value);
	QuerySQL=mySql3.getString();

    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询已经保存过的保全原因出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("EdorReason")[0].value = arrResult[0][0];
            document.getElementsByName("EdorReasonName")[0].value = arrResult[0][1];
            document.getElementsByName("Remark")[0].value = arrResult[0][2];
        }
        catch (ex) {}
    }
}

/**
 * 记事本查看
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("警告：无法获取工作流任务节点任务号。查看记事本失败！ ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "工作流记事本查看", "left");
}

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
