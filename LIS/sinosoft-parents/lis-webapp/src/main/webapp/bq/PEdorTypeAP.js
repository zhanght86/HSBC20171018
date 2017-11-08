/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-05, 2006-03-11, 2006-08-25
 * @purpose  : 保全保费自垫申请、终止主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;    //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeAPInputSql";
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
            //initForm();
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

/*============================================================================*/

/**
 * 查询保单信息
 */
function queryPolInfo()
{
    var strSQL="" ;
    initPolGrid() ; 
/*    strSQL = "select insuredno,insuredname,appntno,appntname,b.riskname,prem,amnt,mult,polno "
             +        " from lcpol a,lmrisk b where a.riskcode=b.riskcode and a.appflag='1'"
             +        " and contno='"+fm.ContNo.value+"' order by polno" ;
    //2008-7-29 turn to display multiline ..
*/    
	var sqlid1="PEdorTypeAPInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
	strSQL=mySql1.getString();
    
    turnPage.queryModal(strSQL, PolGrid);
    //try
    //{
    //    arrResult = easyExecSql(QuerySQL, 1, 0);
    //}
    //catch (ex)
    //{
    //    alert("警告：查询保单信息出现异常！ ");
    //    return;
    //}
    //if (arrResult != null)
    //{
    //    try
    //    {
    //        document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
    //        document.getElementsByName("RiskName")[0].value = arrResult[0][1];
    //        document.getElementsByName("AppntName")[0].value = arrResult[0][2];
    //        document.getElementsByName("InsuredNo")[0].value = arrResult[0][3];
    //        document.getElementsByName("InsuredName")[0].value = arrResult[0][4];
    //        document.getElementsByName("InsuredAppAge")[0].value = arrResult[0][5];
    //    }
    //    catch (ex) {}
    //} //arrResult != null
}

/*============================================================================*/

/**
 * 查询保费自动垫交信息
 */
function queryAutoPayFlag()
{
    var QueryOldSQL, arrResult, QueryNewSQL, arrNewResult;
/*    QueryOldSQL = "select AutoPayFlag "
             +   "from lCCont "
             +  "where 1 = 1 "
             +     getWherePart("ContNo", "ContNo");
             //+     getWherePart("PolNo", "PolNo");
*/    
    var sqlid2="PEdorTypeAPInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	QueryOldSQL=mySql2.getString();
    
/*    QueryNewSQL =  "select AutoPayFlag "
             +   "from LPCont "
             +  "where 1 = 1 "
             +     getWherePart("ContNo", "ContNo")
             //+     getWherePart("PolNo", "PolNo")
             +		 getWherePart("EdorNo","EdorNo");
    //alert(QuerySQL);
*/    
    var sqlid3="PEdorTypeAPInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql3.addSubPara(fm.EdorNo.value);
	QueryNewSQL=mySql3.getString();
    
    try
    {
        arrResult = easyExecSql(QueryOldSQL, 1, 0);
        arrNewResult = easyExecSql(QueryNewSQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保费自动垫交信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
        	            	  //alert(arrResult[0][0]);
            switch (arrResult[0][0])
            {
                case "0":
                    //原垫交信息
                    document.getElementsByName("AutoPayFlag_Old")[0].value = 0;
                    showOneCodeName('AutoPayType', 'AutoPayFlag_Old', 'AutoPayFlagName_Old');
                    //垫交变更
                    if(arrNewResult != null)
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = arrNewResult[0][0];
                    }else
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = 0;
                    }
                    
                    showOneCodeName('AutoPayType', 'AutoPayFlag', 'AutoPayFlagName');
                    //document.getElementsByName("AutoPayFlag")[0].ondblclick = "showCodeList('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].onkeyup = "showCodeListKey('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].readOnly = true;
                    break;
                case "1":
                    //原垫交信息
                    document.getElementsByName("AutoPayFlag_Old")[0].value = 1;
                    showOneCodeName('AutoPayType', 'AutoPayFlag_Old', 'AutoPayFlagName_Old');
                    //垫交变更
                    if(arrNewResult != null)
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = arrNewResult[0][0];
                    }else
                    {
                    	document.getElementsByName("AutoPayFlag")[0].value = 1;
                    }
                    showOneCodeName('AutoPayType', 'AutoPayFlag', 'AutoPayFlagName');
                    //document.getElementsByName("AutoPayFlag")[0].ondblclick = "showCodeList('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].onkeyup = "showCodeListKey('AutoPayType',[this,AutoPayFlagName],[0,1],null,null,null,0,207)";
                    //document.getElementsByName("AutoPayFlag")[0].readOnly = true;
                    break;
                default:
                    //为空和0均为不自垫
                    document.getElementsByName("AutoPayFlag_Old")[0].value = "0";
                    showOneCodeName('AutoPayType', 'AutoPayFlag_Old', 'AutoPayFlagName_Old');
                    break;
            }
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 提交保全项目结果
 */
function saveEdorTypeAP()
{
		var tAutoPayType_Old = document.getElementsByName("AutoPayFlag_Old")[0].value;
		var tAutoPayType = document.getElementsByName("AutoPayFlag")[0].value;
		//alert(tAutoPayType_Old);
		//alert(tAutoPayType);
		if(tAutoPayType_Old == tAutoPayType){
			alert("请对自垫方式做更改后再保存，若不做更改请单击返回");
			return;
			}
		//return;
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
    document.forms(0).action = "PEdorTypeAPSubmit.jsp";
    document.forms(0).submit();
}

/*============================================================================*/

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


//<!-- JavaScript Document END -->
