/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.01
 * @date     : 2005-12-14, 2006-08-19, 2006-10-26
 * @direction: 保全人工核保投(被)保人既往保全查询主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();                //全局变量, 查询结果翻页, 必须有
var turnPageEdorGrid = new turnPageClass();        //全局变量, 既往保全批单信息查询结果翻页
var turnPagePrintGrid = new turnPageClass();       //全局变量, 既往保全批单核保通知书信息查询结果翻页
var turnPageEdorItemGrid = new turnPageClass();    //全局变量, 既往保全批改项目信息查询结果翻页
var turnPagePremGrid = new turnPageClass();        //全局变量, 既往保全批改项目加费信息查询结果翻页
var turnPageSpecGrid = new turnPageClass();        //全局变量, 既往保全批改项目特别约定信息查询结果翻页

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
}

/*============================================================================*/

/**
 * 客户信息查询
 */
function queryCustomerInfo()
{
    var sCustomerNo;
    try
    {
        sCustomerNo = document.getElementsByName("CustomerNo")[0].value;
    }
    catch (ex) {}
    if (sCustomerNo == null || trim(sCustomerNo) == "")
    {
        alert("无法获取客户号。查询客户信息失败！ ");
        return;
    }
    else
    {
        var QuerySQL, arrResult;
		
		var sqlid1="EdorAgoQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(sCustomerNo);//指定传入的参数
	    QuerySQL=mySql1.getString();	
		
//        QuerySQL = "select Name "
//                 +   "from LDPerson "
//                 +  "where CustomerNo = '" + sCustomerNo + "'";
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询客户信息出现异常！ ");
            return;
        }
        if (arrResult != null)
        {
            try
            {
                document.getElementsByName("CustomerName")[0].value = arrResult[0][0];
            }
            catch (ex) {}
        }
    }
}

/*============================================================================*/

/**
 * 既往保全批单信息查询
 */
function queryCustomerEdor()
{
    var sCustomerNo, sCurEdorAcceptNo;
    try
    {
        sCustomerNo = document.getElementsByName("CustomerNo")[0].value;
        sCurEdorAcceptNo = document.getElementsByName("CurEdorAcceptNo")[0].value;
    }
    catch (ex) {}
    if (sCustomerNo == null || trim(sCustomerNo) == "" || sCurEdorAcceptNo == null || trim(sCurEdorAcceptNo) == "")
    {
        alert("无法获取客户号、保全受理号。查询客户既往保全批单信息失败！ ");
        return;
    }
    else
    {
		
		var sqlid2="EdorAgoQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(sCurEdorAcceptNo);//指定传入的参数
		mySql2.addSubPara(sCustomerNo);//指定传入的参数
	    var QuerySQL=mySql2.getString();	
		
//        var QuerySQL = "select a.EdorNo, "
//                     +        "a.ContNo, "
//                     +        "(select AppntName "
//                     +           "from LCAppnt "
//                     +          "where ContNo = a.ContNo), "
//                     +        "a.EdorAppDate, "
//                     +        "a.EdorValiDate, "
//                     +        "(select a.uwState || '-' || trim(CodeName) "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'edorcontuwstate' "
//                     +            "and trim(Code) = trim(a.uwState)), "
//                     +        "(select a.EdorState || '-' || trim(CodeName) "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'edorstate' "
//                     +            "and trim(Code) = trim(a.EdorState)), "
//                     +        "a.EdorAcceptNo "
//                     +   "from LPEdorMain a "
//                     +  "where 1 = 1 "
//                     +    "and a.EdorAcceptNo <> '" + sCurEdorAcceptNo + "' "
//                     +    "and a.ContNo in "
//                     +        "(select ContNo "
//                     +           "from LCCont "
//                     +          "where InsuredNo = '" + sCustomerNo + "') "
//                     +  "order by a.EdorNo asc, a.EdorAppDate asc ";
        //alert(QuerySQL);
        try
        {
            turnPageEdorGrid.pageDivName = "divTurnPageEdorGrid";
            turnPageEdorGrid.queryModal(QuerySQL, EdorGrid);
        }
        catch (ex)
        {
            alert("警告：查询既往保全批单信息出现异常！ ");
            return;
        }
        if (EdorGrid.mulLineCount == 0)
        {
            alert("客户 " + sCustomerNo + " 没有既往保全批单信息！ ");
        }
    }
}

