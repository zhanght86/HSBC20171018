/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 罗辉 <luohui@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @direction: 恢复交费程序处理
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();                //全局变量, 查询结果翻页, 必须有
var turnPageCustomerGrid = new turnPageClass();    //全局变量, 保单客户查询结果翻页

/*============================================================================*/
function getCustomerGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
		var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql8";
		var mySql1=new SqlClass();
		var sqlresourcename = "bq.PEdorTypeIPInputInputSql";  
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tContNo);
		strSQL=mySql1.getString();
          
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
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
            queryBackFee();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

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
function saveEdorTypeHJ()
{
    var nNewBnfCount;
    
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
    document.forms(0).action = "PEdorTypeHJSubmit.jsp";
    document.forms(0).submit();
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

function queryEdorReason()
{
    var sEdorNo, sEdorType;
    try
    {
        sEdorNo = document.getElementsByName("EdorNo")[0].value;
        sEdorType = document.getElementsByName("EdorType")[0].value;
    }
    catch (ex) {}
    var QuerySQL, arrResult;
    var mySql=new SqlClass();
        mySql.setJspName("../../bq/PEdorTypeHJInputSql.jsp");
    mySql.setSqlId("PEdorTypeHJInputSql_3");
    mySql.addPara('EdorNo', sEdorNo);
    mySql.addPara('EdorType', sEdorType);
    try
    {
        QuerySQL = mySql.getString();
        arrResult = easyExecSql(QuerySQL);
    }
    catch (ex) {}
    if (arrResult != null && arrResult[0][0] != null)
    {
        try
        {
            document.getElementsByName("Remark")[0].value = arrResult[0][0];
        }
        catch (ex) {}
    }
}

function queryCustomerInfo()
{
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
        var mySql=new SqlClass();
        mySql.setJspName("../../bq/PEdorTypeHJInputSql.jsp");
        mySql.setSqlId("PEdorTypeHJInputSql_1");
        mySql.addPara('tContNo',ContNo);
        try
       {
           turnPageCustomerGrid.pageDivName = "divTurnPageCustomerGrid";
           turnPageCustomerGrid.queryModal(mySql.getSQL(), CustomerGrid);
       }
       catch (ex)
       {
           alert("警告：查询保单客户信息出现异常！ ");
           return;
       }
    }
}



/**
 * 查询保单险种信息
 */
function queryPolInfo()
{
    var sContNo, sPolNo;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
        sPolNo = document.getElementsByName("PolNo")[0].value;
    }
    catch (ex) {}

    var QuerySQL, arrResult;
    var mySql = new SqlClass();
    mySql.setJspName("../../bq/PEdorTypeHJInputSql.jsp");
    mySql.setSqlId("PEdorTypeHJIInputSql_2");
    mySql.addPara("ContNo", sContNo);
    mySql.addPara("PolNo", sPolNo);
    try
    {
        arrResult = easyExecSql(mySql.getSQL(), 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单险种信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
            document.getElementsByName("RiskName")[0].value = arrResult[0][1];
            document.getElementsByName("CValiDate")[0].value = arrResult[0][2];
            document.getElementsByName("PayToDate")[0].value = arrResult[0][3];
            document.getElementsByName("Prem")[0].value = arrResult[0][4];
        }
        catch (ex) {}
    } //arrResult != null
}