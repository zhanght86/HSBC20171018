/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2006-03-16, 2006-05-16, 2006-06-28, 2006-11-08, 2007-04-20
 * @direction: 保全收付费方式变更主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();                //全局变量, 查询结果翻页, 必须有
var turnPageEdorInfoGrid = new turnPageClass();    //全局变量, 保全信息查询结果翻页
var turnPageChgTrackGrid = new turnPageClass();    //全局变量, 变更轨迹查询结果翻页

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
			var iHeight=250;     //弹出窗口的高度; 
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
            queryEdorInfoGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * showCodeList 的回调函数, 银行帐户信息录入层的显示或隐藏
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "EdorGetPayForm")
    {
        try
        {
            if (oCodeListField.value == "4" || oCodeListField.value == "7")    //银行划款或网上支付
                enableBankInfo(true);
            else
                enableBankInfo(false);
        }
        catch (ex)
        {
            alert("警告：银行帐户信息录入显示/隐藏出现异常！ ");
        }
    } //CodeListType == EdorGetPayForm
}

/*============================================================================*/

/**
 * 根据选择的收付费方式允许/禁止银行帐户信息的录入
 */
function enableBankInfo(isEnableInput)
{
    if (isEnableInput)
    {
        try
        {
            document.getElementsByName("BankCode")[0].disabled = false;
            document.getElementsByName("BankCodeName")[0].disabled = false;
            document.getElementsByName("BankAccNo")[0].disabled = false;
            document.getElementsByName("AccName")[0].disabled = false;
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.getElementsByName("BankCode")[0].disabled = true;
            document.getElementsByName("BankCodeName")[0].disabled = true;
            document.getElementsByName("BankAccNo")[0].disabled = true;
            document.getElementsByName("AccName")[0].disabled = true;
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 回车查询保全批改信息
 */
function queryOnKeyPress()
{
    if (event.keyCode == "13")
    {
        queryEdorAndTrack();
    }
}



/*============================================================================*/

/**
 * 查询保全批改信息
 */
function queryEdorInfoGrid()
{
	
	  var sEdorAcceptNo, sGetNoticeNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo_srh")[0].value;
        sGetNoticeNo = document.getElementsByName("GetNoticeNo_srh")[0].value;
    }
    catch (ex) {}
    if ((sEdorAcceptNo == null || trim(sEdorAcceptNo) == "") && (sGetNoticeNo == null || trim(sGetNoticeNo) == ""))
    {
        alert("您必须至少输入一个查询条件(保全受理号或者领取通知书号)！ ");
        return;
    }
    //查询应收实收
    try
    {
        queryLJSPay();
        queryLJAGet();
        //queryBankInfo();
    }
    catch (ex) {}
    //如果查询结果不为空
    if (EdorInfoGrid.mulLineCount > 0)
    {
        //必需字段赋值
        try
        {
            document.getElementsByName("EdorAcceptNo")[0].value = EdorInfoGrid.getRowColData(0, 1);
            document.getElementsByName("GetNoticeNo")[0].value = EdorInfoGrid.getRowColData(0, 9);
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询应收信息
 */
function queryLJSPay()
{
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.EdorPayGetFormChangeInputSql");//指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.GetNoticeNo_srh.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorAcceptNo_srh.value);//指定传入的参数
		mySql1.addSubPara(fm.Drawer_srh.value);//指定传入的参数
		mySql1.addSubPara(fm.DrawerIDNo_srh.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorAcceptNo_srh.value);//指定传入的参数
		mySql1.addSubPara(fm.CurrentManageCom.value);//指定传入的参数
		
		
		var QuerySQL=mySql1.getString();
    
//    var QuerySQL = "select b.EdorAcceptNo, "
//                 +        "b.GrpContNo, "
//                 +        "b.ManageCom, "
//                 +        "b.EdorType, "
//                 +        "a.SUMDUEPAYMONEY, "
//                 +        "b.GetInterest, "
//                 +        "a.PayDate, "
//                 +        "b.EdorValiDate, "
//                 +        "a.GetNoticeNo, "
//                 +        "c.PayForm,c.PayGetName,c.PersonID,c.BankCode,c.BankAccNo,c.AccName,'' "
//                 +   "from LJSPay a, LPGrpEdorItem b,lpedorapp c "
//                 +  "where 1 = 1 "
//                 +     getWherePart("a.GetNoticeNo", "GetNoticeNo_srh")
//                 +     getWherePart("a.OtherNo", "EdorAcceptNo_srh")
//                 +     getWherePart("c.PayGetName", "Drawer_srh","like")
//                 +     getWherePart("c.PersonID", "DrawerIDNo_srh","like")
//                 +     getWherePart("a.OtherNo", "EdorAcceptNo_srh")
//                 +     getWherePart("b.ManageCom", "CurrentManageCom", "like")
//                 +    "and a.OtherNoType = '10' "
//                 +    "and b.EdorAcceptNo = a.OtherNo "
//                 + " and c.EdorAcceptNo = a.otherno and c.edoracceptno = b.edoracceptno "
//                 +  "order by b.EdorAcceptNo asc, b.EdorValiDate asc";
    //alert(QuerySQL);
    try
    {
        turnPageEdorInfoGrid.pageDivName = "divTurnPageEdorInfoGrid";
        turnPageEdorInfoGrid.queryModal(QuerySQL, EdorInfoGrid);
    }
    catch (ex)
    {
        alert("警告：查询保全批改信息出现异常！ ");
        return;
    }
    //设定变更方式
    if (EdorInfoGrid.mulLineCount > 0)
    {
        try
        {
            document.getElementsByName("ChangeType")[0].value = "1";
            //document.getElementsByName("ChangeTypeName")[0].value = "收费";
            //document.getElementsByName("ChangeType")[0].ondblclick = "";
            //document.getElementsByName("ChangeType")[0].onkeyup = "";
            //document.getElementsByName("ChangeType")[0].readOnly = true;
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询实付信息
 */
function queryLJAGet()
{
    //是否需要查询
    if (EdorInfoGrid.mulLineCount > 0) return;
    //拼写 SQL 语句
    
    var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.EdorPayGetFormChangeInputSql");//指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.CurrentManageCom.value);//指定传入的参数
		mySql2.addSubPara(fm.GetNoticeNo_srh.value);//指定传入的参数
		mySql2.addSubPara(fm.Drawer_srh.value);//指定传入的参数
		mySql2.addSubPara(fm.DrawerIDNo_srh.value);//指定传入的参数
		mySql2.addSubPara(fm.EdorAcceptNo_srh.value);//指定传入的参数
		
		var QuerySQL=mySql2.getString();
    
//    var QuerySQL = "select b.EdorAcceptNo, "
//                 +        "b.GrpContNo, "
//                 +        "b.ManageCom, "
//                 +        "b.EdorType, "
//                 +        "a.SUMGETMONEY, "
//                 +        "b.GetInterest, "
//                 +        "a.EnterAccDate, "
//                 +        "b.EdorValiDate, "
//                 +        "a.GetNoticeNo, "
//                 +        "a.PayMode, "
//                 +        "a.Drawer, "
//                 +        "a.DrawerID, "
//                 +        "a.BankCode, "
//                 +        "a.BankAccNo, "
//                 +        "a.AccName, "
//                 +        "a.ActuGetNo "
//                 +   "from LJAGet a, LPGrpEdorItem b,lpedorapp c "
//                 +  "where 1 = 1 "
//                 +     getWherePart("a.GetNoticeNo", "GetNoticeNo_srh")
//                 +     getWherePart("c.EdorAcceptNo", "EdorAcceptNo_srh")
//                 +     getWherePart("a.Drawer", "Drawer_srh","like")
//                 +     getWherePart("a.DrawerId", "DrawerIDNo_srh","like")
//                 +     getWherePart("b.ManageCom", "CurrentManageCom", "like")
//                 +    "and a.OtherNoType = '10' "
//                 +    "and c.EDORCONFNO = a.OtherNo "
//                 //modidfy by jiaqiangli 2009-02-22 ljaget.otherno=lpedorapp.edorconfno 为归档号
//                 +    "and b.EdorAcceptNo = c.EdorAcceptNo "
//                 +  "order by b.EdorAcceptNo asc, b.EdorValiDate asc";
    //执行 SQL 查询
    try
    {
        turnPageEdorInfoGrid.pageDivName = "divTurnPageEdorInfoGrid";
        turnPageEdorInfoGrid.queryModal(QuerySQL, EdorInfoGrid);
    }
    catch (ex)
    {
        alert("警告：查询保全批改信息出现异常！ ");
        return;
    }
    //设定变更方式
    if (EdorInfoGrid.mulLineCount > 0)
    {
        try
        {
            document.getElementsByName("ChangeType")[0].value = "2";
            //document.getElementsByName("ChangeTypeName")[0].value = "付费";
            //document.getElementsByName("ChangeType")[0].ondblclick = "";
            //document.getElementsByName("ChangeType")[0].onkeyup = "";
            //document.getElementsByName("ChangeType")[0].readOnly = true;
        }
        catch (ex) {}
    }
}






/*============================================================================*/

/**
 * 检查银行帐户信息
 */
function isHaveBankInfo()
{
    var sFormType, sBankCode, sBankAccNo, sAccName;
    try
    {
        sFormType = document.getElementsByName("FormType")[0].value;
        sBankCode = document.getElementsByName("BankCode")[0].value;
        sBankAccNo = document.getElementsByName("BankAccNo")[0].value;
        sAccName = document.getElementsByName("AccName")[0].value;
    }
    catch (ex) {}
    if (sFormType == "4" || sFormType == "7")    //银行划款或网上支付
    {
        if (sBankCode == null || trim(sBankCode)== "" || sBankAccNo == null || trim(sBankAccNo)== "" || sAccName == null || trim(sAccName)== "")
        {
            alert("银行划款或网上支付必须录入完整的银行帐户信息！ ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/**
 * 检查是否已经查询过
 */
function isHaveQueried()
{
    var sEdorAcceptNo, sGetNoticeNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sGetNoticeNo = document.getElementsByName("GetNoticeNo")[0].value;
    }
    catch (ex) {}
    if ((sEdorAcceptNo == null || trim(sEdorAcceptNo) == "") && (sGetNoticeNo == null || trim(sGetNoticeNo) == ""))
    {
        alert("请先输入保全受理号或领取通知书号进行查询！ ");
        return false;
    }
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "" || sGetNoticeNo == null || trim(sGetNoticeNo) == "")
    {
        alert("无法获取保全受理号、领取通知书号！ ");
        return false;
    }
    return true;
}

/*============================================================================*/

/**
 * 保存收付费方式变更结果
 */
function savePayGetForm()
{
		//alert(fm.GetNoticeNo.value);
    if (!isHaveQueried())     return;
    if (!verifyInput2())      return;
    if (!isHaveBankInfo())    return;
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
    document.forms[0].action = "EdorPayGetFormChangeSave.jsp";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/




function ShowSubAccInfo()
{
		var tSelNo = EdorInfoGrid.getSelNo()-1;
		fm.EdorAcceptNo.value = EdorInfoGrid.getRowColData(tSelNo, 1);
		fm.GetNoticeNo.value = EdorInfoGrid.getRowColData(tSelNo, 9);
		fm.FormType.value = EdorInfoGrid.getRowColData(tSelNo, 10);
		showOneCodeName('EdorGetPayForm', 'FormType', 'FormTypeName');
		fm.PayGetName.value = EdorInfoGrid.getRowColData(tSelNo, 11);
		fm.PersonID.value = EdorInfoGrid.getRowColData(tSelNo, 12);
		fm.BankCode.value = EdorInfoGrid.getRowColData(tSelNo, 13);
		showOneCodeName('Bank', 'BankCode', 'BankCodeName');
		fm.BankAccNo.value = EdorInfoGrid.getRowColData(tSelNo, 14);
		fm.AccName.value = EdorInfoGrid.getRowColData(tSelNo, 15);
		fm.ActuGetNo.value = EdorInfoGrid.getRowColData(tSelNo, 16);
		if (fm.FormType.value == "4"){    //银行划款或网上支付
        enableBankInfo(true);
    }else{
        enableBankInfo(false);
    }

}

/*============================================================================*/


//<!-- JavaScript Document END -->