/*============================================================================*/

/**
 * 既往保全批单核保通知书查询和批改项目信息查询
 */
function queryUWPrintEdorItem()
{
    queryEdorUWNotice();
    queryEdorItem();
}

/*============================================================================*/

/**
 * 既往保全批单核保通知书查询
 */
function queryEdorUWNotice()
{
    var nSelNo, sEdorNo;
    try
    {
        nSelNo = EdorGrid.getSelNo() - 1;
        sEdorNo = EdorGrid.getRowColData(nSelNo, 1);
    }
    catch (ex) {}
    if (sEdorNo == null || trim(sEdorNo) == "")
    {
        alert("无法获取批单号。查询既往保全批改项目核保通知书失败！ ");
        return;
    }
    else
    {
		
		var sqlid3="EdorAgoQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(sEdorNo);//指定传入的参数
	    var QuerySQL=mySql3.getString();	
		
//        var QuerySQL = "select a.PrtSeq, "
//                     +        "a.OtherNo, "
//                     +        "trim(a.Code) || '-' || "
//                     +        "(select CodeName "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'bquwnotice' "
//                     +            "and Code = a.Code), "
//                     +        "a.ManageCom, "
//                     +        "(select Name "
//                     +           "from LDCom "
//                     +          "where ComCode = a.ManageCom), "
//                     +        "a.ReqOperator, "
//                     +        "a.MakeDate "
//                     +   "from LOPrtManager a "
//                     +  "where 1 = 1 "
//                     +    "and StandByFlag1 = '" + sEdorNo + "' "
//                     +    "and exists "
//                     +        "(select 'X' "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +           "and CodeType = 'bquwnotice' "
//                     +           "and Code = a.Code) "
//                     +  "order by a.PrtSeq asc";
        try
        {
            turnPagePrintGrid.pageDivName = "divTurnPagePrintGrid";
            turnPagePrintGrid.queryModal(QuerySQL, PrintGrid);
        }
        catch (ex)
        {
            alert("警告：查询客户既往保全批改项目信息出现异常！ ");
            return;
        }
    } //sEdorNo != null
}

/*============================================================================*/

/**
 * 既往保全批改项目信息查询
 */
