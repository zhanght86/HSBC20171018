/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-11-06
 * @direction: 保全申请添加批改项目校验保单密码主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                     //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();               //全局变量, 查询结果翻页, 必须有
var turnPageContPwdGrid = new turnPageClass();    //全局变量, 生调轨迹查询结果翻页

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
            verifyPwdSucc();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 根据输入的申请号码显示需要输入密码的保单
 */
function queryContPwdGrid()
{
    var sOtherNo, sOtherNoType, sEdorItemAppDate, sAppType;
    try
    {
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sOtherNoType = document.getElementsByName("OtherNoType")[0].value;
        sEdorItemAppDate = document.getElementsByName("EdorItemAppDate")[0].value;
        sAppType = document.getElementsByName("AppType")[0].value;
    }
    catch (ex) {}
    if (sOtherNo == null || sOtherNo.trim() == "" || sOtherNoType == null || sOtherNoType.trim() == "" || sEdorItemAppDate == null || sEdorItemAppDate.trim() == "")
    {
        alert("无法获取保全申请信息。查询保单密码信息失败！ ");
        verifyPwdFail();
        return;
    }
//    var QuerySQL = "select a.ContNo, "
//                 +        "a.AppntNo, "
//                 +        "a.AppntName, "
//                 +        "a.InsuredNo, "
//                 +        "a.InsuredName, "
//                 +        "a.CValiDate, "
//                 +        "'' "
//                 +   "from LCCont a "
//                 +  "where 1 = 1 "
//                 +    "and a.Password is not null ";
    var QuerySQL = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
    
    if (sOtherNoType == "1")
    {
//        QuerySQL +=    "and ((a.AppFlag = '1' and not exists "
//                 +         "(select 'X' "
//                 +             "from LCContState "
//                 +            "where 1 = 1 "
//                 +              "and ContNo = a.ContNo "
//                 +              "and (PolNo = '000000' or "
//                 +                   "PolNo = (select PolNo "
//                 +                              "from LCPol "
//                 +                             "where 1 = 1 "
//                 +                               "and ContNo = a.ContNo "
//                 +                               "and PolNo = MainPolNo)) "
//                 +              "and StateType = 'Available' "
//                 +              "and State = '1' "
//                 +              "and (('" + sEdorItemAppDate + "' >= StartDate and '" + sEdorItemAppDate + "' <= EndDate) or "
//                 +                  "('" + sEdorItemAppDate + "' >= StartDate and EndDate is null)))) or "
//                 +        "(a.AppFlag = '4' and exists "
//                 +         "(select 'X' "
//                 +             "from LCContState "
//                 +            "where 1 = 1 "
//                 +              "and ContNo = a.ContNo "
//                 +              "and (PolNo = '000000' or "
//                 +                   "PolNo = (select PolNo "
//                 +                              "from LCPol "
//                 +                             "where 1 = 1 "
//                 +                               "and ContNo = a.ContNo "
//                 +                               "and PolNo = MainPolNo)) "
//                 +              "and StateType = 'Terminate' "
//                 +              "and State = '1' "
//                 +              "and (('" + sEdorItemAppDate + "' >= StartDate and '" + sEdorItemAppDate + "' <= EndDate) or "
//                 +                  "('" + sEdorItemAppDate + "' >= StartDate and EndDate is null)) "
//                 +              "and StateReason in ('01', '05', '06', '09')))) "
//                 +  "and a.ContNo in "
//                 +      "(select ContNo from LCAppnt where AppntNo = '" + sOtherNo + "' "
//                 +      "union "
//                 +      "select ContNo from LCInsured where InsuredNo = '" + sOtherNo + "') ";
        
        sql_id = "PEdorAppPwdVerifySql1";
        my_sql = new SqlClass();
        my_sql.setResourceName("bq.PEdorAppPwdVerifySql"); //指定使用的properties文件名
        my_sql.setSqlId(sql_id);//指定使用的Sql的id
        my_sql.addSubPara(sEdorItemAppDate);//指定传入的参数
        my_sql.addSubPara(sEdorItemAppDate);//指定传入的参数
        my_sql.addSubPara(sEdorItemAppDate);//指定传入的参数
        my_sql.addSubPara(sEdorItemAppDate);//指定传入的参数
        my_sql.addSubPara(sEdorItemAppDate);//指定传入的参数
        my_sql.addSubPara(sEdorItemAppDate);//指定传入的参数
        my_sql.addSubPara(sOtherNo);//指定传入的参数
        my_sql.addSubPara(sOtherNo);//指定传入的参数
    }
    else if (sOtherNoType == "3")
    {
//        QuerySQL += "and a.ContNo = '" + sOtherNo + "' ";
        sql_id = "PEdorAppPwdVerifySql2";
        my_sql = new SqlClass();
        my_sql.setResourceName("bq.PEdorAppPwdVerifySql"); //指定使用的properties文件名
        my_sql.setSqlId(sql_id);//指定使用的Sql的id
        my_sql.addSubPara(sOtherNo);//指定传入的参数
    }
    var tQuerySQL = "";
    if (sAppType != null && sAppType != "6" && sAppType != "7")
    {
        var tQuerySQL = "and a.CustomGetPolDate <= '" + sEdorItemAppDate + "'";
       
    }
    //alert(QuerySQL);
    my_sql.addSubPara(tQuerySQL);//指定传入的参数
    QuerySQL = my_sql.getString();
    
    try
    {
        turnPageContPwdGrid.pageLineNum = 100;
        turnPageContPwdGrid.pageDivName = "divTurnPageContPwdGrid";
        turnPageContPwdGrid.queryModal(QuerySQL, ContPwdGrid);
    }
    catch (ex)
    {
        alert("警告：查询需要输入密码的保单信息出现异常！ ");
    }
}

/*============================================================================*/

/**
 * 让输入密码框获得焦点
 */
function focusContPwdGrid()
{
    if (ContPwdGrid.mulLineCount > 0)
    {
        try
        {
            ContPwdGrid.setFocus(0, 7);
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 校验保单密码录入结果
 */
function verifyContPwd()
{
    if (!ContPwdGrid.checkValue2())
    {
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
    document.forms(0).action = "PEdorAppPwdVerifySave.jsp";
    document.forms(0).submit();
}

/*============================================================================*/

/**
 * 校验成功返回主界面
 */
function verifyPwdSucc()
{
    try
    {
        top.returnValue = true;
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * 校验失败返回主界面
 */
function verifyPwdFail()
{
    try
    {
        top.returnValue = false;
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/*============================================================================*/


//<!-- JavaScript Document END -->