function queryEdorItem()
{
    var nSelNo, sEdorNo, sContNo;
    try
    {
        nSelNo = EdorGrid.getSelNo() - 1;
        sEdorNo = EdorGrid.getRowColData(nSelNo, 1);
        sContNo = EdorGrid.getRowColData(nSelNo, 2);
    }
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "" || sEdorNo == null || trim(sEdorNo) == "")
    {
        alert("无法获取合同号、批单号。查询客户既往保全批改项目信息失败！ ");
        return;
    }
    else
    {
		
		var sqlid4="EdorAgoQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(sContNo);//指定传入的参数
		mySql4.addSubPara(sEdorNo);//指定传入的参数
	    var QuerySQL=mySql4.getString();	
		
//        var QuerySQL = "select a.EdorNo, "
//                     +        "a.ContNo, "
//                     +        "(select distinct EdorCode || '-' || EdorName "
//                     +        "   from LMEdorItem "
//                     +        "  where EdorCode = a.EdorType), "
//                     +        "a.InsuredNo, "
//                     +        "a.PolNo, "
//                     +        "a.EdorAppDate, "
//                     +        "a.EdorValiDate, "
//                     +        "(select a.uwFlag || '-' || trim(CodeName) "
//                     +           "from LDCode "
//                     +          "where 1 = 1 "
//                     +            "and CodeType = 'edoruwstate' "
//                     +            "and trim(Code) = trim(a.uwFlag)), "
//                     +        "a.EdorState "
//                     +   "from LPEdorItem a "
//                     +  "where 1 = 1 "
//                     +    "and a.ContNo = '" + sContNo + "' "
//                     +    "and a.EdorNo = '" + sEdorNo + "' "
//                     +  "order by a.EdorNo asc, a.EdorAppDate asc";
        //alert(QuerySQL);
        try
        {
            turnPageEdorItemGrid.pageDivName = "divTurnPageEdorItemGrid";
            turnPageEdorItemGrid.queryModal(QuerySQL, EdorItemGrid);
        }
        catch (ex)
        {
            alert("警告：查询客户既往保全批改项目信息出现异常！ ");
            return;
        }
        try
        {
            document.all("divPrintEdorItemLayer").style.display = "";
            document.all("divPremSpecLayer").style.display = "none";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 获取选中的批改项目信息的保全明细必要信息、加费信息、特别约定信息
 */
function queryEdorPremSpec()
{
    queryEdorItemDetail();
    queryEdorItemPrem();
    queryEdorItemSpec();
    try
    {
        document.all("divPremSpecLayer").style.display = "";
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * 获取选中的批改项目信息的必要信息, 供保全明细查询使用
 */
function queryEdorItemDetail()
{
    var nSelNo;
    try { nSelNo = EdorItemGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo >= 0)
    {
        var sEdorNo;
        try { sEdorNo = EdorItemGrid.getRowColData(nSelNo, 1); } catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
            return;
        else
        {
            var QuerySQL, arrResult;
			
				var sqlid5="EdorAgoQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(sEdorNo);//指定传入的参数
	    QuerySQL=mySql5.getString();	
			
//            QuerySQL = "select EdorAcceptNo, "
//                     +        "EdorNo, "
//                     +        "EdorType, "
//                     +        "ContNo, "
//                     +        "EdorAppDate, "
//                     +        "EdorValiDate "
//                     +  "from LPEdorItem "
//                     + "where EdorNo = '" + sEdorNo + "' "
//                     + "order by EdorAcceptNo asc, EdorNo asc";
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("警告：查询保全批改项目信息出现异常！ ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("EdorAcceptNo")[0].value = arrResult[0][0];
                    document.getElementsByName("EdorNo")[0].value = arrResult[0][1];
                    document.getElementsByName("EdorType")[0].value = arrResult[0][2];
                    document.getElementsByName("ContNo")[0].value = arrResult[0][3];
                    document.getElementsByName("EdorItemAppDate")[0].value = arrResult[0][4];
                    document.getElementsByName("EdorValiDate")[0].value = arrResult[0][5];
                }
                catch (ex) {}
            } //arrResult != null
        } //sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * 获取选中的批改项目信息的加费信息
 */
function queryEdorItemPrem()
{
    var nSelNo;
    try { nSelNo = EdorItemGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo >= 0)
    {
        var sEdorNo, sContNo, sEdorState;
        try
        {
            sEdorNo = EdorItemGrid.getRowColData(nSelNo, 1);
            sContNo = EdorItemGrid.getRowColData(nSelNo, 2);
            sEdorState = EdorItemGrid.getRowColData(nSelNo, 9);
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "" || sContNo == null || trim(sContNo) == "")
        {
            alert("无法获取批单号、保单号。查询加费信息失败！ ");
            return;
        }
        else
        {
			
		var sqlid6="EdorAgoQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	    var QuerySQL =mySql6.getString();	
			
//            var QuerySQL = "select PolNo, "
//                         +        "DutyCode, "
//                         +        "(case PayPlanType "
//                         +          "when '0' then "
//                         +           "'正常加费' "
//                         +          "when '01' then "
//                         +           "'健康加费' "
//                         +          "when '02' then "
//                         +           "'职业加费' "
//                         +          "when '03' then "
//                         +           "'复效健康加费' "
//                         +          "when '04' then "
//                         +           "'复效职业加费' "
//                         +          "else "
//                         +           "'' "
//                         +        "end), "
//                         +        "SuppRiskScore, "
//                         +        "SecInsuAddPoint, "
//                         +        "(case AddFeeDirect "
//                         +          "when '01' then "
//                         +           "'投保人' "
//                         +          "when '02' then "
//                         +           "'被保人' "
//                         +          "when '03' then "
//                         +           "'多被保险人' "
//                         +          "when '04' then "
//                         +           "'第二被保险人' "
//                         +          "else "
//                         +           "'' "
//                         +        "end), "
//                         +        "Prem, "
//                         +        "PayStartDate, "
//                         +        "PayToDate, "
//                         +        "PayEndDate ";
            //根据批改状态选择CP表
            if (sEdorState == "0" || sEdorState == "6")
            {
                QuerySQL +=  "from LCPrem "
                         +  "where 1 = 1 "
                         +    "and ContNo = '" + sContNo + "' ";
            }
            else
            {
                QuerySQL +=  "from LPPrem "
                         +  "where 1 = 1 "
                         +    "and EdorNo = '" + trim(sEdorNo) + "' ";
            }
            //去掉正常保费
                QuerySQL +=   "and PayPlanType <> '0' "
                         +  "order by PolNo asc ";
            //alert(QuerySQL);
            try
            {
                turnPagePremGrid.pageDivName = "divTurnPagePremGrid";
                turnPagePremGrid.queryModal(QuerySQL, PremGrid);
            }
            catch (ex)
            {
                alert("警告：查询选中的批改项目信息的加费信息出现异常！ ");
                return;
            }
        }
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * 获取选中的批改项目信息的特别约定信息
 */
function queryEdorItemSpec()
{
    var nSelNo;
    try { nSelNo = EdorItemGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo >= 0)
    {
        var sEdorNo;
        try { sEdorNo = EdorItemGrid.getRowColData(nSelNo, 1); } catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
            return;
        else
        {
			
		var sqlid7="EdorAgoQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("bq.EdorAgoQuerySql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(sEdorNo);//指定传入的参数
		mySql7.addSubPara(sEdorNo);//指定传入的参数
	    var QuerySQL =mySql7.getString();	
			
//            var QuerySQL = "select a.EdorNo, "
//                         +        "a.ContNo, "
//                         +        "a.PolNo, "
//                         +        "a.SpecContent, "
//                         +        "a.MakeDate "
//                         +   "from LPSpec a "
//                         +  "where 1 = 1 "
//                         +    "and a.EdorNo = '" + sEdorNo + "' "
//                         +    "and a.SerialNo = "
//                         +        "(select max(SerialNo) "
//                         +           "from LPSpec "
//                         +          "where EdorNo = '" + sEdorNo + "') "
//                         +  "order by a.EdorNo asc, a.MakeDate ";
            //alert(QuerySQL);
            try
            {
                turnPageSpecGrid.pageDivName = "divTurnPageSpecGrid";
                turnPageSpecGrid.queryModal(QuerySQL, SpecGrid);
            }
            catch (ex)
            {
                alert("警告：查询选中的批改项目信息的特别约定信息出现异常！ ");
                return;
            }
        } //sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * 影像资料查询
 */
function showImage()
{
    var nSelNo;
    try { nSelNo = EdorGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo < 0)
    {
        alert("请先选择一条批单！ ");
        return;
    }
    else
    {
        var sEdorAcceptNo;
        try
        {
            sEdorAcceptNo = EdorGrid.getRowColData(nSelNo, 8);
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("无法获取保全受理号。查询影像资料失败！ ");
            return;
        }
        else
        {
           var tUrl="../bq/ImageQueryMain.jsp?BussNo="+sEdorAcceptNo+"&BussType=BQ";
           OpenWindowNew(tUrl,"保全扫描影像","left");
//                var sPrtNo = arrResult[0][0];
//                QuerySQL = "select a.CodeAlias "
//                         +   "from LDCode a, es_doc_relation b "
//                         +  "where 1 = 1 "
//                         +    "and a.CodeType = 'bqscan' "
//                         +    "and a.Code = b.SubType "
//                         +    "and b.BussNo = '" + sEdorAcceptNo + "' "
//                         +    "and b.BussType = 'BQ'";
//                //alert(QuerySQL);
//                try
//                {
//                    arrResult = easyExecSql(QuerySQL, 1, 0);
//                }
//                catch (ex)
//                {
//                    alert("警告：查询保全扫描子业务类型编码出现异常！ ");
//                    return;
//                }
//                if (arrResult == null)
//                {
//                    alert("查询保全扫描子业务类型编码失败！ ");
//                    return;
//                }
//                else
//                {
//                    var sOpenWinURL = "EdorScan.jsp";
//                    var sParameters = "ScanFlag=1&prtNo=" + sPrtNo + "&EdorAcceptNo=" + sEdorAcceptNo + "&SubType=" + sSubType;
//                    OpenWindowNew(sOpenWinURL + "?" + sParameters, "既往保全影像资料查询", "left");
//                } //arrResult != null
        } //sEdorAcceptNo != null
    } //nSelNo > 0
}

/*============================================================================*/

/**
 * 保全核保照会查询
 */
function EdorUWQuery()
{
    var nSelNo;
    try { nSelNo = EdorGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo < 0)
    {
        alert("请先选择一条批单！ ");
        return;
    }
    else
    {
        var sEdorNo, sContNo;
        try
        {
            sEdorNo = EdorGrid.getRowColData(nSelNo, 1);
            sContNo = EdorGrid.getRowColData(nSelNo, 2);
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "" || sContNo == null || trim(sContNo) == "")
        {
            alert("无法获取合同号、批单号。查询保全核保照会失败！ ");
            return;
        }
        else
        {
            var sOpenWinURL = "EdorUWQueryMain.jsp";
            var sParameters = "ContNo=" + sContNo + "&EdorNo=" + sEdorNo;
            OpenWindowNew(sOpenWinURL + "?" + sParameters, "既往保全核保照会查询", "left");
        } //sContNo != null && sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * 既往保全批单核保通知书打印
 */
function printEdorUWNotice()
{
    var nSelNo;
    try { nSelNo = PrintGrid.getSelNo() - 1; } catch (ex) {}
    if (nSelNo < 0)
    {
        alert("请先选择一条核保通知书！ ");
        return;
    }
    else
    {
        var sPrtSeq, sManageCom, sOperator;
        try
        {
            sPrtSeq = PrintGrid.getRowColData(nSelNo, 1);
            sManageCom = PrintGrid.getRowColData(nSelNo, 4);
            sOperator = PrintGrid.getRowColData(nSelNo, 6);
        }
        catch (ex) {}
        if (sPrtSeq == null || trim(sPrtSeq) == "")
        {
            alert("无法获取打印流水号。打印保全核保通知书失败！ ");
            return;
        }
        else
        {
            var sOpenWinURL = "EdorAgoQueryUWNoticePrint.jsp";
            var sParameters = "PrtSeq=" + sPrtSeq + "&ManageCom=" + sManageCom + "&Operator=" + sOperator;
            OpenWindowNew(sOpenWinURL + "?" + sParameters, "既往保全核保通知书打印", "left");
        } //sContNo != null && sEdorNo != null
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * 保全明细查询
 */
function showEdorItemDetail()
{
    if (EdorItemGrid == null)
    {
        alert("请先选择一条批单！ ");
        return;
    }
    else
    {
        var nSelNo;
        try
        {
            nSelNo = EdorItemGrid.getSelNo() - 1;
        }
        catch (ex) {}
        if (nSelNo < 0)
        {
            alert("请先选择一条批改项目！ ");
            return;
        }
        else
        {
            var sEdorType, sEdorState;
            try
            {
                sEdorType = document.getElementsByName("EdorType")[0].value;
                sEdorState = EdorItemGrid.getRowColData(nSelNo, 9);
            }
            catch (ex) {}
            if (sEdorType == null || trim(sEdorType) == "")
            {
                alert("警告：无法获取选定批改项目的批改类型信息！ ");
                return;
            }
            else
            {
                var sOpenWinURL;
                if (sEdorState != null && trim(sEdorState) == "0")
                {
                    sOpenWinURL = "../bqs/PEdorType" + sEdorType + ".jsp";
                }
                else
                {
                    sOpenWinURL = "../bq/PEdorType" + sEdorType + ".jsp";
                }
                OpenWindowNew(sOpenWinURL, "既往保全项目明细查询", "left");
            } //sEdorType != null
        } //nSelNo > 0
    } //EdorItemGrid != null
}

/*============================================================================*/

/**
 *  返回主界面
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
